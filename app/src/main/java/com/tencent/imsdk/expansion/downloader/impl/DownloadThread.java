package com.tencent.imsdk.expansion.downloader.impl;

import android.content.Context;
import android.net.Proxy;
import android.os.Build;
import android.os.PowerManager;
import android.os.Process;
import com.amazonaws.services.s3.Headers;
import com.appsflyer.share.Constants;
import com.tencent.imsdk.expansion.downloader.Helpers;
import com.tencent.imsdk.expansion.downloader.IMLogger;
import com.tencent.imsdk.expansion.downloader.impl.DownloaderService;
import com.tencent.tp.a.h;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Locale;
import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.params.ConnRouteParams;
import org.apache.http.protocol.HTTP;

public class DownloadThread {
    private Context mContext;
    private final DownloadsDB mDB;
    private DownloadInfo mInfo;
    private final DownloadNotification mNotification;
    private DownloaderService mService;
    private String mUserAgent;

    public DownloadThread(DownloadInfo info, DownloaderService service, DownloadNotification notification) {
        this.mContext = service;
        this.mInfo = info;
        this.mService = service;
        this.mNotification = notification;
        this.mDB = DownloadsDB.getDB(service);
        this.mUserAgent = "APKXDL (Linux; U; Android " + Build.VERSION.RELEASE + ";" + Locale.getDefault().toString() + "; " + Build.DEVICE + Constants.URL_PATH_DELIMITER + Build.ID + h.b + service.getPackageName();
    }

    private String userAgent() {
        return this.mUserAgent;
    }

    private static class State {
        public boolean mCountRetry = false;
        public String mFilename;
        public boolean mGotData = false;
        public String mNewUri;
        public int mRedirectCount = 0;
        public String mRequestUri;
        public int mRetryAfter = 0;
        public FileOutputStream mStream;

        public State(DownloadInfo info, DownloaderService service) {
            this.mRedirectCount = info.mRedirectCount;
            this.mRequestUri = info.mUri;
            this.mFilename = service.generateTempSaveFileName(info.mFileName);
        }
    }

    private static class InnerState {
        public int mBytesNotified;
        public int mBytesSoFar;
        public int mBytesThisSession;
        public boolean mContinuingDownload;
        public String mHeaderContentDisposition;
        public String mHeaderContentLength;
        public String mHeaderContentLocation;
        public String mHeaderETag;
        public long mTimeLastNotification;

        private InnerState() {
            this.mBytesSoFar = 0;
            this.mBytesThisSession = 0;
            this.mContinuingDownload = false;
            this.mBytesNotified = 0;
            this.mTimeLastNotification = 0;
        }
    }

    private class StopRequest extends Throwable {
        private static final long serialVersionUID = 6338592678988347973L;
        public int mFinalStatus;

        public StopRequest(int finalStatus, String message) {
            super(message);
            this.mFinalStatus = finalStatus;
        }

        public StopRequest(int finalStatus, String message, Throwable throwable) {
            super(message, throwable);
            this.mFinalStatus = finalStatus;
        }
    }

    private class RetryDownload extends Throwable {
        private static final long serialVersionUID = 6196036036517540229L;

        private RetryDownload() {
        }
    }

    public HttpHost getPreferredHttpHost(Context context, String url) {
        String proxyHost;
        if (isLocalHost(url) || this.mService.isWiFi() || (proxyHost = Proxy.getHost(context)) == null) {
            return null;
        }
        return new HttpHost(proxyHost, Proxy.getPort(context), HttpHost.DEFAULT_SCHEME_NAME);
    }

    private static final boolean isLocalHost(String url) {
        if (url == null) {
            return false;
        }
        try {
            String host = URI.create(url).getHost();
            if (host == null) {
                return false;
            }
            if (host.equalsIgnoreCase("localhost") || host.equals("127.0.0.1") || host.equals("[::1]")) {
                return true;
            }
            return false;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    /* JADX INFO: finally extract failed */
    public void run() {
        HttpGet request;
        Process.setThreadPriority(10);
        State state = new State(this.mInfo, this.mService);
        AndroidHttpClient client = null;
        PowerManager.WakeLock wakeLock = null;
        try {
            wakeLock = ((PowerManager) this.mContext.getSystemService("power")).newWakeLock(1, IMLogger.TAG);
            wakeLock.acquire();
            IMLogger.v("initiating download for " + this.mInfo.mFileName);
            IMLogger.v(" at " + this.mInfo.mUri);
            client = AndroidHttpClient.newInstance(userAgent(), this.mContext);
            boolean finished = false;
            while (!finished) {
                IMLogger.v("initiating download for " + this.mInfo.mFileName);
                IMLogger.v("  at " + this.mInfo.mUri);
                ConnRouteParams.setDefaultProxy(client.getParams(), getPreferredHttpHost(this.mContext, state.mRequestUri));
                request = new HttpGet(state.mRequestUri);
                executeDownload(state, client, request);
                finished = true;
                request.abort();
            }
            IMLogger.v("download completed for " + this.mInfo.mFileName);
            IMLogger.v("  at " + this.mInfo.mUri);
            finalizeDestinationFile(state);
            if (wakeLock != null) {
                wakeLock.release();
            }
            if (client != null) {
                client.close();
            }
            cleanupDestination(state, 200);
            notifyDownloadCompleted(200, state.mCountRetry, state.mRetryAfter, state.mRedirectCount, state.mGotData, state.mFilename);
        } catch (RetryDownload e) {
            request.abort();
        } catch (StopRequest error) {
            try {
                IMLogger.w("Aborting request for download " + this.mInfo.mFileName + ": " + error.getMessage() + ", finalStatus : " + error.mFinalStatus);
                error.printStackTrace();
                int finalStatus = error.mFinalStatus;
                if (wakeLock != null) {
                    wakeLock.release();
                }
                if (client != null) {
                    client.close();
                }
                cleanupDestination(state, finalStatus);
                notifyDownloadCompleted(finalStatus, state.mCountRetry, state.mRetryAfter, state.mRedirectCount, state.mGotData, state.mFilename);
            } catch (Throwable th) {
                Throwable th2 = th;
                if (wakeLock != null) {
                    wakeLock.release();
                }
                if (client != null) {
                    client.close();
                }
                cleanupDestination(state, 491);
                notifyDownloadCompleted(491, state.mCountRetry, state.mRetryAfter, state.mRedirectCount, state.mGotData, state.mFilename);
                throw th2;
            }
        } catch (Throwable ex) {
            IMLogger.w("Exception for " + this.mInfo.mFileName + ": " + ex);
            if (wakeLock != null) {
                wakeLock.release();
            }
            if (client != null) {
                client.close();
            }
            cleanupDestination(state, 491);
            notifyDownloadCompleted(491, state.mCountRetry, state.mRetryAfter, state.mRedirectCount, state.mGotData, state.mFilename);
        }
    }

    private void executeDownload(State state, AndroidHttpClient client, HttpGet request) throws StopRequest, RetryDownload {
        InnerState innerState = new InnerState();
        checkPausedOrCanceled(state);
        setupDestinationFile(state, innerState);
        addRequestHeaders(innerState, request);
        checkConnectivity(state);
        this.mNotification.onDownloadStateChanged(3);
        HttpResponse response = sendRequest(state, client, request);
        handleExceptionalStatus(state, innerState, response);
        IMLogger.v("received response for " + this.mInfo.mUri);
        processResponseHeaders(state, innerState, response);
        InputStream entityStream = openResponseEntity(state, response);
        this.mNotification.onDownloadStateChanged(4);
        transferData(state, innerState, new byte[4096], entityStream);
    }

    private void checkConnectivity(State state) throws StopRequest {
        switch (this.mService.getNetworkAvailabilityState(this.mDB)) {
            case 2:
                throw new StopRequest(DownloaderService.STATUS_WAITING_FOR_NETWORK, "waiting for network to return");
            case 3:
                throw new StopRequest(DownloaderService.STATUS_QUEUED_FOR_WIFI, "waiting for wifi");
            case 5:
                throw new StopRequest(DownloaderService.STATUS_WAITING_FOR_NETWORK, "roaming is not allowed");
            case 6:
                throw new StopRequest(DownloaderService.STATUS_QUEUED_FOR_WIFI_OR_CELLULAR_PERMISSION, "waiting for wifi or for download over cellular to be authorized");
            default:
                return;
        }
    }

    private void transferData(State state, InnerState innerState, byte[] data, InputStream entityStream) throws StopRequest {
        while (true) {
            int bytesRead = readFromResponse(state, innerState, data, entityStream);
            if (bytesRead == -1) {
                handleEndOfStream(state, innerState);
                return;
            }
            state.mGotData = true;
            writeDataToDestination(state, data, bytesRead);
            innerState.mBytesSoFar += bytesRead;
            innerState.mBytesThisSession += bytesRead;
            reportProgress(state, innerState);
            checkPausedOrCanceled(state);
        }
    }

    private void finalizeDestinationFile(State state) throws StopRequest {
        syncDestination(state);
        String tempFilename = state.mFilename;
        String finalFilename = Helpers.generateSaveFileName(this.mService, this.mInfo.mFileName);
        if (!state.mFilename.equals(finalFilename)) {
            File startFile = new File(tempFilename);
            File destFile = new File(finalFilename);
            if (this.mInfo.mTotalBytes == -1 || this.mInfo.mCurrentBytes != this.mInfo.mTotalBytes) {
                throw new StopRequest(DownloaderService.STATUS_FILE_DELIVERED_INCORRECTLY, "file delivered with incorrect size. probably due to network not browser configured");
            } else if (!startFile.renameTo(destFile)) {
                throw new StopRequest(492, "unable to finalize destination file");
            }
        }
    }

    private void cleanupDestination(State state, int finalStatus) {
        closeDestination(state);
        if (state.mFilename != null && DownloaderService.isStatusError(finalStatus)) {
            new File(state.mFilename).delete();
            state.mFilename = null;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:17:0x007a A[SYNTHETIC, Splitter:B:17:0x007a] */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x00e1 A[SYNTHETIC, Splitter:B:27:0x00e1] */
    /* JADX WARNING: Removed duplicated region for block: B:37:0x0149 A[SYNTHETIC, Splitter:B:37:0x0149] */
    /* JADX WARNING: Removed duplicated region for block: B:47:0x01a5 A[SYNTHETIC, Splitter:B:47:0x01a5] */
    /* JADX WARNING: Removed duplicated region for block: B:55:0x01e7 A[SYNTHETIC, Splitter:B:55:0x01e7] */
    /* JADX WARNING: Removed duplicated region for block: B:76:? A[RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:79:? A[RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:82:? A[RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:85:? A[RETURN, SYNTHETIC] */
    /* JADX WARNING: Unknown top exception splitter block from list: {B:34:0x0121=Splitter:B:34:0x0121, B:14:0x0052=Splitter:B:14:0x0052, B:24:0x00b9=Splitter:B:24:0x00b9, B:44:0x0189=Splitter:B:44:0x0189} */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void syncDestination(com.tencent.imsdk.expansion.downloader.impl.DownloadThread.State r7) {
        /*
            r6 = this;
            r0 = 0
            java.io.FileOutputStream r1 = new java.io.FileOutputStream     // Catch:{ FileNotFoundException -> 0x0051, SyncFailedException -> 0x00b8, IOException -> 0x0120, RuntimeException -> 0x0188 }
            java.lang.String r3 = r7.mFilename     // Catch:{ FileNotFoundException -> 0x0051, SyncFailedException -> 0x00b8, IOException -> 0x0120, RuntimeException -> 0x0188 }
            r4 = 1
            r1.<init>(r3, r4)     // Catch:{ FileNotFoundException -> 0x0051, SyncFailedException -> 0x00b8, IOException -> 0x0120, RuntimeException -> 0x0188 }
            java.io.FileDescriptor r3 = r1.getFD()     // Catch:{ FileNotFoundException -> 0x0232, SyncFailedException -> 0x022e, IOException -> 0x022a, RuntimeException -> 0x0226, all -> 0x0223 }
            r3.sync()     // Catch:{ FileNotFoundException -> 0x0232, SyncFailedException -> 0x022e, IOException -> 0x022a, RuntimeException -> 0x0226, all -> 0x0223 }
            if (r1 == 0) goto L_0x0236
            r1.close()     // Catch:{ IOException -> 0x0017, RuntimeException -> 0x0034 }
            r0 = r1
        L_0x0016:
            return
        L_0x0017:
            r2 = move-exception
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = "IOException while closing synced file: "
            java.lang.StringBuilder r3 = r3.append(r4)
            java.lang.String r4 = r2.getMessage()
            java.lang.StringBuilder r3 = r3.append(r4)
            java.lang.String r3 = r3.toString()
            com.tencent.imsdk.expansion.downloader.IMLogger.w(r3)
            r0 = r1
            goto L_0x0016
        L_0x0034:
            r2 = move-exception
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = "exception while closing file: "
            java.lang.StringBuilder r3 = r3.append(r4)
            java.lang.String r4 = r2.getMessage()
            java.lang.StringBuilder r3 = r3.append(r4)
            java.lang.String r3 = r3.toString()
            com.tencent.imsdk.expansion.downloader.IMLogger.w(r3)
            r0 = r1
            goto L_0x0016
        L_0x0051:
            r2 = move-exception
        L_0x0052:
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x01e4 }
            r3.<init>()     // Catch:{ all -> 0x01e4 }
            java.lang.String r4 = "file "
            java.lang.StringBuilder r3 = r3.append(r4)     // Catch:{ all -> 0x01e4 }
            java.lang.String r4 = r7.mFilename     // Catch:{ all -> 0x01e4 }
            java.lang.StringBuilder r3 = r3.append(r4)     // Catch:{ all -> 0x01e4 }
            java.lang.String r4 = " not found: "
            java.lang.StringBuilder r3 = r3.append(r4)     // Catch:{ all -> 0x01e4 }
            java.lang.String r4 = r2.getMessage()     // Catch:{ all -> 0x01e4 }
            java.lang.StringBuilder r3 = r3.append(r4)     // Catch:{ all -> 0x01e4 }
            java.lang.String r3 = r3.toString()     // Catch:{ all -> 0x01e4 }
            com.tencent.imsdk.expansion.downloader.IMLogger.w(r3)     // Catch:{ all -> 0x01e4 }
            if (r0 == 0) goto L_0x0016
            r0.close()     // Catch:{ IOException -> 0x007e, RuntimeException -> 0x009b }
            goto L_0x0016
        L_0x007e:
            r2 = move-exception
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = "IOException while closing synced file: "
            java.lang.StringBuilder r3 = r3.append(r4)
            java.lang.String r4 = r2.getMessage()
            java.lang.StringBuilder r3 = r3.append(r4)
            java.lang.String r3 = r3.toString()
            com.tencent.imsdk.expansion.downloader.IMLogger.w(r3)
            goto L_0x0016
        L_0x009b:
            r2 = move-exception
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = "exception while closing file: "
            java.lang.StringBuilder r3 = r3.append(r4)
            java.lang.String r4 = r2.getMessage()
            java.lang.StringBuilder r3 = r3.append(r4)
            java.lang.String r3 = r3.toString()
            com.tencent.imsdk.expansion.downloader.IMLogger.w(r3)
            goto L_0x0016
        L_0x00b8:
            r2 = move-exception
        L_0x00b9:
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x01e4 }
            r3.<init>()     // Catch:{ all -> 0x01e4 }
            java.lang.String r4 = "file "
            java.lang.StringBuilder r3 = r3.append(r4)     // Catch:{ all -> 0x01e4 }
            java.lang.String r4 = r7.mFilename     // Catch:{ all -> 0x01e4 }
            java.lang.StringBuilder r3 = r3.append(r4)     // Catch:{ all -> 0x01e4 }
            java.lang.String r4 = " sync failed: "
            java.lang.StringBuilder r3 = r3.append(r4)     // Catch:{ all -> 0x01e4 }
            java.lang.String r4 = r2.getMessage()     // Catch:{ all -> 0x01e4 }
            java.lang.StringBuilder r3 = r3.append(r4)     // Catch:{ all -> 0x01e4 }
            java.lang.String r3 = r3.toString()     // Catch:{ all -> 0x01e4 }
            com.tencent.imsdk.expansion.downloader.IMLogger.w(r3)     // Catch:{ all -> 0x01e4 }
            if (r0 == 0) goto L_0x0016
            r0.close()     // Catch:{ IOException -> 0x00e6, RuntimeException -> 0x0103 }
            goto L_0x0016
        L_0x00e6:
            r2 = move-exception
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = "IOException while closing synced file: "
            java.lang.StringBuilder r3 = r3.append(r4)
            java.lang.String r4 = r2.getMessage()
            java.lang.StringBuilder r3 = r3.append(r4)
            java.lang.String r3 = r3.toString()
            com.tencent.imsdk.expansion.downloader.IMLogger.w(r3)
            goto L_0x0016
        L_0x0103:
            r2 = move-exception
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = "exception while closing file: "
            java.lang.StringBuilder r3 = r3.append(r4)
            java.lang.String r4 = r2.getMessage()
            java.lang.StringBuilder r3 = r3.append(r4)
            java.lang.String r3 = r3.toString()
            com.tencent.imsdk.expansion.downloader.IMLogger.w(r3)
            goto L_0x0016
        L_0x0120:
            r2 = move-exception
        L_0x0121:
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x01e4 }
            r3.<init>()     // Catch:{ all -> 0x01e4 }
            java.lang.String r4 = "IOException trying to sync "
            java.lang.StringBuilder r3 = r3.append(r4)     // Catch:{ all -> 0x01e4 }
            java.lang.String r4 = r7.mFilename     // Catch:{ all -> 0x01e4 }
            java.lang.StringBuilder r3 = r3.append(r4)     // Catch:{ all -> 0x01e4 }
            java.lang.String r4 = ": "
            java.lang.StringBuilder r3 = r3.append(r4)     // Catch:{ all -> 0x01e4 }
            java.lang.String r4 = r2.getMessage()     // Catch:{ all -> 0x01e4 }
            java.lang.StringBuilder r3 = r3.append(r4)     // Catch:{ all -> 0x01e4 }
            java.lang.String r3 = r3.toString()     // Catch:{ all -> 0x01e4 }
            com.tencent.imsdk.expansion.downloader.IMLogger.w(r3)     // Catch:{ all -> 0x01e4 }
            if (r0 == 0) goto L_0x0016
            r0.close()     // Catch:{ IOException -> 0x014e, RuntimeException -> 0x016b }
            goto L_0x0016
        L_0x014e:
            r2 = move-exception
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = "IOException while closing synced file: "
            java.lang.StringBuilder r3 = r3.append(r4)
            java.lang.String r4 = r2.getMessage()
            java.lang.StringBuilder r3 = r3.append(r4)
            java.lang.String r3 = r3.toString()
            com.tencent.imsdk.expansion.downloader.IMLogger.w(r3)
            goto L_0x0016
        L_0x016b:
            r2 = move-exception
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = "exception while closing file: "
            java.lang.StringBuilder r3 = r3.append(r4)
            java.lang.String r4 = r2.getMessage()
            java.lang.StringBuilder r3 = r3.append(r4)
            java.lang.String r3 = r3.toString()
            com.tencent.imsdk.expansion.downloader.IMLogger.w(r3)
            goto L_0x0016
        L_0x0188:
            r2 = move-exception
        L_0x0189:
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x01e4 }
            r3.<init>()     // Catch:{ all -> 0x01e4 }
            java.lang.String r4 = "exception while syncing file: "
            java.lang.StringBuilder r3 = r3.append(r4)     // Catch:{ all -> 0x01e4 }
            java.lang.String r4 = r2.getMessage()     // Catch:{ all -> 0x01e4 }
            java.lang.StringBuilder r3 = r3.append(r4)     // Catch:{ all -> 0x01e4 }
            java.lang.String r3 = r3.toString()     // Catch:{ all -> 0x01e4 }
            com.tencent.imsdk.expansion.downloader.IMLogger.w(r3)     // Catch:{ all -> 0x01e4 }
            if (r0 == 0) goto L_0x0016
            r0.close()     // Catch:{ IOException -> 0x01aa, RuntimeException -> 0x01c7 }
            goto L_0x0016
        L_0x01aa:
            r2 = move-exception
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = "IOException while closing synced file: "
            java.lang.StringBuilder r3 = r3.append(r4)
            java.lang.String r4 = r2.getMessage()
            java.lang.StringBuilder r3 = r3.append(r4)
            java.lang.String r3 = r3.toString()
            com.tencent.imsdk.expansion.downloader.IMLogger.w(r3)
            goto L_0x0016
        L_0x01c7:
            r2 = move-exception
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = "exception while closing file: "
            java.lang.StringBuilder r3 = r3.append(r4)
            java.lang.String r4 = r2.getMessage()
            java.lang.StringBuilder r3 = r3.append(r4)
            java.lang.String r3 = r3.toString()
            com.tencent.imsdk.expansion.downloader.IMLogger.w(r3)
            goto L_0x0016
        L_0x01e4:
            r3 = move-exception
        L_0x01e5:
            if (r0 == 0) goto L_0x01ea
            r0.close()     // Catch:{ IOException -> 0x01eb, RuntimeException -> 0x0207 }
        L_0x01ea:
            throw r3
        L_0x01eb:
            r2 = move-exception
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r5 = "IOException while closing synced file: "
            java.lang.StringBuilder r4 = r4.append(r5)
            java.lang.String r5 = r2.getMessage()
            java.lang.StringBuilder r4 = r4.append(r5)
            java.lang.String r4 = r4.toString()
            com.tencent.imsdk.expansion.downloader.IMLogger.w(r4)
            goto L_0x01ea
        L_0x0207:
            r2 = move-exception
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r5 = "exception while closing file: "
            java.lang.StringBuilder r4 = r4.append(r5)
            java.lang.String r5 = r2.getMessage()
            java.lang.StringBuilder r4 = r4.append(r5)
            java.lang.String r4 = r4.toString()
            com.tencent.imsdk.expansion.downloader.IMLogger.w(r4)
            goto L_0x01ea
        L_0x0223:
            r3 = move-exception
            r0 = r1
            goto L_0x01e5
        L_0x0226:
            r2 = move-exception
            r0 = r1
            goto L_0x0189
        L_0x022a:
            r2 = move-exception
            r0 = r1
            goto L_0x0121
        L_0x022e:
            r2 = move-exception
            r0 = r1
            goto L_0x00b9
        L_0x0232:
            r2 = move-exception
            r0 = r1
            goto L_0x0052
        L_0x0236:
            r0 = r1
            goto L_0x0016
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.imsdk.expansion.downloader.impl.DownloadThread.syncDestination(com.tencent.imsdk.expansion.downloader.impl.DownloadThread$State):void");
    }

    private void closeDestination(State state) {
        try {
            if (state.mStream != null) {
                state.mStream.close();
                state.mStream = null;
            }
        } catch (IOException ex) {
            IMLogger.v("exception when closing the file after download : " + ex);
        }
    }

    private void checkPausedOrCanceled(State state) throws StopRequest {
        if (this.mService.getControl() == 1) {
            switch (this.mService.getStatus()) {
                case DownloaderService.STATUS_PAUSED_BY_APP /*193*/:
                    throw new StopRequest(this.mService.getStatus(), "download paused");
                case 490:
                    throw new StopRequest(DownloaderService.STATUS_PAUSED_BY_APP, "download cancel");
                default:
                    return;
            }
        }
    }

    private void reportProgress(State state, InnerState innerState) {
        long now = System.currentTimeMillis();
        if (innerState.mBytesSoFar - innerState.mBytesNotified > 4096 && now - innerState.mTimeLastNotification > 1000) {
            this.mInfo.mCurrentBytes = (long) innerState.mBytesSoFar;
            this.mDB.updateDownloadCurrentBytes(this.mInfo);
            innerState.mBytesNotified = innerState.mBytesSoFar;
            innerState.mTimeLastNotification = now;
            long totalBytesSoFar = ((long) innerState.mBytesThisSession) + this.mService.mBytesSoFar;
            IMLogger.v("downloaded " + this.mInfo.mCurrentBytes + " out of " + this.mInfo.mTotalBytes);
            IMLogger.v("total " + totalBytesSoFar + " out of " + this.mService.mTotalLength);
            this.mService.notifyUpdateBytes(totalBytesSoFar);
        }
    }

    private void writeDataToDestination(State state, byte[] data, int bytesRead) throws StopRequest {
        try {
            if (state.mStream == null) {
                state.mStream = new FileOutputStream(state.mFilename, true);
            }
            state.mStream.write(data, 0, bytesRead);
            closeDestination(state);
        } catch (IOException ex) {
            if (!Helpers.isExternalMediaMounted()) {
                throw new StopRequest(499, "external media not mounted while writing destination file");
            } else if (Helpers.getAvailableBytes(Helpers.getFilesystemRoot(state.mFilename)) < ((long) bytesRead)) {
                throw new StopRequest(498, "insufficient space while writing destination file", ex);
            } else {
                throw new StopRequest(492, "while writing destination file: " + ex.toString(), ex);
            }
        }
    }

    private void handleEndOfStream(State state, InnerState innerState) throws StopRequest {
        this.mInfo.mCurrentBytes = (long) innerState.mBytesSoFar;
        this.mDB.updateDownload(this.mInfo);
        if (!((innerState.mHeaderContentLength == null || innerState.mBytesSoFar == Integer.parseInt(innerState.mHeaderContentLength)) ? false : true)) {
            return;
        }
        if (cannotResume(innerState)) {
            throw new StopRequest(489, "mismatched content length");
        }
        throw new StopRequest(getFinalStatusForHttpError(state), "closed socket before end of file");
    }

    private boolean cannotResume(InnerState innerState) {
        return innerState.mBytesSoFar > 0 && innerState.mHeaderETag == null;
    }

    private int readFromResponse(State state, InnerState innerState, byte[] data, InputStream entityStream) throws StopRequest {
        try {
            return entityStream.read(data);
        } catch (IOException ex) {
            logNetworkState();
            this.mInfo.mCurrentBytes = (long) innerState.mBytesSoFar;
            this.mDB.updateDownload(this.mInfo);
            if (cannotResume(innerState)) {
                throw new StopRequest(489, "while reading response: " + ex.toString() + ", can't resume interrupted download with no ETag", ex);
            }
            throw new StopRequest(getFinalStatusForHttpError(state), "while reading response: " + ex.toString(), ex);
        }
    }

    private InputStream openResponseEntity(State state, HttpResponse response) throws StopRequest {
        try {
            return response.getEntity().getContent();
        } catch (IOException ex) {
            logNetworkState();
            throw new StopRequest(getFinalStatusForHttpError(state), "while getting entity: " + ex.toString(), ex);
        }
    }

    private void logNetworkState() {
        IMLogger.i("Net " + (this.mService.getNetworkAvailabilityState(this.mDB) == 1 ? "Up" : "Down"));
    }

    private void processResponseHeaders(State state, InnerState innerState, HttpResponse response) throws StopRequest {
        if (!innerState.mContinuingDownload) {
            readResponseHeaders(state, innerState, response);
            try {
                state.mFilename = this.mService.generateSaveFile(this.mInfo.mFileName, this.mInfo.mTotalBytes);
                try {
                    state.mStream = new FileOutputStream(state.mFilename);
                } catch (FileNotFoundException exc) {
                    try {
                        if (new File(Helpers.getSaveFilePath(this.mService)).mkdirs()) {
                            state.mStream = new FileOutputStream(state.mFilename);
                        }
                    } catch (Exception e) {
                        throw new StopRequest(492, "while opening destination file: " + exc.toString(), exc);
                    }
                }
                IMLogger.v("writing " + this.mInfo.mUri + " to " + state.mFilename);
                updateDatabaseFromHeaders(state, innerState);
                checkConnectivity(state);
            } catch (DownloaderService.GenerateSaveFileError exc2) {
                throw new StopRequest(exc2.mStatus, exc2.mMessage);
            }
        }
    }

    private void updateDatabaseFromHeaders(State state, InnerState innerState) {
        this.mInfo.mETag = innerState.mHeaderETag;
        this.mDB.updateDownload(this.mInfo);
    }

    private void readResponseHeaders(State state, InnerState innerState, HttpResponse response) throws StopRequest {
        Header header = response.getFirstHeader("Content-Disposition");
        if (header != null) {
            innerState.mHeaderContentDisposition = header.getValue();
        }
        Header header2 = response.getFirstHeader("Content-Location");
        if (header2 != null) {
            innerState.mHeaderContentLocation = header2.getValue();
        }
        Header header3 = response.getFirstHeader(Headers.ETAG);
        if (header3 != null) {
            innerState.mHeaderETag = header3.getValue();
        }
        String headerTransferEncoding = null;
        Header header4 = response.getFirstHeader("Transfer-Encoding");
        if (header4 != null) {
            headerTransferEncoding = header4.getValue();
        }
        if (headerTransferEncoding == null) {
            Header header5 = response.getFirstHeader("Content-Length");
            if (header5 != null) {
                innerState.mHeaderContentLength = header5.getValue();
                long contentLength = Long.parseLong(innerState.mHeaderContentLength);
                if (!(contentLength == -1 || contentLength == this.mInfo.mTotalBytes)) {
                    IMLogger.e("Incorrect file size delivered.");
                }
            }
        } else {
            IMLogger.v("ignoring content-length because of xfer-encoding");
        }
        IMLogger.v("Content-Disposition: " + innerState.mHeaderContentDisposition);
        IMLogger.v("Content-Length: " + innerState.mHeaderContentLength);
        IMLogger.v("Content-Location: " + innerState.mHeaderContentLocation);
        IMLogger.v("ETag: " + innerState.mHeaderETag);
        IMLogger.v("Transfer-Encoding: " + headerTransferEncoding);
        if (innerState.mHeaderContentLength == null && (headerTransferEncoding == null || !headerTransferEncoding.equalsIgnoreCase(HTTP.CHUNK_CODING))) {
            throw new StopRequest(495, "can't know size of download, giving up");
        }
    }

    private void handleExceptionalStatus(State state, InnerState innerState, HttpResponse response) throws StopRequest, RetryDownload {
        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode == 503 && this.mInfo.mNumFailed < 5) {
            handleServiceUnavailable(state, response);
        }
        if (statusCode == 301 || statusCode == 302 || statusCode == 303 || statusCode == 307) {
            handleRedirect(state, response, statusCode);
        }
        if (statusCode != (innerState.mContinuingDownload ? 206 : 200)) {
            handleOtherStatus(state, innerState, statusCode);
        } else {
            state.mRedirectCount = 0;
        }
    }

    private void handleOtherStatus(State state, InnerState innerState, int statusCode) throws StopRequest {
        int finalStatus;
        if (DownloaderService.isStatusError(statusCode)) {
            finalStatus = statusCode;
        } else if (statusCode >= 300 && statusCode < 400) {
            finalStatus = 493;
        } else if (!innerState.mContinuingDownload || statusCode != 200) {
            finalStatus = 494;
        } else {
            finalStatus = 489;
        }
        throw new StopRequest(finalStatus, "http error " + statusCode);
    }

    private void handleRedirect(State state, HttpResponse response, int statusCode) throws StopRequest, RetryDownload {
        IMLogger.v("got HTTP redirect " + statusCode);
        if (state.mRedirectCount >= 5) {
            throw new StopRequest(497, "too many redirects");
        }
        Header header = response.getFirstHeader("Location");
        if (header != null) {
            IMLogger.v("Location :" + header.getValue());
            try {
                String newUri = new URI(this.mInfo.mUri).resolve(new URI(header.getValue())).toString();
                state.mRedirectCount++;
                state.mRequestUri = newUri;
                if (statusCode == 301 || statusCode == 303) {
                    state.mNewUri = newUri;
                }
                throw new RetryDownload();
            } catch (URISyntaxException e) {
                IMLogger.d("Couldn't resolve redirect URI " + header.getValue() + " for " + this.mInfo.mUri);
                throw new StopRequest(495, "Couldn't resolve redirect URI");
            }
        }
    }

    private void addRequestHeaders(InnerState innerState, HttpGet request) {
        if (innerState.mContinuingDownload) {
            if (innerState.mHeaderETag != null) {
                request.addHeader(Headers.GET_OBJECT_IF_MATCH, innerState.mHeaderETag);
            }
            request.addHeader("Range", "bytes=" + innerState.mBytesSoFar + "-");
        }
    }

    private void handleServiceUnavailable(State state, HttpResponse response) throws StopRequest {
        IMLogger.v("got HTTP response code 503");
        state.mCountRetry = true;
        Header header = response.getFirstHeader("Retry-After");
        if (header != null) {
            try {
                IMLogger.v("Retry-After :" + header.getValue());
                state.mRetryAfter = Integer.parseInt(header.getValue());
                if (state.mRetryAfter < 0) {
                    state.mRetryAfter = 0;
                } else {
                    if (state.mRetryAfter < 30) {
                        state.mRetryAfter = 30;
                    } else if (state.mRetryAfter > 86400) {
                        state.mRetryAfter = 86400;
                    }
                    state.mRetryAfter += Helpers.sRandom.nextInt(31);
                    state.mRetryAfter *= 1000;
                }
            } catch (NumberFormatException e) {
            }
        }
        throw new StopRequest(DownloaderService.STATUS_WAITING_TO_RETRY, "got 503 Service Unavailable, will retry later");
    }

    private HttpResponse sendRequest(State state, AndroidHttpClient client, HttpGet request) throws StopRequest {
        try {
            return client.execute(request);
        } catch (IllegalArgumentException ex) {
            throw new StopRequest(495, "while trying to execute request: " + ex.toString(), ex);
        } catch (IOException ex2) {
            logNetworkState();
            throw new StopRequest(getFinalStatusForHttpError(state), "while trying to execute request: " + ex2.toString(), ex2);
        }
    }

    private int getFinalStatusForHttpError(State state) {
        if (this.mService.getNetworkAvailabilityState(this.mDB) != 1) {
            return DownloaderService.STATUS_WAITING_FOR_NETWORK;
        }
        if (this.mInfo.mNumFailed < 5) {
            state.mCountRetry = true;
            return DownloaderService.STATUS_WAITING_TO_RETRY;
        }
        IMLogger.w("reached max retries for " + this.mInfo.mNumFailed);
        return 495;
    }

    private void setupDestinationFile(State state, InnerState innerState) throws StopRequest {
        if (state.mFilename != null) {
            if (!Helpers.isFilenameValid(state.mFilename)) {
                throw new StopRequest(492, "found invalid internal destination filename");
            }
            File f = new File(state.mFilename);
            if (f.exists()) {
                long fileLength = f.length();
                if (fileLength == 0) {
                    f.delete();
                    state.mFilename = null;
                } else if (this.mInfo.mETag == null) {
                    f.delete();
                    throw new StopRequest(489, "Trying to resume a download that can't be resumed");
                } else {
                    try {
                        state.mStream = new FileOutputStream(state.mFilename, true);
                        innerState.mBytesSoFar = (int) fileLength;
                        if (this.mInfo.mTotalBytes != -1) {
                            innerState.mHeaderContentLength = Long.toString(this.mInfo.mTotalBytes);
                        }
                        innerState.mHeaderETag = this.mInfo.mETag;
                        innerState.mContinuingDownload = true;
                    } catch (FileNotFoundException exc) {
                        throw new StopRequest(492, "while opening destination for resuming: " + exc.toString(), exc);
                    }
                }
            }
        }
        if (state.mStream != null) {
            closeDestination(state);
        }
    }

    private void notifyDownloadCompleted(int status, boolean countRetry, int retryAfter, int redirectCount, boolean gotData, String filename) {
        updateDownloadDatabase(status, countRetry, retryAfter, redirectCount, gotData, filename);
        if (DownloaderService.isStatusCompleted(status)) {
        }
    }

    private void updateDownloadDatabase(int status, boolean countRetry, int retryAfter, int redirectCount, boolean gotData, String filename) {
        this.mInfo.mStatus = status;
        this.mInfo.mRetryAfter = retryAfter;
        this.mInfo.mRedirectCount = redirectCount;
        this.mInfo.mLastMod = System.currentTimeMillis();
        if (!countRetry) {
            this.mInfo.mNumFailed = 0;
        } else if (gotData) {
            this.mInfo.mNumFailed = 1;
        } else {
            this.mInfo.mNumFailed++;
        }
        this.mDB.updateDownload(this.mInfo);
    }
}
