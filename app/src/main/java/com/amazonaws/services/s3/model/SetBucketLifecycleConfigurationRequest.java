package com.amazonaws.services.s3.model;

import com.amazonaws.AmazonWebServiceRequest;

public class SetBucketLifecycleConfigurationRequest extends AmazonWebServiceRequest {
    private String bucketName;
    private BucketLifecycleConfiguration lifecycleConfiguration;

    public SetBucketLifecycleConfigurationRequest(String bucketName2, BucketLifecycleConfiguration lifecycleConfiguration2) {
        this.bucketName = bucketName2;
        this.lifecycleConfiguration = lifecycleConfiguration2;
    }

    public String getBucketName() {
        return this.bucketName;
    }

    public void setBucketName(String bucketName2) {
        this.bucketName = bucketName2;
    }

    public SetBucketLifecycleConfigurationRequest withBucketName(String bucketName2) {
        setBucketName(bucketName2);
        return this;
    }

    public BucketLifecycleConfiguration getLifecycleConfiguration() {
        return this.lifecycleConfiguration;
    }

    public void setLifecycleConfiguration(BucketLifecycleConfiguration lifecycleConfiguration2) {
        this.lifecycleConfiguration = lifecycleConfiguration2;
    }

    public SetBucketLifecycleConfigurationRequest withLifecycleConfiguration(BucketLifecycleConfiguration lifecycleConfiguration2) {
        setLifecycleConfiguration(lifecycleConfiguration2);
        return this;
    }
}
