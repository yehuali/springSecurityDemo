package com.example.core.util.matcher;

import javax.servlet.http.HttpServletRequest;
/**
 * 简单策略去匹配HttpServletRequest
 */
public interface RequestMatcher {

    /**
     * 决定策略实现的规则是否与提供的请求匹配。
     * @param request
     * @return
     */
    boolean matches(HttpServletRequest request);
}
