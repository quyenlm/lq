package com.tencent.tp;

import android.content.Context;
import android.os.Process;

class u {
    boolean a;

    public u(boolean z, boolean z2, boolean z3) {
        a();
        this.a = z;
    }

    private void a() {
        this.a = true;
    }

    public void a(Context context) {
        t tVar;
        Process.setThreadPriority(10);
        try {
            tVar = new t();
        } catch (Throwable th) {
            tVar = null;
        }
        if (tVar != null) {
            try {
                tVar.a(context);
            } catch (Throwable th2) {
                tVar = null;
            }
            if (this.a) {
                try {
                    tVar.a();
                } catch (Throwable th3) {
                }
            }
        }
    }
}
