package com.company.iam.auth.usecases

import com.company.iam.auth.RefreshRequest
import com.company.iam.auth.TokenResponse

interface RefreshTokenUseCase {
    fun refresh(request: RefreshRequest): TokenResponse
}
