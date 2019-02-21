package com.example.core.config.annotation;

/**
 * 构建对象
 * @param <O>
 */
public interface SecurityBuilder<O> {
        O build() throws Exception;
}
