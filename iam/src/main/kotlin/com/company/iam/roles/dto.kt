package com.company.iam.roles

data class RoleResponse(
    val id: String,
    val name: String,
    val description: String?,
)

data class AssignRolesRequest(
    val roleNames: List<String>,
)