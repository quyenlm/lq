package com.google.android.gms.internal;

import com.google.android.gms.ads.internal.zzw;
import com.google.android.gms.common.util.zzf;
import com.tencent.midas.oversea.comm.APDataReportManager;
import java.util.Map;

@zzzn
public final class zzrp implements zzrd {
    private static Map<String, Integer> zzJG = zzf.zza("resize", 1, "playVideo", 2, "storePicture", 3, "createCalendarEvent", 4, "setOrientationProperties", 5, "closeResizedAd", 6);
    private final zzw zzJE;
    private final zzwk zzJF;

    public zzrp(zzw zzw, zzwk zzwk) {
        this.zzJE = zzw;
        this.zzJF = zzwk;
    }

    public final void zza(zzaka zzaka, Map<String, String> map) {
        int intValue = zzJG.get(map.get(APDataReportManager.GAMEANDMONTHSLIST_PRE)).intValue();
        if (intValue == 5 || this.zzJE == null || this.zzJE.zzaR()) {
            switch (intValue) {
                case 1:
                    this.zzJF.execute(map);
                    return;
                case 3:
                    new zzwn(zzaka, map).execute();
                    return;
                case 4:
                    new zzwh(zzaka, map).execute();
                    return;
                case 5:
                    new zzwm(zzaka, map).execute();
                    return;
                case 6:
                    this.zzJF.zzk(true);
                    return;
                default:
                    zzafr.zzaS("Unknown MRAID command called.");
                    return;
            }
        } else {
            this.zzJE.zzt((String) null);
        }
    }
}
