package com.example.config;

import com.example.core.config.EnableWebSecurity;
import com.example.core.config.WebSecurityConfigurerAdapter;
import com.example.core.web.builders.HttpSecurity;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) {
        System.out.println("用户http安全配置");
    }

}
