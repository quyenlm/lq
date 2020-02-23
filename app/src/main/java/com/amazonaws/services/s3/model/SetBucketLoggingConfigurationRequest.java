package com.amazonaws.services.s3.model;

import com.amazonaws.AmazonWebServiceRequest;

public class SetBucketLoggingConfigurationRequest extends AmazonWebServiceRequest {
    private String bucketName;
    private BucketLoggingConfiguration loggingConfiguration;

    public SetBucketLoggingConfigurationRequest(String bucketName2, BucketLoggingConfiguration loggingConfiguration2) {
        this.bucketName = bucketName2;
        this.loggingConfiguration = loggingConfiguration2;
    }

    public String getBucketName() {
        return this.bucketName;
    }

    public void setBucketName(String bucketName2) {
        this.bucketName = bucketName2;
    }

    public SetBucketLoggingConfigurationRequest withBucketName(String bucketName2) {
        setBucketName(bucketName2);
        return this;
    }

    public BucketLoggingConfiguration getLoggingConfiguration() {
        return this.loggingConfiguration;
    }

    public void setLoggingConfiguration(BucketLoggingConfiguration loggingConfiguration2) {
        this.loggingConfiguration = loggingConfiguration2;
    }

    public SetBucketLoggingConfigurationRequest withLoggingConfiguration(BucketLoggingConfiguration loggingConfiguration2) {
        setLoggingConfiguration(loggingConfiguration2);
        return this;
    }
}
