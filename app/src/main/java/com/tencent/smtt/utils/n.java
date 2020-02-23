package com.tencent.smtt.utils;

import android.os.Build;
import com.google.android.gms.appinvite.PreviewActivity;
import com.tencent.imsdk.tool.net.AsyncHttpClient;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;

public class n {

    public interface a {
        void a(int i);
    }

    public static String a(String str, Map<String, String> map, byte[] bArr, a aVar, boolean z) {
        HttpURLConnection a2;
        if (bArr == null || (a2 = a(str, map)) == null) {
            return null;
        }
        if (z) {
            a(a2, bArr);
        } else {
            b(a2, bArr);
        }
        return a(a2, aVar, false);
    }

    public static String a(String str, byte[] bArr, a aVar, boolean z) {
        String b;
        byte[] bArr2;
        String str2;
        if (z) {
            try {
                b = q.a().c();
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        } else {
            b = p.a().b();
        }
        String str3 = str + b;
        if (z) {
            try {
                bArr2 = q.a().a(bArr);
            } catch (Exception e2) {
                e2.printStackTrace();
                bArr2 = bArr;
            }
        } else {
            bArr2 = p.a().a(bArr);
        }
        if (bArr2 == null) {
            return null;
        }
        HashMap hashMap = new HashMap();
        hashMap.put("Content-Type", "application/x-www-form-urlencoded");
        hashMap.put("Content-Length", String.valueOf(bArr2.length));
        HttpURLConnection a2 = a(str3, (Map<String, String>) hashMap);
        if (a2 != null) {
            b(a2, bArr2);
            str2 = a(a2, aVar, z);
        } else {
            str2 = null;
        }
        return str2;
    }

    private static String a(HttpURLConnection httpURLConnection, a aVar, boolean z) {
        Throwable th;
        ByteArrayOutputStream byteArrayOutputStream;
        InputStream inputStream;
        Throwable th2;
        ByteArrayOutputStream byteArrayOutputStream2;
        String str;
        try {
            int responseCode = httpURLConnection.getResponseCode();
            if (aVar != null) {
                aVar.a(responseCode);
            }
            if (responseCode == 200) {
                InputStream inputStream2 = httpURLConnection.getInputStream();
                String contentEncoding = httpURLConnection.getContentEncoding();
                if (contentEncoding == null || !contentEncoding.equalsIgnoreCase(AsyncHttpClient.ENCODING_GZIP)) {
                    if (contentEncoding != null) {
                        if (contentEncoding.equalsIgnoreCase("deflate")) {
                            inputStream = new InflaterInputStream(inputStream2, new Inflater(true));
                        }
                    }
                    inputStream = inputStream2;
                } else {
                    inputStream = new GZIPInputStream(inputStream2);
                }
                try {
                    byteArrayOutputStream2 = new ByteArrayOutputStream();
                } catch (Throwable th3) {
                    th = th3;
                    byteArrayOutputStream = null;
                    a(inputStream);
                    a(byteArrayOutputStream);
                    throw th;
                }
                try {
                    byte[] bArr = new byte[128];
                    while (true) {
                        int read = inputStream.read(bArr);
                        if (read == -1) {
                            break;
                        }
                        byteArrayOutputStream2.write(bArr, 0, read);
                    }
                    str = z ? new String(byteArrayOutputStream2.toByteArray(), "utf-8") : new String(p.a().c(byteArrayOutputStream2.toByteArray()));
                } catch (Throwable th4) {
                    th = th4;
                    byteArrayOutputStream = byteArrayOutputStream2;
                    a(inputStream);
                    a(byteArrayOutputStream);
                    throw th;
                }
            } else {
                byteArrayOutputStream2 = null;
                inputStream = null;
                str = null;
            }
            a(inputStream);
            a(byteArrayOutputStream2);
            return str;
        } catch (Throwable th5) {
            th = th5;
            byteArrayOutputStream = null;
            inputStream = null;
            a(inputStream);
            a(byteArrayOutputStream);
            throw th;
        }
    }

    private static HttpURLConnection a(String str, Map<String, String> map) {
        HttpURLConnection httpURLConnection;
        try {
            httpURLConnection = (HttpURLConnection) new URL(str).openConnection();
            try {
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                httpURLConnection.setUseCaches(false);
                httpURLConnection.setConnectTimeout(20000);
                if (Build.VERSION.SDK_INT > 13) {
                    httpURLConnection.setRequestProperty("Connection", PreviewActivity.ON_CLICK_LISTENER_CLOSE);
                } else {
                    httpURLConnection.setRequestProperty("http.keepAlive", "false");
                }
                for (Map.Entry next : map.entrySet()) {
                    httpURLConnection.setRequestProperty((String) next.getKey(), (String) next.getValue());
                }
            } catch (Exception e) {
                e = e;
                e.printStackTrace();
                return httpURLConnection;
            }
        } catch (Exception e2) {
            e = e2;
            httpURLConnection = null;
            e.printStackTrace();
            return httpURLConnection;
        }
        return httpURLConnection;
    }

    private static void a(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Exception e) {
            }
        }
    }

    private static void a(HttpURLConnection httpURLConnection, byte[] bArr) {
        GZIPOutputStream gZIPOutputStream;
        try {
            gZIPOutputStream = new GZIPOutputStream(new BufferedOutputStream(httpURLConnection.getOutputStream(), 204800));
            try {
                gZIPOutputStream.write(bArr);
                gZIPOutputStream.flush();
                a((Closeable) null);
                a(gZIPOutputStream);
            } catch (Exception e) {
                e = e;
                try {
                    e.printStackTrace();
                    a((Closeable) null);
                    a(gZIPOutputStream);
                } catch (Throwable th) {
                    th = th;
                    a((Closeable) null);
                    a(gZIPOutputStream);
                    throw th;
                }
            }
        } catch (Exception e2) {
            e = e2;
            gZIPOutputStream = null;
            e.printStackTrace();
            a((Closeable) null);
            a(gZIPOutputStream);
        } catch (Throwable th2) {
            th = th2;
            gZIPOutputStream = null;
            a((Closeable) null);
            a(gZIPOutputStream);
            throw th;
        }
    }

    private static void b(HttpURLConnection httpURLConnection, byte[] bArr) {
        try {
            OutputStream outputStream = httpURLConnection.getOutputStream();
            outputStream.write(bArr);
            outputStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
