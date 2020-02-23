package com.amazonaws.services.s3.model;

import java.util.HashMap;
import java.util.Map;

public class BucketReplicationConfiguration {
    private String roleARN;
    private Map<String, ReplicationRule> rules = new HashMap();

    public String getRoleARN() {
        return this.roleARN;
    }

    public void setRoleARN(String roleARN2) {
        this.roleARN = roleARN2;
    }

    public BucketReplicationConfiguration withRoleARN(String roleARN2) {
        setRoleARN(roleARN2);
        return this;
    }

    public Map<String, ReplicationRule> getRules() {
        return this.rules;
    }

    public ReplicationRule getRule(String id) {
        return this.rules.get(id);
    }

    public void setRules(Map<String, ReplicationRule> rules2) {
        if (rules2 == null) {
            throw new IllegalArgumentException("Replication rules cannot be null");
        }
        this.rules = new HashMap(rules2);
    }

    public BucketReplicationConfiguration withRules(Map<String, ReplicationRule> rules2) {
        setRules(rules2);
        return this;
    }

    public BucketReplicationConfiguration addRule(String id, ReplicationRule rule) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("Rule id cannot be null or empty.");
        } else if (rule == null) {
            throw new IllegalArgumentException("Replication rule cannot be null");
        } else {
            this.rules.put(id, rule);
            return this;
        }
    }

    public BucketReplicationConfiguration removeRule(String id) {
        this.rules.remove(id);
        return this;
    }
}
