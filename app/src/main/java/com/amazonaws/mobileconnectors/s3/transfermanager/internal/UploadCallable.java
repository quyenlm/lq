package com.amazonaws.mobileconnectors.s3.transfermanager.internal;

import com.amazonaws.event.ProgressEvent;
import com.amazonaws.event.ProgressListenerCallbackExecutor;
import com.amazonaws.event.ProgressListenerChain;
import com.amazonaws.mobileconnectors.s3.transfermanager.PersistableUpload;
import com.amazonaws.mobileconnectors.s3.transfermanager.Transfer;
import com.amazonaws.mobileconnectors.s3.transfermanager.TransferManager;
import com.amazonaws.mobileconnectors.s3.transfermanager.TransferManagerConfiguration;
import com.amazonaws.mobileconnectors.s3.transfermanager.TransferProgress;
import com.amazonaws.mobileconnectors.s3.transfermanager.model.UploadResult;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3EncryptionClient;
import com.amazonaws.services.s3.model.AbortMultipartUploadRequest;
import com.amazonaws.services.s3.model.CompleteMultipartUploadRequest;
import com.amazonaws.services.s3.model.CompleteMultipartUploadResult;
import com.amazonaws.services.s3.model.EncryptedInitiateMultipartUploadRequest;
import com.amazonaws.services.s3.model.EncryptedPutObjectRequest;
import com.amazonaws.services.s3.model.InitiateMultipartUploadRequest;
import com.amazonaws.services.s3.model.ListPartsRequest;
import com.amazonaws.services.s3.model.PartETag;
import com.amazonaws.services.s3.model.PartListing;
import com.amazonaws.services.s3.model.PartSummary;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.StorageClass;
import com.amazonaws.services.s3.model.UploadPartRequest;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class UploadCallable implements Callable<UploadResult> {
    private static final Log log = LogFactory.getLog(UploadCallable.class);
    private final TransferManagerConfiguration configuration;
    private final List<PartETag> eTagsToSkip = new ArrayList();
    private final List<Future<PartETag>> futures = new ArrayList();
    private final ProgressListenerChain listener;
    private String multipartUploadId;
    private PersistableUpload persistableUpload;
    private final PutObjectRequest putObjectRequest;
    private final AmazonS3 s3;
    private final ExecutorService threadPool;
    private final TransferProgress transferProgress;
    private final UploadImpl upload;

    public UploadCallable(TransferManager transferManager, ExecutorService threadPool2, UploadImpl upload2, PutObjectRequest putObjectRequest2, ProgressListenerChain progressListenerChain, String uploadId, TransferProgress transferProgress2) {
        this.s3 = transferManager.getAmazonS3Client();
        this.configuration = transferManager.getConfiguration();
        this.threadPool = threadPool2;
        this.putObjectRequest = putObjectRequest2;
        this.listener = progressListenerChain;
        this.upload = upload2;
        this.multipartUploadId = uploadId;
        this.transferProgress = transferProgress2;
    }

    /* access modifiers changed from: package-private */
    public List<Future<PartETag>> getFutures() {
        return this.futures;
    }

    /* access modifiers changed from: package-private */
    public List<PartETag> getETags() {
        return this.eTagsToSkip;
    }

    /* access modifiers changed from: package-private */
    public String getMultipartUploadId() {
        return this.multipartUploadId;
    }

    public boolean isMultipartUpload() {
        return TransferManagerUtils.shouldUseMultipartUpload(this.putObjectRequest, this.configuration);
    }

    public UploadResult call() throws Exception {
        this.upload.setState(Transfer.TransferState.InProgress);
        if (!isMultipartUpload()) {
            return uploadInOneChunk();
        }
        fireProgressEvent(2);
        return uploadInParts();
    }

    private UploadResult uploadInOneChunk() {
        PutObjectResult putObjectResult = this.s3.putObject(this.putObjectRequest);
        UploadResult uploadResult = new UploadResult();
        uploadResult.setBucketName(this.putObjectRequest.getBucketName());
        uploadResult.setKey(this.putObjectRequest.getKey());
        uploadResult.setETag(putObjectResult.getETag());
        uploadResult.setVersionId(putObjectResult.getVersionId());
        return uploadResult;
    }

    private void captureUploadStateIfPossible() {
        if (this.putObjectRequest.getSSECustomerKey() == null) {
            this.persistableUpload = new PersistableUpload(this.putObjectRequest.getBucketName(), this.putObjectRequest.getKey(), this.putObjectRequest.getFile().getAbsolutePath(), this.multipartUploadId, this.configuration.getMinimumUploadPartSize(), this.configuration.getMultipartUploadThreshold());
            notifyPersistableTransferAvailability();
        }
    }

    public PersistableUpload getPersistableUpload() {
        return this.persistableUpload;
    }

    private void notifyPersistableTransferAvailability() {
        S3ProgressPublisher.publishTransferPersistable(this.listener, this.persistableUpload);
    }

    private UploadResult uploadInParts() throws Exception {
        UploadResult uploadPartsInSeries;
        boolean isUsingEncryption = this.s3 instanceof AmazonS3EncryptionClient;
        long optimalPartSize = getOptimalPartSize(isUsingEncryption);
        if (this.multipartUploadId == null) {
            this.multipartUploadId = initiateMultipartUpload(this.putObjectRequest, isUsingEncryption);
        }
        try {
            UploadPartRequestFactory requestFactory = new UploadPartRequestFactory(this.putObjectRequest, this.multipartUploadId, optimalPartSize);
            if (TransferManagerUtils.isUploadParallelizable(this.putObjectRequest, isUsingEncryption)) {
                captureUploadStateIfPossible();
                uploadPartsInParallel(requestFactory, this.multipartUploadId);
                uploadPartsInSeries = null;
                if (this.putObjectRequest.getInputStream() != null) {
                    try {
                        this.putObjectRequest.getInputStream().close();
                    } catch (Exception e) {
                        log.warn("Unable to cleanly close input stream: " + e.getMessage(), e);
                    }
                }
            } else {
                uploadPartsInSeries = uploadPartsInSeries(requestFactory);
                if (this.putObjectRequest.getInputStream() != null) {
                    try {
                        this.putObjectRequest.getInputStream().close();
                    } catch (Exception e2) {
                        log.warn("Unable to cleanly close input stream: " + e2.getMessage(), e2);
                    }
                }
            }
            return uploadPartsInSeries;
        } catch (Exception e3) {
            fireProgressEvent(8);
            performAbortMultipartUpload();
            throw e3;
        } catch (Throwable th) {
            if (this.putObjectRequest.getInputStream() != null) {
                try {
                    this.putObjectRequest.getInputStream().close();
                } catch (Exception e4) {
                    log.warn("Unable to cleanly close input stream: " + e4.getMessage(), e4);
                }
            }
            throw th;
        }
    }

    /* access modifiers changed from: package-private */
    public void performAbortMultipartUpload() {
        try {
            if (this.multipartUploadId != null) {
                this.s3.abortMultipartUpload(new AbortMultipartUploadRequest(this.putObjectRequest.getBucketName(), this.putObjectRequest.getKey(), this.multipartUploadId));
            }
        } catch (Exception e2) {
            log.info("Unable to abort multipart upload, you may need to manually remove uploaded parts: " + e2.getMessage(), e2);
        }
    }

    private long getOptimalPartSize(boolean isUsingEncryption) {
        long optimalPartSize = TransferManagerUtils.calculateOptimalPartSize(this.putObjectRequest, this.configuration);
        if (isUsingEncryption && optimalPartSize % 32 > 0) {
            optimalPartSize = (optimalPartSize - (optimalPartSize % 32)) + 32;
        }
        log.debug("Calculated optimal part size: " + optimalPartSize);
        return optimalPartSize;
    }

    private UploadResult uploadPartsInSeries(UploadPartRequestFactory requestFactory) {
        List<PartETag> partETags = new ArrayList<>();
        while (requestFactory.hasMoreRequests()) {
            if (this.threadPool.isShutdown()) {
                throw new CancellationException("TransferManager has been shutdown");
            }
            UploadPartRequest uploadPartRequest = requestFactory.getNextUploadPartRequest();
            InputStream inputStream = uploadPartRequest.getInputStream();
            if (inputStream != null && inputStream.markSupported()) {
                if (uploadPartRequest.getPartSize() >= 2147483647L) {
                    inputStream.mark(Integer.MAX_VALUE);
                } else {
                    inputStream.mark((int) uploadPartRequest.getPartSize());
                }
            }
            partETags.add(this.s3.uploadPart(uploadPartRequest).getPartETag());
        }
        CompleteMultipartUploadResult completeMultipartUploadResult = this.s3.completeMultipartUpload(new CompleteMultipartUploadRequest(this.putObjectRequest.getBucketName(), this.putObjectRequest.getKey(), this.multipartUploadId, partETags));
        UploadResult uploadResult = new UploadResult();
        uploadResult.setBucketName(completeMultipartUploadResult.getBucketName());
        uploadResult.setKey(completeMultipartUploadResult.getKey());
        uploadResult.setETag(completeMultipartUploadResult.getETag());
        uploadResult.setVersionId(completeMultipartUploadResult.getVersionId());
        return uploadResult;
    }

    private void uploadPartsInParallel(UploadPartRequestFactory requestFactory, String uploadId) {
        Map<Integer, PartSummary> partNumbers = identifyExistingPartsForResume(uploadId);
        while (requestFactory.hasMoreRequests()) {
            if (this.threadPool.isShutdown()) {
                throw new CancellationException("TransferManager has been shutdown");
            }
            UploadPartRequest request = requestFactory.getNextUploadPartRequest();
            if (partNumbers.containsKey(Integer.valueOf(request.getPartNumber()))) {
                PartSummary summary = partNumbers.get(Integer.valueOf(request.getPartNumber()));
                this.eTagsToSkip.add(new PartETag(request.getPartNumber(), summary.getETag()));
                this.transferProgress.updateProgress(summary.getSize());
            } else {
                this.futures.add(this.threadPool.submit(new UploadPartCallable(this.s3, request)));
            }
        }
    }

    private Map<Integer, PartSummary> identifyExistingPartsForResume(String uploadId) {
        Map<Integer, PartSummary> partNumbers = new HashMap<>();
        if (uploadId != null) {
            int partNumber = 0;
            while (true) {
                PartListing parts = this.s3.listParts(new ListPartsRequest(this.putObjectRequest.getBucketName(), this.putObjectRequest.getKey(), uploadId).withPartNumberMarker(Integer.valueOf(partNumber)));
                for (PartSummary partSummary : parts.getParts()) {
                    partNumbers.put(Integer.valueOf(partSummary.getPartNumber()), partSummary);
                }
                if (!parts.isTruncated()) {
                    break;
                }
                partNumber = parts.getNextPartNumberMarker().intValue();
            }
        }
        return partNumbers;
    }

    private String initiateMultipartUpload(PutObjectRequest putObjectRequest2, boolean isUsingEncryption) {
        InitiateMultipartUploadRequest initiateMultipartUploadRequest;
        if (!isUsingEncryption || !(putObjectRequest2 instanceof EncryptedPutObjectRequest)) {
            initiateMultipartUploadRequest = new InitiateMultipartUploadRequest(putObjectRequest2.getBucketName(), putObjectRequest2.getKey()).withCannedACL(putObjectRequest2.getCannedAcl()).withObjectMetadata(putObjectRequest2.getMetadata());
        } else {
            initiateMultipartUploadRequest = new EncryptedInitiateMultipartUploadRequest(putObjectRequest2.getBucketName(), putObjectRequest2.getKey()).withCannedACL(putObjectRequest2.getCannedAcl()).withObjectMetadata(putObjectRequest2.getMetadata());
            ((EncryptedInitiateMultipartUploadRequest) initiateMultipartUploadRequest).setMaterialsDescription(((EncryptedPutObjectRequest) putObjectRequest2).getMaterialsDescription());
        }
        TransferManager.appendMultipartUserAgent(initiateMultipartUploadRequest);
        if (putObjectRequest2.getStorageClass() != null) {
            initiateMultipartUploadRequest.setStorageClass(StorageClass.fromValue(putObjectRequest2.getStorageClass()));
        }
        if (putObjectRequest2.getRedirectLocation() != null) {
            initiateMultipartUploadRequest.setRedirectLocation(putObjectRequest2.getRedirectLocation());
        }
        if (putObjectRequest2.getSSECustomerKey() != null) {
            initiateMultipartUploadRequest.setSSECustomerKey(putObjectRequest2.getSSECustomerKey());
        }
        String uploadId = this.s3.initiateMultipartUpload(initiateMultipartUploadRequest).getUploadId();
        log.debug("Initiated new multipart upload: " + uploadId);
        return uploadId;
    }

    private void fireProgressEvent(int eventType) {
        ProgressEvent event = new ProgressEvent(0);
        event.setEventCode(eventType);
        ProgressListenerCallbackExecutor.progressChanged(this.listener, event);
    }
}
