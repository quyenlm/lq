package com.tencent.component.net.download.multiplex.http;

import java.io.IOException;
import java.io.InputStream;

public class MttInputStream {
    private int mFlow;
    private InputStream mInputStream;
    private boolean mStat;

    public MttInputStream(InputStream inputStream) {
        this.mInputStream = inputStream;
        this.mStat = true;
        this.mFlow = 0;
    }

    public MttInputStream(InputStream inputStream, boolean stat) {
        this.mInputStream = inputStream;
        this.mStat = stat;
        this.mFlow = 0;
    }

    public InputStream getImpl() {
        return this.mInputStream;
    }

    public int read(byte[] b, int offset, int length) throws IOException {
        int ret = this.mInputStream.read(b, offset, length);
        if (this.mStat) {
            this.mFlow += ret;
        }
        return ret;
    }

    public int getFlow() {
        return this.mFlow;
    }

    public void close() throws IOException {
        this.mInputStream.close();
    }
}
