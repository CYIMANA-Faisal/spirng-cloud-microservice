package com.company.iam.roles.services

import com.company.iam.roles.dtos.ListRolesDTO

interface RoleService {
    fun listRoles(): List<ListRolesDTO.Output>
    fun assignRoles(userId: String, roleNames: List<String>)
}
