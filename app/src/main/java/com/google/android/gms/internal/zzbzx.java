package com.google.android.gms.internal;

import android.os.RemoteException;

public final class zzbzx extends zzbzu<Integer> {
    public zzbzx(int i, String str, Integer num) {
        super(0, str, num);
    }

    /* access modifiers changed from: private */
    /* renamed from: zzc */
    public final Integer zza(zzcac zzcac) {
        try {
            return Integer.valueOf(zzcac.getIntFlagValue(getKey(), ((Integer) zzdI()).intValue(), getSource()));
        } catch (RemoteException e) {
            return (Integer) zzdI();
        }
    }
}
