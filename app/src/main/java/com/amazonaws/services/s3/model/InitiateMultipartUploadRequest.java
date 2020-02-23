package com.amazonaws.services.s3.model;

import com.amazonaws.AmazonWebServiceRequest;

public class InitiateMultipartUploadRequest extends AmazonWebServiceRequest {
    private AccessControlList accessControlList;
    private String bucketName;
    private CannedAccessControlList cannedACL;
    private String key;
    public ObjectMetadata objectMetadata;
    private String redirectLocation;
    private SSECustomerKey sseCustomerKey;
    private StorageClass storageClass;

    public InitiateMultipartUploadRequest(String bucketName2, String key2) {
        this.bucketName = bucketName2;
        this.key = key2;
    }

    public InitiateMultipartUploadRequest(String bucketName2, String key2, ObjectMetadata objectMetadata2) {
        this.bucketName = bucketName2;
        this.key = key2;
        this.objectMetadata = objectMetadata2;
    }

    public String getBucketName() {
        return this.bucketName;
    }

    public void setBucketName(String bucketName2) {
        this.bucketName = bucketName2;
    }

    public InitiateMultipartUploadRequest withBucketName(String bucketName2) {
        this.bucketName = bucketName2;
        return this;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key2) {
        this.key = key2;
    }

    public InitiateMultipartUploadRequest withKey(String key2) {
        this.key = key2;
        return this;
    }

    public CannedAccessControlList getCannedACL() {
        return this.cannedACL;
    }

    public void setCannedACL(CannedAccessControlList cannedACL2) {
        this.cannedACL = cannedACL2;
    }

    public InitiateMultipartUploadRequest withCannedACL(CannedAccessControlList acl) {
        this.cannedACL = acl;
        return this;
    }

    public AccessControlList getAccessControlList() {
        return this.accessControlList;
    }

    public void setAccessControlList(AccessControlList accessControlList2) {
        this.accessControlList = accessControlList2;
    }

    public InitiateMultipartUploadRequest withAccessControlList(AccessControlList accessControlList2) {
        setAccessControlList(accessControlList2);
        return this;
    }

    public StorageClass getStorageClass() {
        return this.storageClass;
    }

    public void setStorageClass(StorageClass storageClass2) {
        this.storageClass = storageClass2;
    }

    public InitiateMultipartUploadRequest withStorageClass(StorageClass storageClass2) {
        this.storageClass = storageClass2;
        return this;
    }

    public ObjectMetadata getObjectMetadata() {
        return this.objectMetadata;
    }

    public void setObjectMetadata(ObjectMetadata objectMetadata2) {
        this.objectMetadata = objectMetadata2;
    }

    public InitiateMultipartUploadRequest withObjectMetadata(ObjectMetadata objectMetadata2) {
        setObjectMetadata(objectMetadata2);
        return this;
    }

    public void setRedirectLocation(String redirectLocation2) {
        this.redirectLocation = redirectLocation2;
    }

    public String getRedirectLocation() {
        return this.redirectLocation;
    }

    public InitiateMultipartUploadRequest withRedirectLocation(String redirectLocation2) {
        this.redirectLocation = redirectLocation2;
        return this;
    }

    public SSECustomerKey getSSECustomerKey() {
        return this.sseCustomerKey;
    }

    public void setSSECustomerKey(SSECustomerKey sseKey) {
        this.sseCustomerKey = sseKey;
    }

    public InitiateMultipartUploadRequest withSSECustomerKey(SSECustomerKey sseKey) {
        setSSECustomerKey(sseKey);
        return this;
    }
}
