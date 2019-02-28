package com.example.core.web.access.expression;

import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;

public abstract class AbstractSecurityExpressionHandler<T> implements SecurityExpressionHandler<T>{

    private ExpressionParser expressionParser = new SpelExpressionParser();

    public final ExpressionParser getExpressionParser() {
        return expressionParser;
    }
}
