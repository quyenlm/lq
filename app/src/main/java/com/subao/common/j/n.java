package com.subao.common.j;

import android.support.annotation.Nullable;
import com.subao.common.i.d;
import com.subao.common.j.a;

/* compiled from: ResponseCallback */
public abstract class n {
    protected final int a;
    protected final int b;
    @Nullable
    private final d.b c;

    /* access modifiers changed from: protected */
    public abstract String a();

    /* access modifiers changed from: protected */
    public abstract void a(int i, byte[] bArr);

    /* access modifiers changed from: protected */
    public abstract void b(int i, byte[] bArr);

    public n(@Nullable d.b bVar, int i, int i2) {
        this.c = bVar;
        this.a = i;
        this.b = i2;
    }

    public final void a(a.c cVar) {
        if (cVar == null) {
            d(-3, (byte[]) null);
        } else {
            c(cVar.a, cVar.b);
        }
    }

    public final void c(int i, byte[] bArr) {
        if (!a(i)) {
            d(i, bArr);
        } else if (bArr != null) {
            a(i, bArr);
        } else {
            d(-4, (byte[]) null);
        }
    }

    public static boolean a(int i) {
        return i >= 200 && i < 300;
    }

    /* access modifiers changed from: protected */
    public final void d(int i, byte[] bArr) {
        if (this.c != null) {
            this.c.a(a(), Integer.toString(i));
        }
        b(i, bArr);
    }

    public final void b() {
        if (this.c != null) {
            this.c.a(a(), "io");
        }
        b(-2, (byte[]) null);
    }
}
