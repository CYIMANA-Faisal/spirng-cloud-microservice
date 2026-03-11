package com.company.iam.users.services

import com.company.iam.users.dtos.CreateUserDTO
import com.company.iam.users.dtos.ListUsersDTO
import org.springframework.data.domain.Page

interface UserService {
    fun listUsers(page: Int, size: Int, search: String?): Page<ListUsersDTO.Output>
    fun createUser(request: CreateUserDTO.Input): CreateUserDTO.Output
}
