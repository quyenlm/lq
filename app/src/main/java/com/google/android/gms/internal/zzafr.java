package com.google.android.gms.internal;

import android.util.Log;
import com.google.android.gms.ads.internal.zzbs;

@zzzn
public final class zzafr extends zzajc {
    public static void v(String str) {
        if (zzhM()) {
            Log.v("Ads", str);
        }
    }

    public static boolean zzhM() {
        if (zzz(2)) {
            if (((Boolean) zzbs.zzbL().zzd(zzmo.zzEp)).booleanValue()) {
                return true;
            }
        }
        return false;
    }
}
