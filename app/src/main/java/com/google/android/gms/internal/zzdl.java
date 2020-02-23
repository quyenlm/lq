package com.google.android.gms.internal;

import java.util.concurrent.ExecutionException;

public final class zzdl extends zzec {
    private static final Object zzrl = new Object();
    private static volatile zzbu zzrm = null;
    private zzau zzrn = null;

    public zzdl(zzdb zzdb, String str, String str2, zzax zzax, int i, int i2, zzau zzau) {
        super(zzdb, str, str2, zzax, i, 27);
        this.zzrn = zzau;
    }

    private final String zzU() {
        try {
            if (this.zzpJ.zzL() != null) {
                this.zzpJ.zzL().get();
            }
            zzax zzK = this.zzpJ.zzK();
            if (!(zzK == null || zzK.zzaT == null)) {
                return zzK.zzaT;
            }
        } catch (InterruptedException | ExecutionException e) {
        }
        return null;
    }

    private static String zza(zzau zzau) {
        if (zzau == null || zzau.zzaR == null || zzdg.zzo(zzau.zzaR.zzaT)) {
            return null;
        }
        return zzau.zzaR.zzaT;
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Code restructure failed: missing block: B:52:0x0108, code lost:
        if (r0 != false) goto L_0x003e;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void zzT() throws java.lang.IllegalAccessException, java.lang.reflect.InvocationTargetException {
        /*
            r10 = this;
            r3 = 3
            r4 = 2
            r1 = 1
            r2 = 0
            com.google.android.gms.internal.zzbu r0 = zzrm
            if (r0 == 0) goto L_0x002a
            com.google.android.gms.internal.zzbu r0 = zzrm
            java.lang.String r0 = r0.zzaT
            boolean r0 = com.google.android.gms.internal.zzdg.zzo(r0)
            if (r0 != 0) goto L_0x002a
            com.google.android.gms.internal.zzbu r0 = zzrm
            java.lang.String r0 = r0.zzaT
            java.lang.String r5 = "E"
            boolean r0 = r0.equals(r5)
            if (r0 != 0) goto L_0x002a
            com.google.android.gms.internal.zzbu r0 = zzrm
            java.lang.String r0 = r0.zzaT
            java.lang.String r5 = "0000000000000000000000000000000000000000000000000000000000000000"
            boolean r0 = r0.equals(r5)
            if (r0 == 0) goto L_0x00b1
        L_0x002a:
            r0 = r1
        L_0x002b:
            if (r0 == 0) goto L_0x007c
            java.lang.Object r5 = zzrl
            monitor-enter(r5)
            com.google.android.gms.internal.zzau r0 = r10.zzrn     // Catch:{ all -> 0x0120 }
            java.lang.String r0 = zza(r0)     // Catch:{ all -> 0x0120 }
            boolean r0 = com.google.android.gms.internal.zzdg.zzo(r0)     // Catch:{ all -> 0x0120 }
            if (r0 != 0) goto L_0x00b4
            r0 = 4
            r3 = r0
        L_0x003e:
            java.lang.reflect.Method r6 = r10.zzrx     // Catch:{ all -> 0x0120 }
            r7 = 0
            r0 = 2
            java.lang.Object[] r8 = new java.lang.Object[r0]     // Catch:{ all -> 0x0120 }
            r0 = 0
            com.google.android.gms.internal.zzdb r9 = r10.zzpJ     // Catch:{ all -> 0x0120 }
            android.content.Context r9 = r9.getContext()     // Catch:{ all -> 0x0120 }
            r8[r0] = r9     // Catch:{ all -> 0x0120 }
            r9 = 1
            if (r3 != r4) goto L_0x0111
            r0 = r1
        L_0x0051:
            java.lang.Boolean r0 = java.lang.Boolean.valueOf(r0)     // Catch:{ all -> 0x0120 }
            r8[r9] = r0     // Catch:{ all -> 0x0120 }
            java.lang.Object r0 = r6.invoke(r7, r8)     // Catch:{ all -> 0x0120 }
            java.lang.String r0 = (java.lang.String) r0     // Catch:{ all -> 0x0120 }
            com.google.android.gms.internal.zzbu r1 = new com.google.android.gms.internal.zzbu     // Catch:{ all -> 0x0120 }
            r1.<init>(r0)     // Catch:{ all -> 0x0120 }
            zzrm = r1     // Catch:{ all -> 0x0120 }
            java.lang.String r0 = r1.zzaT     // Catch:{ all -> 0x0120 }
            boolean r0 = com.google.android.gms.internal.zzdg.zzo(r0)     // Catch:{ all -> 0x0120 }
            if (r0 != 0) goto L_0x0078
            com.google.android.gms.internal.zzbu r0 = zzrm     // Catch:{ all -> 0x0120 }
            java.lang.String r0 = r0.zzaT     // Catch:{ all -> 0x0120 }
            java.lang.String r1 = "E"
            boolean r0 = r0.equals(r1)     // Catch:{ all -> 0x0120 }
            if (r0 == 0) goto L_0x007b
        L_0x0078:
            switch(r3) {
                case 3: goto L_0x0123;
                case 4: goto L_0x0114;
                default: goto L_0x007b;
            }     // Catch:{ all -> 0x0120 }
        L_0x007b:
            monitor-exit(r5)     // Catch:{ all -> 0x0120 }
        L_0x007c:
            com.google.android.gms.internal.zzax r1 = r10.zzro
            monitor-enter(r1)
            com.google.android.gms.internal.zzbu r0 = zzrm     // Catch:{ all -> 0x0133 }
            if (r0 == 0) goto L_0x00af
            com.google.android.gms.internal.zzax r0 = r10.zzro     // Catch:{ all -> 0x0133 }
            com.google.android.gms.internal.zzbu r2 = zzrm     // Catch:{ all -> 0x0133 }
            java.lang.String r2 = r2.zzaT     // Catch:{ all -> 0x0133 }
            r0.zzaT = r2     // Catch:{ all -> 0x0133 }
            com.google.android.gms.internal.zzax r0 = r10.zzro     // Catch:{ all -> 0x0133 }
            com.google.android.gms.internal.zzbu r2 = zzrm     // Catch:{ all -> 0x0133 }
            long r2 = r2.zzlO     // Catch:{ all -> 0x0133 }
            java.lang.Long r2 = java.lang.Long.valueOf(r2)     // Catch:{ all -> 0x0133 }
            r0.zzbx = r2     // Catch:{ all -> 0x0133 }
            com.google.android.gms.internal.zzax r0 = r10.zzro     // Catch:{ all -> 0x0133 }
            com.google.android.gms.internal.zzbu r2 = zzrm     // Catch:{ all -> 0x0133 }
            java.lang.String r2 = r2.zzaV     // Catch:{ all -> 0x0133 }
            r0.zzaV = r2     // Catch:{ all -> 0x0133 }
            com.google.android.gms.internal.zzax r0 = r10.zzro     // Catch:{ all -> 0x0133 }
            com.google.android.gms.internal.zzbu r2 = zzrm     // Catch:{ all -> 0x0133 }
            java.lang.String r2 = r2.zzaW     // Catch:{ all -> 0x0133 }
            r0.zzaW = r2     // Catch:{ all -> 0x0133 }
            com.google.android.gms.internal.zzax r0 = r10.zzro     // Catch:{ all -> 0x0133 }
            com.google.android.gms.internal.zzbu r2 = zzrm     // Catch:{ all -> 0x0133 }
            java.lang.String r2 = r2.zzaX     // Catch:{ all -> 0x0133 }
            r0.zzaX = r2     // Catch:{ all -> 0x0133 }
        L_0x00af:
            monitor-exit(r1)     // Catch:{ all -> 0x0133 }
            return
        L_0x00b1:
            r0 = r2
            goto L_0x002b
        L_0x00b4:
            com.google.android.gms.internal.zzau r0 = r10.zzrn     // Catch:{ all -> 0x0120 }
            java.lang.String r6 = zza(r0)     // Catch:{ all -> 0x0120 }
            boolean r6 = com.google.android.gms.internal.zzdg.zzo(r6)     // Catch:{ all -> 0x0120 }
            if (r6 == 0) goto L_0x010d
            if (r0 == 0) goto L_0x010d
            com.google.android.gms.internal.zzav r6 = r0.zzaQ     // Catch:{ all -> 0x0120 }
            if (r6 == 0) goto L_0x010d
            com.google.android.gms.internal.zzav r0 = r0.zzaQ     // Catch:{ all -> 0x0120 }
            java.lang.Integer r0 = r0.zzaS     // Catch:{ all -> 0x0120 }
            int r0 = r0.intValue()     // Catch:{ all -> 0x0120 }
            if (r0 != r3) goto L_0x010d
            r0 = r1
        L_0x00d1:
            java.lang.Boolean r0 = java.lang.Boolean.valueOf(r0)     // Catch:{ all -> 0x0120 }
            boolean r0 = r0.booleanValue()     // Catch:{ all -> 0x0120 }
            if (r0 == 0) goto L_0x010a
            com.google.android.gms.internal.zzdb r0 = r10.zzpJ     // Catch:{ all -> 0x0120 }
            boolean r0 = r0.zzJ()     // Catch:{ all -> 0x0120 }
            if (r0 == 0) goto L_0x010f
            com.google.android.gms.internal.zzme<java.lang.Boolean> r0 = com.google.android.gms.internal.zzmo.zzFc     // Catch:{ all -> 0x0120 }
            com.google.android.gms.internal.zzmm r6 = com.google.android.gms.ads.internal.zzbs.zzbL()     // Catch:{ all -> 0x0120 }
            java.lang.Object r0 = r6.zzd(r0)     // Catch:{ all -> 0x0120 }
            java.lang.Boolean r0 = (java.lang.Boolean) r0     // Catch:{ all -> 0x0120 }
            boolean r0 = r0.booleanValue()     // Catch:{ all -> 0x0120 }
            if (r0 == 0) goto L_0x010f
            com.google.android.gms.internal.zzme<java.lang.Boolean> r0 = com.google.android.gms.internal.zzmo.zzFd     // Catch:{ all -> 0x0120 }
            com.google.android.gms.internal.zzmm r6 = com.google.android.gms.ads.internal.zzbs.zzbL()     // Catch:{ all -> 0x0120 }
            java.lang.Object r0 = r6.zzd(r0)     // Catch:{ all -> 0x0120 }
            java.lang.Boolean r0 = (java.lang.Boolean) r0     // Catch:{ all -> 0x0120 }
            boolean r0 = r0.booleanValue()     // Catch:{ all -> 0x0120 }
            if (r0 == 0) goto L_0x010f
            r0 = r1
        L_0x0108:
            if (r0 != 0) goto L_0x003e
        L_0x010a:
            r3 = r4
            goto L_0x003e
        L_0x010d:
            r0 = r2
            goto L_0x00d1
        L_0x010f:
            r0 = r2
            goto L_0x0108
        L_0x0111:
            r0 = r2
            goto L_0x0051
        L_0x0114:
            com.google.android.gms.internal.zzbu r0 = zzrm     // Catch:{ all -> 0x0120 }
            com.google.android.gms.internal.zzau r1 = r10.zzrn     // Catch:{ all -> 0x0120 }
            com.google.android.gms.internal.zzaw r1 = r1.zzaR     // Catch:{ all -> 0x0120 }
            java.lang.String r1 = r1.zzaT     // Catch:{ all -> 0x0120 }
            r0.zzaT = r1     // Catch:{ all -> 0x0120 }
            goto L_0x007b
        L_0x0120:
            r0 = move-exception
            monitor-exit(r5)     // Catch:{ all -> 0x0120 }
            throw r0
        L_0x0123:
            java.lang.String r0 = r10.zzU()     // Catch:{ all -> 0x0120 }
            boolean r1 = com.google.android.gms.internal.zzdg.zzo(r0)     // Catch:{ all -> 0x0120 }
            if (r1 != 0) goto L_0x007b
            com.google.android.gms.internal.zzbu r1 = zzrm     // Catch:{ all -> 0x0120 }
            r1.zzaT = r0     // Catch:{ all -> 0x0120 }
            goto L_0x007b
        L_0x0133:
            r0 = move-exception
            monitor-exit(r1)     // Catch:{ all -> 0x0133 }
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzdl.zzT():void");
    }
}
