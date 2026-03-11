package com.company.iam.roles.usecases

import com.company.iam.roles.dtos.ListRolesDTO
import io.github.resilience4j.circuitbreaker.CircuitBreaker
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry
import org.keycloak.admin.client.Keycloak
import org.keycloak.representations.idm.RoleRepresentation
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class ListRolesUseCase(
    private val keycloakAdmin: Keycloak,
    circuitBreakerRegistry: CircuitBreakerRegistry,
    @Value("\${keycloak.admin.realm}") private val realm: String,
) {
    private val circuitBreaker: CircuitBreaker = circuitBreakerRegistry.circuitBreaker("keycloak-admin")

    fun execute(): List<ListRolesDTO.Output> {
        return circuitBreaker.executeSupplier {
            keycloakAdmin.realm(realm).roles().list()
                .map { it.toOutput() }
        }
    }

    private fun RoleRepresentation.toOutput() = ListRolesDTO.Output(
        id = id ?: "",
        name = name ?: "",
        description = description,
    )
}
