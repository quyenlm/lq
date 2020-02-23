package com.amazonaws.services.s3.model;

import com.amazonaws.AmazonWebServiceRequest;

public class SetBucketNotificationConfigurationRequest extends AmazonWebServiceRequest {
    private String bucketName;
    private BucketNotificationConfiguration notificationConfiguration;

    @Deprecated
    public SetBucketNotificationConfigurationRequest(BucketNotificationConfiguration bucketNotificationConfiguration, String bucket) {
        this.notificationConfiguration = bucketNotificationConfiguration;
        this.bucketName = bucket;
    }

    public SetBucketNotificationConfigurationRequest(String bucketName2, BucketNotificationConfiguration notificationConfiguration2) {
        this.bucketName = bucketName2;
        this.notificationConfiguration = notificationConfiguration2;
    }

    @Deprecated
    public BucketNotificationConfiguration getBucketNotificationConfiguration() {
        return this.notificationConfiguration;
    }

    public BucketNotificationConfiguration getNotificationConfiguration() {
        return this.notificationConfiguration;
    }

    @Deprecated
    public void setBucketNotificationConfiguration(BucketNotificationConfiguration bucketNotificationConfiguration) {
        this.notificationConfiguration = bucketNotificationConfiguration;
    }

    public void setNotificationConfiguration(BucketNotificationConfiguration notificationConfiguration2) {
        this.notificationConfiguration = notificationConfiguration2;
    }

    public SetBucketNotificationConfigurationRequest withNotificationConfiguration(BucketNotificationConfiguration notificationConfiguration2) {
        setNotificationConfiguration(notificationConfiguration2);
        return this;
    }

    @Deprecated
    public String getBucket() {
        return this.bucketName;
    }

    public String getBucketName() {
        return this.bucketName;
    }

    @Deprecated
    public void setBucket(String bucket) {
        this.bucketName = bucket;
    }

    public void setBucketName(String bucketName2) {
        this.bucketName = bucketName2;
    }

    public SetBucketNotificationConfigurationRequest withBucketName(String bucketName2) {
        setBucketName(bucketName2);
        return this;
    }
}
