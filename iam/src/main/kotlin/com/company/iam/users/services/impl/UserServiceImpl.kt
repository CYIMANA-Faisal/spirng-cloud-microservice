package com.company.iam.users.services.impl

import com.company.iam.users.dtos.CreateUserDTO
import com.company.iam.users.dtos.ListUsersDTO
import com.company.iam.users.services.UserService
import com.company.iam.users.usecases.CreateUserUseCase
import com.company.iam.users.usecases.ListUsersUseCase
import org.springframework.data.domain.Page
import org.springframework.stereotype.Service

@Service
class UserServiceImpl(
    private val listUsersUseCase: ListUsersUseCase,
    private val createUserUseCase: CreateUserUseCase,
) : UserService {
    override fun listUsers(page: Int, size: Int, search: String?): Page<ListUsersDTO.Output> =
        listUsersUseCase.execute(page, size, search)

    override fun createUser(request: CreateUserDTO.Input): CreateUserDTO.Output =
        createUserUseCase.execute(request)
}
