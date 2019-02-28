package com.example.core.web.config;

import com.example.core.util.matcher.AnyRequestMatcher;
import com.example.core.util.matcher.RequestMatcher;
import com.example.core.web.util.matcher.AntPathRequestMatcher;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpMethod;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class AbstractRequestMatcherRegistry<C> {

    private static final RequestMatcher ANY_REQUEST = AnyRequestMatcher.INSTANCE;

    private ApplicationContext context;

    protected final void setApplicationContext(ApplicationContext context) {
        this.context = context;
    }


    public C antMatchers(HttpMethod method) {
        return antMatchers(method, new String[] { "/**" });
    }

    public C antMatchers(HttpMethod method, String... antPatterns) {
        return chainRequestMatchers(RequestMatchers.antMatchers(method, antPatterns));
    }

    public C anyRequest() {
        return requestMatchers(ANY_REQUEST);
    }

    public C requestMatchers(RequestMatcher... requestMatchers) {
        return chainRequestMatchers(Arrays.asList(requestMatchers));
    }

    /**
     * 子类应该实现这个方法来返回链接到的对象去创建{@link RequestMatcher}实例
     * @param requestMatchers {@link RequestMatcher}实例被创建
     * @return 允许关联的子类的链接对象指向{@link RequestMatcher}
     */
    protected abstract C chainRequestMatchers(List<RequestMatcher> requestMatchers);


    /**
     * 用于创建{@link RequestMatcher}实例的实用程序
     */
    private static final class RequestMatchers {

        /**
         * 创建{@link AntPathRequestMatcher}实例列表
         * @param httpMethod
         * @param antPatterns
         * @return
         */
        public static List<RequestMatcher> antMatchers(HttpMethod httpMethod,
                                                       String... antPatterns) {
            String method = httpMethod == null ? null : httpMethod.toString();
            List<RequestMatcher> matchers = new ArrayList<RequestMatcher>();
            for (String pattern : antPatterns) {
                matchers.add(new AntPathRequestMatcher(pattern, method));
            }
            return matchers;
        }
    }

}
