package com.amazonaws.mobileconnectors.s3.transfermanager.internal;

import com.amazonaws.AmazonClientException;
import com.amazonaws.event.ProgressEvent;
import com.amazonaws.event.ProgressListenerCallbackExecutor;
import com.amazonaws.event.ProgressListenerChain;
import com.amazonaws.mobileconnectors.s3.transfermanager.PauseResult;
import com.amazonaws.mobileconnectors.s3.transfermanager.PauseStatus;
import com.amazonaws.mobileconnectors.s3.transfermanager.PersistableUpload;
import com.amazonaws.mobileconnectors.s3.transfermanager.Transfer;
import com.amazonaws.mobileconnectors.s3.transfermanager.TransferManager;
import com.amazonaws.mobileconnectors.s3.transfermanager.TransferManagerConfiguration;
import com.amazonaws.mobileconnectors.s3.transfermanager.model.UploadResult;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CompleteMultipartUploadRequest;
import com.amazonaws.services.s3.model.CompleteMultipartUploadResult;
import com.amazonaws.services.s3.model.PartETag;
import com.amazonaws.services.s3.model.PutObjectRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class UploadMonitor implements Callable<UploadResult>, TransferMonitor {
    private static final Log log = LogFactory.getLog(UploadMonitor.class);
    private final TransferManagerConfiguration configuration;
    private final List<Future<PartETag>> futures = new ArrayList();
    private boolean isUploadDone = false;
    private final UploadCallable multipartUploadCallable;
    private Future<UploadResult> nextFuture;
    private int pollInterval = 5000;
    private final ProgressListenerCallbackExecutor progressListenerChainCallbackExecutor;
    private final PutObjectRequest putObjectRequest;
    private final AmazonS3 s3;
    /* access modifiers changed from: private */
    public final ExecutorService threadPool;
    private ScheduledExecutorService timedThreadPool;
    private final UploadImpl transfer;
    private String uploadId;

    public synchronized Future<UploadResult> getFuture() {
        return this.nextFuture;
    }

    /* access modifiers changed from: private */
    public synchronized void setNextFuture(Future<UploadResult> nextFuture2) {
        this.nextFuture = nextFuture2;
    }

    public synchronized boolean isDone() {
        return this.isUploadDone;
    }

    private synchronized void markAllDone() {
        this.isUploadDone = true;
    }

    public UploadMonitor(TransferManager manager, UploadImpl transfer2, ExecutorService threadPool2, UploadCallable multipartUploadCallable2, PutObjectRequest putObjectRequest2, ProgressListenerChain progressListenerChain) {
        this.s3 = manager.getAmazonS3Client();
        this.configuration = manager.getConfiguration();
        this.multipartUploadCallable = multipartUploadCallable2;
        this.threadPool = threadPool2;
        this.putObjectRequest = putObjectRequest2;
        this.progressListenerChainCallbackExecutor = ProgressListenerCallbackExecutor.wrapListener(progressListenerChain);
        this.transfer = transfer2;
        setNextFuture(threadPool2.submit(this));
    }

    public void setTimedThreadPool(ScheduledExecutorService timedThreadPool2) {
        this.timedThreadPool = timedThreadPool2;
    }

    public UploadResult call() throws Exception {
        try {
            if (this.uploadId == null) {
                return upload();
            }
            return poll();
        } catch (CancellationException e) {
            this.transfer.setState(Transfer.TransferState.Canceled);
            fireProgressEvent(16);
            throw new AmazonClientException("Upload canceled");
        } catch (Exception e2) {
            this.transfer.setState(Transfer.TransferState.Failed);
            fireProgressEvent(8);
            throw e2;
        }
    }

    private UploadResult poll() throws InterruptedException {
        for (Future<PartETag> f : this.futures) {
            if (!f.isDone()) {
                reschedule();
                return null;
            }
        }
        for (Future<PartETag> f2 : this.futures) {
            if (f2.isCancelled()) {
                throw new CancellationException();
            }
        }
        return completeMultipartUpload();
    }

    private UploadResult upload() throws Exception, InterruptedException {
        UploadResult result = this.multipartUploadCallable.call();
        if (result != null) {
            uploadComplete();
        } else {
            this.uploadId = this.multipartUploadCallable.getMultipartUploadId();
            this.futures.addAll(this.multipartUploadCallable.getFutures());
            reschedule();
        }
        return result;
    }

    private void uploadComplete() {
        markAllDone();
        this.transfer.setState(Transfer.TransferState.Completed);
        if (this.multipartUploadCallable.isMultipartUpload()) {
            fireProgressEvent(4);
        }
    }

    private void reschedule() {
        setNextFuture(this.timedThreadPool.schedule(new Callable<UploadResult>() {
            public UploadResult call() throws Exception {
                UploadMonitor.this.setNextFuture(UploadMonitor.this.threadPool.submit(UploadMonitor.this));
                return null;
            }
        }, (long) this.pollInterval, TimeUnit.MILLISECONDS));
    }

    private void fireProgressEvent(int eventType) {
        if (this.progressListenerChainCallbackExecutor != null) {
            ProgressEvent event = new ProgressEvent(0);
            event.setEventCode(eventType);
            this.progressListenerChainCallbackExecutor.progressChanged(event);
        }
    }

    private UploadResult completeMultipartUpload() {
        CompleteMultipartUploadResult completeMultipartUploadResult = this.s3.completeMultipartUpload(new CompleteMultipartUploadRequest(this.putObjectRequest.getBucketName(), this.putObjectRequest.getKey(), this.uploadId, collectPartETags()));
        uploadComplete();
        UploadResult uploadResult = new UploadResult();
        uploadResult.setBucketName(completeMultipartUploadResult.getBucketName());
        uploadResult.setKey(completeMultipartUploadResult.getKey());
        uploadResult.setETag(completeMultipartUploadResult.getETag());
        uploadResult.setVersionId(completeMultipartUploadResult.getVersionId());
        return uploadResult;
    }

    private List<PartETag> collectPartETags() {
        List<PartETag> partETags = new ArrayList<>();
        partETags.addAll(this.multipartUploadCallable.getETags());
        for (Future<PartETag> future : this.futures) {
            try {
                partETags.add(future.get());
            } catch (Exception e) {
                throw new AmazonClientException("Unable to upload part: " + e.getCause().getMessage(), e.getCause());
            }
        }
        return partETags;
    }

    /* access modifiers changed from: package-private */
    public PauseResult<PersistableUpload> pause(boolean forceCancel) {
        PersistableUpload persistableUpload = this.multipartUploadCallable.getPersistableUpload();
        if (persistableUpload == null) {
            PauseStatus pauseStatus = TransferManagerUtils.determinePauseStatus(this.transfer.getState(), forceCancel);
            if (forceCancel) {
                cancelFutures();
                this.multipartUploadCallable.performAbortMultipartUpload();
            }
            return new PauseResult<>(pauseStatus);
        }
        cancelFutures();
        return new PauseResult<>(PauseStatus.SUCCESS, persistableUpload);
    }

    private void cancelFutures() {
        this.nextFuture.cancel(true);
        for (Future<PartETag> f : this.futures) {
            f.cancel(true);
        }
        this.multipartUploadCallable.getFutures().clear();
        this.futures.clear();
    }

    /* access modifiers changed from: package-private */
    public void performAbort() {
        cancelFutures();
        this.multipartUploadCallable.performAbortMultipartUpload();
    }
}
