package com.example.core.config.annotation;

/**
 * 用于初始化对象，通常用于调用{@link Aware}的方法，{@link InitializingBean#afterPropertiesSet()}
 * 并确保 {@link DisposableBean#destroy()}已被调用
 * @param <T>
 */
public interface ObjectPostProcessor<T> {

    <O extends T> O postProcess(O object);
}
