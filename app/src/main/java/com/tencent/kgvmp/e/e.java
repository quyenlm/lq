package com.tencent.kgvmp.e;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class e {
    private static final String a = e.class.getSimpleName();
    private int b = 1000;
    private int c = 1000;

    private String a(InputStream inputStream) {
        StringBuilder sb = new StringBuilder();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"));
        for (String readLine = bufferedReader.readLine(); readLine != null; readLine = bufferedReader.readLine()) {
            sb.append(readLine);
        }
        inputStream.close();
        return sb.toString();
    }

    /* JADX WARNING: Removed duplicated region for block: B:22:0x0043 A[SYNTHETIC, Splitter:B:22:0x0043] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.String a(java.lang.String r4) {
        /*
            r3 = this;
            r1 = 0
            java.net.URL r0 = new java.net.URL     // Catch:{ Exception -> 0x002e, all -> 0x0040 }
            r0.<init>(r4)     // Catch:{ Exception -> 0x002e, all -> 0x0040 }
            java.net.URLConnection r0 = r0.openConnection()     // Catch:{ Exception -> 0x002e, all -> 0x0040 }
            java.net.HttpURLConnection r0 = (java.net.HttpURLConnection) r0     // Catch:{ Exception -> 0x002e, all -> 0x0040 }
            int r2 = r3.b     // Catch:{ Exception -> 0x002e, all -> 0x0040 }
            r0.setConnectTimeout(r2)     // Catch:{ Exception -> 0x002e, all -> 0x0040 }
            int r2 = r3.c     // Catch:{ Exception -> 0x002e, all -> 0x0040 }
            r0.setReadTimeout(r2)     // Catch:{ Exception -> 0x002e, all -> 0x0040 }
            java.io.BufferedInputStream r2 = new java.io.BufferedInputStream     // Catch:{ Exception -> 0x002e, all -> 0x0040 }
            java.io.InputStream r0 = r0.getInputStream()     // Catch:{ Exception -> 0x002e, all -> 0x0040 }
            r2.<init>(r0)     // Catch:{ Exception -> 0x002e, all -> 0x0040 }
            java.lang.String r0 = r3.a((java.io.InputStream) r2)     // Catch:{ Exception -> 0x004f }
            if (r2 == 0) goto L_0x0028
            r2.close()     // Catch:{ Exception -> 0x0029 }
        L_0x0028:
            return r0
        L_0x0029:
            r1 = move-exception
            r1.printStackTrace()
            goto L_0x0028
        L_0x002e:
            r0 = move-exception
            r2 = r1
        L_0x0030:
            r0.printStackTrace()     // Catch:{ all -> 0x004c }
            if (r2 == 0) goto L_0x0038
            r2.close()     // Catch:{ Exception -> 0x003a }
        L_0x0038:
            r0 = r1
            goto L_0x0028
        L_0x003a:
            r0 = move-exception
            r0.printStackTrace()
            r0 = r1
            goto L_0x0028
        L_0x0040:
            r0 = move-exception
        L_0x0041:
            if (r1 == 0) goto L_0x0046
            r1.close()     // Catch:{ Exception -> 0x0047 }
        L_0x0046:
            throw r0
        L_0x0047:
            r1 = move-exception
            r1.printStackTrace()
            goto L_0x0046
        L_0x004c:
            r0 = move-exception
            r1 = r2
            goto L_0x0041
        L_0x004f:
            r0 = move-exception
            goto L_0x0030
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.kgvmp.e.e.a(java.lang.String):java.lang.String");
    }

    public void a(int i) {
        this.b = i;
    }

    public void b(int i) {
        this.c = i;
    }
}
