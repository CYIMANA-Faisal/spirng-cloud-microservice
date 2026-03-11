package com.company.iam.users.services

import com.company.iam.users.CreateUserRequest
import com.company.iam.users.UserResponse
import com.company.iam.users.usecases.CreateUserUseCase
import com.company.iam.users.usecases.ListUsersUseCase
import io.github.resilience4j.circuitbreaker.CircuitBreaker
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry
import org.keycloak.admin.client.Keycloak
import org.keycloak.representations.idm.UserRepresentation
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.http.HttpStatus
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class KeycloakUserService(
    private val keycloakAdmin: Keycloak,
    circuitBreakerRegistry: CircuitBreakerRegistry,
    @Value("\${keycloak.admin.realm}") private val realm: String,
) : ListUsersUseCase, CreateUserUseCase {

    private val circuitBreaker: CircuitBreaker = circuitBreakerRegistry.circuitBreaker("keycloak-admin")

    override fun listUsers(page: Int, size: Int, search: String?): Page<UserResponse> {
        var currentUser = SecurityContextHolder.getContext()
        return circuitBreaker.executeSupplier {
            val realmResource = keycloakAdmin.realm(realm)
            val first = page * size
            val users = if (search != null) {
                realmResource.users().search(search, first, size)
            } else {
                realmResource.users().list(first, size)
            }
            val total = realmResource.users().count()
            PageImpl(users.map { it.toUserResponse() }, PageRequest.of(page, size), total.toLong())
        }
    }

    override fun createUser(request: CreateUserRequest): UserResponse {
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
            realmResource.users().get(userId).toRepresentation().toUserResponse()
        }
    }

    private fun UserRepresentation.toUserResponse() = UserResponse(
        id = id ?: "",
        email = email ?: "",
        firstName = firstName ?: "",
        lastName = lastName ?: "",
        username = username ?: "",
        enabled = isEnabled,
    )
}
