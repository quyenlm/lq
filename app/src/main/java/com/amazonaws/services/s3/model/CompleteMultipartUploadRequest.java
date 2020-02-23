package com.amazonaws.services.s3.model;

import com.amazonaws.AmazonWebServiceRequest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CompleteMultipartUploadRequest extends AmazonWebServiceRequest {
    private String bucketName;
    private String key;
    private List<PartETag> partETags = new ArrayList();
    private String uploadId;

    public CompleteMultipartUploadRequest(String bucketName2, String key2, String uploadId2, List<PartETag> partETags2) {
        this.bucketName = bucketName2;
        this.key = key2;
        this.uploadId = uploadId2;
        this.partETags = partETags2;
    }

    public String getBucketName() {
        return this.bucketName;
    }

    public void setBucketName(String bucketName2) {
        this.bucketName = bucketName2;
    }

    public CompleteMultipartUploadRequest withBucketName(String bucketName2) {
        this.bucketName = bucketName2;
        return this;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key2) {
        this.key = key2;
    }

    public CompleteMultipartUploadRequest withKey(String key2) {
        this.key = key2;
        return this;
    }

    public String getUploadId() {
        return this.uploadId;
    }

    public void setUploadId(String uploadId2) {
        this.uploadId = uploadId2;
    }

    public CompleteMultipartUploadRequest withUploadId(String uploadId2) {
        this.uploadId = uploadId2;
        return this;
    }

    public List<PartETag> getPartETags() {
        return this.partETags;
    }

    public void setPartETags(List<PartETag> partETags2) {
        this.partETags = partETags2;
    }

    public CompleteMultipartUploadRequest withPartETags(List<PartETag> partETags2) {
        setPartETags(partETags2);
        return this;
    }

    public CompleteMultipartUploadRequest withPartETags(UploadPartResult... uploadPartResults) {
        for (UploadPartResult result : uploadPartResults) {
            this.partETags.add(new PartETag(result.getPartNumber(), result.getETag()));
        }
        return this;
    }

    public CompleteMultipartUploadRequest withPartETags(Collection<UploadPartResult> uploadPartResultsCollection) {
        for (UploadPartResult result : uploadPartResultsCollection) {
            this.partETags.add(new PartETag(result.getPartNumber(), result.getETag()));
        }
        return this;
    }
}
