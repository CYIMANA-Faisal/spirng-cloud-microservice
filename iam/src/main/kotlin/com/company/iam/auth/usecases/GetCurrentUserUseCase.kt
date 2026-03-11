package com.company.iam.auth.usecases

import com.company.iam.auth.dtos.UserInfoDTO
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.stereotype.Component

@Component
class GetCurrentUserUseCase {
    fun execute(): UserInfoDTO.Output {
        val jwt = SecurityContextHolder.getContext().authentication!!.principal as Jwt
        val realmAccess = jwt.getClaim<Map<String, Any>>("realm_access") ?: emptyMap<String, Any>()
        @Suppress("UNCHECKED_CAST")
        val roles = realmAccess["roles"] as? List<String> ?: emptyList()
        return UserInfoDTO.Output(
            sub = jwt.subject ?: "",
            email = jwt.getClaim("email") ?: "",
            name = jwt.getClaim("name") ?: "",
            roles = roles,
        )
    }
}
