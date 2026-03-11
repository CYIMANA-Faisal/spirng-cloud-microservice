package com.company.iam.roles.dtos

class ListRolesDTO {
    // No Input — no request params
    data class Output(val id: String, val name: String, val description: String?)
}
