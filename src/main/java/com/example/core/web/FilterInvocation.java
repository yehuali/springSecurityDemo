package com.example.core.web;

import javax.servlet.FilterChain;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FilterInvocation {

    private FilterChain chain;
    private HttpServletRequest request;
    private HttpServletResponse response;

    public FilterInvocation(ServletRequest request, ServletResponse response,
                            FilterChain chain) {
        if ((request == null) || (response == null) || (chain == null)) {
            throw new IllegalArgumentException("Cannot pass null values to constructor");
        }

        this.request = (HttpServletRequest) request;
        this.response = (HttpServletResponse) response;
        this.chain = chain;
    }

    public HttpServletRequest getRequest() {
        return getHttpRequest();
    }

    public FilterChain getChain() {
        return this.chain;
    }

    public HttpServletResponse getResponse() {
        return getHttpResponse();
    }

    public HttpServletRequest getHttpRequest() {
        return this.request;
    }

    public HttpServletResponse getHttpResponse() {
        return this.response;
    }
}
