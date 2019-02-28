package com.example.core.context;

import com.example.core.web.context.SecurityContext;

/**
 *一种针对线程存储安全上下文信息的策略
 * 首选策略由{@link SecurityContextHolder}加载
 */
public interface SecurityContextHolderStrategy {

    void clearContext();

    SecurityContext getContext();

    void setContext(SecurityContext context);
}
