package com.amazonaws.mobileconnectors.s3.transfermanager.internal;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.event.ProgressListenerChain;
import com.amazonaws.mobileconnectors.s3.transfermanager.Copy;
import com.amazonaws.mobileconnectors.s3.transfermanager.TransferProgress;
import com.amazonaws.mobileconnectors.s3.transfermanager.model.CopyResult;
import java.util.concurrent.ExecutionException;

public class CopyImpl extends AbstractTransfer implements Copy {
    public CopyImpl(String description, TransferProgress transferProgress, ProgressListenerChain progressListenerChain, TransferStateChangeListener stateChangeListener) {
        super(description, transferProgress, progressListenerChain, stateChangeListener);
    }

    public CopyResult waitForCopyResult() throws AmazonClientException, AmazonServiceException, InterruptedException {
        CopyResult result = null;
        while (true) {
            try {
                if (this.monitor.isDone() && result != null) {
                    return result;
                }
                result = (CopyResult) this.monitor.getFuture().get();
            } catch (ExecutionException e) {
                rethrowExecutionException(e);
                return null;
            }
        }
    }
}
