package com.example.core.config;

import com.example.core.web.HttpSecurityBuilder;
import com.example.core.web.config.AbstractHttpConfigurer;
import com.example.core.web.context.SecurityContextRepository;

public final class SessionManagementConfigurer<H extends HttpSecurityBuilder<H>>
        extends AbstractHttpConfigurer<SessionManagementConfigurer<H>, H> {

    @Override
    public void init(H http) throws Exception {
        HttpSessionSecurityContextRepository httpSecurityRepository = new HttpSessionSecurityContextRepository();
        http.setSharedObject(SecurityContextRepository.class,
                httpSecurityRepository);
    }
}
