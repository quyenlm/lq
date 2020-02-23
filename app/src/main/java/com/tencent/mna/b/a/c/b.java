package com.tencent.mna.b.a.c;

import java.util.List;
import java.util.Vector;

/* compiled from: CpuMemGpusInfo */
public class b extends d {
    public long a = 0;
    public long b = 0;
    public long c = 0;
    public long d = 0;
    private List<String> f = new Vector();
    private List<String> g = new Vector();
    private List<String> h = new Vector();
    private int i;

    public b(int i2) {
        this.i = i2;
        this.e = false;
    }

    public void a(int i2, long j, long j2, int i3, long j3, long j4) {
        if (!this.e) {
            this.a = j;
            this.b = j2;
            this.c = j3;
            this.d = j4;
            if (this.f.size() < this.i) {
                StringBuilder sb = new StringBuilder();
                sb.append(i2).append('_').append(i3);
                this.f.add(sb.toString());
            }
        }
    }

    public void a(int i2, int i3) {
        if (!this.e && this.g.size() < this.i) {
            StringBuilder sb = new StringBuilder();
            sb.append(i2).append('_').append(i3);
            this.g.add(sb.toString());
        }
    }

    public void a(int i2) {
        if (!this.e && this.h.size() < this.i) {
            this.h.add(String.valueOf(i2));
        }
    }

    public boolean a() {
        return this.a <= 0 || this.b <= 0 || this.c <= 0 || this.d <= 0;
    }

    public List<String> b() {
        return this.f;
    }

    public List<String> c() {
        return this.g;
    }

    public List<String> d() {
        return this.h;
    }

    public void e() {
        super.e();
        this.f.clear();
        this.g.clear();
        this.h.clear();
        this.a = 0;
        this.b = 0;
        this.c = 0;
        this.d = 0;
    }
}
