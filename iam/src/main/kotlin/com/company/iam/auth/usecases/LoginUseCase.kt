package com.company.iam.auth.usecases

import com.company.iam.auth.dtos.LoginDTO
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.util.LinkedMultiValueMap
import org.springframework.web.client.RestClient
import org.springframework.web.client.body

@Component
class LoginUseCase(
    private val restClient: RestClient,
    @Value("\${keycloak.token-endpoint}") private val tokenEndpoint: String,
    @Value("\${keycloak.client-id}") private val clientId: String,
) {
    fun execute(request: LoginDTO.Input): LoginDTO.Output {
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
            .body<LoginDTO.Output>()!!
    }
}
