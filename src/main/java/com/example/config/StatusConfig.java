package com.example.config;

import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.web.servlet.ErrorPage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

@Configuration
public class StatusConfig {
    /**
     * https://my.oschina.net/dabird/blog/593643
     * Spring Boot默认使用嵌入式Tomcat，默认没有页面来处理401等常见错误
     * 后台返回401未认证，浏览器有默认弹框
     * @return
     */
    @Bean
    public EmbeddedServletContainerCustomizer containerCustomizer() {

        return (container -> {
            ErrorPage error401Page = new ErrorPage(HttpStatus.UNAUTHORIZED, "/401.html");
//            ErrorPage error404Page = new ErrorPage(HttpStatus.NOT_FOUND, "/404.html");
//            ErrorPage error500Page = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/500.html");

//            container.addErrorPages(error401Page, error404Page, error500Page);
            container.addErrorPages(error401Page);
        });
    }
}
