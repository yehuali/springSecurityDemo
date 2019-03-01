package com.example.core.web.access.vote;

import com.example.core.web.access.AccessDecisionManager;
import com.example.core.web.access.AccessDecisionVoter;
import org.springframework.util.Assert;

import java.util.List;

public abstract class AbstractAccessDecisionManager implements AccessDecisionManager {

    private List<AccessDecisionVoter<? extends Object>> decisionVoters;

    protected AbstractAccessDecisionManager(
            List<AccessDecisionVoter<? extends Object>> decisionVoters) {
        Assert.notEmpty(decisionVoters, "A list of AccessDecisionVoters is required");
        this.decisionVoters = decisionVoters;
    }

    public List<AccessDecisionVoter<? extends Object>> getDecisionVoters() {
        return this.decisionVoters;
    }

}
