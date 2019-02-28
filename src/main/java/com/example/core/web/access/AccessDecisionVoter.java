package com.example.core.web.access;

import com.example.core.authentication.Authentication;

import java.util.Collection;

/**
 * 表示类负责对授权决策进行投票
 * 投票的协调(即轮询{@code AccessDecisionVoter}s，对其响应进行计数)
 * ，并作出最终授权决策)由{ @link org.springframework.security.access.AccessDecisionManager }
 * @param <S>
 */
public interface AccessDecisionVoter<S> {

    int ACCESS_GRANTED = 1;
    int ACCESS_ABSTAIN = 0;
    int ACCESS_DENIED = -1;


    int vote(Authentication authentication, S object,
             Collection<ConfigAttribute> attributes);

}
