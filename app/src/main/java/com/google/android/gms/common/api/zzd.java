package com.google.android.gms.common.api;

import android.os.Looper;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.internal.zzbo;
import com.google.android.gms.internal.zzbas;
import com.google.android.gms.internal.zzbem;

public final class zzd {
    private zzbem zzaAM;
    private Looper zzrM;

    public final zzd zza(Looper looper) {
        zzbo.zzb(looper, (Object) "Looper must not be null.");
        this.zzrM = looper;
        return this;
    }

    public final zzd zza(zzbem zzbem) {
        zzbo.zzb(zzbem, (Object) "StatusExceptionMapper must not be null.");
        this.zzaAM = zzbem;
        return this;
    }

    public final GoogleApi.zza zzpj() {
        if (this.zzaAM == null) {
            this.zzaAM = new zzbas();
        }
        if (this.zzrM == null) {
            if (Looper.myLooper() != null) {
                this.zzrM = Looper.myLooper();
            } else {
                this.zzrM = Looper.getMainLooper();
            }
        }
        return new GoogleApi.zza(this.zzaAM, this.zzrM);
    }
}
