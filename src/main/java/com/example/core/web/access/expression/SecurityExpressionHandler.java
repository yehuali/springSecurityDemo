package com.example.core.web.access.expression;

import org.springframework.aop.framework.AopInfrastructureBean;
import org.springframework.expression.ExpressionParser;

public interface SecurityExpressionHandler<T> extends AopInfrastructureBean {

    ExpressionParser getExpressionParser();
}
