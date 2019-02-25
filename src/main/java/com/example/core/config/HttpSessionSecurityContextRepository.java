package com.example.core.config;

import com.example.core.web.context.HttpRequestResponseHolder;
import com.example.core.web.context.SecurityContext;
import com.example.core.web.context.SecurityContextRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HttpSessionSecurityContextRepository implements SecurityContextRepository {
    @Override
    public SecurityContext loadContext(HttpRequestResponseHolder requestResponseHolder) {
        return null;
    }

    @Override
    public void saveContext(SecurityContext context, HttpServletRequest request, HttpServletResponse response) {

    }

    @Override
    public boolean containsContext(HttpServletRequest request) {
        return false;
    }
}
