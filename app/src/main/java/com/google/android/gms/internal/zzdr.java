package com.google.android.gms.internal;

import java.lang.reflect.InvocationTargetException;

public final class zzdr extends zzec {
    private static final Object zzrl = new Object();
    private static volatile Long zzrp = null;

    public zzdr(zzdb zzdb, String str, String str2, zzax zzax, int i, int i2) {
        super(zzdb, str, str2, zzax, i, 22);
    }

    /* access modifiers changed from: protected */
    public final void zzT() throws IllegalAccessException, InvocationTargetException {
        if (zzrp == null) {
            synchronized (zzrl) {
                if (zzrp == null) {
                    zzrp = (Long) this.zzrx.invoke((Object) null, new Object[0]);
                }
            }
        }
        synchronized (this.zzro) {
            this.zzro.zzbr = zzrp;
        }
    }
}
