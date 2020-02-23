package com.amazonaws.services.s3.model;

import com.amazonaws.AmazonWebServiceRequest;

public class GetBucketWebsiteConfigurationRequest extends AmazonWebServiceRequest {
    private String bucketName;

    public GetBucketWebsiteConfigurationRequest(String bucketName2) {
        this.bucketName = bucketName2;
    }

    public void setBucketName(String bucketName2) {
        this.bucketName = bucketName2;
    }

    public String getBucketName() {
        return this.bucketName;
    }

    public GetBucketWebsiteConfigurationRequest withBucketName(String bucketName2) {
        setBucketName(bucketName2);
        return this;
    }
}
