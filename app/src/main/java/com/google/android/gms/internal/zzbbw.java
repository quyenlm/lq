package com.google.android.gms.internal;

import android.app.Activity;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.internal.zzbo;
import com.google.android.gms.common.util.zza;

public class zzbbw extends zzbba {
    private zzbdb zzaAN;
    private final zza<zzbat<?>> zzaCW = new zza<>();

    private zzbbw(zzbdt zzbdt) {
        super(zzbdt);
        this.zzaEG.zza("ConnectionlessLifecycleHelper", (zzbds) this);
    }

    public static void zza(Activity activity, zzbdb zzbdb, zzbat<?> zzbat) {
        zzn(activity);
        zzbdt zzn = zzn(activity);
        zzbbw zzbbw = (zzbbw) zzn.zza("ConnectionlessLifecycleHelper", zzbbw.class);
        if (zzbbw == null) {
            zzbbw = new zzbbw(zzn);
        }
        zzbbw.zzaAN = zzbdb;
        zzbo.zzb(zzbat, (Object) "ApiKey cannot be null");
        zzbbw.zzaCW.add(zzbat);
        zzbdb.zza(zzbbw);
    }

    private final void zzpS() {
        if (!this.zzaCW.isEmpty()) {
            this.zzaAN.zza(this);
        }
    }

    public final void onResume() {
        super.onResume();
        zzpS();
    }

    public final void onStart() {
        super.onStart();
        zzpS();
    }

    public final void onStop() {
        super.onStop();
        this.zzaAN.zzb(this);
    }

    /* access modifiers changed from: protected */
    public final void zza(ConnectionResult connectionResult, int i) {
        this.zzaAN.zza(connectionResult, i);
    }

    /* access modifiers changed from: package-private */
    public final zza<zzbat<?>> zzpR() {
        return this.zzaCW;
    }

    /* access modifiers changed from: protected */
    public final void zzps() {
        this.zzaAN.zzps();
    }
}
