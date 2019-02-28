package com.example.core.web.access.intercept;


import com.example.core.web.FilterInvocation;
import com.example.core.web.access.SecurityMetadataSource;

import javax.servlet.*;
import java.io.IOException;

public class FilterSecurityInterceptor extends AbstractSecurityInterceptor implements Filter {

    private static final String FILTER_APPLIED = "__spring_security_filterSecurityInterceptor_filterApplied";

    private boolean observeOncePerRequest = true;

    private FilterInvocationSecurityMetadataSource securityMetadataSource;


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("FilterSecurityInterceptor验证过滤器");
        FilterInvocation fi = new FilterInvocation(request, response, chain);
        invoke(fi);
    }

    @Override
    public void destroy() {

    }

    public void setSecurityMetadataSource(FilterInvocationSecurityMetadataSource newSource) {
        this.securityMetadataSource = newSource;
    }

    public void invoke(FilterInvocation fi) throws IOException, ServletException {
        if((fi.getRequest() != null)
            && (fi.getRequest().getAttribute(FILTER_APPLIED) != null)
            && observeOncePerRequest){//筛选器已经应用于此请求，用户希望我们观察每个请求处理一次，所以不要重新进行安全检查

            fi.getChain().doFilter(fi.getRequest(), fi.getResponse());

        }else{
            //第一次调用此请求时，请执行安全检查
            if (fi.getRequest() != null) {
                fi.getRequest().setAttribute(FILTER_APPLIED, Boolean.TRUE);
            }

            InterceptorStatusToken token = super.beforeInvocation(fi);

        }
    }


    @Override
    public SecurityMetadataSource obtainSecurityMetadataSource() {
        return this.securityMetadataSource;
    }
}
