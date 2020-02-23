package com.amazonaws.services.s3.model;

import com.amazonaws.AmazonWebServiceRequest;

public class CreateBucketRequest extends AmazonWebServiceRequest implements S3AccelerateUnsupported {
    private AccessControlList accessControlList;
    private String bucketName;
    private CannedAccessControlList cannedAcl;
    private String region;

    public CreateBucketRequest(String bucketName2) {
        this(bucketName2, Region.US_Standard);
    }

    public CreateBucketRequest(String bucketName2, Region region2) {
        this(bucketName2, region2.toString());
    }

    public CreateBucketRequest(String bucketName2, String region2) {
        setBucketName(bucketName2);
        setRegion(region2);
    }

    public void setBucketName(String bucketName2) {
        this.bucketName = bucketName2;
    }

    public String getBucketName() {
        return this.bucketName;
    }

    public void setRegion(String region2) {
        this.region = region2;
    }

    public String getRegion() {
        return this.region;
    }

    public CannedAccessControlList getCannedAcl() {
        return this.cannedAcl;
    }

    public void setCannedAcl(CannedAccessControlList cannedAcl2) {
        this.cannedAcl = cannedAcl2;
    }

    public CreateBucketRequest withCannedAcl(CannedAccessControlList cannedAcl2) {
        setCannedAcl(cannedAcl2);
        return this;
    }

    public AccessControlList getAccessControlList() {
        return this.accessControlList;
    }

    public void setAccessControlList(AccessControlList accessControlList2) {
        this.accessControlList = accessControlList2;
    }

    public CreateBucketRequest withAccessControlList(AccessControlList accessControlList2) {
        setAccessControlList(accessControlList2);
        return this;
    }
}
