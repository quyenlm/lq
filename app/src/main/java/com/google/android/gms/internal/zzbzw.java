package com.google.android.gms.internal;

import android.os.RemoteException;

public final class zzbzw extends zzbzu<Boolean> {
    public zzbzw(int i, String str, Boolean bool) {
        super(0, str, bool);
    }

    /* access modifiers changed from: private */
    /* renamed from: zzb */
    public final Boolean zza(zzcac zzcac) {
        try {
            return Boolean.valueOf(zzcac.getBooleanFlagValue(getKey(), ((Boolean) zzdI()).booleanValue(), getSource()));
        } catch (RemoteException e) {
            return (Boolean) zzdI();
        }
    }
}
