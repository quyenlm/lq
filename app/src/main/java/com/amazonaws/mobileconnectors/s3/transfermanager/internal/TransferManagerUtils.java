package com.amazonaws.mobileconnectors.s3.transfermanager.internal;

import com.amazonaws.mobileconnectors.s3.transfermanager.PauseStatus;
import com.amazonaws.mobileconnectors.s3.transfermanager.Transfer;
import com.amazonaws.mobileconnectors.s3.transfermanager.TransferManagerConfiguration;
import com.amazonaws.services.s3.model.CopyObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import java.io.File;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;

public class TransferManagerUtils {
    public static ThreadPoolExecutor createDefaultExecutorService() {
        return (ThreadPoolExecutor) Executors.newFixedThreadPool(10, new ThreadFactory() {
            private int threadCount = 1;

            public Thread newThread(Runnable r) {
                Thread thread = new Thread(r);
                StringBuilder append = new StringBuilder().append("s3-transfer-manager-worker-");
                int i = this.threadCount;
                this.threadCount = i + 1;
                thread.setName(append.append(i).toString());
                return thread;
            }
        });
    }

    public static boolean isUploadParallelizable(PutObjectRequest putObjectRequest, boolean isUsingEncryption) {
        if (!isUsingEncryption && getRequestFile(putObjectRequest) != null) {
            return true;
        }
        return false;
    }

    public static long getContentLength(PutObjectRequest putObjectRequest) {
        File file = getRequestFile(putObjectRequest);
        if (file != null) {
            return file.length();
        }
        if (putObjectRequest.getInputStream() == null || putObjectRequest.getMetadata().getContentLength() <= 0) {
            return -1;
        }
        return putObjectRequest.getMetadata().getContentLength();
    }

    public static long calculateOptimalPartSize(PutObjectRequest putObjectRequest, TransferManagerConfiguration configuration) {
        return (long) Math.max(Math.ceil(((double) getContentLength(putObjectRequest)) / 10000.0d), (double) configuration.getMinimumUploadPartSize());
    }

    public static boolean shouldUseMultipartUpload(PutObjectRequest putObjectRequest, TransferManagerConfiguration configuration) {
        return getContentLength(putObjectRequest) > configuration.getMultipartUploadThreshold();
    }

    public static File getRequestFile(PutObjectRequest putObjectRequest) {
        if (putObjectRequest.getFile() != null) {
            return putObjectRequest.getFile();
        }
        return null;
    }

    public static long calculateOptimalPartSizeForCopy(CopyObjectRequest copyObjectRequest, TransferManagerConfiguration configuration, long contentLengthOfSource) {
        return (long) Math.max(Math.ceil(((double) contentLengthOfSource) / 10000.0d), (double) configuration.getMultipartCopyPartSize());
    }

    public static PauseStatus determinePauseStatus(Transfer.TransferState transferState, boolean forceCancel) {
        if (forceCancel) {
            if (transferState == Transfer.TransferState.Waiting) {
                return PauseStatus.CANCELLED_BEFORE_START;
            }
            if (transferState == Transfer.TransferState.InProgress) {
                return PauseStatus.CANCELLED;
            }
        }
        if (transferState == Transfer.TransferState.Waiting) {
            return PauseStatus.NOT_STARTED;
        }
        return PauseStatus.NO_EFFECT;
    }
}
