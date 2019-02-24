package com.example.core.authentication.config;

import com.example.core.authentication.Authentication;
import com.example.core.authentication.AuthenticationException;
import com.example.core.authentication.AuthenticationManager;
import com.example.core.authentication.AuthenticationManagerBuilder;
import com.example.core.config.ObjectPostProcessorConfiguration;
import com.example.core.config.annotation.ObjectPostProcessor;
import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.aop.target.LazyInitTargetSource;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.util.Assert;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

@Configuration
@Import(ObjectPostProcessorConfiguration.class)
public class AuthenticationConfiguration {

    private AtomicBoolean buildingAuthenticationManager = new AtomicBoolean();

    private ApplicationContext applicationContext;
    private boolean authenticationManagerInitialized;
    private AuthenticationManager authenticationManager;
    private ObjectPostProcessor<Object> objectPostProcessor;
    private List<GlobalAuthenticationConfigurerAdapter> globalAuthConfigurers = Collections
            .emptyList();


    public AuthenticationManager getAuthenticationManager() throws Exception {
        if (this.authenticationManagerInitialized) {
            return this.authenticationManager;
        }
        AuthenticationManagerBuilder authBuilder = authenticationManagerBuilder(
                this.objectPostProcessor);
        if (this.buildingAuthenticationManager.getAndSet(true)) {
            return new AuthenticationManagerDelegator(authBuilder);
        }

        for (GlobalAuthenticationConfigurerAdapter config : globalAuthConfigurers) {
            authBuilder.apply(config);
        }

        authenticationManager = authBuilder.build();

        if (authenticationManager == null) {
            authenticationManager = getAuthenticationManagerBean();
        }

        this.authenticationManagerInitialized = true;
        return authenticationManager;
    }

    private AuthenticationManager getAuthenticationManagerBean() {
        return lazyBean(AuthenticationManager.class);
    }

    private <T> T lazyBean(Class<T> interfaceName) {
        LazyInitTargetSource lazyTargetSource = new LazyInitTargetSource();
        String[] beanNamesForType = BeanFactoryUtils.beanNamesForTypeIncludingAncestors(
                applicationContext, interfaceName);
        if (beanNamesForType.length == 0) {
            return null;
        }
        Assert.isTrue(beanNamesForType.length == 1,
                "Expecting to only find a single bean for type " + interfaceName
                        + ", but found " + Arrays.asList(beanNamesForType));
        lazyTargetSource.setTargetBeanName(beanNamesForType[0]);
        lazyTargetSource.setBeanFactory(applicationContext);
        ProxyFactoryBean proxyFactory = new ProxyFactoryBean();
        proxyFactory = objectPostProcessor.postProcess(proxyFactory);
        proxyFactory.setTargetSource(lazyTargetSource);
        return (T) proxyFactory.getObject();
    }


    /**
     * 防止在初始化AuthenticationManagerDelegator的事件中出现无限递归
     */
    static final class AuthenticationManagerDelegator implements AuthenticationManager {
        private AuthenticationManagerBuilder delegateBuilder;
        private AuthenticationManager delegate;
        private final Object delegateMonitor = new Object();

        AuthenticationManagerDelegator(AuthenticationManagerBuilder delegateBuilder) {
            Assert.notNull(delegateBuilder, "delegateBuilder cannot be null");
            this.delegateBuilder = delegateBuilder;
        }
        @Override
        public Authentication authenticate(Authentication authentication)
                throws AuthenticationException {
            if (this.delegate != null) {
                return this.delegate.authenticate(authentication);
            }

            synchronized (this.delegateMonitor) {
                if (this.delegate == null) {
                    this.delegate = this.delegateBuilder.getObject();
                    this.delegateBuilder = null;
                }
            }

            return this.delegate.authenticate(authentication);
        }

        @Override
        public String toString() {
            return "AuthenticationManagerDelegator [delegate=" + this.delegate + "]";
        }

    }

    @Bean
    public AuthenticationManagerBuilder authenticationManagerBuilder(
            ObjectPostProcessor<Object> objectPostProcessor) {
        return new AuthenticationManagerBuilder(objectPostProcessor);
    }
}
