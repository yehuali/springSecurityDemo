package com.example.core.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Import({WebSecurityConfiguration.class,ObjectPostProcessorConfiguration.class})
@Configuration
public @interface EnableWebSecurity {
}
