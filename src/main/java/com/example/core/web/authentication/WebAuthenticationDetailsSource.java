package com.example.core.web.authentication;

import com.example.core.authentication.AuthenticationDetailsSource;

import javax.servlet.http.HttpServletRequest;

public class WebAuthenticationDetailsSource implements AuthenticationDetailsSource<HttpServletRequest, WebAuthenticationDetails> {
    public WebAuthenticationDetails buildDetails(HttpServletRequest context) {
        return new WebAuthenticationDetails(context);
    }
}
