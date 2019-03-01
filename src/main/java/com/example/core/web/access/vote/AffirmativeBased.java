package com.example.core.web.access.vote;

import com.example.core.authentication.Authentication;
import com.example.core.web.access.AccessDecisionVoter;
import com.example.core.web.access.AccessDeniedException;
import com.example.core.web.access.ConfigAttribute;

import java.util.Collection;
import java.util.List;

/**
 * 简单的具体实现 { @link org.springframework.security.access。AccessDecisionManager}
 * 如果任何<code>AccessDecisionVoter</code>返回一个肯定的答复,它授予
 */
public class AffirmativeBased extends  AbstractAccessDecisionManager{

    public AffirmativeBased(List<AccessDecisionVoter<? extends Object>> decisionVoters) {
        super(decisionVoters);
    }

    @Override
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException {
        int deny = 0;
        for (AccessDecisionVoter voter : getDecisionVoters()) {
            int result = voter.vote(authentication, object, configAttributes);
            switch (result) {
                case AccessDecisionVoter.ACCESS_GRANTED:
                    return;

                case AccessDecisionVoter.ACCESS_DENIED:
                    deny++;

                    break;

                default:
                    break;
            }
        }

        if (deny > 0) {
            throw new AccessDeniedException("Access is denied");
        }

    }
}
