package com.company.common.security

import org.springframework.security.oauth2.jwt.Jwt

object JwtUtils {

    fun getUserId(jwt: Jwt): String = jwt.subject

    fun getEmail(jwt: Jwt): String? = jwt.getClaim("email")

    fun getPreferredUsername(jwt: Jwt): String? = jwt.getClaim("preferred_username")

    fun getRealmRoles(jwt: Jwt): List<String> {
        val realmAccess = jwt.getClaim<Map<String, Any>>("realm_access") ?: return emptyList()
        @Suppress("UNCHECKED_CAST")
        return realmAccess["roles"] as? List<String> ?: emptyList()
    }

    fun getResourceRoles(jwt: Jwt, clientId: String): List<String> {
        val resourceAccess = jwt.getClaim<Map<String, Any>>("resource_access") ?: return emptyList()
        @Suppress("UNCHECKED_CAST")
        val clientData = resourceAccess[clientId] as? Map<String, Any> ?: return emptyList()
        @Suppress("UNCHECKED_CAST")
        return clientData["roles"] as? List<String> ?: emptyList()
    }
}
