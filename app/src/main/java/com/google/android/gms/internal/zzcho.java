package com.google.android.gms.internal;

import com.google.android.gms.measurement.AppMeasurement;

final class zzcho implements Runnable {
    private /* synthetic */ zzchl zzbtt;
    private /* synthetic */ AppMeasurement.ConditionalUserProperty zzbtu;

    zzcho(zzchl zzchl, AppMeasurement.ConditionalUserProperty conditionalUserProperty) {
        this.zzbtt = zzchl;
        this.zzbtu = conditionalUserProperty;
    }

    public final void run() {
        this.zzbtt.zzc(this.zzbtu);
    }
}
