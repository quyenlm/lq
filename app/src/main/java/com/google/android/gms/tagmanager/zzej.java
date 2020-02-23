package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.zzbf;
import com.google.android.gms.internal.zzbg;

final class zzej extends zzbr {
    private static final String ID = zzbf.RANDOM.toString();
    private static final String zzbFN = zzbg.MIN.toString();
    private static final String zzbFO = zzbg.MAX.toString();

    public zzej() {
        super(ID, new String[0]);
    }

    public final boolean zzAE() {
        return false;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0045, code lost:
        if (r4 <= r0) goto L_0x0047;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final com.google.android.gms.internal.zzbr zzo(java.util.Map<java.lang.String, com.google.android.gms.internal.zzbr> r10) {
        /*
            r9 = this;
            r6 = 0
            r2 = 4746794007244308480(0x41dfffffffc00000, double:2.147483647E9)
            java.lang.String r0 = zzbFN
            java.lang.Object r0 = r10.get(r0)
            com.google.android.gms.internal.zzbr r0 = (com.google.android.gms.internal.zzbr) r0
            java.lang.String r1 = zzbFO
            java.lang.Object r1 = r10.get(r1)
            com.google.android.gms.internal.zzbr r1 = (com.google.android.gms.internal.zzbr) r1
            if (r0 == 0) goto L_0x005b
            com.google.android.gms.internal.zzbr r4 = com.google.android.gms.tagmanager.zzgk.zzCh()
            if (r0 == r4) goto L_0x005b
            if (r1 == 0) goto L_0x005b
            com.google.android.gms.internal.zzbr r4 = com.google.android.gms.tagmanager.zzgk.zzCh()
            if (r1 == r4) goto L_0x005b
            com.google.android.gms.tagmanager.zzgj r0 = com.google.android.gms.tagmanager.zzgk.zzc(r0)
            com.google.android.gms.tagmanager.zzgj r1 = com.google.android.gms.tagmanager.zzgk.zzc(r1)
            com.google.android.gms.tagmanager.zzgj r4 = com.google.android.gms.tagmanager.zzgk.zzCf()
            if (r0 == r4) goto L_0x005b
            com.google.android.gms.tagmanager.zzgj r4 = com.google.android.gms.tagmanager.zzgk.zzCf()
            if (r1 == r4) goto L_0x005b
            double r4 = r0.doubleValue()
            double r0 = r1.doubleValue()
            int r8 = (r4 > r0 ? 1 : (r4 == r0 ? 0 : -1))
            if (r8 > 0) goto L_0x005b
        L_0x0047:
            double r2 = java.lang.Math.random()
            double r0 = r0 - r4
            double r0 = r0 * r2
            double r0 = r0 + r4
            long r0 = java.lang.Math.round(r0)
            java.lang.Long r0 = java.lang.Long.valueOf(r0)
            com.google.android.gms.internal.zzbr r0 = com.google.android.gms.tagmanager.zzgk.zzI(r0)
            return r0
        L_0x005b:
            r0 = r2
            r4 = r6
            goto L_0x0047
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.tagmanager.zzej.zzo(java.util.Map):com.google.android.gms.internal.zzbr");
    }
}
