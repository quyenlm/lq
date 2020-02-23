package com.amazonaws.services.s3.model;

import com.amazonaws.AmazonWebServiceRequest;

public class ListPartsRequest extends AmazonWebServiceRequest {
    private String bucketName;
    private String encodingType;
    private String key;
    private Integer maxParts;
    private Integer partNumberMarker;
    private String uploadId;

    public ListPartsRequest(String bucketName2, String key2, String uploadId2) {
        this.bucketName = bucketName2;
        this.key = key2;
        this.uploadId = uploadId2;
    }

    public String getBucketName() {
        return this.bucketName;
    }

    public void setBucketName(String bucketName2) {
        this.bucketName = bucketName2;
    }

    public ListPartsRequest withBucketName(String bucketName2) {
        this.bucketName = bucketName2;
        return this;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key2) {
        this.key = key2;
    }

    public ListPartsRequest withKey(String key2) {
        this.key = key2;
        return this;
    }

    public String getUploadId() {
        return this.uploadId;
    }

    public void setUploadId(String uploadId2) {
        this.uploadId = uploadId2;
    }

    public ListPartsRequest withUploadId(String uploadId2) {
        this.uploadId = uploadId2;
        return this;
    }

    public Integer getMaxParts() {
        return this.maxParts;
    }

    public void setMaxParts(int maxParts2) {
        this.maxParts = Integer.valueOf(maxParts2);
    }

    public ListPartsRequest withMaxParts(int maxParts2) {
        this.maxParts = Integer.valueOf(maxParts2);
        return this;
    }

    public Integer getPartNumberMarker() {
        return this.partNumberMarker;
    }

    public void setPartNumberMarker(Integer partNumberMarker2) {
        this.partNumberMarker = partNumberMarker2;
    }

    public ListPartsRequest withPartNumberMarker(Integer partNumberMarker2) {
        this.partNumberMarker = partNumberMarker2;
        return this;
    }

    public String getEncodingType() {
        return this.encodingType;
    }

    public void setEncodingType(String encodingType2) {
        this.encodingType = encodingType2;
    }

    public ListPartsRequest withEncodingType(String encodingType2) {
        setEncodingType(encodingType2);
        return this;
    }
}
