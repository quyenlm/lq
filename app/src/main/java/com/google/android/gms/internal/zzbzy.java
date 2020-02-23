package com.google.android.gms.internal;

import android.os.RemoteException;

public final class zzbzy extends zzbzu<Long> {
    public zzbzy(int i, String str, Long l) {
        super(0, str, l);
    }

    /* access modifiers changed from: private */
    /* renamed from: zzd */
    public final Long zza(zzcac zzcac) {
        try {
            return Long.valueOf(zzcac.getLongFlagValue(getKey(), ((Long) zzdI()).longValue(), getSource()));
        } catch (RemoteException e) {
            return (Long) zzdI();
        }
    }
}
