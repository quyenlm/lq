package com.amazonaws.services.s3.model;

import com.amazonaws.AmazonWebServiceRequest;

public class HeadBucketRequest extends AmazonWebServiceRequest {
    private String bucketName;

    public void setBucketName(String bucketName2) {
        this.bucketName = bucketName2;
    }

    public String getBucketName() {
        return this.bucketName;
    }

    public HeadBucketRequest(String bucketName2) {
        this.bucketName = bucketName2;
    }
}
