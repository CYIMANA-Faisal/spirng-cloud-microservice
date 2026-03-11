package com.company.iam.roles.dtos

class AssignRoleDTO {
    data class Input(val roleNames: List<String>)
    // No Output — endpoint returns 200 with empty body
}
