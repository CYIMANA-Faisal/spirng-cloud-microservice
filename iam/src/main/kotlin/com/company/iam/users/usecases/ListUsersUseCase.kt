package com.company.iam.users.usecases

import com.company.iam.users.UserResponse
import org.springframework.data.domain.Page

interface ListUsersUseCase {
    fun listUsers(page: Int, size: Int, search: String?): Page<UserResponse>
}
