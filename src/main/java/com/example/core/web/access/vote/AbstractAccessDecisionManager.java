package com.example.core.web.access.vote;

import com.example.core.web.access.AccessDecisionManager;
import com.example.core.web.access.AccessDecisionVoter;

import java.util.List;

public abstract class AbstractAccessDecisionManager implements AccessDecisionManager {

    private List<AccessDecisionVoter<? extends Object>> decisionVoters;

    public List<AccessDecisionVoter<? extends Object>> getDecisionVoters() {
        return this.decisionVoters;
    }

}
