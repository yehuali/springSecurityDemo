package com.example.core.web.access;

import com.example.core.authentication.Authentication;

import java.util.Collection;

/**
 *做出最终的访问控制(授权)决策
 */
public interface AccessDecisionManager {
    /**
     * 解析传递参数的访问控制决策
     * @param authentication 调用者调用此方法(不能为空)
     * @param object
     * @param configAttributes
     */
    void decide(Authentication authentication, Object object,
                Collection<ConfigAttribute> configAttributes) throws AccessDeniedException;
}
