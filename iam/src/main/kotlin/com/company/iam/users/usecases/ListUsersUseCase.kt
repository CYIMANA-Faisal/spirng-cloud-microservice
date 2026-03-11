package com.company.iam.users.usecases

import com.company.iam.users.dtos.ListUsersDTO
import io.github.resilience4j.circuitbreaker.CircuitBreaker
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry
import org.keycloak.admin.client.Keycloak
import org.keycloak.representations.idm.UserRepresentation
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Component

@Component
class ListUsersUseCase(
    private val keycloakAdmin: Keycloak,
    circuitBreakerRegistry: CircuitBreakerRegistry,
    @Value("\${keycloak.admin.realm}") private val realm: String,
) {
    private val circuitBreaker: CircuitBreaker = circuitBreakerRegistry.circuitBreaker("keycloak-admin")

    fun execute(page: Int, size: Int, search: String?): Page<ListUsersDTO.Output> {
        return circuitBreaker.executeSupplier {
            val realmResource = keycloakAdmin.realm(realm)
            val first = page * size
            val users = if (search != null) {
                realmResource.users().search(search, first, size)
            } else {
                realmResource.users().list(first, size)
            }
            val total = realmResource.users().count()
            PageImpl(users.map { it.toOutput() }, PageRequest.of(page, size), total.toLong())
        }
    }

    private fun UserRepresentation.toOutput() = ListUsersDTO.Output(
        id = id ?: "",
        email = email ?: "",
        firstName = firstName ?: "",
        lastName = lastName ?: "",
        username = username ?: "",
        enabled = isEnabled,
    )
}
