package com.company.iam.auth

import com.fasterxml.jackson.annotation.JsonProperty

data class LoginRequest(
    val username: String,
    val password: String,
)

data class TokenResponse(
    @JsonProperty("access_token") val accessToken: String,
    @JsonProperty("refresh_token") val refreshToken: String,
    @JsonProperty("expires_in") val expiresIn: Long,
    @JsonProperty("token_type") val tokenType: String,
)

data class RefreshRequest(
    @JsonProperty("refresh_token") val refreshToken: String,
)

data class UserInfoResponse(
    val sub: String,
    val email: String,
    val name: String,
    val roles: List<String>,
)
