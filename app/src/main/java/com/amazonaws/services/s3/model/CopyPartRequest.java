package com.amazonaws.services.s3.model;

import com.amazonaws.AmazonWebServiceRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CopyPartRequest extends AmazonWebServiceRequest implements S3AccelerateUnsupported {
    private String destinationBucketName;
    private String destinationKey;
    private SSECustomerKey destinationSSECustomerKey;
    private Long firstByte;
    private Long lastByte;
    private final List<String> matchingETagConstraints = new ArrayList();
    private Date modifiedSinceConstraint;
    private final List<String> nonmatchingEtagConstraints = new ArrayList();
    private int partNumber;
    private String sourceBucketName;
    private String sourceKey;
    private SSECustomerKey sourceSSECustomerKey;
    private String sourceVersionId;
    private Date unmodifiedSinceConstraint;
    private String uploadId;

    public String getUploadId() {
        return this.uploadId;
    }

    public void setUploadId(String uploadId2) {
        this.uploadId = uploadId2;
    }

    public CopyPartRequest withUploadId(String uploadId2) {
        this.uploadId = uploadId2;
        return this;
    }

    public int getPartNumber() {
        return this.partNumber;
    }

    public void setPartNumber(int partNumber2) {
        this.partNumber = partNumber2;
    }

    public CopyPartRequest withPartNumber(int partNumber2) {
        this.partNumber = partNumber2;
        return this;
    }

    public String getSourceBucketName() {
        return this.sourceBucketName;
    }

    public void setSourceBucketName(String sourceBucketName2) {
        this.sourceBucketName = sourceBucketName2;
    }

    public CopyPartRequest withSourceBucketName(String sourceBucketName2) {
        this.sourceBucketName = sourceBucketName2;
        return this;
    }

    public String getSourceKey() {
        return this.sourceKey;
    }

    public void setSourceKey(String sourceKey2) {
        this.sourceKey = sourceKey2;
    }

    public CopyPartRequest withSourceKey(String sourceKey2) {
        this.sourceKey = sourceKey2;
        return this;
    }

    public String getSourceVersionId() {
        return this.sourceVersionId;
    }

    public void setSourceVersionId(String sourceVersionId2) {
        this.sourceVersionId = sourceVersionId2;
    }

    public CopyPartRequest withSourceVersionId(String sourceVersionId2) {
        this.sourceVersionId = sourceVersionId2;
        return this;
    }

    public String getDestinationBucketName() {
        return this.destinationBucketName;
    }

    public void setDestinationBucketName(String destinationBucketName2) {
        this.destinationBucketName = destinationBucketName2;
    }

    public CopyPartRequest withDestinationBucketName(String destinationBucketName2) {
        setDestinationBucketName(destinationBucketName2);
        return this;
    }

    public String getDestinationKey() {
        return this.destinationKey;
    }

    public void setDestinationKey(String destinationKey2) {
        this.destinationKey = destinationKey2;
    }

    public CopyPartRequest withDestinationKey(String destinationKey2) {
        setDestinationKey(destinationKey2);
        return this;
    }

    public Long getFirstByte() {
        return this.firstByte;
    }

    public void setFirstByte(Long firstByte2) {
        this.firstByte = firstByte2;
    }

    public CopyPartRequest withFirstByte(Long firstByte2) {
        this.firstByte = firstByte2;
        return this;
    }

    public Long getLastByte() {
        return this.lastByte;
    }

    public void setLastByte(Long lastByte2) {
        this.lastByte = lastByte2;
    }

    public CopyPartRequest withLastByte(Long lastByte2) {
        this.lastByte = lastByte2;
        return this;
    }

    public List<String> getMatchingETagConstraints() {
        return this.matchingETagConstraints;
    }

    public void setMatchingETagConstraints(List<String> eTagList) {
        this.matchingETagConstraints.clear();
        this.matchingETagConstraints.addAll(eTagList);
    }

    public CopyPartRequest withMatchingETagConstraint(String eTag) {
        this.matchingETagConstraints.add(eTag);
        return this;
    }

    public List<String> getNonmatchingETagConstraints() {
        return this.nonmatchingEtagConstraints;
    }

    public void setNonmatchingETagConstraints(List<String> eTagList) {
        this.nonmatchingEtagConstraints.clear();
        this.nonmatchingEtagConstraints.addAll(eTagList);
    }

    public CopyPartRequest withNonmatchingETagConstraint(String eTag) {
        this.nonmatchingEtagConstraints.add(eTag);
        return this;
    }

    public Date getUnmodifiedSinceConstraint() {
        return this.unmodifiedSinceConstraint;
    }

    public void setUnmodifiedSinceConstraint(Date date) {
        this.unmodifiedSinceConstraint = date;
    }

    public CopyPartRequest withUnmodifiedSinceConstraint(Date date) {
        setUnmodifiedSinceConstraint(date);
        return this;
    }

    public Date getModifiedSinceConstraint() {
        return this.modifiedSinceConstraint;
    }

    public void setModifiedSinceConstraint(Date date) {
        this.modifiedSinceConstraint = date;
    }

    public CopyPartRequest withModifiedSinceConstraint(Date date) {
        setModifiedSinceConstraint(date);
        return this;
    }

    public SSECustomerKey getSourceSSECustomerKey() {
        return this.sourceSSECustomerKey;
    }

    public void setSourceSSECustomerKey(SSECustomerKey sseKey) {
        this.sourceSSECustomerKey = sseKey;
    }

    public CopyPartRequest withSourceSSECustomerKey(SSECustomerKey sseKey) {
        setSourceSSECustomerKey(sseKey);
        return this;
    }

    public SSECustomerKey getDestinationSSECustomerKey() {
        return this.destinationSSECustomerKey;
    }

    public void setDestinationSSECustomerKey(SSECustomerKey sseKey) {
        this.destinationSSECustomerKey = sseKey;
    }

    public CopyPartRequest withDestinationSSECustomerKey(SSECustomerKey sseKey) {
        setDestinationSSECustomerKey(sseKey);
        return this;
    }
}
