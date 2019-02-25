package com.example.core.web.context;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 用于持久化{@link SecurityContext}中的请求策略
 * 通过 {@link SecurityContextPersistenceFilter}获取当前执行线程的上下文以及一旦当前线程删除或请求完成后，存储上下文
 *
 * 使用的持久性机制将取决于实现，但最常见的HttpSession用于存储上下文
 */
public interface SecurityContextRepository {

    SecurityContext loadContext(HttpRequestResponseHolder requestResponseHolder);

    void saveContext(SecurityContext context, HttpServletRequest request,
                     HttpServletResponse response);

    boolean containsContext(HttpServletRequest request);
}
