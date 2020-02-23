package com.google.android.gms.internal;

import java.util.Map;

@zzzn
public final class zzrl implements zzrd {
    private final zzrm zzJC;

    public zzrl(zzrm zzrm) {
        this.zzJC = zzrm;
    }

    public final void zza(zzaka zzaka, Map<String, String> map) {
        float f;
        boolean equals = "1".equals(map.get("transparentBackground"));
        boolean equals2 = "1".equals(map.get("blur"));
        try {
            if (map.get("blurRadius") != null) {
                f = Float.parseFloat(map.get("blurRadius"));
                this.zzJC.zzc(equals);
                this.zzJC.zza(equals2, f);
            }
        } catch (NumberFormatException e) {
            zzafr.zzb("Fail to parse float", e);
        }
        f = 0.0f;
        this.zzJC.zzc(equals);
        this.zzJC.zza(equals2, f);
    }
}
