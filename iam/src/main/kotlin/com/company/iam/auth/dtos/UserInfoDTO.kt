package com.company.iam.auth.dtos

class UserInfoDTO {
    // No Input — data extracted from JWT in SecurityContext
    data class Output(val sub: String, val email: String, val name: String, val roles: List<String>)
}
