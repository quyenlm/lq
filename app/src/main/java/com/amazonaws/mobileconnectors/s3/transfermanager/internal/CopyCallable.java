package com.amazonaws.mobileconnectors.s3.transfermanager.internal;

import com.amazonaws.event.ProgressEvent;
import com.amazonaws.event.ProgressListenerCallbackExecutor;
import com.amazonaws.event.ProgressListenerChain;
import com.amazonaws.mobileconnectors.s3.transfermanager.Transfer;
import com.amazonaws.mobileconnectors.s3.transfermanager.TransferManager;
import com.amazonaws.mobileconnectors.s3.transfermanager.TransferManagerConfiguration;
import com.amazonaws.mobileconnectors.s3.transfermanager.model.CopyResult;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.Headers;
import com.amazonaws.services.s3.model.AbortMultipartUploadRequest;
import com.amazonaws.services.s3.model.CopyObjectRequest;
import com.amazonaws.services.s3.model.CopyObjectResult;
import com.amazonaws.services.s3.model.InitiateMultipartUploadRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PartETag;
import com.amazonaws.services.s3.model.StorageClass;
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

public class CopyCallable implements Callable<CopyResult> {
    private static final Log log = LogFactory.getLog(CopyCallable.class);
    private final TransferManagerConfiguration configuration;
    private final CopyImpl copy;
    private final CopyObjectRequest copyObjectRequest;
    private final List<Future<PartETag>> futures = new ArrayList();
    private final ObjectMetadata metadata;
    private String multipartUploadId;
    private final ProgressListenerCallbackExecutor progressListenerChainCallbackExecutor;
    private final AmazonS3 s3;
    private final ExecutorService threadPool;

    public CopyCallable(TransferManager transferManager, ExecutorService threadPool2, CopyImpl copy2, CopyObjectRequest copyObjectRequest2, ObjectMetadata metadata2, ProgressListenerChain progressListenerChain) {
        this.s3 = transferManager.getAmazonS3Client();
        this.configuration = transferManager.getConfiguration();
        this.threadPool = threadPool2;
        this.copyObjectRequest = copyObjectRequest2;
        this.metadata = metadata2;
        this.progressListenerChainCallbackExecutor = ProgressListenerCallbackExecutor.wrapListener(progressListenerChain);
        this.copy = copy2;
    }

    /* access modifiers changed from: package-private */
    public List<Future<PartETag>> getFutures() {
        return this.futures;
    }

    /* access modifiers changed from: package-private */
    public String getMultipartUploadId() {
        return this.multipartUploadId;
    }

    public boolean isMultipartCopy() {
        return this.metadata.getContentLength() > this.configuration.getMultipartCopyThreshold();
    }

    public CopyResult call() throws Exception {
        this.copy.setState(Transfer.TransferState.InProgress);
        if (!isMultipartCopy()) {
            return copyInOneChunk();
        }
        fireProgressEvent(2);
        copyInParts();
        return null;
    }

    private CopyResult copyInOneChunk() {
        CopyObjectResult copyObjectResult = this.s3.copyObject(this.copyObjectRequest);
        CopyResult copyResult = new CopyResult();
        copyResult.setSourceBucketName(this.copyObjectRequest.getSourceBucketName());
        copyResult.setSourceKey(this.copyObjectRequest.getSourceKey());
        copyResult.setDestinationBucketName(this.copyObjectRequest.getDestinationBucketName());
        copyResult.setDestinationKey(this.copyObjectRequest.getDestinationKey());
        copyResult.setETag(copyObjectResult.getETag());
        copyResult.setVersionId(copyObjectResult.getVersionId());
        return copyResult;
    }

    private void copyInParts() throws Exception {
        String bucketName = this.copyObjectRequest.getDestinationBucketName();
        String key = this.copyObjectRequest.getDestinationKey();
        this.multipartUploadId = initiateMultipartUpload(this.copyObjectRequest);
        try {
            copyPartsInParallel(new CopyPartRequestFactory(this.copyObjectRequest, this.multipartUploadId, getOptimalPartSize(this.metadata.getContentLength()), this.metadata.getContentLength()));
        } catch (Exception e) {
            fireProgressEvent(8);
            try {
                this.s3.abortMultipartUpload(new AbortMultipartUploadRequest(bucketName, key, this.multipartUploadId));
            } catch (Exception e2) {
                log.info("Unable to abort multipart upload, you may need to manually remove uploaded parts: " + e2.getMessage(), e2);
            }
            throw e;
        }
    }

    private long getOptimalPartSize(long contentLengthOfSource) {
        long optimalPartSize = TransferManagerUtils.calculateOptimalPartSizeForCopy(this.copyObjectRequest, this.configuration, contentLengthOfSource);
        log.debug("Calculated optimal part size: " + optimalPartSize);
        return optimalPartSize;
    }

    private void copyPartsInParallel(CopyPartRequestFactory requestFactory) {
        while (requestFactory.hasMoreRequests()) {
            if (this.threadPool.isShutdown()) {
                throw new CancellationException("TransferManager has been shutdown");
            }
            this.futures.add(this.threadPool.submit(new CopyPartCallable(this.s3, requestFactory.getNextCopyPartRequest())));
        }
    }

    private String initiateMultipartUpload(CopyObjectRequest copyObjectRequest2) {
        InitiateMultipartUploadRequest initiateMultipartUploadRequest = new InitiateMultipartUploadRequest(copyObjectRequest2.getDestinationBucketName(), copyObjectRequest2.getDestinationKey()).withCannedACL(copyObjectRequest2.getCannedAccessControlList());
        if (copyObjectRequest2.getAccessControlList() != null) {
            initiateMultipartUploadRequest.setAccessControlList(copyObjectRequest2.getAccessControlList());
        }
        if (copyObjectRequest2.getStorageClass() != null) {
            initiateMultipartUploadRequest.setStorageClass(StorageClass.fromValue(copyObjectRequest2.getStorageClass()));
        }
        if (copyObjectRequest2.getDestinationSSECustomerKey() != null) {
            initiateMultipartUploadRequest.setSSECustomerKey(copyObjectRequest2.getDestinationSSECustomerKey());
        }
        ObjectMetadata newObjectMetadata = copyObjectRequest2.getNewObjectMetadata();
        if (newObjectMetadata == null) {
            newObjectMetadata = new ObjectMetadata();
        }
        if (newObjectMetadata.getContentType() == null) {
            newObjectMetadata.setContentType(this.metadata.getContentType());
        }
        initiateMultipartUploadRequest.setObjectMetadata(newObjectMetadata);
        populateMetadataWithEncryptionParams(this.metadata, newObjectMetadata);
        String uploadId = this.s3.initiateMultipartUpload(initiateMultipartUploadRequest).getUploadId();
        log.debug("Initiated new multipart upload: " + uploadId);
        return uploadId;
    }

    private void fireProgressEvent(int eventType) {
        if (this.progressListenerChainCallbackExecutor != null) {
            ProgressEvent event = new ProgressEvent(0);
            event.setEventCode(eventType);
            this.progressListenerChainCallbackExecutor.progressChanged(event);
        }
    }

    private void populateMetadataWithEncryptionParams(ObjectMetadata source, ObjectMetadata destination) {
        Map<String, String> userMetadataSource = source.getUserMetadata();
        Map<String, String> userMetadataDestination = destination.getUserMetadata();
        String[] headersToCopy = {Headers.CRYPTO_CEK_ALGORITHM, Headers.CRYPTO_IV, Headers.CRYPTO_KEY, Headers.CRYPTO_KEY_V2, Headers.CRYPTO_KEYWRAP_ALGORITHM, Headers.CRYPTO_TAG_LENGTH, Headers.MATERIALS_DESCRIPTION, Headers.UNENCRYPTED_CONTENT_LENGTH, Headers.UNENCRYPTED_CONTENT_MD5};
        if (userMetadataSource != null) {
            if (userMetadataDestination == null) {
                userMetadataDestination = new HashMap<>();
                destination.setUserMetadata(userMetadataDestination);
            }
            for (String header : headersToCopy) {
                String headerValue = userMetadataSource.get(header);
                if (headerValue != null) {
                    userMetadataDestination.put(header, headerValue);
                }
            }
        }
    }
}
