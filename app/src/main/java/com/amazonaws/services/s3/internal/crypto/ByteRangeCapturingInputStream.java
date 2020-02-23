package com.amazonaws.services.s3.internal.crypto;

import com.amazonaws.internal.SdkFilterInputStream;
import java.io.IOException;
import java.io.InputStream;

public class ByteRangeCapturingInputStream extends SdkFilterInputStream {
    private final byte[] block;
    private int blockPosition = 0;
    private final long endingPosition;
    private int markedBlockPosition;
    private long markedStreamPosition;
    private final long startingPosition;
    private long streamPosition;

    public ByteRangeCapturingInputStream(InputStream in, long startingPosition2, long endingPosition2) {
        super(in);
        if (startingPosition2 >= endingPosition2) {
            throw new IllegalArgumentException("Invalid byte range specified: the starting position must be less than the ending position");
        }
        this.startingPosition = startingPosition2;
        this.endingPosition = endingPosition2;
        this.block = new byte[((int) (endingPosition2 - startingPosition2))];
    }

    public byte[] getBlock() {
        return this.block;
    }

    public int read() throws IOException {
        int data = super.read();
        if (data == -1) {
            return -1;
        }
        if (this.streamPosition >= this.startingPosition && this.streamPosition <= this.endingPosition) {
            byte[] bArr = this.block;
            int i = this.blockPosition;
            this.blockPosition = i + 1;
            bArr[i] = (byte) data;
        }
        this.streamPosition++;
        return data;
    }

    public synchronized void mark(int readlimit) {
        super.mark(readlimit);
        if (markSupported()) {
            this.markedStreamPosition = this.streamPosition;
            this.markedBlockPosition = this.blockPosition;
        }
    }

    public synchronized void reset() throws IOException {
        super.reset();
        if (markSupported()) {
            this.streamPosition = this.markedStreamPosition;
            this.blockPosition = this.markedBlockPosition;
        }
    }

    public int read(byte[] b, int off, int len) throws IOException {
        int bytesRead = super.read(b, off, len);
        if (bytesRead == -1) {
            return -1;
        }
        if (this.streamPosition + ((long) bytesRead) >= this.startingPosition && this.streamPosition <= this.endingPosition) {
            for (int i = 0; i < bytesRead; i++) {
                if (this.streamPosition + ((long) i) >= this.startingPosition && this.streamPosition + ((long) i) < this.endingPosition) {
                    byte[] bArr = this.block;
                    int i2 = this.blockPosition;
                    this.blockPosition = i2 + 1;
                    bArr[i2] = b[off + i];
                }
            }
        }
        this.streamPosition += (long) bytesRead;
        return bytesRead;
    }
}
