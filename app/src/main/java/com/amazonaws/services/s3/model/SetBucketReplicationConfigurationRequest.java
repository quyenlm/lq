package com.amazonaws.services.s3.model;

import com.amazonaws.AmazonWebServiceRequest;

public class SetBucketReplicationConfigurationRequest extends AmazonWebServiceRequest {
    private String bucketName;
    private BucketReplicationConfiguration replicationConfiguration;

    public SetBucketReplicationConfigurationRequest() {
    }

    public SetBucketReplicationConfigurationRequest(String bucketName2, BucketReplicationConfiguration replicationConfiguration2) {
        this.bucketName = bucketName2;
        this.replicationConfiguration = replicationConfiguration2;
    }

    public String getBucketName() {
        return this.bucketName;
    }

    public void setBucketName(String bucketName2) {
        this.bucketName = bucketName2;
    }

    public SetBucketReplicationConfigurationRequest withBucketName(String bucketName2) {
        setBucketName(bucketName2);
        return this;
    }

    public BucketReplicationConfiguration getReplicationConfiguration() {
        return this.replicationConfiguration;
    }

    public void setReplicationConfiguration(BucketReplicationConfiguration replicationConfiguration2) {
        this.replicationConfiguration = replicationConfiguration2;
    }

    public SetBucketReplicationConfigurationRequest withReplicationConfiguration(BucketReplicationConfiguration replicationConfiguration2) {
        setReplicationConfiguration(replicationConfiguration2);
        return this;
    }
}
