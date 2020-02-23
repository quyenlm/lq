package com.amazonaws.services.s3.model;

import com.amazonaws.AmazonWebServiceRequest;

public class SetBucketAclRequest extends AmazonWebServiceRequest {
    private AccessControlList acl;
    private String bucketName;
    private CannedAccessControlList cannedAcl;

    public SetBucketAclRequest(String bucketName2, AccessControlList acl2) {
        this.bucketName = bucketName2;
        this.acl = acl2;
        this.cannedAcl = null;
    }

    public SetBucketAclRequest(String bucketName2, CannedAccessControlList acl2) {
        this.bucketName = bucketName2;
        this.acl = null;
        this.cannedAcl = acl2;
    }

    public String getBucketName() {
        return this.bucketName;
    }

    public AccessControlList getAcl() {
        return this.acl;
    }

    public CannedAccessControlList getCannedAcl() {
        return this.cannedAcl;
    }
}
