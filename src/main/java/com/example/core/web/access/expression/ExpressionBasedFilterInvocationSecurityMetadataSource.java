package com.example.core.web.access.expression;

import com.example.core.util.matcher.RequestMatcher;
import com.example.core.web.FilterInvocation;
import com.example.core.web.access.ConfigAttribute;
import com.example.core.web.access.intercept.DefaultFilterInvocationSecurityMetadataSource;
import org.springframework.expression.ExpressionParser;
import org.springframework.util.Assert;

import java.util.Collection;
import java.util.LinkedHashMap;

public class ExpressionBasedFilterInvocationSecurityMetadataSource extends DefaultFilterInvocationSecurityMetadataSource {

    public ExpressionBasedFilterInvocationSecurityMetadataSource(
            LinkedHashMap<RequestMatcher, Collection<ConfigAttribute>> requestMap,
            SecurityExpressionHandler<FilterInvocation> expressionHandler) {
        super(processMap(requestMap, expressionHandler.getExpressionParser()));
        Assert.notNull(expressionHandler,
                "A non-null SecurityExpressionHandler is required");
    }

    private static LinkedHashMap<RequestMatcher, Collection<ConfigAttribute>> processMap(
            LinkedHashMap<RequestMatcher, Collection<ConfigAttribute>> requestMap,
            ExpressionParser parser) {
        return null;
    }
}
