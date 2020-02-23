package com.amazonaws.mobileconnectors.s3.transfermanager;

import com.amazonaws.event.ProgressEvent;
import com.amazonaws.event.ProgressListenerChain;
import com.amazonaws.mobileconnectors.s3.transfermanager.internal.TransferProgressUpdatingListener;

final class MultipleFileTransferProgressUpdatingListener extends TransferProgressUpdatingListener {
    private final ProgressListenerChain progressListenerChain;

    public MultipleFileTransferProgressUpdatingListener(TransferProgress transferProgress, ProgressListenerChain progressListenerChain2) {
        super(transferProgress);
        this.progressListenerChain = progressListenerChain2;
    }

    public void progressChanged(ProgressEvent progressEvent) {
        super.progressChanged(progressEvent);
        this.progressListenerChain.progressChanged(progressEvent);
    }
}
