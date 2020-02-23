package com.tencent.midas.oversea.utils.image.cache;

import android.os.Handler;
import android.os.Message;
import com.tencent.midas.oversea.utils.image.cache.APImageCache;

class d extends Handler {
    final /* synthetic */ APImageCache a;

    d(APImageCache aPImageCache) {
        this.a = aPImageCache;
    }

    public void handleMessage(Message message) {
        if (message != null) {
            switch (message.what) {
                case 0:
                    if ((message.obj instanceof APImageCache.ImageRef) && message.obj != null) {
                        APImageCache.ImageRef imageRef = (APImageCache.ImageRef) message.obj;
                        this.a.a(imageRef.a, imageRef.d, imageRef.e, this.a.mMemoryCache.get(imageRef.b), true);
                        return;
                    }
                    return;
                default:
                    return;
            }
        }
    }
}
