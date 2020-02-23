package com.tencent.mtt.ui.util;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Picture;

public class MttCtrlUtil {
    public static Bitmap getScaleBitmap(Picture picture, int width, int height, boolean needScale, boolean needDrawBrowserBg, Bitmap.Config config) {
        Bitmap bm = null;
        if (picture != null && width > 0 && height > 0 && picture.getHeight() > 0 && picture.getWidth() > 0) {
            try {
                bm = Bitmap.createBitmap(width, height, config);
                float scale = 1.0f;
                if (needScale) {
                    if (((float) width) / ((float) height) > ((float) picture.getHeight()) / ((float) picture.getWidth())) {
                        scale = ((float) height) / ((float) picture.getHeight());
                    } else {
                        scale = ((float) width) / ((float) picture.getWidth());
                    }
                }
                Canvas canvas = new Canvas(bm);
                if (needScale) {
                    canvas.scale(scale, scale);
                }
                canvas.drawPicture(picture);
            } catch (OutOfMemoryError e) {
            }
        }
        return bm;
    }
}
