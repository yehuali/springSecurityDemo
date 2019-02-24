package com.example.core.web;

import com.example.core.config.annotation.SecurityBuilder;

public interface HttpSecurityBuilder<H extends HttpSecurityBuilder<H>> extends SecurityBuilder<DefaultSecurityFilterChain> {

}
