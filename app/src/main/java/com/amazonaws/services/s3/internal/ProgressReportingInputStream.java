package com.amazonaws.services.s3.internal;

import com.amazonaws.internal.SdkFilterInputStream;
import com.amazonaws.services.s3.model.ProgressEvent;
import com.amazonaws.services.s3.model.ProgressListener;
import java.io.IOException;
import java.io.InputStream;

@Deprecated
public class ProgressReportingInputStream extends SdkFilterInputStream {
    private static final int NOTIFICATION_THRESHOLD = 8192;
    private boolean fireCompletedEvent;
    private final ProgressListener listener;
    private int unnotifiedByteCount;

    public ProgressReportingInputStream(InputStream in, ProgressListener listener2) {
        super(in);
        this.listener = listener2;
    }

    public void setFireCompletedEvent(boolean fireCompletedEvent2) {
        this.fireCompletedEvent = fireCompletedEvent2;
    }

    public boolean getFireCompletedEvent() {
        return this.fireCompletedEvent;
    }

    public int read() throws IOException {
        int data = super.read();
        if (data == -1) {
            notifyCompleted();
        }
        if (data != -1) {
            notify(1);
        }
        return data;
    }

    public int read(byte[] b, int off, int len) throws IOException {
        int bytesRead = super.read(b, off, len);
        if (bytesRead == -1) {
            notifyCompleted();
        }
        if (bytesRead != -1) {
            notify(bytesRead);
        }
        return bytesRead;
    }

    public void close() throws IOException {
        if (this.unnotifiedByteCount > 0) {
            this.listener.progressChanged(new ProgressEvent(this.unnotifiedByteCount));
            this.unnotifiedByteCount = 0;
        }
        super.close();
    }

    private void notifyCompleted() {
        if (this.fireCompletedEvent) {
            ProgressEvent event = new ProgressEvent(this.unnotifiedByteCount);
            event.setEventCode(4);
            this.unnotifiedByteCount = 0;
            this.listener.progressChanged(event);
        }
    }

    private void notify(int bytesRead) {
        this.unnotifiedByteCount += bytesRead;
        if (this.unnotifiedByteCount >= 8192) {
            this.listener.progressChanged(new ProgressEvent(this.unnotifiedByteCount));
            this.unnotifiedByteCount = 0;
        }
    }
}
