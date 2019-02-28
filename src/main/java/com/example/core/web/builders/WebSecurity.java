package com.example.core.web.builders;

import com.example.core.config.WebSecurityConfigurerAdapter;
import com.example.core.config.annotation.AbstractConfiguredSecurityBuilder;
import com.example.core.config.annotation.ObjectPostProcessor;
import com.example.core.config.annotation.SecurityBuilder;
import com.example.core.filter.FilterChainProxy;
import com.example.core.web.SecurityFilterChain;
import com.example.core.web.access.intercept.FilterSecurityInterceptor;

import javax.servlet.Filter;
import java.util.ArrayList;
import java.util.List;

public class WebSecurity extends AbstractConfiguredSecurityBuilder<Filter, WebSecurity> implements SecurityBuilder<Filter> {

   private  List<WebSecurityConfigurerAdapter> webSecurityConfigurers;

    private final List<SecurityBuilder<? extends SecurityFilterChain>> securityFilterChainBuilders = new ArrayList<SecurityBuilder<? extends SecurityFilterChain>>();

    private FilterSecurityInterceptor filterSecurityInterceptor;

    private Runnable postBuildAction = new Runnable() {
        public void run() {
        }
    };

    public WebSecurity(ObjectPostProcessor<Object> objectPostProcessor) {
        super(objectPostProcessor);
        System.out.println("初始化WebSecurity");
    }

    public WebSecurity securityInterceptor(FilterSecurityInterceptor securityInterceptor) {
        this.filterSecurityInterceptor = securityInterceptor;
        return this;
    }

    /**
     * 在生成之后立即执行Runnable
     */
    public WebSecurity postBuildAction(Runnable postBuildAction) {
        this.postBuildAction = postBuildAction;
        return this;
    }

    @Override
    protected Filter performBuild() throws Exception {
        int chainSize =  securityFilterChainBuilders.size();
        List<SecurityFilterChain> securityFilterChains = new ArrayList<SecurityFilterChain>(chainSize);

        for (SecurityBuilder<? extends SecurityFilterChain> securityFilterChainBuilder : securityFilterChainBuilders) {
            securityFilterChains.add(securityFilterChainBuilder.build());
        }
        FilterChainProxy filterChainProxy = new FilterChainProxy(securityFilterChains);

        Filter result = filterChainProxy;
        postBuildAction.run();
        return result;
    }


    public WebSecurity addSecurityFilterChainBuilder(
            SecurityBuilder<? extends SecurityFilterChain> securityFilterChainBuilder) {
        this.securityFilterChainBuilders.add(securityFilterChainBuilder);
        return this;
    }
}
