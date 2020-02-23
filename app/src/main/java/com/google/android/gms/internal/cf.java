package com.google.android.gms.internal;

import android.support.annotation.Nullable;
import java.util.List;

final class cf extends cc {
    private final int zzbKA;
    private /* synthetic */ cd zzbKB;
    private final ce zzbKy;
    private final List<Integer> zzbKz;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    cf(cd cdVar, int i, cl clVar, ch chVar, List<Integer> list, int i2, @Nullable ce ceVar, zzcuo zzcuo) {
        super(i, clVar, chVar, zzcuo);
        this.zzbKB = cdVar;
        this.zzbKy = ceVar;
        this.zzbKz = list;
        this.zzbKA = i2;
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x0063  */
    /* JADX WARNING: Removed duplicated region for block: B:18:0x0071  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void zza(com.google.android.gms.internal.cm r7) {
        /*
            r6 = this;
            r1 = 1
            r2 = 0
            com.google.android.gms.common.api.Status r0 = r7.getStatus()
            com.google.android.gms.common.api.Status r3 = com.google.android.gms.common.api.Status.zzaBm
            if (r0 != r3) goto L_0x0117
            java.lang.String r3 = "Container resource successfully loaded from "
            java.lang.String r0 = r7.zzCS()
            java.lang.String r0 = java.lang.String.valueOf(r0)
            int r4 = r0.length()
            if (r4 == 0) goto L_0x0069
            java.lang.String r0 = r3.concat(r0)
        L_0x001e:
            com.google.android.gms.internal.zzcvk.v(r0)
            int r0 = r7.getSource()
            if (r0 != 0) goto L_0x006f
            com.google.android.gms.internal.cn r0 = r7.zzCQ()
            com.google.android.gms.internal.bz r3 = r0.zzCU()
            boolean r3 = r3.zzCL()
            if (r3 != 0) goto L_0x006f
            com.google.android.gms.internal.cd r3 = r6.zzbKB
            com.google.android.gms.common.api.Status r4 = r7.getStatus()
            r3.zza(r4, r0)
            byte[] r3 = r0.zzCT()
            if (r3 == 0) goto L_0x0117
            byte[] r3 = r0.zzCT()
            int r3 = r3.length
            if (r3 <= 0) goto L_0x0117
            com.google.android.gms.internal.cd r2 = r6.zzbKB
            com.google.android.gms.internal.co r2 = r2.zzbKv
            com.google.android.gms.internal.bz r3 = r0.zzCU()
            java.lang.String r3 = r3.zzCK()
            byte[] r0 = r0.zzCT()
            r2.zzd(r3, r0)
            r0 = r1
        L_0x0061:
            if (r0 == 0) goto L_0x0071
            com.google.android.gms.internal.ce r0 = r6.zzbKy
            r0.zza(r7)
        L_0x0068:
            return
        L_0x0069:
            java.lang.String r0 = new java.lang.String
            r0.<init>(r3)
            goto L_0x001e
        L_0x006f:
            r0 = r1
            goto L_0x0061
        L_0x0071:
            java.lang.String r0 = r7.zzCS()
            java.lang.String r1 = java.lang.String.valueOf(r0)
            com.google.android.gms.common.api.Status r0 = r7.getStatus()
            boolean r0 = r0.isSuccess()
            if (r0 == 0) goto L_0x010d
            java.lang.String r0 = "SUCCESS"
        L_0x0085:
            java.lang.String r2 = java.lang.String.valueOf(r1)
            int r2 = r2.length()
            int r2 = r2 + 54
            java.lang.String r3 = java.lang.String.valueOf(r0)
            int r3 = r3.length()
            int r2 = r2 + r3
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>(r2)
            java.lang.String r2 = "Cannot fetch a valid resource from "
            java.lang.StringBuilder r2 = r3.append(r2)
            java.lang.StringBuilder r1 = r2.append(r1)
            java.lang.String r2 = ". Response status: "
            java.lang.StringBuilder r1 = r1.append(r2)
            java.lang.StringBuilder r0 = r1.append(r0)
            java.lang.String r0 = r0.toString()
            com.google.android.gms.internal.zzcvk.v(r0)
            com.google.android.gms.common.api.Status r0 = r7.getStatus()
            boolean r0 = r0.isSuccess()
            if (r0 == 0) goto L_0x00fa
            java.lang.String r1 = "Response source: "
            java.lang.String r0 = r7.zzCS()
            java.lang.String r0 = java.lang.String.valueOf(r0)
            int r2 = r0.length()
            if (r2 == 0) goto L_0x0111
            java.lang.String r0 = r1.concat(r0)
        L_0x00d6:
            com.google.android.gms.internal.zzcvk.v(r0)
            com.google.android.gms.internal.cn r0 = r7.zzCQ()
            byte[] r0 = r0.zzCT()
            int r0 = r0.length
            r1 = 26
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>(r1)
            java.lang.String r1 = "Response size: "
            java.lang.StringBuilder r1 = r2.append(r1)
            java.lang.StringBuilder r0 = r1.append(r0)
            java.lang.String r0 = r0.toString()
            com.google.android.gms.internal.zzcvk.v(r0)
        L_0x00fa:
            com.google.android.gms.internal.cd r0 = r6.zzbKB
            com.google.android.gms.internal.cl r1 = r6.zzbKt
            java.util.List<java.lang.Integer> r2 = r6.zzbKz
            int r3 = r6.zzbKA
            int r3 = r3 + 1
            com.google.android.gms.internal.ce r4 = r6.zzbKy
            com.google.android.gms.internal.zzcuo r5 = r6.zzbHO
            r0.zza(r1, r2, r3, r4, r5)
            goto L_0x0068
        L_0x010d:
            java.lang.String r0 = "FAILURE"
            goto L_0x0085
        L_0x0111:
            java.lang.String r0 = new java.lang.String
            r0.<init>(r1)
            goto L_0x00d6
        L_0x0117:
            r0 = r2
            goto L_0x0061
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.cf.zza(com.google.android.gms.internal.cm):void");
    }
}
