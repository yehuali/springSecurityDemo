package com.example.core.oauth2.config.annotation.web.configurers;

import com.example.core.config.annotation.SecurityConfigurerAdapter;
import com.example.core.oauth2.provider.expression.OAuth2WebSecurityExpressionHandler;
import com.example.core.web.DefaultSecurityFilterChain;
import com.example.core.web.FilterInvocation;
import com.example.core.web.access.expression.SecurityExpressionHandler;
import com.example.core.web.builders.HttpSecurity;

public final class ResourceServerSecurityConfigurer extends
        SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    private SecurityExpressionHandler<FilterInvocation> expressionHandler = new OAuth2WebSecurityExpressionHandler();

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().expressionHandler(expressionHandler);
    }
}
