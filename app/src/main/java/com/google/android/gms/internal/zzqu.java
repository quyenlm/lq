package com.google.android.gms.internal;

import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import com.google.android.gms.ads.internal.zzbs;
import java.util.HashMap;
import java.util.Map;

final class zzqu implements zzrd {
    zzqu() {
    }

    public final void zza(zzaka zzaka, Map<String, String> map) {
        zzbs.zzbz();
        DisplayMetrics zza = zzagz.zza((WindowManager) zzaka.getContext().getSystemService("window"));
        int i = zza.widthPixels;
        int i2 = zza.heightPixels;
        int[] iArr = new int[2];
        HashMap hashMap = new HashMap();
        ((View) zzaka).getLocationInWindow(iArr);
        hashMap.put("xInPixels", Integer.valueOf(iArr[0]));
        hashMap.put("yInPixels", Integer.valueOf(iArr[1]));
        hashMap.put("windowWidthInPixels", Integer.valueOf(i));
        hashMap.put("windowHeightInPixels", Integer.valueOf(i2));
        zzaka.zza("locationReady", (Map<String, ?>) hashMap);
        zzafr.zzaT("GET LOCATION COMPILED");
    }
}
