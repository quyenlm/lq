package com.google.android.gms.internal;

import java.lang.reflect.InvocationTargetException;

public final class zzdy extends zzec {
    public zzdy(zzdb zzdb, String str, String str2, zzax zzax, int i, int i2) {
        super(zzdb, str, str2, zzax, i, 48);
    }

    /* access modifiers changed from: protected */
    public final void zzT() throws IllegalAccessException, InvocationTargetException {
        this.zzro.zzbH = 2;
        boolean booleanValue = ((Boolean) this.zzrx.invoke((Object) null, new Object[]{this.zzpJ.getApplicationContext()})).booleanValue();
        synchronized (this.zzro) {
            if (booleanValue) {
                this.zzro.zzbH = 1;
            } else {
                this.zzro.zzbH = 0;
            }
        }
    }
}
