package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.zzbg;
import com.google.android.gms.internal.zzbr;
import java.util.Map;
import java.util.Set;

public abstract class zzeg extends zzbr {
    private static final String zzbEH = zzbg.ARG0.toString();
    private static final String zzbFE = zzbg.ARG1.toString();

    public zzeg(String str) {
        super(str, zzbEH, zzbFE);
    }

    public final boolean zzAE() {
        return true;
    }

    public final /* bridge */ /* synthetic */ String zzBk() {
        return super.zzBk();
    }

    public final /* bridge */ /* synthetic */ Set zzBl() {
        return super.zzBl();
    }

    /* access modifiers changed from: protected */
    public abstract boolean zza(zzbr zzbr, zzbr zzbr2, Map<String, zzbr> map);

    public final zzbr zzo(Map<String, zzbr> map) {
        for (zzbr zzbr : map.values()) {
            if (zzbr == zzgk.zzCh()) {
                return zzgk.zzI(false);
            }
        }
        zzbr zzbr2 = map.get(zzbEH);
        zzbr zzbr3 = map.get(zzbFE);
        return zzgk.zzI(Boolean.valueOf((zzbr2 == null || zzbr3 == null) ? false : zza(zzbr2, zzbr3, map)));
    }
}
