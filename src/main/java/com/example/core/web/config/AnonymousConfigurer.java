package com.example.core.web.config;

import com.example.core.GrantedAuthority;
import com.example.core.authority.AuthorityUtils;
import com.example.core.web.HttpSecurityBuilder;
import com.example.core.web.authentication.AnonymousAuthenticationFilter;

import java.util.List;
import java.util.UUID;

public class AnonymousConfigurer<H extends HttpSecurityBuilder<H>> extends
        AbstractHttpConfigurer<AnonymousConfigurer<H>, H>{

    private String key;
    private AnonymousAuthenticationFilter authenticationFilter;
    private Object principal = "anonymousUser";
    private List<GrantedAuthority> authorities = AuthorityUtils
            .createAuthorityList("ROLE_ANONYMOUS");

    @Override
    public void init(H builder) throws Exception {
        if (authenticationFilter == null) {
            authenticationFilter = new AnonymousAuthenticationFilter(getKey(), principal,
                    authorities);
        }

    }

    @Override
    public void configure(H http) throws Exception {
        http.addFilter(authenticationFilter);
    }

    private String getKey() {
        if (key == null) {
            key = UUID.randomUUID().toString();
        }
        return key;
    }
}
