package com.company.iam.roles.controllers

import com.company.iam.roles.dtos.AssignRoleDTO
import com.company.iam.roles.dtos.ListRolesDTO
import com.company.iam.roles.services.RoleService
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
    private val roleService: RoleService,
) {

    @GetMapping("/roles")
    @PreAuthorize("hasAuthority('iam:role:read')")
    fun listRoles(): ResponseEntity<List<ListRolesDTO.Output>> =
        ResponseEntity.ok(roleService.listRoles())

    @PostMapping("/users/{id}/roles")
    @PreAuthorize("hasAuthority('iam:role:assign')")
    fun assignRoles(
        @PathVariable id: String,
        @RequestBody request: AssignRoleDTO.Input,
    ): ResponseEntity<Void> {
        roleService.assignRoles(id, request.roleNames)
        return ResponseEntity.ok().build()
    }
}
