package com.amazonaws.services.s3.model;

import com.amazonaws.AmazonWebServiceRequest;

public class SetRequestPaymentConfigurationRequest extends AmazonWebServiceRequest {
    private String bucketName;
    private RequestPaymentConfiguration configuration;

    public SetRequestPaymentConfigurationRequest(String bucketName2, RequestPaymentConfiguration configuration2) {
        setBucketName(bucketName2);
        this.configuration = configuration2;
    }

    public RequestPaymentConfiguration getConfiguration() {
        return this.configuration;
    }

    public void setConfiguration(RequestPaymentConfiguration configuration2) {
        this.configuration = configuration2;
    }

    public String getBucketName() {
        return this.bucketName;
    }

    public void setBucketName(String bucketName2) {
        this.bucketName = bucketName2;
    }
}
