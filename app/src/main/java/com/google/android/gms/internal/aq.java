package com.google.android.gms.internal;

import android.content.Context;
import android.provider.Settings;
import com.google.android.gms.common.internal.zzbo;

public final class aq implements zzcxo {
    private final Context mContext;

    public aq(Context context) {
        this.mContext = context;
    }

    public final dp<?> zzb(zzcwa zzcwa, dp<?>... dpVarArr) {
        boolean z = true;
        zzbo.zzaf(dpVarArr != null);
        if (dpVarArr.length != 0) {
            z = false;
        }
        zzbo.zzaf(z);
        String string = Settings.Secure.getString(this.mContext.getContentResolver(), "android_id");
        if (string == null) {
            string = "";
        }
        return new eb(string);
    }
}
