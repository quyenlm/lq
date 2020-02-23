package com.amazonaws.services.s3.internal.crypto;

import com.amazonaws.internal.SdkInputStream;
import java.io.IOException;
import java.io.InputStream;

public class AdjustedRangeInputStream extends SdkInputStream {
    private boolean closed = false;
    private InputStream decryptedContents;
    private long virtualAvailable;

    public AdjustedRangeInputStream(InputStream objectContents, long rangeBeginning, long rangeEnd) throws IOException {
        this.decryptedContents = objectContents;
        initializeForRead(rangeBeginning, rangeEnd);
    }

    private void initializeForRead(long rangeBeginning, long rangeEnd) throws IOException {
        int numBytesToSkip;
        if (rangeBeginning < ((long) JceEncryptionConstants.SYMMETRIC_CIPHER_BLOCK_SIZE)) {
            numBytesToSkip = (int) rangeBeginning;
        } else {
            numBytesToSkip = JceEncryptionConstants.SYMMETRIC_CIPHER_BLOCK_SIZE + ((int) (rangeBeginning % ((long) JceEncryptionConstants.SYMMETRIC_CIPHER_BLOCK_SIZE)));
        }
        if (numBytesToSkip != 0) {
            while (numBytesToSkip > 0) {
                this.decryptedContents.read();
                numBytesToSkip--;
            }
        }
        this.virtualAvailable = (rangeEnd - rangeBeginning) + 1;
    }

    public int read() throws IOException {
        int result;
        abortIfNeeded();
        if (this.virtualAvailable <= 0) {
            result = -1;
        } else {
            result = this.decryptedContents.read();
        }
        if (result != -1) {
            this.virtualAvailable--;
        } else {
            close();
            this.virtualAvailable = 0;
        }
        return result;
    }

    public int read(byte[] buffer, int offset, int length) throws IOException {
        int numBytesRead;
        abortIfNeeded();
        if (this.virtualAvailable <= 0) {
            numBytesRead = -1;
        } else {
            if (((long) length) > this.virtualAvailable) {
                length = this.virtualAvailable < 2147483647L ? (int) this.virtualAvailable : Integer.MAX_VALUE;
            }
            numBytesRead = this.decryptedContents.read(buffer, offset, length);
        }
        if (numBytesRead != -1) {
            this.virtualAvailable -= (long) numBytesRead;
        } else {
            close();
            this.virtualAvailable = 0;
        }
        return numBytesRead;
    }

    public int available() throws IOException {
        abortIfNeeded();
        int available = this.decryptedContents.available();
        return ((long) available) < this.virtualAvailable ? available : (int) this.virtualAvailable;
    }

    public void close() throws IOException {
        if (!this.closed) {
            this.closed = true;
            this.decryptedContents.close();
        }
        abortIfNeeded();
    }

    /* access modifiers changed from: protected */
    public InputStream getWrappedInputStream() {
        return this.decryptedContents;
    }
}
