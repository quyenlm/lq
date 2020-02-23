package com.google.android.gms.internal;

import android.text.TextUtils;
import com.google.android.gms.ads.internal.js.zzai;
import java.lang.ref.WeakReference;
import java.util.Map;

final class zzof extends zzym implements zzrd {
    private final String zzIe;
    private final WeakReference<zznz> zztX;

    public zzof(zznz zznz, String str) {
        this.zztX = new WeakReference<>(zznz);
        this.zzIe = str;
    }

    public final void zza(zzaka zzaka, Map<String, String> map) {
        int i;
        zznz zznz;
        String str = map.get("ads_id");
        if (!TextUtils.isEmpty(str) && this.zzIe.equals(str)) {
            try {
                i = Integer.parseInt(map.get("eventType"));
            } catch (Exception e) {
                zzafr.zzb("Parse Scion log event type error", e);
                i = -1;
            }
            if (1 == i) {
                zznz zznz2 = (zznz) this.zztX.get();
                if (zznz2 != null) {
                    zznz2.zzat();
                }
            } else if (i == 0 && (zznz = (zznz) this.zztX.get()) != null) {
                zznz.zzau();
            }
        }
    }

    public final void zzd(zzai zzai) {
        zzai.zza("/logScionEvent", (zzrd) this);
    }
}
