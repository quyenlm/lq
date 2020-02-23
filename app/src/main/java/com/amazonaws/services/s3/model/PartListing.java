package com.amazonaws.services.s3.model;

import java.util.ArrayList;
import java.util.List;

public class PartListing {
    private String bucketName;
    private String encodingType;
    private Owner initiator;
    private boolean isTruncated;
    private String key;
    private Integer maxParts;
    private Integer nextPartNumberMarker;
    private Owner owner;
    private Integer partNumberMarker;
    private List<PartSummary> parts;
    private String storageClass;
    private String uploadId;

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

    public String getUploadId() {
        return this.uploadId;
    }

    public void setUploadId(String uploadId2) {
        this.uploadId = uploadId2;
    }

    public Owner getOwner() {
        return this.owner;
    }

    public void setOwner(Owner owner2) {
        this.owner = owner2;
    }

    public Owner getInitiator() {
        return this.initiator;
    }

    public void setInitiator(Owner initiator2) {
        this.initiator = initiator2;
    }

    public String getStorageClass() {
        return this.storageClass;
    }

    public void setStorageClass(String storageClass2) {
        this.storageClass = storageClass2;
    }

    public Integer getPartNumberMarker() {
        return this.partNumberMarker;
    }

    public void setPartNumberMarker(int partNumberMarker2) {
        this.partNumberMarker = Integer.valueOf(partNumberMarker2);
    }

    public Integer getNextPartNumberMarker() {
        return this.nextPartNumberMarker;
    }

    public void setNextPartNumberMarker(int nextPartNumberMarker2) {
        this.nextPartNumberMarker = Integer.valueOf(nextPartNumberMarker2);
    }

    public Integer getMaxParts() {
        return this.maxParts;
    }

    public void setMaxParts(int maxParts2) {
        this.maxParts = Integer.valueOf(maxParts2);
    }

    public String getEncodingType() {
        return this.encodingType;
    }

    public void setEncodingType(String encodingType2) {
        this.encodingType = encodingType2;
    }

    public boolean isTruncated() {
        return this.isTruncated;
    }

    public void setTruncated(boolean isTruncated2) {
        this.isTruncated = isTruncated2;
    }

    public List<PartSummary> getParts() {
        if (this.parts == null) {
            this.parts = new ArrayList();
        }
        return this.parts;
    }

    public void setParts(List<PartSummary> parts2) {
        this.parts = parts2;
    }
}
