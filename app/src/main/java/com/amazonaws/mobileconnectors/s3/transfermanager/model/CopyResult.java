package com.amazonaws.mobileconnectors.s3.transfermanager.model;

@Deprecated
public class CopyResult {
    private String destinationBucketName;
    private String destinationKey;
    private String eTag;
    private String sourceBucketName;
    private String sourceKey;
    private String versionId;

    public String getSourceBucketName() {
        return this.sourceBucketName;
    }

    public void setSourceBucketName(String sourceBucketName2) {
        this.sourceBucketName = sourceBucketName2;
    }

    public String getSourceKey() {
        return this.sourceKey;
    }

    public void setSourceKey(String sourceKey2) {
        this.sourceKey = sourceKey2;
    }

    public String getDestinationBucketName() {
        return this.destinationBucketName;
    }

    public void setDestinationBucketName(String destinationBucketName2) {
        this.destinationBucketName = destinationBucketName2;
    }

    public String getDestinationKey() {
        return this.destinationKey;
    }

    public void setDestinationKey(String destinationKey2) {
        this.destinationKey = destinationKey2;
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
