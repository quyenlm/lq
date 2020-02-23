package com.google.android.gms.internal;

import java.util.Map;

public final class zzabh implements zzrd {
    public final void zza(zzaka zzaka, Map<String, String> map) {
        String str = map.get("request_id");
        String valueOf = String.valueOf(map.get("errors"));
        zzafr.zzaT(valueOf.length() != 0 ? "Invalid request: ".concat(valueOf) : new String("Invalid request: "));
        zzaaz.zzTZ.zzT(str);
    }
}
