package org.crsoft.cartonplast.config;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author lpillaga on 06/07/2022
 */
@Component
public class AuditAwareConfig implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }
//        String username = ((OAuth2AuthenticatedPrincipal) authentication.getPrincipal()).getAttribute("username");
//        assert username != null;
//        return Optional.of(OperationUtils.getCASUsername(username));

        return Optional.of(authentication.getName());
    }
}
