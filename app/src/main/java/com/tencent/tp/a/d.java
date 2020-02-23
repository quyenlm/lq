package com.tencent.tp.a;

import android.content.Context;
import android.view.View;
import com.tencent.tp.a.a;

public class d extends a {
    public d(Context context, String str, String str2, String str3, String str4, String str5, String str6, int i, a.C0032a aVar) {
        super(context, str, str2, str3, str4, str5, str6, i, true, aVar);
        d();
    }

    /* access modifiers changed from: protected */
    public void f() {
        if (this.q >= 0) {
            if (this.t != null) {
                this.t.setText(s());
                if (this.q == 0) {
                    h.a((View) this.t, h.g(this.e).a);
                    this.r = false;
                }
            }
            this.q--;
            return;
        }
        e();
    }

    /* access modifiers changed from: protected */
    public String s() {
        if (this.q < 1) {
            return this.o;
        }
        return this.o + h.a + this.q + h.b;
    }
}
