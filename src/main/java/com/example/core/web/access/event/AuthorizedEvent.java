package com.example.core.web.access.event;

import com.example.core.authentication.Authentication;
import com.example.core.web.access.ConfigAttribute;

import java.util.Collection;

public class AuthorizedEvent extends AbstractAuthorizationEvent {

    private Authentication authentication;
    private Collection<ConfigAttribute> configAttributes;

    public AuthorizedEvent(Object secureObject, Collection<ConfigAttribute> attributes,
                           Authentication authentication) {
        super(secureObject);

        if ((attributes == null) || (authentication == null)) {
            throw new IllegalArgumentException(
                    "All parameters are required and cannot be null");
        }

        this.configAttributes = attributes;
        this.authentication = authentication;
    }
}
