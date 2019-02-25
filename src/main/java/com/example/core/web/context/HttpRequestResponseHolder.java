package com.example.core.web.context;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 用于将请求传入{@link SecurityContextRepository#loadContext(HttpRequestResponseHolder)}
 * 该方法包装请求
 */
public final class HttpRequestResponseHolder {
    private HttpServletRequest request;
    private HttpServletResponse response;

    public HttpRequestResponseHolder(HttpServletRequest request,
                                     HttpServletResponse response) {
        this.request = request;
        this.response = response;
    }

    public HttpServletRequest getRequest() {
        return request;
    }

    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    public HttpServletResponse getResponse() {
        return response;
    }

    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }
}
