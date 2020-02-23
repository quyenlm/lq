package com.tencent.ieg.ntv.utils;

import android.os.AsyncTask;
import java.lang.ref.WeakReference;

public class CDNFileDownloader {
    private static final String TAG = CDNFileDownloader.class.getSimpleName();
    private DownloadTask mDownloadTask = new DownloadTask();
    /* access modifiers changed from: private */
    public WeakReference<IDownloadListener> mListener;
    /* access modifiers changed from: private */
    public int mTimeout = 5000;
    /* access modifiers changed from: private */
    public String mUrl;

    public interface IDownloadListener {
        void onComplete(String str, byte[] bArr);
    }

    public CDNFileDownloader(String url, IDownloadListener listener) {
        this.mUrl = url;
        this.mListener = new WeakReference<>(listener);
    }

    public void setTimeout(int timeout) {
        this.mTimeout = timeout;
    }

    public void start() {
        this.mDownloadTask.execute(new String[0]);
    }

    private class DownloadTask extends AsyncTask<String, Void, byte[]> {
        private DownloadTask() {
        }

        /* access modifiers changed from: protected */
        public byte[] doInBackground(String... strings) {
            return CDNFileDownloader.download(CDNFileDownloader.this.mUrl, CDNFileDownloader.this.mTimeout);
        }

        /* access modifiers changed from: protected */
        public void onPostExecute(byte[] result) {
            CDNFileDownloader.log("result len:" + (result == null ? -1 : result.length));
            if (CDNFileDownloader.this.mListener.get() != null) {
                ((IDownloadListener) CDNFileDownloader.this.mListener.get()).onComplete(CDNFileDownloader.this.mUrl, result);
            }
        }
    }

    /* JADX WARNING: type inference failed for: r5v12, types: [java.net.URLConnection] */
    /* access modifiers changed from: private */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static byte[] download(java.lang.String r7, int r8) {
        /*
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.String r6 = "download urlStr:"
            java.lang.StringBuilder r5 = r5.append(r6)
            java.lang.StringBuilder r5 = r5.append(r7)
            java.lang.String r5 = r5.toString()
            log(r5)
            r4 = 0
            java.net.URL r3 = new java.net.URL     // Catch:{ Exception -> 0x006c }
            r3.<init>(r7)     // Catch:{ Exception -> 0x006c }
            java.net.URLConnection r5 = r3.openConnection()     // Catch:{ Exception -> 0x006c }
            r0 = r5
            java.net.HttpURLConnection r0 = (java.net.HttpURLConnection) r0     // Catch:{ Exception -> 0x006c }
            r4 = r0
            r4.setConnectTimeout(r8)     // Catch:{ Exception -> 0x006c }
            r4.setReadTimeout(r8)     // Catch:{ Exception -> 0x006c }
            java.lang.String r5 = "GET"
            r4.setRequestMethod(r5)     // Catch:{ Exception -> 0x006c }
            int r2 = r4.getResponseCode()     // Catch:{ Exception -> 0x006c }
            r5 = 200(0xc8, float:2.8E-43)
            if (r2 != r5) goto L_0x004a
            java.lang.String r5 = "request success"
            log(r5)     // Catch:{ Exception -> 0x006c }
            java.io.InputStream r5 = r4.getInputStream()     // Catch:{ Exception -> 0x006c }
            byte[] r5 = com.tencent.ieg.ntv.utils.Util.getBytes(r5)     // Catch:{ Exception -> 0x006c }
            if (r4 == 0) goto L_0x0049
            r4.disconnect()
        L_0x0049:
            return r5
        L_0x004a:
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x006c }
            r5.<init>()     // Catch:{ Exception -> 0x006c }
            java.lang.String r6 = "http responseCode:"
            java.lang.StringBuilder r5 = r5.append(r6)     // Catch:{ Exception -> 0x006c }
            java.lang.StringBuilder r5 = r5.append(r2)     // Catch:{ Exception -> 0x006c }
            java.lang.String r5 = r5.toString()     // Catch:{ Exception -> 0x006c }
            log(r5)     // Catch:{ Exception -> 0x006c }
            if (r4 == 0) goto L_0x0065
            r4.disconnect()
        L_0x0065:
            java.lang.String r5 = "request failed, return null"
            log(r5)
            r5 = 0
            goto L_0x0049
        L_0x006c:
            r1 = move-exception
            java.lang.String r5 = TAG     // Catch:{ all -> 0x0092 }
            com.tencent.ieg.ntv.utils.Logger.w((java.lang.String) r5, (java.lang.Exception) r1)     // Catch:{ all -> 0x0092 }
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ all -> 0x0092 }
            r5.<init>()     // Catch:{ all -> 0x0092 }
            java.lang.String r6 = "download exception:"
            java.lang.StringBuilder r5 = r5.append(r6)     // Catch:{ all -> 0x0092 }
            java.lang.String r6 = r1.getMessage()     // Catch:{ all -> 0x0092 }
            java.lang.StringBuilder r5 = r5.append(r6)     // Catch:{ all -> 0x0092 }
            java.lang.String r5 = r5.toString()     // Catch:{ all -> 0x0092 }
            log(r5)     // Catch:{ all -> 0x0092 }
            if (r4 == 0) goto L_0x0065
            r4.disconnect()
            goto L_0x0065
        L_0x0092:
            r5 = move-exception
            if (r4 == 0) goto L_0x0098
            r4.disconnect()
        L_0x0098:
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.ieg.ntv.utils.CDNFileDownloader.download(java.lang.String, int):byte[]");
    }

    /* access modifiers changed from: private */
    public static void log(String msg) {
        Logger.d(TAG, msg);
    }
}
