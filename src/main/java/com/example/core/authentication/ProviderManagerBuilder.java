package com.example.core.authentication;

import com.example.core.config.annotation.SecurityBuilder;

/**
 * 利用SecurityBuilder创建ProviderManager
 */
public interface ProviderManagerBuilder<B extends ProviderManagerBuilder<B>> extends SecurityBuilder<AuthenticationManager> {

    /**
     * 添加自定义AuthenticationProvider的身份验证
     * 因为AuthenticationProvider实现是未知的，所以所有定义必须在外部完成，并立即返回
     * @param authenticationProvider
     * @return
     */
    B authenticationProvider(AuthenticationProvider authenticationProvider);
}
