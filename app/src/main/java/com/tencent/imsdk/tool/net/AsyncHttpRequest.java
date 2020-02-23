package com.tencent.imsdk.tool.net;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import com.tencent.imsdk.IMConfig;
import com.tencent.imsdk.tool.etc.IMLogger;
import com.tencent.imsdk.tool.etc.SharedPreferenceHelper;
import com.vk.sdk.api.VKApiConst;
import java.io.IOException;
import java.net.MalformedURLException;
import javax.net.ssl.SSLException;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.protocol.ExecutionContext;
import org.apache.http.protocol.HttpContext;

public class AsyncHttpRequest implements Runnable {
    private static final String USEFUL_CERTIFICATE_FILE_NAME = "useful_cert";
    private boolean cancelIsNotified = false;
    private final AbstractHttpClient client;
    private final HttpContext context;
    private int executionCount;
    private int indexOfCertFiles = 0;
    private boolean isCancelled = false;
    private boolean isFinished = false;
    private final HttpUriRequest request;
    private final ResponseHandlerInterface responseHandler;

    public AsyncHttpRequest(AbstractHttpClient client2, HttpContext context2, HttpUriRequest request2, ResponseHandlerInterface responseHandler2) {
        this.client = client2;
        this.context = context2;
        this.request = request2;
        this.responseHandler = responseHandler2;
    }

    public void run() {
        if (!isCancelled()) {
            if (this.responseHandler != null) {
                this.responseHandler.sendStartMessage();
            }
            if (!isCancelled()) {
                try {
                    makeRequestWithRetries();
                } catch (IOException e) {
                    if (isCancelled() || this.responseHandler == null) {
                        Log.e("AsyncHttpRequest", "makeRequestWithRetries returned error, but handler is null", e);
                    } else {
                        this.responseHandler.sendFailureMessage(0, (Header[]) null, (byte[]) null, e);
                    }
                }
                if (!isCancelled()) {
                    if (this.responseHandler != null) {
                        this.responseHandler.sendFinishMessage();
                    }
                    this.isFinished = true;
                }
            }
        }
    }

    private void makeRequest() throws IOException {
        if (!isCancelled()) {
            if (this.request.getURI().getScheme() == null) {
                throw new MalformedURLException("No valid URI scheme was provided");
            }
            HttpResponse response = this.client.execute(this.request, this.context);
            if (!isCancelled() && this.responseHandler != null) {
                this.responseHandler.sendResponseMessage(response);
            }
        }
    }

    private HttpRequestRetryHandler retryAfterSSLException(IOException ex, int executionCount2) {
        String[] certFiles = IMConfig.SERVER_CERTIFICATE_FILES;
        int certsNum = certFiles.length;
        int maxRetryNum = certsNum;
        if ((ex instanceof SSLException) && executionCount2 < certsNum) {
            Context appContext = IMConfig.getCurContext();
            String certFilename = null;
            if (executionCount2 == 0) {
                certFilename = SharedPreferenceHelper.getSharedPreferenceString(appContext, USEFUL_CERTIFICATE_FILE_NAME, "");
                IMLogger.d("jarrettye First retry will get the last store certificate file : " + certFilename);
                if (!TextUtils.isEmpty(certFilename)) {
                    for (int index = 0; index < certsNum; index++) {
                        if (certFiles[index].equals(certFilename)) {
                            this.indexOfCertFiles = index;
                        }
                    }
                }
            }
            while (maxRetryNum > 0 && IMConfig.getCertificateFile(certFiles[this.indexOfCertFiles % certsNum]) == null) {
                this.indexOfCertFiles++;
                maxRetryNum--;
                this.indexOfCertFiles %= certsNum;
            }
            this.context.setAttribute(ExecutionContext.HTTP_REQUEST, this.request);
            this.client.getConnectionManager().getSchemeRegistry().register(new Scheme(VKApiConst.HTTPS, MySSLSocketFactory.getFixedSocketFactory(certFiles[this.indexOfCertFiles % certsNum]), 443));
            if (!certFiles[this.indexOfCertFiles % certsNum].equals(certFilename)) {
                IMLogger.d("jarrettye " + certFiles[this.indexOfCertFiles % certsNum] + " will be store ");
                SharedPreferenceHelper.setSharedPreferenceString(appContext, USEFUL_CERTIFICATE_FILE_NAME, certFiles[this.indexOfCertFiles % certsNum]);
            }
        }
        this.indexOfCertFiles++;
        return new RetryHandler(maxRetryNum, 5);
    }

    /* JADX WARNING: Removed duplicated region for block: B:16:0x0045 A[Catch:{ Exception -> 0x00cc }] */
    /* JADX WARNING: Removed duplicated region for block: B:40:0x00ce  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void makeRequestWithRetries() throws java.io.IOException {
        /*
            r8 = this;
            r3 = 1
            r0 = 0
            org.apache.http.impl.client.AbstractHttpClient r6 = r8.client
            org.apache.http.client.HttpRequestRetryHandler r4 = r6.getHttpRequestRetryHandler()
            r1 = r0
        L_0x0009:
            if (r3 == 0) goto L_0x00a4
            r8.makeRequest()     // Catch:{ UnknownHostException -> 0x000f, NullPointerException -> 0x0050, IOException -> 0x007a }
        L_0x000e:
            return
        L_0x000f:
            r2 = move-exception
            java.io.IOException r0 = new java.io.IOException     // Catch:{ Exception -> 0x00a6 }
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x00a6 }
            r6.<init>()     // Catch:{ Exception -> 0x00a6 }
            java.lang.String r7 = "UnknownHostException exception: "
            java.lang.StringBuilder r6 = r6.append(r7)     // Catch:{ Exception -> 0x00a6 }
            java.lang.String r7 = r2.getMessage()     // Catch:{ Exception -> 0x00a6 }
            java.lang.StringBuilder r6 = r6.append(r7)     // Catch:{ Exception -> 0x00a6 }
            java.lang.String r6 = r6.toString()     // Catch:{ Exception -> 0x00a6 }
            r0.<init>(r6)     // Catch:{ Exception -> 0x00a6 }
            int r6 = r8.executionCount     // Catch:{ Exception -> 0x00cc }
            if (r6 <= 0) goto L_0x004e
            int r6 = r8.executionCount     // Catch:{ Exception -> 0x00cc }
            int r6 = r6 + 1
            r8.executionCount = r6     // Catch:{ Exception -> 0x00cc }
            org.apache.http.protocol.HttpContext r7 = r8.context     // Catch:{ Exception -> 0x00cc }
            boolean r6 = r4.retryRequest(r0, r6, r7)     // Catch:{ Exception -> 0x00cc }
            if (r6 == 0) goto L_0x004e
            r3 = 1
        L_0x003f:
            if (r3 == 0) goto L_0x00ce
            com.tencent.imsdk.tool.net.ResponseHandlerInterface r6 = r8.responseHandler     // Catch:{ Exception -> 0x00cc }
            if (r6 == 0) goto L_0x00ce
            com.tencent.imsdk.tool.net.ResponseHandlerInterface r6 = r8.responseHandler     // Catch:{ Exception -> 0x00cc }
            int r7 = r8.executionCount     // Catch:{ Exception -> 0x00cc }
            r6.sendRetryMessage(r7)     // Catch:{ Exception -> 0x00cc }
            r1 = r0
            goto L_0x0009
        L_0x004e:
            r3 = 0
            goto L_0x003f
        L_0x0050:
            r2 = move-exception
            java.io.IOException r0 = new java.io.IOException     // Catch:{ Exception -> 0x00a6 }
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x00a6 }
            r6.<init>()     // Catch:{ Exception -> 0x00a6 }
            java.lang.String r7 = "NPE in HttpClient: "
            java.lang.StringBuilder r6 = r6.append(r7)     // Catch:{ Exception -> 0x00a6 }
            java.lang.String r7 = r2.getMessage()     // Catch:{ Exception -> 0x00a6 }
            java.lang.StringBuilder r6 = r6.append(r7)     // Catch:{ Exception -> 0x00a6 }
            java.lang.String r6 = r6.toString()     // Catch:{ Exception -> 0x00a6 }
            r0.<init>(r6)     // Catch:{ Exception -> 0x00a6 }
            int r6 = r8.executionCount     // Catch:{ Exception -> 0x00cc }
            int r6 = r6 + 1
            r8.executionCount = r6     // Catch:{ Exception -> 0x00cc }
            org.apache.http.protocol.HttpContext r7 = r8.context     // Catch:{ Exception -> 0x00cc }
            boolean r3 = r4.retryRequest(r0, r6, r7)     // Catch:{ Exception -> 0x00cc }
            goto L_0x003f
        L_0x007a:
            r2 = move-exception
            boolean r6 = r8.isCancelled()     // Catch:{ Exception -> 0x00a6 }
            if (r6 != 0) goto L_0x000e
            r0 = r2
            int r6 = r8.executionCount     // Catch:{ Exception -> 0x00cc }
            org.apache.http.client.HttpRequestRetryHandler r5 = r8.retryAfterSSLException(r2, r6)     // Catch:{ Exception -> 0x00cc }
            if (r5 == 0) goto L_0x0097
            int r6 = r8.executionCount     // Catch:{ Exception -> 0x00cc }
            int r6 = r6 + 1
            r8.executionCount = r6     // Catch:{ Exception -> 0x00cc }
            org.apache.http.protocol.HttpContext r7 = r8.context     // Catch:{ Exception -> 0x00cc }
            boolean r3 = r5.retryRequest(r0, r6, r7)     // Catch:{ Exception -> 0x00cc }
            goto L_0x003f
        L_0x0097:
            int r6 = r8.executionCount     // Catch:{ Exception -> 0x00cc }
            int r6 = r6 + 1
            r8.executionCount = r6     // Catch:{ Exception -> 0x00cc }
            org.apache.http.protocol.HttpContext r7 = r8.context     // Catch:{ Exception -> 0x00cc }
            boolean r3 = r4.retryRequest(r0, r6, r7)     // Catch:{ Exception -> 0x00cc }
            goto L_0x003f
        L_0x00a4:
            r0 = r1
        L_0x00a5:
            throw r0
        L_0x00a6:
            r2 = move-exception
            r0 = r1
        L_0x00a8:
            java.lang.String r6 = "AsyncHttpRequest"
            java.lang.String r7 = "Unhandled exception origin cause"
            android.util.Log.e(r6, r7, r2)
            java.io.IOException r0 = new java.io.IOException
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>()
            java.lang.String r7 = "Unhandled exception: "
            java.lang.StringBuilder r6 = r6.append(r7)
            java.lang.String r7 = r2.getMessage()
            java.lang.StringBuilder r6 = r6.append(r7)
            java.lang.String r6 = r6.toString()
            r0.<init>(r6)
            goto L_0x00a5
        L_0x00cc:
            r2 = move-exception
            goto L_0x00a8
        L_0x00ce:
            r1 = r0
            goto L_0x0009
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.imsdk.tool.net.AsyncHttpRequest.makeRequestWithRetries():void");
    }

    public boolean isCancelled() {
        if (this.isCancelled) {
            sendCancelNotification();
        }
        return this.isCancelled;
    }

    private synchronized void sendCancelNotification() {
        if (!this.isFinished && this.isCancelled && !this.cancelIsNotified) {
            this.cancelIsNotified = true;
            if (this.responseHandler != null) {
                this.responseHandler.sendCancelMessage();
            }
        }
    }

    public boolean isDone() {
        return isCancelled() || this.isFinished;
    }

    public boolean cancel(boolean mayInterruptIfRunning) {
        this.isCancelled = true;
        this.request.abort();
        return isCancelled();
    }
}
