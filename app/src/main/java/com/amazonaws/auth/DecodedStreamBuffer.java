package com.amazonaws.auth;

import com.amazonaws.AmazonClientException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

class DecodedStreamBuffer {
    private static final Log log = LogFactory.getLog(DecodedStreamBuffer.class);
    private byte[] bufferArray;
    private boolean bufferSizeOverflow;
    private int byteBuffered;
    private int maxBufferSize;
    private int pos = -1;

    public DecodedStreamBuffer(int maxBufferSize2) {
        this.bufferArray = new byte[maxBufferSize2];
        this.maxBufferSize = maxBufferSize2;
    }

    public void buffer(byte read) {
        this.pos = -1;
        if (this.byteBuffered >= this.maxBufferSize) {
            if (log.isDebugEnabled()) {
                log.debug("Buffer size " + this.maxBufferSize + " has been exceeded and the input stream will not be repeatable. Freeing buffer memory");
            }
            this.bufferSizeOverflow = true;
            return;
        }
        byte[] bArr = this.bufferArray;
        int i = this.byteBuffered;
        this.byteBuffered = i + 1;
        bArr[i] = read;
    }

    public void buffer(byte[] array, int offset, int length) {
        this.pos = -1;
        if (this.byteBuffered + length > this.maxBufferSize) {
            if (log.isDebugEnabled()) {
                log.debug("Buffer size " + this.maxBufferSize + " has been exceeded and the input stream will not be repeatable. Freeing buffer memory");
            }
            this.bufferSizeOverflow = true;
            return;
        }
        System.arraycopy(array, offset, this.bufferArray, this.byteBuffered, length);
        this.byteBuffered += length;
    }

    public boolean hasNext() {
        return this.pos != -1 && this.pos < this.byteBuffered;
    }

    public byte next() {
        byte[] bArr = this.bufferArray;
        int i = this.pos;
        this.pos = i + 1;
        return bArr[i];
    }

    public void startReadBuffer() {
        if (this.bufferSizeOverflow) {
            throw new AmazonClientException("The input stream is not repeatable since the buffer size " + this.maxBufferSize + " has been exceeded.");
        }
        this.pos = 0;
    }
}
