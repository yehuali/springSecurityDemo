package com.example.core.web.access.intercept;

import com.example.core.util.matcher.RequestMatcher;
import com.example.core.web.access.ConfigAttribute;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public class DefaultFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    private final Map<RequestMatcher, Collection<ConfigAttribute>> requestMap;
    public DefaultFilterInvocationSecurityMetadataSource(
            LinkedHashMap<RequestMatcher, Collection<ConfigAttribute>> requestMap) {

        this.requestMap = requestMap;
    }

    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        return null;
    }
}
