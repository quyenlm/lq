package com.amazonaws.services.s3.model;

import com.amazonaws.AmazonWebServiceRequest;

public class GenericBucketRequest extends AmazonWebServiceRequest {
    private String bucketName;

    public GenericBucketRequest(String bucketName2) {
        this.bucketName = bucketName2;
    }

    @Deprecated
    public String getBucket() {
        return this.bucketName;
    }

    public String getBucketName() {
        return this.bucketName;
    }

    public void setBucketName(String bucketName2) {
        this.bucketName = bucketName2;
    }

    public GenericBucketRequest withBucketName(String bucketName2) {
        setBucketName(bucketName2);
        return this;
    }
}
