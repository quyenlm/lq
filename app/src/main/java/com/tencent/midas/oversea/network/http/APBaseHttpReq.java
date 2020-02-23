package com.tencent.midas.oversea.network.http;

import com.tencent.imsdk.framework.request.HttpRequest;
import com.tencent.midas.oversea.api.APMidasPayAPI;
import com.tencent.midas.oversea.comm.APAppDataInterface;
import com.tencent.midas.oversea.comm.APDataReportManager;
import com.tencent.midas.oversea.comm.APLog;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.cert.CertificateNotYetValidException;
import java.util.HashMap;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.security.cert.CertificateExpiredException;
import org.apache.http.conn.util.InetAddressUtils;

public abstract class APBaseHttpReq extends Thread {
    private byte[] a;
    private boolean b = false;
    protected boolean changetToHttp = false;
    protected IAPHttpAns httpAns;
    public APBaseHttpParam httpParam = new APBaseHttpParam();
    protected HttpURLConnection httpURLConnection;
    protected HostnameVerifier mHostnameVerifier;

    public APBaseHttpReq() {
        this.httpParam.reqParam = new HashMap<>();
        this.httpParam.domain = APAppDataInterface.singleton().getSysServerIP();
    }

    private void a() {
        try {
            APLog.i("APHttp Request", "URL = " + this.httpParam.url + " HOST = " + this.httpParam.defaultDomain);
            URL url = new URL(this.httpParam.url);
            this.httpAns.onStart(this);
            try {
                this.httpURLConnection = (HttpURLConnection) url.openConnection();
                this.httpURLConnection.setConnectTimeout(this.httpParam.connectTimeout);
                this.httpURLConnection.setReadTimeout(this.httpParam.readTimeout);
                this.httpURLConnection.setRequestProperty("Host", this.httpParam.defaultDomain);
                this.httpURLConnection.setUseCaches(false);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (MalformedURLException e2) {
            e2.printStackTrace();
        }
    }

    private void a(int i, int i2, Exception exc, String str) {
        a(i, i2, exc.toString());
        APLog.i("APBaseHttpReq", getClass().getName() + " tryAgain reqTimes = " + this.httpParam.requestTimes + " tryTimes = " + this.httpParam.reTryTimes);
        try {
            if (this.httpParam.reqType.equals("https://")) {
                HttpsURLConnection.setDefaultHostnameVerifier(this.mHostnameVerifier);
            }
            if (this.httpParam.requestTimes < this.httpParam.reTryTimes) {
                this.httpParam.constructReTryUrl();
                c();
                return;
            }
            try {
                if (this.httpParam.reqType.equals("https://")) {
                    for (Throwable th = exc; th != null; th = th.getCause()) {
                        if ((th instanceof CertificateExpiredException) || (th instanceof CertificateNotYetValidException)) {
                            APLog.w("APBaseHttpReq", "您的设备系统时间不正确，请更改");
                            this.httpAns.onError(this, APErrorCode.ERROR_NETWORK_HTTPSTIMES, str);
                            return;
                        }
                    }
                }
                this.httpAns.onError(this, i, str);
                APLog.i("APBaseHttpReq", "errorType:" + i + " errorMsg:" + str);
                exc.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e2) {
        }
    }

    private void a(int i, int i2, String str) {
        if (!this.httpParam.urlName.endsWith("log_data")) {
            this.httpParam.endTime = System.currentTimeMillis();
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("name=");
            stringBuffer.append(this.httpParam.urlName);
            stringBuffer.append(HttpRequest.HTTP_REQ_ENTITY_JOIN);
            stringBuffer.append("code=");
            stringBuffer.append(i2);
            stringBuffer.append(HttpRequest.HTTP_REQ_ENTITY_JOIN);
            stringBuffer.append("times=");
            stringBuffer.append(this.httpParam.endTime - this.httpParam.begTime);
            stringBuffer.append(HttpRequest.HTTP_REQ_ENTITY_JOIN);
            stringBuffer.append("network=");
            stringBuffer.append(APAppDataInterface.singleton().getNetworkState());
            stringBuffer.append(HttpRequest.HTTP_REQ_ENTITY_JOIN);
            stringBuffer.append("errorCode=");
            stringBuffer.append(i);
            stringBuffer.append(HttpRequest.HTTP_REQ_ENTITY_JOIN);
            stringBuffer.append("errorMsg=");
            stringBuffer.append(str);
            try {
                APDataReportManager.getInstance().insertData(APDataReportManager.NETWORK_REQUEST, APMidasPayAPI.singleton().mBuyType, (String) null, (String) null, stringBuffer.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void a(InputStream inputStream, OutputStream outputStream) {
        if (inputStream != null) {
            try {
                inputStream.close();
            } catch (Exception e) {
                return;
            }
        }
        if (outputStream != null) {
            outputStream.flush();
            outputStream.close();
        }
        this.httpURLConnection.disconnect();
    }

    private void b() {
        constructParam();
        this.httpParam.constructUrl();
        this.mHostnameVerifier = HttpsURLConnection.getDefaultHostnameVerifier();
        preCreateConnection();
        a();
        d();
        setHeader();
        setBody();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:110:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:111:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:112:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:113:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:114:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:115:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:118:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:119:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:120:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x00ab, code lost:
        r0 = e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x00ac, code lost:
        r1 = r7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:?, code lost:
        r9.a = r8.toByteArray();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:?, code lost:
        com.tencent.midas.oversea.network.http.APIPList.getInstance().setIPState(r9.httpParam.domain, true);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:51:0x01a3, code lost:
        r0 = e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:56:0x01c6, code lost:
        com.tencent.midas.oversea.comm.APLog.i("APBaseHttpReq", "finally https");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:58:?, code lost:
        r0 = javax.net.ssl.SSLContext.getInstance("TLS");
        r0.init((javax.net.ssl.KeyManager[]) null, (javax.net.ssl.TrustManager[]) null, new java.security.SecureRandom());
        javax.net.ssl.HttpsURLConnection.setDefaultHostnameVerifier(r9.mHostnameVerifier);
        javax.net.ssl.HttpsURLConnection.setDefaultSSLSocketFactory(r0.getSocketFactory());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:59:0x01eb, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:60:0x01ec, code lost:
        com.tencent.midas.oversea.comm.APLog.w("APBaseHttpReq", "finally Exception");
        r0.printStackTrace();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:67:0x021c, code lost:
        com.tencent.midas.oversea.comm.APLog.i("APBaseHttpReq", "finally https");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:69:?, code lost:
        r0 = javax.net.ssl.SSLContext.getInstance("TLS");
        r0.init((javax.net.ssl.KeyManager[]) null, (javax.net.ssl.TrustManager[]) null, new java.security.SecureRandom());
        javax.net.ssl.HttpsURLConnection.setDefaultHostnameVerifier(r9.mHostnameVerifier);
        javax.net.ssl.HttpsURLConnection.setDefaultSSLSocketFactory(r0.getSocketFactory());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:70:0x0241, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:71:0x0242, code lost:
        com.tencent.midas.oversea.comm.APLog.w("APBaseHttpReq", "finally Exception");
        r0.printStackTrace();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:78:0x0275, code lost:
        com.tencent.midas.oversea.comm.APLog.i("APBaseHttpReq", "finally https");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:80:?, code lost:
        r0 = javax.net.ssl.SSLContext.getInstance("TLS");
        r0.init((javax.net.ssl.KeyManager[]) null, (javax.net.ssl.TrustManager[]) null, new java.security.SecureRandom());
        javax.net.ssl.HttpsURLConnection.setDefaultHostnameVerifier(r9.mHostnameVerifier);
        javax.net.ssl.HttpsURLConnection.setDefaultSSLSocketFactory(r0.getSocketFactory());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:81:0x029a, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:82:0x029b, code lost:
        com.tencent.midas.oversea.comm.APLog.w("APBaseHttpReq", "finally Exception");
        r0.printStackTrace();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:96:0x02ed, code lost:
        r0 = e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:97:0x02f0, code lost:
        r0 = e;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Removed duplicated region for block: B:108:? A[RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:110:? A[RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:112:? A[RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:114:? A[RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x00ab A[ExcHandler: ConnectTimeoutException (e org.apache.http.conn.ConnectTimeoutException), Splitter:B:6:0x0042] */
    /* JADX WARNING: Removed duplicated region for block: B:44:0x0138  */
    /* JADX WARNING: Removed duplicated region for block: B:51:0x01a3 A[ExcHandler: SocketTimeoutException (e java.net.SocketTimeoutException), Splitter:B:6:0x0042] */
    /* JADX WARNING: Removed duplicated region for block: B:56:0x01c6  */
    /* JADX WARNING: Removed duplicated region for block: B:67:0x021c  */
    /* JADX WARNING: Removed duplicated region for block: B:78:0x0275  */
    /* JADX WARNING: Removed duplicated region for block: B:87:0x02b8  */
    /* JADX WARNING: Removed duplicated region for block: B:97:0x02f0 A[ExcHandler: IOException (e java.io.IOException), Splitter:B:6:0x0042] */
    /* JADX WARNING: Unknown top exception splitter block from list: {B:74:0x0250=Splitter:B:74:0x0250, B:52:0x01a4=Splitter:B:52:0x01a4, B:63:0x01fa=Splitter:B:63:0x01fa} */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void c() {
        /*
            r9 = this;
            r6 = 200(0xc8, float:2.8E-43)
            r0 = 0
            r1 = 0
            java.io.ByteArrayOutputStream r8 = new java.io.ByteArrayOutputStream
            r8.<init>()
            com.tencent.midas.oversea.network.http.APBaseHttpParam r2 = r9.httpParam
            long r4 = java.lang.System.currentTimeMillis()
            r2.begTime = r4
            r9.b()
            com.tencent.midas.oversea.network.http.APBaseHttpParam r2 = r9.httpParam     // Catch:{ ConnectTimeoutException -> 0x02f7, SocketTimeoutException -> 0x02f3, IOException -> 0x01f8, Exception -> 0x024e, all -> 0x02a7 }
            java.lang.String r2 = r2.sendType     // Catch:{ ConnectTimeoutException -> 0x02f7, SocketTimeoutException -> 0x02f3, IOException -> 0x01f8, Exception -> 0x024e, all -> 0x02a7 }
            java.lang.String r3 = "POST"
            boolean r2 = r2.equals(r3)     // Catch:{ ConnectTimeoutException -> 0x02f7, SocketTimeoutException -> 0x02f3, IOException -> 0x01f8, Exception -> 0x024e, all -> 0x02a7 }
            if (r2 == 0) goto L_0x003c
            java.io.DataOutputStream r2 = new java.io.DataOutputStream     // Catch:{ ConnectTimeoutException -> 0x02f7, SocketTimeoutException -> 0x02f3, IOException -> 0x01f8, Exception -> 0x024e, all -> 0x02a7 }
            java.net.HttpURLConnection r3 = r9.httpURLConnection     // Catch:{ ConnectTimeoutException -> 0x02f7, SocketTimeoutException -> 0x02f3, IOException -> 0x01f8, Exception -> 0x024e, all -> 0x02a7 }
            java.io.OutputStream r3 = r3.getOutputStream()     // Catch:{ ConnectTimeoutException -> 0x02f7, SocketTimeoutException -> 0x02f3, IOException -> 0x01f8, Exception -> 0x024e, all -> 0x02a7 }
            r2.<init>(r3)     // Catch:{ ConnectTimeoutException -> 0x02f7, SocketTimeoutException -> 0x02f3, IOException -> 0x01f8, Exception -> 0x024e, all -> 0x02a7 }
            com.tencent.midas.oversea.network.http.APBaseHttpParam r3 = r9.httpParam     // Catch:{ ConnectTimeoutException -> 0x02f7, SocketTimeoutException -> 0x02f3, IOException -> 0x01f8, Exception -> 0x024e, all -> 0x02a7 }
            java.lang.String r3 = r3.urlParams     // Catch:{ ConnectTimeoutException -> 0x02f7, SocketTimeoutException -> 0x02f3, IOException -> 0x01f8, Exception -> 0x024e, all -> 0x02a7 }
            byte[] r3 = r3.getBytes()     // Catch:{ ConnectTimeoutException -> 0x02f7, SocketTimeoutException -> 0x02f3, IOException -> 0x01f8, Exception -> 0x024e, all -> 0x02a7 }
            r2.write(r3)     // Catch:{ ConnectTimeoutException -> 0x02f7, SocketTimeoutException -> 0x02f3, IOException -> 0x01f8, Exception -> 0x024e, all -> 0x02a7 }
            r2.flush()     // Catch:{ ConnectTimeoutException -> 0x02f7, SocketTimeoutException -> 0x02f3, IOException -> 0x01f8, Exception -> 0x024e, all -> 0x02a7 }
            r2.close()     // Catch:{ ConnectTimeoutException -> 0x02f7, SocketTimeoutException -> 0x02f3, IOException -> 0x01f8, Exception -> 0x024e, all -> 0x02a7 }
        L_0x003c:
            java.net.HttpURLConnection r2 = r9.httpURLConnection     // Catch:{ ConnectTimeoutException -> 0x02f7, SocketTimeoutException -> 0x02f3, IOException -> 0x01f8, Exception -> 0x024e, all -> 0x02a7 }
            java.io.InputStream r7 = r2.getInputStream()     // Catch:{ ConnectTimeoutException -> 0x02f7, SocketTimeoutException -> 0x02f3, IOException -> 0x01f8, Exception -> 0x024e, all -> 0x02a7 }
            java.net.HttpURLConnection r1 = r9.httpURLConnection     // Catch:{ ConnectTimeoutException -> 0x00ab, SocketTimeoutException -> 0x01a3, IOException -> 0x02f0, Exception -> 0x02ed }
            int r1 = r1.getResponseCode()     // Catch:{ ConnectTimeoutException -> 0x00ab, SocketTimeoutException -> 0x01a3, IOException -> 0x02f0, Exception -> 0x02ed }
            if (r1 != r6) goto L_0x016a
            r1 = 1024(0x400, float:1.435E-42)
            byte[] r2 = new byte[r1]     // Catch:{ ConnectTimeoutException -> 0x00ab, SocketTimeoutException -> 0x01a3, IOException -> 0x02f0, Exception -> 0x02ed }
        L_0x004e:
            int r3 = r7.read(r2)     // Catch:{ ConnectTimeoutException -> 0x00ab, SocketTimeoutException -> 0x01a3, IOException -> 0x02f0, Exception -> 0x02ed }
            if (r3 <= 0) goto L_0x00ff
            boolean r1 = r9.b     // Catch:{ ConnectTimeoutException -> 0x00ab, SocketTimeoutException -> 0x01a3, IOException -> 0x02f0, Exception -> 0x02ed }
            if (r1 == 0) goto L_0x009e
            java.lang.Thread r0 = java.lang.Thread.currentThread()     // Catch:{ ConnectTimeoutException -> 0x00ab, SocketTimeoutException -> 0x01a3, IOException -> 0x02f0, Exception -> 0x02ed }
            r0.interrupt()     // Catch:{ ConnectTimeoutException -> 0x00ab, SocketTimeoutException -> 0x01a3, IOException -> 0x02f0, Exception -> 0x02ed }
            r9.a(r7, r8)
            com.tencent.midas.oversea.network.http.APBaseHttpParam r0 = r9.httpParam
            java.lang.String r0 = r0.reqType
            java.lang.String r1 = "https://"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x0091
            java.lang.String r0 = "APBaseHttpReq"
            java.lang.String r1 = "finally https"
            com.tencent.midas.oversea.comm.APLog.i(r0, r1)
            java.lang.String r0 = "TLS"
            javax.net.ssl.SSLContext r0 = javax.net.ssl.SSLContext.getInstance(r0)     // Catch:{ Exception -> 0x0092 }
            r1 = 0
            r2 = 0
            java.security.SecureRandom r3 = new java.security.SecureRandom     // Catch:{ Exception -> 0x0092 }
            r3.<init>()     // Catch:{ Exception -> 0x0092 }
            r0.init(r1, r2, r3)     // Catch:{ Exception -> 0x0092 }
            javax.net.ssl.HostnameVerifier r1 = r9.mHostnameVerifier     // Catch:{ Exception -> 0x0092 }
            javax.net.ssl.HttpsURLConnection.setDefaultHostnameVerifier(r1)     // Catch:{ Exception -> 0x0092 }
            javax.net.ssl.SSLSocketFactory r0 = r0.getSocketFactory()     // Catch:{ Exception -> 0x0092 }
            javax.net.ssl.HttpsURLConnection.setDefaultSSLSocketFactory(r0)     // Catch:{ Exception -> 0x0092 }
        L_0x0091:
            return
        L_0x0092:
            r0 = move-exception
            java.lang.String r1 = "APBaseHttpReq"
            java.lang.String r2 = "finally Exception"
            com.tencent.midas.oversea.comm.APLog.w(r1, r2)
            r0.printStackTrace()
            goto L_0x0091
        L_0x009e:
            r1 = 0
            r8.write(r2, r1, r3)     // Catch:{ ConnectTimeoutException -> 0x00ab, SocketTimeoutException -> 0x01a3, IOException -> 0x02f0, Exception -> 0x02ed }
            int r0 = r0 + r3
            com.tencent.midas.oversea.network.http.IAPHttpAns r1 = r9.httpAns     // Catch:{ ConnectTimeoutException -> 0x00ab, SocketTimeoutException -> 0x01a3, IOException -> 0x02f0, Exception -> 0x02ed }
            long r4 = (long) r0     // Catch:{ ConnectTimeoutException -> 0x00ab, SocketTimeoutException -> 0x01a3, IOException -> 0x02f0, Exception -> 0x02ed }
            r6 = r9
            r1.onReceive(r2, r3, r4, r6)     // Catch:{ ConnectTimeoutException -> 0x00ab, SocketTimeoutException -> 0x01a3, IOException -> 0x02f0, Exception -> 0x02ed }
            goto L_0x004e
        L_0x00ab:
            r0 = move-exception
            r1 = r7
        L_0x00ad:
            com.tencent.midas.oversea.api.APMidasPayAPI.singleton()     // Catch:{ all -> 0x02ea }
            android.content.Context r2 = com.tencent.midas.oversea.api.APMidasPayAPI.applicationContext     // Catch:{ all -> 0x02ea }
            java.lang.String r3 = "unipay_network_error_2"
            java.lang.String r2 = com.tencent.midas.oversea.comm.APCommMethod.getStringId(r2, r3)     // Catch:{ all -> 0x02ea }
            r9.a(r1, r8)     // Catch:{ all -> 0x02ea }
            r3 = -7
            r4 = -1
            r9.a(r3, r4, r0, r2)     // Catch:{ all -> 0x02ea }
            r9.a(r1, r8)
            com.tencent.midas.oversea.network.http.APBaseHttpParam r0 = r9.httpParam
            java.lang.String r0 = r0.reqType
            java.lang.String r1 = "https://"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x0091
            java.lang.String r0 = "APBaseHttpReq"
            java.lang.String r1 = "finally https"
            com.tencent.midas.oversea.comm.APLog.i(r0, r1)
            java.lang.String r0 = "TLS"
            javax.net.ssl.SSLContext r0 = javax.net.ssl.SSLContext.getInstance(r0)     // Catch:{ Exception -> 0x00f3 }
            r1 = 0
            r2 = 0
            java.security.SecureRandom r3 = new java.security.SecureRandom     // Catch:{ Exception -> 0x00f3 }
            r3.<init>()     // Catch:{ Exception -> 0x00f3 }
            r0.init(r1, r2, r3)     // Catch:{ Exception -> 0x00f3 }
            javax.net.ssl.HostnameVerifier r1 = r9.mHostnameVerifier     // Catch:{ Exception -> 0x00f3 }
            javax.net.ssl.HttpsURLConnection.setDefaultHostnameVerifier(r1)     // Catch:{ Exception -> 0x00f3 }
            javax.net.ssl.SSLSocketFactory r0 = r0.getSocketFactory()     // Catch:{ Exception -> 0x00f3 }
            javax.net.ssl.HttpsURLConnection.setDefaultSSLSocketFactory(r0)     // Catch:{ Exception -> 0x00f3 }
            goto L_0x0091
        L_0x00f3:
            r0 = move-exception
            java.lang.String r1 = "APBaseHttpReq"
            java.lang.String r2 = "finally Exception"
            com.tencent.midas.oversea.comm.APLog.w(r1, r2)
            r0.printStackTrace()
            goto L_0x0091
        L_0x00ff:
            byte[] r0 = r8.toByteArray()     // Catch:{ ConnectTimeoutException -> 0x00ab, SocketTimeoutException -> 0x01a3, IOException -> 0x02f0, Exception -> 0x02ed }
            r9.a = r0     // Catch:{ ConnectTimeoutException -> 0x00ab, SocketTimeoutException -> 0x01a3, IOException -> 0x02f0, Exception -> 0x02ed }
            com.tencent.midas.oversea.network.http.APIPList r0 = com.tencent.midas.oversea.network.http.APIPList.getInstance()     // Catch:{ Exception -> 0x02fa, ConnectTimeoutException -> 0x00ab, SocketTimeoutException -> 0x01a3, IOException -> 0x02f0 }
            com.tencent.midas.oversea.network.http.APBaseHttpParam r1 = r9.httpParam     // Catch:{ Exception -> 0x02fa, ConnectTimeoutException -> 0x00ab, SocketTimeoutException -> 0x01a3, IOException -> 0x02f0 }
            java.lang.String r1 = r1.domain     // Catch:{ Exception -> 0x02fa, ConnectTimeoutException -> 0x00ab, SocketTimeoutException -> 0x01a3, IOException -> 0x02f0 }
            r2 = 1
            r0.setIPState(r1, r2)     // Catch:{ Exception -> 0x02fa, ConnectTimeoutException -> 0x00ab, SocketTimeoutException -> 0x01a3, IOException -> 0x02f0 }
        L_0x0111:
            com.tencent.midas.oversea.comm.APAppDataInterface r0 = com.tencent.midas.oversea.comm.APAppDataInterface.singleton()     // Catch:{ ConnectTimeoutException -> 0x00ab, SocketTimeoutException -> 0x01a3, IOException -> 0x02f0, Exception -> 0x02ed }
            com.tencent.midas.oversea.network.http.APBaseHttpParam r1 = r9.httpParam     // Catch:{ ConnectTimeoutException -> 0x00ab, SocketTimeoutException -> 0x01a3, IOException -> 0x02f0, Exception -> 0x02ed }
            java.lang.String r1 = r1.domain     // Catch:{ ConnectTimeoutException -> 0x00ab, SocketTimeoutException -> 0x01a3, IOException -> 0x02f0, Exception -> 0x02ed }
            r0.setSysServerIP(r1)     // Catch:{ ConnectTimeoutException -> 0x00ab, SocketTimeoutException -> 0x01a3, IOException -> 0x02f0, Exception -> 0x02ed }
            com.tencent.midas.oversea.network.http.IAPHttpAns r0 = r9.httpAns     // Catch:{ ConnectTimeoutException -> 0x00ab, SocketTimeoutException -> 0x01a3, IOException -> 0x02f0, Exception -> 0x02ed }
            r0.onFinish(r9)     // Catch:{ ConnectTimeoutException -> 0x00ab, SocketTimeoutException -> 0x01a3, IOException -> 0x02f0, Exception -> 0x02ed }
            r0 = 0
            r1 = 200(0xc8, float:2.8E-43)
            java.lang.String r2 = ""
            r9.a(r0, r1, r2)     // Catch:{ ConnectTimeoutException -> 0x00ab, SocketTimeoutException -> 0x01a3, IOException -> 0x02f0, Exception -> 0x02ed }
        L_0x0129:
            r9.a(r7, r8)
            com.tencent.midas.oversea.network.http.APBaseHttpParam r0 = r9.httpParam
            java.lang.String r0 = r0.reqType
            java.lang.String r1 = "https://"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x0091
            java.lang.String r0 = "APBaseHttpReq"
            java.lang.String r1 = "finally https"
            com.tencent.midas.oversea.comm.APLog.i(r0, r1)
            java.lang.String r0 = "TLS"
            javax.net.ssl.SSLContext r0 = javax.net.ssl.SSLContext.getInstance(r0)     // Catch:{ Exception -> 0x015d }
            r1 = 0
            r2 = 0
            java.security.SecureRandom r3 = new java.security.SecureRandom     // Catch:{ Exception -> 0x015d }
            r3.<init>()     // Catch:{ Exception -> 0x015d }
            r0.init(r1, r2, r3)     // Catch:{ Exception -> 0x015d }
            javax.net.ssl.HostnameVerifier r1 = r9.mHostnameVerifier     // Catch:{ Exception -> 0x015d }
            javax.net.ssl.HttpsURLConnection.setDefaultHostnameVerifier(r1)     // Catch:{ Exception -> 0x015d }
            javax.net.ssl.SSLSocketFactory r0 = r0.getSocketFactory()     // Catch:{ Exception -> 0x015d }
            javax.net.ssl.HttpsURLConnection.setDefaultSSLSocketFactory(r0)     // Catch:{ Exception -> 0x015d }
            goto L_0x0091
        L_0x015d:
            r0 = move-exception
            java.lang.String r1 = "APBaseHttpReq"
            java.lang.String r2 = "finally Exception"
            com.tencent.midas.oversea.comm.APLog.w(r1, r2)
            r0.printStackTrace()
            goto L_0x0091
        L_0x016a:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ ConnectTimeoutException -> 0x00ab, SocketTimeoutException -> 0x01a3, IOException -> 0x02f0, Exception -> 0x02ed }
            r0.<init>()     // Catch:{ ConnectTimeoutException -> 0x00ab, SocketTimeoutException -> 0x01a3, IOException -> 0x02f0, Exception -> 0x02ed }
            com.tencent.midas.oversea.api.APMidasPayAPI.singleton()     // Catch:{ ConnectTimeoutException -> 0x00ab, SocketTimeoutException -> 0x01a3, IOException -> 0x02f0, Exception -> 0x02ed }
            android.content.Context r1 = com.tencent.midas.oversea.api.APMidasPayAPI.applicationContext     // Catch:{ ConnectTimeoutException -> 0x00ab, SocketTimeoutException -> 0x01a3, IOException -> 0x02f0, Exception -> 0x02ed }
            java.lang.String r2 = "unipay_network_error_1"
            java.lang.String r1 = com.tencent.midas.oversea.comm.APCommMethod.getStringId(r1, r2)     // Catch:{ ConnectTimeoutException -> 0x00ab, SocketTimeoutException -> 0x01a3, IOException -> 0x02f0, Exception -> 0x02ed }
            java.lang.StringBuilder r0 = r0.append(r1)     // Catch:{ ConnectTimeoutException -> 0x00ab, SocketTimeoutException -> 0x01a3, IOException -> 0x02f0, Exception -> 0x02ed }
            java.net.HttpURLConnection r1 = r9.httpURLConnection     // Catch:{ ConnectTimeoutException -> 0x00ab, SocketTimeoutException -> 0x01a3, IOException -> 0x02f0, Exception -> 0x02ed }
            int r1 = r1.getResponseCode()     // Catch:{ ConnectTimeoutException -> 0x00ab, SocketTimeoutException -> 0x01a3, IOException -> 0x02f0, Exception -> 0x02ed }
            java.lang.StringBuilder r0 = r0.append(r1)     // Catch:{ ConnectTimeoutException -> 0x00ab, SocketTimeoutException -> 0x01a3, IOException -> 0x02f0, Exception -> 0x02ed }
            java.lang.String r1 = ")"
            java.lang.StringBuilder r0 = r0.append(r1)     // Catch:{ ConnectTimeoutException -> 0x00ab, SocketTimeoutException -> 0x01a3, IOException -> 0x02f0, Exception -> 0x02ed }
            java.lang.String r0 = r0.toString()     // Catch:{ ConnectTimeoutException -> 0x00ab, SocketTimeoutException -> 0x01a3, IOException -> 0x02f0, Exception -> 0x02ed }
            java.net.HttpURLConnection r1 = r9.httpURLConnection     // Catch:{ ConnectTimeoutException -> 0x00ab, SocketTimeoutException -> 0x01a3, IOException -> 0x02f0, Exception -> 0x02ed }
            int r1 = r1.getResponseCode()     // Catch:{ ConnectTimeoutException -> 0x00ab, SocketTimeoutException -> 0x01a3, IOException -> 0x02f0, Exception -> 0x02ed }
            java.net.HttpURLConnection r2 = r9.httpURLConnection     // Catch:{ ConnectTimeoutException -> 0x00ab, SocketTimeoutException -> 0x01a3, IOException -> 0x02f0, Exception -> 0x02ed }
            int r2 = r2.getResponseCode()     // Catch:{ ConnectTimeoutException -> 0x00ab, SocketTimeoutException -> 0x01a3, IOException -> 0x02f0, Exception -> 0x02ed }
            r3 = 0
            r9.a(r1, r2, r3, r0)     // Catch:{ ConnectTimeoutException -> 0x00ab, SocketTimeoutException -> 0x01a3, IOException -> 0x02f0, Exception -> 0x02ed }
            goto L_0x0129
        L_0x01a3:
            r0 = move-exception
        L_0x01a4:
            com.tencent.midas.oversea.api.APMidasPayAPI.singleton()     // Catch:{ all -> 0x02e8 }
            android.content.Context r1 = com.tencent.midas.oversea.api.APMidasPayAPI.applicationContext     // Catch:{ all -> 0x02e8 }
            java.lang.String r2 = "unipay_network_error_3"
            java.lang.String r1 = com.tencent.midas.oversea.comm.APCommMethod.getStringId(r1, r2)     // Catch:{ all -> 0x02e8 }
            r9.a(r7, r8)     // Catch:{ all -> 0x02e8 }
            r2 = -8
            r3 = -2
            r9.a(r2, r3, r0, r1)     // Catch:{ all -> 0x02e8 }
            r9.a(r7, r8)
            com.tencent.midas.oversea.network.http.APBaseHttpParam r0 = r9.httpParam
            java.lang.String r0 = r0.reqType
            java.lang.String r1 = "https://"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x0091
            java.lang.String r0 = "APBaseHttpReq"
            java.lang.String r1 = "finally https"
            com.tencent.midas.oversea.comm.APLog.i(r0, r1)
            java.lang.String r0 = "TLS"
            javax.net.ssl.SSLContext r0 = javax.net.ssl.SSLContext.getInstance(r0)     // Catch:{ Exception -> 0x01eb }
            r1 = 0
            r2 = 0
            java.security.SecureRandom r3 = new java.security.SecureRandom     // Catch:{ Exception -> 0x01eb }
            r3.<init>()     // Catch:{ Exception -> 0x01eb }
            r0.init(r1, r2, r3)     // Catch:{ Exception -> 0x01eb }
            javax.net.ssl.HostnameVerifier r1 = r9.mHostnameVerifier     // Catch:{ Exception -> 0x01eb }
            javax.net.ssl.HttpsURLConnection.setDefaultHostnameVerifier(r1)     // Catch:{ Exception -> 0x01eb }
            javax.net.ssl.SSLSocketFactory r0 = r0.getSocketFactory()     // Catch:{ Exception -> 0x01eb }
            javax.net.ssl.HttpsURLConnection.setDefaultSSLSocketFactory(r0)     // Catch:{ Exception -> 0x01eb }
            goto L_0x0091
        L_0x01eb:
            r0 = move-exception
            java.lang.String r1 = "APBaseHttpReq"
            java.lang.String r2 = "finally Exception"
            com.tencent.midas.oversea.comm.APLog.w(r1, r2)
            r0.printStackTrace()
            goto L_0x0091
        L_0x01f8:
            r0 = move-exception
            r7 = r1
        L_0x01fa:
            com.tencent.midas.oversea.api.APMidasPayAPI.singleton()     // Catch:{ all -> 0x02e8 }
            android.content.Context r1 = com.tencent.midas.oversea.api.APMidasPayAPI.applicationContext     // Catch:{ all -> 0x02e8 }
            java.lang.String r2 = "unipay_network_error_4"
            java.lang.String r1 = com.tencent.midas.oversea.comm.APCommMethod.getStringId(r1, r2)     // Catch:{ all -> 0x02e8 }
            int r2 = com.tencent.midas.oversea.comm.APTools.getErrorCodeFromException(r0)     // Catch:{ all -> 0x02e8 }
            r3 = -3
            r9.a(r2, r3, r0, r1)     // Catch:{ all -> 0x02e8 }
            r9.a(r7, r8)
            com.tencent.midas.oversea.network.http.APBaseHttpParam r0 = r9.httpParam
            java.lang.String r0 = r0.reqType
            java.lang.String r1 = "https://"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x0091
            java.lang.String r0 = "APBaseHttpReq"
            java.lang.String r1 = "finally https"
            com.tencent.midas.oversea.comm.APLog.i(r0, r1)
            java.lang.String r0 = "TLS"
            javax.net.ssl.SSLContext r0 = javax.net.ssl.SSLContext.getInstance(r0)     // Catch:{ Exception -> 0x0241 }
            r1 = 0
            r2 = 0
            java.security.SecureRandom r3 = new java.security.SecureRandom     // Catch:{ Exception -> 0x0241 }
            r3.<init>()     // Catch:{ Exception -> 0x0241 }
            r0.init(r1, r2, r3)     // Catch:{ Exception -> 0x0241 }
            javax.net.ssl.HostnameVerifier r1 = r9.mHostnameVerifier     // Catch:{ Exception -> 0x0241 }
            javax.net.ssl.HttpsURLConnection.setDefaultHostnameVerifier(r1)     // Catch:{ Exception -> 0x0241 }
            javax.net.ssl.SSLSocketFactory r0 = r0.getSocketFactory()     // Catch:{ Exception -> 0x0241 }
            javax.net.ssl.HttpsURLConnection.setDefaultSSLSocketFactory(r0)     // Catch:{ Exception -> 0x0241 }
            goto L_0x0091
        L_0x0241:
            r0 = move-exception
            java.lang.String r1 = "APBaseHttpReq"
            java.lang.String r2 = "finally Exception"
            com.tencent.midas.oversea.comm.APLog.w(r1, r2)
            r0.printStackTrace()
            goto L_0x0091
        L_0x024e:
            r0 = move-exception
            r7 = r1
        L_0x0250:
            r0.printStackTrace()     // Catch:{ all -> 0x02e8 }
            com.tencent.midas.oversea.api.APMidasPayAPI.singleton()     // Catch:{ all -> 0x02e8 }
            android.content.Context r1 = com.tencent.midas.oversea.api.APMidasPayAPI.applicationContext     // Catch:{ all -> 0x02e8 }
            java.lang.String r2 = "unipay_network_error_5"
            java.lang.String r1 = com.tencent.midas.oversea.comm.APCommMethod.getStringId(r1, r2)     // Catch:{ all -> 0x02e8 }
            r9.a(r7, r8)     // Catch:{ all -> 0x02e8 }
            r2 = -6
            r3 = -4
            r9.a(r2, r3, r0, r1)     // Catch:{ all -> 0x02e8 }
            r9.a(r7, r8)
            com.tencent.midas.oversea.network.http.APBaseHttpParam r0 = r9.httpParam
            java.lang.String r0 = r0.reqType
            java.lang.String r1 = "https://"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x0091
            java.lang.String r0 = "APBaseHttpReq"
            java.lang.String r1 = "finally https"
            com.tencent.midas.oversea.comm.APLog.i(r0, r1)
            java.lang.String r0 = "TLS"
            javax.net.ssl.SSLContext r0 = javax.net.ssl.SSLContext.getInstance(r0)     // Catch:{ Exception -> 0x029a }
            r1 = 0
            r2 = 0
            java.security.SecureRandom r3 = new java.security.SecureRandom     // Catch:{ Exception -> 0x029a }
            r3.<init>()     // Catch:{ Exception -> 0x029a }
            r0.init(r1, r2, r3)     // Catch:{ Exception -> 0x029a }
            javax.net.ssl.HostnameVerifier r1 = r9.mHostnameVerifier     // Catch:{ Exception -> 0x029a }
            javax.net.ssl.HttpsURLConnection.setDefaultHostnameVerifier(r1)     // Catch:{ Exception -> 0x029a }
            javax.net.ssl.SSLSocketFactory r0 = r0.getSocketFactory()     // Catch:{ Exception -> 0x029a }
            javax.net.ssl.HttpsURLConnection.setDefaultSSLSocketFactory(r0)     // Catch:{ Exception -> 0x029a }
            goto L_0x0091
        L_0x029a:
            r0 = move-exception
            java.lang.String r1 = "APBaseHttpReq"
            java.lang.String r2 = "finally Exception"
            com.tencent.midas.oversea.comm.APLog.w(r1, r2)
            r0.printStackTrace()
            goto L_0x0091
        L_0x02a7:
            r0 = move-exception
            r7 = r1
        L_0x02a9:
            r9.a(r7, r8)
            com.tencent.midas.oversea.network.http.APBaseHttpParam r1 = r9.httpParam
            java.lang.String r1 = r1.reqType
            java.lang.String r2 = "https://"
            boolean r1 = r1.equals(r2)
            if (r1 == 0) goto L_0x02db
            java.lang.String r1 = "APBaseHttpReq"
            java.lang.String r2 = "finally https"
            com.tencent.midas.oversea.comm.APLog.i(r1, r2)
            java.lang.String r1 = "TLS"
            javax.net.ssl.SSLContext r1 = javax.net.ssl.SSLContext.getInstance(r1)     // Catch:{ Exception -> 0x02dc }
            r2 = 0
            r3 = 0
            java.security.SecureRandom r4 = new java.security.SecureRandom     // Catch:{ Exception -> 0x02dc }
            r4.<init>()     // Catch:{ Exception -> 0x02dc }
            r1.init(r2, r3, r4)     // Catch:{ Exception -> 0x02dc }
            javax.net.ssl.HostnameVerifier r2 = r9.mHostnameVerifier     // Catch:{ Exception -> 0x02dc }
            javax.net.ssl.HttpsURLConnection.setDefaultHostnameVerifier(r2)     // Catch:{ Exception -> 0x02dc }
            javax.net.ssl.SSLSocketFactory r1 = r1.getSocketFactory()     // Catch:{ Exception -> 0x02dc }
            javax.net.ssl.HttpsURLConnection.setDefaultSSLSocketFactory(r1)     // Catch:{ Exception -> 0x02dc }
        L_0x02db:
            throw r0
        L_0x02dc:
            r1 = move-exception
            java.lang.String r2 = "APBaseHttpReq"
            java.lang.String r3 = "finally Exception"
            com.tencent.midas.oversea.comm.APLog.w(r2, r3)
            r1.printStackTrace()
            goto L_0x02db
        L_0x02e8:
            r0 = move-exception
            goto L_0x02a9
        L_0x02ea:
            r0 = move-exception
            r7 = r1
            goto L_0x02a9
        L_0x02ed:
            r0 = move-exception
            goto L_0x0250
        L_0x02f0:
            r0 = move-exception
            goto L_0x01fa
        L_0x02f3:
            r0 = move-exception
            r7 = r1
            goto L_0x01a4
        L_0x02f7:
            r0 = move-exception
            goto L_0x00ad
        L_0x02fa:
            r0 = move-exception
            goto L_0x0111
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.midas.oversea.network.http.APBaseHttpReq.c():void");
    }

    private void d() {
        if (this.httpURLConnection.getDoOutput()) {
            try {
                this.httpURLConnection.getOutputStream().flush();
                this.httpURLConnection.getOutputStream().close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void changeToHttp() {
        this.changetToHttp = true;
        this.httpParam.setReqWithHttp();
    }

    public void constructParam() {
    }

    public byte[] getContent() {
        return this.a;
    }

    public IAPHttpAns getHttpAns() {
        return this.httpAns;
    }

    public boolean isIPAddress(String str) {
        return str != null && InetAddressUtils.isIPv4Address(str);
    }

    /* access modifiers changed from: protected */
    public void preCreateConnection() {
    }

    public void requestAgain() {
        c();
    }

    public void run() {
        c();
        super.run();
    }

    /* access modifiers changed from: protected */
    public void setBody() {
    }

    public void setContent(byte[] bArr) {
        this.a = bArr;
    }

    /* access modifiers changed from: protected */
    public void setHeader() {
    }

    public void setHttpAns(IAPHttpAns iAPHttpAns) {
        this.httpAns = iAPHttpAns;
    }

    /* access modifiers changed from: protected */
    public void setUrl(String str, String str2, String str3, String str4) {
        this.httpParam.setUrl(str, str2, str3, str4);
    }

    public void startRequest() {
        start();
    }

    public void stopRequest() {
        APLog.i("APBaseHttpReq", "stopRequest");
        this.b = true;
        this.httpAns.onStop(this);
    }
}
