package com.company.iam.roles.services

import com.company.iam.roles.RoleResponse
import com.company.iam.roles.usecases.AssignRoleUseCase
import com.company.iam.roles.usecases.ListRolesUseCase
import io.github.resilience4j.circuitbreaker.CircuitBreaker
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry
import org.keycloak.admin.client.Keycloak
import org.keycloak.representations.idm.RoleRepresentation
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class KeycloakRoleService(
    private val keycloakAdmin: Keycloak,
    circuitBreakerRegistry: CircuitBreakerRegistry,
    @Value("\${keycloak.admin.realm}") private val realm: String,
) : ListRolesUseCase, AssignRoleUseCase {

    private val circuitBreaker: CircuitBreaker = circuitBreakerRegistry.circuitBreaker("keycloak-admin")

    override fun listRoles(): List<RoleResponse> {
        return circuitBreaker.executeSupplier {
            keycloakAdmin.realm(realm).roles().list()
                .map { it.toRoleResponse() }
        }
    }

    override fun assignRoles(userId: String, roleNames: List<String>) {
        circuitBreaker.executeRunnable {
            val callerAuthorities = SecurityContextHolder.getContext().authentication!!.authorities
                .map { it.authority }

            val privilegeViolations = roleNames.filter { roleName -> roleName !in callerAuthorities }
            if (privilegeViolations.isNotEmpty()) {
                throw ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "Caller does not possess the following roles being assigned: $privilegeViolations"
                )
            }

            val realmResource = keycloakAdmin.realm(realm)
            val userResource = try {
                realmResource.users().get(userId).also { it.toRepresentation() }
            } catch (e: Exception) {
                throw ResponseStatusException(HttpStatus.NOT_FOUND, "User $userId not found")
            }

            val rolesToAssign = roleNames.mapNotNull { roleName ->
                try {
                    realmResource.roles().get(roleName).toRepresentation()
                } catch (e: Exception) {
                    throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Role $roleName not found")
                }
            }

            userResource.roles().realmLevel().add(rolesToAssign)
        }
    }

    private fun RoleRepresentation.toRoleResponse() = RoleResponse(
        id = id ?: "",
        name = name ?: "",
        description = description,
    )
}
