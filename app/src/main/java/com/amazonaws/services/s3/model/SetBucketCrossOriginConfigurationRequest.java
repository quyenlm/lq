package com.amazonaws.services.s3.model;

import com.amazonaws.AmazonWebServiceRequest;

public class SetBucketCrossOriginConfigurationRequest extends AmazonWebServiceRequest {
    private String bucketName;
    private BucketCrossOriginConfiguration crossOriginConfiguration;

    public SetBucketCrossOriginConfigurationRequest(String bucketName2, BucketCrossOriginConfiguration crossOriginConfiguration2) {
        this.bucketName = bucketName2;
        this.crossOriginConfiguration = crossOriginConfiguration2;
    }

    public String getBucketName() {
        return this.bucketName;
    }

    public void setBucketName(String bucketName2) {
        this.bucketName = bucketName2;
    }

    public SetBucketCrossOriginConfigurationRequest withBucketName(String bucketName2) {
        setBucketName(bucketName2);
        return this;
    }

    public BucketCrossOriginConfiguration getCrossOriginConfiguration() {
        return this.crossOriginConfiguration;
    }

    public void setCrossOriginConfiguration(BucketCrossOriginConfiguration crossOriginConfiguration2) {
        this.crossOriginConfiguration = crossOriginConfiguration2;
    }

    public SetBucketCrossOriginConfigurationRequest withCrossOriginConfiguration(BucketCrossOriginConfiguration crossOriginConfiguration2) {
        setCrossOriginConfiguration(crossOriginConfiguration2);
        return this;
    }
}
