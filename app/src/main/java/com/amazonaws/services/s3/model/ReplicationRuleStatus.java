package com.amazonaws.services.s3.model;

public enum ReplicationRuleStatus {
    Enabled("Enabled"),
    Disabled(BucketLifecycleConfiguration.DISABLED);
    
    private final String status;

    private ReplicationRuleStatus(String status2) {
        this.status = status2;
    }

    public String getStatus() {
        return this.status;
    }
}
