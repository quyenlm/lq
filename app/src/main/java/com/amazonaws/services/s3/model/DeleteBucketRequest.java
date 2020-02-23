package com.amazonaws.services.s3.model;

import com.amazonaws.AmazonWebServiceRequest;

public class DeleteBucketRequest extends AmazonWebServiceRequest implements S3AccelerateUnsupported {
    private String bucketName;

    public DeleteBucketRequest(String bucketName2) {
        setBucketName(bucketName2);
    }

    public void setBucketName(String bucketName2) {
        this.bucketName = bucketName2;
    }

    public String getBucketName() {
        return this.bucketName;
    }
}
