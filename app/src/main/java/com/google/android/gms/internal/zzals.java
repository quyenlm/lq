package com.google.android.gms.internal;

import android.text.TextUtils;
import com.google.android.gms.analytics.zzj;
import com.google.android.gms.measurement.AppMeasurement;
import java.util.HashMap;

public final class zzals extends zzj<zzals> {
    public String zzafa;
    public boolean zzafb;

    public final String toString() {
        HashMap hashMap = new HashMap();
        hashMap.put("description", this.zzafa);
        hashMap.put(AppMeasurement.Param.FATAL, Boolean.valueOf(this.zzafb));
        return zzh(hashMap);
    }

    public final /* synthetic */ void zzb(zzj zzj) {
        zzals zzals = (zzals) zzj;
        if (!TextUtils.isEmpty(this.zzafa)) {
            zzals.zzafa = this.zzafa;
        }
        if (this.zzafb) {
            zzals.zzafb = this.zzafb;
        }
    }
}
