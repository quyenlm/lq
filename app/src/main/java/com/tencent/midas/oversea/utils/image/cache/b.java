package com.tencent.midas.oversea.utils.image.cache;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

class b extends LruCache<String, Bitmap> {
    final /* synthetic */ APImageCache a;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    b(APImageCache aPImageCache, int i) {
        super(i);
        this.a = aPImageCache;
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public int sizeOf(String str, Bitmap bitmap) {
        return bitmap.getRowBytes() * bitmap.getHeight();
    }
}
