package com.google.android.gms.internal;

import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.internal.zzbo;
import java.util.HashMap;
import java.util.Set;

public final class zzcam {
    private static final String[] zzbcJ = {"requestId", "outcome"};
    private final int zzaxu;
    private final HashMap<String, Integer> zzbcK;

    private zzcam(int i, HashMap<String, Integer> hashMap) {
        this.zzaxu = i;
        this.zzbcK = hashMap;
    }

    public static zzcam zzN(DataHolder dataHolder) {
        zzcao zzcao = new zzcao();
        zzcao.zzbd(dataHolder.getStatusCode());
        int count = dataHolder.getCount();
        for (int i = 0; i < count; i++) {
            int zzat = dataHolder.zzat(i);
            zzcao.zzs(dataHolder.zzd("requestId", i, zzat), dataHolder.zzc("outcome", i, zzat));
        }
        return zzcao.zzvm();
    }

    public final Set<String> getRequestIds() {
        return this.zzbcK.keySet();
    }

    public final int getRequestOutcome(String str) {
        zzbo.zzb(this.zzbcK.containsKey(str), (Object) new StringBuilder(String.valueOf(str).length() + 46).append("Request ").append(str).append(" was not part of the update operation!").toString());
        return this.zzbcK.get(str).intValue();
    }
}
