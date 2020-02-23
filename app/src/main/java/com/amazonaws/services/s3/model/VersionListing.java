package com.amazonaws.services.s3.model;

import java.util.ArrayList;
import java.util.List;

public class VersionListing {
    private String bucketName;
    private List<String> commonPrefixes = new ArrayList();
    private String delimiter;
    private String encodingType;
    private boolean isTruncated;
    private String keyMarker;
    private int maxKeys;
    private String nextKeyMarker;
    private String nextVersionIdMarker;
    private String prefix;
    private String versionIdMarker;
    private List<S3VersionSummary> versionSummaries = new ArrayList();

    public List<S3VersionSummary> getVersionSummaries() {
        return this.versionSummaries;
    }

    public void setVersionSummaries(List<S3VersionSummary> versionSummaries2) {
        this.versionSummaries = versionSummaries2;
    }

    public List<String> getCommonPrefixes() {
        return this.commonPrefixes;
    }

    public void setCommonPrefixes(List<String> commonPrefixes2) {
        this.commonPrefixes = commonPrefixes2;
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

    public String getKeyMarker() {
        return this.keyMarker;
    }

    public void setKeyMarker(String keyMarker2) {
        this.keyMarker = keyMarker2;
    }

    public String getVersionIdMarker() {
        return this.versionIdMarker;
    }

    public void setVersionIdMarker(String versionIdMarker2) {
        this.versionIdMarker = versionIdMarker2;
    }

    public int getMaxKeys() {
        return this.maxKeys;
    }

    public void setMaxKeys(int maxKeys2) {
        this.maxKeys = maxKeys2;
    }

    public String getDelimiter() {
        return this.delimiter;
    }

    public void setDelimiter(String delimiter2) {
        this.delimiter = delimiter2;
    }

    public String getNextKeyMarker() {
        return this.nextKeyMarker;
    }

    public void setNextKeyMarker(String marker) {
        this.nextKeyMarker = marker;
    }

    public String getNextVersionIdMarker() {
        return this.nextVersionIdMarker;
    }

    public void setNextVersionIdMarker(String marker) {
        this.nextVersionIdMarker = marker;
    }

    public boolean isTruncated() {
        return this.isTruncated;
    }

    public void setTruncated(boolean isTruncated2) {
        this.isTruncated = isTruncated2;
    }

    public String getEncodingType() {
        return this.encodingType;
    }

    public void setEncodingType(String encodingType2) {
        this.encodingType = encodingType2;
    }
}
