package com.company.iam.roles.services.impl

import com.company.iam.roles.dtos.ListRolesDTO
import com.company.iam.roles.services.RoleService
import com.company.iam.roles.usecases.AssignRoleUseCase
import com.company.iam.roles.usecases.ListRolesUseCase
import org.springframework.stereotype.Service

@Service
class RoleServiceImpl(
    private val listRolesUseCase: ListRolesUseCase,
    private val assignRoleUseCase: AssignRoleUseCase,
) : RoleService {
    override fun listRoles(): List<ListRolesDTO.Output> = listRolesUseCase.execute()
    override fun assignRoles(userId: String, roleNames: List<String>) = assignRoleUseCase.execute(userId, roleNames)
}
