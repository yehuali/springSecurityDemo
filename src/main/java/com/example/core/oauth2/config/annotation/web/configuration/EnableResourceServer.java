package com.example.core.oauth2.config.annotation.web.configuration;

import org.springframework.context.annotation.Import;

@Import(ResourceServerConfiguration.class)
public @interface EnableResourceServer {
}
