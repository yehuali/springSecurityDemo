package com.example.core.web.config;

import com.example.core.authentication.AuthenticationManager;
import com.example.core.web.HttpSecurityBuilder;
import com.example.core.web.access.AccessDecisionManager;
import com.example.core.web.access.AccessDecisionVoter;
import com.example.core.web.access.intercept.FilterInvocationSecurityMetadataSource;
import com.example.core.web.access.intercept.FilterSecurityInterceptor;
import com.example.core.web.access.vote.AffirmativeBased;

import java.util.List;

public abstract class AbstractInterceptUrlConfigurer<C extends AbstractInterceptUrlConfigurer<C, H>, H extends HttpSecurityBuilder<H>>
        extends AbstractHttpConfigurer<C, H> {

    private AccessDecisionManager accessDecisionManager;

    @Override
    public void configure(H http) throws Exception {
        FilterInvocationSecurityMetadataSource metadataSource = createMetadataSource(http);
        if (metadataSource == null) {
            return;
        }
        FilterSecurityInterceptor securityInterceptor = createFilterSecurityInterceptor(
                http, metadataSource, http.getSharedObject(AuthenticationManager.class));

        securityInterceptor = postProcess(securityInterceptor);
        http.addFilter(securityInterceptor);
        http.setSharedObject(FilterSecurityInterceptor.class, securityInterceptor);
    }

    abstract class AbstractInterceptUrlRegistry<R extends AbstractInterceptUrlRegistry<R, T>, T>
            extends AbstractConfigAttributeRequestMatcherRegistry<T> {


    }

    abstract FilterInvocationSecurityMetadataSource createMetadataSource(H http);

    private FilterSecurityInterceptor createFilterSecurityInterceptor(H http,
                                                                      FilterInvocationSecurityMetadataSource metadataSource,
                                                                      AuthenticationManager authenticationManager) throws Exception {
        FilterSecurityInterceptor securityInterceptor = new FilterSecurityInterceptor();
        securityInterceptor.setSecurityMetadataSource(metadataSource);
        securityInterceptor.setAccessDecisionManager(getAccessDecisionManager(http));
        securityInterceptor.setAuthenticationManager(authenticationManager);
        securityInterceptor.afterPropertiesSet();
        return securityInterceptor;
    }

    private AccessDecisionManager getAccessDecisionManager(H http) {
        if (accessDecisionManager == null) {
            accessDecisionManager = createDefaultAccessDecisionManager(http);
        }
        return accessDecisionManager;
    }

    private AccessDecisionManager createDefaultAccessDecisionManager(H http) {
        AffirmativeBased result = new AffirmativeBased(getDecisionVoters(http));
        return result;
    }

    abstract List<AccessDecisionVoter<? extends Object>> getDecisionVoters(H http);
}
