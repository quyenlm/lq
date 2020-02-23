package com.google.android.gms.internal;

import android.content.Context;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.internal.zzq;
import com.google.android.gms.common.internal.zzz;
import com.google.android.gms.common.zzc;
import com.google.android.gms.panorama.zza;

public final class zzcql extends zzz<zzcqc> {
    public zzcql(Context context, Looper looper, zzq zzq, GoogleApiClient.ConnectionCallbacks connectionCallbacks, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
        super(context, looper, 3, zzq, connectionCallbacks, onConnectionFailedListener);
    }

    public final /* synthetic */ IInterface zzd(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.panorama.internal.IPanoramaService");
        return queryLocalInterface instanceof zzcqc ? (zzcqc) queryLocalInterface : new zzcqd(iBinder);
    }

    /* access modifiers changed from: protected */
    public final String zzdb() {
        return "com.google.android.gms.panorama.service.START";
    }

    /* access modifiers changed from: protected */
    public final String zzdc() {
        return "com.google.android.gms.panorama.internal.IPanoramaService";
    }

    public final zzc[] zzrd() {
        return new zzc[]{zza.zzbzQ};
    }
}
