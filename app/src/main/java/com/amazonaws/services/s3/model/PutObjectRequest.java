package com.amazonaws.services.s3.model;

import com.amazonaws.AmazonWebServiceRequest;
import com.amazonaws.event.ProgressListener;
import java.io.File;
import java.io.InputStream;

public class PutObjectRequest extends AmazonWebServiceRequest implements Cloneable {
    private AccessControlList accessControlList;
    private String bucketName;
    private CannedAccessControlList cannedAcl;
    private File file;
    private ProgressListener generalProgressListener;
    private InputStream inputStream;
    private String key;
    private ObjectMetadata metadata;
    private String redirectLocation;
    private SSECustomerKey sseCustomerKey;
    private String storageClass;

    public PutObjectRequest(String bucketName2, String key2, File file2) {
        this.bucketName = bucketName2;
        this.key = key2;
        this.file = file2;
    }

    public PutObjectRequest(String bucketName2, String key2, String redirectLocation2) {
        this.bucketName = bucketName2;
        this.key = key2;
        this.redirectLocation = redirectLocation2;
    }

    public PutObjectRequest(String bucketName2, String key2, InputStream input, ObjectMetadata metadata2) {
        this.bucketName = bucketName2;
        this.key = key2;
        this.inputStream = input;
        this.metadata = metadata2;
    }

    public String getBucketName() {
        return this.bucketName;
    }

    public void setBucketName(String bucketName2) {
        this.bucketName = bucketName2;
    }

    public PutObjectRequest withBucketName(String bucketName2) {
        setBucketName(bucketName2);
        return this;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key2) {
        this.key = key2;
    }

    public PutObjectRequest withKey(String key2) {
        setKey(key2);
        return this;
    }

    public String getStorageClass() {
        return this.storageClass;
    }

    public void setStorageClass(String storageClass2) {
        this.storageClass = storageClass2;
    }

    public PutObjectRequest withStorageClass(String storageClass2) {
        setStorageClass(storageClass2);
        return this;
    }

    public void setStorageClass(StorageClass storageClass2) {
        this.storageClass = storageClass2.toString();
    }

    public PutObjectRequest withStorageClass(StorageClass storageClass2) {
        setStorageClass(storageClass2);
        return this;
    }

    public File getFile() {
        return this.file;
    }

    public void setFile(File file2) {
        this.file = file2;
    }

    public PutObjectRequest withFile(File file2) {
        setFile(file2);
        return this;
    }

    public ObjectMetadata getMetadata() {
        return this.metadata;
    }

    public void setMetadata(ObjectMetadata metadata2) {
        this.metadata = metadata2;
    }

    public PutObjectRequest withMetadata(ObjectMetadata metadata2) {
        setMetadata(metadata2);
        return this;
    }

    public CannedAccessControlList getCannedAcl() {
        return this.cannedAcl;
    }

    public void setCannedAcl(CannedAccessControlList cannedAcl2) {
        this.cannedAcl = cannedAcl2;
    }

    public PutObjectRequest withCannedAcl(CannedAccessControlList cannedAcl2) {
        setCannedAcl(cannedAcl2);
        return this;
    }

    public AccessControlList getAccessControlList() {
        return this.accessControlList;
    }

    public void setAccessControlList(AccessControlList accessControlList2) {
        this.accessControlList = accessControlList2;
    }

    public PutObjectRequest withAccessControlList(AccessControlList accessControlList2) {
        setAccessControlList(accessControlList2);
        return this;
    }

    public InputStream getInputStream() {
        return this.inputStream;
    }

    public void setInputStream(InputStream inputStream2) {
        this.inputStream = inputStream2;
    }

    public PutObjectRequest withInputStream(InputStream inputStream2) {
        setInputStream(inputStream2);
        return this;
    }

    public void setRedirectLocation(String redirectLocation2) {
        this.redirectLocation = redirectLocation2;
    }

    public String getRedirectLocation() {
        return this.redirectLocation;
    }

    public PutObjectRequest withRedirectLocation(String redirectLocation2) {
        this.redirectLocation = redirectLocation2;
        return this;
    }

    public SSECustomerKey getSSECustomerKey() {
        return this.sseCustomerKey;
    }

    public void setSSECustomerKey(SSECustomerKey sseKey) {
        this.sseCustomerKey = sseKey;
    }

    public PutObjectRequest withSSECustomerKey(SSECustomerKey sseKey) {
        setSSECustomerKey(sseKey);
        return this;
    }

    @Deprecated
    public void setProgressListener(ProgressListener progressListener) {
        this.generalProgressListener = new LegacyS3ProgressListener(progressListener);
    }

    @Deprecated
    public ProgressListener getProgressListener() {
        if (this.generalProgressListener instanceof LegacyS3ProgressListener) {
            return ((LegacyS3ProgressListener) this.generalProgressListener).unwrap();
        }
        return null;
    }

    @Deprecated
    public PutObjectRequest withProgressListener(ProgressListener progressListener) {
        setProgressListener(progressListener);
        return this;
    }

    public void setGeneralProgressListener(ProgressListener generalProgressListener2) {
        this.generalProgressListener = generalProgressListener2;
    }

    public ProgressListener getGeneralProgressListener() {
        return this.generalProgressListener;
    }

    public PutObjectRequest withGeneralProgressListener(ProgressListener generalProgressListener2) {
        setGeneralProgressListener(generalProgressListener2);
        return this;
    }

    public PutObjectRequest clone() {
        ObjectMetadata clone;
        PutObjectRequest withInputStream = new PutObjectRequest(this.bucketName, this.key, this.redirectLocation).withAccessControlList(this.accessControlList).withCannedAcl(this.cannedAcl).withFile(this.file).withGeneralProgressListener(this.generalProgressListener).withInputStream(this.inputStream);
        if (this.metadata == null) {
            clone = null;
        } else {
            clone = this.metadata.clone();
        }
        return (PutObjectRequest) withInputStream.withMetadata(clone).withStorageClass(this.storageClass).withRequestMetricCollector(getRequestMetricCollector());
    }
}
