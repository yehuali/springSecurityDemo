package com.example.core.web.context;

import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *1.职责:请求来临时，创建SecurityContext安全上下文信息
 *            请求结束时，清空SecurityContextHolder
 * 2.虽然安全上下文信息被存储于Session中，但我们在实际使用中不应该直接操作Session，
 *     而应当使用SecurityContextHolder
 */
public class SecurityContextPersistenceFilter extends GenericFilterBean{

    static final String FILTER_APPLIED = "__spring_security_scpf_applied";

    private SecurityContextRepository repo;


    public SecurityContextPersistenceFilter(SecurityContextRepository repo) {
        this.repo = repo;
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        System.out.println("SecurityContextPersistenceFilter过滤器过滤");
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
//        SecurityContext contextBeforeChainExecution = repo.loadContext(holder);

        try {
//            SecurityContextHolder.setContext(contextBeforeChainExecution);

            //调用过滤链的下一个过滤器
            chain.doFilter(holder.getRequest(), holder.getResponse());

        }finally{//整个过滤器链走完最后清空
            request.removeAttribute(FILTER_APPLIED);
        }

    }
}
