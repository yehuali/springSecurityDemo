package com.example.core.config;

import com.example.core.authentication.config.AuthenticationConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Import({WebSecurityConfiguration.class,ObjectPostProcessorConfiguration.class, AuthenticationConfiguration.class})
@Configuration
public @interface EnableWebSecurity {
}
