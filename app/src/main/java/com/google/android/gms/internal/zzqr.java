package com.google.android.gms.internal;

import java.util.Map;

final class zzqr implements zzrd {
    zzqr() {
    }

    public final void zza(zzaka zzaka, Map<String, String> map) {
        String str = map.get("action");
        if ("pause".equals(str)) {
            zzaka.zzaJ();
        } else if ("resume".equals(str)) {
            zzaka.zzaK();
        }
    }
}
