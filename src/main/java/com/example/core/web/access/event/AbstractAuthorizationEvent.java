package com.example.core.web.access.event;

import org.springframework.context.ApplicationEvent;

public abstract class AbstractAuthorizationEvent extends ApplicationEvent {
    /**
     * Create a new ApplicationEvent.
     *
     * @param source the object on which the event initially occurred (never {@code null})
     */
    public AbstractAuthorizationEvent(Object source) {
        super(source);
    }
}
