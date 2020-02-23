package com.tencent.mna.b.a.c;

import java.util.List;
import java.util.Vector;

/* compiled from: NetsInfo */
public class f extends d {
    private List<String> a = new Vector();
    private int b;

    public f(int i) {
        this.b = i;
        this.e = false;
    }

    public void a(int i) {
        if (!this.e && this.a.size() < this.b) {
            this.a.add(String.valueOf(i));
        }
    }

    public void a(int i, int i2) {
        if (!this.e && this.a.size() < this.b) {
            this.a.add(String.valueOf(i) + '_' + i2);
        }
    }

    public List<String> a() {
        return this.a;
    }

    public void e() {
        super.e();
        this.a.clear();
    }
}
