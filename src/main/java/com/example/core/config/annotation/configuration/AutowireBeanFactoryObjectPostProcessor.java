package com.example.core.config.annotation.configuration;

import com.example.core.config.annotation.ObjectPostProcessor;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.util.Assert;

/**
 * https://blog.csdn.net/andy_zhang2007/article/details/84970156
 */
public final class AutowireBeanFactoryObjectPostProcessor implements ObjectPostProcessor<Object> {

    private final AutowireCapableBeanFactory autowireBeanFactory;

    public AutowireBeanFactoryObjectPostProcessor(
            AutowireCapableBeanFactory autowireBeanFactory) {
        Assert.notNull(autowireBeanFactory, "autowireBeanFactory cannot be null");
        this.autowireBeanFactory = autowireBeanFactory;
    }

    @Override
    public <T> T postProcess(T object) {
        if (object == null) {
            return null;
        }
        T result = null;
        try {
            result = (T) this.autowireBeanFactory.initializeBean(object,
                    object.toString());
        }
        catch (RuntimeException e) {
            Class<?> type = object.getClass();
            throw new RuntimeException(
                    "Could not postProcess " + object + " of type " + type, e);
        }
        //// 使用容器autowireBeanFactory标准依赖注入方法autowireBean()处理 object对象的依赖注入
        this.autowireBeanFactory.autowireBean(object);
        return result;
    }
}
