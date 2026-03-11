package com.company.iam.auth.usecases

interface LogoutUseCase {
    fun logout(refreshToken: String)
}
