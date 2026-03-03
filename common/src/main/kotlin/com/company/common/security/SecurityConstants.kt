package com.company.common.security

object SecurityConstants {
    const val ROLE_ADMIN = "ROLE_ADMIN"
    const val ROLE_USER = "ROLE_USER"
    const val ROLE_MANAGER = "ROLE_MANAGER"

    val PUBLIC_URLS: Array<String> = arrayOf(
        "/actuator/health",
        "/v3/api-docs/**",
        "/swagger-ui/**",
        "/swagger-ui.html"
    )
}
