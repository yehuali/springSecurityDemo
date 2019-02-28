package com.example.core.web;

import com.example.core.config.annotation.SecurityBuilder;

import javax.servlet.Filter;

public interface HttpSecurityBuilder<H extends HttpSecurityBuilder<H>> extends SecurityBuilder<DefaultSecurityFilterChain> {

    <C> C getSharedObject(Class<C> sharedType);

    <C> void setSharedObject(Class<C> sharedType, C object);

    H addFilter(Filter filter);
}
