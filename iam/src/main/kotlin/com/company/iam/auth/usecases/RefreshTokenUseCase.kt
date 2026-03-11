package com.company.iam.auth.usecases

import com.company.iam.auth.dtos.RefreshTokenDTO
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.util.LinkedMultiValueMap
import org.springframework.web.client.RestClient
import org.springframework.web.client.body

@Component
class RefreshTokenUseCase(
    private val restClient: RestClient,
    @Value("\${keycloak.token-endpoint}") private val tokenEndpoint: String,
    @Value("\${keycloak.client-id}") private val clientId: String,
) {
    fun execute(request: RefreshTokenDTO.Input): RefreshTokenDTO.Output {
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
            .body<RefreshTokenDTO.Output>()!!
    }
}
