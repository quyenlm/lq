package com.amazonaws.services.s3.model;

import com.amazonaws.AmazonWebServiceRequest;

public class GetBucketPolicyRequest extends AmazonWebServiceRequest {
    private String bucketName;

    public GetBucketPolicyRequest(String bucketName2) {
        this.bucketName = bucketName2;
    }

    public String getBucketName() {
        return this.bucketName;
    }

    public void setBucketName(String bucketName2) {
        this.bucketName = bucketName2;
    }

    public GetBucketPolicyRequest withBucketName(String bucketName2) {
        setBucketName(bucketName2);
        return this;
    }
}
