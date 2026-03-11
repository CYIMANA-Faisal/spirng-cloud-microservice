package com.company.iam.common.config

import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry
import org.keycloak.admin.client.Keycloak
import org.keycloak.admin.client.KeycloakBuilder
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class KeycloakAdminConfig(
    @Value("\${keycloak.admin.server-url}") private val serverUrl: String,
    @Value("\${keycloak.admin.realm}") private val realm: String,
    @Value("\${keycloak.admin.client-id}") private val clientId: String,
    @Value("\${keycloak.admin.client-secret}") private val clientSecret: String,
) {

    @Bean
    fun keycloakAdmin(): Keycloak = KeycloakBuilder.builder()
        .serverUrl(serverUrl)
        .realm(realm)
        .clientId(clientId)
        .clientSecret(clientSecret)
        .grantType("client_credentials")
        .build()

    @Bean
    fun keycloakAdminCircuitBreaker(registry: CircuitBreakerRegistry) =
        registry.circuitBreaker("keycloak-admin")
}
