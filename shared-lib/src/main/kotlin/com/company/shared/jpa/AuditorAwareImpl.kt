package com.company.shared.jpa

import org.springframework.data.domain.AuditorAware
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import java.util.Optional

@Component("auditorAware")
class AuditorAwareImpl : AuditorAware<String> {

    override fun getCurrentAuditor(): Optional<String> {
        val authentication = SecurityContextHolder.getContext().authentication
        return if (authentication == null || !authentication.isAuthenticated || authentication.principal == "anonymousUser") {
            Optional.of("system")
        } else {
            Optional.of(authentication.name)
        }
    }
}
