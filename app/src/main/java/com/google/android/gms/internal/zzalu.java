package com.google.android.gms.internal;

import android.text.TextUtils;
import com.facebook.internal.AnalyticsEvents;
import com.google.android.gms.analytics.zzj;
import java.util.HashMap;

public final class zzalu extends zzj<zzalu> {
    private int zzafk;
    private int zzafl;
    private String zzafm;
    private String zzafn;
    private boolean zzafo;
    private boolean zzafp;
    private String zzul;

    public zzalu() {
        this(false);
    }

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private zzalu(boolean r9) {
        /*
            r8 = this;
            r6 = 2147483647(0x7fffffff, double:1.060997895E-314)
            r1 = 0
            java.util.UUID r2 = java.util.UUID.randomUUID()
            long r4 = r2.getLeastSignificantBits()
            long r4 = r4 & r6
            int r0 = (int) r4
            if (r0 == 0) goto L_0x0014
        L_0x0010:
            r8.<init>(r1, r0)
            return
        L_0x0014:
            long r2 = r2.getMostSignificantBits()
            long r2 = r2 & r6
            int r0 = (int) r2
            if (r0 != 0) goto L_0x0010
            java.lang.String r0 = "GAv4"
            java.lang.String r2 = "UUID.randomUUID() returned 0."
            android.util.Log.e(r0, r2)
            r0 = 2147483647(0x7fffffff, float:NaN)
            goto L_0x0010
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzalu.<init>(boolean):void");
    }

    private zzalu(boolean z, int i) {
        if (i == 0) {
            throw new IllegalArgumentException("Given Integer is zero");
        }
        this.zzafk = i;
        this.zzafp = false;
    }

    public final String toString() {
        HashMap hashMap = new HashMap();
        hashMap.put("screenName", this.zzul);
        hashMap.put("interstitial", Boolean.valueOf(this.zzafo));
        hashMap.put(AnalyticsEvents.PARAMETER_SHARE_DIALOG_SHOW_AUTOMATIC, Boolean.valueOf(this.zzafp));
        hashMap.put("screenId", Integer.valueOf(this.zzafk));
        hashMap.put("referrerScreenId", Integer.valueOf(this.zzafl));
        hashMap.put("referrerScreenName", this.zzafm);
        hashMap.put("referrerUri", this.zzafn);
        return zzh(hashMap);
    }

    public final /* synthetic */ void zzb(zzj zzj) {
        zzalu zzalu = (zzalu) zzj;
        if (!TextUtils.isEmpty(this.zzul)) {
            zzalu.zzul = this.zzul;
        }
        if (this.zzafk != 0) {
            zzalu.zzafk = this.zzafk;
        }
        if (this.zzafl != 0) {
            zzalu.zzafl = this.zzafl;
        }
        if (!TextUtils.isEmpty(this.zzafm)) {
            zzalu.zzafm = this.zzafm;
        }
        if (!TextUtils.isEmpty(this.zzafn)) {
            String str = this.zzafn;
            if (TextUtils.isEmpty(str)) {
                zzalu.zzafn = null;
            } else {
                zzalu.zzafn = str;
            }
        }
        if (this.zzafo) {
            zzalu.zzafo = this.zzafo;
        }
        if (this.zzafp) {
            zzalu.zzafp = this.zzafp;
        }
    }

    public final String zzkd() {
        return this.zzul;
    }

    public final int zzke() {
        return this.zzafk;
    }

    public final String zzkf() {
        return this.zzafn;
    }
}
