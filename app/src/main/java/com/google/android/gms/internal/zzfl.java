package com.google.android.gms.internal;

import android.database.ContentObserver;
import android.os.Handler;

final class zzfl extends ContentObserver {
    private /* synthetic */ zzfi zzxh;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public zzfl(zzfi zzfi, Handler handler) {
        super(handler);
        this.zzxh = zzfi;
    }

    public final void onChange(boolean z) {
        super.onChange(z);
        this.zzxh.zzcp();
    }
}
