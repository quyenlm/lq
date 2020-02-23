package com.amazonaws.services.s3.model;

public class RoutingRuleCondition {
    String httpErrorCodeReturnedEquals;
    String keyPrefixEquals;

    public void setKeyPrefixEquals(String keyPrefixEquals2) {
        this.keyPrefixEquals = keyPrefixEquals2;
    }

    public String getKeyPrefixEquals() {
        return this.keyPrefixEquals;
    }

    public RoutingRuleCondition withKeyPrefixEquals(String keyPrefixEquals2) {
        setKeyPrefixEquals(keyPrefixEquals2);
        return this;
    }

    public void setHttpErrorCodeReturnedEquals(String httpErrorCodeReturnedEquals2) {
        this.httpErrorCodeReturnedEquals = httpErrorCodeReturnedEquals2;
    }

    public String getHttpErrorCodeReturnedEquals() {
        return this.httpErrorCodeReturnedEquals;
    }

    public RoutingRuleCondition withHttpErrorCodeReturnedEquals(String httpErrorCodeReturnedEquals2) {
        setHttpErrorCodeReturnedEquals(httpErrorCodeReturnedEquals2);
        return this;
    }
}
