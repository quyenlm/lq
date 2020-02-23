package com.google.android.gms.internal;

import java.io.IOException;
import java.io.Reader;
import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.List;

public final class pt extends Reader {
    private boolean closed;
    private int zzccA;
    private boolean zzccB;
    private List<String> zzccw;
    private int zzccx;
    private int zzccy;
    private int zzccz;

    public pt() {
        this.zzccw = null;
        this.closed = false;
        this.zzccz = this.zzccx;
        this.zzccA = this.zzccy;
        this.zzccB = false;
        this.zzccw = new ArrayList();
    }

    private final String zzGE() {
        if (this.zzccy < this.zzccw.size()) {
            return this.zzccw.get(this.zzccy);
        }
        return null;
    }

    private final int zzGF() {
        String zzGE = zzGE();
        if (zzGE == null) {
            return 0;
        }
        return zzGE.length() - this.zzccx;
    }

    private final void zzGG() throws IOException {
        if (this.closed) {
            throw new IOException("Stream already closed");
        } else if (!this.zzccB) {
            throw new IOException("Reader needs to be frozen before read operations can be called");
        }
    }

    private final long zzau(long j) {
        long j2 = 0;
        while (this.zzccy < this.zzccw.size() && j2 < j) {
            int zzGF = zzGF();
            long j3 = j - j2;
            if (j3 < ((long) zzGF)) {
                this.zzccx = (int) (((long) this.zzccx) + j3);
                j2 += j3;
            } else {
                j2 += (long) zzGF;
                this.zzccx = 0;
                this.zzccy++;
            }
        }
        return j2;
    }

    public final void close() throws IOException {
        zzGG();
        this.closed = true;
    }

    public final void mark(int i) throws IOException {
        zzGG();
        this.zzccz = this.zzccx;
        this.zzccA = this.zzccy;
    }

    public final boolean markSupported() {
        return true;
    }

    public final int read() throws IOException {
        zzGG();
        String zzGE = zzGE();
        if (zzGE == null) {
            return -1;
        }
        char charAt = zzGE.charAt(this.zzccx);
        zzau(1);
        return charAt;
    }

    public final int read(CharBuffer charBuffer) throws IOException {
        zzGG();
        int remaining = charBuffer.remaining();
        int i = 0;
        String zzGE = zzGE();
        while (remaining > 0 && zzGE != null) {
            int min = Math.min(zzGE.length() - this.zzccx, remaining);
            charBuffer.put(this.zzccw.get(this.zzccy), this.zzccx, this.zzccx + min);
            remaining -= min;
            i += min;
            zzau((long) min);
            zzGE = zzGE();
        }
        if (i > 0 || zzGE != null) {
            return i;
        }
        return -1;
    }

    public final int read(char[] cArr, int i, int i2) throws IOException {
        zzGG();
        int i3 = 0;
        String zzGE = zzGE();
        while (zzGE != null && i3 < i2) {
            int min = Math.min(zzGF(), i2 - i3);
            zzGE.getChars(this.zzccx, this.zzccx + min, cArr, i + i3);
            i3 += min;
            zzau((long) min);
            zzGE = zzGE();
        }
        if (i3 > 0 || zzGE != null) {
            return i3;
        }
        return -1;
    }

    public final boolean ready() throws IOException {
        zzGG();
        return true;
    }

    public final void reset() throws IOException {
        this.zzccx = this.zzccz;
        this.zzccy = this.zzccA;
    }

    public final long skip(long j) throws IOException {
        zzGG();
        return zzau(j);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder();
        for (String append : this.zzccw) {
            sb.append(append);
        }
        return sb.toString();
    }

    public final void zzGD() {
        if (this.zzccB) {
            throw new IllegalStateException("Trying to freeze frozen StringListReader");
        }
        this.zzccB = true;
    }

    public final void zzgN(String str) {
        if (this.zzccB) {
            throw new IllegalStateException("Trying to add string after reading");
        } else if (str.length() > 0) {
            this.zzccw.add(str);
        }
    }
}
