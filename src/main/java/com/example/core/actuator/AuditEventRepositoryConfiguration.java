package com.example.core.actuator;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnMissingBean(AuditEventRepository.class)
public class AuditEventRepositoryConfiguration {
    @Bean
    public InMemoryAuditEventRepository auditEventRepository() throws Exception {
        return new InMemoryAuditEventRepository();
    }
}
