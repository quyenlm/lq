package com.google.android.gms.internal;

import android.content.Context;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import android.support.annotation.NonNull;
import com.google.android.gms.common.internal.zzd;
import com.google.android.gms.common.internal.zzf;
import com.google.android.gms.common.internal.zzg;

public final class zzcfk extends zzd<zzcfd> {
    public zzcfk(Context context, Looper looper, zzf zzf, zzg zzg) {
        super(context, looper, 93, zzf, zzg, (String) null);
    }

    public final /* synthetic */ IInterface zzd(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.measurement.internal.IMeasurementService");
        return queryLocalInterface instanceof zzcfd ? (zzcfd) queryLocalInterface : new zzcff(iBinder);
    }

    /* access modifiers changed from: protected */
    @NonNull
    public final String zzdb() {
        return "com.google.android.gms.measurement.START";
    }

    /* access modifiers changed from: protected */
    @NonNull
    public final String zzdc() {
        return "com.google.android.gms.measurement.internal.IMeasurementService";
    }
}
