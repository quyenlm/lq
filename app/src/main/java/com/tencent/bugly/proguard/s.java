package com.tencent.bugly.proguard;

import android.content.Context;
import android.os.Process;
import android.os.SystemClock;
import com.tencent.bugly.crashreport.common.info.b;
import com.tencent.imsdk.tool.etc.APNUtil;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

/* compiled from: BUGLY */
public final class s {
    private static s b;
    public Map<String, String> a = null;
    private Context c;

    private s(Context context) {
        this.c = context;
    }

    public static s a(Context context) {
        if (b == null) {
            b = new s(context);
        }
        return b;
    }

    public final byte[] a(String str, byte[] bArr, v vVar, Map<String, String> map) {
        long length;
        long j;
        if (str == null) {
            x.e("Failed for no URL.", new Object[0]);
            return null;
        }
        int i = 0;
        int i2 = 0;
        if (bArr == null) {
            length = 0;
        } else {
            length = (long) bArr.length;
        }
        x.c("request: %s, send: %d (pid=%d | tid=%d)", str, Long.valueOf(length), Integer.valueOf(Process.myPid()), Integer.valueOf(Process.myTid()));
        boolean z = false;
        String str2 = str;
        while (i <= 0 && i2 <= 0) {
            if (z) {
                z = false;
            } else {
                i++;
                if (i > 1) {
                    x.c("try time: " + i, new Object[0]);
                    SystemClock.sleep(((long) new Random(System.currentTimeMillis()).nextInt(10000)) + 10000);
                }
            }
            String c2 = b.c(this.c);
            if (c2 == null) {
                x.d("Failed to request for network not avail", new Object[0]);
            } else {
                vVar.a(length);
                HttpURLConnection a2 = a(str2, bArr, c2, map);
                if (a2 != null) {
                    try {
                        int responseCode = a2.getResponseCode();
                        if (responseCode == 200) {
                            this.a = a(a2);
                            byte[] b2 = b(a2);
                            if (b2 == null) {
                                j = 0;
                            } else {
                                j = (long) b2.length;
                            }
                            vVar.b(j);
                            try {
                                a2.disconnect();
                            } catch (Throwable th) {
                                if (!x.a(th)) {
                                    th.printStackTrace();
                                }
                            }
                            return b2;
                        }
                        if (responseCode == 301 || responseCode == 302 || responseCode == 303 || responseCode == 307) {
                            z = true;
                            String headerField = a2.getHeaderField("Location");
                            if (headerField == null) {
                                x.e("Failed to redirect: %d" + responseCode, new Object[0]);
                                try {
                                    a2.disconnect();
                                } catch (Throwable th2) {
                                    if (!x.a(th2)) {
                                        th2.printStackTrace();
                                    }
                                }
                                return null;
                            }
                            i2++;
                            i = 0;
                            try {
                                x.c("redirect code: %d ,to:%s", Integer.valueOf(responseCode), headerField);
                                str2 = headerField;
                            } catch (IOException e) {
                                e = e;
                                str2 = headerField;
                                try {
                                    if (!x.a(e)) {
                                        e.printStackTrace();
                                    }
                                    try {
                                        a2.disconnect();
                                    } catch (Throwable th3) {
                                        if (!x.a(th3)) {
                                            th3.printStackTrace();
                                        }
                                    }
                                } catch (Throwable th4) {
                                    if (!x.a(th4)) {
                                        th4.printStackTrace();
                                    }
                                }
                            }
                        }
                        x.d("response code " + responseCode, new Object[0]);
                        long contentLength = (long) a2.getContentLength();
                        if (contentLength < 0) {
                            contentLength = 0;
                        }
                        vVar.b(contentLength);
                        try {
                            a2.disconnect();
                        } catch (Throwable th5) {
                            if (!x.a(th5)) {
                                th5.printStackTrace();
                            }
                        }
                    } catch (IOException e2) {
                        e = e2;
                    }
                } else {
                    x.c("Failed to execute post.", new Object[0]);
                    vVar.b(0);
                }
            }
        }
        return null;
        throw th;
    }

    private static Map<String, String> a(HttpURLConnection httpURLConnection) {
        HashMap hashMap = new HashMap();
        Map headerFields = httpURLConnection.getHeaderFields();
        if (headerFields == null || headerFields.size() == 0) {
            return null;
        }
        for (String str : headerFields.keySet()) {
            List list = (List) headerFields.get(str);
            if (list.size() > 0) {
                hashMap.put(str, list.get(0));
            }
        }
        return hashMap;
    }

    /* JADX WARNING: Removed duplicated region for block: B:29:0x004a A[SYNTHETIC, Splitter:B:29:0x004a] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static byte[] b(java.net.HttpURLConnection r6) {
        /*
            r0 = 0
            if (r6 != 0) goto L_0x0004
        L_0x0003:
            return r0
        L_0x0004:
            java.io.BufferedInputStream r2 = new java.io.BufferedInputStream     // Catch:{ Throwable -> 0x0056, all -> 0x0046 }
            java.io.InputStream r1 = r6.getInputStream()     // Catch:{ Throwable -> 0x0056, all -> 0x0046 }
            r2.<init>(r1)     // Catch:{ Throwable -> 0x0056, all -> 0x0046 }
            java.io.ByteArrayOutputStream r1 = new java.io.ByteArrayOutputStream     // Catch:{ Throwable -> 0x0021 }
            r1.<init>()     // Catch:{ Throwable -> 0x0021 }
            r3 = 1024(0x400, float:1.435E-42)
            byte[] r3 = new byte[r3]     // Catch:{ Throwable -> 0x0021 }
        L_0x0016:
            int r4 = r2.read(r3)     // Catch:{ Throwable -> 0x0021 }
            if (r4 <= 0) goto L_0x0036
            r5 = 0
            r1.write(r3, r5, r4)     // Catch:{ Throwable -> 0x0021 }
            goto L_0x0016
        L_0x0021:
            r1 = move-exception
        L_0x0022:
            boolean r3 = com.tencent.bugly.proguard.x.a(r1)     // Catch:{ all -> 0x0053 }
            if (r3 != 0) goto L_0x002b
            r1.printStackTrace()     // Catch:{ all -> 0x0053 }
        L_0x002b:
            if (r2 == 0) goto L_0x0003
            r2.close()     // Catch:{ Throwable -> 0x0031 }
            goto L_0x0003
        L_0x0031:
            r1 = move-exception
            r1.printStackTrace()
            goto L_0x0003
        L_0x0036:
            r1.flush()     // Catch:{ Throwable -> 0x0021 }
            byte[] r0 = r1.toByteArray()     // Catch:{ Throwable -> 0x0021 }
            r2.close()     // Catch:{ Throwable -> 0x0041 }
            goto L_0x0003
        L_0x0041:
            r1 = move-exception
            r1.printStackTrace()
            goto L_0x0003
        L_0x0046:
            r1 = move-exception
            r2 = r0
        L_0x0048:
            if (r2 == 0) goto L_0x004d
            r2.close()     // Catch:{ Throwable -> 0x004e }
        L_0x004d:
            throw r1
        L_0x004e:
            r0 = move-exception
            r0.printStackTrace()
            goto L_0x004d
        L_0x0053:
            r0 = move-exception
            r1 = r0
            goto L_0x0048
        L_0x0056:
            r1 = move-exception
            r2 = r0
            goto L_0x0022
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.proguard.s.b(java.net.HttpURLConnection):byte[]");
    }

    private HttpURLConnection a(String str, byte[] bArr, String str2, Map<String, String> map) {
        if (str == null) {
            x.e("destUrl is null.", new Object[0]);
            return null;
        }
        HttpURLConnection a2 = a(str2, str);
        if (a2 == null) {
            x.e("Failed to get HttpURLConnection object.", new Object[0]);
            return null;
        }
        try {
            a2.setRequestProperty("wup_version", "3.0");
            if (map != null && map.size() > 0) {
                for (Map.Entry next : map.entrySet()) {
                    a2.setRequestProperty((String) next.getKey(), URLEncoder.encode((String) next.getValue(), "utf-8"));
                }
            }
            a2.setRequestProperty("A37", URLEncoder.encode(str2, "utf-8"));
            a2.setRequestProperty("A38", URLEncoder.encode(str2, "utf-8"));
            OutputStream outputStream = a2.getOutputStream();
            if (bArr == null) {
                outputStream.write(0);
            } else {
                outputStream.write(bArr);
            }
            return a2;
        } catch (Throwable th) {
            if (!x.a(th)) {
                th.printStackTrace();
            }
            x.e("Failed to upload, please check your network.", new Object[0]);
            return null;
        }
    }

    private static HttpURLConnection a(String str, String str2) {
        HttpURLConnection httpURLConnection;
        try {
            URL url = new URL(str2);
            if (a.b() != null) {
                httpURLConnection = (HttpURLConnection) url.openConnection(a.b());
            } else if (str == null || !str.toLowerCase(Locale.US).contains(APNUtil.ANP_NAME_WAP)) {
                httpURLConnection = (HttpURLConnection) url.openConnection();
            } else {
                httpURLConnection = (HttpURLConnection) url.openConnection(new Proxy(Proxy.Type.HTTP, new InetSocketAddress(System.getProperty("http.proxyHost"), Integer.parseInt(System.getProperty("http.proxyPort")))));
            }
            httpURLConnection.setConnectTimeout(30000);
            httpURLConnection.setReadTimeout(10000);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setUseCaches(false);
            httpURLConnection.setInstanceFollowRedirects(false);
            return httpURLConnection;
        } catch (Throwable th) {
            if (!x.a(th)) {
                th.printStackTrace();
            }
            return null;
        }
    }
}
