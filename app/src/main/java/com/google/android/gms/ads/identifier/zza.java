package com.google.android.gms.ads.identifier;

final class zza extends Thread {
    private /* synthetic */ String zzsD;

    zza(AdvertisingIdClient advertisingIdClient, String str) {
        this.zzsD = str;
    }

    /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void run() {
        /*
            r6 = this;
            com.google.android.gms.ads.identifier.zzb r0 = new com.google.android.gms.ads.identifier.zzb
            r0.<init>()
            java.lang.String r2 = r6.zzsD
            java.net.URL r0 = new java.net.URL     // Catch:{ IndexOutOfBoundsException -> 0x0053, IOException -> 0x00d2, RuntimeException -> 0x0092 }
            r0.<init>(r2)     // Catch:{ IndexOutOfBoundsException -> 0x0053, IOException -> 0x00d2, RuntimeException -> 0x0092 }
            java.net.URLConnection r0 = r0.openConnection()     // Catch:{ IndexOutOfBoundsException -> 0x0053, IOException -> 0x00d2, RuntimeException -> 0x0092 }
            java.net.HttpURLConnection r0 = (java.net.HttpURLConnection) r0     // Catch:{ IndexOutOfBoundsException -> 0x0053, IOException -> 0x00d2, RuntimeException -> 0x0092 }
            int r1 = r0.getResponseCode()     // Catch:{ all -> 0x004e }
            r3 = 200(0xc8, float:2.8E-43)
            if (r1 < r3) goto L_0x001e
            r3 = 300(0x12c, float:4.2E-43)
            if (r1 < r3) goto L_0x004a
        L_0x001e:
            java.lang.String r3 = "HttpUrlPinger"
            java.lang.String r4 = java.lang.String.valueOf(r2)     // Catch:{ all -> 0x004e }
            int r4 = r4.length()     // Catch:{ all -> 0x004e }
            int r4 = r4 + 65
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ all -> 0x004e }
            r5.<init>(r4)     // Catch:{ all -> 0x004e }
            java.lang.String r4 = "Received non-success response code "
            java.lang.StringBuilder r4 = r5.append(r4)     // Catch:{ all -> 0x004e }
            java.lang.StringBuilder r1 = r4.append(r1)     // Catch:{ all -> 0x004e }
            java.lang.String r4 = " from pinging URL: "
            java.lang.StringBuilder r1 = r1.append(r4)     // Catch:{ all -> 0x004e }
            java.lang.StringBuilder r1 = r1.append(r2)     // Catch:{ all -> 0x004e }
            java.lang.String r1 = r1.toString()     // Catch:{ all -> 0x004e }
            android.util.Log.w(r3, r1)     // Catch:{ all -> 0x004e }
        L_0x004a:
            r0.disconnect()     // Catch:{ IndexOutOfBoundsException -> 0x0053, IOException -> 0x00d2, RuntimeException -> 0x0092 }
        L_0x004d:
            return
        L_0x004e:
            r1 = move-exception
            r0.disconnect()     // Catch:{ IndexOutOfBoundsException -> 0x0053, IOException -> 0x00d2, RuntimeException -> 0x0092 }
            throw r1     // Catch:{ IndexOutOfBoundsException -> 0x0053, IOException -> 0x00d2, RuntimeException -> 0x0092 }
        L_0x0053:
            r0 = move-exception
            java.lang.String r1 = "HttpUrlPinger"
            java.lang.String r3 = r0.getMessage()
            java.lang.String r3 = java.lang.String.valueOf(r3)
            java.lang.String r4 = java.lang.String.valueOf(r2)
            int r4 = r4.length()
            int r4 = r4 + 32
            java.lang.String r5 = java.lang.String.valueOf(r3)
            int r5 = r5.length()
            int r4 = r4 + r5
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>(r4)
            java.lang.String r4 = "Error while parsing ping URL: "
            java.lang.StringBuilder r4 = r5.append(r4)
            java.lang.StringBuilder r2 = r4.append(r2)
            java.lang.String r4 = ". "
            java.lang.StringBuilder r2 = r2.append(r4)
            java.lang.StringBuilder r2 = r2.append(r3)
            java.lang.String r2 = r2.toString()
            android.util.Log.w(r1, r2, r0)
            goto L_0x004d
        L_0x0092:
            r0 = move-exception
        L_0x0093:
            java.lang.String r1 = "HttpUrlPinger"
            java.lang.String r3 = r0.getMessage()
            java.lang.String r3 = java.lang.String.valueOf(r3)
            java.lang.String r4 = java.lang.String.valueOf(r2)
            int r4 = r4.length()
            int r4 = r4 + 27
            java.lang.String r5 = java.lang.String.valueOf(r3)
            int r5 = r5.length()
            int r4 = r4 + r5
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>(r4)
            java.lang.String r4 = "Error while pinging URL: "
            java.lang.StringBuilder r4 = r5.append(r4)
            java.lang.StringBuilder r2 = r4.append(r2)
            java.lang.String r4 = ". "
            java.lang.StringBuilder r2 = r2.append(r4)
            java.lang.StringBuilder r2 = r2.append(r3)
            java.lang.String r2 = r2.toString()
            android.util.Log.w(r1, r2, r0)
            goto L_0x004d
        L_0x00d2:
            r0 = move-exception
            goto L_0x0093
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.ads.identifier.zza.run():void");
    }
}
