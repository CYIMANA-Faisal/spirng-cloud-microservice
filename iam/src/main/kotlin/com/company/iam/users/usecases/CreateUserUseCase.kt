package com.company.iam.users.usecases

import com.company.iam.users.CreateUserRequest
import com.company.iam.users.UserResponse

interface CreateUserUseCase {
    fun createUser(request: CreateUserRequest): UserResponse
}
