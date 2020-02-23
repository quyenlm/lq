package com.facebook.internal;

import android.content.Context;
import android.net.Uri;
import com.facebook.LoggingBehavior;
import com.facebook.internal.FileLruCache;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;

class ImageResponseCache {
    static final String TAG = ImageResponseCache.class.getSimpleName();
    private static FileLruCache imageCache;

    ImageResponseCache() {
    }

    static synchronized FileLruCache getCache(Context context) throws IOException {
        FileLruCache fileLruCache;
        synchronized (ImageResponseCache.class) {
            if (imageCache == null) {
                imageCache = new FileLruCache(TAG, new FileLruCache.Limits());
            }
            fileLruCache = imageCache;
        }
        return fileLruCache;
    }

    static InputStream getCachedImageStream(Uri uri, Context context) {
        if (uri == null || !isCDNURL(uri)) {
            return null;
        }
        try {
            return getCache(context).get(uri.toString());
        } catch (IOException e) {
            Logger.log(LoggingBehavior.CACHE, 5, TAG, e.toString());
            return null;
        }
    }

    static InputStream interceptAndCacheImageStream(Context context, HttpURLConnection connection) throws IOException {
        if (connection.getResponseCode() != 200) {
            return null;
        }
        Uri uri = Uri.parse(connection.getURL().toString());
        InputStream stream = connection.getInputStream();
        try {
            if (isCDNURL(uri)) {
                return getCache(context).interceptAndPut(uri.toString(), new BufferedHttpInputStream(stream, connection));
            }
            return stream;
        } catch (IOException e) {
            return stream;
        }
    }

    private static boolean isCDNURL(Uri uri) {
        if (uri != null) {
            String uriHost = uri.getHost();
            if (uriHost.endsWith("fbcdn.net")) {
                return true;
            }
            if (!uriHost.startsWith("fbcdn") || !uriHost.endsWith("akamaihd.net")) {
                return false;
            }
            return true;
        }
        return false;
    }

    static void clearCache(Context context) {
        try {
            getCache(context).clearCache();
        } catch (IOException e) {
            Logger.log(LoggingBehavior.CACHE, 5, TAG, "clearCache failed " + e.getMessage());
        }
    }

    private static class BufferedHttpInputStream extends BufferedInputStream {
        HttpURLConnection connection;

        BufferedHttpInputStream(InputStream stream, HttpURLConnection connection2) {
            super(stream, 8192);
            this.connection = connection2;
        }

        public void close() throws IOException {
            super.close();
            Utility.disconnectQuietly(this.connection);
        }
    }
}
