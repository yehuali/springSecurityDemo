package com.example.core.web.config;

import com.example.core.web.HttpSecurityBuilder;
import com.example.core.web.access.ExceptionTranslationFilter;

public class ExceptionHandlingConfigurer<H extends HttpSecurityBuilder<H>> extends AbstractHttpConfigurer<ExceptionHandlingConfigurer<H>, H> {
    @Override
    public void configure(H http) throws Exception {
        ExceptionTranslationFilter exceptionTranslationFilter = new ExceptionTranslationFilter();
        http.addFilter(exceptionTranslationFilter);
    }
}
