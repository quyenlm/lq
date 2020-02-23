package com.amazonaws.services.s3.model;

import com.amazonaws.AmazonWebServiceRequest;
import com.amazonaws.event.ProgressListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GetObjectRequest extends AmazonWebServiceRequest {
    private String bucketName;
    private ProgressListener generalProgressListener;
    private boolean isRequesterPays;
    private String key;
    private List<String> matchingETagConstraints;
    private Date modifiedSinceConstraint;
    private List<String> nonmatchingEtagConstraints;
    private long[] range;
    private ResponseHeaderOverrides responseHeaders;
    private SSECustomerKey sseCustomerKey;
    private Date unmodifiedSinceConstraint;
    private String versionId;

    public GetObjectRequest(String bucketName2, String key2) {
        this(bucketName2, key2, (String) null);
    }

    public GetObjectRequest(String bucketName2, String key2, String versionId2) {
        this.matchingETagConstraints = new ArrayList();
        this.nonmatchingEtagConstraints = new ArrayList();
        setBucketName(bucketName2);
        setKey(key2);
        setVersionId(versionId2);
        setRequesterPays(false);
    }

    public GetObjectRequest(String bucketName2, String key2, boolean isRequesterPays2) {
        this.matchingETagConstraints = new ArrayList();
        this.nonmatchingEtagConstraints = new ArrayList();
        this.bucketName = bucketName2;
        this.key = key2;
        this.isRequesterPays = isRequesterPays2;
    }

    public String getBucketName() {
        return this.bucketName;
    }

    public void setBucketName(String bucketName2) {
        this.bucketName = bucketName2;
    }

    public GetObjectRequest withBucketName(String bucketName2) {
        setBucketName(bucketName2);
        return this;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key2) {
        this.key = key2;
    }

    public GetObjectRequest withKey(String key2) {
        setKey(key2);
        return this;
    }

    public String getVersionId() {
        return this.versionId;
    }

    public void setVersionId(String versionId2) {
        this.versionId = versionId2;
    }

    public GetObjectRequest withVersionId(String versionId2) {
        setVersionId(versionId2);
        return this;
    }

    public long[] getRange() {
        if (this.range == null) {
            return null;
        }
        return (long[]) this.range.clone();
    }

    public void setRange(long start, long end) {
        this.range = new long[]{start, end};
    }

    public GetObjectRequest withRange(long start, long end) {
        setRange(start, end);
        return this;
    }

    public List<String> getMatchingETagConstraints() {
        return this.matchingETagConstraints;
    }

    public void setMatchingETagConstraints(List<String> eTagList) {
        this.matchingETagConstraints = eTagList;
    }

    public GetObjectRequest withMatchingETagConstraint(String eTag) {
        this.matchingETagConstraints.add(eTag);
        return this;
    }

    public List<String> getNonmatchingETagConstraints() {
        return this.nonmatchingEtagConstraints;
    }

    public void setNonmatchingETagConstraints(List<String> eTagList) {
        this.nonmatchingEtagConstraints = eTagList;
    }

    public GetObjectRequest withNonmatchingETagConstraint(String eTag) {
        this.nonmatchingEtagConstraints.add(eTag);
        return this;
    }

    public Date getUnmodifiedSinceConstraint() {
        return this.unmodifiedSinceConstraint;
    }

    public void setUnmodifiedSinceConstraint(Date date) {
        this.unmodifiedSinceConstraint = date;
    }

    public GetObjectRequest withUnmodifiedSinceConstraint(Date date) {
        setUnmodifiedSinceConstraint(date);
        return this;
    }

    public Date getModifiedSinceConstraint() {
        return this.modifiedSinceConstraint;
    }

    public void setModifiedSinceConstraint(Date date) {
        this.modifiedSinceConstraint = date;
    }

    public GetObjectRequest withModifiedSinceConstraint(Date date) {
        setModifiedSinceConstraint(date);
        return this;
    }

    public ResponseHeaderOverrides getResponseHeaders() {
        return this.responseHeaders;
    }

    public void setResponseHeaders(ResponseHeaderOverrides responseHeaders2) {
        this.responseHeaders = responseHeaders2;
    }

    public GetObjectRequest withResponseHeaders(ResponseHeaderOverrides responseHeaders2) {
        setResponseHeaders(responseHeaders2);
        return this;
    }

    @Deprecated
    public void setProgressListener(ProgressListener progressListener) {
        this.generalProgressListener = new LegacyS3ProgressListener(progressListener);
    }

    @Deprecated
    public ProgressListener getProgressListener() {
        if (this.generalProgressListener instanceof LegacyS3ProgressListener) {
            return ((LegacyS3ProgressListener) this.generalProgressListener).unwrap();
        }
        return null;
    }

    @Deprecated
    public GetObjectRequest withProgressListener(ProgressListener progressListener) {
        setProgressListener(progressListener);
        return this;
    }

    public void setGeneralProgressListener(ProgressListener generalProgressListener2) {
        this.generalProgressListener = generalProgressListener2;
    }

    public ProgressListener getGeneralProgressListener() {
        return this.generalProgressListener;
    }

    public GetObjectRequest withGeneralProgressListener(ProgressListener progressListener) {
        setGeneralProgressListener(progressListener);
        return this;
    }

    public boolean isRequesterPays() {
        return this.isRequesterPays;
    }

    public void setRequesterPays(boolean isRequesterPays2) {
        this.isRequesterPays = isRequesterPays2;
    }

    public SSECustomerKey getSSECustomerKey() {
        return this.sseCustomerKey;
    }

    public void setSSECustomerKey(SSECustomerKey sseKey) {
        this.sseCustomerKey = sseKey;
    }

    public GetObjectRequest withSSECustomerKey(SSECustomerKey sseKey) {
        setSSECustomerKey(sseKey);
        return this;
    }
}
