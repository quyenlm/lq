package com.amazonaws.services.s3.internal;

import com.amazonaws.internal.SdkInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class RepeatableFileInputStream extends SdkInputStream {
    private static final Log log = LogFactory.getLog(RepeatableFileInputStream.class);
    private long bytesReadPastMarkPoint = 0;
    private final File file;
    private FileInputStream fis = null;
    private long markPoint = 0;

    public RepeatableFileInputStream(File file2) throws FileNotFoundException {
        if (file2 == null) {
            throw new IllegalArgumentException("File cannot be null");
        }
        this.fis = new FileInputStream(file2);
        this.file = file2;
    }

    public File getFile() {
        return this.file;
    }

    public void reset() throws IOException {
        this.fis.close();
        abortIfNeeded();
        this.fis = new FileInputStream(this.file);
        long toSkip = this.markPoint;
        while (toSkip > 0) {
            toSkip -= this.fis.skip(toSkip);
        }
        if (log.isDebugEnabled()) {
            log.debug("Reset to mark point " + this.markPoint + " after returning " + this.bytesReadPastMarkPoint + " bytes");
        }
        this.bytesReadPastMarkPoint = 0;
    }

    public boolean markSupported() {
        return true;
    }

    public void mark(int readlimit) {
        abortIfNeeded();
        this.markPoint += this.bytesReadPastMarkPoint;
        this.bytesReadPastMarkPoint = 0;
        if (log.isDebugEnabled()) {
            log.debug("Input stream marked at " + this.markPoint + " bytes");
        }
    }

    public int available() throws IOException {
        abortIfNeeded();
        return this.fis.available();
    }

    public void close() throws IOException {
        this.fis.close();
        abortIfNeeded();
    }

    public int read() throws IOException {
        abortIfNeeded();
        int byteRead = this.fis.read();
        if (byteRead == -1) {
            return -1;
        }
        this.bytesReadPastMarkPoint++;
        return byteRead;
    }

    public long skip(long n) throws IOException {
        abortIfNeeded();
        long skipped = this.fis.skip(n);
        this.bytesReadPastMarkPoint += skipped;
        return skipped;
    }

    public int read(byte[] arg0, int arg1, int arg2) throws IOException {
        abortIfNeeded();
        int count = this.fis.read(arg0, arg1, arg2);
        this.bytesReadPastMarkPoint += (long) count;
        return count;
    }

    public InputStream getWrappedInputStream() {
        return this.fis;
    }
}
