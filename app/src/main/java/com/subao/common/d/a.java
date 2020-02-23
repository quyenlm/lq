package com.subao.common.d;

import java.io.InputStream;

/* compiled from: Buffer */
public class a {
    private byte[] a;
    private int b;

    public a(int i) {
        this.a = new byte[i];
    }

    public int a(InputStream inputStream, int i) {
        int length = i - (this.a.length - this.b);
        if (length > 0) {
            byte[] bArr = new byte[(Math.max(this.a.length / 2, length) + this.a.length)];
            System.arraycopy(this.a, 0, bArr, 0, this.b);
            this.a = bArr;
        }
        int read = inputStream.read(this.a, this.b, i);
        if (read > 0) {
            this.b += read;
        }
        return read;
    }

    public byte[] a() {
        byte[] bArr = new byte[this.b];
        if (this.b > 0) {
            System.arraycopy(this.a, 0, bArr, 0, this.b);
        }
        return bArr;
    }
}
