package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.zzbf;
import com.google.android.gms.internal.zzbg;
import com.google.android.gms.internal.zzbr;
import java.util.HashMap;
import java.util.Map;

final class zzam extends zzbr {
    private static final String ID = zzbf.FUNCTION_CALL.toString();
    private static final String zzbDq = zzbg.ADDITIONAL_PARAMS.toString();
    private static final String zzbEb = zzbg.FUNCTION_CALL_NAME.toString();
    private final zzan zzbEc;

    public zzam(zzan zzan) {
        super(ID, zzbEb);
        this.zzbEc = zzan;
    }

    public final boolean zzAE() {
        return false;
    }

    public final zzbr zzo(Map<String, zzbr> map) {
        String zzb = zzgk.zzb(map.get(zzbEb));
        HashMap hashMap = new HashMap();
        zzbr zzbr = map.get(zzbDq);
        if (zzbr != null) {
            Object zzg = zzgk.zzg(zzbr);
            if (!(zzg instanceof Map)) {
                zzdj.zzaT("FunctionCallMacro: expected ADDITIONAL_PARAMS to be a map.");
                return zzgk.zzCh();
            }
            for (Map.Entry entry : ((Map) zzg).entrySet()) {
                hashMap.put(entry.getKey().toString(), entry.getValue());
            }
        }
        try {
            return zzgk.zzI(this.zzbEc.zzd(zzb, hashMap));
        } catch (Exception e) {
            String valueOf = String.valueOf(e.getMessage());
            zzdj.zzaT(new StringBuilder(String.valueOf(zzb).length() + 34 + String.valueOf(valueOf).length()).append("Custom macro/tag ").append(zzb).append(" threw exception ").append(valueOf).toString());
            return zzgk.zzCh();
        }
    }
}
