package com.google.android.gms.internal;

import java.lang.reflect.InvocationTargetException;

public final class zzdx extends zzec {
    private static final Object zzrl = new Object();
    private static volatile Long zzrt = null;

    public zzdx(zzdb zzdb, String str, String str2, zzax zzax, int i, int i2) {
        super(zzdb, str, str2, zzax, i, 33);
    }

    /* access modifiers changed from: protected */
    public final void zzT() throws IllegalAccessException, InvocationTargetException {
        if (zzrt == null) {
            synchronized (zzrl) {
                if (zzrt == null) {
                    zzrt = (Long) this.zzrx.invoke((Object) null, new Object[0]);
                }
            }
        }
        synchronized (this.zzro) {
            this.zzro.zzbw = zzrt;
        }
    }
}
