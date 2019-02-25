package com.example.core.web.config;

import com.example.core.config.annotation.SecurityConfigurerAdapter;
import com.example.core.web.DefaultSecurityFilterChain;
import com.example.core.web.HttpSecurityBuilder;

public abstract class AbstractHttpConfigurer<T extends AbstractHttpConfigurer<T, B>, B extends HttpSecurityBuilder<B>>
        extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, B> {
}
