package com.example.core.web.authentication;

import com.example.core.GrantedAuthority;
import com.example.core.authentication.AnonymousAuthenticationToken;
import com.example.core.authentication.Authentication;
import com.example.core.authentication.AuthenticationDetailsSource;
import com.example.core.web.context.SecurityContextHolder;
import org.springframework.util.Assert;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

public class AnonymousAuthenticationFilter extends GenericFilterBean {

    private AuthenticationDetailsSource<HttpServletRequest, ?> authenticationDetailsSource = new WebAuthenticationDetailsSource();

    private String key;
    private Object principal;
    private List<GrantedAuthority> authorities;

    public AnonymousAuthenticationFilter(String key, Object principal,
                                         List<GrantedAuthority> authorities) {
        Assert.hasLength(key, "key cannot be null or empty");
        Assert.notNull(principal, "Anonymous authentication principal must be set");
        Assert.notNull(authorities, "Anonymous authorities must be set");
        this.key = key;
        this.principal = principal;
        this.authorities = authorities;
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            SecurityContextHolder.getContext().setAuthentication(
                    createAuthentication((HttpServletRequest) req));
        }else{

        }

        chain.doFilter(req, res);
    }

    public Authentication createAuthentication(HttpServletRequest request) {
        AnonymousAuthenticationToken auth = new AnonymousAuthenticationToken(key,
                principal, authorities);
        auth.setDetails(authenticationDetailsSource.buildDetails(request));

        return auth;
    }
}
