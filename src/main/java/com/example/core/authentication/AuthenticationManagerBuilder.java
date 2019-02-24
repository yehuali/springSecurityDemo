package com.example.core.authentication;

import com.example.core.config.annotation.AbstractConfiguredSecurityBuilder;
import com.example.core.config.annotation.ObjectPostProcessor;
import org.springframework.util.Assert;

/**
 * SecurityBuilder用于构建AuthenticationManager
 * 可以轻松构建内存身份验证，LDAP身份验证、基于JDBC身份验证
 * 添加UserDetailService,并添加AuthenticationProvider
 */
public class AuthenticationManagerBuilder extends
        AbstractConfiguredSecurityBuilder<AuthenticationManager, AuthenticationManagerBuilder>
        implements ProviderManagerBuilder<AuthenticationManagerBuilder> {

    private AuthenticationManager parentAuthenticationManager;

    private Boolean eraseCredentials;

    private AuthenticationEventPublisher eventPublisher;

    public AuthenticationManagerBuilder(ObjectPostProcessor<Object> objectPostProcessor) {
        super(objectPostProcessor, true);
    }

    @Override
    protected AuthenticationManager performBuild() throws Exception {
        return null;
    }

    /**
     * @param eraseCredentials  如果AuthenticationManager在认证后应该清除Authentication的认证，则为true
     * @return AuthenticationManagerBuilder 进行进一步定制
     */
    public AuthenticationManagerBuilder eraseCredentials(boolean eraseCredentials) {
        this.eraseCredentials = eraseCredentials;
        return this;
    }

    /**
     * 设置AuthenticationEventPublisher
     * @param eventPublisher
     * @return AuthenticationManagerBuilder 进行进一步定制
     */
    public AuthenticationManagerBuilder authenticationEventPublisher(
            AuthenticationEventPublisher eventPublisher) {
        Assert.notNull(eventPublisher, "AuthenticationEventPublisher cannot be null");
        this.eventPublisher = eventPublisher;
        return this;
    }

    /**
      * 允许提供一个父 {@link AuthenticationManager}, 如果{@link AuthenticationManager}无法对提供的服务器进行身份验证将尝试
     * @param authenticationManager
     * @return
     */
    public AuthenticationManagerBuilder parentAuthenticationManager(
            AuthenticationManager authenticationManager) {
//        if (authenticationManager instanceof ProviderManager) {
//            eraseCredentials(((ProviderManager) authenticationManager)
//                    .isEraseCredentialsAfterAuthentication());
//        }
        this.parentAuthenticationManager = authenticationManager;
        return this;
    }


    @Override
    public AuthenticationManagerBuilder authenticationProvider(AuthenticationProvider authenticationProvider) {
        return null;
    }
}
