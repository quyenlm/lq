package com.google.android.gms.ads.internal;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import com.google.android.gms.internal.zzafj;
import com.google.android.gms.internal.zzafr;
import com.google.android.gms.internal.zzagz;
import com.google.android.gms.internal.zzaje;
import com.google.android.gms.internal.zzmo;
import com.google.android.gms.internal.zzzn;

@zzzn
public final class zzac {
    /* access modifiers changed from: private */
    public Context mContext;
    /* access modifiers changed from: private */
    public final Object mLock = new Object();

    public final void zza(Context context, zzaje zzaje, String str, @Nullable Runnable runnable) {
        zza(context, zzaje, true, (zzafj) null, str, (String) null, runnable);
    }

    /* access modifiers changed from: package-private */
    public final void zza(Context context, zzaje zzaje, boolean z, @Nullable zzafj zzafj, String str, @Nullable String str2, @Nullable Runnable runnable) {
        boolean z2;
        if (zzafj == null) {
            z2 = true;
        } else {
            z2 = (((zzbs.zzbF().currentTimeMillis() - zzafj.zzhi()) > ((Long) zzbs.zzbL().zzd(zzmo.zzFQ)).longValue() ? 1 : ((zzbs.zzbF().currentTimeMillis() - zzafj.zzhi()) == ((Long) zzbs.zzbL().zzd(zzmo.zzFQ)).longValue() ? 0 : -1)) > 0) || !zzafj.zzhj();
        }
        if (z2) {
            if (context == null) {
                zzafr.zzaT("Context not provided to fetch application settings");
            } else if (!TextUtils.isEmpty(str) || !TextUtils.isEmpty(str2)) {
                this.mContext = context;
                zzagz.zzZr.post(new zzae(this, zzbs.zzbz().zze(context, zzaje), new zzad(this, runnable), str, str2, z, context));
            } else {
                zzafr.zzaT("App settings could not be fetched. Required parameters missing");
            }
        }
    }
}
