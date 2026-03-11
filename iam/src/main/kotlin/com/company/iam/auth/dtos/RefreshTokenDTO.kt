package com.company.iam.auth.dtos

import com.fasterxml.jackson.annotation.JsonProperty

class RefreshTokenDTO {
    data class Input(@JsonProperty("refresh_token") val refreshToken: String)
    data class Output(
        @JsonProperty("access_token")  val accessToken: String,
        @JsonProperty("refresh_token") val refreshToken: String,
        @JsonProperty("expires_in")    val expiresIn: Long,
        @JsonProperty("token_type")    val tokenType: String,
    )
}
