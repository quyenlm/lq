package com.google.android.gms.internal;

import android.support.annotation.Nullable;

@zzzn
public final class zzmu {
    public static boolean zza(@Nullable zznb zznb, @Nullable zzmz zzmz, String... strArr) {
        if (zznb == null || zzmz == null) {
            return false;
        }
        return zznb.zza(zzmz, strArr);
    }

    @Nullable
    public static zzmz zzb(@Nullable zznb zznb) {
        if (zznb == null) {
            return null;
        }
        return zznb.zzdS();
    }
}
