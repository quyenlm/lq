package com.google.android.gms.internal;

import android.content.Context;
import com.google.android.gms.common.internal.zzbo;

public final class bc extends zzcxq {
    private final Context mContext;
    private final zzcvy zzbJZ;

    public bc(Context context, zzcvy zzcvy) {
        this.mContext = context;
        this.zzbJZ = zzcvy;
    }

    /* access modifiers changed from: protected */
    public final dp<?> zza(zzcwa zzcwa, dp<?>... dpVarArr) {
        zzbo.zzaf(true);
        zzbo.zzaf(dpVarArr.length > 0);
        Object obj = this.zzbJZ.zzCy().zzBh().get(zzcxp.zzd(dpVarArr[0]));
        Object obj2 = obj;
        if (obj == null) {
            obj2 = obj;
            if (dpVarArr.length > 1) {
                obj2 = dpVarArr[1];
            }
        }
        return ed.zzQ(obj2);
    }
}
