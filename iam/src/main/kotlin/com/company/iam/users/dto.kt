package com.company.iam.users

data class UserResponse(
    val id: String,
    val email: String,
    val firstName: String,
    val lastName: String,
    val username: String,
    val enabled: Boolean,
)

data class CreateUserRequest(
    val email: String,
    val firstName: String,
    val lastName: String,
    val username: String? = null,
)
