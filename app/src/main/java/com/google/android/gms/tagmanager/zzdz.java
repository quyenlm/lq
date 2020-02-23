package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.zzbr;
import java.util.Map;

abstract class zzdz extends zzeg {
    public zzdz(String str) {
        super(str);
    }

    /* access modifiers changed from: protected */
    public final boolean zza(zzbr zzbr, zzbr zzbr2, Map<String, zzbr> map) {
        zzgj zzc = zzgk.zzc(zzbr);
        zzgj zzc2 = zzgk.zzc(zzbr2);
        if (zzc == zzgk.zzCf() || zzc2 == zzgk.zzCf()) {
            return false;
        }
        return zza(zzc, zzc2, map);
    }

    /* access modifiers changed from: protected */
    public abstract boolean zza(zzgj zzgj, zzgj zzgj2, Map<String, zzbr> map);
}
