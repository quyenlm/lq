package com.google.android.gms.internal;

import java.util.Map;

final class zzabw implements zzrd {
    private /* synthetic */ zzabu zzUR;

    zzabw(zzabu zzabu) {
        this.zzUR = zzabu;
    }

    public final void zza(zzaka zzaka, Map<String, String> map) {
        synchronized (this.zzUR.mLock) {
            if (!this.zzUR.zzUM.isDone()) {
                zzaca zzaca = new zzaca(-2, map);
                if (this.zzUR.zzQx.equals(zzaca.getRequestId())) {
                    String url = zzaca.getUrl();
                    if (url == null) {
                        zzafr.zzaT("URL missing in loadAdUrl GMSG.");
                        return;
                    }
                    if (url.contains("%40mediation_adapters%40")) {
                        String replaceAll = url.replaceAll("%40mediation_adapters%40", zzafo.zzb(zzaka.getContext(), map.get("check_adapters"), this.zzUR.zzUL));
                        zzaca.setUrl(replaceAll);
                        String valueOf = String.valueOf(replaceAll);
                        zzafr.v(valueOf.length() != 0 ? "Ad request URL modified to ".concat(valueOf) : new String("Ad request URL modified to "));
                    }
                    this.zzUR.zzUM.zzg(zzaca);
                }
            }
        }
    }
}
