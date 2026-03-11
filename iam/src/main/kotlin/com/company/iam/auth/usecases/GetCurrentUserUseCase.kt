package com.company.iam.auth.usecases

import com.company.iam.auth.UserInfoResponse

interface GetCurrentUserUseCase {
    fun getCurrentUser(): UserInfoResponse
}
