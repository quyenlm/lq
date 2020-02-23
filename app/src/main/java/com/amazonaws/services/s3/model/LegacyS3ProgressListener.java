package com.amazonaws.services.s3.model;

import com.amazonaws.event.ProgressEvent;
import com.amazonaws.event.ProgressListener;

public class LegacyS3ProgressListener implements ProgressListener {
    private final ProgressListener listener;

    public LegacyS3ProgressListener(ProgressListener listener2) {
        this.listener = listener2;
    }

    public ProgressListener unwrap() {
        return this.listener;
    }

    public void progressChanged(ProgressEvent progressEvent) {
        if (this.listener != null) {
            this.listener.progressChanged(transform(progressEvent));
        }
    }

    private ProgressEvent transform(ProgressEvent event) {
        return new ProgressEvent(event.getEventCode(), event.getBytesTransferred());
    }
}
