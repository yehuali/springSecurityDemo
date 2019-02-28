package com.example.core.web.context;

import com.example.core.context.SecurityContextHolderStrategy;
import org.springframework.util.StringUtils;

/**
 * 用于存储安全上下文（SecurityContext)信息
 * 将SecurityContext与当前线程绑定
 * 提供一系列静态方法，方法委托给SecurityContextHolderStrategy的实例
 *  --->目的：提供简便的方法针对给定的JVM使用相应的策略
 *  这是一个JVM范围，因为类的所有都是static,便于调用
 *
 *
 */
public class SecurityContextHolder {

    private static SecurityContextHolderStrategy strategy;

    public static final String MODE_THREADLOCAL = "MODE_THREADLOCAL";
    public static final String SYSTEM_PROPERTY = "spring.security.strategy";
    private static String strategyName = System.getProperty(SYSTEM_PROPERTY);


    static {
        initialize();
    }

    private static void initialize() {
        if (!StringUtils.hasText(strategyName)) {
            // Set default
            strategyName = MODE_THREADLOCAL;
        }
        if (strategyName.equals(MODE_THREADLOCAL)) {
            strategy = new ThreadLocalSecurityContextHolderStrategy();
        }
    }

    public static void setContext(SecurityContext context) {
        strategy.setContext(context);
    }

    public static SecurityContext getContext() {
        return strategy.getContext();
    }

    public static void clearContext() {
        strategy.clearContext();
    }
}
