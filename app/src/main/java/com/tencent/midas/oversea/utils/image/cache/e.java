package com.tencent.midas.oversea.utils.image.cache;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

class e extends LruCache<String, Bitmap> {
    final /* synthetic */ APImageCacheSync a;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    e(APImageCacheSync aPImageCacheSync, int i) {
        super(i);
        this.a = aPImageCacheSync;
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public int sizeOf(String str, Bitmap bitmap) {
        return bitmap.getRowBytes() * bitmap.getHeight();
    }
}
