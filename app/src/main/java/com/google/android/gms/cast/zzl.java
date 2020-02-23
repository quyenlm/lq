package com.google.android.gms.cast;

import android.app.PendingIntent;
import android.os.RemoteException;
import android.text.TextUtils;
import com.google.android.gms.cast.Cast;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.internal.zzaxx;
import com.google.android.gms.internal.zzayi;
import com.google.android.gms.internal.zzbaz;

final class zzl extends zzayi {
    private /* synthetic */ String val$sessionId;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzl(Cast.CastApi.zza zza, GoogleApiClient googleApiClient, String str) {
        super(googleApiClient);
        this.val$sessionId = str;
    }

    public final void zza(zzaxx zzaxx) throws RemoteException {
        if (TextUtils.isEmpty(this.val$sessionId)) {
            setResult(zzb(new Status(2001, "IllegalArgument: sessionId cannot be null or empty", (PendingIntent) null)));
            return;
        }
        try {
            zzaxx.zza(this.val$sessionId, (zzbaz<Status>) this);
        } catch (IllegalStateException e) {
            zzad(2001);
        }
    }
}
