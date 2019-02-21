package com.example.core.config;

import com.example.core.authentication.DefaultAuthenticationEventPublisher;
import com.example.core.config.annotation.ObjectPostProcessor;
import com.example.core.web.builders.HttpSecurity;
import com.example.core.web.builders.WebSecurity;
import com.example.core.web.config.WebSecurityConfigurer;
import org.springframework.beans.factory.annotation.Autowired;


public abstract class WebSecurityConfigurerAdapter implements WebSecurityConfigurer<WebSecurity> {
    private HttpSecurity http;

    private ObjectPostProcessor<Object> objectPostProcessor = new ObjectPostProcessor<Object>() {
        public <T> T postProcess(T object) {
            throw new IllegalStateException(
                    ObjectPostProcessor.class.getName()
                            + " is a required bean. Ensure you have used @EnableWebSecurity and @Configuration");
        }
    };

    @Override
    public void init(final WebSecurity web) {
        //获取http实例
        final HttpSecurity http = getHttp();
        //添加过滤器链
        web.addSecurityFilterChainBuilder(http);
    }


    protected final HttpSecurity getHttp(){
        if (http != null) {
            return http;
        }
        //将DefaultAuthenticationEventPublisher注入到ApplicationEventPublisher属性中
        DefaultAuthenticationEventPublisher eventPublisher = objectPostProcessor.postProcess( new DefaultAuthenticationEventPublisher());

        http = new HttpSecurity();
        configure(http);
        return http;
    }


    @Autowired
    public void setObjectPostProcessor(ObjectPostProcessor<Object> objectPostProcessor) {
        this.objectPostProcessor = objectPostProcessor;
    }

    protected void configure(HttpSecurity http){

    }

    public void configure(WebSecurity web) throws Exception {
    }
}
