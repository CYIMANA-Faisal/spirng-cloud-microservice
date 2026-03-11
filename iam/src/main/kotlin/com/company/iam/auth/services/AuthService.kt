package com.company.iam.auth.services

import com.company.iam.auth.dtos.LoginDTO
import com.company.iam.auth.dtos.RefreshTokenDTO
import com.company.iam.auth.dtos.UserInfoDTO

interface AuthService {
    fun login(request: LoginDTO.Input): LoginDTO.Output
    fun refresh(request: RefreshTokenDTO.Input): RefreshTokenDTO.Output
    fun logout(refreshToken: String)
    fun getCurrentUser(): UserInfoDTO.Output
}
