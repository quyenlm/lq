package com.amazonaws.services.s3.model;

import com.amazonaws.AmazonWebServiceRequest;

public class ListMultipartUploadsRequest extends AmazonWebServiceRequest {
    private String bucketName;
    private String delimiter;
    private String encodingType;
    private String keyMarker;
    private Integer maxUploads;
    private String prefix;
    private String uploadIdMarker;

    public ListMultipartUploadsRequest(String bucketName2) {
        this.bucketName = bucketName2;
    }

    public String getBucketName() {
        return this.bucketName;
    }

    public void setBucketName(String bucketName2) {
        this.bucketName = bucketName2;
    }

    public ListMultipartUploadsRequest withBucketName(String bucketName2) {
        this.bucketName = bucketName2;
        return this;
    }

    public Integer getMaxUploads() {
        return this.maxUploads;
    }

    public void setMaxUploads(Integer maxUploads2) {
        this.maxUploads = maxUploads2;
    }

    public ListMultipartUploadsRequest withMaxUploads(int maxUploadsInt) {
        this.maxUploads = Integer.valueOf(maxUploadsInt);
        return this;
    }

    public String getKeyMarker() {
        return this.keyMarker;
    }

    public void setKeyMarker(String keyMarker2) {
        this.keyMarker = keyMarker2;
    }

    public ListMultipartUploadsRequest withKeyMarker(String keyMarker2) {
        this.keyMarker = keyMarker2;
        return this;
    }

    public String getUploadIdMarker() {
        return this.uploadIdMarker;
    }

    public void setUploadIdMarker(String uploadIdMarker2) {
        this.uploadIdMarker = uploadIdMarker2;
    }

    public ListMultipartUploadsRequest withUploadIdMarker(String uploadIdMarker2) {
        this.uploadIdMarker = uploadIdMarker2;
        return this;
    }

    public String getDelimiter() {
        return this.delimiter;
    }

    public void setDelimiter(String delimiter2) {
        this.delimiter = delimiter2;
    }

    public ListMultipartUploadsRequest withDelimiter(String delimiter2) {
        setDelimiter(delimiter2);
        return this;
    }

    public String getPrefix() {
        return this.prefix;
    }

    public void setPrefix(String prefix2) {
        this.prefix = prefix2;
    }

    public ListMultipartUploadsRequest withPrefix(String prefix2) {
        setPrefix(prefix2);
        return this;
    }

    public String getEncodingType() {
        return this.encodingType;
    }

    public void setEncodingType(String encodingType2) {
        this.encodingType = encodingType2;
    }

    public ListMultipartUploadsRequest withEncodingType(String encodingType2) {
        setEncodingType(encodingType2);
        return this;
    }
}
