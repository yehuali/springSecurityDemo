package com.example.core.config;


import com.example.core.web.HelloWorldServlet;
import org.springframework.boot.web.servlet.DelegatingFilterProxyRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SecurityFilterAutoConfiguration {
    private static final String DEFAULT_FILTER_NAME = "springSecurityFilterChain";

    /**
     * 注册DelegatingFilterProxy
     * @return
     */
    @Bean
    public DelegatingFilterProxyRegistrationBean securityFilterChainRegistration() {
        DelegatingFilterProxyRegistrationBean registration = new DelegatingFilterProxyRegistrationBean(
                DEFAULT_FILTER_NAME);
        registration.setOrder(0);
        registration.setDispatcherTypes(null);
        return registration;
    }

    @Bean
    public ServletRegistrationBean helloWorldServlet() {
        ServletRegistrationBean helloWorldServlet = new ServletRegistrationBean();
        helloWorldServlet.addUrlMappings("/hello");
        helloWorldServlet.setServlet(new HelloWorldServlet());
        return helloWorldServlet;
    }
}
