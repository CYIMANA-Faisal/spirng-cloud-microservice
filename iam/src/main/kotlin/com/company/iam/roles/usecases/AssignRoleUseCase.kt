package com.company.iam.roles.usecases

interface AssignRoleUseCase {
    fun assignRoles(userId: String, roleNames: List<String>)
}
