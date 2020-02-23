package com.google.android.gms.internal;

import java.util.Arrays;
import java.util.List;

public final class zzcxn extends zzcxq {
    private final String mName;
    private zzcwa zzbIR = null;
    private final List<String> zzbJS;
    private final List<ea> zzbJT;

    public zzcxn(zzcwa zzcwa, String str, List<String> list, List<ea> list2) {
        this.mName = str;
        this.zzbJS = list;
        this.zzbJT = list2;
    }

    public final String getName() {
        return this.mName;
    }

    public final String toString() {
        String str = this.mName;
        String valueOf = String.valueOf(this.zzbJS.toString());
        String valueOf2 = String.valueOf(this.zzbJT.toString());
        return new StringBuilder(String.valueOf(str).length() + 26 + String.valueOf(valueOf).length() + String.valueOf(valueOf2).length()).append(str).append("\n\tparams: ").append(valueOf).append("\n\t: statements: ").append(valueOf2).toString();
    }

    public final dp<?> zza(zzcwa zzcwa, dp<?>... dpVarArr) {
        try {
            zzcwa zzCz = this.zzbIR.zzCz();
            for (int i = 0; i < this.zzbJS.size(); i++) {
                if (dpVarArr.length > i) {
                    zzCz.zza(this.zzbJS.get(i), dpVarArr[i]);
                } else {
                    zzCz.zza(this.zzbJS.get(i), dv.zzbLu);
                }
            }
            zzCz.zza("arguments", new dw(Arrays.asList(dpVarArr)));
            for (ea zza : this.zzbJT) {
                dp zza2 = ed.zza(zzCz, zza);
                if ((zza2 instanceof dv) && ((dv) zza2).zzDr()) {
                    return ((dv) zza2).zzDq();
                }
            }
        } catch (RuntimeException e) {
            String str = this.mName;
            String valueOf = String.valueOf(e.getMessage());
            zzcvk.e(new StringBuilder(String.valueOf(str).length() + 33 + String.valueOf(valueOf).length()).append("Internal error - Function call: ").append(str).append("\n").append(valueOf).toString());
        }
        return dv.zzbLu;
    }

    public final void zza(zzcwa zzcwa) {
        this.zzbIR = zzcwa;
    }
}
