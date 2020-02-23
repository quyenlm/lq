package com.tencent.msdk.a.a;

import android.content.Context;

public abstract class g {
    protected Context a = null;

    protected g(Context context) {
        this.a = context;
    }

    public static String c() {
        return i.d("6X8Y4XdM2Vhvn0I=");
    }

    public static String d() {
        return i.d("6X8Y4XdM2Vhvn0KfzcEatGnWaNU=");
    }

    private void d(String str) {
        if (a()) {
            a(b(str));
        }
    }

    private String g() {
        if (a()) {
            return c(b());
        }
        return null;
    }

    public void a(d dVar) {
        if (dVar != null) {
            d(dVar.toString());
        }
    }

    /* access modifiers changed from: protected */
    public abstract void a(String str);

    /* access modifiers changed from: protected */
    public abstract boolean a();

    /* access modifiers changed from: protected */
    public abstract String b();

    /* access modifiers changed from: protected */
    public String b(String str) {
        return i.e(str);
    }

    /* access modifiers changed from: protected */
    public String c(String str) {
        return i.d(str);
    }

    public d e() {
        String g = g();
        if (g != null) {
            return d.a(g);
        }
        return null;
    }

    /* access modifiers changed from: protected */
    public String f() {
        return i.d("4kU71lN96TJUomD1vOU9lgj9Tw==");
    }
}
