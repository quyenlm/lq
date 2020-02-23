package com.tencent.midas.oversea.utils.image.cache;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.util.LruCache;
import android.text.TextUtils;
import com.tencent.midas.oversea.comm.APAppDataInterface;
import com.tencent.midas.oversea.comm.APLog;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class APImageCacheSync {
    private static volatile APImageCacheSync a = null;
    public APDiskLruCache mDiskCache;
    public LruCache<String, Bitmap> mMemoryCache;

    private APImageCacheSync(Context context) {
        int memoryClass = ((ActivityManager) context.getSystemService("activity")).getMemoryClass();
        this.mMemoryCache = new e(this, ((memoryClass > 32 ? 32 : memoryClass) * 1048576) / 8);
        this.mDiskCache = APDiskLruCache.openCache(context, APDiskLruCache.getDiskCacheDir(context, APAppDataInterface.singleton().getOfferid() + "_img"), 2097152);
    }

    private static Bitmap a(String str) {
        InputStream inputStream;
        Bitmap bitmap;
        InputStream inputStream2 = null;
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        try {
            HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(str).openConnection();
            httpURLConnection.setDoInput(true);
            httpURLConnection.connect();
            inputStream = httpURLConnection.getInputStream();
            if (inputStream != null) {
                try {
                    if (httpURLConnection.getResponseCode() == 200) {
                        bitmap = BitmapFactory.decodeStream(inputStream);
                        inputStream.close();
                        return bitmap;
                    }
                } catch (IOException e) {
                    e = e;
                    try {
                        e.printStackTrace();
                        try {
                            inputStream.close();
                            bitmap = null;
                        } catch (Exception e2) {
                            bitmap = null;
                        }
                        return bitmap;
                    } catch (Throwable th) {
                        th = th;
                        inputStream2 = inputStream;
                        try {
                            inputStream2.close();
                        } catch (Exception e3) {
                        }
                        throw th;
                    }
                }
            }
            bitmap = null;
            try {
                inputStream.close();
            } catch (Exception e4) {
            }
        } catch (IOException e5) {
            e = e5;
            inputStream = null;
        } catch (Throwable th2) {
            th = th2;
            inputStream2.close();
            throw th;
        }
        return bitmap;
    }

    private static Bitmap a(String str, boolean z) {
        int i = 0;
        Bitmap a2 = a(str);
        while (i < 3 && a2 == null) {
            i++;
            a2 = a(str);
        }
        return a2;
    }

    public static APImageCacheSync getInstance(Context context) {
        if (a == null) {
            synchronized (APImageCacheSync.class) {
                if (a == null) {
                    a = new APImageCacheSync(context);
                }
            }
        }
        return a;
    }

    public Bitmap getImage(String str) {
        Bitmap bitmap;
        if (str == null || str.equals("")) {
            return null;
        }
        Bitmap bitmap2 = this.mMemoryCache.get(str);
        if (bitmap2 != null) {
            APLog.i("APImageCacheSync", "mMemoryCache got");
            return bitmap2;
        } else if (this.mDiskCache == null || (bitmap = this.mDiskCache.get(str)) == null) {
            APLog.i("APImageCache", "mImage url:" + str);
            Bitmap a2 = a(str, true);
            if (a2 == null || str == null) {
                return a2;
            }
            APLog.i("APImageCacheSync", "net got");
            this.mMemoryCache.put(str, a2);
            if (this.mDiskCache == null) {
                return a2;
            }
            this.mDiskCache.put(str, a2);
            return a2;
        } else {
            APLog.i("APImageCacheSync", "mDiskCache got");
            if (this.mMemoryCache.get(str) != null) {
                return bitmap;
            }
            this.mMemoryCache.put(str, bitmap);
            return bitmap;
        }
    }
}
