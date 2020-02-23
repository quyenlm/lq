package com.amazonaws.services.s3.model;

public class ReplicationDestinationConfig {
    private String bucketARN;
    private String storageClass;

    public String getBucketARN() {
        return this.bucketARN;
    }

    public void setBucketARN(String bucketARN2) {
        if (bucketARN2 == null) {
            throw new IllegalArgumentException("Bucket name cannot be null");
        }
        this.bucketARN = bucketARN2;
    }

    public ReplicationDestinationConfig withBucketARN(String bucketARN2) {
        setBucketARN(bucketARN2);
        return this;
    }

    public void setStorageClass(String storageClass2) {
        this.storageClass = storageClass2;
    }

    public void setStorageClass(StorageClass storageClass2) {
        setStorageClass(storageClass2 == null ? null : storageClass2.toString());
    }

    public ReplicationDestinationConfig withStorageClass(String storageClass2) {
        setStorageClass(storageClass2);
        return this;
    }

    public ReplicationDestinationConfig withStorageClass(StorageClass storageClass2) {
        setStorageClass(storageClass2 == null ? null : storageClass2.toString());
        return this;
    }

    public String getStorageClass() {
        return this.storageClass;
    }
}
