package com.amazonaws.services.s3.model;

public class BucketAccelerateConfiguration {
    private String status;

    public BucketAccelerateConfiguration(String status2) {
        setStatus(status2);
    }

    public BucketAccelerateConfiguration(BucketAccelerateStatus status2) {
        setStatus(status2);
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status2) {
        this.status = status2;
    }

    public void setStatus(BucketAccelerateStatus status2) {
        setStatus(status2.toString());
    }

    public BucketAccelerateConfiguration withStatus(String status2) {
        setStatus(status2);
        return this;
    }

    public BucketAccelerateConfiguration withStatus(BucketAccelerateStatus status2) {
        setStatus(status2);
        return this;
    }

    public boolean isAccelerateEnabled() {
        return BucketAccelerateStatus.Enabled.toString().equals(getStatus());
    }
}
