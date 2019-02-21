package com.example.core.authentication.evnt;

import com.example.core.authentication.Authentication;
import com.example.core.authentication.AuthenticationException;
import org.springframework.util.Assert;

public abstract class AbstractAuthenticationFailureEvent extends AbstractAuthenticationEvent  {

    private final AuthenticationException exception;

    public AbstractAuthenticationFailureEvent(Authentication authentication,
                                              AuthenticationException exception) {
        super(authentication);
        Assert.notNull(exception, "AuthenticationException is required");
        this.exception = exception;
    }

    public AuthenticationException getException() {
        return exception;
    }

}
