package com.example.core.authentication;

/**
 * 表示一个类可以处理一个特定Authentication实现
 */
public interface AuthenticationProvider {

    Authentication authenticate(Authentication authentication)
            throws AuthenticationException;

    boolean supports(Class<?> authentication);
}
