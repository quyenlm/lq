package com.amazonaws.services.s3.model;

import com.amazonaws.AmazonWebServiceRequest;

public class ListVersionsRequest extends AmazonWebServiceRequest {
    private String bucketName;
    private String delimiter;
    private String encodingType;
    private String keyMarker;
    private Integer maxResults;
    private String prefix;
    private String versionIdMarker;

    public ListVersionsRequest() {
    }

    public ListVersionsRequest(String bucketName2, String prefix2, String keyMarker2, String versionIdMarker2, String delimiter2, Integer maxResults2) {
        setBucketName(bucketName2);
        setPrefix(prefix2);
        setKeyMarker(keyMarker2);
        setVersionIdMarker(versionIdMarker2);
        setDelimiter(delimiter2);
        setMaxResults(maxResults2);
    }

    public String getBucketName() {
        return this.bucketName;
    }

    public void setBucketName(String bucketName2) {
        this.bucketName = bucketName2;
    }

    public ListVersionsRequest withBucketName(String bucketName2) {
        setBucketName(bucketName2);
        return this;
    }

    public String getPrefix() {
        return this.prefix;
    }

    public void setPrefix(String prefix2) {
        this.prefix = prefix2;
    }

    public ListVersionsRequest withPrefix(String prefix2) {
        setPrefix(prefix2);
        return this;
    }

    public String getKeyMarker() {
        return this.keyMarker;
    }

    public void setKeyMarker(String keyMarker2) {
        this.keyMarker = keyMarker2;
    }

    public ListVersionsRequest withKeyMarker(String keyMarker2) {
        setKeyMarker(keyMarker2);
        return this;
    }

    public String getVersionIdMarker() {
        return this.versionIdMarker;
    }

    public void setVersionIdMarker(String versionIdMarker2) {
        this.versionIdMarker = versionIdMarker2;
    }

    public ListVersionsRequest withVersionIdMarker(String versionIdMarker2) {
        setVersionIdMarker(versionIdMarker2);
        return this;
    }

    public String getDelimiter() {
        return this.delimiter;
    }

    public void setDelimiter(String delimiter2) {
        this.delimiter = delimiter2;
    }

    public ListVersionsRequest withDelimiter(String delimiter2) {
        setDelimiter(delimiter2);
        return this;
    }

    public Integer getMaxResults() {
        return this.maxResults;
    }

    public void setMaxResults(Integer maxResults2) {
        this.maxResults = maxResults2;
    }

    public ListVersionsRequest withMaxResults(Integer maxResults2) {
        setMaxResults(maxResults2);
        return this;
    }

    public String getEncodingType() {
        return this.encodingType;
    }

    public void setEncodingType(String encodingType2) {
        this.encodingType = encodingType2;
    }

    public ListVersionsRequest withEncodingType(String encodingType2) {
        setEncodingType(encodingType2);
        return this;
    }
}
