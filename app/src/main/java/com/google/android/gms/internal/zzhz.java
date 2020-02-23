package com.google.android.gms.internal;

import android.content.Context;
import android.os.DeadObjectException;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import com.google.android.gms.common.internal.zzd;
import com.google.android.gms.common.internal.zzf;
import com.google.android.gms.common.internal.zzg;
import com.tencent.smtt.sdk.TbsListener;

@zzzn
public final class zzhz extends zzd<zzid> {
    zzhz(Context context, Looper looper, zzf zzf, zzg zzg) {
        super(context, looper, TbsListener.ErrorCode.DOWNLOAD_RETRYTIMES302_EXCEED, zzf, zzg, (String) null);
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ IInterface zzd(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.ads.internal.cache.ICacheService");
        return queryLocalInterface instanceof zzid ? (zzid) queryLocalInterface : new zzie(iBinder);
    }

    /* access modifiers changed from: protected */
    public final String zzdb() {
        return "com.google.android.gms.ads.service.CACHE";
    }

    /* access modifiers changed from: protected */
    public final String zzdc() {
        return "com.google.android.gms.ads.internal.cache.ICacheService";
    }

    public final zzid zzdd() throws DeadObjectException {
        return (zzid) super.zzrf();
    }
}
