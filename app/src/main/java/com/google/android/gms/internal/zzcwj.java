package com.google.android.gms.internal;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Locale;

final class zzcwj implements zzcus {
    private final Context mContext;
    private final String zzJP;
    private final zzcwm zzbJc;
    private final zzcwl zzbJd;

    zzcwj(Context context, zzcwl zzcwl) {
        this(new zzcwk(), context, zzcwl);
    }

    private zzcwj(zzcwm zzcwm, Context context, zzcwl zzcwl) {
        String str = null;
        this.zzbJc = zzcwm;
        this.mContext = context.getApplicationContext();
        this.zzbJd = zzcwl;
        String str2 = Build.VERSION.RELEASE;
        Locale locale = Locale.getDefault();
        if (!(locale == null || locale.getLanguage() == null || locale.getLanguage().length() == 0)) {
            StringBuilder sb = new StringBuilder();
            sb.append(locale.getLanguage().toLowerCase());
            if (!(locale.getCountry() == null || locale.getCountry().length() == 0)) {
                sb.append("-").append(locale.getCountry().toLowerCase());
            }
            str = sb.toString();
        }
        this.zzJP = String.format("%s/%s (Linux; U; Android %s; %s; %s Build/%s)", new Object[]{"GoogleTagManager", "5.05", str2, str, Build.MODEL, Build.ID});
    }

    private static URL zzd(zzcuw zzcuw) {
        try {
            return new URL(zzcuw.zzBo());
        } catch (MalformedURLException e) {
            zzcvk.e("Error trying to parse the GTM url.");
            return null;
        }
    }

    public final boolean zzBf() {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) this.mContext.getSystemService("connectivity")).getActiveNetworkInfo();
        if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
            return true;
        }
        zzcvk.v("...no network connectivity");
        return false;
    }

    /* JADX WARNING: Removed duplicated region for block: B:120:0x007b A[SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x0078 A[SYNTHETIC, Splitter:B:24:0x0078] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void zzK(java.util.List<com.google.android.gms.internal.zzcuw> r15) {
        /*
            r14 = this;
            int r0 = r15.size()
            r1 = 40
            int r7 = java.lang.Math.min(r0, r1)
            r1 = 1
            r0 = 0
            r6 = r0
        L_0x000d:
            if (r6 >= r7) goto L_0x027c
            java.lang.Object r0 = r15.get(r6)
            com.google.android.gms.internal.zzcuw r0 = (com.google.android.gms.internal.zzcuw) r0
            java.net.URL r8 = zzd(r0)
            java.lang.String r9 = r0.zzCo()
            java.util.Map r2 = r0.zzCp()
            java.lang.String r4 = r0.zzCq()
            if (r8 != 0) goto L_0x0037
            java.lang.String r2 = "No destination: discarding hit."
            com.google.android.gms.internal.zzcvk.zzaT(r2)
            com.google.android.gms.internal.zzcwl r2 = r14.zzbJd
            r2.zzb(r0)
            r0 = r1
        L_0x0032:
            int r2 = r6 + 1
            r6 = r2
            r1 = r0
            goto L_0x000d
        L_0x0037:
            r5 = 0
            com.google.android.gms.internal.zzcwm r3 = r14.zzbJc     // Catch:{ IOException -> 0x027d }
            java.net.HttpURLConnection r10 = r3.zzc(r8)     // Catch:{ IOException -> 0x027d }
            if (r1 == 0) goto L_0x028d
            android.content.Context r3 = r14.mContext     // Catch:{ all -> 0x0280 }
            com.google.android.gms.internal.zzcvm.zzbt(r3)     // Catch:{ all -> 0x0280 }
            r3 = 0
        L_0x0046:
            java.lang.String r1 = "User-Agent"
            java.lang.String r11 = r14.zzJP     // Catch:{ all -> 0x0073 }
            r10.setRequestProperty(r1, r11)     // Catch:{ all -> 0x0073 }
            if (r2 == 0) goto L_0x00d3
            java.util.Set r1 = r2.entrySet()     // Catch:{ all -> 0x0073 }
            java.util.Iterator r11 = r1.iterator()     // Catch:{ all -> 0x0073 }
        L_0x0057:
            boolean r1 = r11.hasNext()     // Catch:{ all -> 0x0073 }
            if (r1 == 0) goto L_0x00d3
            java.lang.Object r1 = r11.next()     // Catch:{ all -> 0x0073 }
            java.util.Map$Entry r1 = (java.util.Map.Entry) r1     // Catch:{ all -> 0x0073 }
            java.lang.Object r2 = r1.getKey()     // Catch:{ all -> 0x0073 }
            java.lang.String r2 = (java.lang.String) r2     // Catch:{ all -> 0x0073 }
            java.lang.Object r1 = r1.getValue()     // Catch:{ all -> 0x0073 }
            java.lang.String r1 = (java.lang.String) r1     // Catch:{ all -> 0x0073 }
            r10.setRequestProperty(r2, r1)     // Catch:{ all -> 0x0073 }
            goto L_0x0057
        L_0x0073:
            r1 = move-exception
            r2 = r1
            r4 = r5
        L_0x0076:
            if (r4 == 0) goto L_0x007b
            r4.close()     // Catch:{ IOException -> 0x007f }
        L_0x007b:
            r10.disconnect()     // Catch:{ IOException -> 0x007f }
            throw r2     // Catch:{ IOException -> 0x007f }
        L_0x007f:
            r2 = move-exception
            r1 = r3
        L_0x0081:
            java.lang.String r3 = java.lang.String.valueOf(r8)
            java.lang.Class r4 = r2.getClass()
            java.lang.String r4 = r4.getSimpleName()
            java.lang.String r4 = java.lang.String.valueOf(r4)
            java.lang.String r5 = java.lang.String.valueOf(r3)
            int r5 = r5.length()
            int r5 = r5 + 27
            java.lang.String r8 = java.lang.String.valueOf(r4)
            int r8 = r8.length()
            int r5 = r5 + r8
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            r8.<init>(r5)
            java.lang.String r5 = "Exception sending hit to "
            java.lang.StringBuilder r5 = r8.append(r5)
            java.lang.StringBuilder r3 = r5.append(r3)
            java.lang.String r5 = ": "
            java.lang.StringBuilder r3 = r3.append(r5)
            java.lang.StringBuilder r3 = r3.append(r4)
            java.lang.String r3 = r3.toString()
            com.google.android.gms.internal.zzcvk.zzaT(r3)
            java.lang.String r2 = r2.getMessage()
            com.google.android.gms.internal.zzcvk.zzaT(r2)
            com.google.android.gms.internal.zzcwl r2 = r14.zzbJd
            r2.zzc(r0)
            r0 = r1
            goto L_0x0032
        L_0x00d3:
            if (r9 != 0) goto L_0x00f7
            java.lang.String r1 = "Hit %d retrieved from the store has null HTTP method."
            r2 = 1
            java.lang.Object[] r2 = new java.lang.Object[r2]     // Catch:{ all -> 0x0073 }
            r4 = 0
            long r12 = r0.zzBm()     // Catch:{ all -> 0x0073 }
            java.lang.Long r9 = java.lang.Long.valueOf(r12)     // Catch:{ all -> 0x0073 }
            r2[r4] = r9     // Catch:{ all -> 0x0073 }
            java.lang.String r1 = java.lang.String.format(r1, r2)     // Catch:{ all -> 0x0073 }
            com.google.android.gms.internal.zzcvk.zzaT(r1)     // Catch:{ all -> 0x0073 }
            com.google.android.gms.internal.zzcwl r1 = r14.zzbJd     // Catch:{ all -> 0x0073 }
            r1.zzb(r0)     // Catch:{ all -> 0x0073 }
            r10.disconnect()     // Catch:{ IOException -> 0x007f }
            r0 = r3
            goto L_0x0032
        L_0x00f7:
            java.lang.String r1 = "GET"
            boolean r1 = r9.equals(r1)     // Catch:{ all -> 0x0073 }
            if (r1 != 0) goto L_0x0131
            java.lang.String r1 = "HEAD"
            boolean r1 = r9.equals(r1)     // Catch:{ all -> 0x0073 }
            if (r1 != 0) goto L_0x0131
            java.lang.String r1 = "POST"
            boolean r1 = r9.equals(r1)     // Catch:{ all -> 0x0073 }
            if (r1 != 0) goto L_0x0131
            java.lang.String r1 = "PUT"
            boolean r1 = r9.equals(r1)     // Catch:{ all -> 0x0073 }
            if (r1 != 0) goto L_0x0131
            java.lang.String r1 = "Unrecongnized HTTP method %s. Supported methods are GET, HEAD, PUT and/or POST"
            r2 = 1
            java.lang.Object[] r2 = new java.lang.Object[r2]     // Catch:{ all -> 0x0073 }
            r4 = 0
            r2[r4] = r9     // Catch:{ all -> 0x0073 }
            java.lang.String r1 = java.lang.String.format(r1, r2)     // Catch:{ all -> 0x0073 }
            com.google.android.gms.internal.zzcvk.zzaT(r1)     // Catch:{ all -> 0x0073 }
            com.google.android.gms.internal.zzcwl r1 = r14.zzbJd     // Catch:{ all -> 0x0073 }
            r1.zzb(r0)     // Catch:{ all -> 0x0073 }
            r10.disconnect()     // Catch:{ IOException -> 0x007f }
            r0 = r3
            goto L_0x0032
        L_0x0131:
            r1 = -1
            int r2 = r9.hashCode()     // Catch:{ all -> 0x0073 }
            switch(r2) {
                case 70454: goto L_0x0197;
                case 79599: goto L_0x01b5;
                case 2213344: goto L_0x01a1;
                case 2461856: goto L_0x01ab;
                default: goto L_0x0139;
            }     // Catch:{ all -> 0x0073 }
        L_0x0139:
            switch(r1) {
                case 0: goto L_0x01c0;
                case 1: goto L_0x01c0;
                case 2: goto L_0x01d9;
                case 3: goto L_0x01d9;
                default: goto L_0x013c;
            }     // Catch:{ all -> 0x0073 }
        L_0x013c:
            int r1 = r10.getResponseCode()     // Catch:{ all -> 0x0073 }
            r2 = 200(0xc8, float:2.8E-43)
            if (r1 == r2) goto L_0x0234
            java.lang.String r2 = java.lang.String.valueOf(r8)     // Catch:{ all -> 0x0073 }
            java.lang.String r4 = java.lang.String.valueOf(r2)     // Catch:{ all -> 0x0073 }
            int r4 = r4.length()     // Catch:{ all -> 0x0073 }
            int r4 = r4 + 39
            java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch:{ all -> 0x0073 }
            r9.<init>(r4)     // Catch:{ all -> 0x0073 }
            java.lang.String r4 = "Bad response received for "
            java.lang.StringBuilder r4 = r9.append(r4)     // Catch:{ all -> 0x0073 }
            java.lang.StringBuilder r2 = r4.append(r2)     // Catch:{ all -> 0x0073 }
            java.lang.String r4 = ": "
            java.lang.StringBuilder r2 = r2.append(r4)     // Catch:{ all -> 0x0073 }
            java.lang.StringBuilder r1 = r2.append(r1)     // Catch:{ all -> 0x0073 }
            java.lang.String r1 = r1.toString()     // Catch:{ all -> 0x0073 }
            com.google.android.gms.internal.zzcvk.zzaT(r1)     // Catch:{ all -> 0x0073 }
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ all -> 0x0073 }
            r1.<init>()     // Catch:{ all -> 0x0073 }
            r4 = 0
            java.io.BufferedReader r2 = new java.io.BufferedReader     // Catch:{ all -> 0x0289 }
            java.io.InputStreamReader r9 = new java.io.InputStreamReader     // Catch:{ all -> 0x0289 }
            java.io.InputStream r11 = r10.getErrorStream()     // Catch:{ all -> 0x0289 }
            r9.<init>(r11)     // Catch:{ all -> 0x0289 }
            r2.<init>(r9)     // Catch:{ all -> 0x0289 }
        L_0x0186:
            java.lang.String r4 = r2.readLine()     // Catch:{ all -> 0x0190 }
            if (r4 == 0) goto L_0x0204
            r1.append(r4)     // Catch:{ all -> 0x0190 }
            goto L_0x0186
        L_0x0190:
            r1 = move-exception
        L_0x0191:
            if (r2 == 0) goto L_0x0196
            r2.close()     // Catch:{ all -> 0x0073 }
        L_0x0196:
            throw r1     // Catch:{ all -> 0x0073 }
        L_0x0197:
            java.lang.String r2 = "GET"
            boolean r2 = r9.equals(r2)     // Catch:{ all -> 0x0073 }
            if (r2 == 0) goto L_0x0139
            r1 = 0
            goto L_0x0139
        L_0x01a1:
            java.lang.String r2 = "HEAD"
            boolean r2 = r9.equals(r2)     // Catch:{ all -> 0x0073 }
            if (r2 == 0) goto L_0x0139
            r1 = 1
            goto L_0x0139
        L_0x01ab:
            java.lang.String r2 = "POST"
            boolean r2 = r9.equals(r2)     // Catch:{ all -> 0x0073 }
            if (r2 == 0) goto L_0x0139
            r1 = 2
            goto L_0x0139
        L_0x01b5:
            java.lang.String r2 = "PUT"
            boolean r2 = r9.equals(r2)     // Catch:{ all -> 0x0073 }
            if (r2 == 0) goto L_0x0139
            r1 = 3
            goto L_0x0139
        L_0x01c0:
            if (r4 == 0) goto L_0x01d4
            java.lang.String r1 = "Body of %s hit is ignored: %s."
            r2 = 2
            java.lang.Object[] r2 = new java.lang.Object[r2]     // Catch:{ all -> 0x0073 }
            r11 = 0
            r2[r11] = r9     // Catch:{ all -> 0x0073 }
            r11 = 1
            r2[r11] = r4     // Catch:{ all -> 0x0073 }
            java.lang.String r1 = java.lang.String.format(r1, r2)     // Catch:{ all -> 0x0073 }
            com.google.android.gms.internal.zzcvk.zzaT(r1)     // Catch:{ all -> 0x0073 }
        L_0x01d4:
            r10.setRequestMethod(r9)     // Catch:{ all -> 0x0073 }
            goto L_0x013c
        L_0x01d9:
            r10.setRequestMethod(r9)     // Catch:{ all -> 0x0073 }
            if (r4 == 0) goto L_0x013c
            r1 = 1
            r10.setDoOutput(r1)     // Catch:{ all -> 0x0073 }
            java.lang.String r1 = "UTF-8"
            java.nio.charset.Charset r1 = java.nio.charset.Charset.forName(r1)     // Catch:{ all -> 0x0073 }
            byte[] r1 = r4.getBytes(r1)     // Catch:{ all -> 0x0073 }
            int r2 = r1.length     // Catch:{ all -> 0x0073 }
            r10.setFixedLengthStreamingMode(r2)     // Catch:{ all -> 0x0073 }
            java.io.BufferedOutputStream r2 = new java.io.BufferedOutputStream     // Catch:{ all -> 0x0073 }
            java.io.OutputStream r4 = r10.getOutputStream()     // Catch:{ all -> 0x0073 }
            r2.<init>(r4)     // Catch:{ all -> 0x0073 }
            r2.write(r1)     // Catch:{ all -> 0x0073 }
            r2.flush()     // Catch:{ all -> 0x0073 }
            r2.close()     // Catch:{ all -> 0x0073 }
            goto L_0x013c
        L_0x0204:
            java.lang.String r4 = "Error Message: "
            java.lang.String r1 = r1.toString()     // Catch:{ all -> 0x0190 }
            java.lang.String r1 = java.lang.String.valueOf(r1)     // Catch:{ all -> 0x0190 }
            int r9 = r1.length()     // Catch:{ all -> 0x0190 }
            if (r9 == 0) goto L_0x022e
            java.lang.String r1 = r4.concat(r1)     // Catch:{ all -> 0x0190 }
        L_0x0218:
            com.google.android.gms.internal.zzcvk.zzaT(r1)     // Catch:{ all -> 0x0190 }
            r2.close()     // Catch:{ all -> 0x0073 }
            com.google.android.gms.internal.zzcwl r1 = r14.zzbJd     // Catch:{ all -> 0x0073 }
            r1.zzc(r0)     // Catch:{ all -> 0x0073 }
        L_0x0223:
            if (r5 == 0) goto L_0x0228
            r5.close()     // Catch:{ IOException -> 0x007f }
        L_0x0228:
            r10.disconnect()     // Catch:{ IOException -> 0x007f }
            r0 = r3
            goto L_0x0032
        L_0x022e:
            java.lang.String r1 = new java.lang.String     // Catch:{ all -> 0x0190 }
            r1.<init>(r4)     // Catch:{ all -> 0x0190 }
            goto L_0x0218
        L_0x0234:
            java.io.InputStream r4 = r10.getInputStream()     // Catch:{ all -> 0x0073 }
            java.lang.String r1 = java.lang.String.valueOf(r8)     // Catch:{ all -> 0x0285 }
            java.lang.String r2 = java.lang.String.valueOf(r1)     // Catch:{ all -> 0x0285 }
            int r2 = r2.length()     // Catch:{ all -> 0x0285 }
            int r2 = r2 + 23
            java.lang.String r5 = java.lang.String.valueOf(r9)     // Catch:{ all -> 0x0285 }
            int r5 = r5.length()     // Catch:{ all -> 0x0285 }
            int r2 = r2 + r5
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ all -> 0x0285 }
            r5.<init>(r2)     // Catch:{ all -> 0x0285 }
            java.lang.String r2 = "Hit sent to "
            java.lang.StringBuilder r2 = r5.append(r2)     // Catch:{ all -> 0x0285 }
            java.lang.StringBuilder r1 = r2.append(r1)     // Catch:{ all -> 0x0285 }
            java.lang.String r2 = "(method = "
            java.lang.StringBuilder r1 = r1.append(r2)     // Catch:{ all -> 0x0285 }
            java.lang.StringBuilder r1 = r1.append(r9)     // Catch:{ all -> 0x0285 }
            java.lang.String r2 = ")"
            java.lang.StringBuilder r1 = r1.append(r2)     // Catch:{ all -> 0x0285 }
            java.lang.String r1 = r1.toString()     // Catch:{ all -> 0x0285 }
            com.google.android.gms.internal.zzcvk.v(r1)     // Catch:{ all -> 0x0285 }
            com.google.android.gms.internal.zzcwl r1 = r14.zzbJd     // Catch:{ all -> 0x0285 }
            r1.zza(r0)     // Catch:{ all -> 0x0285 }
            r5 = r4
            goto L_0x0223
        L_0x027c:
            return
        L_0x027d:
            r2 = move-exception
            goto L_0x0081
        L_0x0280:
            r2 = move-exception
            r4 = r5
            r3 = r1
            goto L_0x0076
        L_0x0285:
            r1 = move-exception
            r2 = r1
            goto L_0x0076
        L_0x0289:
            r1 = move-exception
            r2 = r4
            goto L_0x0191
        L_0x028d:
            r3 = r1
            goto L_0x0046
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzcwj.zzK(java.util.List):void");
    }
}
