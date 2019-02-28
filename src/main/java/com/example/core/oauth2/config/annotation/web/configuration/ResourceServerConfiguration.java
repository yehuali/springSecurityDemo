package com.example.core.oauth2.config.annotation.web.configuration;

import com.example.core.config.WebSecurityConfigurerAdapter;
import com.example.core.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import com.example.core.web.builders.HttpSecurity;
import com.example.core.web.builders.WebSecurity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

import java.util.Collections;
import java.util.List;

@Configuration
public class ResourceServerConfiguration extends WebSecurityConfigurerAdapter implements Ordered {

    private int order = 3;

    private List<ResourceServerConfigurer> configurers = Collections.emptyList();

    /**
     * @see WebSecurityConfigurerAdapter#init(WebSecurity)
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        ResourceServerSecurityConfigurer resources = new ResourceServerSecurityConfigurer();
        http.apply(resources);

        for (ResourceServerConfigurer configurer : configurers) {
            configurer.configure(http);
        }

    }

    @Autowired(required = false)
    public void setConfigurers(List<ResourceServerConfigurer> configurers) {
        this.configurers = configurers;
    }

    @Override
    public int getOrder() {
        return order;
    }
}
