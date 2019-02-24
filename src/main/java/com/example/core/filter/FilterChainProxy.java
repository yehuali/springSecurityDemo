package com.example.core.filter;

import com.example.core.web.SecurityFilterChain;
import com.example.core.web.firewall.FirewalledRequest;
import com.example.core.web.firewall.HttpFirewall;
import com.example.core.web.firewall.StrictHttpFirewall;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Filter的作用 https://www.cnkirito.moe/spring-security-4/
 */
public class FilterChainProxy extends GenericFilterBean {

    private List<SecurityFilterChain> filterChains;

    private HttpFirewall firewall = new StrictHttpFirewall();

    public FilterChainProxy(List<SecurityFilterChain> filterChains) {
        System.out.println("初始化FilterChainProxy");
        this.filterChains = filterChains;
    }



    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("FilterChainProxy过滤器过滤");
        doFilterInternal(request,response,chain);
    }

    private void doFilterInternal(ServletRequest request, ServletResponse response,
                                  FilterChain chain) throws IOException, ServletException {

        FirewalledRequest fwRequest = firewall
                .getFirewalledRequest((HttpServletRequest) request);
        HttpServletResponse fwResponse = firewall
                .getFirewalledResponse((HttpServletResponse) response);

        List<Filter> filters = getFilters(fwRequest);

        if (filters == null || filters.size() == 0) {
            fwRequest.reset();

            chain.doFilter(fwRequest, fwResponse);
            return;
        }

        VirtualFilterChain vfc = new VirtualFilterChain(fwRequest, chain, filters);
        vfc.doFilter(fwRequest, fwResponse);
    }


    private List<Filter> getFilters(HttpServletRequest request) {
        for (SecurityFilterChain chain : filterChains) {
            if (chain.matches(request)) {
                return chain.getFilters();
            }
        }
        return null;
    }

    /**
     * 递归执行拦截器
     */
    private static class VirtualFilterChain implements FilterChain {
        private final FilterChain originalChain;
        private final List<Filter> additionalFilters;
        private final FirewalledRequest firewalledRequest;
        private final int size;
        private int currentPosition = 0;

        private VirtualFilterChain(FirewalledRequest firewalledRequest,
                                   FilterChain chain, List<Filter> additionalFilters) {
            this.originalChain = chain;
            this.additionalFilters = additionalFilters;
            this.size = additionalFilters.size();
            this.firewalledRequest = firewalledRequest;
        }

        @Override
        public void doFilter(ServletRequest request, ServletResponse response) throws IOException, ServletException {
            if (currentPosition == size) {
                //退出安全过滤器链时，禁用路径剥离
                this.firewalledRequest.reset();

                originalChain.doFilter(request, response);
            }else{
                currentPosition++;
                Filter nextFilter = additionalFilters.get(currentPosition - 1);
                nextFilter.doFilter(request, response, this);
            }
        }
    }
}
