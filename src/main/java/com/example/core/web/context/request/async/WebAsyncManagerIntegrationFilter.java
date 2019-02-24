package com.example.core.web.context.request.async;

import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 提供了{@link SecurityContext}和Spring Web的集成
 * {@link WebAsyncManager} 通过用
 * {@link SecurityContextCallableProcessingInterceptor#beforeConcurrentHandling(org.springframework.web.context.request.NativeWebRequest, Callable)}
 * 上填充{@link SecurityContext}
 */
public class WebAsyncManagerIntegrationFilter extends OncePerRequestFilter{

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        System.out.println("WebAsyncManagerIntegrationFilter过滤");
        filterChain.doFilter(request, response);
    }

}
