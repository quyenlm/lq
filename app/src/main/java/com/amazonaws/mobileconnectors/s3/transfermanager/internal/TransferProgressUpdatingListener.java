package com.amazonaws.mobileconnectors.s3.transfermanager.internal;

import com.amazonaws.event.ProgressEvent;
import com.amazonaws.event.ProgressListener;
import com.amazonaws.mobileconnectors.s3.transfermanager.TransferProgress;

public class TransferProgressUpdatingListener implements ProgressListener {
    private final TransferProgress transferProgress;

    public TransferProgressUpdatingListener(TransferProgress transferProgress2) {
        this.transferProgress = transferProgress2;
    }

    public void progressChanged(ProgressEvent progressEvent) {
        long bytes = progressEvent.getBytesTransferred();
        if (bytes != 0) {
            this.transferProgress.updateProgress(bytes);
        }
    }
}
