package com.tencent.mna.b.a.c;

import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.tencent.component.debug.FileTracerConfig;
import java.util.ArrayList;
import java.util.Vector;

/* compiled from: DelaysInfo */
public class c {
    private ArrayList<Integer> a = new ArrayList<>();
    private long b = 0;
    private int c = -1;
    private int d = 0;

    public c(int i) {
        this.c = i;
    }

    public synchronized void a(int i) {
        if (FileTracerConfig.FOREVER - this.b < ((long) i)) {
            throw new ArithmeticException("addDelay Long overflow");
        }
        this.a.add(Integer.valueOf(i));
        if (this.c <= 0 || i <= this.c) {
            this.b += (long) i;
        } else {
            this.b += (long) this.c;
            this.d++;
        }
    }

    public synchronized void a() {
        this.a.clear();
        this.b = 0;
        this.d = 0;
    }

    public synchronized double b() {
        int size;
        size = this.a.size();
        return size > 0 ? ((double) this.b) / ((double) size) : FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE;
    }

    public synchronized int c() {
        return this.a.size();
    }

    public synchronized int d() {
        int i;
        int size = this.a.size();
        if (size > 0) {
            i = this.a.get(size - 1).intValue();
        } else {
            i = -1;
        }
        return i;
    }

    public synchronized int e() {
        return this.d;
    }

    public synchronized double f() {
        double d2;
        double sqrt;
        double d3 = FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE;
        synchronized (this) {
            int size = this.a.size();
            if (size > 0) {
                d2 = ((double) this.b) / ((double) size);
            } else {
                d2 = 0.0d;
            }
            for (int i = size - 1; i >= 0; i--) {
                d3 += (((double) this.a.get(i).intValue()) - d2) * (((double) this.a.get(i).intValue()) - d2);
            }
            sqrt = Math.sqrt(d3 / ((double) size));
        }
        return sqrt;
    }

    public synchronized Vector<Integer> g() {
        return new Vector<>(this.a);
    }

    public synchronized String a(String str) {
        StringBuilder sb;
        int size = this.a.size();
        sb = new StringBuilder(size * 2);
        for (int i = 0; i < size; i++) {
            sb.append(this.a.get(i));
            if (i < size - 1) {
                sb.append(str);
            }
        }
        return sb.toString();
    }
}
