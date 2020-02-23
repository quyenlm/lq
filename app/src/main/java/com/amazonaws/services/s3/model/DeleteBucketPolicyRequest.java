package com.amazonaws.services.s3.model;

import com.amazonaws.AmazonWebServiceRequest;

public class DeleteBucketPolicyRequest extends AmazonWebServiceRequest {
    private String bucketName;

    public DeleteBucketPolicyRequest(String bucketName2) {
        this.bucketName = bucketName2;
    }

    public String getBucketName() {
        return this.bucketName;
    }

    public void setBucketName(String bucketName2) {
        this.bucketName = bucketName2;
    }

    public DeleteBucketPolicyRequest withBucketName(String bucketName2) {
        setBucketName(bucketName2);
        return this;
    }
}
