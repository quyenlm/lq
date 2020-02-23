package com.google.android.gms.cast;

import android.os.RemoteException;
import com.google.android.gms.cast.Cast;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.internal.zzaxx;
import com.google.android.gms.internal.zzbaz;

final class zzh extends Cast.zza {
    private /* synthetic */ String zzaoR;
    private /* synthetic */ LaunchOptions zzaoS;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzh(Cast.CastApi.zza zza, GoogleApiClient googleApiClient, String str, LaunchOptions launchOptions) {
        super(googleApiClient);
        this.zzaoR = str;
        this.zzaoS = launchOptions;
    }

    public final void zza(zzaxx zzaxx) throws RemoteException {
        try {
            zzaxx.zza(this.zzaoR, this.zzaoS, (zzbaz<Cast.ApplicationConnectionResult>) this);
        } catch (IllegalStateException e) {
            zzad(2001);
        }
    }
}
