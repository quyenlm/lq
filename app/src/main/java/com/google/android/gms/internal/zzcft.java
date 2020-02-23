package com.google.android.gms.internal;

import android.support.annotation.WorkerThread;
import com.google.android.gms.common.internal.zzbo;
import java.net.URL;
import java.util.Map;

@WorkerThread
final class zzcft implements Runnable {
    private final String mPackageName;
    private final URL zzJu;
    private final byte[] zzaKA;
    private final zzcfr zzbrd;
    private final Map<String, String> zzbre;
    private /* synthetic */ zzcfp zzbrf;

    public zzcft(zzcfp zzcfp, String str, URL url, byte[] bArr, Map<String, String> map, zzcfr zzcfr) {
        this.zzbrf = zzcfp;
        zzbo.zzcF(str);
        zzbo.zzu(url);
        zzbo.zzu(zzcfr);
        this.zzJu = url;
        this.zzaKA = bArr;
        this.zzbrd = zzcfr;
        this.mPackageName = str;
        this.zzbre = map;
    }

    /* JADX WARNING: Removed duplicated region for block: B:12:0x0025  */
    /* JADX WARNING: Removed duplicated region for block: B:42:0x0113 A[SYNTHETIC, Splitter:B:42:0x0113] */
    /* JADX WARNING: Removed duplicated region for block: B:45:0x0118  */
    /* JADX WARNING: Removed duplicated region for block: B:9:0x0020 A[SYNTHETIC, Splitter:B:9:0x0020] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void run() {
        /*
            r13 = this;
            r3 = 0
            r4 = 0
            com.google.android.gms.internal.zzcfp r0 = r13.zzbrf
            r0.zzwq()
            java.net.URL r0 = r13.zzJu     // Catch:{ IOException -> 0x0019, all -> 0x010c }
            java.net.URLConnection r0 = r0.openConnection()     // Catch:{ IOException -> 0x0019, all -> 0x010c }
            boolean r1 = r0 instanceof java.net.HttpURLConnection     // Catch:{ IOException -> 0x0019, all -> 0x010c }
            if (r1 != 0) goto L_0x003d
            java.io.IOException r0 = new java.io.IOException     // Catch:{ IOException -> 0x0019, all -> 0x010c }
            java.lang.String r1 = "Failed to obtain HTTP connection"
            r0.<init>(r1)     // Catch:{ IOException -> 0x0019, all -> 0x010c }
            throw r0     // Catch:{ IOException -> 0x0019, all -> 0x010c }
        L_0x0019:
            r9 = move-exception
            r11 = r4
            r8 = r3
            r1 = r4
            r0 = r4
        L_0x001e:
            if (r1 == 0) goto L_0x0023
            r1.close()     // Catch:{ IOException -> 0x00f4 }
        L_0x0023:
            if (r0 == 0) goto L_0x0028
            r0.disconnect()
        L_0x0028:
            com.google.android.gms.internal.zzcfp r0 = r13.zzbrf
            com.google.android.gms.internal.zzcgg r0 = r0.zzwE()
            com.google.android.gms.internal.zzcfs r5 = new com.google.android.gms.internal.zzcfs
            java.lang.String r6 = r13.mPackageName
            com.google.android.gms.internal.zzcfr r7 = r13.zzbrd
            r10 = r4
            r12 = r4
            r5.<init>(r6, r7, r8, r9, r10, r11)
            r0.zzj(r5)
        L_0x003c:
            return
        L_0x003d:
            java.net.HttpURLConnection r0 = (java.net.HttpURLConnection) r0     // Catch:{ IOException -> 0x0019, all -> 0x010c }
            r1 = 0
            r0.setDefaultUseCaches(r1)     // Catch:{ IOException -> 0x0019, all -> 0x010c }
            com.google.android.gms.internal.zzcem.zzxz()     // Catch:{ IOException -> 0x0019, all -> 0x010c }
            r1 = 60000(0xea60, float:8.4078E-41)
            r0.setConnectTimeout(r1)     // Catch:{ IOException -> 0x0019, all -> 0x010c }
            com.google.android.gms.internal.zzcem.zzxA()     // Catch:{ IOException -> 0x0019, all -> 0x010c }
            r1 = 61000(0xee48, float:8.5479E-41)
            r0.setReadTimeout(r1)     // Catch:{ IOException -> 0x0019, all -> 0x010c }
            r1 = 0
            r0.setInstanceFollowRedirects(r1)     // Catch:{ IOException -> 0x0019, all -> 0x010c }
            r1 = 1
            r0.setDoInput(r1)     // Catch:{ IOException -> 0x0019, all -> 0x010c }
            java.util.Map<java.lang.String, java.lang.String> r1 = r13.zzbre     // Catch:{ IOException -> 0x0087, all -> 0x0147 }
            if (r1 == 0) goto L_0x008c
            java.util.Map<java.lang.String, java.lang.String> r1 = r13.zzbre     // Catch:{ IOException -> 0x0087, all -> 0x0147 }
            java.util.Set r1 = r1.entrySet()     // Catch:{ IOException -> 0x0087, all -> 0x0147 }
            java.util.Iterator r5 = r1.iterator()     // Catch:{ IOException -> 0x0087, all -> 0x0147 }
        L_0x006b:
            boolean r1 = r5.hasNext()     // Catch:{ IOException -> 0x0087, all -> 0x0147 }
            if (r1 == 0) goto L_0x008c
            java.lang.Object r1 = r5.next()     // Catch:{ IOException -> 0x0087, all -> 0x0147 }
            java.util.Map$Entry r1 = (java.util.Map.Entry) r1     // Catch:{ IOException -> 0x0087, all -> 0x0147 }
            java.lang.Object r2 = r1.getKey()     // Catch:{ IOException -> 0x0087, all -> 0x0147 }
            java.lang.String r2 = (java.lang.String) r2     // Catch:{ IOException -> 0x0087, all -> 0x0147 }
            java.lang.Object r1 = r1.getValue()     // Catch:{ IOException -> 0x0087, all -> 0x0147 }
            java.lang.String r1 = (java.lang.String) r1     // Catch:{ IOException -> 0x0087, all -> 0x0147 }
            r0.addRequestProperty(r2, r1)     // Catch:{ IOException -> 0x0087, all -> 0x0147 }
            goto L_0x006b
        L_0x0087:
            r9 = move-exception
            r11 = r4
            r8 = r3
            r1 = r4
            goto L_0x001e
        L_0x008c:
            byte[] r1 = r13.zzaKA     // Catch:{ IOException -> 0x0087, all -> 0x0147 }
            if (r1 == 0) goto L_0x00cc
            com.google.android.gms.internal.zzcfp r1 = r13.zzbrf     // Catch:{ IOException -> 0x0087, all -> 0x0147 }
            com.google.android.gms.internal.zzcjl r1 = r1.zzwB()     // Catch:{ IOException -> 0x0087, all -> 0x0147 }
            byte[] r2 = r13.zzaKA     // Catch:{ IOException -> 0x0087, all -> 0x0147 }
            byte[] r2 = r1.zzl((byte[]) r2)     // Catch:{ IOException -> 0x0087, all -> 0x0147 }
            com.google.android.gms.internal.zzcfp r1 = r13.zzbrf     // Catch:{ IOException -> 0x0087, all -> 0x0147 }
            com.google.android.gms.internal.zzcfl r1 = r1.zzwF()     // Catch:{ IOException -> 0x0087, all -> 0x0147 }
            com.google.android.gms.internal.zzcfn r1 = r1.zzyD()     // Catch:{ IOException -> 0x0087, all -> 0x0147 }
            java.lang.String r5 = "Uploading data. size"
            int r6 = r2.length     // Catch:{ IOException -> 0x0087, all -> 0x0147 }
            java.lang.Integer r6 = java.lang.Integer.valueOf(r6)     // Catch:{ IOException -> 0x0087, all -> 0x0147 }
            r1.zzj(r5, r6)     // Catch:{ IOException -> 0x0087, all -> 0x0147 }
            r1 = 1
            r0.setDoOutput(r1)     // Catch:{ IOException -> 0x0087, all -> 0x0147 }
            java.lang.String r1 = "Content-Encoding"
            java.lang.String r5 = "gzip"
            r0.addRequestProperty(r1, r5)     // Catch:{ IOException -> 0x0087, all -> 0x0147 }
            int r1 = r2.length     // Catch:{ IOException -> 0x0087, all -> 0x0147 }
            r0.setFixedLengthStreamingMode(r1)     // Catch:{ IOException -> 0x0087, all -> 0x0147 }
            r0.connect()     // Catch:{ IOException -> 0x0087, all -> 0x0147 }
            java.io.OutputStream r1 = r0.getOutputStream()     // Catch:{ IOException -> 0x0087, all -> 0x0147 }
            r1.write(r2)     // Catch:{ IOException -> 0x0158, all -> 0x014d }
            r1.close()     // Catch:{ IOException -> 0x0158, all -> 0x014d }
        L_0x00cc:
            int r3 = r0.getResponseCode()     // Catch:{ IOException -> 0x0087, all -> 0x0147 }
            java.util.Map r6 = r0.getHeaderFields()     // Catch:{ IOException -> 0x0087, all -> 0x0147 }
            com.google.android.gms.internal.zzcfp r1 = r13.zzbrf     // Catch:{ IOException -> 0x015d, all -> 0x0153 }
            byte[] r5 = com.google.android.gms.internal.zzcfp.zzc(r0)     // Catch:{ IOException -> 0x015d, all -> 0x0153 }
            if (r0 == 0) goto L_0x00df
            r0.disconnect()
        L_0x00df:
            com.google.android.gms.internal.zzcfp r0 = r13.zzbrf
            com.google.android.gms.internal.zzcgg r8 = r0.zzwE()
            com.google.android.gms.internal.zzcfs r0 = new com.google.android.gms.internal.zzcfs
            java.lang.String r1 = r13.mPackageName
            com.google.android.gms.internal.zzcfr r2 = r13.zzbrd
            r7 = r4
            r0.<init>(r1, r2, r3, r4, r5, r6)
            r8.zzj(r0)
            goto L_0x003c
        L_0x00f4:
            r1 = move-exception
            com.google.android.gms.internal.zzcfp r2 = r13.zzbrf
            com.google.android.gms.internal.zzcfl r2 = r2.zzwF()
            com.google.android.gms.internal.zzcfn r2 = r2.zzyx()
            java.lang.String r3 = "Error closing HTTP compressed POST connection output stream. appId"
            java.lang.String r5 = r13.mPackageName
            java.lang.Object r5 = com.google.android.gms.internal.zzcfl.zzdZ(r5)
            r2.zze(r3, r5, r1)
            goto L_0x0023
        L_0x010c:
            r0 = move-exception
            r8 = r0
            r6 = r4
            r5 = r4
            r7 = r4
        L_0x0111:
            if (r5 == 0) goto L_0x0116
            r5.close()     // Catch:{ IOException -> 0x0130 }
        L_0x0116:
            if (r7 == 0) goto L_0x011b
            r7.disconnect()
        L_0x011b:
            com.google.android.gms.internal.zzcfp r0 = r13.zzbrf
            com.google.android.gms.internal.zzcgg r9 = r0.zzwE()
            com.google.android.gms.internal.zzcfs r0 = new com.google.android.gms.internal.zzcfs
            java.lang.String r1 = r13.mPackageName
            com.google.android.gms.internal.zzcfr r2 = r13.zzbrd
            r5 = r4
            r7 = r4
            r0.<init>(r1, r2, r3, r4, r5, r6)
            r9.zzj(r0)
            throw r8
        L_0x0130:
            r0 = move-exception
            com.google.android.gms.internal.zzcfp r1 = r13.zzbrf
            com.google.android.gms.internal.zzcfl r1 = r1.zzwF()
            com.google.android.gms.internal.zzcfn r1 = r1.zzyx()
            java.lang.String r2 = "Error closing HTTP compressed POST connection output stream. appId"
            java.lang.String r5 = r13.mPackageName
            java.lang.Object r5 = com.google.android.gms.internal.zzcfl.zzdZ(r5)
            r1.zze(r2, r5, r0)
            goto L_0x0116
        L_0x0147:
            r1 = move-exception
            r8 = r1
            r6 = r4
            r5 = r4
            r7 = r0
            goto L_0x0111
        L_0x014d:
            r2 = move-exception
            r8 = r2
            r6 = r4
            r5 = r1
            r7 = r0
            goto L_0x0111
        L_0x0153:
            r1 = move-exception
            r8 = r1
            r5 = r4
            r7 = r0
            goto L_0x0111
        L_0x0158:
            r9 = move-exception
            r11 = r4
            r8 = r3
            goto L_0x001e
        L_0x015d:
            r9 = move-exception
            r11 = r6
            r8 = r3
            r1 = r4
            goto L_0x001e
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzcft.run():void");
    }
}
