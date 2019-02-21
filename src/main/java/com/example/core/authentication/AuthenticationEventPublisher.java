package com.example.core.authentication;


public interface AuthenticationEventPublisher {

    void publishAuthenticationSuccess(Authentication authentication);

    void publishAuthenticationFailure(AuthenticationException exception,
                                      Authentication authentication);
}
