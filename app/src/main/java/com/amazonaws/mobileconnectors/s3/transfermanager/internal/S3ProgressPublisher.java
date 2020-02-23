package com.amazonaws.mobileconnectors.s3.transfermanager.internal;

import com.amazonaws.event.ProgressListener;
import com.amazonaws.event.ProgressListenerCallbackExecutor;
import com.amazonaws.mobileconnectors.s3.transfermanager.PersistableTransfer;
import java.util.concurrent.Future;

public class S3ProgressPublisher extends ProgressListenerCallbackExecutor {
    public static Future<?> publishTransferPersistable(ProgressListener listener, final PersistableTransfer persistableTransfer) {
        if (persistableTransfer == null || !(listener instanceof S3ProgressListener)) {
            return null;
        }
        final S3ProgressListener s3listener = (S3ProgressListener) listener;
        return getExecutorService().submit(new Runnable() {
            public void run() {
                s3listener.onPersistableTransfer(persistableTransfer);
            }
        });
    }
}
