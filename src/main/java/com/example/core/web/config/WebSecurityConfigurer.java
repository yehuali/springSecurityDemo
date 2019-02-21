package com.example.core.web.config;

import com.example.core.config.annotation.SecurityBuilder;
import com.example.core.config.annotation.SecurityConfigurer;

import javax.servlet.Filter;

public interface WebSecurityConfigurer<T extends SecurityBuilder<Filter>> extends
        SecurityConfigurer<Filter, T> {
}
