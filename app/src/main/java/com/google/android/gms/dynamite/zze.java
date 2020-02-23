package com.google.android.gms.dynamite;

import android.content.Context;
import com.google.android.gms.dynamite.DynamiteModule;

final class zze implements DynamiteModule.zzd {
    zze() {
    }

    public final zzi zza(Context context, String str, zzh zzh) throws DynamiteModule.zzc {
        zzi zzi = new zzi();
        zzi.zzaSU = zzh.zzE(context, str);
        zzi.zzaSV = zzh.zzb(context, str, true);
        if (zzi.zzaSU == 0 && zzi.zzaSV == 0) {
            zzi.zzaSW = 0;
        } else if (zzi.zzaSV >= zzi.zzaSU) {
            zzi.zzaSW = 1;
        } else {
            zzi.zzaSW = -1;
        }
        return zzi;
    }
}
