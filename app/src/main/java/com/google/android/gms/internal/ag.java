package com.google.android.gms.internal;

import android.content.Context;
import com.google.android.gms.common.internal.zzbo;

public final class ag extends zzcxq {
    private final Context mContext;
    private final zzcvy zzbJZ;

    public ag(Context context, zzcvy zzcvy) {
        this.mContext = (Context) zzbo.zzu(context);
        this.zzbJZ = zzcvy;
    }

    /* access modifiers changed from: protected */
    public final dp<?> zza(zzcwa zzcwa, dp<?>... dpVarArr) {
        String str = null;
        zzbo.zzaf(true);
        if (dpVarArr.length == 0 || dpVarArr[0] == dv.zzbLu) {
            return new eb("");
        }
        Object obj = this.zzbJZ.zzCy().zzBh().get("_ldl");
        if (obj == null) {
            return new eb("");
        }
        dp<?> zzQ = ed.zzQ(obj);
        if (!(zzQ instanceof eb)) {
            return new eb("");
        }
        String value = ((eb) zzQ).value();
        if (!zzcvj.zzV(value, "conv").equals(zzcxp.zzd(dpVarArr[0]))) {
            return new eb("");
        }
        if (dpVarArr.length > 1 && dpVarArr[1] != dv.zzbLu) {
            str = zzcxp.zzd(dpVarArr[1]);
        }
        String zzV = zzcvj.zzV(value, str);
        return zzV != null ? new eb(zzV) : new eb("");
    }
}
