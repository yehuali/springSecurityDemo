package com.example.core.web.builders;

import com.example.core.config.WebSecurityConfigurerAdapter;
import com.example.core.config.annotation.AbstractConfiguredSecurityBuilder;
import com.example.core.config.annotation.SecurityBuilder;

import javax.servlet.Filter;
import java.util.ArrayList;
import java.util.List;

public class WebSecurity extends AbstractConfiguredSecurityBuilder<Filter, WebSecurity> implements SecurityBuilder<Filter> {

   private  List<WebSecurityConfigurerAdapter> webSecurityConfigurers;

    private final List<HttpSecurity> securityFilterChainBuilders = new ArrayList<HttpSecurity>();

    public WebSecurity() {
        System.out.println("初始化WebSecurity");
    }


//    @Override
//    public Filter build() throws Exception {
//        init();
//
//        int chainSize = securityFilterChainBuilders.size();
//        List<HttpSecurity> securityFilterChains = new ArrayList<HttpSecurity>(
//                chainSize);
////        for(){
////
////        }
//        //创建FilterChainProxy 对象
//        FilterChainProxy filterChainProxy = new FilterChainProxy(securityFilterChains);
//        return filterChainProxy;
//    }



    public WebSecurity addSecurityFilterChainBuilder(
            HttpSecurity securityFilterChainBuilder) {
        this.securityFilterChainBuilders.add(securityFilterChainBuilder);
        return this;
    }
}
