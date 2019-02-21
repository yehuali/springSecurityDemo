package com.example.core.config;


import com.example.core.config.annotation.ObjectPostProcessor;
import com.example.core.config.annotation.configuration.AutowireBeanFactoryObjectPostProcessor;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ObjectPostProcessorConfiguration {

    //这里的参数beanFactory会被自动注入成当前Spring bean容器
    @Bean
    public ObjectPostProcessor<Object> objectPostProcessor(
            AutowireCapableBeanFactory beanFactory) {
        return new AutowireBeanFactoryObjectPostProcessor(beanFactory);
    }
}
