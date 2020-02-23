package com.google.android.gms.internal;

final class zzcjh extends zzcer {
    private /* synthetic */ zzcjg zzbux;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzcjh(zzcjg zzcjg, zzcgl zzcgl) {
        super(zzcgl);
        this.zzbux = zzcjg;
    }

    public final void run() {
        this.zzbux.zzwF().zzyD().log("Sending upload intent from DelayedRunnable");
        this.zzbux.zzzr();
    }
}
