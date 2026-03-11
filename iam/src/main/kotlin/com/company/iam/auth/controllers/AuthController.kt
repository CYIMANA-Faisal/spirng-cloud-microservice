package com.company.iam.auth.controllers

import com.company.iam.auth.dtos.LoginDTO
import com.company.iam.auth.dtos.LogoutDTO
import com.company.iam.auth.dtos.RefreshTokenDTO
import com.company.iam.auth.dtos.UserInfoDTO
import com.company.iam.auth.services.AuthService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/iam/auth")
class AuthController(
    private val authService: AuthService,
) {

    @PostMapping("/login")
    fun login(@RequestBody request: LoginDTO.Input): ResponseEntity<LoginDTO.Output> =
        ResponseEntity.ok(authService.login(request))

    @PostMapping("/token/refresh")
    fun refresh(@RequestBody request: RefreshTokenDTO.Input): ResponseEntity<RefreshTokenDTO.Output> =
        ResponseEntity.ok(authService.refresh(request))

    @PostMapping("/logout")
    fun logout(@RequestBody request: LogoutDTO.Input): ResponseEntity<Void> {
        authService.logout(request.refreshToken)
        return ResponseEntity.noContent().build()
    }

    @GetMapping("/userinfo")
    fun userinfo(): ResponseEntity<UserInfoDTO.Output> =
        ResponseEntity.ok(authService.getCurrentUser())
}
