package com.amazonaws.mobileconnectors.s3.transfermanager.internal;

import com.amazonaws.services.s3.internal.InputSubstream;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.SSECustomerKey;
import com.amazonaws.services.s3.model.UploadPartRequest;
import java.io.File;

public class UploadPartRequestFactory {
    private final String bucketName;
    private final File file;
    private final String key;
    private long offset = 0;
    private final long optimalPartSize;
    private int partNumber = 1;
    private final PutObjectRequest putObjectRequest;
    private long remainingBytes;
    private SSECustomerKey sseCustomerKey;
    private final String uploadId;

    public UploadPartRequestFactory(PutObjectRequest putObjectRequest2, String uploadId2, long optimalPartSize2) {
        this.putObjectRequest = putObjectRequest2;
        this.uploadId = uploadId2;
        this.optimalPartSize = optimalPartSize2;
        this.bucketName = putObjectRequest2.getBucketName();
        this.key = putObjectRequest2.getKey();
        this.file = TransferManagerUtils.getRequestFile(putObjectRequest2);
        this.remainingBytes = TransferManagerUtils.getContentLength(putObjectRequest2);
        this.sseCustomerKey = putObjectRequest2.getSSECustomerKey();
    }

    public synchronized boolean hasMoreRequests() {
        return this.remainingBytes > 0;
    }

    public synchronized UploadPartRequest getNextUploadPartRequest() {
        UploadPartRequest request;
        long partSize = Math.min(this.optimalPartSize, this.remainingBytes);
        boolean isLastPart = this.remainingBytes - partSize <= 0;
        if (this.putObjectRequest.getInputStream() != null) {
            UploadPartRequest withInputStream = new UploadPartRequest().withBucketName(this.bucketName).withKey(this.key).withUploadId(this.uploadId).withInputStream(new InputSubstream(this.putObjectRequest.getInputStream(), 0, partSize, isLastPart));
            int i = this.partNumber;
            this.partNumber = i + 1;
            request = withInputStream.withPartNumber(i).withPartSize(partSize);
        } else {
            UploadPartRequest withFileOffset = new UploadPartRequest().withBucketName(this.bucketName).withKey(this.key).withUploadId(this.uploadId).withFile(this.file).withFileOffset(this.offset);
            int i2 = this.partNumber;
            this.partNumber = i2 + 1;
            request = withFileOffset.withPartNumber(i2).withPartSize(partSize);
        }
        if (this.sseCustomerKey != null) {
            request.setSSECustomerKey(this.sseCustomerKey);
        }
        this.offset += partSize;
        this.remainingBytes -= partSize;
        request.setLastPart(isLastPart);
        request.setGeneralProgressListener(this.putObjectRequest.getGeneralProgressListener());
        return request;
    }
}
