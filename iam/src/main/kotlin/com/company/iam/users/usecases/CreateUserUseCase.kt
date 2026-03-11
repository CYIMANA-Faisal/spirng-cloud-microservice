package com.company.iam.users.usecases

import com.company.iam.users.dtos.CreateUserDTO
import io.github.resilience4j.circuitbreaker.CircuitBreaker
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry
import org.keycloak.admin.client.Keycloak
import org.keycloak.representations.idm.UserRepresentation
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.server.ResponseStatusException

@Component
class CreateUserUseCase(
    private val keycloakAdmin: Keycloak,
    circuitBreakerRegistry: CircuitBreakerRegistry,
    @Value("\${keycloak.admin.realm}") private val realm: String,
) {
    private val circuitBreaker: CircuitBreaker = circuitBreakerRegistry.circuitBreaker("keycloak-admin")

    fun execute(request: CreateUserDTO.Input): CreateUserDTO.Output {
        return circuitBreaker.executeSupplier {
            val realmResource = keycloakAdmin.realm(realm)
            val userRep = UserRepresentation().apply {
                email = request.email
                firstName = request.firstName
                lastName = request.lastName
                username = request.username ?: request.email
                isEnabled = true
                isEmailVerified = false
            }

            val response = realmResource.users().create(userRep)

            if (response.status == HttpStatus.CONFLICT.value()) {
                throw ResponseStatusException(HttpStatus.CONFLICT, "User with email ${request.email} already exists")
            }
            if (response.status != HttpStatus.CREATED.value()) {
                throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to create user")
            }

            val userId = response.location.path.substringAfterLast("/")
            realmResource.users().get(userId).sendVerifyEmail()
            realmResource.users().get(userId).toRepresentation().toOutput()
        }
    }

    private fun UserRepresentation.toOutput() = CreateUserDTO.Output(
        id = id ?: "",
        email = email ?: "",
        firstName = firstName ?: "",
        lastName = lastName ?: "",
        username = username ?: "",
        enabled = isEnabled,
    )
}
