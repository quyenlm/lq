package com.google.android.gms.internal;

import java.io.OutputStream;

final class zzaav implements Runnable {
    private /* synthetic */ OutputStream zzTS;
    private /* synthetic */ byte[] zzTT;

    zzaav(zzaau zzaau, OutputStream outputStream, byte[] bArr) {
        this.zzTS = outputStream;
        this.zzTT = bArr;
    }

    /* JADX WARNING: Removed duplicated region for block: B:12:0x0029  */
    /* JADX WARNING: Removed duplicated region for block: B:13:0x002f  */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x0037  */
    /* JADX WARNING: Removed duplicated region for block: B:19:0x003d  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void run() {
        /*
            r4 = this;
            r2 = 0
            java.io.DataOutputStream r1 = new java.io.DataOutputStream     // Catch:{ IOException -> 0x0017, all -> 0x0033 }
            java.io.OutputStream r0 = r4.zzTS     // Catch:{ IOException -> 0x0017, all -> 0x0033 }
            r1.<init>(r0)     // Catch:{ IOException -> 0x0017, all -> 0x0033 }
            byte[] r0 = r4.zzTT     // Catch:{ IOException -> 0x0043 }
            int r0 = r0.length     // Catch:{ IOException -> 0x0043 }
            r1.writeInt(r0)     // Catch:{ IOException -> 0x0043 }
            byte[] r0 = r4.zzTT     // Catch:{ IOException -> 0x0043 }
            r1.write(r0)     // Catch:{ IOException -> 0x0043 }
            com.google.android.gms.common.util.zzn.closeQuietly(r1)
        L_0x0016:
            return
        L_0x0017:
            r0 = move-exception
            r1 = r2
        L_0x0019:
            java.lang.String r2 = "Error transporting the ad response"
            com.google.android.gms.internal.zzafr.zzb(r2, r0)     // Catch:{ all -> 0x0041 }
            com.google.android.gms.internal.zzafk r2 = com.google.android.gms.ads.internal.zzbs.zzbD()     // Catch:{ all -> 0x0041 }
            java.lang.String r3 = "LargeParcelTeleporter.pipeData.1"
            r2.zza((java.lang.Throwable) r0, (java.lang.String) r3)     // Catch:{ all -> 0x0041 }
            if (r1 != 0) goto L_0x002f
            java.io.OutputStream r0 = r4.zzTS
            com.google.android.gms.common.util.zzn.closeQuietly(r0)
            goto L_0x0016
        L_0x002f:
            com.google.android.gms.common.util.zzn.closeQuietly(r1)
            goto L_0x0016
        L_0x0033:
            r0 = move-exception
            r1 = r2
        L_0x0035:
            if (r1 != 0) goto L_0x003d
            java.io.OutputStream r1 = r4.zzTS
            com.google.android.gms.common.util.zzn.closeQuietly(r1)
        L_0x003c:
            throw r0
        L_0x003d:
            com.google.android.gms.common.util.zzn.closeQuietly(r1)
            goto L_0x003c
        L_0x0041:
            r0 = move-exception
            goto L_0x0035
        L_0x0043:
            r0 = move-exception
            goto L_0x0019
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzaav.run():void");
    }
}
