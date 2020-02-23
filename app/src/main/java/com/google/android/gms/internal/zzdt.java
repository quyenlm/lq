package com.google.android.gms.internal;

import com.tencent.component.debug.TraceFormat;
import java.lang.reflect.InvocationTargetException;

public final class zzdt extends zzec {
    private static volatile String zzaY = null;
    private static final Object zzrl = new Object();

    public zzdt(zzdb zzdb, String str, String str2, zzax zzax, int i, int i2) {
        super(zzdb, str, str2, zzax, i, 1);
    }

    /* access modifiers changed from: protected */
    public final void zzT() throws IllegalAccessException, InvocationTargetException {
        this.zzro.zzaY = TraceFormat.STR_ERROR;
        if (zzaY == null) {
            synchronized (zzrl) {
                if (zzaY == null) {
                    zzaY = (String) this.zzrx.invoke((Object) null, new Object[0]);
                }
            }
        }
        synchronized (this.zzro) {
            this.zzro.zzaY = zzaY;
        }
    }
}
