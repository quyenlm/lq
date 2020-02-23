package com.google.android.gms.internal;

import com.google.android.gms.common.internal.zzbo;
import java.io.UnsupportedEncodingException;

public final class v extends zzcxq {
    /* access modifiers changed from: protected */
    public final dp<?> zza(zzcwa zzcwa, dp<?>... dpVarArr) {
        zzbo.zzaf(true);
        try {
            return new eb(u.decode(zzcxp.zzd(dpVarArr.length > 0 ? (dp) zzbo.zzu(dpVarArr[0]) : dv.zzbLu), ""));
        } catch (UnsupportedEncodingException e) {
            return dv.zzbLu;
        }
    }
}
