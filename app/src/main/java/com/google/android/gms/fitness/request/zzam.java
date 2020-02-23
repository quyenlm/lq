package com.google.android.gms.fitness.request;

import java.util.HashMap;
import java.util.Map;

public final class zzam {
    private static final zzam zzaWT = new zzam();
    private final Map<OnDataPointListener, zzak> zzaWU = new HashMap();

    private zzam() {
    }

    public static zzam zztV() {
        return zzaWT;
    }

    public final zzak zza(OnDataPointListener onDataPointListener) {
        zzak zzak;
        synchronized (this.zzaWU) {
            zzak = this.zzaWU.get(onDataPointListener);
            if (zzak == null) {
                zzak = new zzak(onDataPointListener);
                this.zzaWU.put(onDataPointListener, zzak);
            }
        }
        return zzak;
    }

    public final zzak zzb(OnDataPointListener onDataPointListener) {
        zzak zzak;
        synchronized (this.zzaWU) {
            zzak = this.zzaWU.get(onDataPointListener);
        }
        return zzak;
    }

    public final zzak zzc(OnDataPointListener onDataPointListener) {
        zzak remove;
        synchronized (this.zzaWU) {
            remove = this.zzaWU.remove(onDataPointListener);
            if (remove == null) {
                remove = new zzak(onDataPointListener);
            }
        }
        return remove;
    }
}
