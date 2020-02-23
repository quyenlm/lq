package com.tencent.gsdk.utils.c;

import java.net.InetAddress;

/* compiled from: AbsSpeedTester */
abstract class a implements c {
    private b a;

    /* access modifiers changed from: protected */
    public abstract short a(InetAddress inetAddress, int i, int i2);

    a() {
    }

    public void a(b bVar) {
        this.a = bVar;
    }

    public short b(InetAddress inetAddress, int i, int i2) {
        short a2 = a(inetAddress, i, i2);
        if (this.a != null) {
            this.a.a(a2);
        }
        return a2;
    }
}
