package com.tencent.mna.b.a.c;

import java.util.List;
import java.util.Vector;

/* compiled from: FpsMoveClickInfo */
public class e extends d {
    private List<String> a = new Vector();
    private List<String> b = new Vector();
    private List<String> c = new Vector();

    public e() {
        this.e = false;
    }

    public void a(String str) {
        if (!this.e) {
            this.a.add(str);
        }
    }

    public void b(String str) {
        if (!this.e) {
            this.b.add(str);
        }
    }

    public void c(String str) {
        if (!this.e) {
            this.c.add(str);
        }
    }

    public List<String> a() {
        return this.a;
    }

    public List<String> b() {
        return this.b;
    }

    public List<String> c() {
        return this.c;
    }

    public void e() {
        super.e();
        this.a.clear();
        this.b.clear();
        this.c.clear();
    }
}
