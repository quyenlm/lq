package com.amazonaws.mobileconnectors.s3.transfermanager.model;

@Deprecated
public class UploadResult {
    private String bucketName;
    private String eTag;
    private String key;
    private String versionId;

    public String getBucketName() {
        return this.bucketName;
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

    public String getETag() {
        return this.eTag;
    }

    public void setETag(String etag) {
        this.eTag = etag;
    }

    public String getVersionId() {
        return this.versionId;
    }

    public void setVersionId(String versionId2) {
        this.versionId = versionId2;
    }
}
