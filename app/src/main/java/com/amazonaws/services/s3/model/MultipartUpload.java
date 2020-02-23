package com.amazonaws.services.s3.model;

import java.util.Date;

public class MultipartUpload {
    private Date initiated;
    private Owner initiator;
    private String key;
    private Owner owner;
    private String storageClass;
    private String uploadId;

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

    public Date getInitiated() {
        return this.initiated;
    }

    public void setInitiated(Date initiated2) {
        this.initiated = initiated2;
    }
}
