package com.google.android.gms.internal;

import com.google.android.gms.measurement.AppMeasurement;

final class zzchn implements Runnable {
    private /* synthetic */ zzchl zzbtt;
    private /* synthetic */ AppMeasurement.ConditionalUserProperty zzbtu;

    zzchn(zzchl zzchl, AppMeasurement.ConditionalUserProperty conditionalUserProperty) {
        this.zzbtt = zzchl;
        this.zzbtu = conditionalUserProperty;
    }

    public final void run() {
        this.zzbtt.zzb(this.zzbtu);
    }
}
