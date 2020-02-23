package com.amazonaws.services.s3.model;

import com.amazonaws.AmazonWebServiceRequest;

public class ListObjectsV2Request extends AmazonWebServiceRequest {
    private String bucketName;
    private String continuationToken;
    private String delimiter;
    private String encodingType;
    private boolean fetchOwner;
    private Integer maxKeys;
    private String prefix;
    private String startAfter;

    public String getBucketName() {
        return this.bucketName;
    }

    public void setBucketName(String bucketName2) {
        this.bucketName = bucketName2;
    }

    public ListObjectsV2Request withBucketName(String bucketName2) {
        setBucketName(bucketName2);
        return this;
    }

    public String getDelimiter() {
        return this.delimiter;
    }

    public void setDelimiter(String delimiter2) {
        this.delimiter = delimiter2;
    }

    public ListObjectsV2Request withDelimiter(String delimiter2) {
        setDelimiter(delimiter2);
        return this;
    }

    public String getEncodingType() {
        return this.encodingType;
    }

    public void setEncodingType(String encodingType2) {
        this.encodingType = encodingType2;
    }

    public ListObjectsV2Request withEncodingType(String encodingType2) {
        setEncodingType(encodingType2);
        return this;
    }

    public Integer getMaxKeys() {
        return this.maxKeys;
    }

    public void setMaxKeys(Integer maxKeys2) {
        this.maxKeys = maxKeys2;
    }

    public ListObjectsV2Request withMaxKeys(Integer maxKeys2) {
        setMaxKeys(maxKeys2);
        return this;
    }

    public String getPrefix() {
        return this.prefix;
    }

    public void setPrefix(String prefix2) {
        this.prefix = prefix2;
    }

    public ListObjectsV2Request withPrefix(String prefix2) {
        setPrefix(prefix2);
        return this;
    }

    public String getContinuationToken() {
        return this.continuationToken;
    }

    public void setContinuationToken(String continuationToken2) {
        this.continuationToken = continuationToken2;
    }

    public ListObjectsV2Request withContinuationToken(String continuationToken2) {
        setContinuationToken(continuationToken2);
        return this;
    }

    public boolean isFetchOwner() {
        return this.fetchOwner;
    }

    public void setFetchOwner(boolean fetchOwner2) {
        this.fetchOwner = fetchOwner2;
    }

    public ListObjectsV2Request withFetchOwner(boolean fetchOwner2) {
        setFetchOwner(fetchOwner2);
        return this;
    }

    public String getStartAfter() {
        return this.startAfter;
    }

    public void setStartAfter(String startAfter2) {
        this.startAfter = startAfter2;
    }

    public ListObjectsV2Request withStartAfter(String startAfter2) {
        setStartAfter(startAfter2);
        return this;
    }
}
