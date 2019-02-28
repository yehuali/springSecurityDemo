package com.example.core.web.access.event;

import com.example.core.authentication.Authentication;
import com.example.core.web.access.AccessDeniedException;
import com.example.core.web.access.ConfigAttribute;

import java.util.Collection;

public class AuthorizationFailureEvent extends AbstractAuthorizationEvent {

    private AccessDeniedException accessDeniedException;
    private Authentication authentication;
    private Collection<ConfigAttribute> configAttributes;

    public AuthorizationFailureEvent(Object secureObject,
                                     Collection<ConfigAttribute> attributes, Authentication authentication,
                                     AccessDeniedException accessDeniedException) {
        super(secureObject);

        if ((attributes == null) || (authentication == null)
                || (accessDeniedException == null)) {
            throw new IllegalArgumentException(
                    "All parameters are required and cannot be null");
        }

        this.configAttributes = attributes;
        this.authentication = authentication;
        this.accessDeniedException = accessDeniedException;
    }

    public AccessDeniedException getAccessDeniedException() {
        return accessDeniedException;
    }

    public Authentication getAuthentication() {
        return authentication;
    }

    public Collection<ConfigAttribute> getConfigAttributes() {
        return configAttributes;
    }
}
