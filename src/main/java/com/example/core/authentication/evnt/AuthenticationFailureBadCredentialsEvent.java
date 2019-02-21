package com.example.core.authentication.evnt;

import com.example.core.authentication.Authentication;
import com.example.core.authentication.AuthenticationException;

public class AuthenticationFailureBadCredentialsEvent extends  AbstractAuthenticationFailureEvent{

    public AuthenticationFailureBadCredentialsEvent(Authentication authentication,
                                                    AuthenticationException exception) {
        super(authentication, exception);
    }
}
