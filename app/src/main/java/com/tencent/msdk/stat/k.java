package com.tencent.msdk.stat;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import com.tencent.imsdk.framework.request.HttpRequestParams;
import com.tencent.msdk.a.a.d;
import com.tencent.msdk.a.a.h;
import com.tencent.msdk.a.a.i;
import com.tencent.msdk.stat.common.StatLogger;
import com.tencent.msdk.stat.common.j;
import java.util.Arrays;
import java.util.List;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.json.JSONObject;

class k {
    private static StatLogger d = j.b();
    private static k e = null;
    private static Context f = null;
    DefaultHttpClient a = null;
    Handler b = null;
    StringBuilder c = new StringBuilder(4096);
    private long g = 0;

    private k(Context context) {
        try {
            HandlerThread handlerThread = new HandlerThread("StatDispatcher");
            handlerThread.start();
            this.b = new Handler(handlerThread.getLooper());
            f = context.getApplicationContext();
            this.g = System.currentTimeMillis() / 1000;
            BasicHttpParams basicHttpParams = new BasicHttpParams();
            HttpConnectionParams.setStaleCheckingEnabled(basicHttpParams, false);
            HttpConnectionParams.setConnectionTimeout(basicHttpParams, 10000);
            HttpConnectionParams.setSoTimeout(basicHttpParams, 10000);
            this.a = new DefaultHttpClient(basicHttpParams);
            this.a.setKeepAliveStrategy(new l(this));
        } catch (Throwable th) {
            d.e(th);
        }
    }

    static Context a() {
        return f;
    }

    static void a(Context context) {
        f = context.getApplicationContext();
    }

    private void a(JSONObject jSONObject) {
        try {
            String optString = jSONObject.optString(HttpRequestParams.PUSH_XG_ID);
            if (i.c(optString)) {
                if (StatConfig.isDebugEnable()) {
                    d.i("update mid:" + optString);
                }
                d dVar = new d();
                dVar.a = aj.a(f).b(f).b();
                dVar.b = aj.a(f).b(f).c();
                dVar.d = System.currentTimeMillis();
                dVar.c = optString;
                h.a(f).a(dVar);
            }
            if (!jSONObject.isNull("cfg")) {
                StatConfig.a(f, jSONObject.getJSONObject("cfg"));
            }
            if (!jSONObject.isNull("ncts")) {
                int i = jSONObject.getInt("ncts");
                int currentTimeMillis = (int) (((long) i) - (System.currentTimeMillis() / 1000));
                if (StatConfig.isDebugEnable()) {
                    d.i("server time:" + i + ", diff time:" + currentTimeMillis);
                }
                j.y(f);
                j.a(f, currentTimeMillis);
            }
        } catch (Throwable th) {
            d.w(th);
        }
    }

    static k b(Context context) {
        if (e == null) {
            synchronized (k.class) {
                if (e == null) {
                    e = new k(context);
                }
            }
        }
        return e;
    }

    /* access modifiers changed from: package-private */
    public void a(com.tencent.msdk.stat.a.d dVar, j jVar) {
        b(Arrays.asList(new String[]{dVar.g()}), jVar);
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Code restructure failed: missing block: B:37:0x01c7, code lost:
        r0 = th;
     */
    /* JADX WARNING: Exception block dominator not found, dom blocks: [] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void a(java.util.List<?> r11, com.tencent.msdk.stat.j r12) {
        /*
            r10 = this;
            r1 = 0
            r2 = 0
            if (r11 == 0) goto L_0x000a
            boolean r0 = r11.isEmpty()
            if (r0 == 0) goto L_0x000b
        L_0x000a:
            return
        L_0x000b:
            int r3 = r11.size()
            java.lang.StringBuilder r0 = r10.c     // Catch:{ Throwable -> 0x01c7, all -> 0x023a }
            r4 = 0
            java.lang.StringBuilder r5 = r10.c     // Catch:{ Throwable -> 0x01c7, all -> 0x023a }
            int r5 = r5.length()     // Catch:{ Throwable -> 0x01c7, all -> 0x023a }
            r0.delete(r4, r5)     // Catch:{ Throwable -> 0x01c7, all -> 0x023a }
            java.lang.StringBuilder r0 = r10.c     // Catch:{ Throwable -> 0x01c7, all -> 0x023a }
            java.lang.String r4 = "["
            r0.append(r4)     // Catch:{ Throwable -> 0x01c7, all -> 0x023a }
            java.lang.String r4 = "rc4"
            r0 = r2
        L_0x0025:
            if (r0 >= r3) goto L_0x0042
            java.lang.StringBuilder r5 = r10.c     // Catch:{ Throwable -> 0x01c7, all -> 0x023a }
            java.lang.Object r6 = r11.get(r0)     // Catch:{ Throwable -> 0x01c7, all -> 0x023a }
            java.lang.String r6 = r6.toString()     // Catch:{ Throwable -> 0x01c7, all -> 0x023a }
            r5.append(r6)     // Catch:{ Throwable -> 0x01c7, all -> 0x023a }
            int r5 = r3 + -1
            if (r0 == r5) goto L_0x003f
            java.lang.StringBuilder r5 = r10.c     // Catch:{ Throwable -> 0x01c7, all -> 0x023a }
            java.lang.String r6 = ","
            r5.append(r6)     // Catch:{ Throwable -> 0x01c7, all -> 0x023a }
        L_0x003f:
            int r0 = r0 + 1
            goto L_0x0025
        L_0x0042:
            java.lang.StringBuilder r0 = r10.c     // Catch:{ Throwable -> 0x01c7, all -> 0x023a }
            java.lang.String r3 = "]"
            r0.append(r3)     // Catch:{ Throwable -> 0x01c7, all -> 0x023a }
            java.lang.StringBuilder r0 = r10.c     // Catch:{ Throwable -> 0x01c7, all -> 0x023a }
            java.lang.String r0 = r0.toString()     // Catch:{ Throwable -> 0x01c7, all -> 0x023a }
            int r3 = r0.length()     // Catch:{ Throwable -> 0x01c7, all -> 0x023a }
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x01c7, all -> 0x023a }
            r5.<init>()     // Catch:{ Throwable -> 0x01c7, all -> 0x023a }
            java.lang.String r6 = com.tencent.msdk.stat.StatConfig.getStatReportUrl()     // Catch:{ Throwable -> 0x01c7, all -> 0x023a }
            java.lang.StringBuilder r5 = r5.append(r6)     // Catch:{ Throwable -> 0x01c7, all -> 0x023a }
            java.lang.String r6 = "/?index="
            java.lang.StringBuilder r5 = r5.append(r6)     // Catch:{ Throwable -> 0x01c7, all -> 0x023a }
            long r6 = r10.g     // Catch:{ Throwable -> 0x01c7, all -> 0x023a }
            java.lang.StringBuilder r5 = r5.append(r6)     // Catch:{ Throwable -> 0x01c7, all -> 0x023a }
            java.lang.String r5 = r5.toString()     // Catch:{ Throwable -> 0x01c7, all -> 0x023a }
            long r6 = r10.g     // Catch:{ Throwable -> 0x01c7, all -> 0x023a }
            r8 = 1
            long r6 = r6 + r8
            r10.g = r6     // Catch:{ Throwable -> 0x01c7, all -> 0x023a }
            boolean r6 = com.tencent.msdk.stat.StatConfig.isDebugEnable()     // Catch:{ Throwable -> 0x01c7, all -> 0x023a }
            if (r6 == 0) goto L_0x00a9
            com.tencent.msdk.stat.common.StatLogger r6 = d     // Catch:{ Throwable -> 0x01c7, all -> 0x023a }
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x01c7, all -> 0x023a }
            r7.<init>()     // Catch:{ Throwable -> 0x01c7, all -> 0x023a }
            java.lang.String r8 = "["
            java.lang.StringBuilder r7 = r7.append(r8)     // Catch:{ Throwable -> 0x01c7, all -> 0x023a }
            java.lang.StringBuilder r7 = r7.append(r5)     // Catch:{ Throwable -> 0x01c7, all -> 0x023a }
            java.lang.String r8 = "]Send request("
            java.lang.StringBuilder r7 = r7.append(r8)     // Catch:{ Throwable -> 0x01c7, all -> 0x023a }
            java.lang.StringBuilder r7 = r7.append(r3)     // Catch:{ Throwable -> 0x01c7, all -> 0x023a }
            java.lang.String r8 = "bytes), content:"
            java.lang.StringBuilder r7 = r7.append(r8)     // Catch:{ Throwable -> 0x01c7, all -> 0x023a }
            java.lang.StringBuilder r7 = r7.append(r0)     // Catch:{ Throwable -> 0x01c7, all -> 0x023a }
            java.lang.String r7 = r7.toString()     // Catch:{ Throwable -> 0x01c7, all -> 0x023a }
            r6.i(r7)     // Catch:{ Throwable -> 0x01c7, all -> 0x023a }
        L_0x00a9:
            org.apache.http.client.methods.HttpPost r6 = new org.apache.http.client.methods.HttpPost     // Catch:{ Throwable -> 0x01c7, all -> 0x023a }
            r6.<init>((java.lang.String) r5)     // Catch:{ Throwable -> 0x01c7, all -> 0x023a }
            java.lang.String r5 = "Accept-Encoding"
            java.lang.String r7 = "gzip"
            r6.addHeader(r5, r7)     // Catch:{ Throwable -> 0x01c7, all -> 0x023a }
            java.lang.String r5 = "Connection"
            java.lang.String r7 = "Keep-Alive"
            r6.setHeader(r5, r7)     // Catch:{ Throwable -> 0x01c7, all -> 0x023a }
            java.lang.String r5 = "Cache-Control"
            r6.removeHeaders(r5)     // Catch:{ Throwable -> 0x01c7, all -> 0x023a }
            android.content.Context r5 = f     // Catch:{ Throwable -> 0x01c7, all -> 0x023a }
            com.tencent.msdk.stat.a r5 = com.tencent.msdk.stat.a.a((android.content.Context) r5)     // Catch:{ Throwable -> 0x01c7, all -> 0x023a }
            org.apache.http.HttpHost r5 = r5.a()     // Catch:{ Throwable -> 0x01c7, all -> 0x023a }
            java.lang.String r7 = "Content-Encoding"
            r6.addHeader(r7, r4)     // Catch:{ Throwable -> 0x01c7, all -> 0x023a }
            if (r5 != 0) goto L_0x01f1
            org.apache.http.impl.client.DefaultHttpClient r7 = r10.a     // Catch:{ Throwable -> 0x01c7, all -> 0x023a }
            org.apache.http.params.HttpParams r7 = r7.getParams()     // Catch:{ Throwable -> 0x01c7, all -> 0x023a }
            java.lang.String r8 = "http.route.default-proxy"
            r7.removeParameter(r8)     // Catch:{ Throwable -> 0x01c7, all -> 0x023a }
        L_0x00dd:
            java.io.ByteArrayOutputStream r7 = new java.io.ByteArrayOutputStream     // Catch:{ Throwable -> 0x01c7, all -> 0x023a }
            r7.<init>(r3)     // Catch:{ Throwable -> 0x01c7, all -> 0x023a }
            java.lang.String r8 = "UTF-8"
            byte[] r0 = r0.getBytes(r8)     // Catch:{ Throwable -> 0x01c7, all -> 0x023a }
            int r8 = r0.length     // Catch:{ Throwable -> 0x01c7, all -> 0x023a }
            int r9 = com.tencent.msdk.stat.StatConfig.o     // Catch:{ Throwable -> 0x01c7, all -> 0x023a }
            if (r3 <= r9) goto L_0x00ee
            r2 = 1
        L_0x00ee:
            if (r2 == 0) goto L_0x0166
            java.lang.String r2 = "Content-Encoding"
            r6.removeHeaders(r2)     // Catch:{ Throwable -> 0x01c7, all -> 0x023a }
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x01c7, all -> 0x023a }
            r2.<init>()     // Catch:{ Throwable -> 0x01c7, all -> 0x023a }
            java.lang.StringBuilder r2 = r2.append(r4)     // Catch:{ Throwable -> 0x01c7, all -> 0x023a }
            java.lang.String r3 = ",gzip"
            java.lang.StringBuilder r2 = r2.append(r3)     // Catch:{ Throwable -> 0x01c7, all -> 0x023a }
            java.lang.String r2 = r2.toString()     // Catch:{ Throwable -> 0x01c7, all -> 0x023a }
            java.lang.String r3 = "Content-Encoding"
            r6.addHeader(r3, r2)     // Catch:{ Throwable -> 0x01c7, all -> 0x023a }
            if (r5 == 0) goto L_0x0119
            java.lang.String r3 = "X-Content-Encoding"
            r6.removeHeaders(r3)     // Catch:{ Throwable -> 0x01c7, all -> 0x023a }
            java.lang.String r3 = "X-Content-Encoding"
            r6.addHeader(r3, r2)     // Catch:{ Throwable -> 0x01c7, all -> 0x023a }
        L_0x0119:
            r2 = 4
            byte[] r2 = new byte[r2]     // Catch:{ Throwable -> 0x01c7, all -> 0x023a }
            r7.write(r2)     // Catch:{ Throwable -> 0x01c7, all -> 0x023a }
            java.util.zip.GZIPOutputStream r2 = new java.util.zip.GZIPOutputStream     // Catch:{ Throwable -> 0x01c7, all -> 0x023a }
            r2.<init>(r7)     // Catch:{ Throwable -> 0x01c7, all -> 0x023a }
            r2.write(r0)     // Catch:{ Throwable -> 0x01c7, all -> 0x023a }
            r2.close()     // Catch:{ Throwable -> 0x01c7, all -> 0x023a }
            byte[] r0 = r7.toByteArray()     // Catch:{ Throwable -> 0x01c7, all -> 0x023a }
            r2 = 0
            r3 = 4
            java.nio.ByteBuffer r2 = java.nio.ByteBuffer.wrap(r0, r2, r3)     // Catch:{ Throwable -> 0x01c7, all -> 0x023a }
            r2.putInt(r8)     // Catch:{ Throwable -> 0x01c7, all -> 0x023a }
            boolean r2 = com.tencent.msdk.stat.StatConfig.isDebugEnable()     // Catch:{ Throwable -> 0x01c7, all -> 0x023a }
            if (r2 == 0) goto L_0x0166
            com.tencent.msdk.stat.common.StatLogger r2 = d     // Catch:{ Throwable -> 0x01c7, all -> 0x023a }
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x01c7, all -> 0x023a }
            r3.<init>()     // Catch:{ Throwable -> 0x01c7, all -> 0x023a }
            java.lang.String r4 = "before Gzip:"
            java.lang.StringBuilder r3 = r3.append(r4)     // Catch:{ Throwable -> 0x01c7, all -> 0x023a }
            java.lang.StringBuilder r3 = r3.append(r8)     // Catch:{ Throwable -> 0x01c7, all -> 0x023a }
            java.lang.String r4 = " bytes, after Gzip:"
            java.lang.StringBuilder r3 = r3.append(r4)     // Catch:{ Throwable -> 0x01c7, all -> 0x023a }
            int r4 = r0.length     // Catch:{ Throwable -> 0x01c7, all -> 0x023a }
            java.lang.StringBuilder r3 = r3.append(r4)     // Catch:{ Throwable -> 0x01c7, all -> 0x023a }
            java.lang.String r4 = " bytes"
            java.lang.StringBuilder r3 = r3.append(r4)     // Catch:{ Throwable -> 0x01c7, all -> 0x023a }
            java.lang.String r3 = r3.toString()     // Catch:{ Throwable -> 0x01c7, all -> 0x023a }
            r2.d(r3)     // Catch:{ Throwable -> 0x01c7, all -> 0x023a }
        L_0x0166:
            byte[] r0 = com.tencent.msdk.stat.common.e.a(r0)     // Catch:{ Throwable -> 0x01c7, all -> 0x023a }
            org.apache.http.entity.ByteArrayEntity r2 = new org.apache.http.entity.ByteArrayEntity     // Catch:{ Throwable -> 0x01c7, all -> 0x023a }
            r2.<init>(r0)     // Catch:{ Throwable -> 0x01c7, all -> 0x023a }
            r6.setEntity(r2)     // Catch:{ Throwable -> 0x01c7, all -> 0x023a }
            org.apache.http.impl.client.DefaultHttpClient r0 = r10.a     // Catch:{ Throwable -> 0x01c7, all -> 0x023a }
            org.apache.http.HttpResponse r2 = r0.execute(r6)     // Catch:{ Throwable -> 0x01c7, all -> 0x023a }
            org.apache.http.HttpEntity r0 = r2.getEntity()     // Catch:{ Throwable -> 0x01c7, all -> 0x023a }
            org.apache.http.StatusLine r3 = r2.getStatusLine()     // Catch:{ Throwable -> 0x01c7, all -> 0x023a }
            int r3 = r3.getStatusCode()     // Catch:{ Throwable -> 0x01c7, all -> 0x023a }
            long r4 = r0.getContentLength()     // Catch:{ Throwable -> 0x01c7, all -> 0x023a }
            boolean r6 = com.tencent.msdk.stat.StatConfig.isDebugEnable()     // Catch:{ Throwable -> 0x01c7, all -> 0x023a }
            if (r6 == 0) goto L_0x01b0
            com.tencent.msdk.stat.common.StatLogger r6 = d     // Catch:{ Throwable -> 0x01c7, all -> 0x023a }
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x01c7, all -> 0x023a }
            r8.<init>()     // Catch:{ Throwable -> 0x01c7, all -> 0x023a }
            java.lang.String r9 = "http recv response status code:"
            java.lang.StringBuilder r8 = r8.append(r9)     // Catch:{ Throwable -> 0x01c7, all -> 0x023a }
            java.lang.StringBuilder r8 = r8.append(r3)     // Catch:{ Throwable -> 0x01c7, all -> 0x023a }
            java.lang.String r9 = ", content length:"
            java.lang.StringBuilder r8 = r8.append(r9)     // Catch:{ Throwable -> 0x01c7, all -> 0x023a }
            java.lang.StringBuilder r8 = r8.append(r4)     // Catch:{ Throwable -> 0x01c7, all -> 0x023a }
            java.lang.String r8 = r8.toString()     // Catch:{ Throwable -> 0x01c7, all -> 0x023a }
            r6.i(r8)     // Catch:{ Throwable -> 0x01c7, all -> 0x023a }
        L_0x01b0:
            r8 = 0
            int r4 = (r4 > r8 ? 1 : (r4 == r8 ? 0 : -1))
            if (r4 > 0) goto L_0x023c
            com.tencent.msdk.stat.common.StatLogger r2 = d     // Catch:{ Throwable -> 0x01c7, all -> 0x023a }
            java.lang.String r3 = "Server response no data."
            r2.e((java.lang.Object) r3)     // Catch:{ Throwable -> 0x01c7, all -> 0x023a }
            if (r12 == 0) goto L_0x01c2
            r12.b()     // Catch:{ Throwable -> 0x01c7, all -> 0x023a }
        L_0x01c2:
            org.apache.http.util.EntityUtils.toString(r0)     // Catch:{ Throwable -> 0x01c7, all -> 0x023a }
            goto L_0x000a
        L_0x01c7:
            r0 = move-exception
        L_0x01c8:
            if (r0 == 0) goto L_0x000a
            com.tencent.msdk.stat.common.StatLogger r2 = d
            r2.error((java.lang.Throwable) r0)
            if (r12 == 0) goto L_0x01d4
            r12.b()     // Catch:{ Throwable -> 0x0329 }
        L_0x01d4:
            boolean r2 = r0 instanceof java.lang.OutOfMemoryError
            if (r2 == 0) goto L_0x0331
            java.lang.System.gc()
            r10.c = r1
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r1 = 2048(0x800, float:2.87E-42)
            r0.<init>(r1)
            r10.c = r0
        L_0x01e6:
            android.content.Context r0 = f
            com.tencent.msdk.stat.a r0 = com.tencent.msdk.stat.a.a((android.content.Context) r0)
            r0.d()
            goto L_0x000a
        L_0x01f1:
            boolean r7 = com.tencent.msdk.stat.StatConfig.isDebugEnable()     // Catch:{ Throwable -> 0x01c7, all -> 0x023a }
            if (r7 == 0) goto L_0x0213
            com.tencent.msdk.stat.common.StatLogger r7 = d     // Catch:{ Throwable -> 0x01c7, all -> 0x023a }
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x01c7, all -> 0x023a }
            r8.<init>()     // Catch:{ Throwable -> 0x01c7, all -> 0x023a }
            java.lang.String r9 = "proxy:"
            java.lang.StringBuilder r8 = r8.append(r9)     // Catch:{ Throwable -> 0x01c7, all -> 0x023a }
            java.lang.String r9 = r5.toHostString()     // Catch:{ Throwable -> 0x01c7, all -> 0x023a }
            java.lang.StringBuilder r8 = r8.append(r9)     // Catch:{ Throwable -> 0x01c7, all -> 0x023a }
            java.lang.String r8 = r8.toString()     // Catch:{ Throwable -> 0x01c7, all -> 0x023a }
            r7.d(r8)     // Catch:{ Throwable -> 0x01c7, all -> 0x023a }
        L_0x0213:
            java.lang.String r7 = "X-Content-Encoding"
            r6.addHeader(r7, r4)     // Catch:{ Throwable -> 0x01c7, all -> 0x023a }
            org.apache.http.impl.client.DefaultHttpClient r7 = r10.a     // Catch:{ Throwable -> 0x01c7, all -> 0x023a }
            org.apache.http.params.HttpParams r7 = r7.getParams()     // Catch:{ Throwable -> 0x01c7, all -> 0x023a }
            java.lang.String r8 = "http.route.default-proxy"
            r7.setParameter(r8, r5)     // Catch:{ Throwable -> 0x01c7, all -> 0x023a }
            java.lang.String r7 = "X-Online-Host"
            java.lang.String r8 = com.tencent.msdk.stat.StatConfig.k     // Catch:{ Throwable -> 0x01c7, all -> 0x023a }
            r6.addHeader(r7, r8)     // Catch:{ Throwable -> 0x01c7, all -> 0x023a }
            java.lang.String r7 = "Accept"
            java.lang.String r8 = "*/*"
            r6.addHeader(r7, r8)     // Catch:{ Throwable -> 0x01c7, all -> 0x023a }
            java.lang.String r7 = "Content-Type"
            java.lang.String r8 = "json"
            r6.addHeader(r7, r8)     // Catch:{ Throwable -> 0x01c7, all -> 0x023a }
            goto L_0x00dd
        L_0x023a:
            r0 = move-exception
            throw r0
        L_0x023c:
            java.io.InputStream r4 = r0.getContent()     // Catch:{ Throwable -> 0x01c7, all -> 0x023a }
            java.io.DataInputStream r5 = new java.io.DataInputStream     // Catch:{ Throwable -> 0x01c7, all -> 0x023a }
            r5.<init>(r4)     // Catch:{ Throwable -> 0x01c7, all -> 0x023a }
            long r8 = r0.getContentLength()     // Catch:{ Throwable -> 0x01c7, all -> 0x023a }
            int r0 = (int) r8     // Catch:{ Throwable -> 0x01c7, all -> 0x023a }
            byte[] r0 = new byte[r0]     // Catch:{ Throwable -> 0x01c7, all -> 0x023a }
            r5.readFully(r0)     // Catch:{ Throwable -> 0x01c7, all -> 0x023a }
            r4.close()     // Catch:{ Throwable -> 0x01c7, all -> 0x023a }
            r5.close()     // Catch:{ Throwable -> 0x01c7, all -> 0x023a }
            java.lang.String r5 = "Content-Encoding"
            org.apache.http.Header r2 = r2.getFirstHeader(r5)     // Catch:{ Throwable -> 0x01c7, all -> 0x023a }
            if (r2 == 0) goto L_0x0271
            java.lang.String r5 = r2.getValue()     // Catch:{ Throwable -> 0x01c7, all -> 0x023a }
            java.lang.String r6 = "gzip,rc4"
            boolean r5 = r5.equalsIgnoreCase(r6)     // Catch:{ Throwable -> 0x01c7, all -> 0x023a }
            if (r5 == 0) goto L_0x02b8
            byte[] r0 = com.tencent.msdk.stat.common.j.a((byte[]) r0)     // Catch:{ Throwable -> 0x01c7, all -> 0x023a }
            byte[] r0 = com.tencent.msdk.stat.common.e.b(r0)     // Catch:{ Throwable -> 0x01c7, all -> 0x023a }
        L_0x0271:
            java.lang.String r2 = new java.lang.String     // Catch:{ Throwable -> 0x01c7, all -> 0x023a }
            java.lang.String r5 = "UTF-8"
            r2.<init>(r0, r5)     // Catch:{ Throwable -> 0x01c7, all -> 0x023a }
            boolean r5 = com.tencent.msdk.stat.StatConfig.isDebugEnable()     // Catch:{ Throwable -> 0x01c7, all -> 0x023a }
            if (r5 == 0) goto L_0x0296
            com.tencent.msdk.stat.common.StatLogger r5 = d     // Catch:{ Throwable -> 0x01c7, all -> 0x023a }
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x01c7, all -> 0x023a }
            r6.<init>()     // Catch:{ Throwable -> 0x01c7, all -> 0x023a }
            java.lang.String r8 = "http get response data:"
            java.lang.StringBuilder r6 = r6.append(r8)     // Catch:{ Throwable -> 0x01c7, all -> 0x023a }
            java.lang.StringBuilder r6 = r6.append(r2)     // Catch:{ Throwable -> 0x01c7, all -> 0x023a }
            java.lang.String r6 = r6.toString()     // Catch:{ Throwable -> 0x01c7, all -> 0x023a }
            r5.i(r6)     // Catch:{ Throwable -> 0x01c7, all -> 0x023a }
        L_0x0296:
            org.json.JSONObject r5 = new org.json.JSONObject     // Catch:{ Throwable -> 0x01c7, all -> 0x023a }
            r5.<init>(r2)     // Catch:{ Throwable -> 0x01c7, all -> 0x023a }
            r2 = 200(0xc8, float:2.8E-43)
            if (r3 != r2) goto L_0x02fa
            r10.a((org.json.JSONObject) r5)     // Catch:{ Throwable -> 0x01c7, all -> 0x023a }
            if (r12 == 0) goto L_0x02af
            java.lang.String r0 = "ret"
            int r0 = r5.optInt(r0)     // Catch:{ Throwable -> 0x01c7, all -> 0x023a }
            if (r0 != 0) goto L_0x02ef
            r12.a()     // Catch:{ Throwable -> 0x01c7, all -> 0x023a }
        L_0x02af:
            r4.close()     // Catch:{ Throwable -> 0x01c7, all -> 0x023a }
            r7.close()     // Catch:{ Throwable -> 0x01c7, all -> 0x023a }
            r0 = r1
            goto L_0x01c8
        L_0x02b8:
            java.lang.String r5 = r2.getValue()     // Catch:{ Throwable -> 0x01c7, all -> 0x023a }
            java.lang.String r6 = "rc4,gzip"
            boolean r5 = r5.equalsIgnoreCase(r6)     // Catch:{ Throwable -> 0x01c7, all -> 0x023a }
            if (r5 == 0) goto L_0x02cd
            byte[] r0 = com.tencent.msdk.stat.common.e.b(r0)     // Catch:{ Throwable -> 0x01c7, all -> 0x023a }
            byte[] r0 = com.tencent.msdk.stat.common.j.a((byte[]) r0)     // Catch:{ Throwable -> 0x01c7, all -> 0x023a }
            goto L_0x0271
        L_0x02cd:
            java.lang.String r5 = r2.getValue()     // Catch:{ Throwable -> 0x01c7, all -> 0x023a }
            java.lang.String r6 = "gzip"
            boolean r5 = r5.equalsIgnoreCase(r6)     // Catch:{ Throwable -> 0x01c7, all -> 0x023a }
            if (r5 == 0) goto L_0x02de
            byte[] r0 = com.tencent.msdk.stat.common.j.a((byte[]) r0)     // Catch:{ Throwable -> 0x01c7, all -> 0x023a }
            goto L_0x0271
        L_0x02de:
            java.lang.String r2 = r2.getValue()     // Catch:{ Throwable -> 0x01c7, all -> 0x023a }
            java.lang.String r5 = "rc4"
            boolean r2 = r2.equalsIgnoreCase(r5)     // Catch:{ Throwable -> 0x01c7, all -> 0x023a }
            if (r2 == 0) goto L_0x0271
            byte[] r0 = com.tencent.msdk.stat.common.e.b(r0)     // Catch:{ Throwable -> 0x01c7, all -> 0x023a }
            goto L_0x0271
        L_0x02ef:
            com.tencent.msdk.stat.common.StatLogger r0 = d     // Catch:{ Throwable -> 0x01c7, all -> 0x023a }
            java.lang.String r2 = "response error data."
            r0.error((java.lang.Object) r2)     // Catch:{ Throwable -> 0x01c7, all -> 0x023a }
            r12.b()     // Catch:{ Throwable -> 0x01c7, all -> 0x023a }
            goto L_0x02af
        L_0x02fa:
            com.tencent.msdk.stat.common.StatLogger r2 = d     // Catch:{ Throwable -> 0x01c7, all -> 0x023a }
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x01c7, all -> 0x023a }
            r5.<init>()     // Catch:{ Throwable -> 0x01c7, all -> 0x023a }
            java.lang.String r6 = "Server response error code:"
            java.lang.StringBuilder r5 = r5.append(r6)     // Catch:{ Throwable -> 0x01c7, all -> 0x023a }
            java.lang.StringBuilder r3 = r5.append(r3)     // Catch:{ Throwable -> 0x01c7, all -> 0x023a }
            java.lang.String r5 = ", error:"
            java.lang.StringBuilder r3 = r3.append(r5)     // Catch:{ Throwable -> 0x01c7, all -> 0x023a }
            java.lang.String r5 = new java.lang.String     // Catch:{ Throwable -> 0x01c7, all -> 0x023a }
            java.lang.String r6 = "UTF-8"
            r5.<init>(r0, r6)     // Catch:{ Throwable -> 0x01c7, all -> 0x023a }
            java.lang.StringBuilder r0 = r3.append(r5)     // Catch:{ Throwable -> 0x01c7, all -> 0x023a }
            java.lang.String r0 = r0.toString()     // Catch:{ Throwable -> 0x01c7, all -> 0x023a }
            r2.error((java.lang.Object) r0)     // Catch:{ Throwable -> 0x01c7, all -> 0x023a }
            if (r12 == 0) goto L_0x02af
            r12.b()     // Catch:{ Throwable -> 0x01c7, all -> 0x023a }
            goto L_0x02af
        L_0x0329:
            r2 = move-exception
            com.tencent.msdk.stat.common.StatLogger r3 = d
            r3.e((java.lang.Throwable) r2)
            goto L_0x01d4
        L_0x0331:
            boolean r1 = r0 instanceof java.net.UnknownHostException
            if (r1 != 0) goto L_0x01e6
            boolean r0 = r0 instanceof java.net.SocketTimeoutException
            if (r0 == 0) goto L_0x01e6
            goto L_0x01e6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.msdk.stat.k.a(java.util.List, com.tencent.msdk.stat.j):void");
    }

    /* access modifiers changed from: package-private */
    public void b(List<?> list, j jVar) {
        if (this.b != null) {
            this.b.post(new m(this, list, jVar));
        }
    }
}
