package com.amazonaws.services.s3.model;

public class RoutingRule {
    RoutingRuleCondition condition;
    RedirectRule redirect;

    public void setCondition(RoutingRuleCondition condition2) {
        this.condition = condition2;
    }

    public RoutingRuleCondition getCondition() {
        return this.condition;
    }

    public RoutingRule withCondition(RoutingRuleCondition condition2) {
        setCondition(condition2);
        return this;
    }

    public void setRedirect(RedirectRule redirect2) {
        this.redirect = redirect2;
    }

    public RedirectRule getRedirect() {
        return this.redirect;
    }

    public RoutingRule withRedirect(RedirectRule redirect2) {
        setRedirect(redirect2);
        return this;
    }
}
