package com.example.core.oauth2.config.annotation.web.configuration;

import com.example.core.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import com.example.core.web.builders.HttpSecurity;

public interface ResourceServerConfigurer {

    void configure(ResourceServerSecurityConfigurer resources) throws Exception;

    void configure(HttpSecurity http) throws Exception;
}
