package com.company.iam.auth.dtos

import com.fasterxml.jackson.annotation.JsonProperty

class LogoutDTO {
    data class Input(@JsonProperty("refresh_token") val refreshToken: String)
    // No Output — endpoint returns 204
}
