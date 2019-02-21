package com.example.core.authentication;

import org.springframework.beans.factory.Aware;
import org.springframework.context.ApplicationEventPublisher;

/**
 * 该接口的实现类希望被ApplicationEventPublisher通知
 */
public interface ApplicationEventPublisherAware extends Aware {

    /**
     * 在填充普通bean属性之后，但在初始化之前，类似InitializingBean的afterPropertiesSet
     *     或自定义init方法的回调，在ApplicationContextAware#setApplicationContext之前调用
     *    --->该对象将使用该事件发布者
     * @param applicationEventPublisher
     */
    void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher);
}
