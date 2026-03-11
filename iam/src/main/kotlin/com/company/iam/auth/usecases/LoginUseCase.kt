package com.company.iam.auth.usecases

import com.company.iam.auth.LoginRequest
import com.company.iam.auth.TokenResponse

interface LoginUseCase {
    fun login(request: LoginRequest): TokenResponse
}
