package com.google.android.gms.internal;

import android.content.Context;
import android.os.DeadObjectException;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import com.google.android.gms.common.internal.zzd;
import com.google.android.gms.common.internal.zzf;
import com.google.android.gms.common.internal.zzg;

@zzzn
public final class zzaad extends zzd<zzaam> {
    private int zzSx;

    public zzaad(Context context, Looper looper, zzf zzf, zzg zzg, int i) {
        super(context, looper, 8, zzf, zzg, (String) null);
        this.zzSx = i;
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ IInterface zzd(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.ads.internal.request.IAdRequestService");
        return queryLocalInterface instanceof zzaam ? (zzaam) queryLocalInterface : new zzaao(iBinder);
    }

    /* access modifiers changed from: protected */
    public final String zzdb() {
        return "com.google.android.gms.ads.service.START";
    }

    /* access modifiers changed from: protected */
    public final String zzdc() {
        return "com.google.android.gms.ads.internal.request.IAdRequestService";
    }

    public final zzaam zzgC() throws DeadObjectException {
        return (zzaam) super.zzrf();
    }
}
