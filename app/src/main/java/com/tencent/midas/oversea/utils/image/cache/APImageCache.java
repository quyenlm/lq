package com.tencent.midas.oversea.utils.image.cache;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.util.LruCache;
import android.text.TextUtils;
import android.widget.ImageView;
import com.tencent.midas.oversea.comm.APAppDataInterface;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Executors;

public class APImageCache {
    private static volatile APImageCache a = null;
    /* access modifiers changed from: private */
    public Queue<ImageRef> b = new LinkedList();
    /* access modifiers changed from: private */
    public Handler c = new d(this);
    public APDiskLruCache mDiskCache;
    public LruCache<String, Bitmap> mMemoryCache;
    public Runnable runnable = new c(this);

    public class ImageRef {
        ImageView a = null;
        String b = "";
        int c = 0;
        int d = 0;
        int e = 0;

        ImageRef(ImageView imageView, String str, int i, int i2, int i3) {
            this.a = imageView;
            this.b = str;
            this.c = i;
            this.d = i2;
            this.e = i3;
        }
    }

    private APImageCache(Context context) {
        int memoryClass = ((ActivityManager) context.getSystemService("activity")).getMemoryClass();
        this.mMemoryCache = new b(this, ((memoryClass > 32 ? 32 : memoryClass) * 1048576) / 8);
        this.mDiskCache = APDiskLruCache.openCache(context, APDiskLruCache.getDiskCacheDir(context, APAppDataInterface.singleton().getOfferid()), 2097152);
    }

    private Bitmap a(Bitmap bitmap, int i, int i2) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Matrix matrix = new Matrix();
        matrix.postScale(((float) i) / ((float) width), ((float) i2) / ((float) height));
        return Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
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

    /* access modifiers changed from: private */
    public void a(ImageView imageView, int i, int i2, Bitmap bitmap, boolean z) {
        if (i > 0 && i2 > 0) {
            bitmap = a(bitmap, i, i2);
        }
        if (z) {
            TransitionDrawable transitionDrawable = new TransitionDrawable(new Drawable[]{new ColorDrawable(17170445), new BitmapDrawable(bitmap)});
            transitionDrawable.setCrossFadeEnabled(true);
            imageView.setImageDrawable(transitionDrawable);
            transitionDrawable.startTransition(300);
            return;
        }
        imageView.setImageBitmap(bitmap);
    }

    /* access modifiers changed from: private */
    public static Bitmap b(String str, boolean z) {
        int i = 0;
        Bitmap a2 = a(str);
        while (i < 3 && a2 == null) {
            i++;
            a2 = a(str);
        }
        return a2;
    }

    public static APImageCache getInstance(Context context) {
        if (Looper.myLooper() != Looper.getMainLooper()) {
            throw new RuntimeException("Cannot instantiate outside UI thread.");
        }
        if (a == null) {
            synchronized (APImageCache.class) {
                if (a == null) {
                    a = new APImageCache(context);
                    a.intiThread();
                }
            }
        }
        return a;
    }

    public void displayImage(ImageView imageView, int i, int i2, String str, int i3) {
        Bitmap bitmap;
        if (imageView != null) {
            if (i3 > 0) {
                if (imageView.getBackground() == null) {
                    imageView.setBackgroundResource(i3);
                }
                imageView.setImageDrawable((Drawable) null);
            }
            if (str != null && !str.equals("")) {
                Bitmap bitmap2 = this.mMemoryCache.get(str);
                if (bitmap2 != null) {
                    a(imageView, i, i2, bitmap2, false);
                } else if (this.mDiskCache == null || (bitmap = this.mDiskCache.get(str)) == null) {
                    queueImage(new ImageRef(imageView, str, i3, i, i2));
                } else {
                    if (this.mMemoryCache.get(str) == null) {
                        this.mMemoryCache.put(str, bitmap);
                    }
                    a(imageView, i, i2, bitmap, false);
                }
            }
        }
    }

    public void intiThread() {
        Executors.newSingleThreadExecutor().submit(this.runnable);
    }

    public void queueImage(ImageRef imageRef) {
        synchronized (this) {
            this.b.add(imageRef);
        }
        synchronized (this.runnable) {
            this.runnable.notify();
        }
    }
}
