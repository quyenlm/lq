package com.google.android.gms.internal;

import com.tencent.component.debug.TraceFormat;
import java.lang.reflect.InvocationTargetException;

public final class zzdk extends zzec {
    private static volatile String zzrk = null;
    private static final Object zzrl = new Object();

    public zzdk(zzdb zzdb, String str, String str2, zzax zzax, int i, int i2) {
        super(zzdb, str, str2, zzax, i, 29);
    }

    /* access modifiers changed from: protected */
    public final void zzT() throws IllegalAccessException, InvocationTargetException {
        this.zzro.zzbt = TraceFormat.STR_ERROR;
        if (zzrk == null) {
            synchronized (zzrl) {
                if (zzrk == null) {
                    zzrk = (String) this.zzrx.invoke((Object) null, new Object[]{this.zzpJ.getContext()});
                }
            }
        }
        synchronized (this.zzro) {
            this.zzro.zzbt = zzbt.zza(zzrk.getBytes(), true);
        }
    }
}
