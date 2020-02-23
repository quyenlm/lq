package com.google.android.gms.internal;

import android.os.RemoteException;
import java.util.Map;

final class zzcvw implements aa {
    private /* synthetic */ zzcvu zzbIQ;

    private zzcvw(zzcvu zzcvu) {
        this.zzbIQ = zzcvu;
    }

    /* synthetic */ zzcvw(zzcvu zzcvu, zzcvv zzcvv) {
        this(zzcvu);
    }

    public final Object zzd(String str, Map<String, Object> map) {
        try {
            this.zzbIQ.zzbHW.zze(str, map);
            return null;
        } catch (RemoteException e) {
            String valueOf = String.valueOf(e.getMessage());
            zzcvk.e(valueOf.length() != 0 ? "Error calling customEvaluator proxy:".concat(valueOf) : new String("Error calling customEvaluator proxy:"));
            return null;
        }
    }
}
