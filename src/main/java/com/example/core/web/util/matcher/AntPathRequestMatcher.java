package com.example.core.web.util.matcher;

import com.example.core.util.matcher.RequestMatcher;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpMethod;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.Map;

/**
 * Matcher将预定义的ant样式模式与URL({@code servlepath + pathInfo})的一个{@code HttpServletRequest}比较
 * 查询字符串URL被忽略，匹配不区分大小写或区分大小写取决于传入构造函数的参数
 *
 * 使用模式值{@code /**}或{@code **}作为通用匹配，将匹配任何请求
 * 以{@code /**}结尾的模式(没有其他模式)使用子字符串匹配和mdash优化
 *
 * {@code /aaa/**}将匹配{@code /aaa}、{@code /aaa/}和任何子目录，例如{ @code / aaa / bbb / ccc }
 */
public final class AntPathRequestMatcher implements RequestMatcher,RequestVariablesExtractor {

    private static final Log logger = LogFactory.getLog(AntPathRequestMatcher.class);
    private static final String MATCH_ALL = "/**";

    private final Matcher matcher;
    private final String pattern;
    private final HttpMethod httpMethod;
    private final boolean caseSensitive;

    public AntPathRequestMatcher(String pattern, String httpMethod) {
        this(pattern, httpMethod, true);
    }

    public AntPathRequestMatcher(String pattern, String httpMethod,
                                 boolean caseSensitive) {
        Assert.hasText(pattern, "Pattern cannot be null or empty");
        this.caseSensitive = caseSensitive;

        if (pattern.equals(MATCH_ALL) || pattern.equals("**")) {
            pattern = MATCH_ALL;
            this.matcher = null;
        }else {
            //如果模式以{@code /**}结尾，并且没有其他通配符或路径变量，然后优化到子路径匹配
            if (pattern.endsWith(MATCH_ALL)
                    && (pattern.indexOf('?') == -1 && pattern.indexOf('{') == -1
                    && pattern.indexOf('}') == -1)
                    && pattern.indexOf("*") == pattern.length() - 2) {
                this.matcher = new SubpathMatcher(
                        pattern.substring(0, pattern.length() - 3), caseSensitive);
            }
            else {
                this.matcher = new SpringAntMatcher(pattern, caseSensitive);
            }
        }
        this.pattern = pattern;
        this.httpMethod = StringUtils.hasText(httpMethod) ? HttpMethod.valueOf(httpMethod)
                : null;
    }

    @Override
    public boolean matches(HttpServletRequest request) {
        if (this.httpMethod != null && StringUtils.hasText(request.getMethod())
                && this.httpMethod != valueOf(request.getMethod())) {
            if (logger.isDebugEnabled()) {
                logger.debug("Request '" + request.getMethod() + " "
                        + getRequestPath(request) + "'" + " doesn't match '"
                        + this.httpMethod + " " + this.pattern);
            }

            return false;
        }

        if (this.pattern.equals(MATCH_ALL)) {
            if (logger.isDebugEnabled()) {
                logger.debug("Request '" + getRequestPath(request)
                        + "' matched by universal pattern '/**'");
            }

            return true;
        }

        String url = getRequestPath(request);

        if (logger.isDebugEnabled()) {
            logger.debug("Checking match of request : '" + url + "'; against '"
                    + this.pattern + "'");
        }

        return this.matcher.matches(url);
    }

    private String getRequestPath(HttpServletRequest request) {
        String url = request.getServletPath();

        if (request.getPathInfo() != null) {
            url += request.getPathInfo();
        }

        return url;
    }

    private static HttpMethod valueOf(String method) {
        try {
            return HttpMethod.valueOf(method);
        }
        catch (IllegalArgumentException e) {
        }

        return null;
    }

    @Override
    public Map<String, String> extractUriTemplateVariables(HttpServletRequest request) {
        if (this.matcher == null || !matches(request)) {
            return Collections.emptyMap();
        }
        String url = getRequestPath(request);
        return this.matcher.extractUriTemplateVariables(url);
    }


    private static interface Matcher {
        boolean matches(String path);

        Map<String, String> extractUriTemplateVariables(String path);
    }

    private static class SpringAntMatcher implements Matcher {
        private final AntPathMatcher antMatcher;

        private final String pattern;

        private SpringAntMatcher(String pattern, boolean caseSensitive) {
            this.pattern = pattern;
            this.antMatcher = createMatcher(caseSensitive);
        }

        @Override
        public boolean matches(String path) {
            return this.antMatcher.match(this.pattern, path);
        }

        @Override
        public Map<String, String> extractUriTemplateVariables(String path) {
            return this.antMatcher.extractUriTemplateVariables(this.pattern, path);
        }

        private static AntPathMatcher createMatcher(boolean caseSensitive) {
            AntPathMatcher matcher = new AntPathMatcher();
            matcher.setTrimTokens(false);
            matcher.setCaseSensitive(caseSensitive);
            return matcher;
        }
    }

    //优化的匹配跟踪通配符
    private static class SubpathMatcher implements Matcher {
        private final String subpath;
        private final int length;
        private final boolean caseSensitive;

        private SubpathMatcher(String subpath, boolean caseSensitive) {
            assert!subpath.contains("*");
            this.subpath = caseSensitive ? subpath : subpath.toLowerCase();
            this.length = subpath.length();
            this.caseSensitive = caseSensitive;
        }

        @Override
        public boolean matches(String path) {
            if (!this.caseSensitive) {
                path = path.toLowerCase();
            }
            return path.startsWith(this.subpath)
                    && (path.length() == this.length || path.charAt(this.length) == '/');
        }

        @Override
        public Map<String, String> extractUriTemplateVariables(String path) {
            return Collections.emptyMap();
        }

    }
}
