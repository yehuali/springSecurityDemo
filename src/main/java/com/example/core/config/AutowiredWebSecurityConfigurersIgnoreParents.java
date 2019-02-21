package com.example.core.config;


import com.example.core.config.annotation.SecurityConfigurer;
import com.example.core.web.builders.WebSecurity;
import com.example.core.web.config.WebSecurityConfigurer;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.util.Assert;

import javax.servlet.Filter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

final class AutowiredWebSecurityConfigurersIgnoreParents {

    private final ConfigurableListableBeanFactory beanFactory;

    public AutowiredWebSecurityConfigurersIgnoreParents(
            ConfigurableListableBeanFactory beanFactory) {
        Assert.notNull(beanFactory, "beanFactory cannot be null");
        this.beanFactory = beanFactory;
    }

    public List<SecurityConfigurer<Filter, WebSecurity>> getWebSecurityConfigurers() {
        List<SecurityConfigurer< Filter,WebSecurity>> webSecurityConfigurers = new ArrayList<SecurityConfigurer<Filter, WebSecurity>>();
        Map<String, WebSecurityConfigurer> beansOfType = beanFactory
                .getBeansOfType(WebSecurityConfigurer.class);
        for (Map.Entry<String, WebSecurityConfigurer> entry : beansOfType.entrySet()) {
            webSecurityConfigurers.add(entry.getValue());
        }
        return  webSecurityConfigurers;
    }
}
