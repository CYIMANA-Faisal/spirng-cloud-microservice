package com.company.security

import org.springframework.core.convert.converter.Converter
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.oauth2.jwt.Jwt

class KeycloakRoleConverter : Converter<Jwt, Collection<GrantedAuthority>> {

    override fun convert(jwt: Jwt): Collection<GrantedAuthority> {
        val authorities = mutableListOf<GrantedAuthority>()

        // Extract realm_access.roles
        val realmAccess = jwt.getClaim<Map<String, Any>>("realm_access")
        if (realmAccess != null) {
            @Suppress("UNCHECKED_CAST")
            val realmRoles = realmAccess["roles"] as? List<String> ?: emptyList()
            realmRoles.forEach { role ->
                authorities.add(SimpleGrantedAuthority("ROLE_$role"))
            }
        }

        // Extract resource_access.<client>.roles
        val resourceAccess = jwt.getClaim<Map<String, Any>>("resource_access")
        if (resourceAccess != null) {
            resourceAccess.values.forEach { clientData ->
                @Suppress("UNCHECKED_CAST")
                val clientMap = clientData as? Map<String, Any> ?: return@forEach
                @Suppress("UNCHECKED_CAST")
                val clientRoles = clientMap["roles"] as? List<String> ?: emptyList()
                clientRoles.forEach { role ->
                    authorities.add(SimpleGrantedAuthority("ROLE_$role"))
                }
            }
        }

        return authorities
    }
}
