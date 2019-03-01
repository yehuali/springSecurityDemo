package com.example.core.actuator.security;

import com.example.core.web.access.event.AbstractAuthorizationEvent;

public class AuthorizationAuditListener extends AbstractAuthorizationAuditListener {
    @Override
    public void onApplicationEvent(AbstractAuthorizationEvent event) {
        System.out.println("监听到事件");
    }
}
