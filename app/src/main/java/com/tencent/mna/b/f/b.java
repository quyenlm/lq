package com.tencent.mna.b.f;

import com.tencent.mna.base.utils.k;
import java.util.regex.Pattern;

/* compiled from: QosHttpConnection */
public class b {
    private static final Pattern a = Pattern.compile("^(([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\\.){3}([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])$");

    /* JADX WARNING: Removed duplicated region for block: B:19:0x0091  */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x00c3  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String a(java.lang.String r7, java.lang.String r8) {
        /*
            r2 = 0
            java.net.URL r0 = new java.net.URL     // Catch:{ Exception -> 0x00ca }
            r0.<init>(r7)     // Catch:{ Exception -> 0x00ca }
            java.net.URLConnection r0 = r0.openConnection()     // Catch:{ Exception -> 0x00ca }
            java.net.HttpURLConnection r0 = (java.net.HttpURLConnection) r0     // Catch:{ Exception -> 0x00ca }
            java.lang.String r1 = "POST"
            r0.setRequestMethod(r1)     // Catch:{ Exception -> 0x0073, all -> 0x00c7 }
            r1 = 10000(0x2710, float:1.4013E-41)
            r0.setReadTimeout(r1)     // Catch:{ Exception -> 0x0073, all -> 0x00c7 }
            r1 = 10000(0x2710, float:1.4013E-41)
            r0.setConnectTimeout(r1)     // Catch:{ Exception -> 0x0073, all -> 0x00c7 }
            r1 = 1
            r0.setDoInput(r1)     // Catch:{ Exception -> 0x0073, all -> 0x00c7 }
            r1 = 1
            r0.setDoOutput(r1)     // Catch:{ Exception -> 0x0073, all -> 0x00c7 }
            r1 = 1
            r0.setInstanceFollowRedirects(r1)     // Catch:{ Exception -> 0x0073, all -> 0x00c7 }
            java.lang.String r1 = "Content-Type"
            java.lang.String r2 = "application/json; charset=UTF-8"
            r0.setRequestProperty(r1, r2)     // Catch:{ Exception -> 0x0073, all -> 0x00c7 }
            r0.connect()     // Catch:{ Exception -> 0x0073, all -> 0x00c7 }
            java.io.DataOutputStream r1 = new java.io.DataOutputStream     // Catch:{ Exception -> 0x0073, all -> 0x00c7 }
            java.io.OutputStream r2 = r0.getOutputStream()     // Catch:{ Exception -> 0x0073, all -> 0x00c7 }
            r1.<init>(r2)     // Catch:{ Exception -> 0x0073, all -> 0x00c7 }
            r2 = 16
            byte[] r2 = new byte[r2]     // Catch:{ Exception -> 0x0073, all -> 0x00c7 }
            r2 = {77, 79, 67, 81, 79, 83, 50, 48, 49, 55, 65, 49, 68, 51, 72, 56} // fill-array     // Catch:{ Exception -> 0x0073, all -> 0x00c7 }
            byte[] r3 = com.tencent.mna.base.utils.a.a((byte[]) r2, (java.lang.String) r8)     // Catch:{ Exception -> 0x0073, all -> 0x00c7 }
            if (r3 == 0) goto L_0x004a
            r1.write(r3)     // Catch:{ Exception -> 0x0073, all -> 0x00c7 }
        L_0x004a:
            r1.flush()     // Catch:{ Exception -> 0x0073, all -> 0x00c7 }
            r1.close()     // Catch:{ Exception -> 0x0073, all -> 0x00c7 }
            int r1 = r0.getResponseCode()     // Catch:{ Exception -> 0x0073, all -> 0x00c7 }
            r3 = 200(0xc8, float:2.8E-43)
            if (r1 != r3) goto L_0x00b6
            java.io.InputStream r1 = r0.getInputStream()     // Catch:{ Exception -> 0x0073, all -> 0x00c7 }
            java.io.ByteArrayOutputStream r3 = new java.io.ByteArrayOutputStream     // Catch:{ Exception -> 0x0073, all -> 0x00c7 }
            r3.<init>()     // Catch:{ Exception -> 0x0073, all -> 0x00c7 }
            r4 = 1024(0x400, float:1.435E-42)
            byte[] r4 = new byte[r4]     // Catch:{ Exception -> 0x0073, all -> 0x00c7 }
        L_0x0065:
            r5 = 0
            int r6 = r4.length     // Catch:{ Exception -> 0x0073, all -> 0x00c7 }
            int r5 = r1.read(r4, r5, r6)     // Catch:{ Exception -> 0x0073, all -> 0x00c7 }
            r6 = -1
            if (r5 == r6) goto L_0x0097
            r6 = 0
            r3.write(r4, r6, r5)     // Catch:{ Exception -> 0x0073, all -> 0x00c7 }
            goto L_0x0065
        L_0x0073:
            r1 = move-exception
            r2 = r0
        L_0x0075:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ all -> 0x00bf }
            r0.<init>()     // Catch:{ all -> 0x00bf }
            java.lang.String r3 = "Qos http post failed, exception:"
            java.lang.StringBuilder r0 = r0.append(r3)     // Catch:{ all -> 0x00bf }
            java.lang.String r1 = r1.getMessage()     // Catch:{ all -> 0x00bf }
            java.lang.StringBuilder r0 = r0.append(r1)     // Catch:{ all -> 0x00bf }
            java.lang.String r0 = r0.toString()     // Catch:{ all -> 0x00bf }
            com.tencent.mna.base.utils.h.a((java.lang.String) r0)     // Catch:{ all -> 0x00bf }
            if (r2 == 0) goto L_0x0094
            r2.disconnect()
        L_0x0094:
            java.lang.String r0 = ""
        L_0x0096:
            return r0
        L_0x0097:
            r3.flush()     // Catch:{ Exception -> 0x0073, all -> 0x00c7 }
            byte[] r4 = r3.toByteArray()     // Catch:{ Exception -> 0x0073, all -> 0x00c7 }
            byte[] r2 = com.tencent.mna.base.utils.a.a((byte[]) r2, (byte[]) r4)     // Catch:{ Exception -> 0x0073, all -> 0x00c7 }
            r3.close()     // Catch:{ Exception -> 0x0073, all -> 0x00c7 }
            r1.close()     // Catch:{ Exception -> 0x0073, all -> 0x00c7 }
            java.lang.String r1 = new java.lang.String     // Catch:{ Exception -> 0x0073, all -> 0x00c7 }
            java.lang.String r3 = "UTF-8"
            r1.<init>(r2, r3)     // Catch:{ Exception -> 0x0073, all -> 0x00c7 }
            if (r0 == 0) goto L_0x00b4
            r0.disconnect()
        L_0x00b4:
            r0 = r1
            goto L_0x0096
        L_0x00b6:
            java.lang.String r1 = ""
            if (r0 == 0) goto L_0x00bd
            r0.disconnect()
        L_0x00bd:
            r0 = r1
            goto L_0x0096
        L_0x00bf:
            r0 = move-exception
            r1 = r0
        L_0x00c1:
            if (r2 == 0) goto L_0x00c6
            r2.disconnect()
        L_0x00c6:
            throw r1
        L_0x00c7:
            r1 = move-exception
            r2 = r0
            goto L_0x00c1
        L_0x00ca:
            r0 = move-exception
            r1 = r0
            goto L_0x0075
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.mna.b.f.b.a(java.lang.String, java.lang.String):java.lang.String");
    }

    /* JADX WARNING: Removed duplicated region for block: B:16:0x0075  */
    /* JADX WARNING: Removed duplicated region for block: B:31:0x00bd  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String a(java.lang.String r6) {
        /*
            r2 = 0
            java.net.URL r0 = new java.net.URL     // Catch:{ Exception -> 0x00c4 }
            r0.<init>(r6)     // Catch:{ Exception -> 0x00c4 }
            java.net.URLConnection r0 = r0.openConnection()     // Catch:{ Exception -> 0x00c4 }
            java.net.HttpURLConnection r0 = (java.net.HttpURLConnection) r0     // Catch:{ Exception -> 0x00c4 }
            java.lang.String r1 = "GET"
            r0.setRequestMethod(r1)     // Catch:{ Exception -> 0x0057, all -> 0x00c1 }
            r1 = 10000(0x2710, float:1.4013E-41)
            r0.setReadTimeout(r1)     // Catch:{ Exception -> 0x0057, all -> 0x00c1 }
            r1 = 10000(0x2710, float:1.4013E-41)
            r0.setConnectTimeout(r1)     // Catch:{ Exception -> 0x0057, all -> 0x00c1 }
            r1 = 1
            r0.setDoInput(r1)     // Catch:{ Exception -> 0x0057, all -> 0x00c1 }
            r1 = 1
            r0.setInstanceFollowRedirects(r1)     // Catch:{ Exception -> 0x0057, all -> 0x00c1 }
            java.lang.String r1 = "Content-Type"
            java.lang.String r2 = "application/x-www-form-urlencoded; charset=UTF-8"
            r0.setRequestProperty(r1, r2)     // Catch:{ Exception -> 0x0057, all -> 0x00c1 }
            java.lang.String r1 = "Accept"
            java.lang.String r2 = "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8,UC/145,plugin/1,alipay/u"
            r0.setRequestProperty(r1, r2)     // Catch:{ Exception -> 0x0057, all -> 0x00c1 }
            r0.connect()     // Catch:{ Exception -> 0x0057, all -> 0x00c1 }
            int r1 = r0.getResponseCode()     // Catch:{ Exception -> 0x0057, all -> 0x00c1 }
            r2 = 200(0xc8, float:2.8E-43)
            if (r1 != r2) goto L_0x0096
            java.io.InputStream r1 = r0.getInputStream()     // Catch:{ Exception -> 0x0057, all -> 0x00c1 }
            java.io.ByteArrayOutputStream r2 = new java.io.ByteArrayOutputStream     // Catch:{ Exception -> 0x0057, all -> 0x00c1 }
            r2.<init>()     // Catch:{ Exception -> 0x0057, all -> 0x00c1 }
            r3 = 1024(0x400, float:1.435E-42)
            byte[] r3 = new byte[r3]     // Catch:{ Exception -> 0x0057, all -> 0x00c1 }
        L_0x0049:
            r4 = 0
            int r5 = r3.length     // Catch:{ Exception -> 0x0057, all -> 0x00c1 }
            int r4 = r1.read(r3, r4, r5)     // Catch:{ Exception -> 0x0057, all -> 0x00c1 }
            r5 = -1
            if (r4 == r5) goto L_0x007b
            r5 = 0
            r2.write(r3, r5, r4)     // Catch:{ Exception -> 0x0057, all -> 0x00c1 }
            goto L_0x0049
        L_0x0057:
            r1 = move-exception
            r2 = r0
        L_0x0059:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ all -> 0x00b9 }
            r0.<init>()     // Catch:{ all -> 0x00b9 }
            java.lang.String r3 = "Qos http get failed, exception:"
            java.lang.StringBuilder r0 = r0.append(r3)     // Catch:{ all -> 0x00b9 }
            java.lang.String r1 = r1.getMessage()     // Catch:{ all -> 0x00b9 }
            java.lang.StringBuilder r0 = r0.append(r1)     // Catch:{ all -> 0x00b9 }
            java.lang.String r0 = r0.toString()     // Catch:{ all -> 0x00b9 }
            com.tencent.mna.base.utils.h.a((java.lang.String) r0)     // Catch:{ all -> 0x00b9 }
            if (r2 == 0) goto L_0x0078
            r2.disconnect()
        L_0x0078:
            java.lang.String r0 = ""
        L_0x007a:
            return r0
        L_0x007b:
            r2.flush()     // Catch:{ Exception -> 0x0057, all -> 0x00c1 }
            byte[] r3 = r2.toByteArray()     // Catch:{ Exception -> 0x0057, all -> 0x00c1 }
            r2.close()     // Catch:{ Exception -> 0x0057, all -> 0x00c1 }
            r1.close()     // Catch:{ Exception -> 0x0057, all -> 0x00c1 }
            java.lang.String r1 = new java.lang.String     // Catch:{ Exception -> 0x0057, all -> 0x00c1 }
            java.lang.String r2 = "UTF-8"
            r1.<init>(r3, r2)     // Catch:{ Exception -> 0x0057, all -> 0x00c1 }
            if (r0 == 0) goto L_0x0094
            r0.disconnect()
        L_0x0094:
            r0 = r1
            goto L_0x007a
        L_0x0096:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0057, all -> 0x00c1 }
            r1.<init>()     // Catch:{ Exception -> 0x0057, all -> 0x00c1 }
            java.lang.String r2 = "Http failed, responseCode:"
            java.lang.StringBuilder r1 = r1.append(r2)     // Catch:{ Exception -> 0x0057, all -> 0x00c1 }
            int r2 = r0.getResponseCode()     // Catch:{ Exception -> 0x0057, all -> 0x00c1 }
            java.lang.StringBuilder r1 = r1.append(r2)     // Catch:{ Exception -> 0x0057, all -> 0x00c1 }
            java.lang.String r1 = r1.toString()     // Catch:{ Exception -> 0x0057, all -> 0x00c1 }
            com.tencent.mna.base.utils.h.c(r1)     // Catch:{ Exception -> 0x0057, all -> 0x00c1 }
            java.lang.String r1 = ""
            if (r0 == 0) goto L_0x00b7
            r0.disconnect()
        L_0x00b7:
            r0 = r1
            goto L_0x007a
        L_0x00b9:
            r0 = move-exception
            r1 = r0
        L_0x00bb:
            if (r2 == 0) goto L_0x00c0
            r2.disconnect()
        L_0x00c0:
            throw r1
        L_0x00c1:
            r1 = move-exception
            r2 = r0
            goto L_0x00bb
        L_0x00c4:
            r0 = move-exception
            r1 = r0
            goto L_0x0059
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.mna.b.f.b.a(java.lang.String):java.lang.String");
    }

    public static boolean b(String str) {
        return a.matcher(str).matches();
    }

    public static String a() {
        if (k.a(com.tencent.mna.b.g()) == 4) {
            return null;
        }
        return k.a(true);
    }
}
