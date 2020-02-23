package com.amazonaws.mobileconnectors.s3.transfermanager.internal;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.event.ProgressListenerChain;
import com.amazonaws.mobileconnectors.s3.transfermanager.MultipleFileUpload;
import com.amazonaws.mobileconnectors.s3.transfermanager.TransferProgress;
import com.amazonaws.mobileconnectors.s3.transfermanager.Upload;
import java.util.Collection;
import java.util.Collections;

public class MultipleFileUploadImpl extends MultipleFileTransfer<Upload> implements MultipleFileUpload {
    private final String bucketName;
    private final String keyPrefix;

    public MultipleFileUploadImpl(String description, TransferProgress transferProgress, ProgressListenerChain progressListenerChain, String keyPrefix2, String bucketName2, Collection<? extends Upload> subTransfers) {
        super(description, transferProgress, progressListenerChain, subTransfers);
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

    public Collection<? extends Upload> getSubTransfers() {
        return Collections.unmodifiableCollection(this.subTransfers);
    }
}
