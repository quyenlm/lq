package com.google.android.gms.internal;

import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;

public final class zzbev {
    public static final Status zzaFj = new Status(8, "The connection to Google Play services was lost");
    private static final zzbbe<?>[] zzaFk = new zzbbe[0];
    private final Map<Api.zzc<?>, Api.zze> zzaDF;
    final Set<zzbbe<?>> zzaFl = Collections.synchronizedSet(Collections.newSetFromMap(new WeakHashMap()));
    private final zzbex zzaFm = new zzbew(this);

    public zzbev(Map<Api.zzc<?>, Api.zze> map) {
        this.zzaDF = map;
    }

    public final void release() {
        for (zzbbe zzbbe : (zzbbe[]) this.zzaFl.toArray(zzaFk)) {
            zzbbe.zza((zzbex) null);
            zzbbe.zzpo();
            if (zzbbe.zzpB()) {
                this.zzaFl.remove(zzbbe);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public final void zzb(zzbbe<? extends Result> zzbbe) {
        this.zzaFl.add(zzbbe);
        zzbbe.zza(this.zzaFm);
    }

    public final void zzqM() {
        for (zzbbe zzs : (zzbbe[]) this.zzaFl.toArray(zzaFk)) {
            zzs.zzs(zzaFj);
        }
    }
}
