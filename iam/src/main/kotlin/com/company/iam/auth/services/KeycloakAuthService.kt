package com.company.iam.auth.services

import com.company.iam.auth.LoginRequest
import com.company.iam.auth.RefreshRequest
import com.company.iam.auth.TokenResponse
import com.company.iam.auth.UserInfoResponse
import com.company.iam.auth.usecases.GetCurrentUserUseCase
import com.company.iam.auth.usecases.LoginUseCase
import com.company.iam.auth.usecases.LogoutUseCase
import com.company.iam.auth.usecases.RefreshTokenUseCase
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.MediaType
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.stereotype.Service
import org.springframework.util.LinkedMultiValueMap
import org.springframework.web.client.RestClient
import org.springframework.web.client.body

@Service
class KeycloakAuthService(
    private val restClient: RestClient,
    @Value("\${keycloak.token-endpoint}") private val tokenEndpoint: String,
    @Value("\${keycloak.logout-endpoint}") private val logoutEndpoint: String,
    @Value("\${keycloak.client-id}") private val clientId: String,
) : LoginUseCase, RefreshTokenUseCase, LogoutUseCase, GetCurrentUserUseCase {

    override fun login(request: LoginRequest): TokenResponse {
        val form = LinkedMultiValueMap<String, String>().apply {
            add("grant_type", "password")
            add("client_id", clientId)
            add("username", request.username)
            add("password", request.password)
        }
        return restClient.post()
            .uri(tokenEndpoint)
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .body(form)
            .retrieve()
            .body<TokenResponse>()!!
    }

    override fun refresh(request: RefreshRequest): TokenResponse {
        val form = LinkedMultiValueMap<String, String>().apply {
            add("grant_type", "refresh_token")
            add("client_id", clientId)
            add("refresh_token", request.refreshToken)
        }
        return restClient.post()
            .uri(tokenEndpoint)
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .body(form)
            .retrieve()
            .body<TokenResponse>()!!
    }

    override fun logout(refreshToken: String) {
        val form = LinkedMultiValueMap<String, String>().apply {
            add("client_id", clientId)
            add("refresh_token", refreshToken)
        }
        restClient.post()
            .uri(logoutEndpoint)
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .body(form)
            .retrieve()
            .toBodilessEntity()
    }

    override fun getCurrentUser(): UserInfoResponse {
        val jwt = SecurityContextHolder.getContext().authentication!!.principal as Jwt
        val realmAccess = jwt.getClaim<Map<String, Any>>("realm_access") ?: emptyMap<String, Any>()
        @Suppress("UNCHECKED_CAST")
        val roles = realmAccess["roles"] as? List<String> ?: emptyList()
        return UserInfoResponse(
            sub = jwt.subject ?: "",
            email = jwt.getClaim("email") ?: "",
            name = jwt.getClaim("name") ?: "",
            roles = roles,
        )
    }
}
