package com.tencent.qt.alg.network;

public interface ContentHandler {
    void complete(int i, int i2);

    boolean handle(byte[] bArr, int i, int i2);

    boolean prepare(long j, long j2);
}
