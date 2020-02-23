package com.amazonaws.mobileconnectors.s3.transfermanager.internal;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.event.ProgressListenerChain;
import com.amazonaws.mobileconnectors.s3.transfermanager.Download;
import com.amazonaws.mobileconnectors.s3.transfermanager.MultipleFileDownload;
import com.amazonaws.mobileconnectors.s3.transfermanager.Transfer;
import com.amazonaws.mobileconnectors.s3.transfermanager.TransferProgress;
import java.io.IOException;
import java.util.Collection;

public class MultipleFileDownloadImpl extends MultipleFileTransfer<Download> implements MultipleFileDownload {
    private final String bucketName;
    private final String keyPrefix;

    public MultipleFileDownloadImpl(String description, TransferProgress transferProgress, ProgressListenerChain progressListenerChain, String keyPrefix2, String bucketName2, Collection<? extends Download> downloads) {
        super(description, transferProgress, progressListenerChain, downloads);
        this.keyPrefix = keyPrefix2;
        this.bucketName = bucketName2;
    }

    public String getKeyPrefix() {
        return this.keyPrefix;
    }

    public String getBucketName() {
        return this.bucketName;
    }

    public void waitForCompletion() throws AmazonClientException, AmazonServiceException, InterruptedException {
        if (!this.subTransfers.isEmpty()) {
            super.waitForCompletion();
        }
    }

    public void abort() throws IOException {
        for (Transfer fileDownload : this.subTransfers) {
            ((DownloadImpl) fileDownload).abortWithoutNotifyingStateChangeListener();
        }
        for (Transfer fileDownload2 : this.subTransfers) {
            ((DownloadImpl) fileDownload2).notifyStateChangeListeners(Transfer.TransferState.Canceled);
        }
    }
}
