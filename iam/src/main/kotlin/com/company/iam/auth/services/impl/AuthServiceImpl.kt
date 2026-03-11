package com.company.iam.auth.services.impl

import com.company.iam.auth.dtos.LoginDTO
import com.company.iam.auth.dtos.RefreshTokenDTO
import com.company.iam.auth.dtos.UserInfoDTO
import com.company.iam.auth.services.AuthService
import com.company.iam.auth.usecases.GetCurrentUserUseCase
import com.company.iam.auth.usecases.LoginUseCase
import com.company.iam.auth.usecases.LogoutUseCase
import com.company.iam.auth.usecases.RefreshTokenUseCase
import org.springframework.stereotype.Service

@Service
class AuthServiceImpl(
    private val loginUseCase: LoginUseCase,
    private val refreshTokenUseCase: RefreshTokenUseCase,
    private val logoutUseCase: LogoutUseCase,
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
) : AuthService {
    override fun login(request: LoginDTO.Input): LoginDTO.Output = loginUseCase.execute(request)
    override fun refresh(request: RefreshTokenDTO.Input): RefreshTokenDTO.Output = refreshTokenUseCase.execute(request)
    override fun logout(refreshToken: String) = logoutUseCase.execute(refreshToken)
    override fun getCurrentUser(): UserInfoDTO.Output = getCurrentUserUseCase.execute()
}
