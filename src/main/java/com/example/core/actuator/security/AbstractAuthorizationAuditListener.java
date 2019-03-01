package com.example.core.actuator.security;

import com.example.core.web.access.event.AbstractAuthorizationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.context.ApplicationListener;

/**
 * 相关源代码分析参考：https://blog.csdn.net/qq_26000415/article/details/79138270
 */
public class AbstractAuthorizationAuditListener implements
        ApplicationListener<AbstractAuthorizationEvent>, ApplicationEventPublisherAware {

    private ApplicationEventPublisher publisher;

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    protected ApplicationEventPublisher getPublisher() {
        return this.publisher;
    }

    @Override
    public void onApplicationEvent(AbstractAuthorizationEvent event) {
//        if (getPublisher() != null) {
//            getPublisher().publishEvent(new AuditApplicationEvent(event));
//        }
    }
}
