package com.company.iam.users.controllers

import com.company.iam.users.dtos.CreateUserDTO
import com.company.iam.users.dtos.ListUsersDTO
import com.company.iam.users.services.UserService
import org.springframework.data.domain.Page
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.net.URI

@RestController
@RequestMapping("/api/iam/users")
class UserController(
    private val userService: UserService,
) {

    @GetMapping
    @PreAuthorize("hasAuthority('iam:user:read')")
    fun listUsers(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "20") size: Int,
        @RequestParam(required = false) search: String?,
    ): ResponseEntity<Page<ListUsersDTO.Output>> =
        ResponseEntity.ok(userService.listUsers(page, size, search))

    @PostMapping
    @PreAuthorize("hasAuthority('iam:user:create')")
    fun createUser(@RequestBody request: CreateUserDTO.Input): ResponseEntity<CreateUserDTO.Output> {
        val user = userService.createUser(request)
        return ResponseEntity.created(URI.create("/api/iam/users/${user.id}")).body(user)
    }
}
