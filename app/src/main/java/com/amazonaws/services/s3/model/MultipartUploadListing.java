package com.amazonaws.services.s3.model;

import java.util.ArrayList;
import java.util.List;

public class MultipartUploadListing {
    private String bucketName;
    private List<String> commonPrefixes = new ArrayList();
    private String delimiter;
    private String encodingType;
    private boolean isTruncated;
    private String keyMarker;
    private int maxUploads;
    private List<MultipartUpload> multipartUploads;
    private String nextKeyMarker;
    private String nextUploadIdMarker;
    private String prefix;
    private String uploadIdMarker;

    public String getBucketName() {
        return this.bucketName;
    }

    public void setBucketName(String bucketName2) {
        this.bucketName = bucketName2;
    }

    public String getKeyMarker() {
        return this.keyMarker;
    }

    public void setKeyMarker(String keyMarker2) {
        this.keyMarker = keyMarker2;
    }

    public String getUploadIdMarker() {
        return this.uploadIdMarker;
    }

    public void setUploadIdMarker(String uploadIdMarker2) {
        this.uploadIdMarker = uploadIdMarker2;
    }

    public String getNextKeyMarker() {
        return this.nextKeyMarker;
    }

    public void setNextKeyMarker(String nextKeyMarker2) {
        this.nextKeyMarker = nextKeyMarker2;
    }

    public String getNextUploadIdMarker() {
        return this.nextUploadIdMarker;
    }

    public void setNextUploadIdMarker(String nextUploadIdMarker2) {
        this.nextUploadIdMarker = nextUploadIdMarker2;
    }

    public int getMaxUploads() {
        return this.maxUploads;
    }

    public void setMaxUploads(int maxUploads2) {
        this.maxUploads = maxUploads2;
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

    public List<MultipartUpload> getMultipartUploads() {
        if (this.multipartUploads == null) {
            this.multipartUploads = new ArrayList();
        }
        return this.multipartUploads;
    }

    public void setMultipartUploads(List<MultipartUpload> multipartUploads2) {
        this.multipartUploads = multipartUploads2;
    }

    public List<String> getCommonPrefixes() {
        return this.commonPrefixes;
    }

    public void setCommonPrefixes(List<String> commonPrefixes2) {
        this.commonPrefixes = commonPrefixes2;
    }

    public String getDelimiter() {
        return this.delimiter;
    }

    public void setDelimiter(String delimiter2) {
        this.delimiter = delimiter2;
    }

    public String getPrefix() {
        return this.prefix;
    }

    public void setPrefix(String prefix2) {
        this.prefix = prefix2;
    }
}
