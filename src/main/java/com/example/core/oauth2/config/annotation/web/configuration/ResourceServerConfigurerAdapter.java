package com.example.core.oauth2.config.annotation.web.configuration;

import com.example.core.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import com.example.core.web.builders.HttpSecurity;

public class ResourceServerConfigurerAdapter implements ResourceServerConfigurer {

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {

    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
    }
}
