package com.example.core.filter;

import com.example.core.web.builders.HttpSecurity;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;
import java.util.List;

public class FilterChainProxy extends GenericFilterBean {
    private List<HttpSecurity> filterChains;

    public FilterChainProxy(List<HttpSecurity> filterChains) {
        System.out.println("初始化FilterChainProxy");
        this.filterChains = filterChains;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("xxxxasdasasasa");
    }
}
