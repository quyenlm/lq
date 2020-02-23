package com.amazonaws.services.s3.model;

public class BucketVersioningConfiguration {
    public static final String ENABLED = "Enabled";
    public static final String OFF = "Off";
    public static final String SUSPENDED = "Suspended";
    private Boolean isMfaDeleteEnabled = null;
    private String status;

    public BucketVersioningConfiguration() {
        setStatus(OFF);
    }

    public BucketVersioningConfiguration(String status2) {
        setStatus(status2);
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status2) {
        this.status = status2;
    }

    public BucketVersioningConfiguration withStatus(String status2) {
        setStatus(status2);
        return this;
    }

    public Boolean isMfaDeleteEnabled() {
        return this.isMfaDeleteEnabled;
    }

    public void setMfaDeleteEnabled(Boolean mfaDeleteEnabled) {
        this.isMfaDeleteEnabled = mfaDeleteEnabled;
    }

    public BucketVersioningConfiguration withMfaDeleteEnabled(Boolean mfaDeleteEnabled) {
        setMfaDeleteEnabled(mfaDeleteEnabled);
        return this;
    }
}
