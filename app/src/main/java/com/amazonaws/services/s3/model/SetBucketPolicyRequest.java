package com.amazonaws.services.s3.model;

import com.amazonaws.AmazonWebServiceRequest;

public class SetBucketPolicyRequest extends AmazonWebServiceRequest {
    private String bucketName;
    private String policyText;

    public SetBucketPolicyRequest(String bucketName2, String policyText2) {
        this.bucketName = bucketName2;
        this.policyText = policyText2;
    }

    public String getBucketName() {
        return this.bucketName;
    }

    public void setBucketName(String bucketName2) {
        this.bucketName = bucketName2;
    }

    public SetBucketPolicyRequest withBucketName(String bucketName2) {
        setBucketName(bucketName2);
        return this;
    }

    public String getPolicyText() {
        return this.policyText;
    }

    public void setPolicyText(String policyText2) {
        this.policyText = policyText2;
    }

    public SetBucketPolicyRequest withPolicyText(String policyText2) {
        setPolicyText(policyText2);
        return this;
    }
}
