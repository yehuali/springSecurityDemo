package com.example.core.web.access;

import java.io.Serializable;

/**
 * 存储与安全系统相关的配置属性。
 * 当一个 {@link AbstractSecurityInterceptor} 被设置，为安全对象模式定义了配置属性列表
 * 这些配置属性对{@link RunAsManager}有特殊意义，
 * {@link AccessDecisionManager}或<code>AccessDecisionManager</code>委托
 *
 * 与其他<code>ConfigAttribute</code>s为同一个安全对象存储在运行时目标
 */
public interface ConfigAttribute extends Serializable {
    String getAttribute();
}
