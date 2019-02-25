package com.example.core.web.context;

import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SecurityContextPersistenceFilter extends GenericFilterBean{

    static final String FILTER_APPLIED = "__spring_security_scpf_applied";

    private SecurityContextRepository repo;


    public SecurityContextPersistenceFilter(SecurityContextRepository repo) {
        this.repo = repo;
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        if (request.getAttribute(FILTER_APPLIED) != null) {
            //  确保一次请求该过滤器只被调用一次
            chain.doFilter(request, response);
            return;
        }

        request.setAttribute(FILTER_APPLIED, Boolean.TRUE);

        HttpRequestResponseHolder holder = new HttpRequestResponseHolder(request,
                response);
        SecurityContext contextBeforeChainExecution = repo.loadContext(holder);

        try {
            SecurityContextHolder.setContext(contextBeforeChainExecution);

            chain.doFilter(holder.getRequest(), holder.getResponse());

        }finally{

        }

    }
}
