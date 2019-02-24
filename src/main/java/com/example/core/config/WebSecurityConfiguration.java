package com.example.core.config;

import com.example.core.config.annotation.ObjectPostProcessor;
import com.example.core.config.annotation.SecurityConfigurer;
import com.example.core.web.builders.WebSecurity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.List;

@Configuration
public class WebSecurityConfiguration {
    private WebSecurity webSecurity;

    @Autowired
    public void setFilterChainProxySecurityConfigurer(ObjectPostProcessor<Object> objectPostProcessor,
            @Value("#{@autowiredWebSecurityConfigurersIgnoreParents.getWebSecurityConfigurers()}") List<SecurityConfigurer<Filter, WebSecurity>> webSecurityConfigurers)
            throws Exception{
        System.out.println("webSecurityConfigurers的大小："+webSecurityConfigurers.size());
        webSecurity = objectPostProcessor.postProcess(new WebSecurity(objectPostProcessor));
        for(SecurityConfigurer<Filter, WebSecurity> webSecurityConfigurer : webSecurityConfigurers){
            webSecurity.apply(webSecurityConfigurer);
        }
    }

    @Bean(name = "springSecurityFilterChain")
    public Filter springSecurityFilterChain() throws Exception {
        return webSecurity.build();
    }

    @Bean
    public AutowiredWebSecurityConfigurersIgnoreParents autowiredWebSecurityConfigurersIgnoreParents(ConfigurableListableBeanFactory beanFactory) {
        return new AutowiredWebSecurityConfigurersIgnoreParents(beanFactory);
    }

}
