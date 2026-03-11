package com.company.iam.users.controllers

import com.company.iam.users.CreateUserRequest
import com.company.iam.users.UserResponse
import com.company.iam.users.usecases.CreateUserUseCase
import com.company.iam.users.usecases.ListUsersUseCase
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
    private val listUsersUseCase: ListUsersUseCase,
    private val createUserUseCase: CreateUserUseCase,
) {

    @GetMapping
    @PreAuthorize("hasAuthority('iam:user:read')")
    fun listUsers(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "20") size: Int,
        @RequestParam(required = false) search: String?,
    ): ResponseEntity<Page<UserResponse>> =
        ResponseEntity.ok(listUsersUseCase.listUsers(page, size, search))

    @PostMapping
    @PreAuthorize("hasAuthority('iam:user:create')")
    fun createUser(@RequestBody request: CreateUserRequest): ResponseEntity<UserResponse> {
        val user = createUserUseCase.createUser(request)
        return ResponseEntity.created(URI.create("/api/iam/users/${user.id}")).body(user)
    }
}
