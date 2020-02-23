package com.amazonaws.mobileconnectors.s3.transfermanager.internal;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.event.ProgressListenerChain;
import com.amazonaws.mobileconnectors.s3.transfermanager.PauseResult;
import com.amazonaws.mobileconnectors.s3.transfermanager.PauseStatus;
import com.amazonaws.mobileconnectors.s3.transfermanager.PersistableUpload;
import com.amazonaws.mobileconnectors.s3.transfermanager.TransferProgress;
import com.amazonaws.mobileconnectors.s3.transfermanager.Upload;
import com.amazonaws.mobileconnectors.s3.transfermanager.exception.PauseException;
import com.amazonaws.mobileconnectors.s3.transfermanager.model.UploadResult;
import java.util.concurrent.ExecutionException;

public class UploadImpl extends AbstractTransfer implements Upload {
    public UploadImpl(String description, TransferProgress transferProgressInternalState, ProgressListenerChain progressListenerChain, TransferStateChangeListener listener) {
        super(description, transferProgressInternalState, progressListenerChain, listener);
    }

    public UploadResult waitForUploadResult() throws AmazonClientException, AmazonServiceException, InterruptedException {
        UploadResult result = null;
        while (true) {
            try {
                if (this.monitor.isDone() && result != null) {
                    return result;
                }
                result = (UploadResult) this.monitor.getFuture().get();
            } catch (ExecutionException e) {
                rethrowExecutionException(e);
                return null;
            }
        }
    }

    public PersistableUpload pause() throws PauseException {
        PauseResult<PersistableUpload> pauseResult = pause(true);
        if (pauseResult.getPauseStatus() == PauseStatus.SUCCESS) {
            return pauseResult.getInfoToResume();
        }
        throw new PauseException(pauseResult.getPauseStatus());
    }

    private PauseResult<PersistableUpload> pause(boolean forceCancelTransfers) throws AmazonClientException {
        return ((UploadMonitor) this.monitor).pause(forceCancelTransfers);
    }

    public PauseResult<PersistableUpload> tryPause(boolean forceCancelTransfers) {
        return pause(forceCancelTransfers);
    }

    public void abort() {
        ((UploadMonitor) this.monitor).performAbort();
    }
}
