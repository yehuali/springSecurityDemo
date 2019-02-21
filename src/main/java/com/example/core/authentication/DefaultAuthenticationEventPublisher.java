package com.example.core.authentication;

import com.example.core.authentication.evnt.AbstractAuthenticationEvent;
import com.example.core.authentication.evnt.AbstractAuthenticationFailureEvent;
import com.example.core.authentication.evnt.AuthenticationFailureBadCredentialsEvent;
import org.springframework.context.ApplicationEventPublisher;

import java.lang.reflect.Constructor;
import java.util.HashMap;

/**
 * 事件发布器，内部实现就是spring的ApplicationEventPublisher
 * 用于springsecurity各种权限时间的交互
 *   --->登陆失败,发布一个事件，然后通知其它组件做出相应的响应
 *  2.如果配置为bean，则它将自动获取ApplicationEventPublisher，否则构造函数将发布者作为参数使用
 * 3.异常映射系统可以设置additionalExceptionMappings为properties调整
 *    --->键表示异常，值表示事件(AbstractAuthenticationFailureEvent的子类) ，并提供构造函数
 */
public class DefaultAuthenticationEventPublisher implements AuthenticationEventPublisher,ApplicationEventPublisherAware {

    private ApplicationEventPublisher applicationEventPublisher;

    private final HashMap<String, Constructor<? extends AbstractAuthenticationEvent>> exceptionMappings = new HashMap<String, Constructor<? extends AbstractAuthenticationEvent>>();

    public DefaultAuthenticationEventPublisher() {
        this(null);
    }

    public DefaultAuthenticationEventPublisher(
            ApplicationEventPublisher applicationEventPublisher) {

        this.applicationEventPublisher = applicationEventPublisher;

        addMapping(BadCredentialsException.class.getName(),
                AuthenticationFailureBadCredentialsEvent.class);

    }

    private void addMapping(String exceptionClass,
                            Class<? extends AbstractAuthenticationFailureEvent> eventClass) {

    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public void publishAuthenticationSuccess(Authentication authentication) {

    }

    @Override
    public void publishAuthenticationFailure(AuthenticationException exception, Authentication authentication) {

    }
}
