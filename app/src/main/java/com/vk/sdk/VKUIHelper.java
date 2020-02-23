package com.vk.sdk;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;

public class VKUIHelper {
    private static Context sApplicationContext;

    static void setApplicationContext(Context appContext) {
        if (appContext != null) {
            sApplicationContext = appContext.getApplicationContext();
        }
    }

    @Nullable
    public static Context getApplicationContext() {
        return sApplicationContext;
    }

    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, int maxHeight, int pixels) {
        if (bitmap == null || sApplicationContext == null) {
            return bitmap;
        }
        int maxHeight2 = (int) (sApplicationContext.getResources().getDisplayMetrics().density * ((float) maxHeight));
        int newWidth = (int) (((float) bitmap.getWidth()) / ((((float) bitmap.getHeight()) * 1.0f) / ((float) maxHeight2)));
        Bitmap output = Bitmap.createBitmap(newWidth, maxHeight2, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        Paint paint = new Paint();
        Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        Rect dstRect = new Rect(0, 0, newWidth, maxHeight2);
        RectF rectF = new RectF(dstRect);
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(-1);
        canvas.drawRoundRect(rectF, (float) pixels, (float) pixels, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, dstRect, paint);
        return output;
    }
}
