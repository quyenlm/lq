package com.amazonaws.mobileconnectors.s3.transfermanager.internal;

import com.amazonaws.event.ProgressListener;
import com.amazonaws.event.ProgressListenerChain;
import com.amazonaws.mobileconnectors.s3.transfermanager.PersistableTransfer;

public class S3ProgressListenerChain extends ProgressListenerChain implements S3ProgressListener {
    public S3ProgressListenerChain(ProgressListener... listeners) {
        super(listeners);
    }

    public void onPersistableTransfer(PersistableTransfer persistableTransfer) {
        for (ProgressListener listener : getListeners()) {
            if (listener instanceof S3ProgressListener) {
                ((S3ProgressListener) listener).onPersistableTransfer(persistableTransfer);
            }
        }
    }
}
