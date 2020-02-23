package com.amazonaws.services.s3.model;

import java.util.ArrayList;
import java.util.List;

public class ListObjectsV2Result {
    private String bucketName;
    private List<String> commonPrefixes = new ArrayList();
    private String continuationToken;
    private String delimiter;
    private String encodingType;
    private boolean isTruncated;
    private int keyCount;
    private int maxKeys;
    private String nextContinuationToken;
    private List<S3ObjectSummary> objectSummaries = new ArrayList();
    private String prefix;
    private String startAfter;

    public boolean isTruncated() {
        return this.isTruncated;
    }

    public void setTruncated(boolean isTruncated2) {
        this.isTruncated = isTruncated2;
    }

    public String getBucketName() {
        return this.bucketName;
    }

    public void setBucketName(String bucketName2) {
        this.bucketName = bucketName2;
    }

    public String getPrefix() {
        return this.prefix;
    }

    public void setPrefix(String prefix2) {
        this.prefix = prefix2;
    }

    public String getDelimiter() {
        return this.delimiter;
    }

    public void setDelimiter(String delimiter2) {
        this.delimiter = delimiter2;
    }

    public String getEncodingType() {
        return this.encodingType;
    }

    public void setEncodingType(String encodingType2) {
        this.encodingType = encodingType2;
    }

    public String getContinuationToken() {
        return this.continuationToken;
    }

    public void setContinuationToken(String continuationToken2) {
        this.continuationToken = continuationToken2;
    }

    public String getNextContinuationToken() {
        return this.nextContinuationToken;
    }

    public void setNextContinuationToken(String nextContinuationToken2) {
        this.nextContinuationToken = nextContinuationToken2;
    }

    public int getKeyCount() {
        return this.keyCount;
    }

    public void setKeyCount(int keyCount2) {
        this.keyCount = keyCount2;
    }

    public int getMaxKeys() {
        return this.maxKeys;
    }

    public void setMaxKeys(int maxKeys2) {
        this.maxKeys = maxKeys2;
    }

    public String getStartAfter() {
        return this.startAfter;
    }

    public void setStartAfter(String startAfter2) {
        this.startAfter = startAfter2;
    }

    public List<S3ObjectSummary> getObjectSummaries() {
        return this.objectSummaries;
    }

    public List<String> getCommonPrefixes() {
        return this.commonPrefixes;
    }

    public void setCommonPrefixes(List<String> commonPrefixes2) {
        this.commonPrefixes = commonPrefixes2;
    }
}
