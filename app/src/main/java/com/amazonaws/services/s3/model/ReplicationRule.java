package com.amazonaws.services.s3.model;

public class ReplicationRule {
    private ReplicationDestinationConfig destinationConfig;
    private String prefix;
    private String status;

    public String getPrefix() {
        return this.prefix;
    }

    public void setPrefix(String prefix2) {
        if (prefix2 == null) {
            throw new IllegalArgumentException("Prefix cannot be null for a replication rule");
        }
        this.prefix = prefix2;
    }

    public ReplicationRule withPrefix(String prefix2) {
        setPrefix(prefix2);
        return this;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status2) {
        this.status = status2;
    }

    public ReplicationRule withStatus(String status2) {
        setStatus(status2);
        return this;
    }

    public void setStatus(ReplicationRuleStatus status2) {
        setStatus(status2.getStatus());
    }

    public ReplicationRule withStatus(ReplicationRuleStatus status2) {
        setStatus(status2.getStatus());
        return this;
    }

    public ReplicationDestinationConfig getDestinationConfig() {
        return this.destinationConfig;
    }

    public void setDestinationConfig(ReplicationDestinationConfig destinationConfig2) {
        if (destinationConfig2 == null) {
            throw new IllegalArgumentException("Destination cannot be null in the replication rule");
        }
        this.destinationConfig = destinationConfig2;
    }

    public ReplicationRule withDestinationConfig(ReplicationDestinationConfig destinationConfig2) {
        setDestinationConfig(destinationConfig2);
        return this;
    }
}
