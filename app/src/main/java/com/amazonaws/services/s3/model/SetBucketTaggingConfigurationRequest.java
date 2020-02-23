package com.amazonaws.services.s3.model;

import com.amazonaws.AmazonWebServiceRequest;

public class SetBucketTaggingConfigurationRequest extends AmazonWebServiceRequest {
    private String bucketName;
    private BucketTaggingConfiguration taggingConfiguration;

    public SetBucketTaggingConfigurationRequest(String bucketName2, BucketTaggingConfiguration taggingConfiguration2) {
        this.bucketName = bucketName2;
        this.taggingConfiguration = taggingConfiguration2;
    }

    public String getBucketName() {
        return this.bucketName;
    }

    public void setBucketName(String bucketName2) {
        this.bucketName = bucketName2;
    }

    public SetBucketTaggingConfigurationRequest withBucketName(String bucketName2) {
        setBucketName(bucketName2);
        return this;
    }

    public BucketTaggingConfiguration getTaggingConfiguration() {
        return this.taggingConfiguration;
    }

    public void setTaggingConfiguration(BucketTaggingConfiguration taggingConfiguration2) {
        this.taggingConfiguration = taggingConfiguration2;
    }

    public SetBucketTaggingConfigurationRequest withTaggingConfiguration(BucketTaggingConfiguration taggingConfiguration2) {
        setTaggingConfiguration(taggingConfiguration2);
        return this;
    }
}
