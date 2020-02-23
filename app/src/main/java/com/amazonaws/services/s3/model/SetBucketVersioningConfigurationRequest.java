package com.amazonaws.services.s3.model;

import com.amazonaws.AmazonWebServiceRequest;

public class SetBucketVersioningConfigurationRequest extends AmazonWebServiceRequest {
    private String bucketName;
    private MultiFactorAuthentication mfa;
    private BucketVersioningConfiguration versioningConfiguration;

    public SetBucketVersioningConfigurationRequest(String bucketName2, BucketVersioningConfiguration configuration) {
        this.bucketName = bucketName2;
        this.versioningConfiguration = configuration;
    }

    public SetBucketVersioningConfigurationRequest(String bucketName2, BucketVersioningConfiguration configuration, MultiFactorAuthentication mfa2) {
        this(bucketName2, configuration);
        this.mfa = mfa2;
    }

    public String getBucketName() {
        return this.bucketName;
    }

    public void setBucketName(String bucketName2) {
        this.bucketName = bucketName2;
    }

    public SetBucketVersioningConfigurationRequest withBucketName(String bucketName2) {
        setBucketName(bucketName2);
        return this;
    }

    public BucketVersioningConfiguration getVersioningConfiguration() {
        return this.versioningConfiguration;
    }

    public void setVersioningConfiguration(BucketVersioningConfiguration versioningConfiguration2) {
        this.versioningConfiguration = versioningConfiguration2;
    }

    public SetBucketVersioningConfigurationRequest withVersioningConfiguration(BucketVersioningConfiguration versioningConfiguration2) {
        setVersioningConfiguration(versioningConfiguration2);
        return this;
    }

    public MultiFactorAuthentication getMfa() {
        return this.mfa;
    }

    public void setMfa(MultiFactorAuthentication mfa2) {
        this.mfa = mfa2;
    }

    public SetBucketVersioningConfigurationRequest withMfa(MultiFactorAuthentication mfa2) {
        setMfa(mfa2);
        return this;
    }
}
