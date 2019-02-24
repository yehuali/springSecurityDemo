package com.example.core.web.firewall;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *  可用于拒绝潜在危险的请求和/或包装它们控制他们的行为
 *  在通过过滤器链发送任何请求之前,将实现注入{@code FilterChainProxy}并将被调用;
 *  如果响应行为也应该受到限制,它还可以提供响应包装器
 */
public interface HttpFirewall {
    /**
     * 提供将通过过滤器链传递的请求对象
     * 如果请求应该立即被拒绝，则抛出RequestRejectedException
     */
    FirewalledRequest getFirewalledRequest(HttpServletRequest request)
            throws RequestRejectedException;

    /**
     * 提供将通过过滤器链传递的响应
     * @param response 原始响应
     * @return  原始响应或替换/包装器。
     */
    HttpServletResponse getFirewalledResponse(HttpServletResponse response);
}
