package com.google.android.gms.internal;

import java.lang.reflect.InvocationTargetException;

public final class zzdi extends zzec {
    public zzdi(zzdb zzdb, String str, String str2, zzax zzax, int i, int i2) {
        super(zzdb, str, str2, zzax, i, 3);
    }

    /* access modifiers changed from: protected */
    public final void zzT() throws IllegalAccessException, InvocationTargetException {
        synchronized (this.zzro) {
            this.zzro.zzaZ = -1L;
            this.zzro.zzaZ = (Long) this.zzrx.invoke((Object) null, new Object[]{this.zzpJ.getContext()});
        }
    }
}
