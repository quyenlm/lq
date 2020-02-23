package com.tencent.beacon.cover;

import android.content.Context;
import android.os.Build;
import com.appsflyer.AppsFlyerLib;
import com.google.android.gms.appinvite.PreviewActivity;
import com.tencent.component.debug.TraceFormat;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;

/* compiled from: VersionUpdateRequester */
public final class i implements Runnable {
    private String a = null;
    private Context b;
    private List<a> c = null;
    private c d = null;

    public i(Context context, List<a> list) {
        this.b = context;
        this.c = new ArrayList();
        this.c.addAll(list);
        this.a = g.f(this.b);
        this.d = new c(this.b);
        this.d.a();
    }

    private String a() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("appkey", g.b(this.b));
            jSONObject.put("appversion", g.c(this.b));
            jSONObject.put("model", g.a());
            jSONObject.put(AppsFlyerLib.ATTRIBUTION_ID_COLUMN_NAME, g.d(this.b));
            jSONObject.put("imei", g.e(this.b));
            jSONObject.put("cpuabi", g.b());
            jSONObject.put("coverSDKver", "1.0.0");
            JSONArray jSONArray = new JSONArray();
            if (this.c != null) {
                for (a next : this.c) {
                    if (next != null) {
                        JSONObject jSONObject2 = new JSONObject();
                        jSONObject2.put("compId", next.a);
                        jSONObject2.put("compVer", next.b);
                        jSONObject2.put("compType", next.c);
                        jSONObject2.put("md5", next.g);
                        jSONArray.put(jSONObject2);
                    }
                }
            }
            jSONObject.put("compList", jSONArray);
        } catch (Exception e) {
        }
        g.a(TraceFormat.STR_DEBUG, "post json data:" + jSONObject.toString(), new Object[0]);
        return jSONObject.toString();
    }

    private boolean a(String str) {
        try {
            JSONObject jSONObject = new JSONObject(str);
            if (jSONObject.getString("appkey").equals(g.b(this.b)) && jSONObject.getString("appversion").equals(g.c(this.b)) && jSONObject.getString("coverSDKver").equals("1.0.0")) {
                if (jSONObject.getInt("isUpdate") != 1) {
                    return true;
                }
                JSONArray jSONArray = jSONObject.getJSONArray("updateList");
                if (jSONArray.length() <= 0) {
                    return true;
                }
                ArrayList arrayList = new ArrayList();
                for (int i = 0; i < jSONArray.length(); i++) {
                    JSONObject jSONObject2 = jSONArray.getJSONObject(i);
                    a aVar = new a();
                    aVar.a = jSONObject2.getInt("compId");
                    aVar.b = jSONObject2.getString("compVer");
                    aVar.c = jSONObject2.getInt("compType");
                    aVar.d = jSONObject2.getString("name");
                    aVar.e = jSONObject2.getString("url");
                    aVar.f = jSONObject2.getInt("size");
                    aVar.g = jSONObject2.getString("md5");
                    if (aVar.c == g.c) {
                        aVar.h = jSONObject.getString("cpuabi");
                    }
                    arrayList.add(aVar);
                }
                if (arrayList.size() <= 0) {
                    return true;
                }
                this.d.a((List<a>) arrayList);
                return true;
            }
        } catch (Exception e) {
            g.a(TraceFormat.STR_ERROR, "parse the response data to json object failed!", new Object[0]);
        }
        return false;
    }

    private boolean b() {
        byte[] a2;
        String a3 = a();
        String a4 = g.a(this.b, this.a);
        try {
            byte[] a5 = g.a(true, this.a, a3.getBytes("utf-8"));
            if (a5 == null) {
                return false;
            }
            HashMap hashMap = new HashMap();
            hashMap.put("Content-Type", "application/x-www-form-urlencoded");
            hashMap.put("Content-Length", String.valueOf(a5.length));
            hashMap.put("encr_type", "rsapost");
            hashMap.put("rsa_encr_key", a4);
            int i = 0;
            while (true) {
                int i2 = i + 1;
                if (i >= 3) {
                    return false;
                }
                g.a(TraceFormat.STR_DEBUG, "start http post: http://oth.update.mdt.qq.com:8080/beacon/vercheck", new Object[0]);
                HttpURLConnection a6 = a("http://oth.update.mdt.qq.com:8080/beacon/vercheck", (Map<String, String>) hashMap);
                if (!(a6 == null || (a2 = a(a6, a5)) == null)) {
                    try {
                        if (g.a(false, this.a, a2) != null) {
                            String str = new String(g.a(false, this.a, a2));
                            g.a(TraceFormat.STR_DEBUG, "ResponseData: " + str, new Object[0]);
                            return a(str);
                        }
                    } catch (Exception e) {
                        g.a(TraceFormat.STR_ERROR, "decode response data error!", new Object[0]);
                    }
                }
                g.a(10000);
                i = i2;
            }
        } catch (Exception e2) {
            g.a(TraceFormat.STR_ERROR, "Encry post data error!", new Object[0]);
            return false;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:24:0x005e A[SYNTHETIC, Splitter:B:24:0x005e] */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x0063 A[SYNTHETIC, Splitter:B:27:0x0063] */
    /* JADX WARNING: Removed duplicated region for block: B:52:0x00a5 A[SYNTHETIC, Splitter:B:52:0x00a5] */
    /* JADX WARNING: Removed duplicated region for block: B:55:0x00aa A[SYNTHETIC, Splitter:B:55:0x00aa] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    protected static byte[] a(java.net.HttpURLConnection r7, byte[] r8) {
        /*
            r0 = 0
            r3 = 0
            java.io.OutputStream r1 = r7.getOutputStream()     // Catch:{ Exception -> 0x0067 }
            r1.write(r8)     // Catch:{ Exception -> 0x0067 }
            r1.flush()     // Catch:{ Exception -> 0x0067 }
        L_0x000c:
            int r1 = r7.getResponseCode()     // Catch:{ Exception -> 0x008b, all -> 0x00a0 }
            r2 = 200(0xc8, float:2.8E-43)
            if (r1 != r2) goto L_0x0066
            java.io.InputStream r1 = r7.getInputStream()     // Catch:{ Exception -> 0x008b, all -> 0x00a0 }
            java.lang.String r2 = r7.getContentEncoding()     // Catch:{ Exception -> 0x008b, all -> 0x00a0 }
            if (r2 == 0) goto L_0x0075
            java.lang.String r3 = "gzip"
            boolean r3 = r2.equalsIgnoreCase(r3)     // Catch:{ Exception -> 0x008b, all -> 0x00a0 }
            if (r3 == 0) goto L_0x0075
            java.util.zip.GZIPInputStream r2 = new java.util.zip.GZIPInputStream     // Catch:{ Exception -> 0x008b, all -> 0x00a0 }
            r2.<init>(r1)     // Catch:{ Exception -> 0x008b, all -> 0x00a0 }
        L_0x002b:
            java.io.ByteArrayOutputStream r3 = new java.io.ByteArrayOutputStream     // Catch:{ Exception -> 0x00be, all -> 0x00b8 }
            r3.<init>()     // Catch:{ Exception -> 0x00be, all -> 0x00b8 }
            r1 = 128(0x80, float:1.794E-43)
            byte[] r1 = new byte[r1]     // Catch:{ Exception -> 0x0040 }
        L_0x0034:
            int r4 = r2.read(r1)     // Catch:{ Exception -> 0x0040 }
            r5 = -1
            if (r4 == r5) goto L_0x0091
            r5 = 0
            r3.write(r1, r5, r4)     // Catch:{ Exception -> 0x0040 }
            goto L_0x0034
        L_0x0040:
            r1 = move-exception
        L_0x0041:
            java.lang.String r4 = "E"
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ all -> 0x00bb }
            java.lang.String r6 = "parse response failure: "
            r5.<init>(r6)     // Catch:{ all -> 0x00bb }
            java.lang.String r1 = r1.toString()     // Catch:{ all -> 0x00bb }
            java.lang.StringBuilder r1 = r5.append(r1)     // Catch:{ all -> 0x00bb }
            java.lang.String r1 = r1.toString()     // Catch:{ all -> 0x00bb }
            r5 = 0
            java.lang.Object[] r5 = new java.lang.Object[r5]     // Catch:{ all -> 0x00bb }
            com.tencent.beacon.cover.g.a((java.lang.String) r4, (java.lang.String) r1, (java.lang.Object[]) r5)     // Catch:{ all -> 0x00bb }
            if (r2 == 0) goto L_0x0061
            r2.close()     // Catch:{ IOException -> 0x00b0 }
        L_0x0061:
            if (r3 == 0) goto L_0x0066
            r3.close()     // Catch:{ IOException -> 0x00b2 }
        L_0x0066:
            return r0
        L_0x0067:
            r1 = move-exception
            r1.printStackTrace()
            java.lang.String r1 = "E"
            java.lang.String r2 = "httpURLConnection write post data error!"
            java.lang.Object[] r3 = new java.lang.Object[r3]
            com.tencent.beacon.cover.g.a((java.lang.String) r1, (java.lang.String) r2, (java.lang.Object[]) r3)
            goto L_0x000c
        L_0x0075:
            if (r2 == 0) goto L_0x008f
            java.lang.String r3 = "deflate"
            boolean r2 = r2.equalsIgnoreCase(r3)     // Catch:{ Exception -> 0x008b, all -> 0x00a0 }
            if (r2 == 0) goto L_0x008f
            java.util.zip.InflaterInputStream r2 = new java.util.zip.InflaterInputStream     // Catch:{ Exception -> 0x008b, all -> 0x00a0 }
            java.util.zip.Inflater r3 = new java.util.zip.Inflater     // Catch:{ Exception -> 0x008b, all -> 0x00a0 }
            r4 = 1
            r3.<init>(r4)     // Catch:{ Exception -> 0x008b, all -> 0x00a0 }
            r2.<init>(r1, r3)     // Catch:{ Exception -> 0x008b, all -> 0x00a0 }
            goto L_0x002b
        L_0x008b:
            r1 = move-exception
            r2 = r0
            r3 = r0
            goto L_0x0041
        L_0x008f:
            r2 = r1
            goto L_0x002b
        L_0x0091:
            byte[] r0 = r3.toByteArray()     // Catch:{ Exception -> 0x0040 }
            if (r2 == 0) goto L_0x009a
            r2.close()     // Catch:{ IOException -> 0x00ae }
        L_0x009a:
            r3.close()     // Catch:{ IOException -> 0x009e }
            goto L_0x0066
        L_0x009e:
            r1 = move-exception
            goto L_0x0066
        L_0x00a0:
            r1 = move-exception
            r2 = r0
            r3 = r0
        L_0x00a3:
            if (r2 == 0) goto L_0x00a8
            r2.close()     // Catch:{ IOException -> 0x00b4 }
        L_0x00a8:
            if (r3 == 0) goto L_0x00ad
            r3.close()     // Catch:{ IOException -> 0x00b6 }
        L_0x00ad:
            throw r1
        L_0x00ae:
            r1 = move-exception
            goto L_0x009a
        L_0x00b0:
            r1 = move-exception
            goto L_0x0061
        L_0x00b2:
            r1 = move-exception
            goto L_0x0066
        L_0x00b4:
            r0 = move-exception
            goto L_0x00a8
        L_0x00b6:
            r0 = move-exception
            goto L_0x00ad
        L_0x00b8:
            r1 = move-exception
            r3 = r0
            goto L_0x00a3
        L_0x00bb:
            r0 = move-exception
            r1 = r0
            goto L_0x00a3
        L_0x00be:
            r1 = move-exception
            r3 = r0
            goto L_0x0041
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.beacon.cover.i.a(java.net.HttpURLConnection, byte[]):byte[]");
    }

    protected static HttpURLConnection a(String str, Map<String, String> map) {
        try {
            HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(str).openConnection();
            try {
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                httpURLConnection.setUseCaches(false);
                httpURLConnection.setConnectTimeout(20000);
                httpURLConnection.setReadTimeout(20000);
                if (Build.VERSION.SDK_INT > 13) {
                    httpURLConnection.setRequestProperty("Connection", PreviewActivity.ON_CLICK_LISTENER_CLOSE);
                } else {
                    httpURLConnection.setRequestProperty("http.keepAlive", "false");
                }
                for (Map.Entry next : map.entrySet()) {
                    httpURLConnection.setRequestProperty((String) next.getKey(), (String) next.getValue());
                }
                return httpURLConnection;
            } catch (Exception e) {
                return httpURLConnection;
            }
        } catch (Exception e2) {
            return null;
        }
    }

    public final void run() {
        if (b()) {
            g.a(TraceFormat.STR_INFO, "version check request success!", new Object[0]);
        }
    }
}
