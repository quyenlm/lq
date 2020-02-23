package com.amazonaws.services.s3.model;

import com.amazonaws.AmazonServiceException;

public class AmazonS3Exception extends AmazonServiceException {
    private static final long serialVersionUID = 7573680383273658477L;
    private String cloudFrontId;
    private String extendedRequestId;

    public AmazonS3Exception(String message) {
        super(message);
    }

    public AmazonS3Exception(String message, Exception cause) {
        super(message, cause);
    }

    public String getExtendedRequestId() {
        return this.extendedRequestId;
    }

    public void setExtendedRequestId(String extendedRequestId2) {
        this.extendedRequestId = extendedRequestId2;
    }

    public String getCloudFrontId() {
        return this.cloudFrontId;
    }

    public void setCloudFrontId(String cloudFrontId2) {
        this.cloudFrontId = cloudFrontId2;
    }

    public String toString() {
        return super.toString() + ", S3 Extended Request ID: " + getExtendedRequestId();
    }
}
