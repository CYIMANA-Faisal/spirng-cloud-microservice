package com.company.iam.common.config

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.security.SecurityScheme
import io.swagger.v3.oas.models.security.SecurityRequirement
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class OpenApiConfig {

    @Bean
    fun iamOpenAPI(): OpenAPI = OpenAPI()
        .info(
            Info()
                .title("IAM Service API")
                .description("Identity and Access Management — authentication, users, and roles")
                .version("1.0.0")
        )
        .components(
            Components().addSecuritySchemes(
                "bearer-jwt",
                SecurityScheme()
                    .type(SecurityScheme.Type.HTTP)
                    .scheme("bearer")
                    .bearerFormat("JWT")
                    .description("JWT issued by Keycloak")
            )
        )
        .addSecurityItem(SecurityRequirement().addList("bearer-jwt"))
}
