package com.google.android.gms.internal;

import android.os.RemoteException;

public final class zzbzz extends zzbzu<String> {
    public zzbzz(int i, String str, String str2) {
        super(0, str, str2);
    }

    /* access modifiers changed from: private */
    /* renamed from: zze */
    public final String zza(zzcac zzcac) {
        try {
            return zzcac.getStringFlagValue(getKey(), (String) zzdI(), getSource());
        } catch (RemoteException e) {
            return (String) zzdI();
        }
    }
}
