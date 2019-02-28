package com.example.core.web.config;

import com.example.core.util.matcher.RequestMatcher;
import com.example.core.web.FilterInvocation;
import com.example.core.web.HttpSecurityBuilder;
import com.example.core.web.access.AccessDecisionVoter;
import com.example.core.web.access.ConfigAttribute;
import com.example.core.web.access.SecurityConfig;
import com.example.core.web.access.expression.DefaultWebSecurityExpressionHandler;
import com.example.core.web.access.expression.ExpressionBasedFilterInvocationSecurityMetadataSource;
import com.example.core.web.access.expression.SecurityExpressionHandler;
import com.example.core.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.context.ApplicationContext;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * 添加SpEl表达式的授权的URL到应用中
 * 至少一个@RequestMapping需要映射到{@link ConfigAttribute}为{@link SecurityContextConfigurer}有意义
 *
 * 填充FilterSecurityInterceptor过滤器
 * @param <H>
 */
public class ExpressionUrlAuthorizationConfigurer<H extends HttpSecurityBuilder<H>>
        extends
        AbstractInterceptUrlConfigurer<ExpressionUrlAuthorizationConfigurer<H>, H>{

    static final String permitAll = "permitAll";
    private static final String authenticated = "authenticated";
    private final ExpressionInterceptUrlRegistry REGISTRY;
    private SecurityExpressionHandler<FilterInvocation> expressionHandler;

    public ExpressionUrlAuthorizationConfigurer(ApplicationContext context) {
        this.REGISTRY = new ExpressionInterceptUrlRegistry(context);
    }

    public ExpressionInterceptUrlRegistry getRegistry() {
        return REGISTRY;
    }

    @Override
    FilterInvocationSecurityMetadataSource createMetadataSource(H http) {
        LinkedHashMap<RequestMatcher, Collection<ConfigAttribute>> requestMap = REGISTRY
                .createRequestMap();
        if (requestMap.isEmpty()) {
            throw new IllegalStateException(
                    "At least one mapping is required (i.e. authorizeRequests().anyRequest().authenticated())");
        }
        return new ExpressionBasedFilterInvocationSecurityMetadataSource(requestMap,
                getExpressionHandler(http));
    }




    private SecurityExpressionHandler<FilterInvocation> getExpressionHandler(H http) {
        /**
         * expressionHandler 参考OAuth2WebSecurityExpressionHandler
         */
        if(expressionHandler == null){
            DefaultWebSecurityExpressionHandler defaultHandler = new DefaultWebSecurityExpressionHandler();
            expressionHandler = defaultHandler;
        }
        return expressionHandler;
    }


    @Override
    List<AccessDecisionVoter<? extends Object>> getDecisionVoters(H http) {
//        List<AccessDecisionVoter<? extends Object>> decisionVoters = new ArrayList<AccessDecisionVoter<? extends Object>>();
//        WebExpressionVoter expressionVoter = new WebExpressionVoter();
//        expressionVoter.setExpressionHandler(getExpressionHandler(http));
//        decisionVoters.add(expressionVoter);
//        return decisionVoters;
        return null;
    }

    public class ExpressionInterceptUrlRegistry
            extends
            ExpressionUrlAuthorizationConfigurer<H>.AbstractInterceptUrlRegistry<ExpressionInterceptUrlRegistry, AuthorizedUrl> {

        private ExpressionInterceptUrlRegistry(ApplicationContext context) {
            setApplicationContext(context);
        }

        @Override
        protected final AuthorizedUrl chainRequestMatchersInternal(
                List<RequestMatcher> requestMatchers) {
            return new AuthorizedUrl(requestMatchers);
        }

        public ExpressionInterceptUrlRegistry expressionHandler(
                SecurityExpressionHandler<FilterInvocation> expressionHandler) {
            ExpressionUrlAuthorizationConfigurer.this.expressionHandler = expressionHandler;
            return this;
        }

    }

    public class AuthorizedUrl {
        private List<? extends RequestMatcher> requestMatchers;
        private boolean not;
        private AuthorizedUrl(List<? extends RequestMatcher> requestMatchers) {
            this.requestMatchers = requestMatchers;
        }

        protected List<? extends RequestMatcher> getMatchers() {
            return this.requestMatchers;
        }

        public ExpressionInterceptUrlRegistry permitAll() {
            return access(permitAll);
        }

        public ExpressionInterceptUrlRegistry authenticated() {
            return access(authenticated);
        }


        public ExpressionInterceptUrlRegistry access(String attribute) {
            if (not) {
                attribute = "!" + attribute;
            }
            interceptUrl(requestMatchers, SecurityConfig.createList(attribute));
            return ExpressionUrlAuthorizationConfigurer.this.REGISTRY;
        }

        private void interceptUrl(Iterable<? extends RequestMatcher> requestMatchers,
                                  Collection<ConfigAttribute> configAttributes) {
            for (RequestMatcher requestMatcher : requestMatchers) {
                REGISTRY.addMapping(new AbstractConfigAttributeRequestMatcherRegistry.UrlMapping(
                        requestMatcher, configAttributes));
            }
        }
    }
}
