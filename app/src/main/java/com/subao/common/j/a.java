package com.subao.common.j;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.amazonaws.services.s3.util.Mimetypes;
import com.subao.common.d;
import com.subao.common.e;
import com.subao.common.e.n;
import com.tencent.imsdk.tool.net.AsyncHttpClient;
import com.tencent.imsdk.tool.net.RequestParams;
import java.io.Closeable;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Locale;
import java.util.zip.GZIPOutputStream;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPut;

/* compiled from: Http */
public class a {
    private final int a;
    private final int b;

    public a(int i, int i2) {
        this.a = i;
        this.b = i2;
    }

    @NonNull
    public static URL a(String str) {
        try {
            return new URL(str);
        } catch (RuntimeException e) {
            throw new g();
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:12:0x001a A[SYNTHETIC, Splitter:B:12:0x001a] */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x002a A[Catch:{ Exception -> 0x0084 }] */
    /* JADX WARNING: Removed duplicated region for block: B:51:0x008c  */
    @android.support.annotation.NonNull
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static com.subao.common.j.a.c a(@android.support.annotation.NonNull java.net.HttpURLConnection r9) {
        /*
            r1 = 0
            r2 = 0
            int r3 = c(r9)     // Catch:{ IOException -> 0x006a, RuntimeException -> 0x0074 }
            java.io.InputStream r0 = r9.getInputStream()     // Catch:{ IOException -> 0x005c, all -> 0x0063 }
            byte[] r1 = com.subao.common.e.a((java.io.InputStream) r0)     // Catch:{ IOException -> 0x008a, all -> 0x0086 }
            com.subao.common.e.a((java.io.Closeable) r0)     // Catch:{ IOException -> 0x006a, RuntimeException -> 0x0074 }
            r0 = r1
        L_0x0012:
            if (r0 != 0) goto L_0x008c
            java.io.InputStream r1 = r9.getErrorStream()     // Catch:{ IOException -> 0x006a, RuntimeException -> 0x0074 }
            if (r1 == 0) goto L_0x008c
            byte[] r0 = com.subao.common.e.a((java.io.InputStream) r1)     // Catch:{ all -> 0x006f }
            com.subao.common.e.a((java.io.Closeable) r1)     // Catch:{ IOException -> 0x006a, RuntimeException -> 0x0074 }
            r1 = r0
        L_0x0022:
            java.lang.String r0 = "SubaoNet"
            boolean r0 = com.subao.common.d.a(r0)     // Catch:{ Exception -> 0x0084 }
            if (r0 == 0) goto L_0x0056
            java.lang.String r4 = "SubaoNet"
            java.util.Locale r5 = com.subao.common.e.n.b     // Catch:{ Exception -> 0x0084 }
            java.lang.String r6 = "[%s] response: code=%d, data size=%d"
            r0 = 3
            java.lang.Object[] r7 = new java.lang.Object[r0]     // Catch:{ Exception -> 0x0084 }
            r0 = 0
            java.net.URL r8 = r9.getURL()     // Catch:{ Exception -> 0x0084 }
            java.lang.String r8 = r8.toString()     // Catch:{ Exception -> 0x0084 }
            r7[r0] = r8     // Catch:{ Exception -> 0x0084 }
            r0 = 1
            java.lang.Integer r8 = java.lang.Integer.valueOf(r3)     // Catch:{ Exception -> 0x0084 }
            r7[r0] = r8     // Catch:{ Exception -> 0x0084 }
            r8 = 2
            if (r1 != 0) goto L_0x0082
            r0 = r2
        L_0x0049:
            java.lang.Integer r0 = java.lang.Integer.valueOf(r0)     // Catch:{ Exception -> 0x0084 }
            r7[r8] = r0     // Catch:{ Exception -> 0x0084 }
            java.lang.String r0 = java.lang.String.format(r5, r6, r7)     // Catch:{ Exception -> 0x0084 }
            android.util.Log.d(r4, r0)     // Catch:{ Exception -> 0x0084 }
        L_0x0056:
            com.subao.common.j.a$c r0 = new com.subao.common.j.a$c     // Catch:{ IOException -> 0x006a, RuntimeException -> 0x0074 }
            r0.<init>(r3, r1)     // Catch:{ IOException -> 0x006a, RuntimeException -> 0x0074 }
            return r0
        L_0x005c:
            r0 = move-exception
            r0 = r1
        L_0x005e:
            com.subao.common.e.a((java.io.Closeable) r0)     // Catch:{ IOException -> 0x006a, RuntimeException -> 0x0074 }
            r0 = r1
            goto L_0x0012
        L_0x0063:
            r0 = move-exception
            r2 = r0
            r3 = r1
        L_0x0066:
            com.subao.common.e.a((java.io.Closeable) r3)     // Catch:{ IOException -> 0x006a, RuntimeException -> 0x0074 }
            throw r2     // Catch:{ IOException -> 0x006a, RuntimeException -> 0x0074 }
        L_0x006a:
            r0 = move-exception
            a((java.net.HttpURLConnection) r9, (java.lang.Exception) r0)
            throw r0
        L_0x006f:
            r0 = move-exception
            com.subao.common.e.a((java.io.Closeable) r1)     // Catch:{ IOException -> 0x006a, RuntimeException -> 0x0074 }
            throw r0     // Catch:{ IOException -> 0x006a, RuntimeException -> 0x0074 }
        L_0x0074:
            r0 = move-exception
            a((java.net.HttpURLConnection) r9, (java.lang.Exception) r0)
            java.io.IOException r1 = new java.io.IOException
            java.lang.String r0 = r0.getMessage()
            r1.<init>(r0)
            throw r1
        L_0x0082:
            int r0 = r1.length     // Catch:{ Exception -> 0x0084 }
            goto L_0x0049
        L_0x0084:
            r0 = move-exception
            goto L_0x0056
        L_0x0086:
            r1 = move-exception
            r2 = r1
            r3 = r0
            goto L_0x0066
        L_0x008a:
            r4 = move-exception
            goto L_0x005e
        L_0x008c:
            r1 = r0
            goto L_0x0022
        */
        throw new UnsupportedOperationException("Method not decompiled: com.subao.common.j.a.a(java.net.HttpURLConnection):com.subao.common.j.a$c");
    }

    private static void a(@NonNull HttpURLConnection httpURLConnection, @NonNull Exception exc) {
        if (d.a("SubaoNet")) {
            URL url = httpURLConnection.getURL();
            if (url != null) {
                d.b("SubaoNet", url.toString());
            }
            d.b("SubaoNet", exc.getMessage());
        }
    }

    @NonNull
    public static c b(@NonNull HttpURLConnection httpURLConnection) {
        d(httpURLConnection);
        try {
            return a(httpURLConnection);
        } finally {
            httpURLConnection.disconnect();
        }
    }

    private static void d(HttpURLConnection httpURLConnection) {
        if (d.a("SubaoNet", 3)) {
            d.a("SubaoNet", String.format("Try HTTP request (%s): %s", new Object[]{httpURLConnection.getRequestMethod(), httpURLConnection.getURL().toString()}));
        }
    }

    @NonNull
    public static c a(HttpURLConnection httpURLConnection, byte[] bArr) {
        return a(httpURLConnection, bArr, false);
    }

    @NonNull
    private static c a(HttpURLConnection httpURLConnection, byte[] bArr, boolean z) {
        d(httpURLConnection);
        if (bArr != null && bArr.length > 0) {
            if (z) {
                httpURLConnection.setRequestProperty("Content-Encoding", AsyncHttpClient.ENCODING_GZIP);
            } else {
                httpURLConnection.setFixedLengthStreamingMode(bArr.length);
            }
            httpURLConnection.setDoOutput(true);
            OutputStream outputStream = null;
            try {
                outputStream = httpURLConnection.getOutputStream();
                if (z) {
                    GZIPOutputStream gZIPOutputStream = new GZIPOutputStream(outputStream);
                    gZIPOutputStream.write(bArr);
                    gZIPOutputStream.flush();
                    gZIPOutputStream.finish();
                } else {
                    outputStream.write(bArr);
                    outputStream.flush();
                }
                e.a((Closeable) outputStream);
            } catch (RuntimeException e) {
                throw new g();
            } catch (Throwable th) {
                e.a((Closeable) outputStream);
                throw th;
            }
        }
        return a(httpURLConnection);
    }

    public static void a(@NonNull HttpURLConnection httpURLConnection, @Nullable String str) {
        if (str != null) {
            httpURLConnection.setRequestProperty("Content-Type", str);
        }
    }

    public static void b(@NonNull HttpURLConnection httpURLConnection, @Nullable String str) {
        if (str != null) {
            httpURLConnection.setRequestProperty("Accept", str);
        }
    }

    public static int c(@NonNull HttpURLConnection httpURLConnection) {
        int i;
        try {
            i = httpURLConnection.getResponseCode();
        } catch (IOException e) {
            try {
                i = httpURLConnection.getResponseCode();
            } catch (RuntimeException e2) {
                i = -1;
            }
        }
        if (i >= 0) {
            return i;
        }
        throw new IOException("No valid response code.");
    }

    @NonNull
    public HttpURLConnection a(@NonNull URL url, @Nullable b bVar, @Nullable String str) {
        try {
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            if (bVar != null) {
                httpURLConnection.setRequestMethod(bVar.e);
            }
            httpURLConnection.setConnectTimeout(this.a);
            httpURLConnection.setReadTimeout(this.b);
            httpURLConnection.setDoInput(true);
            httpURLConnection.setInstanceFollowRedirects(true);
            a(httpURLConnection, str);
            httpURLConnection.setRequestProperty("Connection", "Close");
            return httpURLConnection;
        } catch (RuntimeException e) {
            throw new IOException("网络权限被禁用");
        }
    }

    @NonNull
    public c a(URL url, String str) {
        HttpURLConnection httpURLConnection = null;
        try {
            httpURLConnection = a(url, b.GET, str);
            return b(httpURLConnection);
        } finally {
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
        }
    }

    /* compiled from: Http */
    public enum b {
        GET("GET"),
        POST("POST"),
        PUT(HttpPut.METHOD_NAME),
        DELETE(HttpDelete.METHOD_NAME);
        
        public final String e;

        private b(String str) {
            this.e = str;
        }
    }

    /* renamed from: com.subao.common.j.a$a  reason: collision with other inner class name */
    /* compiled from: Http */
    public enum C0013a {
        ANY("*"),
        HTML(Mimetypes.MIMETYPE_HTML),
        JSON(RequestParams.APPLICATION_JSON),
        PROTOBUF("application/x-protobuf");
        
        public final String e;

        private C0013a(String str) {
            this.e = str;
        }
    }

    /* compiled from: Http */
    public static class c {
        public final int a;
        public final byte[] b;

        public c(int i, byte[] bArr) {
            this.a = i;
            this.b = bArr;
        }

        public String toString() {
            int i = 0;
            Locale locale = n.b;
            Object[] objArr = new Object[2];
            objArr[0] = Integer.valueOf(this.a);
            if (this.b != null) {
                i = this.b.length;
            }
            objArr[1] = Integer.valueOf(i);
            return String.format(locale, "[Response: Code=%d, Data Length=%d])", objArr);
        }
    }
}
