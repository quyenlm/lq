package com.amazonaws.services.s3.model;

import com.amazonaws.AmazonWebServiceRequest;

public class RestoreObjectRequest extends AmazonWebServiceRequest {
    private String bucketName;
    private int expirationInDays;
    private String key;
    private String versionId;

    public RestoreObjectRequest(String bucketName2, String key2) {
        this(bucketName2, key2, -1);
    }

    public RestoreObjectRequest(String bucketName2, String key2, int expirationInDays2) {
        this.bucketName = bucketName2;
        this.key = key2;
        this.expirationInDays = expirationInDays2;
    }

    public String getBucketName() {
        return this.bucketName;
    }

    public RestoreObjectRequest withBucketName(String bucketName2) {
        this.bucketName = bucketName2;
        return this;
    }

    public void setBucketName(String bucketName2) {
        this.bucketName = bucketName2;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key2) {
        this.key = key2;
    }

    public RestoreObjectRequest withKey(String key2) {
        this.key = key2;
        return this;
    }

    public String getVersionId() {
        return this.versionId;
    }

    public void setVersionId(String versionId2) {
        this.versionId = versionId2;
    }

    public RestoreObjectRequest withVersionId(String versionId2) {
        this.versionId = versionId2;
        return this;
    }

    public void setExpirationInDays(int expirationInDays2) {
        this.expirationInDays = expirationInDays2;
    }

    public int getExpirationInDays() {
        return this.expirationInDays;
    }

    public RestoreObjectRequest withExpirationInDays(int expirationInDays2) {
        this.expirationInDays = expirationInDays2;
        return this;
    }
}
