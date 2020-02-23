package com.amazonaws.services.s3.internal;

import com.amazonaws.internal.SdkFilterInputStream;
import java.io.IOException;
import java.io.InputStream;

public final class InputSubstream extends SdkFilterInputStream {
    private final boolean closeSourceStream;
    private long currentPosition = 0;
    private long markedPosition = 0;
    private final long requestedLength;
    private final long requestedOffset;

    public InputSubstream(InputStream in, long offset, long length, boolean closeSourceStream2) {
        super(in);
        this.requestedLength = length;
        this.requestedOffset = offset;
        this.closeSourceStream = closeSourceStream2;
    }

    public int read() throws IOException {
        byte[] b = new byte[1];
        int bytesRead = read(b, 0, 1);
        return bytesRead == -1 ? bytesRead : b[0];
    }

    public int read(byte[] b, int off, int len) throws IOException {
        while (this.currentPosition < this.requestedOffset) {
            this.currentPosition += super.skip(this.requestedOffset - this.currentPosition);
        }
        long bytesRemaining = (this.requestedLength + this.requestedOffset) - this.currentPosition;
        if (bytesRemaining <= 0) {
            return -1;
        }
        int bytesRead = super.read(b, off, (int) Math.min((long) len, bytesRemaining));
        this.currentPosition += (long) bytesRead;
        return bytesRead;
    }

    public synchronized void mark(int readlimit) {
        this.markedPosition = this.currentPosition;
        super.mark(readlimit);
    }

    public synchronized void reset() throws IOException {
        this.currentPosition = this.markedPosition;
        super.reset();
    }

    public void close() throws IOException {
        if (this.closeSourceStream) {
            super.close();
        }
    }

    public int available() throws IOException {
        long bytesRemaining;
        if (this.currentPosition < this.requestedOffset) {
            bytesRemaining = this.requestedLength;
        } else {
            bytesRemaining = (this.requestedLength + this.requestedOffset) - this.currentPosition;
        }
        return (int) Math.min(bytesRemaining, (long) super.available());
    }
}
