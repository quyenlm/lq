package com.google.android.gms.internal;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

final class zzavo implements zzavd {
    private /* synthetic */ zzavn zzave;

    zzavo(zzavn zzavn) {
        this.zzave = zzavn;
    }

    public final void zzc(Bitmap bitmap) {
        Bitmap bitmap2 = null;
        if (bitmap != null) {
            int width = bitmap.getWidth();
            int height = bitmap.getHeight();
            int i = (int) (((((float) width) * 9.0f) / 16.0f) + 0.5f);
            float f = (float) ((i - height) / 2);
            RectF rectF = new RectF(0.0f, f, (float) width, ((float) height) + f);
            Bitmap createBitmap = Bitmap.createBitmap(width, i, bitmap.getConfig());
            new Canvas(createBitmap).drawBitmap(bitmap, (Rect) null, rectF, (Paint) null);
            bitmap2 = createBitmap;
        }
        this.zzave.zza(bitmap2, 0);
    }
}
