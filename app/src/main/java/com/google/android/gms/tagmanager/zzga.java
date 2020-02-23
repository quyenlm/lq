package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.zzbr;
import java.util.Map;

abstract class zzga extends zzeg {
    public zzga(String str) {
        super(str);
    }

    /* access modifiers changed from: protected */
    public final boolean zza(zzbr zzbr, zzbr zzbr2, Map<String, zzbr> map) {
        String zzb = zzgk.zzb(zzbr);
        String zzb2 = zzgk.zzb(zzbr2);
        if (zzb == zzgk.zzCg() || zzb2 == zzgk.zzCg()) {
            return false;
        }
        return zza(zzb, zzb2, map);
    }

    /* access modifiers changed from: protected */
    public abstract boolean zza(String str, String str2, Map<String, zzbr> map);
}
