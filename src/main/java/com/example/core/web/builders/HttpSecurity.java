package com.example.core.web.builders;

import com.example.core.authentication.AuthenticationManagerBuilder;
import com.example.core.config.SessionManagementConfigurer;
import com.example.core.config.annotation.AbstractConfiguredSecurityBuilder;
import com.example.core.config.annotation.ObjectPostProcessor;
import com.example.core.config.annotation.SecurityBuilder;
import com.example.core.config.annotation.SecurityConfigurerAdapter;
import com.example.core.util.matcher.AnyRequestMatcher;
import com.example.core.util.matcher.RequestMatcher;
import com.example.core.web.DefaultSecurityFilterChain;
import com.example.core.web.HttpSecurityBuilder;
import com.example.core.web.config.ExpressionUrlAuthorizationConfigurer;
import com.example.core.web.config.SecurityContextConfigurer;
import org.springframework.context.ApplicationContext;

import javax.servlet.Filter;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * 一个类似于Spring Security的XML
 * 1.可以为特定的http配置基于web的安全性请求，默认情况下，它将应用所有请求，但可以用 {@link #requestMatcher(requestMatcher)}限制使用
 *
 */
public class HttpSecurity extends AbstractConfiguredSecurityBuilder<DefaultSecurityFilterChain, HttpSecurity>
        implements SecurityBuilder<DefaultSecurityFilterChain>, HttpSecurityBuilder<HttpSecurity> {

    private List<Filter> filters = new ArrayList<Filter>();
    private FilterComparator comparator = new FilterComparator();
    private RequestMatcher requestMatcher = AnyRequestMatcher.INSTANCE;


    public HttpSecurity(ObjectPostProcessor<Object> objectPostProcessor,
                        AuthenticationManagerBuilder authenticationBuilder,
                        Map<Class<? extends Object>, Object> sharedObjects) {
        super(objectPostProcessor);
        for (Map.Entry<Class<? extends Object>, Object> entry : sharedObjects
                .entrySet()) {
            setSharedObject((Class<Object>) entry.getKey(), entry.getValue());
        }
    }



    @Override
    protected DefaultSecurityFilterChain performBuild() throws Exception {
        //对过滤器链中的过滤器进行排序
        Collections.sort(filters, comparator);
        return new DefaultSecurityFilterChain(requestMatcher, filters);
    }


    public HttpSecurity requestMatcher(RequestMatcher requestMatcher) {
        this.requestMatcher = requestMatcher;
        return this;
    }

    /**
     * 允许限制访问基于{@link HttpServletRequest}使用
     * 可看springSecurity源码的示例配置代码
     * @return
     * @throws Exception
     */
    public ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry authorizeRequests()
            throws Exception {
        ApplicationContext context = getContext();
        return getOrApply(new ExpressionUrlAuthorizationConfigurer<HttpSecurity>(context))
                .getRegistry();
    }

    private ApplicationContext getContext() {
        return getSharedObject(ApplicationContext.class);
    }

    public SessionManagementConfigurer<HttpSecurity> sessionManagement() throws Exception {
        return getOrApply(new SessionManagementConfigurer<HttpSecurity>());
    }

    /**
     * 设置在{@link SecurityContextHolder}与{@link HttpServletRequest} 之间的{@link SecurityContext}管理
     * 在用 {@link WebSecurityConfigurerAdapter}时候自动应用
     * @return
     * @throws Exception
     */
    public SecurityContextConfigurer<HttpSecurity> securityContext() throws Exception {
        return getOrApply(new SecurityContextConfigurer<HttpSecurity>());
    }



    /**
     *  如果{@link SecurityConfigurer}已经被指定，否则获取新的{@link SecurityConfigurerAdapter}
     * @param configurer  如果没有找到{@link SecurityConfigurer}类，则用参数的{@link SecurityConfigurer}
     * @return
     * @throws Exception
     */
    private <C extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity>> C getOrApply(
            C configurer) throws Exception {
        C existingConfig = (C) getConfigurer(configurer.getClass());
        if (existingConfig != null) {
            return existingConfig;
        }
        return apply(configurer);
    }

    public HttpSecurity addFilter(Filter filter) {
        Class<? extends Filter> filterClass = filter.getClass();
        this.filters.add(filter);
        return this;
    }
}
