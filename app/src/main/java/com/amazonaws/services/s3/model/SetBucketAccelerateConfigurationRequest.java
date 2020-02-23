package com.amazonaws.services.s3.model;

import com.amazonaws.AmazonWebServiceRequest;

public class SetBucketAccelerateConfigurationRequest extends AmazonWebServiceRequest implements S3AccelerateUnsupported {
    private BucketAccelerateConfiguration accelerateConfiguration;
    private String bucketName;

    public SetBucketAccelerateConfigurationRequest(String bucketName2, BucketAccelerateConfiguration configuration) {
        this.bucketName = bucketName2;
        this.accelerateConfiguration = configuration;
    }

    public String getBucketName() {
        return this.bucketName;
    }

    public void setBucketName(String bucketName2) {
        this.bucketName = bucketName2;
    }

    public SetBucketAccelerateConfigurationRequest withBucketName(String bucketName2) {
        setBucketName(bucketName2);
        return this;
    }

    public BucketAccelerateConfiguration getAccelerateConfiguration() {
        return this.accelerateConfiguration;
    }

    public void setAccelerateConfiguration(BucketAccelerateConfiguration accelerateConfiguration2) {
        this.accelerateConfiguration = accelerateConfiguration2;
    }

    public SetBucketAccelerateConfigurationRequest withAccelerateConfiguration(BucketAccelerateConfiguration accelerateConfiguration2) {
        setAccelerateConfiguration(accelerateConfiguration2);
        return this;
    }
}
