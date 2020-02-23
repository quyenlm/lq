package com.amazonaws.services.s3.model;

import java.util.Date;

public class S3VersionSummary {
    protected String bucketName;
    private String eTag;
    private boolean isDeleteMarker;
    private boolean isLatest;
    private String key;
    private Date lastModified;
    private Owner owner;
    private long size;
    private String storageClass;
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

    public String getVersionId() {
        return this.versionId;
    }

    public void setVersionId(String id) {
        this.versionId = id;
    }

    public boolean isLatest() {
        return this.isLatest;
    }

    public void setIsLatest(boolean isLatest2) {
        this.isLatest = isLatest2;
    }

    public Date getLastModified() {
        return this.lastModified;
    }

    public void setLastModified(Date lastModified2) {
        this.lastModified = lastModified2;
    }

    public Owner getOwner() {
        return this.owner;
    }

    public void setOwner(Owner owner2) {
        this.owner = owner2;
    }

    public boolean isDeleteMarker() {
        return this.isDeleteMarker;
    }

    public void setIsDeleteMarker(boolean isDeleteMarker2) {
        this.isDeleteMarker = isDeleteMarker2;
    }

    public String getETag() {
        return this.eTag;
    }

    public void setETag(String eTag2) {
        this.eTag = eTag2;
    }

    public String getStorageClass() {
        return this.storageClass;
    }

    public void setStorageClass(String storageClass2) {
        this.storageClass = storageClass2;
    }

    public long getSize() {
        return this.size;
    }

    public void setSize(long size2) {
        this.size = size2;
    }
}
