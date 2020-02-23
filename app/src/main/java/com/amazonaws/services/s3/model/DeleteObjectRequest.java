package com.amazonaws.services.s3.model;

import com.amazonaws.AmazonWebServiceRequest;

public class DeleteObjectRequest extends AmazonWebServiceRequest {
    private String bucketName;
    private String key;

    public DeleteObjectRequest(String bucketName2, String key2) {
        setBucketName(bucketName2);
        setKey(key2);
    }

    public String getBucketName() {
        return this.bucketName;
    }

    public void setBucketName(String bucketName2) {
        this.bucketName = bucketName2;
    }

    public DeleteObjectRequest withBucketName(String bucketName2) {
        setBucketName(bucketName2);
        return this;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key2) {
        this.key = key2;
    }

    public DeleteObjectRequest withKey(String key2) {
        setKey(key2);
        return this;
    }
}
