package com.example.core.authentication.config;

import com.example.core.authentication.AuthenticationManager;
import com.example.core.authentication.AuthenticationManagerBuilder;
import com.example.core.config.annotation.SecurityConfigurer;

public class GlobalAuthenticationConfigurerAdapter implements
        SecurityConfigurer<AuthenticationManager, AuthenticationManagerBuilder> {
    @Override
    public void init(AuthenticationManagerBuilder builder) throws Exception {

    }

    @Override
    public void configure(AuthenticationManagerBuilder builder) throws Exception {

    }
}
