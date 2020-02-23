package com.tencent.mna.base.c;

import com.tencent.mna.base.utils.h;

/* compiled from: ReporterAbstract */
abstract class e implements d {
    private boolean a = true;

    /* access modifiers changed from: package-private */
    public abstract void a();

    /* access modifiers changed from: package-private */
    public abstract d b(String str, String str2);

    /* access modifiers changed from: package-private */
    public abstract d b(String str, String str2, String str3);

    e() {
    }

    public final d a(String str, String str2) {
        return !h() ? this : b(str, str2);
    }

    public final d a(String str, String str2, String str3) {
        return !h() ? this : b(str, str2, str3);
    }

    public final void g() {
        if (h()) {
            a();
        }
    }

    public final boolean h() {
        return this.a;
    }

    public final void a(boolean z) {
        h.a("Reporter usereport:" + z);
        this.a = z;
    }
}
