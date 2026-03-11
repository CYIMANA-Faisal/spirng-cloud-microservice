package com.company.iam.roles.usecases

import com.company.iam.roles.RoleResponse

interface ListRolesUseCase {
    fun listRoles(): List<RoleResponse>
}
