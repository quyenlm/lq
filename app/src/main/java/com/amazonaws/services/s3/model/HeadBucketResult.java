package com.amazonaws.services.s3.model;

public class HeadBucketResult {
    private String bucketRegion;

    public String getBucketRegion() {
        return this.bucketRegion;
    }

    public void setBucketRegion(String bucketRegion2) {
        this.bucketRegion = bucketRegion2;
    }

    public HeadBucketResult withBucketRegion(String bucketRegion2) {
        setBucketRegion(bucketRegion2);
        return this;
    }
}
