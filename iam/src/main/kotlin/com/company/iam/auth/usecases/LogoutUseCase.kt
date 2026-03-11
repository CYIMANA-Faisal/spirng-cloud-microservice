package com.company.iam.auth.usecases

import org.springframework.beans.factory.annotation.Value
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.util.LinkedMultiValueMap
import org.springframework.web.client.RestClient

@Component
class LogoutUseCase(
    private val restClient: RestClient,
    @Value("\${keycloak.logout-endpoint}") private val logoutEndpoint: String,
    @Value("\${keycloak.client-id}") private val clientId: String,
) {
    fun execute(refreshToken: String) {
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
}
