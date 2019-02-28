package com.example.core.web.access;

import org.springframework.aop.framework.AopInfrastructureBean;

import java.util.Collection;

/**
 *由存储并标识{@link ConfigAttribute}的类实现
 * 应用于给定的安全对象调用
 */
public interface SecurityMetadataSource extends AopInfrastructureBean {
    Collection<ConfigAttribute> getAttributes(Object object)
            throws IllegalArgumentException;
}
