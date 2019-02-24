package com.example.core.oauth2;

import com.example.core.authentication.AuthenticationManagerBuilder;
import com.example.core.config.WebSecurityConfigurerAdapter;

/**
 * Oauth2
 */
public class AuthorizationServerSecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    }
}
