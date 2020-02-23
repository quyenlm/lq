package com.amazonaws.services.s3.model;

import com.amazonaws.AmazonWebServiceRequest;

public class GetObjectMetadataRequest extends AmazonWebServiceRequest {
    private String bucketName;
    private String key;
    private SSECustomerKey sseCustomerKey;
    private String versionId;

    public GetObjectMetadataRequest(String bucketName2, String key2) {
        setBucketName(bucketName2);
        setKey(key2);
    }

    public GetObjectMetadataRequest(String bucketName2, String key2, String versionId2) {
        this(bucketName2, key2);
        setVersionId(versionId2);
    }

    public String getBucketName() {
        return this.bucketName;
    }

    public void setBucketName(String bucketName2) {
        this.bucketName = bucketName2;
    }

    public GetObjectMetadataRequest withBucketName(String bucketName2) {
        setBucketName(bucketName2);
        return this;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key2) {
        this.key = key2;
    }

    public GetObjectMetadataRequest withKey(String key2) {
        setKey(key2);
        return this;
    }

    public String getVersionId() {
        return this.versionId;
    }

    public void setVersionId(String versionId2) {
        this.versionId = versionId2;
    }

    public GetObjectMetadataRequest withVersionId(String versionId2) {
        setVersionId(versionId2);
        return this;
    }

    public SSECustomerKey getSSECustomerKey() {
        return this.sseCustomerKey;
    }

    public void setSSECustomerKey(SSECustomerKey sseKey) {
        this.sseCustomerKey = sseKey;
    }

    public GetObjectMetadataRequest withSSECustomerKey(SSECustomerKey sseKey) {
        setSSECustomerKey(sseKey);
        return this;
    }
}
