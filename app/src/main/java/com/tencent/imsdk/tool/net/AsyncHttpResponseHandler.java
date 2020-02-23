package com.tencent.imsdk.tool.net;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.tencent.imsdk.tool.etc.IMLogger;
import java.io.IOException;
import java.net.URI;
import java.util.Locale;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpResponseException;

public abstract class AsyncHttpResponseHandler implements ResponseHandlerInterface {
    protected static final int BUFFER_SIZE = 4096;
    protected static final int CANCEL_MESSAGE = 6;
    public static final String DEFAULT_CHARSET = "UTF-8";
    protected static final int FAILURE_MESSAGE = 1;
    protected static final int FINISH_MESSAGE = 3;
    protected static final int PROGRESS_MESSAGE = 4;
    protected static final int RETRY_MESSAGE = 5;
    protected static final int START_MESSAGE = 2;
    protected static final int SUCCESS_MESSAGE = 0;
    private Handler handler;
    private Header[] requestHeaders = null;
    private URI requestURI = null;
    private String responseCharset = "UTF-8";
    private boolean useSynchronousMode;

    public abstract void onFailure(int i, Header[] headerArr, byte[] bArr, Throwable th);

    public abstract void onSuccess(int i, Header[] headerArr, byte[] bArr);

    public URI getRequestURI() {
        return this.requestURI;
    }

    public Header[] getRequestHeaders() {
        return this.requestHeaders;
    }

    public void setRequestURI(URI requestURI2) {
        this.requestURI = requestURI2;
    }

    public void setRequestHeaders(Header[] requestHeaders2) {
        this.requestHeaders = requestHeaders2;
    }

    private static class ResponderHandler extends Handler {
        private final AsyncHttpResponseHandler mResponder;

        ResponderHandler(AsyncHttpResponseHandler mResponder2) {
            this.mResponder = mResponder2;
        }

        public void handleMessage(Message msg) {
            this.mResponder.handleMessage(msg);
        }
    }

    public boolean getUseSynchronousMode() {
        return this.useSynchronousMode;
    }

    public void setUseSynchronousMode(boolean value) {
        if (!value && Looper.myLooper() == null) {
            value = true;
            IMLogger.w("Current thread has not called Looper.prepare(). Forcing synchronous mode.");
        }
        if (!value && this.handler == null) {
            this.handler = new ResponderHandler(this);
        } else if (value && this.handler != null) {
            this.handler = null;
        }
        IMLogger.d("set synchronous mode : " + value);
        this.useSynchronousMode = value;
    }

    public void setCharset(String charset) {
        this.responseCharset = charset;
    }

    public String getCharset() {
        return this.responseCharset == null ? "UTF-8" : this.responseCharset;
    }

    public AsyncHttpResponseHandler() {
        setUseSynchronousMode(false);
    }

    public void onProgress(int bytesWritten, int totalSize) {
        Locale locale = Locale.US;
        Object[] objArr = new Object[3];
        objArr[0] = Integer.valueOf(bytesWritten);
        objArr[1] = Integer.valueOf(totalSize);
        objArr[2] = Double.valueOf(totalSize > 0 ? ((((double) bytesWritten) * 1.0d) / ((double) totalSize)) * 100.0d : -1.0d);
        IMLogger.d(String.format(locale, "Progress %d from %d (%2.0f%%)", objArr));
    }

    public void onStart() {
    }

    public void onFinish() {
    }

    public void onRetry(int retryNo) {
        IMLogger.d(String.format(Locale.US, "Request retry no. %d", new Object[]{Integer.valueOf(retryNo)}));
    }

    public void onCancel() {
        IMLogger.d("Request got cancelled");
    }

    public final void sendProgressMessage(int bytesWritten, int bytesTotal) {
        sendMessage(obtainMessage(4, new Object[]{Integer.valueOf(bytesWritten), Integer.valueOf(bytesTotal)}));
    }

    public final void sendSuccessMessage(int statusCode, Header[] headers, byte[] responseBytes) {
        sendMessage(obtainMessage(0, new Object[]{Integer.valueOf(statusCode), headers, responseBytes}));
    }

    public final void sendFailureMessage(int statusCode, Header[] headers, byte[] responseBody, Throwable throwable) {
        sendMessage(obtainMessage(1, new Object[]{Integer.valueOf(statusCode), headers, responseBody, throwable}));
    }

    public final void sendStartMessage() {
        sendMessage(obtainMessage(2, (Object) null));
    }

    public final void sendFinishMessage() {
        sendMessage(obtainMessage(3, (Object) null));
    }

    public final void sendRetryMessage(int retryNo) {
        sendMessage(obtainMessage(5, new Object[]{Integer.valueOf(retryNo)}));
    }

    public final void sendCancelMessage() {
        sendMessage(obtainMessage(6, (Object) null));
    }

    /* access modifiers changed from: protected */
    public void handleMessage(Message message) {
        switch (message.what) {
            case 0:
                Object[] response = (Object[]) message.obj;
                if (response == null || response.length < 3) {
                    IMLogger.e("SUCCESS_MESSAGE didn't got enough params");
                    return;
                } else {
                    onSuccess(((Integer) response[0]).intValue(), (Header[]) response[1], (byte[]) response[2]);
                    return;
                }
            case 1:
                Object[] response2 = (Object[]) message.obj;
                if (response2 == null || response2.length < 4) {
                    IMLogger.e("FAILURE_MESSAGE didn't got enough params");
                    return;
                } else {
                    onFailure(((Integer) response2[0]).intValue(), (Header[]) response2[1], (byte[]) response2[2], (Throwable) response2[3]);
                    return;
                }
            case 2:
                onStart();
                return;
            case 3:
                onFinish();
                return;
            case 4:
                Object[] response3 = (Object[]) message.obj;
                if (response3 == null || response3.length < 2) {
                    IMLogger.e("PROGRESS_MESSAGE didn't got enough params");
                    return;
                }
                try {
                    onProgress(((Integer) response3[0]).intValue(), ((Integer) response3[1]).intValue());
                    return;
                } catch (Throwable t) {
                    IMLogger.e("custom onProgress contains an error", t);
                    return;
                }
            case 5:
                Object[] response4 = (Object[]) message.obj;
                if (response4 == null || response4.length != 1) {
                    IMLogger.e("RETRY_MESSAGE didn't get enough params");
                    return;
                } else {
                    onRetry(((Integer) response4[0]).intValue());
                    return;
                }
            case 6:
                onCancel();
                return;
            default:
                return;
        }
    }

    /* access modifiers changed from: protected */
    public void sendMessage(Message msg) {
        if (getUseSynchronousMode() || this.handler == null) {
            handleMessage(msg);
        } else if (!Thread.currentThread().isInterrupted()) {
            this.handler.sendMessage(msg);
        }
    }

    /* access modifiers changed from: protected */
    public void postRunnable(Runnable runnable) {
        if (runnable == null) {
            return;
        }
        if (getUseSynchronousMode() || this.handler == null) {
            runnable.run();
        } else {
            this.handler.post(runnable);
        }
    }

    /* access modifiers changed from: protected */
    public Message obtainMessage(int responseMessageId, Object responseMessageData) {
        if (this.handler != null) {
            return Message.obtain(this.handler, responseMessageId, responseMessageData);
        }
        Message msg = Message.obtain();
        if (msg == null) {
            return msg;
        }
        msg.what = responseMessageId;
        msg.obj = responseMessageData;
        return msg;
    }

    public void sendResponseMessage(HttpResponse response) throws IOException {
        if (!Thread.currentThread().isInterrupted()) {
            StatusLine status = response.getStatusLine();
            byte[] responseBody = getResponseData(response.getEntity());
            if (Thread.currentThread().isInterrupted()) {
                return;
            }
            if (status.getStatusCode() >= 300) {
                sendFailureMessage(status.getStatusCode(), response.getAllHeaders(), responseBody, new HttpResponseException(status.getStatusCode(), status.getReasonPhrase()));
            } else {
                sendSuccessMessage(status.getStatusCode(), response.getAllHeaders(), responseBody);
            }
        }
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Code restructure failed: missing block: B:33:?, code lost:
        com.tencent.imsdk.tool.net.AsyncHttpClient.silentCloseInputStream(r6);
        com.tencent.imsdk.tool.net.AsyncHttpClient.endEntityViaReflection(r15);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:?, code lost:
        return r0.toByteArray();
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public byte[] getResponseData(org.apache.http.HttpEntity r15) throws java.io.IOException {
        /*
            r14 = this;
            r12 = 0
            r1 = 4096(0x1000, float:5.74E-42)
            r8 = 0
            if (r15 == 0) goto L_0x0071
            java.io.InputStream r6 = r15.getContent()
            if (r6 == 0) goto L_0x0071
            long r2 = r15.getContentLength()
            r10 = 2147483647(0x7fffffff, double:1.060997895E-314)
            int r10 = (r2 > r10 ? 1 : (r2 == r10 ? 0 : -1))
            if (r10 <= 0) goto L_0x0020
            java.lang.IllegalArgumentException r10 = new java.lang.IllegalArgumentException
            java.lang.String r11 = "HTTP entity too large to be buffered in memory"
            r10.<init>(r11)
            throw r10
        L_0x0020:
            int r10 = (r2 > r12 ? 1 : (r2 == r12 ? 0 : -1))
            if (r10 > 0) goto L_0x0063
        L_0x0024:
            org.apache.http.util.ByteArrayBuffer r0 = new org.apache.http.util.ByteArrayBuffer     // Catch:{ OutOfMemoryError -> 0x0057 }
            r0.<init>(r1)     // Catch:{ OutOfMemoryError -> 0x0057 }
            r10 = 4096(0x1000, float:5.74E-42)
            byte[] r9 = new byte[r10]     // Catch:{ all -> 0x004f }
            r4 = 0
        L_0x002e:
            int r7 = r6.read(r9)     // Catch:{ all -> 0x004f }
            r10 = -1
            if (r7 == r10) goto L_0x0067
            java.lang.Thread r10 = java.lang.Thread.currentThread()     // Catch:{ all -> 0x004f }
            boolean r10 = r10.isInterrupted()     // Catch:{ all -> 0x004f }
            if (r10 != 0) goto L_0x0067
            int r4 = r4 + r7
            r10 = 0
            r0.append((byte[]) r9, (int) r10, (int) r7)     // Catch:{ all -> 0x004f }
            int r10 = (r2 > r12 ? 1 : (r2 == r12 ? 0 : -1))
            if (r10 > 0) goto L_0x0065
            r10 = 1
        L_0x004a:
            int r10 = (int) r10     // Catch:{ all -> 0x004f }
            r14.sendProgressMessage(r4, r10)     // Catch:{ all -> 0x004f }
            goto L_0x002e
        L_0x004f:
            r10 = move-exception
            com.tencent.imsdk.tool.net.AsyncHttpClient.silentCloseInputStream(r6)     // Catch:{ OutOfMemoryError -> 0x0057 }
            com.tencent.imsdk.tool.net.AsyncHttpClient.endEntityViaReflection(r15)     // Catch:{ OutOfMemoryError -> 0x0057 }
            throw r10     // Catch:{ OutOfMemoryError -> 0x0057 }
        L_0x0057:
            r5 = move-exception
            java.lang.System.gc()
            java.io.IOException r10 = new java.io.IOException
            java.lang.String r11 = "File too large to fit into available memory"
            r10.<init>(r11)
            throw r10
        L_0x0063:
            int r1 = (int) r2
            goto L_0x0024
        L_0x0065:
            r10 = r2
            goto L_0x004a
        L_0x0067:
            com.tencent.imsdk.tool.net.AsyncHttpClient.silentCloseInputStream(r6)     // Catch:{ OutOfMemoryError -> 0x0057 }
            com.tencent.imsdk.tool.net.AsyncHttpClient.endEntityViaReflection(r15)     // Catch:{ OutOfMemoryError -> 0x0057 }
            byte[] r8 = r0.toByteArray()     // Catch:{ OutOfMemoryError -> 0x0057 }
        L_0x0071:
            return r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.imsdk.tool.net.AsyncHttpResponseHandler.getResponseData(org.apache.http.HttpEntity):byte[]");
    }
}
