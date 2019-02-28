package com.example.core.web.config;

import com.example.core.util.matcher.RequestMatcher;
import com.example.core.web.access.ConfigAttribute;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * 注册{@link RequestMatcher}的基类
 * 例如，它可能允许指定需要某种级别的授权的{@link RequestMatcher}
 * @param <C>
 */
public abstract class AbstractConfigAttributeRequestMatcherRegistry<C> extends
        AbstractRequestMatcherRegistry<C>{

    private List<UrlMapping> urlMappings = new ArrayList<UrlMapping>();
    private List<RequestMatcher> unmappedMatchers;

    /**
     * 创建映射从{@link RequestMatcher}到{@link Collection}的{@link ConfigAttribute}实例
     * @return  返回值不能为空
     */
    final LinkedHashMap<RequestMatcher, Collection<ConfigAttribute>> createRequestMap() {
        if (unmappedMatchers != null) {
            throw new IllegalStateException(
                    "An incomplete mapping was found for "
                            + unmappedMatchers
                            + ". Try completing it with something like requestUrls().<something>.hasRole('USER')");
        }
        LinkedHashMap<RequestMatcher, Collection<ConfigAttribute>> requestMap = new LinkedHashMap<RequestMatcher, Collection<ConfigAttribute>>();
        for (UrlMapping mapping : getUrlMappings()) {
            RequestMatcher matcher = mapping.getRequestMatcher();
            Collection<ConfigAttribute> configAttrs = mapping.getConfigAttrs();
            requestMap.put(matcher, configAttrs);
        }
        return requestMap;
    }

    /**
     * 获取由子类在{@link #chainRequestMatchers(java.util.List)}添加的{@link UrlMapping}，可能为空
     * @return
     */
    final List<UrlMapping> getUrlMappings() {
        return urlMappings;
    }

    final void addMapping(UrlMapping urlMapping) {
        this.unmappedMatchers = null;
        this.urlMappings.add(urlMapping);
    }

    public final C chainRequestMatchers(List<RequestMatcher> requestMatchers) {
        this.unmappedMatchers = requestMatchers;
        return chainRequestMatchersInternal(requestMatchers);
    }

    protected abstract C chainRequestMatchersInternal(List<RequestMatcher> requestMatchers);

    static final class UrlMapping {
        private RequestMatcher requestMatcher;
        private Collection<ConfigAttribute> configAttrs;

        UrlMapping(RequestMatcher requestMatcher, Collection<ConfigAttribute> configAttrs) {
            this.requestMatcher = requestMatcher;
            this.configAttrs = configAttrs;
        }

        public RequestMatcher getRequestMatcher() {
            return requestMatcher;
        }

        public Collection<ConfigAttribute> getConfigAttrs() {
            return configAttrs;
        }
    }



}
