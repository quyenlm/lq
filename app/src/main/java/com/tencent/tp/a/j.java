package com.tencent.tp.a;

import android.content.Context;
import com.tencent.tp.a.a;

public class j extends a {
    public j(Context context, String str, String str2, String str3, String str4, int i, a.C0032a aVar) {
        super(context, str, str2, str3, str4, (String) null, (String) null, i, false, aVar);
        d();
    }

    /* access modifiers changed from: protected */
    public void f() {
        if (this.q >= 0) {
            if (this.s != null) {
                this.s.setText(r());
            }
            this.q--;
            return;
        }
        e();
        this.v.c(this);
    }

    /* access modifiers changed from: protected */
    public int n() {
        return h.e;
    }

    /* access modifiers changed from: protected */
    public String r() {
        return this.n + h.a + this.q + h.b;
    }
}
