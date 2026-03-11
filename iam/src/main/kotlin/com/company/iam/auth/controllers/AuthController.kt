package com.company.iam.auth.controllers

import com.company.iam.auth.LoginRequest
import com.company.iam.auth.RefreshRequest
import com.company.iam.auth.TokenResponse
import com.company.iam.auth.UserInfoResponse
import com.company.iam.auth.usecases.GetCurrentUserUseCase
import com.company.iam.auth.usecases.LoginUseCase
import com.company.iam.auth.usecases.LogoutUseCase
import com.company.iam.auth.usecases.RefreshTokenUseCase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/iam/auth")
class AuthController(
    private val loginUseCase: LoginUseCase,
    private val refreshTokenUseCase: RefreshTokenUseCase,
    private val logoutUseCase: LogoutUseCase,
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
) {

    @PostMapping("/login")
    fun login(@RequestBody request: LoginRequest): ResponseEntity<TokenResponse> =
        ResponseEntity.ok(loginUseCase.login(request))

    @PostMapping("/token/refresh")
    fun refresh(@RequestBody request: RefreshRequest): ResponseEntity<TokenResponse> =
        ResponseEntity.ok(refreshTokenUseCase.refresh(request))

    @PostMapping("/logout")
    fun logout(@RequestBody request: RefreshRequest): ResponseEntity<Void> {
        logoutUseCase.logout(request.refreshToken)
        return ResponseEntity.noContent().build()
    }

    @GetMapping("/userinfo")
    fun userinfo(): ResponseEntity<UserInfoResponse> =
        ResponseEntity.ok(getCurrentUserUseCase.getCurrentUser())
}
