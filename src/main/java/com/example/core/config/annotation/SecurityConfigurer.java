package com.example.core.config.annotation;

/**
 * 配置{@link SecurityBuilder}
 * 调用步骤：{@link #init(SecurityBuilder)}  ---> {@link #configure(SecurityBuilder)}
 * @param <O>
 * @param <B>
 */
public interface SecurityConfigurer<O, B extends SecurityBuilder<O>>  {
    void init(B builder) throws Exception;

    void configure(B builder) throws Exception;
}
