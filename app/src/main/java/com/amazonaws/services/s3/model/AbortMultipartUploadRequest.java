package com.amazonaws.services.s3.model;

import com.amazonaws.AmazonWebServiceRequest;

public class AbortMultipartUploadRequest extends AmazonWebServiceRequest {
    private String bucketName;
    private String key;
    private String uploadId;

    public AbortMultipartUploadRequest(String bucketName2, String key2, String uploadId2) {
        this.bucketName = bucketName2;
        this.key = key2;
        this.uploadId = uploadId2;
    }

    public String getBucketName() {
        return this.bucketName;
    }

    public void setBucketName(String value) {
        this.bucketName = value;
    }

    public AbortMultipartUploadRequest withBucketName(String bucketName2) {
        this.bucketName = bucketName2;
        return this;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key2) {
        this.key = key2;
    }

    public AbortMultipartUploadRequest withKey(String key2) {
        this.key = key2;
        return this;
    }

    public String getUploadId() {
        return this.uploadId;
    }

    public void setUploadId(String uploadId2) {
        this.uploadId = uploadId2;
    }

    public AbortMultipartUploadRequest withUploadId(String uploadId2) {
        this.uploadId = uploadId2;
        return this;
    }
}
