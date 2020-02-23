package com.google.android.gms.internal;

import android.content.Context;
import android.content.pm.PackageManager;
import com.google.android.gms.common.internal.zzbo;

public final class aj implements zzcxo {
    private final Context mContext;

    public aj(Context context) {
        this.mContext = (Context) zzbo.zzu(context);
    }

    public final dp<?> zzb(zzcwa zzcwa, dp<?>... dpVarArr) {
        boolean z = true;
        zzbo.zzaf(dpVarArr != null);
        if (dpVarArr.length != 0) {
            z = false;
        }
        zzbo.zzaf(z);
        try {
            return new dt(Double.valueOf((double) this.mContext.getPackageManager().getPackageInfo(this.mContext.getPackageName(), 0).versionCode));
        } catch (PackageManager.NameNotFoundException e) {
            String valueOf = String.valueOf(this.mContext.getPackageName());
            String valueOf2 = String.valueOf(e.getMessage());
            zzcvk.e(new StringBuilder(String.valueOf(valueOf).length() + 25 + String.valueOf(valueOf2).length()).append("Package name ").append(valueOf).append(" not found. ").append(valueOf2).toString());
            return dv.zzbLu;
        }
    }
}
