package com.facebook.internal;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import com.facebook.internal.WorkQueue;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class ImageDownloader {
    private static final int CACHE_READ_QUEUE_MAX_CONCURRENT = 2;
    private static final int DOWNLOAD_QUEUE_MAX_CONCURRENT = 8;
    private static WorkQueue cacheReadQueue = new WorkQueue(2);
    private static WorkQueue downloadQueue = new WorkQueue(8);
    private static Handler handler;
    private static final Map<RequestKey, DownloaderContext> pendingRequests = new HashMap();

    public static void downloadAsync(ImageRequest request) {
        if (request != null) {
            RequestKey key = new RequestKey(request.getImageUri(), request.getCallerTag());
            synchronized (pendingRequests) {
                DownloaderContext downloaderContext = pendingRequests.get(key);
                if (downloaderContext != null) {
                    downloaderContext.request = request;
                    downloaderContext.isCancelled = false;
                    downloaderContext.workItem.moveToFront();
                } else {
                    enqueueCacheRead(request, key, request.isCachedRedirectAllowed());
                }
            }
        }
    }

    public static boolean cancelRequest(ImageRequest request) {
        boolean cancelled = false;
        RequestKey key = new RequestKey(request.getImageUri(), request.getCallerTag());
        synchronized (pendingRequests) {
            DownloaderContext downloaderContext = pendingRequests.get(key);
            if (downloaderContext != null) {
                cancelled = true;
                if (downloaderContext.workItem.cancel()) {
                    pendingRequests.remove(key);
                } else {
                    downloaderContext.isCancelled = true;
                }
            }
        }
        return cancelled;
    }

    public static void prioritizeRequest(ImageRequest request) {
        RequestKey key = new RequestKey(request.getImageUri(), request.getCallerTag());
        synchronized (pendingRequests) {
            DownloaderContext downloaderContext = pendingRequests.get(key);
            if (downloaderContext != null) {
                downloaderContext.workItem.moveToFront();
            }
        }
    }

    public static void clearCache(Context context) {
        ImageResponseCache.clearCache(context);
        UrlRedirectCache.clearCache();
    }

    private static void enqueueCacheRead(ImageRequest request, RequestKey key, boolean allowCachedRedirects) {
        enqueueRequest(request, key, cacheReadQueue, new CacheReadWorkItem(request.getContext(), key, allowCachedRedirects));
    }

    private static void enqueueDownload(ImageRequest request, RequestKey key) {
        enqueueRequest(request, key, downloadQueue, new DownloadImageWorkItem(request.getContext(), key));
    }

    private static void enqueueRequest(ImageRequest request, RequestKey key, WorkQueue workQueue, Runnable workItem) {
        synchronized (pendingRequests) {
            DownloaderContext downloaderContext = new DownloaderContext();
            downloaderContext.request = request;
            pendingRequests.put(key, downloaderContext);
            downloaderContext.workItem = workQueue.addActiveWorkItem(workItem);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:4:0x000a, code lost:
        r1 = r6.request;
        r5 = r1.getCallback();
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static void issueResponse(com.facebook.internal.ImageDownloader.RequestKey r8, java.lang.Exception r9, android.graphics.Bitmap r10, boolean r11) {
        /*
            com.facebook.internal.ImageDownloader$DownloaderContext r6 = removePendingRequest(r8)
            if (r6 == 0) goto L_0x0021
            boolean r0 = r6.isCancelled
            if (r0 != 0) goto L_0x0021
            com.facebook.internal.ImageRequest r1 = r6.request
            com.facebook.internal.ImageRequest$Callback r5 = r1.getCallback()
            if (r5 == 0) goto L_0x0021
            android.os.Handler r7 = getHandler()
            com.facebook.internal.ImageDownloader$1 r0 = new com.facebook.internal.ImageDownloader$1
            r2 = r9
            r3 = r11
            r4 = r10
            r0.<init>(r1, r2, r3, r4, r5)
            r7.post(r0)
        L_0x0021:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.internal.ImageDownloader.issueResponse(com.facebook.internal.ImageDownloader$RequestKey, java.lang.Exception, android.graphics.Bitmap, boolean):void");
    }

    /* access modifiers changed from: private */
    public static void readFromCache(RequestKey key, Context context, boolean allowCachedRedirects) {
        Uri redirectUri;
        InputStream cachedStream = null;
        boolean isCachedRedirect = false;
        if (allowCachedRedirects && (redirectUri = UrlRedirectCache.getRedirectedUri(key.uri)) != null) {
            cachedStream = ImageResponseCache.getCachedImageStream(redirectUri, context);
            isCachedRedirect = cachedStream != null;
        }
        if (!isCachedRedirect) {
            cachedStream = ImageResponseCache.getCachedImageStream(key.uri, context);
        }
        if (cachedStream != null) {
            Bitmap bitmap = BitmapFactory.decodeStream(cachedStream);
            Utility.closeQuietly(cachedStream);
            issueResponse(key, (Exception) null, bitmap, isCachedRedirect);
            return;
        }
        DownloaderContext downloaderContext = removePendingRequest(key);
        if (downloaderContext != null && !downloaderContext.isCancelled) {
            enqueueDownload(downloaderContext.request, key);
        }
    }

    /* JADX WARNING: type inference failed for: r16v4, types: [java.net.URLConnection] */
    /* access modifiers changed from: private */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void download(com.facebook.internal.ImageDownloader.RequestKey r19, android.content.Context r20) {
        /*
            r5 = 0
            r14 = 0
            r8 = 0
            r2 = 0
            r10 = 1
            java.net.URL r15 = new java.net.URL     // Catch:{ IOException -> 0x0058, all -> 0x00d7 }
            r0 = r19
            android.net.Uri r0 = r0.uri     // Catch:{ IOException -> 0x0058, all -> 0x00d7 }
            r16 = r0
            java.lang.String r16 = r16.toString()     // Catch:{ IOException -> 0x0058, all -> 0x00d7 }
            r15.<init>(r16)     // Catch:{ IOException -> 0x0058, all -> 0x00d7 }
            java.net.URLConnection r16 = r15.openConnection()     // Catch:{ IOException -> 0x0058, all -> 0x00d7 }
            r0 = r16
            java.net.HttpURLConnection r0 = (java.net.HttpURLConnection) r0     // Catch:{ IOException -> 0x0058, all -> 0x00d7 }
            r5 = r0
            r16 = 0
            r0 = r16
            r5.setInstanceFollowRedirects(r0)     // Catch:{ IOException -> 0x0058, all -> 0x00d7 }
            int r16 = r5.getResponseCode()     // Catch:{ IOException -> 0x0058, all -> 0x00d7 }
            switch(r16) {
                case 200: goto L_0x00b5;
                case 301: goto L_0x006c;
                case 302: goto L_0x006c;
                default: goto L_0x002b;
            }     // Catch:{ IOException -> 0x0058, all -> 0x00d7 }
        L_0x002b:
            java.io.InputStream r14 = r5.getErrorStream()     // Catch:{ IOException -> 0x0058, all -> 0x00d7 }
            java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x0058, all -> 0x00d7 }
            r9.<init>()     // Catch:{ IOException -> 0x0058, all -> 0x00d7 }
            if (r14 == 0) goto L_0x00cf
            java.io.InputStreamReader r11 = new java.io.InputStreamReader     // Catch:{ IOException -> 0x0058, all -> 0x00d7 }
            r11.<init>(r14)     // Catch:{ IOException -> 0x0058, all -> 0x00d7 }
            r16 = 128(0x80, float:1.794E-43)
            r0 = r16
            char[] r3 = new char[r0]     // Catch:{ IOException -> 0x0058, all -> 0x00d7 }
        L_0x0041:
            r16 = 0
            int r0 = r3.length     // Catch:{ IOException -> 0x0058, all -> 0x00d7 }
            r17 = r0
            r0 = r16
            r1 = r17
            int r4 = r11.read(r3, r0, r1)     // Catch:{ IOException -> 0x0058, all -> 0x00d7 }
            if (r4 <= 0) goto L_0x00c0
            r16 = 0
            r0 = r16
            r9.append(r3, r0, r4)     // Catch:{ IOException -> 0x0058, all -> 0x00d7 }
            goto L_0x0041
        L_0x0058:
            r7 = move-exception
            r8 = r7
            com.facebook.internal.Utility.closeQuietly(r14)
            com.facebook.internal.Utility.disconnectQuietly(r5)
        L_0x0060:
            if (r10 == 0) goto L_0x006b
            r16 = 0
            r0 = r19
            r1 = r16
            issueResponse(r0, r8, r2, r1)
        L_0x006b:
            return
        L_0x006c:
            r10 = 0
            java.lang.String r16 = "location"
            r0 = r16
            java.lang.String r12 = r5.getHeaderField(r0)     // Catch:{ IOException -> 0x0058, all -> 0x00d7 }
            boolean r16 = com.facebook.internal.Utility.isNullOrEmpty((java.lang.String) r12)     // Catch:{ IOException -> 0x0058, all -> 0x00d7 }
            if (r16 != 0) goto L_0x00ae
            android.net.Uri r13 = android.net.Uri.parse(r12)     // Catch:{ IOException -> 0x0058, all -> 0x00d7 }
            r0 = r19
            android.net.Uri r0 = r0.uri     // Catch:{ IOException -> 0x0058, all -> 0x00d7 }
            r16 = r0
            r0 = r16
            com.facebook.internal.UrlRedirectCache.cacheUriRedirect(r0, r13)     // Catch:{ IOException -> 0x0058, all -> 0x00d7 }
            com.facebook.internal.ImageDownloader$DownloaderContext r6 = removePendingRequest(r19)     // Catch:{ IOException -> 0x0058, all -> 0x00d7 }
            if (r6 == 0) goto L_0x00ae
            boolean r0 = r6.isCancelled     // Catch:{ IOException -> 0x0058, all -> 0x00d7 }
            r16 = r0
            if (r16 != 0) goto L_0x00ae
            com.facebook.internal.ImageRequest r0 = r6.request     // Catch:{ IOException -> 0x0058, all -> 0x00d7 }
            r16 = r0
            com.facebook.internal.ImageDownloader$RequestKey r17 = new com.facebook.internal.ImageDownloader$RequestKey     // Catch:{ IOException -> 0x0058, all -> 0x00d7 }
            r0 = r19
            java.lang.Object r0 = r0.tag     // Catch:{ IOException -> 0x0058, all -> 0x00d7 }
            r18 = r0
            r0 = r17
            r1 = r18
            r0.<init>(r13, r1)     // Catch:{ IOException -> 0x0058, all -> 0x00d7 }
            r18 = 0
            enqueueCacheRead(r16, r17, r18)     // Catch:{ IOException -> 0x0058, all -> 0x00d7 }
        L_0x00ae:
            com.facebook.internal.Utility.closeQuietly(r14)
            com.facebook.internal.Utility.disconnectQuietly(r5)
            goto L_0x0060
        L_0x00b5:
            r0 = r20
            java.io.InputStream r14 = com.facebook.internal.ImageResponseCache.interceptAndCacheImageStream(r0, r5)     // Catch:{ IOException -> 0x0058, all -> 0x00d7 }
            android.graphics.Bitmap r2 = android.graphics.BitmapFactory.decodeStream(r14)     // Catch:{ IOException -> 0x0058, all -> 0x00d7 }
            goto L_0x00ae
        L_0x00c0:
            com.facebook.internal.Utility.closeQuietly(r11)     // Catch:{ IOException -> 0x0058, all -> 0x00d7 }
        L_0x00c3:
            com.facebook.FacebookException r8 = new com.facebook.FacebookException     // Catch:{ IOException -> 0x0058, all -> 0x00d7 }
            java.lang.String r16 = r9.toString()     // Catch:{ IOException -> 0x0058, all -> 0x00d7 }
            r0 = r16
            r8.<init>((java.lang.String) r0)     // Catch:{ IOException -> 0x0058, all -> 0x00d7 }
            goto L_0x00ae
        L_0x00cf:
            java.lang.String r16 = "Unexpected error while downloading an image."
            r0 = r16
            r9.append(r0)     // Catch:{ IOException -> 0x0058, all -> 0x00d7 }
            goto L_0x00c3
        L_0x00d7:
            r16 = move-exception
            com.facebook.internal.Utility.closeQuietly(r14)
            com.facebook.internal.Utility.disconnectQuietly(r5)
            throw r16
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.internal.ImageDownloader.download(com.facebook.internal.ImageDownloader$RequestKey, android.content.Context):void");
    }

    private static synchronized Handler getHandler() {
        Handler handler2;
        synchronized (ImageDownloader.class) {
            if (handler == null) {
                handler = new Handler(Looper.getMainLooper());
            }
            handler2 = handler;
        }
        return handler2;
    }

    private static DownloaderContext removePendingRequest(RequestKey key) {
        DownloaderContext remove;
        synchronized (pendingRequests) {
            remove = pendingRequests.remove(key);
        }
        return remove;
    }

    private static class RequestKey {
        private static final int HASH_MULTIPLIER = 37;
        private static final int HASH_SEED = 29;
        Object tag;
        Uri uri;

        RequestKey(Uri url, Object tag2) {
            this.uri = url;
            this.tag = tag2;
        }

        public int hashCode() {
            return ((this.uri.hashCode() + 1073) * 37) + this.tag.hashCode();
        }

        public boolean equals(Object o) {
            if (o == null || !(o instanceof RequestKey)) {
                return false;
            }
            RequestKey compareTo = (RequestKey) o;
            return compareTo.uri == this.uri && compareTo.tag == this.tag;
        }
    }

    private static class DownloaderContext {
        boolean isCancelled;
        ImageRequest request;
        WorkQueue.WorkItem workItem;

        private DownloaderContext() {
        }
    }

    private static class CacheReadWorkItem implements Runnable {
        private boolean allowCachedRedirects;
        private Context context;
        private RequestKey key;

        CacheReadWorkItem(Context context2, RequestKey key2, boolean allowCachedRedirects2) {
            this.context = context2;
            this.key = key2;
            this.allowCachedRedirects = allowCachedRedirects2;
        }

        public void run() {
            ImageDownloader.readFromCache(this.key, this.context, this.allowCachedRedirects);
        }
    }

    private static class DownloadImageWorkItem implements Runnable {
        private Context context;
        private RequestKey key;

        DownloadImageWorkItem(Context context2, RequestKey key2) {
            this.context = context2;
            this.key = key2;
        }

        public void run() {
            ImageDownloader.download(this.key, this.context);
        }
    }
}
