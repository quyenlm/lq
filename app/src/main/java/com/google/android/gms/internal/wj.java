package com.google.android.gms.internal;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class wj implements wm {
    private final Set<String> zzchE;
    private final wn zzchF;

    public wj(wn wnVar, List<String> list) {
        if (list != null) {
            this.zzchE = new HashSet(list);
        } else {
            this.zzchE = null;
        }
        this.zzchF = wnVar;
    }

    public final wn zzGQ() {
        return this.zzchF;
    }

    /* access modifiers changed from: protected */
    public String zza(wn wnVar, String str, String str2, long j) {
        String valueOf = String.valueOf(new Date(j).toString());
        String valueOf2 = String.valueOf(wnVar);
        return new StringBuilder(String.valueOf(valueOf).length() + 6 + String.valueOf(valueOf2).length() + String.valueOf(str).length() + String.valueOf(str2).length()).append(valueOf).append(" [").append(valueOf2).append("] ").append(str).append(": ").append(str2).toString();
    }

    /* access modifiers changed from: protected */
    public void zzad(String str, String str2) {
        System.err.println(str2);
    }

    /* access modifiers changed from: protected */
    public void zzae(String str, String str2) {
        System.out.println(str2);
    }

    /* access modifiers changed from: protected */
    public void zzaf(String str, String str2) {
        System.out.println(str2);
    }

    /* access modifiers changed from: protected */
    public void zzag(String str, String str2) {
        System.out.println(str2);
    }

    public final void zzb(wn wnVar, String str, String str2, long j) {
        if (wnVar.ordinal() >= this.zzchF.ordinal() && (this.zzchE == null || wnVar.ordinal() > wn.DEBUG.ordinal() || this.zzchE.contains(str))) {
            String zza = zza(wnVar, str, str2, j);
            switch (wk.zzcbZ[wnVar.ordinal()]) {
                case 1:
                    zzad(str, zza);
                    return;
                case 2:
                    zzae(str, zza);
                    return;
                case 3:
                    zzaf(str, zza);
                    return;
                case 4:
                    zzag(str, zza);
                    return;
                default:
                    throw new RuntimeException("Should not reach here!");
            }
        }
    }
}
