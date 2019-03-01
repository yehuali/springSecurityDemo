package com.example.core.web.access.expression;

import com.example.core.authentication.Authentication;
import com.example.core.web.FilterInvocation;
import com.example.core.web.access.AccessDecisionVoter;
import com.example.core.web.access.ConfigAttribute;

import java.util.Collection;

public class WebExpressionVoter implements AccessDecisionVoter<FilterInvocation> {

    private SecurityExpressionHandler<FilterInvocation> expressionHandler = new DefaultWebSecurityExpressionHandler();

    public void setExpressionHandler(
            SecurityExpressionHandler<FilterInvocation> expressionHandler) {
        this.expressionHandler = expressionHandler;
    }

    @Override
    public int vote(Authentication authentication, FilterInvocation object, Collection<ConfigAttribute> attributes) {
        //待完成
//        return 0;
        return -1;
    }


}
