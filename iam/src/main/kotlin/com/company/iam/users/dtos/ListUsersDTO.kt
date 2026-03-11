package com.company.iam.users.dtos

class ListUsersDTO {
    data class Input(val page: Int = 0, val size: Int = 20, val search: String? = null)
    data class Output(val id: String, val email: String, val firstName: String, val lastName: String, val username: String, val enabled: Boolean)
}
