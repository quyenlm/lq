package com.google.android.gms.cast;

import android.os.RemoteException;
import com.google.android.gms.cast.Cast;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.internal.zzaxx;
import com.google.android.gms.internal.zzbaz;

final class zzi extends Cast.zza {
    private /* synthetic */ String val$sessionId;
    private /* synthetic */ String zzaoR;
    private /* synthetic */ zzz zzaoT = null;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzi(Cast.CastApi.zza zza, GoogleApiClient googleApiClient, String str, String str2, zzz zzz) {
        super(googleApiClient);
        this.zzaoR = str;
        this.val$sessionId = str2;
    }

    public final void zza(zzaxx zzaxx) throws RemoteException {
        try {
            zzaxx.zza(this.zzaoR, this.val$sessionId, this.zzaoT, (zzbaz<Cast.ApplicationConnectionResult>) this);
        } catch (IllegalStateException e) {
            zzad(2001);
        }
    }
}
