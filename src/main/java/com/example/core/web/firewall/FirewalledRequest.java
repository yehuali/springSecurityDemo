package com.example.core.web.firewall;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public abstract class FirewalledRequest extends HttpServletRequestWrapper {

    public FirewalledRequest(HttpServletRequest request) {
        super(request);
    }

    /**
     * 当它即将继续正确的应用程序,此方法将在请求通过过滤器链后调用
     * 实现可以选择修改请求的状态安全基础设施，同时仍保持原有
     */
    public abstract void reset();
}
