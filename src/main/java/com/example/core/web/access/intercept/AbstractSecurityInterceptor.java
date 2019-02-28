package com.example.core.web.access.intercept;

import com.example.core.authentication.Authentication;
import com.example.core.authentication.AuthenticationException;
import com.example.core.authentication.AuthenticationManager;
import com.example.core.web.access.AccessDecisionManager;
import com.example.core.web.access.AccessDeniedException;
import com.example.core.web.access.ConfigAttribute;
import com.example.core.web.access.SecurityMetadataSource;
import com.example.core.web.access.event.AuthorizationFailureEvent;
import com.example.core.web.access.event.AuthorizedEvent;
import com.example.core.web.context.SecurityContextHolder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;

import java.util.Collection;

public abstract class AbstractSecurityInterceptor {

    protected final Log logger = LogFactory.getLog(getClass());

    private AuthenticationManager authenticationManager = new NoOpAuthenticationManager();

    private AccessDecisionManager accessDecisionManager;
    private ApplicationEventPublisher eventPublisher;

    private boolean alwaysReauthenticate = false;
    private boolean publishAuthorizationSuccess = false;

    public abstract SecurityMetadataSource obtainSecurityMetadataSource();

    public void setAuthenticationManager(AuthenticationManager newManager) {
        this.authenticationManager = newManager;
    }

    public void setAccessDecisionManager(AccessDecisionManager accessDecisionManager) {
        this.accessDecisionManager = accessDecisionManager;
    }

    public void afterPropertiesSet() throws Exception {

    }

    protected InterceptorStatusToken beforeInvocation(Object object) {

        Collection<ConfigAttribute> attributes = this.obtainSecurityMetadataSource()
                .getAttributes(object);

        Authentication authenticated = authenticateIfRequired();

        //尝试认证
        try {
            this.accessDecisionManager.decide(authenticated, object, attributes);
        }catch(AccessDeniedException accessDeniedException){
            publishEvent(new AuthorizationFailureEvent(object, attributes, authenticated,
                    accessDeniedException));
            throw accessDeniedException;
        }


        if (publishAuthorizationSuccess) {
            publishEvent(new AuthorizedEvent(object, attributes, authenticated));
        }

        return null;

    }

    private void publishEvent(ApplicationEvent event) {
        if (this.eventPublisher != null) {
            this.eventPublisher.publishEvent(event);
        }
    }

    /**
     * 如果{@link Authentication#isAuthenticated()}返回false或属性<tt>alwaysReauthenticate</tt>已设置为true，
     * 则检查当前身份验证令牌并将其传递给AuthenticationManager
     * @return
     */
    private Authentication authenticateIfRequired() {
        Authentication authentication = SecurityContextHolder.getContext()
                .getAuthentication();
        if (authentication.isAuthenticated() && !alwaysReauthenticate) {
            if (logger.isDebugEnabled()) {
                logger.debug("Previously Authenticated: " + authentication);
            }

            return authentication;
        }

        authentication = authenticationManager.authenticate(authentication);
        //我们不需要authenticated.setAuthentication(true)，因为每个provider会做
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return authentication;
    }

    private static class NoOpAuthenticationManager implements AuthenticationManager {

        public Authentication authenticate(Authentication authentication)
                throws AuthenticationException {
            throw new RuntimeException("Cannot authenticate "
                    + authentication);
        }
    }


}
