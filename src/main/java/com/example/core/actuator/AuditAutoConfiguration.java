package com.example.core.actuator;

import com.example.core.actuator.security.AbstractAuthorizationAuditListener;
import com.example.core.actuator.security.AuthorizationAuditListener;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuditAutoConfiguration {

    private final AuditEventRepository auditEventRepository;

    public AuditAutoConfiguration(
            ObjectProvider<AuditEventRepository> auditEventRepository) {
        this.auditEventRepository = auditEventRepository.getIfAvailable();
    }

//    /**
//     * 注册1个id为auditListener,类型为AuditListener的bean
//     * 当beanFactory中不存在类型为AbstractAuditListener的bean时生效
//     * @return
//     * @throws Exception
//     */
//    @Bean
//    @ConditionalOnMissingBean(AbstractAuditListener.class)
//    public AuditListener auditListener() throws Exception {
//        return new AuditListener(this.auditEventRepository);
//    }

    @Bean
    @ConditionalOnMissingBean(AbstractAuthorizationAuditListener.class)
    public AuthorizationAuditListener authorizationAuditListener() throws Exception {
        return new AuthorizationAuditListener();
    }

}
