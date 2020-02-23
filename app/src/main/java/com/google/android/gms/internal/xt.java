package com.google.android.gms.internal;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

public final class xt {
    private final qr zzciC;
    private final qr zzciD;
    private final xm zzciE;

    public xt(pe peVar) {
        qr qrVar = null;
        List<String> zzGs = peVar.zzGs();
        this.zzciC = zzGs != null ? new qr(zzGs) : null;
        List<String> zzGt = peVar.zzGt();
        this.zzciD = zzGt != null ? new qr(zzGt) : qrVar;
        this.zzciE = xp.zza(peVar.zzGu(), xd.zzJb());
    }

    private final xm zzb(qr qrVar, xm xmVar, xm xmVar2) {
        boolean z = true;
        int i = 0;
        int zzj = this.zzciC == null ? 1 : qrVar.compareTo(this.zzciC);
        int zzj2 = this.zzciD == null ? -1 : qrVar.compareTo(this.zzciD);
        boolean z2 = this.zzciC != null && qrVar.zzi(this.zzciC);
        if (this.zzciD == null || !qrVar.zzi(this.zzciD)) {
            z = false;
        }
        if (zzj > 0 && zzj2 < 0 && !z) {
            return xmVar2;
        }
        if (zzj > 0 && z && xmVar2.zzIQ()) {
            return xmVar2;
        }
        if (zzj > 0 && zzj2 == 0) {
            return xmVar.zzIQ() ? xd.zzJb() : xmVar;
        }
        if (!z2 && !z) {
            return xmVar;
        }
        HashSet hashSet = new HashSet();
        Iterator it = xmVar.iterator();
        while (it.hasNext()) {
            hashSet.add(((xl) it.next()).zzJk());
        }
        Iterator it2 = xmVar2.iterator();
        while (it2.hasNext()) {
            hashSet.add(((xl) it2.next()).zzJk());
        }
        ArrayList arrayList = new ArrayList(hashSet.size() + 1);
        arrayList.addAll(hashSet);
        if (!xmVar2.zzIR().isEmpty() || !xmVar.zzIR().isEmpty()) {
            arrayList.add(wp.zzIL());
        }
        ArrayList arrayList2 = arrayList;
        int size = arrayList2.size();
        xm xmVar3 = xmVar;
        while (i < size) {
            Object obj = arrayList2.get(i);
            i++;
            wp wpVar = (wp) obj;
            xm zzm = xmVar.zzm(wpVar);
            xm zzb = zzb(qrVar.zza(wpVar), xmVar.zzm(wpVar), xmVar2.zzm(wpVar));
            xmVar3 = zzb != zzm ? xmVar3.zze(wpVar, zzb) : xmVar3;
        }
        return xmVar3;
    }

    public final String toString() {
        String valueOf = String.valueOf(this.zzciC);
        String valueOf2 = String.valueOf(this.zzciD);
        String valueOf3 = String.valueOf(this.zzciE);
        return new StringBuilder(String.valueOf(valueOf).length() + 55 + String.valueOf(valueOf2).length() + String.valueOf(valueOf3).length()).append("RangeMerge{optExclusiveStart=").append(valueOf).append(", optInclusiveEnd=").append(valueOf2).append(", snap=").append(valueOf3).append("}").toString();
    }

    public final xm zzm(xm xmVar) {
        return zzb(qr.zzGZ(), xmVar, this.zzciE);
    }
}
