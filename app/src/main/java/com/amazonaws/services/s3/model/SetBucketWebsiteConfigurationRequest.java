package com.amazonaws.services.s3.model;

import com.amazonaws.AmazonWebServiceRequest;

public class SetBucketWebsiteConfigurationRequest extends AmazonWebServiceRequest {
    private String bucketName;
    private BucketWebsiteConfiguration configuration;

    public SetBucketWebsiteConfigurationRequest(String bucketName2, BucketWebsiteConfiguration configuration2) {
        this.bucketName = bucketName2;
        this.configuration = configuration2;
    }

    public void setBucketName(String bucketName2) {
        this.bucketName = bucketName2;
    }

    public String getBucketName() {
        return this.bucketName;
    }

    public SetBucketWebsiteConfigurationRequest withBucketName(String bucketName2) {
        setBucketName(bucketName2);
        return this;
    }

    public void setConfiguration(BucketWebsiteConfiguration configuration2) {
        this.configuration = configuration2;
    }

    public BucketWebsiteConfiguration getConfiguration() {
        return this.configuration;
    }

    public SetBucketWebsiteConfigurationRequest withConfiguration(BucketWebsiteConfiguration configuration2) {
        setConfiguration(configuration2);
        return this;
    }
}
