package com.amazonaws.services.s3.model;

import com.amazonaws.AmazonWebServiceRequest;

public class GetRequestPaymentConfigurationRequest extends AmazonWebServiceRequest {
    private String bucketName;

    public GetRequestPaymentConfigurationRequest(String bucketName2) {
        this.bucketName = bucketName2;
    }

    public String getBucketName() {
        return this.bucketName;
    }

    public void setBucketName(String bucketName2) {
        this.bucketName = bucketName2;
    }
}
