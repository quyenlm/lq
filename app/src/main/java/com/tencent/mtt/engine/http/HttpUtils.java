package com.tencent.mtt.engine.http;

import MTT.ThirdAppInfo;
import android.os.Build;
import android.util.Log;
import com.google.android.gms.appinvite.PreviewActivity;
import com.tencent.imsdk.framework.request.HttpRequest;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class HttpUtils {
    public static final String DEFAULT_ENCODE_NAME = "utf-8";
    public static final String DEFAULT_POST_ADDR = "http://p.mb.qq.com/thirdapp";
    private static final int DEFAULT_TIME_OUT = 20000;
    public static byte[] POST_DATA_KEY = null;
    private static final String TAG = "HttpUtils";
    public static final String WUP_PROXY_DOMAIN = "http://wup.imtt.qq.com:8080";

    static {
        POST_DATA_KEY = null;
        try {
            POST_DATA_KEY = "65dRa93L".getBytes("utf-8");
        } catch (UnsupportedEncodingException e) {
        }
    }

    public static boolean post(byte[] postData) {
        return post(postData, WUP_PROXY_DOMAIN);
    }

    public static boolean post(final byte[] postData, final String dstUrl) {
        new Thread(TAG) {
            public void run() {
                if (postData != null) {
                    try {
                        HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(dstUrl).openConnection();
                        httpURLConnection.setRequestMethod("POST");
                        httpURLConnection.setDoOutput(true);
                        httpURLConnection.setDoInput(true);
                        httpURLConnection.setUseCaches(false);
                        httpURLConnection.setConnectTimeout(20000);
                        if (Build.VERSION.SDK_INT > 13) {
                            httpURLConnection.setRequestProperty("Connection", PreviewActivity.ON_CLICK_LISTENER_CLOSE);
                        }
                        httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                        httpURLConnection.setRequestProperty("Content-Length", String.valueOf(postData.length));
                        try {
                            OutputStream outputStream = httpURLConnection.getOutputStream();
                            outputStream.write(postData);
                            outputStream.flush();
                            if (httpURLConnection.getResponseCode() == 200) {
                                Log.d(HttpUtils.TAG, "succ");
                            } else {
                                Log.d(HttpUtils.TAG, "fail not 200");
                            }
                        } catch (Exception e) {
                        }
                    } catch (IOException e2) {
                    }
                }
            }
        }.start();
        return false;
    }

    public static void post(final ThirdAppInfo appInfo) {
        new Thread(TAG) {
            public void run() {
                HttpUtils.post(appInfo, HttpUtils.DEFAULT_POST_ADDR);
            }
        }.start();
    }

    /* JADX WARNING: Removed duplicated region for block: B:19:0x005b  */
    /* JADX WARNING: Removed duplicated region for block: B:36:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static boolean post(MTT.ThirdAppInfo r9, java.lang.String r10) {
        /*
            r6 = 1
            r5 = 0
            byte[] r7 = POST_DATA_KEY
            if (r7 != 0) goto L_0x0010
            java.lang.String r7 = "65dRa93L"
            java.lang.String r8 = "utf-8"
            byte[] r7 = r7.getBytes(r8)     // Catch:{ UnsupportedEncodingException -> 0x00a5 }
            POST_DATA_KEY = r7     // Catch:{ UnsupportedEncodingException -> 0x00a5 }
        L_0x0010:
            byte[] r7 = POST_DATA_KEY
            if (r7 != 0) goto L_0x0015
        L_0x0014:
            return r5
        L_0x0015:
            java.net.URL r4 = new java.net.URL     // Catch:{ IOException -> 0x008d }
            r4.<init>(r10)     // Catch:{ IOException -> 0x008d }
            java.net.URLConnection r1 = r4.openConnection()     // Catch:{ IOException -> 0x008d }
            java.net.HttpURLConnection r1 = (java.net.HttpURLConnection) r1     // Catch:{ IOException -> 0x008d }
            java.lang.String r7 = "POST"
            r1.setRequestMethod(r7)     // Catch:{ IOException -> 0x008d }
            r1.setDoOutput(r6)
            r1.setDoInput(r6)
            r1.setUseCaches(r5)
            r7 = 20000(0x4e20, float:2.8026E-41)
            r1.setConnectTimeout(r7)
            int r7 = android.os.Build.VERSION.SDK_INT
            r8 = 13
            if (r7 <= r8) goto L_0x0040
            java.lang.String r7 = "Connection"
            java.lang.String r8 = "close"
            r1.setRequestProperty(r7, r8)
        L_0x0040:
            r3 = 0
            byte[] r3 = (byte[]) r3
            if (r9 == 0) goto L_0x008f
            java.lang.StringBuffer r7 = getPostData(r9)     // Catch:{ Exception -> 0x0097 }
            if (r7 == 0) goto L_0x008f
            java.lang.StringBuffer r7 = getPostData(r9)     // Catch:{ Exception -> 0x0097 }
            java.lang.String r7 = r7.toString()     // Catch:{ Exception -> 0x0097 }
            java.lang.String r8 = "utf-8"
            byte[] r3 = r7.getBytes(r8)     // Catch:{ Exception -> 0x0097 }
        L_0x0059:
            if (r3 == 0) goto L_0x0014
            byte[] r7 = POST_DATA_KEY
            byte[] r3 = com.tencent.mtt.des.DesUtils.DesEncrypt(r7, r3, r6)
            java.lang.String r7 = "Content-Type"
            java.lang.String r8 = "application/x-www-form-urlencoded"
            r1.setRequestProperty(r7, r8)
            java.lang.String r7 = "Content-Length"
            int r8 = r3.length
            java.lang.String r8 = java.lang.String.valueOf(r8)
            r1.setRequestProperty(r7, r8)
            java.io.OutputStream r2 = r1.getOutputStream()     // Catch:{ Exception -> 0x00a2 }
            r2.write(r3)     // Catch:{ Exception -> 0x00a2 }
            r2.flush()     // Catch:{ Exception -> 0x00a2 }
            int r7 = r1.getResponseCode()     // Catch:{ Exception -> 0x00a2 }
            r8 = 200(0xc8, float:2.8E-43)
            if (r7 != r8) goto L_0x0099
            java.lang.String r7 = "poby"
            java.lang.String r8 = "succ"
            android.util.Log.d(r7, r8)     // Catch:{ Exception -> 0x00a2 }
            r5 = r6
            goto L_0x0014
        L_0x008d:
            r0 = move-exception
            goto L_0x0014
        L_0x008f:
            java.lang.String r7 = "HttpUtils"
            java.lang.String r8 = "!!! appInfo or getPostData(appInfo) is null"
            android.util.Log.e(r7, r8)     // Catch:{ Exception -> 0x0097 }
            goto L_0x0059
        L_0x0097:
            r7 = move-exception
            goto L_0x0059
        L_0x0099:
            java.lang.String r6 = "poby"
            java.lang.String r7 = "fail not 200"
            android.util.Log.d(r6, r7)     // Catch:{ Exception -> 0x00a2 }
            goto L_0x0014
        L_0x00a2:
            r0 = move-exception
            goto L_0x0014
        L_0x00a5:
            r7 = move-exception
            goto L_0x0010
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.mtt.engine.http.HttpUtils.post(MTT.ThirdAppInfo, java.lang.String):boolean");
    }

    private static StringBuffer getPostData(ThirdAppInfo appInfo) {
        StringBuffer buffer = new StringBuffer();
        try {
            buffer.append("sAppName").append(HttpRequest.HTTP_REQ_ENTITY_MERGE).append(URLEncoder.encode(appInfo.sAppName, "utf-8")).append("|");
            buffer.append("sTime").append(HttpRequest.HTTP_REQ_ENTITY_MERGE).append(URLEncoder.encode(appInfo.sTime, "utf-8")).append("|");
            buffer.append("sQua").append(HttpRequest.HTTP_REQ_ENTITY_MERGE).append(URLEncoder.encode(appInfo.sQua, "utf-8")).append("|");
            buffer.append("sLc").append(HttpRequest.HTTP_REQ_ENTITY_MERGE).append(URLEncoder.encode(appInfo.sLc, "utf-8")).append("|");
            buffer.append("sGuid").append(HttpRequest.HTTP_REQ_ENTITY_MERGE).append(URLEncoder.encode(appInfo.sGuid, "utf-8")).append("|");
            buffer.append("iPv").append(HttpRequest.HTTP_REQ_ENTITY_MERGE).append(URLEncoder.encode(String.valueOf(appInfo.iPv), "utf-8"));
            return buffer;
        } catch (Exception e) {
            return null;
        }
    }
}
