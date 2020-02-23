package com.google.android.gms.internal;

import android.content.Context;
import android.telephony.TelephonyManager;
import com.google.android.gms.common.internal.zzbo;

public final class al implements zzcxo {
    private final Context mContext;

    public al(Context context) {
        this.mContext = (Context) zzbo.zzu(context);
    }

    public final dp<?> zzb(zzcwa zzcwa, dp<?>... dpVarArr) {
        String networkOperatorName;
        boolean z = true;
        zzbo.zzaf(dpVarArr != null);
        if (dpVarArr.length != 0) {
            z = false;
        }
        zzbo.zzaf(z);
        TelephonyManager telephonyManager = (TelephonyManager) this.mContext.getSystemService("phone");
        return (telephonyManager == null || (networkOperatorName = telephonyManager.getNetworkOperatorName()) == null) ? dv.zzbLu : new eb(networkOperatorName);
    }
}
