package com.google.android.gms.internal;

import com.google.android.gms.ads.internal.zzbs;
import com.google.android.gms.common.internal.zzc;
import java.util.Map;

@zzzn
public final class zzsf implements zzrd {
    public final void zza(zzaka zzaka, Map<String, String> map) {
        int i;
        zzbs.zzbW();
        if (!map.containsKey("abort")) {
            String str = map.get("src");
            if (str == null) {
                zzafr.zzaT("Precache video action is missing the src parameter.");
                return;
            }
            try {
                i = Integer.parseInt(map.get("player"));
            } catch (NumberFormatException e) {
                i = 0;
            }
            String str2 = map.containsKey("mimetype") ? map.get("mimetype") : "";
            if (zzsa.zzf(zzaka)) {
                zzafr.zzaT("Precache task already running.");
                return;
            }
            zzc.zzr(zzaka.zzak());
            new zzry(zzaka, zzaka.zzak().zztm.zza(zzaka, i, str2), str).zzhL();
        } else if (!zzsa.zze(zzaka)) {
            zzafr.zzaT("Precache abort but no preload task running.");
        }
    }
}
