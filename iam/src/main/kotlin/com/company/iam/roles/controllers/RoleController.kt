package com.company.iam.roles.controllers

import com.company.iam.roles.AssignRolesRequest
import com.company.iam.roles.RoleResponse
import com.company.iam.roles.usecases.AssignRoleUseCase
import com.company.iam.roles.usecases.ListRolesUseCase
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/iam")
class RoleController(
    private val listRolesUseCase: ListRolesUseCase,
    private val assignRoleUseCase: AssignRoleUseCase,
) {

    @GetMapping("/roles")
    @PreAuthorize("hasAuthority('iam:role:read')")
    fun listRoles(): ResponseEntity<List<RoleResponse>> =
        ResponseEntity.ok(listRolesUseCase.listRoles())

    @PostMapping("/users/{id}/roles")
    @PreAuthorize("hasAuthority('iam:role:assign')")
    fun assignRoles(
        @PathVariable id: String,
        @RequestBody request: AssignRolesRequest,
    ): ResponseEntity<Void> {
        assignRoleUseCase.assignRoles(id, request.roleNames)
        return ResponseEntity.ok().build()
    }
}
