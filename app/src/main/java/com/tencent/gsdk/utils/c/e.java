package com.tencent.gsdk.utils.c;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/* compiled from: SpeedTestStatHelper */
class e implements b {
    final ReentrantReadWriteLock.ReadLock a = this.g.readLock();
    final ReentrantReadWriteLock.WriteLock b = this.g.writeLock();
    List<Short> c = Collections.emptyList();
    List<Short> d = Collections.emptyList();
    long e = 0;
    long f = 0;
    private final ReentrantReadWriteLock g = new ReentrantReadWriteLock();
    private int h;

    e(int i) {
        this.h = i;
    }

    public void a(short s) {
        if (-1 == s) {
            s = (short) this.h;
        }
        Short valueOf = Short.valueOf(s);
        this.b.lock();
        if (this.c == Collections.emptyList()) {
            this.c = new ArrayList();
        }
        this.c.add(valueOf);
        this.e += (long) s;
        if (s != -1 && s < this.h) {
            if (this.d == Collections.emptyList()) {
                this.d = new ArrayList();
            }
            this.d.add(valueOf);
            this.f += (long) s;
        }
        this.b.unlock();
    }
}
