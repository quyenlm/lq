package com.google.android.gms.internal;

import java.util.Map;

final class zzabv implements zzrd {
    private /* synthetic */ zzabu zzUR;

    zzabv(zzabu zzabu) {
        this.zzUR = zzabu;
    }

    public final void zza(zzaka zzaka, Map<String, String> map) {
        synchronized (this.zzUR.mLock) {
            if (!this.zzUR.zzUM.isDone()) {
                if (this.zzUR.zzQx.equals(map.get("request_id"))) {
                    zzaca zzaca = new zzaca(1, map);
                    String valueOf = String.valueOf(zzaca.getType());
                    String valueOf2 = String.valueOf(zzaca.zzgH());
                    zzafr.zzaT(new StringBuilder(String.valueOf(valueOf).length() + 24 + String.valueOf(valueOf2).length()).append("Invalid ").append(valueOf).append(" request error: ").append(valueOf2).toString());
                    this.zzUR.zzUM.zzg(zzaca);
                }
            }
        }
    }
}
