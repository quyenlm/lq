package com.amazonaws.services.s3.model;

import com.amazonaws.AmazonWebServiceRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CopyObjectRequest extends AmazonWebServiceRequest implements S3AccelerateUnsupported {
    private AccessControlList accessControlList;
    private CannedAccessControlList cannedACL;
    private String destinationBucketName;
    private String destinationKey;
    private SSECustomerKey destinationSSECustomerKey;
    private List<String> matchingETagConstraints;
    private Date modifiedSinceConstraint;
    private ObjectMetadata newObjectMetadata;
    private List<String> nonmatchingEtagConstraints;
    private String redirectLocation;
    private String sourceBucketName;
    private String sourceKey;
    private SSECustomerKey sourceSSECustomerKey;
    private String sourceVersionId;
    private String storageClass;
    private Date unmodifiedSinceConstraint;

    public CopyObjectRequest(String sourceBucketName2, String sourceKey2, String destinationBucketName2, String destinationKey2) {
        this(sourceBucketName2, sourceKey2, (String) null, destinationBucketName2, destinationKey2);
    }

    public CopyObjectRequest(String sourceBucketName2, String sourceKey2, String sourceVersionId2, String destinationBucketName2, String destinationKey2) {
        this.matchingETagConstraints = new ArrayList();
        this.nonmatchingEtagConstraints = new ArrayList();
        this.sourceBucketName = sourceBucketName2;
        this.sourceKey = sourceKey2;
        this.sourceVersionId = sourceVersionId2;
        this.destinationBucketName = destinationBucketName2;
        this.destinationKey = destinationKey2;
    }

    public String getSourceBucketName() {
        return this.sourceBucketName;
    }

    public void setSourceBucketName(String sourceBucketName2) {
        this.sourceBucketName = sourceBucketName2;
    }

    public CopyObjectRequest withSourceBucketName(String sourceBucketName2) {
        setSourceBucketName(sourceBucketName2);
        return this;
    }

    public String getSourceKey() {
        return this.sourceKey;
    }

    public void setSourceKey(String sourceKey2) {
        this.sourceKey = sourceKey2;
    }

    public CopyObjectRequest withSourceKey(String sourceKey2) {
        setSourceKey(sourceKey2);
        return this;
    }

    public String getSourceVersionId() {
        return this.sourceVersionId;
    }

    public void setSourceVersionId(String sourceVersionId2) {
        this.sourceVersionId = sourceVersionId2;
    }

    public CopyObjectRequest withSourceVersionId(String sourceVersionId2) {
        setSourceVersionId(sourceVersionId2);
        return this;
    }

    public String getDestinationBucketName() {
        return this.destinationBucketName;
    }

    public void setDestinationBucketName(String destinationBucketName2) {
        this.destinationBucketName = destinationBucketName2;
    }

    public CopyObjectRequest withDestinationBucketName(String destinationBucketName2) {
        setDestinationBucketName(destinationBucketName2);
        return this;
    }

    public String getDestinationKey() {
        return this.destinationKey;
    }

    public void setDestinationKey(String destinationKey2) {
        this.destinationKey = destinationKey2;
    }

    public CopyObjectRequest withDestinationKey(String destinationKey2) {
        setDestinationKey(destinationKey2);
        return this;
    }

    public String getStorageClass() {
        return this.storageClass;
    }

    public void setStorageClass(String storageClass2) {
        this.storageClass = storageClass2;
    }

    public CopyObjectRequest withStorageClass(String storageClass2) {
        setStorageClass(storageClass2);
        return this;
    }

    public void setStorageClass(StorageClass storageClass2) {
        this.storageClass = storageClass2.toString();
    }

    public CopyObjectRequest withStorageClass(StorageClass storageClass2) {
        setStorageClass(storageClass2);
        return this;
    }

    public CannedAccessControlList getCannedAccessControlList() {
        return this.cannedACL;
    }

    public void setCannedAccessControlList(CannedAccessControlList cannedACL2) {
        this.cannedACL = cannedACL2;
    }

    public CopyObjectRequest withCannedAccessControlList(CannedAccessControlList cannedACL2) {
        setCannedAccessControlList(cannedACL2);
        return this;
    }

    public AccessControlList getAccessControlList() {
        return this.accessControlList;
    }

    public void setAccessControlList(AccessControlList accessControlList2) {
        this.accessControlList = accessControlList2;
    }

    public CopyObjectRequest withAccessControlList(AccessControlList accessControlList2) {
        setAccessControlList(accessControlList2);
        return this;
    }

    public ObjectMetadata getNewObjectMetadata() {
        return this.newObjectMetadata;
    }

    public void setNewObjectMetadata(ObjectMetadata newObjectMetadata2) {
        this.newObjectMetadata = newObjectMetadata2;
    }

    public CopyObjectRequest withNewObjectMetadata(ObjectMetadata newObjectMetadata2) {
        setNewObjectMetadata(newObjectMetadata2);
        return this;
    }

    public List<String> getMatchingETagConstraints() {
        return this.matchingETagConstraints;
    }

    public void setMatchingETagConstraints(List<String> eTagList) {
        this.matchingETagConstraints = eTagList;
    }

    public CopyObjectRequest withMatchingETagConstraint(String eTag) {
        this.matchingETagConstraints.add(eTag);
        return this;
    }

    public List<String> getNonmatchingETagConstraints() {
        return this.nonmatchingEtagConstraints;
    }

    public void setNonmatchingETagConstraints(List<String> eTagList) {
        this.nonmatchingEtagConstraints = eTagList;
    }

    public CopyObjectRequest withNonmatchingETagConstraint(String eTag) {
        this.nonmatchingEtagConstraints.add(eTag);
        return this;
    }

    public Date getUnmodifiedSinceConstraint() {
        return this.unmodifiedSinceConstraint;
    }

    public void setUnmodifiedSinceConstraint(Date date) {
        this.unmodifiedSinceConstraint = date;
    }

    public CopyObjectRequest withUnmodifiedSinceConstraint(Date date) {
        setUnmodifiedSinceConstraint(date);
        return this;
    }

    public Date getModifiedSinceConstraint() {
        return this.modifiedSinceConstraint;
    }

    public void setModifiedSinceConstraint(Date date) {
        this.modifiedSinceConstraint = date;
    }

    public CopyObjectRequest withModifiedSinceConstraint(Date date) {
        setModifiedSinceConstraint(date);
        return this;
    }

    public void setRedirectLocation(String redirectLocation2) {
        this.redirectLocation = redirectLocation2;
    }

    public String getRedirectLocation() {
        return this.redirectLocation;
    }

    public CopyObjectRequest withRedirectLocation(String redirectLocation2) {
        this.redirectLocation = redirectLocation2;
        return this;
    }

    public SSECustomerKey getSourceSSECustomerKey() {
        return this.sourceSSECustomerKey;
    }

    public void setSourceSSECustomerKey(SSECustomerKey sseKey) {
        this.sourceSSECustomerKey = sseKey;
    }

    public CopyObjectRequest withSourceSSECustomerKey(SSECustomerKey sseKey) {
        setSourceSSECustomerKey(sseKey);
        return this;
    }

    public SSECustomerKey getDestinationSSECustomerKey() {
        return this.destinationSSECustomerKey;
    }

    public void setDestinationSSECustomerKey(SSECustomerKey sseKey) {
        this.destinationSSECustomerKey = sseKey;
    }

    public CopyObjectRequest withDestinationSSECustomerKey(SSECustomerKey sseKey) {
        setDestinationSSECustomerKey(sseKey);
        return this;
    }
}
