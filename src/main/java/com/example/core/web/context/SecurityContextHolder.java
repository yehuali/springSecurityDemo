package com.example.core.web.context;

import com.example.core.context.SecurityContextHolderStrategy;

/**
 * 将SecurityContext与当前线程绑定
 * 提供一系列静态方法，方法委托给SecurityContextHolderStrategy的实例
 *  --->目的：提供简便的方法针对给定的JVM使用相应的策略
 *  这是一个JVM范围，因为类的所有都是static,便于调用
 *
 *
 */
public class SecurityContextHolder {

    private static SecurityContextHolderStrategy strategy;

    public static void setContext(SecurityContext context) {
        strategy.setContext(context);
    }
}
