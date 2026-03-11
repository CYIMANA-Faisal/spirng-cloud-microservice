package com.company.iam.users.dtos

class CreateUserDTO {
    data class Input(val email: String, val firstName: String, val lastName: String, val username: String? = null)
    data class Output(val id: String, val email: String, val firstName: String, val lastName: String, val username: String, val enabled: Boolean)
}
