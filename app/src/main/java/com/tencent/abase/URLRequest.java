package com.tencent.abase;

import android.util.Log;
import com.tencent.qqgamemi.srp.aws.upload.UploaderTask;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class URLRequest {
    private static final int ABASE_WWW_FAILED = 1;
    private static final int ABASE_WWW_IOEXCEPTION = 6;
    private static final int ABASE_WWW_NETWORKEXCEPTION = 5;
    private static final int ABASE_WWW_SECURITYEXCEPTION = 7;
    private static final int ABASE_WWW_SUCC = 0;
    private static final int ABASE_WWW_TIMEOUT = 2;
    private static final int ABASE_WWW_UNKNOWNHOST = 3;
    private static final int ABASE_WWW_UNSUPPORTEDURL = 4;
    private static final int CHUNK = 128000;
    private static final int INIT = 0;
    private static final int PAUSE = 2;
    private static final int PROCESSING = 1;
    private static final String TAG = "URLRequest";
    /* access modifiers changed from: private */
    public long delegate;
    /* access modifiers changed from: private */
    public long mDownloadDelegate;
    /* access modifiers changed from: private */
    public long mFileCurrentSize = 0;
    private String mFileMD5;
    /* access modifiers changed from: private */
    public String mFilePath;
    /* access modifiers changed from: private */
    public long mFileSize = 0;
    private Map<String, String> mHeaders = new HashMap();
    private int mPart;
    private int mPartCount;
    /* access modifiers changed from: private */
    public int mState = 0;
    private long mUploadDelegate;
    /* access modifiers changed from: private */
    public String mUrlStr;
    /* access modifiers changed from: private */
    public int timeout;

    class DownloadTask implements Runnable {
        DownloadTask() {
        }

        /* JADX WARNING: type inference failed for: r2v25, types: [java.net.URLConnection] */
        /* JADX WARNING: Multi-variable type inference failed */
        /* JADX WARNING: Removed duplicated region for block: B:24:0x00bd A[Catch:{ UnknownHostException -> 0x0225, SocketTimeoutException -> 0x0212, IOException -> 0x0200 }] */
        /* JADX WARNING: Removed duplicated region for block: B:29:0x00f7 A[SYNTHETIC, Splitter:B:29:0x00f7] */
        /* JADX WARNING: Removed duplicated region for block: B:34:0x0108 A[SYNTHETIC, Splitter:B:34:0x0108] */
        /* JADX WARNING: Removed duplicated region for block: B:48:0x0151 A[SYNTHETIC, Splitter:B:48:0x0151] */
        /* JADX WARNING: Removed duplicated region for block: B:59:0x0181 A[SYNTHETIC, Splitter:B:59:0x0181] */
        /* JADX WARNING: Removed duplicated region for block: B:70:0x01b5 A[SYNTHETIC, Splitter:B:70:0x01b5] */
        /* JADX WARNING: Removed duplicated region for block: B:78:0x01cd A[SYNTHETIC, Splitter:B:78:0x01cd] */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void run() {
            /*
                r14 = this;
                r13 = 2
                r3 = 0
                r4 = 0
                r5 = 0
                java.net.URL r6 = new java.net.URL     // Catch:{ UnknownHostException -> 0x0132, SocketTimeoutException -> 0x0162, IOException -> 0x0192, all -> 0x01c6 }
                com.tencent.abase.URLRequest r2 = com.tencent.abase.URLRequest.this     // Catch:{ UnknownHostException -> 0x0132, SocketTimeoutException -> 0x0162, IOException -> 0x0192, all -> 0x01c6 }
                java.lang.String r2 = r2.mUrlStr     // Catch:{ UnknownHostException -> 0x0132, SocketTimeoutException -> 0x0162, IOException -> 0x0192, all -> 0x01c6 }
                r6.<init>(r2)     // Catch:{ UnknownHostException -> 0x0132, SocketTimeoutException -> 0x0162, IOException -> 0x0192, all -> 0x01c6 }
                com.tencent.abase.URLRequest r2 = com.tencent.abase.URLRequest.this     // Catch:{ UnknownHostException -> 0x0132, SocketTimeoutException -> 0x0162, IOException -> 0x0192, all -> 0x01c6 }
                int r2 = r2.mState     // Catch:{ UnknownHostException -> 0x0132, SocketTimeoutException -> 0x0162, IOException -> 0x0192, all -> 0x01c6 }
                if (r2 != 0) goto L_0x022a
                java.net.URLConnection r2 = r6.openConnection()     // Catch:{ UnknownHostException -> 0x0132, SocketTimeoutException -> 0x0162, IOException -> 0x0192, all -> 0x01c6 }
                java.net.HttpURLConnection r2 = (java.net.HttpURLConnection) r2     // Catch:{ UnknownHostException -> 0x0132, SocketTimeoutException -> 0x0162, IOException -> 0x0192, all -> 0x01c6 }
                com.tencent.abase.URLRequest r7 = com.tencent.abase.URLRequest.this     // Catch:{ UnknownHostException -> 0x0215, SocketTimeoutException -> 0x0203, IOException -> 0x01f2, all -> 0x01dc }
                int r7 = r7.timeout     // Catch:{ UnknownHostException -> 0x0215, SocketTimeoutException -> 0x0203, IOException -> 0x01f2, all -> 0x01dc }
                r2.setReadTimeout(r7)     // Catch:{ UnknownHostException -> 0x0215, SocketTimeoutException -> 0x0203, IOException -> 0x01f2, all -> 0x01dc }
                com.tencent.abase.URLRequest r7 = com.tencent.abase.URLRequest.this     // Catch:{ UnknownHostException -> 0x0215, SocketTimeoutException -> 0x0203, IOException -> 0x01f2, all -> 0x01dc }
                int r7 = r7.timeout     // Catch:{ UnknownHostException -> 0x0215, SocketTimeoutException -> 0x0203, IOException -> 0x01f2, all -> 0x01dc }
                r2.setConnectTimeout(r7)     // Catch:{ UnknownHostException -> 0x0215, SocketTimeoutException -> 0x0203, IOException -> 0x01f2, all -> 0x01dc }
                com.tencent.abase.URLRequest r7 = com.tencent.abase.URLRequest.this     // Catch:{ UnknownHostException -> 0x0215, SocketTimeoutException -> 0x0203, IOException -> 0x01f2, all -> 0x01dc }
                boolean r7 = r7.initDownload(r2)     // Catch:{ UnknownHostException -> 0x0215, SocketTimeoutException -> 0x0203, IOException -> 0x01f2, all -> 0x01dc }
                if (r7 != 0) goto L_0x0048
                if (r3 == 0) goto L_0x003c
                r4.close()     // Catch:{ Exception -> 0x0043 }
            L_0x003c:
                r2.disconnect()     // Catch:{ Exception -> 0x0043 }
                r5.close()     // Catch:{ Exception -> 0x0043 }
            L_0x0042:
                return
            L_0x0043:
                r2 = move-exception
                r2.printStackTrace()
                goto L_0x0042
            L_0x0048:
                r10 = r2
            L_0x0049:
                com.tencent.abase.URLRequest r2 = com.tencent.abase.URLRequest.this     // Catch:{ UnknownHostException -> 0x021b, SocketTimeoutException -> 0x0209, IOException -> 0x01f7, all -> 0x01e1 }
                r4 = 1
                int unused = r2.mState = r4     // Catch:{ UnknownHostException -> 0x021b, SocketTimeoutException -> 0x0209, IOException -> 0x01f7, all -> 0x01e1 }
                java.net.URLConnection r2 = r6.openConnection()     // Catch:{ UnknownHostException -> 0x021b, SocketTimeoutException -> 0x0209, IOException -> 0x01f7, all -> 0x01e1 }
                r0 = r2
                java.net.HttpURLConnection r0 = (java.net.HttpURLConnection) r0     // Catch:{ UnknownHostException -> 0x021b, SocketTimeoutException -> 0x0209, IOException -> 0x01f7, all -> 0x01e1 }
                r10 = r0
                com.tencent.abase.URLRequest r2 = com.tencent.abase.URLRequest.this     // Catch:{ UnknownHostException -> 0x021b, SocketTimeoutException -> 0x0209, IOException -> 0x01f7, all -> 0x01e1 }
                int r2 = r2.timeout     // Catch:{ UnknownHostException -> 0x021b, SocketTimeoutException -> 0x0209, IOException -> 0x01f7, all -> 0x01e1 }
                r10.setConnectTimeout(r2)     // Catch:{ UnknownHostException -> 0x021b, SocketTimeoutException -> 0x0209, IOException -> 0x01f7, all -> 0x01e1 }
                java.lang.String r2 = "GET"
                r10.setRequestMethod(r2)     // Catch:{ UnknownHostException -> 0x021b, SocketTimeoutException -> 0x0209, IOException -> 0x01f7, all -> 0x01e1 }
                java.lang.String r2 = "Range"
                java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ UnknownHostException -> 0x021b, SocketTimeoutException -> 0x0209, IOException -> 0x01f7, all -> 0x01e1 }
                r4.<init>()     // Catch:{ UnknownHostException -> 0x021b, SocketTimeoutException -> 0x0209, IOException -> 0x01f7, all -> 0x01e1 }
                java.lang.String r5 = "bytes="
                java.lang.StringBuilder r4 = r4.append(r5)     // Catch:{ UnknownHostException -> 0x021b, SocketTimeoutException -> 0x0209, IOException -> 0x01f7, all -> 0x01e1 }
                com.tencent.abase.URLRequest r5 = com.tencent.abase.URLRequest.this     // Catch:{ UnknownHostException -> 0x021b, SocketTimeoutException -> 0x0209, IOException -> 0x01f7, all -> 0x01e1 }
                long r6 = r5.mFileCurrentSize     // Catch:{ UnknownHostException -> 0x021b, SocketTimeoutException -> 0x0209, IOException -> 0x01f7, all -> 0x01e1 }
                java.lang.StringBuilder r4 = r4.append(r6)     // Catch:{ UnknownHostException -> 0x021b, SocketTimeoutException -> 0x0209, IOException -> 0x01f7, all -> 0x01e1 }
                java.lang.String r5 = "-"
                java.lang.StringBuilder r4 = r4.append(r5)     // Catch:{ UnknownHostException -> 0x021b, SocketTimeoutException -> 0x0209, IOException -> 0x01f7, all -> 0x01e1 }
                com.tencent.abase.URLRequest r5 = com.tencent.abase.URLRequest.this     // Catch:{ UnknownHostException -> 0x021b, SocketTimeoutException -> 0x0209, IOException -> 0x01f7, all -> 0x01e1 }
                long r6 = r5.mFileSize     // Catch:{ UnknownHostException -> 0x021b, SocketTimeoutException -> 0x0209, IOException -> 0x01f7, all -> 0x01e1 }
                java.lang.StringBuilder r4 = r4.append(r6)     // Catch:{ UnknownHostException -> 0x021b, SocketTimeoutException -> 0x0209, IOException -> 0x01f7, all -> 0x01e1 }
                java.lang.String r4 = r4.toString()     // Catch:{ UnknownHostException -> 0x021b, SocketTimeoutException -> 0x0209, IOException -> 0x01f7, all -> 0x01e1 }
                r10.setRequestProperty(r2, r4)     // Catch:{ UnknownHostException -> 0x021b, SocketTimeoutException -> 0x0209, IOException -> 0x01f7, all -> 0x01e1 }
                com.tencent.abase.URLRequest r2 = com.tencent.abase.URLRequest.this     // Catch:{ UnknownHostException -> 0x021b, SocketTimeoutException -> 0x0209, IOException -> 0x01f7, all -> 0x01e1 }
                r2.addHeadersToConn(r10)     // Catch:{ UnknownHostException -> 0x021b, SocketTimeoutException -> 0x0209, IOException -> 0x01f7, all -> 0x01e1 }
                java.io.RandomAccessFile r11 = new java.io.RandomAccessFile     // Catch:{ UnknownHostException -> 0x021b, SocketTimeoutException -> 0x0209, IOException -> 0x01f7, all -> 0x01e1 }
                com.tencent.abase.URLRequest r2 = com.tencent.abase.URLRequest.this     // Catch:{ UnknownHostException -> 0x021b, SocketTimeoutException -> 0x0209, IOException -> 0x01f7, all -> 0x01e1 }
                java.lang.String r2 = r2.mFilePath     // Catch:{ UnknownHostException -> 0x021b, SocketTimeoutException -> 0x0209, IOException -> 0x01f7, all -> 0x01e1 }
                java.lang.String r4 = "rwd"
                r11.<init>(r2, r4)     // Catch:{ UnknownHostException -> 0x021b, SocketTimeoutException -> 0x0209, IOException -> 0x01f7, all -> 0x01e1 }
                com.tencent.abase.URLRequest r2 = com.tencent.abase.URLRequest.this     // Catch:{ UnknownHostException -> 0x0220, SocketTimeoutException -> 0x020e, IOException -> 0x01fc, all -> 0x01e6 }
                long r4 = r2.mFileCurrentSize     // Catch:{ UnknownHostException -> 0x0220, SocketTimeoutException -> 0x020e, IOException -> 0x01fc, all -> 0x01e6 }
                r11.seek(r4)     // Catch:{ UnknownHostException -> 0x0220, SocketTimeoutException -> 0x020e, IOException -> 0x01fc, all -> 0x01e6 }
                java.io.InputStream r12 = r10.getInputStream()     // Catch:{ UnknownHostException -> 0x0220, SocketTimeoutException -> 0x020e, IOException -> 0x01fc, all -> 0x01e6 }
                r2 = 6144(0x1800, float:8.61E-42)
                byte[] r2 = new byte[r2]     // Catch:{ UnknownHostException -> 0x0225, SocketTimeoutException -> 0x0212, IOException -> 0x0200 }
            L_0x00b6:
                int r3 = r12.read(r2)     // Catch:{ UnknownHostException -> 0x0225, SocketTimeoutException -> 0x0212, IOException -> 0x0200 }
                r4 = -1
                if (r3 == r4) goto L_0x0108
                r4 = 0
                r11.write(r2, r4, r3)     // Catch:{ UnknownHostException -> 0x0225, SocketTimeoutException -> 0x0212, IOException -> 0x0200 }
                com.tencent.abase.URLRequest r4 = com.tencent.abase.URLRequest.this     // Catch:{ UnknownHostException -> 0x0225, SocketTimeoutException -> 0x0212, IOException -> 0x0200 }
                com.tencent.abase.URLRequest r5 = com.tencent.abase.URLRequest.this     // Catch:{ UnknownHostException -> 0x0225, SocketTimeoutException -> 0x0212, IOException -> 0x0200 }
                long r6 = r5.mFileCurrentSize     // Catch:{ UnknownHostException -> 0x0225, SocketTimeoutException -> 0x0212, IOException -> 0x0200 }
                long r8 = (long) r3     // Catch:{ UnknownHostException -> 0x0225, SocketTimeoutException -> 0x0212, IOException -> 0x0200 }
                long r6 = r6 + r8
                long unused = r4.mFileCurrentSize = r6     // Catch:{ UnknownHostException -> 0x0225, SocketTimeoutException -> 0x0212, IOException -> 0x0200 }
                com.tencent.abase.URLRequest r3 = com.tencent.abase.URLRequest.this     // Catch:{ UnknownHostException -> 0x0225, SocketTimeoutException -> 0x0212, IOException -> 0x0200 }
                com.tencent.abase.URLRequest r4 = com.tencent.abase.URLRequest.this     // Catch:{ UnknownHostException -> 0x0225, SocketTimeoutException -> 0x0212, IOException -> 0x0200 }
                long r4 = r4.mDownloadDelegate     // Catch:{ UnknownHostException -> 0x0225, SocketTimeoutException -> 0x0212, IOException -> 0x0200 }
                com.tencent.abase.URLRequest r6 = com.tencent.abase.URLRequest.this     // Catch:{ UnknownHostException -> 0x0225, SocketTimeoutException -> 0x0212, IOException -> 0x0200 }
                long r6 = r6.mFileCurrentSize     // Catch:{ UnknownHostException -> 0x0225, SocketTimeoutException -> 0x0212, IOException -> 0x0200 }
                com.tencent.abase.URLRequest r8 = com.tencent.abase.URLRequest.this     // Catch:{ UnknownHostException -> 0x0225, SocketTimeoutException -> 0x0212, IOException -> 0x0200 }
                long r8 = r8.mFileSize     // Catch:{ UnknownHostException -> 0x0225, SocketTimeoutException -> 0x0212, IOException -> 0x0200 }
                r3.JNITaskProgress(r4, r6, r8)     // Catch:{ UnknownHostException -> 0x0225, SocketTimeoutException -> 0x0212, IOException -> 0x0200 }
                com.tencent.abase.URLRequest r3 = com.tencent.abase.URLRequest.this     // Catch:{ UnknownHostException -> 0x0225, SocketTimeoutException -> 0x0212, IOException -> 0x0200 }
                int r3 = r3.mState     // Catch:{ UnknownHostException -> 0x0225, SocketTimeoutException -> 0x0212, IOException -> 0x0200 }
                if (r3 == r13) goto L_0x00f5
                com.tencent.abase.URLRequest r3 = com.tencent.abase.URLRequest.this     // Catch:{ UnknownHostException -> 0x0225, SocketTimeoutException -> 0x0212, IOException -> 0x0200 }
                int r3 = r3.mState     // Catch:{ UnknownHostException -> 0x0225, SocketTimeoutException -> 0x0212, IOException -> 0x0200 }
                if (r3 != 0) goto L_0x00b6
            L_0x00f5:
                if (r12 == 0) goto L_0x00fa
                r12.close()     // Catch:{ Exception -> 0x0102 }
            L_0x00fa:
                r10.disconnect()     // Catch:{ Exception -> 0x0102 }
                r11.close()     // Catch:{ Exception -> 0x0102 }
                goto L_0x0042
            L_0x0102:
                r2 = move-exception
                r2.printStackTrace()
                goto L_0x0042
            L_0x0108:
                com.tencent.abase.URLRequest r2 = com.tencent.abase.URLRequest.this     // Catch:{ UnknownHostException -> 0x0225, SocketTimeoutException -> 0x0212, IOException -> 0x0200 }
                com.tencent.abase.URLRequest r3 = com.tencent.abase.URLRequest.this     // Catch:{ UnknownHostException -> 0x0225, SocketTimeoutException -> 0x0212, IOException -> 0x0200 }
                long r3 = r3.mDownloadDelegate     // Catch:{ UnknownHostException -> 0x0225, SocketTimeoutException -> 0x0212, IOException -> 0x0200 }
                r5 = 0
                com.tencent.abase.URLRequest r6 = com.tencent.abase.URLRequest.this     // Catch:{ UnknownHostException -> 0x0225, SocketTimeoutException -> 0x0212, IOException -> 0x0200 }
                long r6 = r6.mFileSize     // Catch:{ UnknownHostException -> 0x0225, SocketTimeoutException -> 0x0212, IOException -> 0x0200 }
                r2.JNITaskFinished(r3, r5, r6)     // Catch:{ UnknownHostException -> 0x0225, SocketTimeoutException -> 0x0212, IOException -> 0x0200 }
                com.tencent.abase.URLRequest r2 = com.tencent.abase.URLRequest.this     // Catch:{ UnknownHostException -> 0x0225, SocketTimeoutException -> 0x0212, IOException -> 0x0200 }
                r2.uninit()     // Catch:{ UnknownHostException -> 0x0225, SocketTimeoutException -> 0x0212, IOException -> 0x0200 }
                if (r12 == 0) goto L_0x0124
                r12.close()     // Catch:{ Exception -> 0x012c }
            L_0x0124:
                r10.disconnect()     // Catch:{ Exception -> 0x012c }
                r11.close()     // Catch:{ Exception -> 0x012c }
                goto L_0x0042
            L_0x012c:
                r2 = move-exception
                r2.printStackTrace()
                goto L_0x0042
            L_0x0132:
                r2 = move-exception
                r8 = r3
                r9 = r3
                r10 = r3
            L_0x0136:
                java.lang.String r2 = "URLRequest"
                java.lang.String r3 = "UnknownHost"
                android.util.Log.e(r2, r3)     // Catch:{ all -> 0x01ed }
                com.tencent.abase.URLRequest r2 = com.tencent.abase.URLRequest.this     // Catch:{ all -> 0x01ed }
                com.tencent.abase.URLRequest r3 = com.tencent.abase.URLRequest.this     // Catch:{ all -> 0x01ed }
                long r3 = r3.mDownloadDelegate     // Catch:{ all -> 0x01ed }
                r5 = 3
                com.tencent.abase.URLRequest r6 = com.tencent.abase.URLRequest.this     // Catch:{ all -> 0x01ed }
                long r6 = r6.mFileSize     // Catch:{ all -> 0x01ed }
                r2.JNITaskFinished(r3, r5, r6)     // Catch:{ all -> 0x01ed }
                if (r9 == 0) goto L_0x0154
                r9.close()     // Catch:{ Exception -> 0x015c }
            L_0x0154:
                r10.disconnect()     // Catch:{ Exception -> 0x015c }
                r8.close()     // Catch:{ Exception -> 0x015c }
                goto L_0x0042
            L_0x015c:
                r2 = move-exception
                r2.printStackTrace()
                goto L_0x0042
            L_0x0162:
                r2 = move-exception
                r11 = r3
                r12 = r3
                r10 = r3
            L_0x0166:
                java.lang.String r2 = "URLRequest"
                java.lang.String r3 = "SocketTimeoutException"
                android.util.Log.e(r2, r3)     // Catch:{ all -> 0x01ea }
                com.tencent.abase.URLRequest r2 = com.tencent.abase.URLRequest.this     // Catch:{ all -> 0x01ea }
                com.tencent.abase.URLRequest r3 = com.tencent.abase.URLRequest.this     // Catch:{ all -> 0x01ea }
                long r3 = r3.delegate     // Catch:{ all -> 0x01ea }
                r5 = 2
                com.tencent.abase.URLRequest r6 = com.tencent.abase.URLRequest.this     // Catch:{ all -> 0x01ea }
                long r6 = r6.mFileSize     // Catch:{ all -> 0x01ea }
                r2.JNITaskFinished(r3, r5, r6)     // Catch:{ all -> 0x01ea }
                if (r12 == 0) goto L_0x0184
                r12.close()     // Catch:{ Exception -> 0x018c }
            L_0x0184:
                r10.disconnect()     // Catch:{ Exception -> 0x018c }
                r11.close()     // Catch:{ Exception -> 0x018c }
                goto L_0x0042
            L_0x018c:
                r2 = move-exception
                r2.printStackTrace()
                goto L_0x0042
            L_0x0192:
                r2 = move-exception
                r4 = r2
                r11 = r3
                r12 = r3
                r10 = r3
            L_0x0197:
                java.lang.String r2 = "URLRequest"
                java.lang.String r3 = "IOException"
                android.util.Log.e(r2, r3)     // Catch:{ all -> 0x01ea }
                r4.printStackTrace()     // Catch:{ all -> 0x01ea }
                com.tencent.abase.URLRequest r2 = com.tencent.abase.URLRequest.this     // Catch:{ all -> 0x01ea }
                com.tencent.abase.URLRequest r3 = com.tencent.abase.URLRequest.this     // Catch:{ all -> 0x01ea }
                long r3 = r3.mDownloadDelegate     // Catch:{ all -> 0x01ea }
                r5 = 6
                com.tencent.abase.URLRequest r6 = com.tencent.abase.URLRequest.this     // Catch:{ all -> 0x01ea }
                long r6 = r6.mFileSize     // Catch:{ all -> 0x01ea }
                r2.JNITaskFinished(r3, r5, r6)     // Catch:{ all -> 0x01ea }
                if (r12 == 0) goto L_0x01b8
                r12.close()     // Catch:{ Exception -> 0x01c0 }
            L_0x01b8:
                r10.disconnect()     // Catch:{ Exception -> 0x01c0 }
                r11.close()     // Catch:{ Exception -> 0x01c0 }
                goto L_0x0042
            L_0x01c0:
                r2 = move-exception
                r2.printStackTrace()
                goto L_0x0042
            L_0x01c6:
                r2 = move-exception
                r4 = r2
                r11 = r3
                r12 = r3
                r10 = r3
            L_0x01cb:
                if (r12 == 0) goto L_0x01d0
                r12.close()     // Catch:{ Exception -> 0x01d7 }
            L_0x01d0:
                r10.disconnect()     // Catch:{ Exception -> 0x01d7 }
                r11.close()     // Catch:{ Exception -> 0x01d7 }
            L_0x01d6:
                throw r4
            L_0x01d7:
                r2 = move-exception
                r2.printStackTrace()
                goto L_0x01d6
            L_0x01dc:
                r4 = move-exception
                r11 = r3
                r12 = r3
                r10 = r2
                goto L_0x01cb
            L_0x01e1:
                r2 = move-exception
                r4 = r2
                r11 = r3
                r12 = r3
                goto L_0x01cb
            L_0x01e6:
                r2 = move-exception
                r4 = r2
                r12 = r3
                goto L_0x01cb
            L_0x01ea:
                r2 = move-exception
                r4 = r2
                goto L_0x01cb
            L_0x01ed:
                r2 = move-exception
                r4 = r2
                r11 = r8
                r12 = r9
                goto L_0x01cb
            L_0x01f2:
                r4 = move-exception
                r11 = r3
                r12 = r3
                r10 = r2
                goto L_0x0197
            L_0x01f7:
                r2 = move-exception
                r4 = r2
                r11 = r3
                r12 = r3
                goto L_0x0197
            L_0x01fc:
                r2 = move-exception
                r4 = r2
                r12 = r3
                goto L_0x0197
            L_0x0200:
                r2 = move-exception
                r4 = r2
                goto L_0x0197
            L_0x0203:
                r4 = move-exception
                r11 = r3
                r12 = r3
                r10 = r2
                goto L_0x0166
            L_0x0209:
                r2 = move-exception
                r11 = r3
                r12 = r3
                goto L_0x0166
            L_0x020e:
                r2 = move-exception
                r12 = r3
                goto L_0x0166
            L_0x0212:
                r2 = move-exception
                goto L_0x0166
            L_0x0215:
                r4 = move-exception
                r8 = r3
                r9 = r3
                r10 = r2
                goto L_0x0136
            L_0x021b:
                r2 = move-exception
                r8 = r3
                r9 = r3
                goto L_0x0136
            L_0x0220:
                r2 = move-exception
                r8 = r11
                r9 = r3
                goto L_0x0136
            L_0x0225:
                r2 = move-exception
                r8 = r11
                r9 = r12
                goto L_0x0136
            L_0x022a:
                r10 = r3
                goto L_0x0049
            */
            throw new UnsupportedOperationException("Method not decompiled: com.tencent.abase.URLRequest.DownloadTask.run():void");
        }
    }

    class RequestTask implements Runnable {
        byte[] mBody;

        RequestTask() {
            this.mBody = null;
        }

        RequestTask(byte[] bArr) {
            this.mBody = bArr;
        }

        public void run() {
            URLResponse uRLResponse = new URLResponse();
            try {
                HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(URLRequest.this.mUrlStr).openConnection();
                httpURLConnection.setReadTimeout(URLRequest.this.timeout);
                httpURLConnection.setConnectTimeout(URLRequest.this.timeout);
                URLRequest.this.addHeadersToConn(httpURLConnection);
                if (this.mBody != null) {
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(httpURLConnection.getOutputStream());
                    bufferedOutputStream.write(this.mBody);
                    bufferedOutputStream.flush();
                    bufferedOutputStream.close();
                } else {
                    httpURLConnection.setRequestMethod("GET");
                }
                httpURLConnection.connect();
                Map headerFields = httpURLConnection.getHeaderFields();
                if (headerFields == null || headerFields.entrySet() == null) {
                    Log.e(URLRequest.TAG, "headerFields == null || headerFields.entrySet() == null");
                    return;
                }
                for (Map.Entry entry : headerFields.entrySet()) {
                    String str = (String) entry.getKey();
                    List<String> list = (List) entry.getValue();
                    StringBuilder sb = new StringBuilder();
                    if (list != null) {
                        for (String append : list) {
                            sb.append(append);
                        }
                    }
                    if (str == null) {
                        uRLResponse.version = sb.toString().split("\\ ")[0];
                    } else {
                        uRLResponse.headers.put(str, sb.toString());
                    }
                }
                try {
                    uRLResponse.status = httpURLConnection.getResponseCode();
                } catch (IOException e) {
                    e.printStackTrace();
                    uRLResponse.status = 0;
                }
                try {
                    uRLResponse.statusMsg = httpURLConnection.getResponseMessage();
                } catch (IOException e2) {
                    e2.printStackTrace();
                    uRLResponse.statusMsg = "No Status Message!";
                }
                try {
                    BufferedInputStream bufferedInputStream = new BufferedInputStream(httpURLConnection.getInputStream());
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    byte[] bArr = new byte[6144];
                    while (true) {
                        try {
                            int read = bufferedInputStream.read(bArr);
                            if (read != -1) {
                                byteArrayOutputStream.write(bArr, 0, read);
                            } else {
                                try {
                                    byteArrayOutputStream.flush();
                                    byteArrayOutputStream.close();
                                    uRLResponse.body = byteArrayOutputStream.toByteArray();
                                    URLRequest.this.response2cpp(uRLResponse, 0);
                                    return;
                                } catch (IOException e3) {
                                    e3.printStackTrace();
                                    URLRequest.this.response2cpp(uRLResponse, 6);
                                    return;
                                }
                            }
                        } catch (IOException e4) {
                            e4.printStackTrace();
                            URLRequest.this.response2cpp(uRLResponse, 6);
                            return;
                        }
                    }
                } catch (IOException e5) {
                    e5.printStackTrace();
                    URLRequest.this.response2cpp(uRLResponse, 6);
                }
            } catch (UnknownHostException e6) {
                Log.e(URLRequest.TAG, "UnknownHost");
                URLRequest.this.response2cpp(uRLResponse, 3);
            } catch (SocketTimeoutException e7) {
                Log.e(URLRequest.TAG, "SocketTimeoutException");
                URLRequest.this.response2cpp(uRLResponse, 2);
            } catch (IOException e8) {
                Log.e(URLRequest.TAG, "IOException");
                e8.printStackTrace();
                URLRequest.this.response2cpp(uRLResponse, 6);
            } catch (SecurityException e9) {
                Log.e(URLRequest.TAG, "SecurityException error=" + e9.toString());
                URLRequest.this.response2cpp(uRLResponse, 7);
            }
        }
    }

    class UploadTask implements Runnable {
        UploadTask() {
        }

        public void run() {
            URLRequest.this.UploadProc();
        }
    }

    private void JNIRemoveCacheData(long j) {
        try {
            nativeRemoveCacheData(j);
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    private void JNISaveUploadPart(long j, int i) {
        try {
            nativeSaveUploadPart(j, i);
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    private void JNITaskBegan(long j, long j2) {
        try {
            nativeTaskBegan(j, j2);
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    /* access modifiers changed from: private */
    public void JNITaskFinished(long j, int i, long j2) {
        try {
            nativeTaskFinished(j, i, j2);
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    /* access modifiers changed from: private */
    public void JNITaskProgress(long j, long j2, long j3) {
        try {
            nativeTaskProgress(j, j2, j3);
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    /* JADX WARNING: type inference failed for: r2v27, types: [java.net.URLConnection] */
    /* access modifiers changed from: private */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void UploadProc() {
        /*
            r17 = this;
            r10 = 0
            r2 = 1
            r0 = r17
            r0.mState = r2
            java.util.UUID r2 = java.util.UUID.randomUUID()
            java.lang.String r11 = r2.toString()
            java.lang.String r12 = "--"
            java.lang.String r13 = "\r\n"
            java.io.File r3 = new java.io.File     // Catch:{ UnknownHostException -> 0x0241, SocketTimeoutException -> 0x033d, IOException -> 0x0371, JSONException -> 0x038a, SecurityException -> 0x03a3 }
            r0 = r17
            java.lang.String r2 = r0.mFilePath     // Catch:{ UnknownHostException -> 0x0241, SocketTimeoutException -> 0x033d, IOException -> 0x0371, JSONException -> 0x038a, SecurityException -> 0x03a3 }
            r3.<init>(r2)     // Catch:{ UnknownHostException -> 0x0241, SocketTimeoutException -> 0x033d, IOException -> 0x0371, JSONException -> 0x038a, SecurityException -> 0x03a3 }
            java.lang.String r4 = r3.getName()     // Catch:{ UnknownHostException -> 0x0241, SocketTimeoutException -> 0x033d, IOException -> 0x0371, JSONException -> 0x038a, SecurityException -> 0x03a3 }
            r0 = r17
            java.lang.String r2 = r0.mUrlStr     // Catch:{ UnknownHostException -> 0x0241, SocketTimeoutException -> 0x033d, IOException -> 0x0371, JSONException -> 0x038a, SecurityException -> 0x03a3 }
            r0 = r17
            int r5 = r0.mPart     // Catch:{ UnknownHostException -> 0x0241, SocketTimeoutException -> 0x033d, IOException -> 0x0371, JSONException -> 0x038a, SecurityException -> 0x03a3 }
            if (r5 != 0) goto L_0x01b0
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ UnknownHostException -> 0x0241, SocketTimeoutException -> 0x033d, IOException -> 0x0371, JSONException -> 0x038a, SecurityException -> 0x03a3 }
            r5.<init>()     // Catch:{ UnknownHostException -> 0x0241, SocketTimeoutException -> 0x033d, IOException -> 0x0371, JSONException -> 0x038a, SecurityException -> 0x03a3 }
            java.lang.StringBuilder r2 = r5.append(r2)     // Catch:{ UnknownHostException -> 0x0241, SocketTimeoutException -> 0x033d, IOException -> 0x0371, JSONException -> 0x038a, SecurityException -> 0x03a3 }
            java.lang.String r5 = "&size="
            java.lang.StringBuilder r2 = r2.append(r5)     // Catch:{ UnknownHostException -> 0x0241, SocketTimeoutException -> 0x033d, IOException -> 0x0371, JSONException -> 0x038a, SecurityException -> 0x03a3 }
            r0 = r17
            long r6 = r0.mFileSize     // Catch:{ UnknownHostException -> 0x0241, SocketTimeoutException -> 0x033d, IOException -> 0x0371, JSONException -> 0x038a, SecurityException -> 0x03a3 }
            java.lang.StringBuilder r2 = r2.append(r6)     // Catch:{ UnknownHostException -> 0x0241, SocketTimeoutException -> 0x033d, IOException -> 0x0371, JSONException -> 0x038a, SecurityException -> 0x03a3 }
            java.lang.String r5 = "&part_count="
            java.lang.StringBuilder r2 = r2.append(r5)     // Catch:{ UnknownHostException -> 0x0241, SocketTimeoutException -> 0x033d, IOException -> 0x0371, JSONException -> 0x038a, SecurityException -> 0x03a3 }
            r0 = r17
            int r5 = r0.mPartCount     // Catch:{ UnknownHostException -> 0x0241, SocketTimeoutException -> 0x033d, IOException -> 0x0371, JSONException -> 0x038a, SecurityException -> 0x03a3 }
            java.lang.StringBuilder r2 = r2.append(r5)     // Catch:{ UnknownHostException -> 0x0241, SocketTimeoutException -> 0x033d, IOException -> 0x0371, JSONException -> 0x038a, SecurityException -> 0x03a3 }
            java.lang.String r5 = "&part="
            java.lang.StringBuilder r2 = r2.append(r5)     // Catch:{ UnknownHostException -> 0x0241, SocketTimeoutException -> 0x033d, IOException -> 0x0371, JSONException -> 0x038a, SecurityException -> 0x03a3 }
            r0 = r17
            int r5 = r0.mPart     // Catch:{ UnknownHostException -> 0x0241, SocketTimeoutException -> 0x033d, IOException -> 0x0371, JSONException -> 0x038a, SecurityException -> 0x03a3 }
            java.lang.StringBuilder r2 = r2.append(r5)     // Catch:{ UnknownHostException -> 0x0241, SocketTimeoutException -> 0x033d, IOException -> 0x0371, JSONException -> 0x038a, SecurityException -> 0x03a3 }
            java.lang.String r5 = "&file_md5="
            java.lang.StringBuilder r2 = r2.append(r5)     // Catch:{ UnknownHostException -> 0x0241, SocketTimeoutException -> 0x033d, IOException -> 0x0371, JSONException -> 0x038a, SecurityException -> 0x03a3 }
            r0 = r17
            java.lang.String r5 = r0.mFileMD5     // Catch:{ UnknownHostException -> 0x0241, SocketTimeoutException -> 0x033d, IOException -> 0x0371, JSONException -> 0x038a, SecurityException -> 0x03a3 }
            java.lang.StringBuilder r2 = r2.append(r5)     // Catch:{ UnknownHostException -> 0x0241, SocketTimeoutException -> 0x033d, IOException -> 0x0371, JSONException -> 0x038a, SecurityException -> 0x03a3 }
            java.lang.String r5 = "&filename="
            java.lang.StringBuilder r2 = r2.append(r5)     // Catch:{ UnknownHostException -> 0x0241, SocketTimeoutException -> 0x033d, IOException -> 0x0371, JSONException -> 0x038a, SecurityException -> 0x03a3 }
            java.lang.StringBuilder r2 = r2.append(r4)     // Catch:{ UnknownHostException -> 0x0241, SocketTimeoutException -> 0x033d, IOException -> 0x0371, JSONException -> 0x038a, SecurityException -> 0x03a3 }
            java.lang.String r2 = r2.toString()     // Catch:{ UnknownHostException -> 0x0241, SocketTimeoutException -> 0x033d, IOException -> 0x0371, JSONException -> 0x038a, SecurityException -> 0x03a3 }
        L_0x0078:
            java.lang.String r5 = "URLRequest"
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ UnknownHostException -> 0x0241, SocketTimeoutException -> 0x033d, IOException -> 0x0371, JSONException -> 0x038a, SecurityException -> 0x03a3 }
            r6.<init>()     // Catch:{ UnknownHostException -> 0x0241, SocketTimeoutException -> 0x033d, IOException -> 0x0371, JSONException -> 0x038a, SecurityException -> 0x03a3 }
            java.lang.String r7 = "url is "
            java.lang.StringBuilder r6 = r6.append(r7)     // Catch:{ UnknownHostException -> 0x0241, SocketTimeoutException -> 0x033d, IOException -> 0x0371, JSONException -> 0x038a, SecurityException -> 0x03a3 }
            java.lang.StringBuilder r6 = r6.append(r2)     // Catch:{ UnknownHostException -> 0x0241, SocketTimeoutException -> 0x033d, IOException -> 0x0371, JSONException -> 0x038a, SecurityException -> 0x03a3 }
            java.lang.String r6 = r6.toString()     // Catch:{ UnknownHostException -> 0x0241, SocketTimeoutException -> 0x033d, IOException -> 0x0371, JSONException -> 0x038a, SecurityException -> 0x03a3 }
            android.util.Log.i(r5, r6)     // Catch:{ UnknownHostException -> 0x0241, SocketTimeoutException -> 0x033d, IOException -> 0x0371, JSONException -> 0x038a, SecurityException -> 0x03a3 }
            java.net.URL r5 = new java.net.URL     // Catch:{ UnknownHostException -> 0x0241, SocketTimeoutException -> 0x033d, IOException -> 0x0371, JSONException -> 0x038a, SecurityException -> 0x03a3 }
            r5.<init>(r2)     // Catch:{ UnknownHostException -> 0x0241, SocketTimeoutException -> 0x033d, IOException -> 0x0371, JSONException -> 0x038a, SecurityException -> 0x03a3 }
            java.net.URLConnection r2 = r5.openConnection()     // Catch:{ UnknownHostException -> 0x0241, SocketTimeoutException -> 0x033d, IOException -> 0x0371, JSONException -> 0x038a, SecurityException -> 0x03a3 }
            r0 = r2
            java.net.HttpURLConnection r0 = (java.net.HttpURLConnection) r0     // Catch:{ UnknownHostException -> 0x0241, SocketTimeoutException -> 0x033d, IOException -> 0x0371, JSONException -> 0x038a, SecurityException -> 0x03a3 }
            r10 = r0
            r0 = r17
            int r2 = r0.timeout     // Catch:{ UnknownHostException -> 0x0241, SocketTimeoutException -> 0x033d, IOException -> 0x0371, JSONException -> 0x038a, SecurityException -> 0x03a3 }
            r10.setReadTimeout(r2)     // Catch:{ UnknownHostException -> 0x0241, SocketTimeoutException -> 0x033d, IOException -> 0x0371, JSONException -> 0x038a, SecurityException -> 0x03a3 }
            r0 = r17
            int r2 = r0.timeout     // Catch:{ UnknownHostException -> 0x0241, SocketTimeoutException -> 0x033d, IOException -> 0x0371, JSONException -> 0x038a, SecurityException -> 0x03a3 }
            r10.setConnectTimeout(r2)     // Catch:{ UnknownHostException -> 0x0241, SocketTimeoutException -> 0x033d, IOException -> 0x0371, JSONException -> 0x038a, SecurityException -> 0x03a3 }
            java.lang.String r2 = "POST"
            r10.setRequestMethod(r2)     // Catch:{ UnknownHostException -> 0x0241, SocketTimeoutException -> 0x033d, IOException -> 0x0371, JSONException -> 0x038a, SecurityException -> 0x03a3 }
            r2 = 1
            r10.setDoInput(r2)     // Catch:{ UnknownHostException -> 0x0241, SocketTimeoutException -> 0x033d, IOException -> 0x0371, JSONException -> 0x038a, SecurityException -> 0x03a3 }
            r2 = 1
            r10.setDoOutput(r2)     // Catch:{ UnknownHostException -> 0x0241, SocketTimeoutException -> 0x033d, IOException -> 0x0371, JSONException -> 0x038a, SecurityException -> 0x03a3 }
            r2 = 0
            r10.setUseCaches(r2)     // Catch:{ UnknownHostException -> 0x0241, SocketTimeoutException -> 0x033d, IOException -> 0x0371, JSONException -> 0x038a, SecurityException -> 0x03a3 }
            java.lang.String r2 = "connection"
            java.lang.String r5 = "keep-alive"
            r10.setRequestProperty(r2, r5)     // Catch:{ UnknownHostException -> 0x0241, SocketTimeoutException -> 0x033d, IOException -> 0x0371, JSONException -> 0x038a, SecurityException -> 0x03a3 }
            java.lang.String r2 = "Accept"
            java.lang.String r5 = "*/*"
            r10.setRequestProperty(r2, r5)     // Catch:{ UnknownHostException -> 0x0241, SocketTimeoutException -> 0x033d, IOException -> 0x0371, JSONException -> 0x038a, SecurityException -> 0x03a3 }
            java.lang.String r2 = "Content-Type"
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ UnknownHostException -> 0x0241, SocketTimeoutException -> 0x033d, IOException -> 0x0371, JSONException -> 0x038a, SecurityException -> 0x03a3 }
            r5.<init>()     // Catch:{ UnknownHostException -> 0x0241, SocketTimeoutException -> 0x033d, IOException -> 0x0371, JSONException -> 0x038a, SecurityException -> 0x03a3 }
            java.lang.String r6 = "multipart/form-data;boundary="
            java.lang.StringBuilder r5 = r5.append(r6)     // Catch:{ UnknownHostException -> 0x0241, SocketTimeoutException -> 0x033d, IOException -> 0x0371, JSONException -> 0x038a, SecurityException -> 0x03a3 }
            java.lang.StringBuilder r5 = r5.append(r11)     // Catch:{ UnknownHostException -> 0x0241, SocketTimeoutException -> 0x033d, IOException -> 0x0371, JSONException -> 0x038a, SecurityException -> 0x03a3 }
            java.lang.String r5 = r5.toString()     // Catch:{ UnknownHostException -> 0x0241, SocketTimeoutException -> 0x033d, IOException -> 0x0371, JSONException -> 0x038a, SecurityException -> 0x03a3 }
            r10.setRequestProperty(r2, r5)     // Catch:{ UnknownHostException -> 0x0241, SocketTimeoutException -> 0x033d, IOException -> 0x0371, JSONException -> 0x038a, SecurityException -> 0x03a3 }
            r0 = r17
            r0.addHeadersToConn(r10)     // Catch:{ UnknownHostException -> 0x0241, SocketTimeoutException -> 0x033d, IOException -> 0x0371, JSONException -> 0x038a, SecurityException -> 0x03a3 }
            r0 = r17
            int r2 = r0.mPart     // Catch:{ UnknownHostException -> 0x0241, SocketTimeoutException -> 0x033d, IOException -> 0x0371, JSONException -> 0x038a, SecurityException -> 0x03a3 }
            if (r2 != 0) goto L_0x00f8
            r0 = r17
            long r6 = r0.mUploadDelegate     // Catch:{ UnknownHostException -> 0x0241, SocketTimeoutException -> 0x033d, IOException -> 0x0371, JSONException -> 0x038a, SecurityException -> 0x03a3 }
            r0 = r17
            long r8 = r0.mFileSize     // Catch:{ UnknownHostException -> 0x0241, SocketTimeoutException -> 0x033d, IOException -> 0x0371, JSONException -> 0x038a, SecurityException -> 0x03a3 }
            nativeTaskBegan(r6, r8)     // Catch:{ UnknownHostException -> 0x0241, SocketTimeoutException -> 0x033d, IOException -> 0x0371, JSONException -> 0x038a, SecurityException -> 0x03a3 }
        L_0x00f8:
            if (r3 == 0) goto L_0x02b0
            java.io.OutputStream r2 = r10.getOutputStream()     // Catch:{ UnknownHostException -> 0x0241, SocketTimeoutException -> 0x033d, IOException -> 0x0371, JSONException -> 0x038a, SecurityException -> 0x03a3 }
            java.io.DataOutputStream r14 = new java.io.DataOutputStream     // Catch:{ UnknownHostException -> 0x0241, SocketTimeoutException -> 0x033d, IOException -> 0x0371, JSONException -> 0x038a, SecurityException -> 0x03a3 }
            r14.<init>(r2)     // Catch:{ UnknownHostException -> 0x0241, SocketTimeoutException -> 0x033d, IOException -> 0x0371, JSONException -> 0x038a, SecurityException -> 0x03a3 }
            java.lang.StringBuffer r2 = new java.lang.StringBuffer     // Catch:{ UnknownHostException -> 0x0241, SocketTimeoutException -> 0x033d, IOException -> 0x0371, JSONException -> 0x038a, SecurityException -> 0x03a3 }
            r2.<init>()     // Catch:{ UnknownHostException -> 0x0241, SocketTimeoutException -> 0x033d, IOException -> 0x0371, JSONException -> 0x038a, SecurityException -> 0x03a3 }
            r2.append(r12)     // Catch:{ UnknownHostException -> 0x0241, SocketTimeoutException -> 0x033d, IOException -> 0x0371, JSONException -> 0x038a, SecurityException -> 0x03a3 }
            r2.append(r11)     // Catch:{ UnknownHostException -> 0x0241, SocketTimeoutException -> 0x033d, IOException -> 0x0371, JSONException -> 0x038a, SecurityException -> 0x03a3 }
            r2.append(r13)     // Catch:{ UnknownHostException -> 0x0241, SocketTimeoutException -> 0x033d, IOException -> 0x0371, JSONException -> 0x038a, SecurityException -> 0x03a3 }
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ UnknownHostException -> 0x0241, SocketTimeoutException -> 0x033d, IOException -> 0x0371, JSONException -> 0x038a, SecurityException -> 0x03a3 }
            r3.<init>()     // Catch:{ UnknownHostException -> 0x0241, SocketTimeoutException -> 0x033d, IOException -> 0x0371, JSONException -> 0x038a, SecurityException -> 0x03a3 }
            java.lang.String r5 = "Content-Disposition: form-data; name=\"myfile\"; filename=\""
            java.lang.StringBuilder r3 = r3.append(r5)     // Catch:{ UnknownHostException -> 0x0241, SocketTimeoutException -> 0x033d, IOException -> 0x0371, JSONException -> 0x038a, SecurityException -> 0x03a3 }
            java.lang.StringBuilder r3 = r3.append(r4)     // Catch:{ UnknownHostException -> 0x0241, SocketTimeoutException -> 0x033d, IOException -> 0x0371, JSONException -> 0x038a, SecurityException -> 0x03a3 }
            java.lang.String r4 = ".part"
            java.lang.StringBuilder r3 = r3.append(r4)     // Catch:{ UnknownHostException -> 0x0241, SocketTimeoutException -> 0x033d, IOException -> 0x0371, JSONException -> 0x038a, SecurityException -> 0x03a3 }
            r0 = r17
            int r4 = r0.mPart     // Catch:{ UnknownHostException -> 0x0241, SocketTimeoutException -> 0x033d, IOException -> 0x0371, JSONException -> 0x038a, SecurityException -> 0x03a3 }
            java.lang.StringBuilder r3 = r3.append(r4)     // Catch:{ UnknownHostException -> 0x0241, SocketTimeoutException -> 0x033d, IOException -> 0x0371, JSONException -> 0x038a, SecurityException -> 0x03a3 }
            java.lang.String r4 = "\""
            java.lang.StringBuilder r3 = r3.append(r4)     // Catch:{ UnknownHostException -> 0x0241, SocketTimeoutException -> 0x033d, IOException -> 0x0371, JSONException -> 0x038a, SecurityException -> 0x03a3 }
            java.lang.StringBuilder r3 = r3.append(r13)     // Catch:{ UnknownHostException -> 0x0241, SocketTimeoutException -> 0x033d, IOException -> 0x0371, JSONException -> 0x038a, SecurityException -> 0x03a3 }
            java.lang.String r3 = r3.toString()     // Catch:{ UnknownHostException -> 0x0241, SocketTimeoutException -> 0x033d, IOException -> 0x0371, JSONException -> 0x038a, SecurityException -> 0x03a3 }
            r2.append(r3)     // Catch:{ UnknownHostException -> 0x0241, SocketTimeoutException -> 0x033d, IOException -> 0x0371, JSONException -> 0x038a, SecurityException -> 0x03a3 }
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ UnknownHostException -> 0x0241, SocketTimeoutException -> 0x033d, IOException -> 0x0371, JSONException -> 0x038a, SecurityException -> 0x03a3 }
            r3.<init>()     // Catch:{ UnknownHostException -> 0x0241, SocketTimeoutException -> 0x033d, IOException -> 0x0371, JSONException -> 0x038a, SecurityException -> 0x03a3 }
            java.lang.String r4 = "Content-Type: application/octet-stream"
            java.lang.StringBuilder r3 = r3.append(r4)     // Catch:{ UnknownHostException -> 0x0241, SocketTimeoutException -> 0x033d, IOException -> 0x0371, JSONException -> 0x038a, SecurityException -> 0x03a3 }
            java.lang.StringBuilder r3 = r3.append(r13)     // Catch:{ UnknownHostException -> 0x0241, SocketTimeoutException -> 0x033d, IOException -> 0x0371, JSONException -> 0x038a, SecurityException -> 0x03a3 }
            java.lang.String r3 = r3.toString()     // Catch:{ UnknownHostException -> 0x0241, SocketTimeoutException -> 0x033d, IOException -> 0x0371, JSONException -> 0x038a, SecurityException -> 0x03a3 }
            r2.append(r3)     // Catch:{ UnknownHostException -> 0x0241, SocketTimeoutException -> 0x033d, IOException -> 0x0371, JSONException -> 0x038a, SecurityException -> 0x03a3 }
            r2.append(r13)     // Catch:{ UnknownHostException -> 0x0241, SocketTimeoutException -> 0x033d, IOException -> 0x0371, JSONException -> 0x038a, SecurityException -> 0x03a3 }
            java.lang.String r2 = r2.toString()     // Catch:{ UnknownHostException -> 0x0241, SocketTimeoutException -> 0x033d, IOException -> 0x0371, JSONException -> 0x038a, SecurityException -> 0x03a3 }
            byte[] r2 = r2.getBytes()     // Catch:{ UnknownHostException -> 0x0241, SocketTimeoutException -> 0x033d, IOException -> 0x0371, JSONException -> 0x038a, SecurityException -> 0x03a3 }
            r14.write(r2)     // Catch:{ UnknownHostException -> 0x0241, SocketTimeoutException -> 0x033d, IOException -> 0x0371, JSONException -> 0x038a, SecurityException -> 0x03a3 }
            java.io.RandomAccessFile r15 = new java.io.RandomAccessFile     // Catch:{ UnknownHostException -> 0x0241, SocketTimeoutException -> 0x033d, IOException -> 0x0371, JSONException -> 0x038a, SecurityException -> 0x03a3 }
            r0 = r17
            java.lang.String r2 = r0.mFilePath     // Catch:{ UnknownHostException -> 0x0241, SocketTimeoutException -> 0x033d, IOException -> 0x0371, JSONException -> 0x038a, SecurityException -> 0x03a3 }
            java.lang.String r3 = "r"
            r15.<init>(r2, r3)     // Catch:{ UnknownHostException -> 0x0241, SocketTimeoutException -> 0x033d, IOException -> 0x0371, JSONException -> 0x038a, SecurityException -> 0x03a3 }
            r2 = 1024(0x400, float:1.435E-42)
            byte[] r0 = new byte[r2]     // Catch:{ UnknownHostException -> 0x0241, SocketTimeoutException -> 0x033d, IOException -> 0x0371, JSONException -> 0x038a, SecurityException -> 0x03a3 }
            r16 = r0
            r0 = r17
            int r2 = r0.mPart     // Catch:{ UnknownHostException -> 0x0241, SocketTimeoutException -> 0x033d, IOException -> 0x0371, JSONException -> 0x038a, SecurityException -> 0x03a3 }
            r3 = 128000(0x1f400, float:1.79366E-40)
            int r2 = r2 * r3
            long r2 = (long) r2     // Catch:{ UnknownHostException -> 0x0241, SocketTimeoutException -> 0x033d, IOException -> 0x0371, JSONException -> 0x038a, SecurityException -> 0x03a3 }
            r15.seek(r2)     // Catch:{ UnknownHostException -> 0x0241, SocketTimeoutException -> 0x033d, IOException -> 0x0371, JSONException -> 0x038a, SecurityException -> 0x03a3 }
            r2 = 0
        L_0x0181:
            r3 = 125(0x7d, float:1.75E-43)
            if (r2 >= r3) goto L_0x01e9
            int r3 = r15.read(r16)     // Catch:{ UnknownHostException -> 0x0241, SocketTimeoutException -> 0x033d, IOException -> 0x0371, JSONException -> 0x038a, SecurityException -> 0x03a3 }
            r4 = -1
            if (r3 == r4) goto L_0x01e9
            r4 = 0
            r0 = r16
            r14.write(r0, r4, r3)     // Catch:{ UnknownHostException -> 0x0241, SocketTimeoutException -> 0x033d, IOException -> 0x0371, JSONException -> 0x038a, SecurityException -> 0x03a3 }
            r0 = r17
            long r4 = r0.mFileCurrentSize     // Catch:{ UnknownHostException -> 0x0241, SocketTimeoutException -> 0x033d, IOException -> 0x0371, JSONException -> 0x038a, SecurityException -> 0x03a3 }
            long r6 = (long) r3     // Catch:{ UnknownHostException -> 0x0241, SocketTimeoutException -> 0x033d, IOException -> 0x0371, JSONException -> 0x038a, SecurityException -> 0x03a3 }
            long r4 = r4 + r6
            r0 = r17
            r0.mFileCurrentSize = r4     // Catch:{ UnknownHostException -> 0x0241, SocketTimeoutException -> 0x033d, IOException -> 0x0371, JSONException -> 0x038a, SecurityException -> 0x03a3 }
            r0 = r17
            long r4 = r0.mUploadDelegate     // Catch:{ UnknownHostException -> 0x0241, SocketTimeoutException -> 0x033d, IOException -> 0x0371, JSONException -> 0x038a, SecurityException -> 0x03a3 }
            r0 = r17
            long r6 = r0.mFileCurrentSize     // Catch:{ UnknownHostException -> 0x0241, SocketTimeoutException -> 0x033d, IOException -> 0x0371, JSONException -> 0x038a, SecurityException -> 0x03a3 }
            r0 = r17
            long r8 = r0.mFileSize     // Catch:{ UnknownHostException -> 0x0241, SocketTimeoutException -> 0x033d, IOException -> 0x0371, JSONException -> 0x038a, SecurityException -> 0x03a3 }
            r3 = r17
            r3.JNITaskProgress(r4, r6, r8)     // Catch:{ UnknownHostException -> 0x0241, SocketTimeoutException -> 0x033d, IOException -> 0x0371, JSONException -> 0x038a, SecurityException -> 0x03a3 }
            int r2 = r2 + 1
            goto L_0x0181
        L_0x01b0:
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ UnknownHostException -> 0x0241, SocketTimeoutException -> 0x033d, IOException -> 0x0371, JSONException -> 0x038a, SecurityException -> 0x03a3 }
            r5.<init>()     // Catch:{ UnknownHostException -> 0x0241, SocketTimeoutException -> 0x033d, IOException -> 0x0371, JSONException -> 0x038a, SecurityException -> 0x03a3 }
            java.lang.StringBuilder r2 = r5.append(r2)     // Catch:{ UnknownHostException -> 0x0241, SocketTimeoutException -> 0x033d, IOException -> 0x0371, JSONException -> 0x038a, SecurityException -> 0x03a3 }
            java.lang.String r5 = "&size="
            java.lang.StringBuilder r2 = r2.append(r5)     // Catch:{ UnknownHostException -> 0x0241, SocketTimeoutException -> 0x033d, IOException -> 0x0371, JSONException -> 0x038a, SecurityException -> 0x03a3 }
            r0 = r17
            long r6 = r0.mFileSize     // Catch:{ UnknownHostException -> 0x0241, SocketTimeoutException -> 0x033d, IOException -> 0x0371, JSONException -> 0x038a, SecurityException -> 0x03a3 }
            java.lang.StringBuilder r2 = r2.append(r6)     // Catch:{ UnknownHostException -> 0x0241, SocketTimeoutException -> 0x033d, IOException -> 0x0371, JSONException -> 0x038a, SecurityException -> 0x03a3 }
            java.lang.String r5 = "&part_count="
            java.lang.StringBuilder r2 = r2.append(r5)     // Catch:{ UnknownHostException -> 0x0241, SocketTimeoutException -> 0x033d, IOException -> 0x0371, JSONException -> 0x038a, SecurityException -> 0x03a3 }
            r0 = r17
            int r5 = r0.mPartCount     // Catch:{ UnknownHostException -> 0x0241, SocketTimeoutException -> 0x033d, IOException -> 0x0371, JSONException -> 0x038a, SecurityException -> 0x03a3 }
            java.lang.StringBuilder r2 = r2.append(r5)     // Catch:{ UnknownHostException -> 0x0241, SocketTimeoutException -> 0x033d, IOException -> 0x0371, JSONException -> 0x038a, SecurityException -> 0x03a3 }
            java.lang.String r5 = "&part="
            java.lang.StringBuilder r2 = r2.append(r5)     // Catch:{ UnknownHostException -> 0x0241, SocketTimeoutException -> 0x033d, IOException -> 0x0371, JSONException -> 0x038a, SecurityException -> 0x03a3 }
            r0 = r17
            int r5 = r0.mPart     // Catch:{ UnknownHostException -> 0x0241, SocketTimeoutException -> 0x033d, IOException -> 0x0371, JSONException -> 0x038a, SecurityException -> 0x03a3 }
            java.lang.StringBuilder r2 = r2.append(r5)     // Catch:{ UnknownHostException -> 0x0241, SocketTimeoutException -> 0x033d, IOException -> 0x0371, JSONException -> 0x038a, SecurityException -> 0x03a3 }
            java.lang.String r2 = r2.toString()     // Catch:{ UnknownHostException -> 0x0241, SocketTimeoutException -> 0x033d, IOException -> 0x0371, JSONException -> 0x038a, SecurityException -> 0x03a3 }
            goto L_0x0078
        L_0x01e9:
            r15.close()     // Catch:{ UnknownHostException -> 0x0241, SocketTimeoutException -> 0x033d, IOException -> 0x0371, JSONException -> 0x038a, SecurityException -> 0x03a3 }
            byte[] r2 = r13.getBytes()     // Catch:{ UnknownHostException -> 0x0241, SocketTimeoutException -> 0x033d, IOException -> 0x0371, JSONException -> 0x038a, SecurityException -> 0x03a3 }
            r14.write(r2)     // Catch:{ UnknownHostException -> 0x0241, SocketTimeoutException -> 0x033d, IOException -> 0x0371, JSONException -> 0x038a, SecurityException -> 0x03a3 }
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ UnknownHostException -> 0x0241, SocketTimeoutException -> 0x033d, IOException -> 0x0371, JSONException -> 0x038a, SecurityException -> 0x03a3 }
            r2.<init>()     // Catch:{ UnknownHostException -> 0x0241, SocketTimeoutException -> 0x033d, IOException -> 0x0371, JSONException -> 0x038a, SecurityException -> 0x03a3 }
            java.lang.StringBuilder r2 = r2.append(r12)     // Catch:{ UnknownHostException -> 0x0241, SocketTimeoutException -> 0x033d, IOException -> 0x0371, JSONException -> 0x038a, SecurityException -> 0x03a3 }
            java.lang.StringBuilder r2 = r2.append(r11)     // Catch:{ UnknownHostException -> 0x0241, SocketTimeoutException -> 0x033d, IOException -> 0x0371, JSONException -> 0x038a, SecurityException -> 0x03a3 }
            java.lang.StringBuilder r2 = r2.append(r12)     // Catch:{ UnknownHostException -> 0x0241, SocketTimeoutException -> 0x033d, IOException -> 0x0371, JSONException -> 0x038a, SecurityException -> 0x03a3 }
            java.lang.StringBuilder r2 = r2.append(r13)     // Catch:{ UnknownHostException -> 0x0241, SocketTimeoutException -> 0x033d, IOException -> 0x0371, JSONException -> 0x038a, SecurityException -> 0x03a3 }
            java.lang.String r2 = r2.toString()     // Catch:{ UnknownHostException -> 0x0241, SocketTimeoutException -> 0x033d, IOException -> 0x0371, JSONException -> 0x038a, SecurityException -> 0x03a3 }
            byte[] r2 = r2.getBytes()     // Catch:{ UnknownHostException -> 0x0241, SocketTimeoutException -> 0x033d, IOException -> 0x0371, JSONException -> 0x038a, SecurityException -> 0x03a3 }
            r14.write(r2)     // Catch:{ UnknownHostException -> 0x0241, SocketTimeoutException -> 0x033d, IOException -> 0x0371, JSONException -> 0x038a, SecurityException -> 0x03a3 }
            r14.flush()     // Catch:{ UnknownHostException -> 0x0241, SocketTimeoutException -> 0x033d, IOException -> 0x0371, JSONException -> 0x038a, SecurityException -> 0x03a3 }
            int r3 = r10.getResponseCode()     // Catch:{ UnknownHostException -> 0x0241, SocketTimeoutException -> 0x033d, IOException -> 0x0371, JSONException -> 0x038a, SecurityException -> 0x03a3 }
            r2 = 1
            r4 = 200(0xc8, float:2.8E-43)
            if (r3 != r4) goto L_0x0356
            java.io.InputStream r3 = r10.getInputStream()     // Catch:{ UnknownHostException -> 0x0241, SocketTimeoutException -> 0x033d, IOException -> 0x0371, JSONException -> 0x038a, SecurityException -> 0x03a3 }
            java.io.ByteArrayOutputStream r4 = new java.io.ByteArrayOutputStream     // Catch:{ UnknownHostException -> 0x0241, SocketTimeoutException -> 0x033d, IOException -> 0x0371, JSONException -> 0x038a, SecurityException -> 0x03a3 }
            r4.<init>()     // Catch:{ UnknownHostException -> 0x0241, SocketTimeoutException -> 0x033d, IOException -> 0x0371, JSONException -> 0x038a, SecurityException -> 0x03a3 }
            java.io.BufferedInputStream r5 = new java.io.BufferedInputStream     // Catch:{ UnknownHostException -> 0x0241, SocketTimeoutException -> 0x033d, IOException -> 0x0371, JSONException -> 0x038a, SecurityException -> 0x03a3 }
            r5.<init>(r3)     // Catch:{ UnknownHostException -> 0x0241, SocketTimeoutException -> 0x033d, IOException -> 0x0371, JSONException -> 0x038a, SecurityException -> 0x03a3 }
            java.io.BufferedOutputStream r3 = new java.io.BufferedOutputStream     // Catch:{ UnknownHostException -> 0x0241, SocketTimeoutException -> 0x033d, IOException -> 0x0371, JSONException -> 0x038a, SecurityException -> 0x03a3 }
            r3.<init>(r4)     // Catch:{ UnknownHostException -> 0x0241, SocketTimeoutException -> 0x033d, IOException -> 0x0371, JSONException -> 0x038a, SecurityException -> 0x03a3 }
            r6 = 8192(0x2000, float:1.14794E-41)
            byte[] r6 = new byte[r6]     // Catch:{ UnknownHostException -> 0x0241, SocketTimeoutException -> 0x033d, IOException -> 0x0371, JSONException -> 0x038a, SecurityException -> 0x03a3 }
        L_0x0236:
            int r7 = r5.read(r6)     // Catch:{ UnknownHostException -> 0x0241, SocketTimeoutException -> 0x033d, IOException -> 0x0371, JSONException -> 0x038a, SecurityException -> 0x03a3 }
            if (r7 <= 0) goto L_0x0259
            r8 = 0
            r3.write(r6, r8, r7)     // Catch:{ UnknownHostException -> 0x0241, SocketTimeoutException -> 0x033d, IOException -> 0x0371, JSONException -> 0x038a, SecurityException -> 0x03a3 }
            goto L_0x0236
        L_0x0241:
            r2 = move-exception
            r2.printStackTrace()     // Catch:{ all -> 0x03d5 }
            r0 = r17
            long r3 = r0.mUploadDelegate     // Catch:{ all -> 0x03d5 }
            r5 = 3
            r0 = r17
            long r6 = r0.mFileSize     // Catch:{ all -> 0x03d5 }
            r2 = r17
            r2.JNITaskFinished(r3, r5, r6)     // Catch:{ all -> 0x03d5 }
            if (r10 == 0) goto L_0x0258
            r10.disconnect()
        L_0x0258:
            return
        L_0x0259:
            r3.flush()     // Catch:{ UnknownHostException -> 0x0241, SocketTimeoutException -> 0x033d, IOException -> 0x0371, JSONException -> 0x038a, SecurityException -> 0x03a3 }
            byte[] r3 = r4.toByteArray()     // Catch:{ UnknownHostException -> 0x0241, SocketTimeoutException -> 0x033d, IOException -> 0x0371, JSONException -> 0x038a, SecurityException -> 0x03a3 }
            java.lang.String r4 = new java.lang.String     // Catch:{ UnknownHostException -> 0x0241, SocketTimeoutException -> 0x033d, IOException -> 0x0371, JSONException -> 0x038a, SecurityException -> 0x03a3 }
            java.lang.String r5 = "UTF-8"
            r4.<init>(r3, r5)     // Catch:{ UnknownHostException -> 0x0241, SocketTimeoutException -> 0x033d, IOException -> 0x0371, JSONException -> 0x038a, SecurityException -> 0x03a3 }
            java.lang.String r3 = "URLRequest"
            android.util.Log.i(r3, r4)     // Catch:{ UnknownHostException -> 0x0241, SocketTimeoutException -> 0x033d, IOException -> 0x0371, JSONException -> 0x038a, SecurityException -> 0x03a3 }
            org.json.JSONObject r3 = new org.json.JSONObject     // Catch:{ UnknownHostException -> 0x0241, SocketTimeoutException -> 0x033d, IOException -> 0x0371, JSONException -> 0x038a, SecurityException -> 0x03a3 }
            r3.<init>(r4)     // Catch:{ UnknownHostException -> 0x0241, SocketTimeoutException -> 0x033d, IOException -> 0x0371, JSONException -> 0x038a, SecurityException -> 0x03a3 }
            java.lang.String r4 = "err_code"
            int r4 = r3.optInt(r4)     // Catch:{ UnknownHostException -> 0x0241, SocketTimeoutException -> 0x033d, IOException -> 0x0371, JSONException -> 0x038a, SecurityException -> 0x03a3 }
            if (r4 == 0) goto L_0x02b9
            java.lang.String r2 = "URLRequest"
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ UnknownHostException -> 0x0241, SocketTimeoutException -> 0x033d, IOException -> 0x0371, JSONException -> 0x038a, SecurityException -> 0x03a3 }
            r5.<init>()     // Catch:{ UnknownHostException -> 0x0241, SocketTimeoutException -> 0x033d, IOException -> 0x0371, JSONException -> 0x038a, SecurityException -> 0x03a3 }
            java.lang.String r6 = "err_code "
            java.lang.StringBuilder r5 = r5.append(r6)     // Catch:{ UnknownHostException -> 0x0241, SocketTimeoutException -> 0x033d, IOException -> 0x0371, JSONException -> 0x038a, SecurityException -> 0x03a3 }
            java.lang.StringBuilder r4 = r5.append(r4)     // Catch:{ UnknownHostException -> 0x0241, SocketTimeoutException -> 0x033d, IOException -> 0x0371, JSONException -> 0x038a, SecurityException -> 0x03a3 }
            java.lang.String r5 = ",err_msg "
            java.lang.StringBuilder r4 = r4.append(r5)     // Catch:{ UnknownHostException -> 0x0241, SocketTimeoutException -> 0x033d, IOException -> 0x0371, JSONException -> 0x038a, SecurityException -> 0x03a3 }
            java.lang.String r5 = "err_msg"
            java.lang.String r3 = r3.optString(r5)     // Catch:{ UnknownHostException -> 0x0241, SocketTimeoutException -> 0x033d, IOException -> 0x0371, JSONException -> 0x038a, SecurityException -> 0x03a3 }
            java.lang.StringBuilder r3 = r4.append(r3)     // Catch:{ UnknownHostException -> 0x0241, SocketTimeoutException -> 0x033d, IOException -> 0x0371, JSONException -> 0x038a, SecurityException -> 0x03a3 }
            java.lang.String r3 = r3.toString()     // Catch:{ UnknownHostException -> 0x0241, SocketTimeoutException -> 0x033d, IOException -> 0x0371, JSONException -> 0x038a, SecurityException -> 0x03a3 }
            android.util.Log.e(r2, r3)     // Catch:{ UnknownHostException -> 0x0241, SocketTimeoutException -> 0x033d, IOException -> 0x0371, JSONException -> 0x038a, SecurityException -> 0x03a3 }
            r2 = 1
        L_0x02a2:
            r5 = r2
        L_0x02a3:
            r0 = r17
            long r3 = r0.mUploadDelegate     // Catch:{ UnknownHostException -> 0x0241, SocketTimeoutException -> 0x033d, IOException -> 0x0371, JSONException -> 0x038a, SecurityException -> 0x03a3 }
            r0 = r17
            long r6 = r0.mFileSize     // Catch:{ UnknownHostException -> 0x0241, SocketTimeoutException -> 0x033d, IOException -> 0x0371, JSONException -> 0x038a, SecurityException -> 0x03a3 }
            r2 = r17
            r2.JNITaskFinished(r3, r5, r6)     // Catch:{ UnknownHostException -> 0x0241, SocketTimeoutException -> 0x033d, IOException -> 0x0371, JSONException -> 0x038a, SecurityException -> 0x03a3 }
        L_0x02b0:
            r17.uninit()     // Catch:{ UnknownHostException -> 0x0241, SocketTimeoutException -> 0x033d, IOException -> 0x0371, JSONException -> 0x038a, SecurityException -> 0x03a3 }
            if (r10 == 0) goto L_0x0258
            r10.disconnect()
            goto L_0x0258
        L_0x02b9:
            r0 = r17
            int r3 = r0.mPart     // Catch:{ UnknownHostException -> 0x0241, SocketTimeoutException -> 0x033d, IOException -> 0x0371, JSONException -> 0x038a, SecurityException -> 0x03a3 }
            r0 = r17
            int r4 = r0.mPartCount     // Catch:{ UnknownHostException -> 0x0241, SocketTimeoutException -> 0x033d, IOException -> 0x0371, JSONException -> 0x038a, SecurityException -> 0x03a3 }
            int r4 = r4 + -1
            if (r3 >= r4) goto L_0x0325
            r0 = r17
            int r2 = r0.mState     // Catch:{ UnknownHostException -> 0x0241, SocketTimeoutException -> 0x033d, IOException -> 0x0371, JSONException -> 0x038a, SecurityException -> 0x03a3 }
            if (r2 != 0) goto L_0x02db
            r0 = r17
            long r2 = r0.mUploadDelegate     // Catch:{ UnknownHostException -> 0x0241, SocketTimeoutException -> 0x033d, IOException -> 0x0371, JSONException -> 0x038a, SecurityException -> 0x03a3 }
            r0 = r17
            r0.JNIRemoveCacheData(r2)     // Catch:{ UnknownHostException -> 0x0241, SocketTimeoutException -> 0x033d, IOException -> 0x0371, JSONException -> 0x038a, SecurityException -> 0x03a3 }
            if (r10 == 0) goto L_0x0258
            r10.disconnect()
            goto L_0x0258
        L_0x02db:
            java.lang.String r2 = "URLRequest"
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ UnknownHostException -> 0x0241, SocketTimeoutException -> 0x033d, IOException -> 0x0371, JSONException -> 0x038a, SecurityException -> 0x03a3 }
            r3.<init>()     // Catch:{ UnknownHostException -> 0x0241, SocketTimeoutException -> 0x033d, IOException -> 0x0371, JSONException -> 0x038a, SecurityException -> 0x03a3 }
            java.lang.String r4 = "part"
            java.lang.StringBuilder r3 = r3.append(r4)     // Catch:{ UnknownHostException -> 0x0241, SocketTimeoutException -> 0x033d, IOException -> 0x0371, JSONException -> 0x038a, SecurityException -> 0x03a3 }
            r0 = r17
            int r4 = r0.mPart     // Catch:{ UnknownHostException -> 0x0241, SocketTimeoutException -> 0x033d, IOException -> 0x0371, JSONException -> 0x038a, SecurityException -> 0x03a3 }
            java.lang.StringBuilder r3 = r3.append(r4)     // Catch:{ UnknownHostException -> 0x0241, SocketTimeoutException -> 0x033d, IOException -> 0x0371, JSONException -> 0x038a, SecurityException -> 0x03a3 }
            java.lang.String r4 = " finished!"
            java.lang.StringBuilder r3 = r3.append(r4)     // Catch:{ UnknownHostException -> 0x0241, SocketTimeoutException -> 0x033d, IOException -> 0x0371, JSONException -> 0x038a, SecurityException -> 0x03a3 }
            java.lang.String r3 = r3.toString()     // Catch:{ UnknownHostException -> 0x0241, SocketTimeoutException -> 0x033d, IOException -> 0x0371, JSONException -> 0x038a, SecurityException -> 0x03a3 }
            android.util.Log.i(r2, r3)     // Catch:{ UnknownHostException -> 0x0241, SocketTimeoutException -> 0x033d, IOException -> 0x0371, JSONException -> 0x038a, SecurityException -> 0x03a3 }
            r0 = r17
            int r2 = r0.mPart     // Catch:{ UnknownHostException -> 0x0241, SocketTimeoutException -> 0x033d, IOException -> 0x0371, JSONException -> 0x038a, SecurityException -> 0x03a3 }
            int r2 = r2 + 1
            r0 = r17
            r0.mPart = r2     // Catch:{ UnknownHostException -> 0x0241, SocketTimeoutException -> 0x033d, IOException -> 0x0371, JSONException -> 0x038a, SecurityException -> 0x03a3 }
            r0 = r17
            long r2 = r0.mUploadDelegate     // Catch:{ UnknownHostException -> 0x0241, SocketTimeoutException -> 0x033d, IOException -> 0x0371, JSONException -> 0x038a, SecurityException -> 0x03a3 }
            r0 = r17
            int r4 = r0.mPart     // Catch:{ UnknownHostException -> 0x0241, SocketTimeoutException -> 0x033d, IOException -> 0x0371, JSONException -> 0x038a, SecurityException -> 0x03a3 }
            r0 = r17
            r0.JNISaveUploadPart(r2, r4)     // Catch:{ UnknownHostException -> 0x0241, SocketTimeoutException -> 0x033d, IOException -> 0x0371, JSONException -> 0x038a, SecurityException -> 0x03a3 }
            r0 = r17
            int r2 = r0.mState     // Catch:{ UnknownHostException -> 0x0241, SocketTimeoutException -> 0x033d, IOException -> 0x0371, JSONException -> 0x038a, SecurityException -> 0x03a3 }
            r3 = 1
            if (r2 != r3) goto L_0x031e
            r17.UploadProc()     // Catch:{ UnknownHostException -> 0x0241, SocketTimeoutException -> 0x033d, IOException -> 0x0371, JSONException -> 0x038a, SecurityException -> 0x03a3 }
        L_0x031e:
            if (r10 == 0) goto L_0x0258
            r10.disconnect()
            goto L_0x0258
        L_0x0325:
            r0 = r17
            int r3 = r0.mPart     // Catch:{ UnknownHostException -> 0x0241, SocketTimeoutException -> 0x033d, IOException -> 0x0371, JSONException -> 0x038a, SecurityException -> 0x03a3 }
            r0 = r17
            int r4 = r0.mPartCount     // Catch:{ UnknownHostException -> 0x0241, SocketTimeoutException -> 0x033d, IOException -> 0x0371, JSONException -> 0x038a, SecurityException -> 0x03a3 }
            int r4 = r4 + -1
            if (r3 != r4) goto L_0x02a2
            r2 = 0
            r0 = r17
            long r4 = r0.mUploadDelegate     // Catch:{ UnknownHostException -> 0x0241, SocketTimeoutException -> 0x033d, IOException -> 0x0371, JSONException -> 0x038a, SecurityException -> 0x03a3 }
            r0 = r17
            r0.JNIRemoveCacheData(r4)     // Catch:{ UnknownHostException -> 0x0241, SocketTimeoutException -> 0x033d, IOException -> 0x0371, JSONException -> 0x038a, SecurityException -> 0x03a3 }
            goto L_0x02a2
        L_0x033d:
            r2 = move-exception
            r2.printStackTrace()     // Catch:{ all -> 0x03d5 }
            r0 = r17
            long r3 = r0.mUploadDelegate     // Catch:{ all -> 0x03d5 }
            r5 = 2
            r0 = r17
            long r6 = r0.mFileSize     // Catch:{ all -> 0x03d5 }
            r2 = r17
            r2.JNITaskFinished(r3, r5, r6)     // Catch:{ all -> 0x03d5 }
            if (r10 == 0) goto L_0x0258
            r10.disconnect()
            goto L_0x0258
        L_0x0356:
            java.lang.String r2 = "URLRequest"
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ UnknownHostException -> 0x0241, SocketTimeoutException -> 0x033d, IOException -> 0x0371, JSONException -> 0x038a, SecurityException -> 0x03a3 }
            r4.<init>()     // Catch:{ UnknownHostException -> 0x0241, SocketTimeoutException -> 0x033d, IOException -> 0x0371, JSONException -> 0x038a, SecurityException -> 0x03a3 }
            java.lang.String r5 = "response code:"
            java.lang.StringBuilder r4 = r4.append(r5)     // Catch:{ UnknownHostException -> 0x0241, SocketTimeoutException -> 0x033d, IOException -> 0x0371, JSONException -> 0x038a, SecurityException -> 0x03a3 }
            java.lang.StringBuilder r3 = r4.append(r3)     // Catch:{ UnknownHostException -> 0x0241, SocketTimeoutException -> 0x033d, IOException -> 0x0371, JSONException -> 0x038a, SecurityException -> 0x03a3 }
            java.lang.String r3 = r3.toString()     // Catch:{ UnknownHostException -> 0x0241, SocketTimeoutException -> 0x033d, IOException -> 0x0371, JSONException -> 0x038a, SecurityException -> 0x03a3 }
            android.util.Log.e(r2, r3)     // Catch:{ UnknownHostException -> 0x0241, SocketTimeoutException -> 0x033d, IOException -> 0x0371, JSONException -> 0x038a, SecurityException -> 0x03a3 }
            r5 = 1
            goto L_0x02a3
        L_0x0371:
            r2 = move-exception
            r2.printStackTrace()     // Catch:{ all -> 0x03d5 }
            r0 = r17
            long r3 = r0.mUploadDelegate     // Catch:{ all -> 0x03d5 }
            r5 = 6
            r0 = r17
            long r6 = r0.mFileSize     // Catch:{ all -> 0x03d5 }
            r2 = r17
            r2.JNITaskFinished(r3, r5, r6)     // Catch:{ all -> 0x03d5 }
            if (r10 == 0) goto L_0x0258
            r10.disconnect()
            goto L_0x0258
        L_0x038a:
            r2 = move-exception
            r2.printStackTrace()     // Catch:{ all -> 0x03d5 }
            r0 = r17
            long r3 = r0.mUploadDelegate     // Catch:{ all -> 0x03d5 }
            r5 = 6
            r0 = r17
            long r6 = r0.mFileSize     // Catch:{ all -> 0x03d5 }
            r2 = r17
            r2.JNITaskFinished(r3, r5, r6)     // Catch:{ all -> 0x03d5 }
            if (r10 == 0) goto L_0x0258
            r10.disconnect()
            goto L_0x0258
        L_0x03a3:
            r2 = move-exception
            java.lang.String r3 = "URLRequest"
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x03d5 }
            r4.<init>()     // Catch:{ all -> 0x03d5 }
            java.lang.String r5 = "SecurityException error="
            java.lang.StringBuilder r4 = r4.append(r5)     // Catch:{ all -> 0x03d5 }
            java.lang.String r2 = r2.toString()     // Catch:{ all -> 0x03d5 }
            java.lang.StringBuilder r2 = r4.append(r2)     // Catch:{ all -> 0x03d5 }
            java.lang.String r2 = r2.toString()     // Catch:{ all -> 0x03d5 }
            android.util.Log.e(r3, r2)     // Catch:{ all -> 0x03d5 }
            r0 = r17
            long r3 = r0.mUploadDelegate     // Catch:{ all -> 0x03d5 }
            r5 = 7
            r0 = r17
            long r6 = r0.mFileSize     // Catch:{ all -> 0x03d5 }
            r2 = r17
            r2.JNITaskFinished(r3, r5, r6)     // Catch:{ all -> 0x03d5 }
            if (r10 == 0) goto L_0x0258
            r10.disconnect()
            goto L_0x0258
        L_0x03d5:
            r2 = move-exception
            if (r10 == 0) goto L_0x03db
            r10.disconnect()
        L_0x03db:
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.abase.URLRequest.UploadProc():void");
    }

    /* access modifiers changed from: private */
    public void addHeadersToConn(HttpURLConnection httpURLConnection) {
        if (httpURLConnection == null) {
            Log.e(TAG, "urlConn is null!");
            return;
        }
        for (String next : this.mHeaders.keySet()) {
            httpURLConnection.setRequestProperty(next, this.mHeaders.get(next));
        }
    }

    public static void init() {
        Log.i(TAG, "URLRequest init");
        try {
            nativeInit();
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    /* access modifiers changed from: private */
    public boolean initDownload(HttpURLConnection httpURLConnection) {
        if (httpURLConnection == null) {
            try {
                Log.e(TAG, "urlConn is null");
                httpURLConnection.disconnect();
                return false;
            } catch (UnknownHostException e) {
                Log.e(TAG, "UnknownHost");
                JNITaskFinished(this.mDownloadDelegate, 3, this.mFileSize);
                httpURLConnection.disconnect();
                return false;
            } catch (SocketTimeoutException e2) {
                Log.e(TAG, "SocketTimeoutException");
                JNITaskFinished(this.mDownloadDelegate, 2, this.mFileSize);
                httpURLConnection.disconnect();
                return false;
            } catch (Exception e3) {
                Exception exc = e3;
                JNITaskFinished(this.mDownloadDelegate, 1, this.mFileSize);
                exc.printStackTrace();
                httpURLConnection.disconnect();
                return false;
            } catch (Throwable th) {
                httpURLConnection.disconnect();
                throw th;
            }
        } else {
            this.mFileCurrentSize = 0;
            File file = new File(this.mFilePath);
            if (!file.exists()) {
                file.createNewFile();
            }
            RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rwd");
            this.mFileSize = (long) httpURLConnection.getContentLength();
            if (this.mFileSize == -1) {
                Log.i(TAG, "get fileSize Fail");
                randomAccessFile.close();
                httpURLConnection.disconnect();
                return false;
            }
            Log.i(TAG, UploaderTask.FILESIZE + this.mFileSize);
            randomAccessFile.setLength(this.mFileSize);
            JNITaskBegan(this.mDownloadDelegate, this.mFileSize);
            httpURLConnection.disconnect();
            return true;
        }
    }

    public static native void nativeInit();

    public static native void nativeRemoveCacheData(long j);

    public static native void nativeResponse(int i, long j, int i2, String str, String str2, String str3, byte[] bArr, String[] strArr);

    public static native void nativeSaveUploadPart(long j, int i);

    public static native void nativeTaskBegan(long j, long j2);

    public static native void nativeTaskFinished(long j, int i, long j2);

    public static native void nativeTaskProgress(long j, long j2, long j3);

    /* access modifiers changed from: private */
    public void uninit() {
        this.mState = 0;
        this.mFileCurrentSize = 0;
        this.mFileSize = 0;
    }

    public void addHead(String str, String str2) {
        this.mHeaders.put(str, str2);
    }

    public void cancel() {
        uninit();
    }

    public void downloadFile(String str) {
        this.mFilePath = str;
        if (this.mState != 1) {
            new Thread(new DownloadTask()).start();
        }
    }

    public void get() {
        new Thread(new RequestTask()).start();
    }

    public int initWithURL(String str, int i) {
        this.mUrlStr = str;
        this.timeout = i;
        return 0;
    }

    public void pause() {
        if (this.mState == 1) {
            this.mState = 2;
        }
    }

    public void post(byte[] bArr) {
        new Thread(new RequestTask(bArr)).start();
    }

    public void response2cpp(URLResponse uRLResponse, int i) {
        Log.i(TAG, "url[" + uRLResponse.URL + "]response2cpp with result :" + i);
        if (i != 0) {
            try {
                nativeResponse(i, this.delegate, 0, "", uRLResponse.URL, "", (byte[]) null, (String[]) null);
            } catch (Throwable th) {
                th.printStackTrace();
            }
        } else {
            ArrayList arrayList = new ArrayList();
            for (Map.Entry next : uRLResponse.headers.entrySet()) {
                String str = (String) next.getKey();
                String str2 = (String) next.getValue();
                if (!(str2 == null || str == null)) {
                    arrayList.add(str);
                    arrayList.add(str2);
                }
            }
            try {
                nativeResponse(i, this.delegate, uRLResponse.status, uRLResponse.statusMsg, uRLResponse.URL, uRLResponse.version, uRLResponse.body, (String[]) arrayList.toArray(new String[0]));
            } catch (Throwable th2) {
                th2.printStackTrace();
            }
        }
    }

    public void setBody(byte[] bArr) {
    }

    public void setDelegate(long j) {
        this.delegate = j;
    }

    public void setDownloadDelegate(long j) {
        this.mDownloadDelegate = j;
    }

    public void setUploadDelegate(long j) {
        this.mUploadDelegate = j;
    }

    public void uploadFile(String str, int i, int i2, int i3, String str2) {
        this.mFilePath = str;
        this.mFileSize = (long) i;
        this.mPart = i3;
        this.mPartCount = i2;
        this.mFileMD5 = str2;
        if (this.mState != 1) {
            new Thread(new UploadTask()).start();
        }
    }
}
