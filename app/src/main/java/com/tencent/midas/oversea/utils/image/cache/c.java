package com.tencent.midas.oversea.utils.image.cache;

import android.graphics.Bitmap;
import com.tencent.midas.oversea.comm.APLog;
import com.tencent.midas.oversea.utils.image.cache.APImageCache;

class c implements Runnable {
    final /* synthetic */ APImageCache a;

    c(APImageCache aPImageCache) {
        this.a = aPImageCache;
    }

    public void run() {
        APImageCache.ImageRef imageRef;
        while (true) {
            if (this.a.b.size() > 0) {
                synchronized (this) {
                    imageRef = (APImageCache.ImageRef) this.a.b.remove();
                }
                APLog.i("APImageCache", "mImageQueue url:" + imageRef.b);
                Bitmap a2 = APImageCache.b(imageRef.b, true);
                if (!(a2 == null || imageRef.b == null)) {
                    this.a.mMemoryCache.put(imageRef.b, a2);
                    if (this.a.mDiskCache != null) {
                        this.a.mDiskCache.put(imageRef.b, a2);
                    }
                }
                if (this.a.c != null) {
                    this.a.c.sendMessage(this.a.c.obtainMessage(0, imageRef));
                }
            } else {
                synchronized (this) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        while (true) {
        }
    }
}
