package com.amazonaws.mobileconnectors.s3.transfermanager.internal;

import com.amazonaws.services.s3.model.CopyObjectRequest;
import com.amazonaws.services.s3.model.CopyPartRequest;

public class CopyPartRequestFactory {
    private final CopyObjectRequest copyObjectRequest;
    private long offset = 0;
    private final long optimalPartSize;
    private int partNumber = 1;
    private long remainingBytes;
    private final String uploadId;

    public CopyPartRequestFactory(CopyObjectRequest copyObjectRequest2, String uploadId2, long optimalPartSize2, long contentLength) {
        this.copyObjectRequest = copyObjectRequest2;
        this.uploadId = uploadId2;
        this.optimalPartSize = optimalPartSize2;
        this.remainingBytes = contentLength;
    }

    public synchronized boolean hasMoreRequests() {
        return this.remainingBytes > 0;
    }

    public synchronized CopyPartRequest getNextCopyPartRequest() {
        CopyPartRequest request;
        long partSize = Math.min(this.optimalPartSize, this.remainingBytes);
        CopyPartRequest withUploadId = new CopyPartRequest().withSourceBucketName(this.copyObjectRequest.getSourceBucketName()).withSourceKey(this.copyObjectRequest.getSourceKey()).withUploadId(this.uploadId);
        int i = this.partNumber;
        this.partNumber = i + 1;
        request = withUploadId.withPartNumber(i).withDestinationBucketName(this.copyObjectRequest.getDestinationBucketName()).withDestinationKey(this.copyObjectRequest.getDestinationKey()).withSourceVersionId(this.copyObjectRequest.getSourceVersionId()).withFirstByte(new Long(this.offset)).withLastByte(new Long((this.offset + partSize) - 1)).withSourceSSECustomerKey(this.copyObjectRequest.getSourceSSECustomerKey()).withDestinationSSECustomerKey(this.copyObjectRequest.getDestinationSSECustomerKey());
        setOtherMetadataInRequest(request);
        this.offset += partSize;
        this.remainingBytes -= partSize;
        return request;
    }

    private void setOtherMetadataInRequest(CopyPartRequest request) {
        if (this.copyObjectRequest.getMatchingETagConstraints() != null) {
            request.setMatchingETagConstraints(this.copyObjectRequest.getMatchingETagConstraints());
        }
        if (this.copyObjectRequest.getModifiedSinceConstraint() != null) {
            request.setModifiedSinceConstraint(this.copyObjectRequest.getModifiedSinceConstraint());
        }
        if (this.copyObjectRequest.getNonmatchingETagConstraints() != null) {
            request.setNonmatchingETagConstraints(this.copyObjectRequest.getNonmatchingETagConstraints());
        }
        if (this.copyObjectRequest.getSourceVersionId() != null) {
            request.setSourceVersionId(this.copyObjectRequest.getSourceVersionId());
        }
        if (this.copyObjectRequest.getUnmodifiedSinceConstraint() != null) {
            request.setUnmodifiedSinceConstraint(this.copyObjectRequest.getUnmodifiedSinceConstraint());
        }
    }
}
