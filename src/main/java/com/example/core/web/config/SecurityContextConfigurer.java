package com.example.core.web.config;

import com.example.core.web.HttpSecurityBuilder;
import com.example.core.web.context.SecurityContextPersistenceFilter;
import com.example.core.web.context.SecurityContextRepository;

public final class SecurityContextConfigurer<H extends HttpSecurityBuilder<H>> extends AbstractHttpConfigurer<SecurityContextConfigurer<H>, H>  {

    public SecurityContextConfigurer() {
    }



    @Override
    public void init(H http) throws Exception {

    }

    @Override
    public void configure(H http) throws Exception {

        SecurityContextRepository securityContextRepository = http
                .getSharedObject(SecurityContextRepository.class);

        SecurityContextPersistenceFilter securityContextFilter = new SecurityContextPersistenceFilter(
                securityContextRepository);

    }
}
