package com.example.core.authentication;
/**
 * 处理Authentication请求
 */
public interface AuthenticationManager {

    Authentication authenticate(Authentication authentication)
            throws AuthenticationException;
}
