package com.amazonaws.services.s3.model;

import com.amazonaws.AmazonWebServiceRequest;

public class ListObjectsRequest extends AmazonWebServiceRequest {
    private String bucketName;
    private String delimiter;
    private String encodingType;
    private String marker;
    private Integer maxKeys;
    private String prefix;

    public ListObjectsRequest() {
    }

    public ListObjectsRequest(String bucketName2, String prefix2, String marker2, String delimiter2, Integer maxKeys2) {
        setBucketName(bucketName2);
        setPrefix(prefix2);
        setMarker(marker2);
        setDelimiter(delimiter2);
        setMaxKeys(maxKeys2);
    }

    public String getBucketName() {
        return this.bucketName;
    }

    public void setBucketName(String bucketName2) {
        this.bucketName = bucketName2;
    }

    public ListObjectsRequest withBucketName(String bucketName2) {
        setBucketName(bucketName2);
        return this;
    }

    public String getPrefix() {
        return this.prefix;
    }

    public void setPrefix(String prefix2) {
        this.prefix = prefix2;
    }

    public ListObjectsRequest withPrefix(String prefix2) {
        setPrefix(prefix2);
        return this;
    }

    public String getMarker() {
        return this.marker;
    }

    public void setMarker(String marker2) {
        this.marker = marker2;
    }

    public ListObjectsRequest withMarker(String marker2) {
        setMarker(marker2);
        return this;
    }

    public String getDelimiter() {
        return this.delimiter;
    }

    public void setDelimiter(String delimiter2) {
        this.delimiter = delimiter2;
    }

    public ListObjectsRequest withDelimiter(String delimiter2) {
        setDelimiter(delimiter2);
        return this;
    }

    public Integer getMaxKeys() {
        return this.maxKeys;
    }

    public void setMaxKeys(Integer maxKeys2) {
        this.maxKeys = maxKeys2;
    }

    public ListObjectsRequest withMaxKeys(Integer maxKeys2) {
        setMaxKeys(maxKeys2);
        return this;
    }

    public String getEncodingType() {
        return this.encodingType;
    }

    public void setEncodingType(String encodingType2) {
        this.encodingType = encodingType2;
    }

    public ListObjectsRequest withEncodingType(String encodingType2) {
        setEncodingType(encodingType2);
        return this;
    }
}
