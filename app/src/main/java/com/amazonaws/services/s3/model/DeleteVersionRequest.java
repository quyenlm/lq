package com.amazonaws.services.s3.model;

import com.amazonaws.AmazonWebServiceRequest;

public class DeleteVersionRequest extends AmazonWebServiceRequest {
    private String bucketName;
    private String key;
    private MultiFactorAuthentication mfa;
    private String versionId;

    public DeleteVersionRequest(String bucketName2, String key2, String versionId2) {
        this.bucketName = bucketName2;
        this.key = key2;
        this.versionId = versionId2;
    }

    public DeleteVersionRequest(String bucketName2, String key2, String versionId2, MultiFactorAuthentication mfa2) {
        this(bucketName2, key2, versionId2);
        this.mfa = mfa2;
    }

    public String getBucketName() {
        return this.bucketName;
    }

    public void setBucketName(String bucketName2) {
        this.bucketName = bucketName2;
    }

    public DeleteVersionRequest withBucketName(String bucketName2) {
        setBucketName(bucketName2);
        return this;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key2) {
        this.key = key2;
    }

    public DeleteVersionRequest withKey(String key2) {
        setKey(key2);
        return this;
    }

    public String getVersionId() {
        return this.versionId;
    }

    public void setVersionId(String versionId2) {
        this.versionId = versionId2;
    }

    public DeleteVersionRequest withVersionId(String versionId2) {
        setVersionId(versionId2);
        return this;
    }

    public MultiFactorAuthentication getMfa() {
        return this.mfa;
    }

    public void setMfa(MultiFactorAuthentication mfa2) {
        this.mfa = mfa2;
    }

    public DeleteVersionRequest withMfa(MultiFactorAuthentication mfa2) {
        setMfa(mfa2);
        return this;
    }
}
