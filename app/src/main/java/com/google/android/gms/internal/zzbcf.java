package com.google.android.gms.internal;

import android.os.Looper;
import android.support.annotation.NonNull;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.internal.zzbo;
import com.google.android.gms.common.internal.zzj;
import java.lang.ref.WeakReference;

final class zzbcf implements zzj {
    /* access modifiers changed from: private */
    public final boolean zzaCj;
    private final WeakReference<zzbcd> zzaDq;
    private final Api<?> zzayW;

    public zzbcf(zzbcd zzbcd, Api<?> api, boolean z) {
        this.zzaDq = new WeakReference<>(zzbcd);
        this.zzayW = api;
        this.zzaCj = z;
    }

    public final void zzf(@NonNull ConnectionResult connectionResult) {
        boolean z = false;
        zzbcd zzbcd = (zzbcd) this.zzaDq.get();
        if (zzbcd != null) {
            if (Looper.myLooper() == zzbcd.zzaCZ.zzaCl.getLooper()) {
                z = true;
            }
            zzbo.zza(z, (Object) "onReportServiceBinding must be called on the GoogleApiClient handler thread");
            zzbcd.zzaCv.lock();
            try {
                if (zzbcd.zzan(0)) {
                    if (!connectionResult.isSuccess()) {
                        zzbcd.zzb(connectionResult, this.zzayW, this.zzaCj);
                    }
                    if (zzbcd.zzpW()) {
                        zzbcd.zzpX();
                    }
                    zzbcd.zzaCv.unlock();
                }
            } finally {
                zzbcd.zzaCv.unlock();
            }
        }
    }
}
