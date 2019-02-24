package com.example.core.web.builders;

import com.example.core.authentication.AuthenticationManagerBuilder;
import com.example.core.config.annotation.AbstractConfiguredSecurityBuilder;
import com.example.core.config.annotation.ObjectPostProcessor;
import com.example.core.config.annotation.SecurityBuilder;
import com.example.core.util.matcher.AnyRequestMatcher;
import com.example.core.util.matcher.RequestMatcher;
import com.example.core.web.DefaultSecurityFilterChain;
import com.example.core.web.HttpSecurityBuilder;

import javax.servlet.Filter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

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


    }

    @Override
    protected DefaultSecurityFilterChain performBuild() throws Exception {
        Collections.sort(filters, comparator);
        return new DefaultSecurityFilterChain(requestMatcher, filters);
    }

    public HttpSecurity addFilter(Filter filter) {
        Class<? extends Filter> filterClass = filter.getClass();
        this.filters.add(filter);
        return this;
    }
}
