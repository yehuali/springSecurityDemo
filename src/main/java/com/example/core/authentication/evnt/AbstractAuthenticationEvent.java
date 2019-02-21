package com.example.core.authentication.evnt;

import com.example.core.authentication.Authentication;
import org.springframework.context.ApplicationEvent;

public abstract class AbstractAuthenticationEvent extends ApplicationEvent {

    public AbstractAuthenticationEvent(Authentication authentication) {
        super(authentication);
    }

    /**
     * 获取造成事件的请求
     * @return 验证请求
     */
    public Authentication getAuthentication() {
        return (Authentication) super.getSource();
    }
}
