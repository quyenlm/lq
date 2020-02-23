package com.tencent.bugly.proguard;

import java.util.HashMap;
import java.util.Map;

/* compiled from: BUGLY */
public final class as extends k implements Cloneable {
    private static ar m = new ar();
    private static Map<String, String> n = new HashMap();
    private static /* synthetic */ boolean o;
    public boolean a = true;
    public boolean b = true;
    public boolean c = true;
    public String d = "";
    public String e = "";
    public ar f = null;
    public Map<String, String> g = null;
    public long h = 0;
    public int i = 0;
    private String j = "";
    private String k = "";
    private int l = 0;

    static {
        boolean z;
        if (!as.class.desiredAssertionStatus()) {
            z = true;
        } else {
            z = false;
        }
        o = z;
        n.put("", "");
    }

    public final boolean equals(Object o2) {
        if (o2 == null) {
            return false;
        }
        as asVar = (as) o2;
        if (!l.a(this.a, asVar.a) || !l.a(this.b, asVar.b) || !l.a(this.c, asVar.c) || !l.a((Object) this.d, (Object) asVar.d) || !l.a((Object) this.e, (Object) asVar.e) || !l.a((Object) this.f, (Object) asVar.f) || !l.a((Object) this.g, (Object) asVar.g) || !l.a(this.h, asVar.h) || !l.a((Object) this.j, (Object) asVar.j) || !l.a((Object) this.k, (Object) asVar.k) || !l.a(this.l, asVar.l) || !l.a(this.i, asVar.i)) {
            return false;
        }
        return true;
    }

    public final int hashCode() {
        try {
            throw new Exception("Need define key first!");
        } catch (Exception e2) {
            e2.printStackTrace();
            return 0;
        }
    }

    public final Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e2) {
            if (o) {
                return null;
            }
            throw new AssertionError();
        }
    }

    public final void a(j jVar) {
        jVar.a(this.a, 0);
        jVar.a(this.b, 1);
        jVar.a(this.c, 2);
        if (this.d != null) {
            jVar.a(this.d, 3);
        }
        if (this.e != null) {
            jVar.a(this.e, 4);
        }
        if (this.f != null) {
            jVar.a((k) this.f, 5);
        }
        if (this.g != null) {
            jVar.a(this.g, 6);
        }
        jVar.a(this.h, 7);
        if (this.j != null) {
            jVar.a(this.j, 8);
        }
        if (this.k != null) {
            jVar.a(this.k, 9);
        }
        jVar.a(this.l, 10);
        jVar.a(this.i, 11);
    }

    public final void a(i iVar) {
        boolean z = this.a;
        this.a = iVar.a(0, true);
        boolean z2 = this.b;
        this.b = iVar.a(1, true);
        boolean z3 = this.c;
        this.c = iVar.a(2, true);
        this.d = iVar.b(3, false);
        this.e = iVar.b(4, false);
        this.f = (ar) iVar.a((k) m, 5, false);
        this.g = (Map) iVar.a(n, 6, false);
        this.h = iVar.a(this.h, 7, false);
        this.j = iVar.b(8, false);
        this.k = iVar.b(9, false);
        this.l = iVar.a(this.l, 10, false);
        this.i = iVar.a(this.i, 11, false);
    }

    public final void a(StringBuilder sb, int i2) {
        h hVar = new h(sb, i2);
        hVar.a(this.a, "enable");
        hVar.a(this.b, "enableUserInfo");
        hVar.a(this.c, "enableQuery");
        hVar.a(this.d, "url");
        hVar.a(this.e, "expUrl");
        hVar.a((k) this.f, "security");
        hVar.a(this.g, "valueMap");
        hVar.a(this.h, "strategylastUpdateTime");
        hVar.a(this.j, "httpsUrl");
        hVar.a(this.k, "httpsExpUrl");
        hVar.a(this.l, "eventRecordCount");
        hVar.a(this.i, "eventTimeInterval");
    }
}
