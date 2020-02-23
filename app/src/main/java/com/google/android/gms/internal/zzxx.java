package com.google.android.gms.internal;

import android.content.Context;
import android.support.annotation.Nullable;
import com.google.android.gms.ads.internal.zza;
import com.google.android.gms.ads.internal.zzbb;
import com.google.android.gms.ads.internal.zzbs;
import com.google.android.gms.common.util.zzq;

@zzzn
public final class zzxx {
    public static zzahp zza(Context context, zza zza, zzafg zzafg, zzcu zzcu, @Nullable zzaka zzaka, zzuq zzuq, zzxy zzxy, zznb zznb) {
        zzahp zzya;
        zzaai zzaai = zzafg.zzXY;
        if (zzaai.zzTo) {
            zzya = new zzyd(context, zzafg, zzuq, zzxy, zznb, zzaka);
        } else if (zzaai.zzAv || (zza instanceof zzbb)) {
            zzya = (!zzaai.zzAv || !(zza instanceof zzbb)) ? new zzya(zzafg, zzxy) : new zzyf(context, (zzbb) zza, zzafg, zzcu, zzxy, zznb);
        } else {
            zzya = (!((Boolean) zzbs.zzbL().zzd(zzmo.zzDm)).booleanValue() || !zzq.zzsc() || zzq.zzse() || zzaka == null || !zzaka.zzam().zzAt) ? new zzxz(context, zzafg, zzaka, zzxy) : new zzyc(context, zzafg, zzaka, zzxy);
        }
        String valueOf = String.valueOf(zzya.getClass().getName());
        zzafr.zzaC(valueOf.length() != 0 ? "AdRenderer: ".concat(valueOf) : new String("AdRenderer: "));
        zzya.zzgp();
        return zzya;
    }
}
