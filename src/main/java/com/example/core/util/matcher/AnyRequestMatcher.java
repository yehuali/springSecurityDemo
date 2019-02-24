package com.example.core.util.matcher;

import javax.servlet.http.HttpServletRequest;

/**
 * 匹配任何提供的请求
 */
public final class AnyRequestMatcher implements RequestMatcher {
    public static final RequestMatcher INSTANCE = new AnyRequestMatcher();

    @Override
    public boolean matches(HttpServletRequest request) {
        return true;
    }

}
