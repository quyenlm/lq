package com.tencent.component.utils;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import com.beetalk.sdk.ShareConstants;
import com.google.android.gms.analytics.ecommerce.Promotion;
import com.tencent.rtmp2.TXLiveConstants;
import java.io.ByteArrayOutputStream;

public class BitmapUtils {
    private static final int DEFAULT_QUALITY = 90;

    public static byte[] compressToBytes(Bitmap bitmap, Bitmap.CompressFormat format) {
        return compressToBytes(bitmap, 90, format);
    }

    public static byte[] compressToBytes(Bitmap bitmap) {
        return compressToBytes(bitmap, 90, Bitmap.CompressFormat.JPEG);
    }

    public static byte[] compressToBytes(Bitmap bitmap, int quality, Bitmap.CompressFormat format) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream(65536);
        bitmap.compress(format, quality, baos);
        return baos.toByteArray();
    }

    @SuppressLint({"InlinedApi"})
    public static Bitmap processExif(Bitmap bitmap, String imagePath) {
        if (bitmap == null || imagePath == null || PlatformUtil.version() < 5) {
            return bitmap;
        }
        ExifInterface exif = null;
        try {
            exif = new ExifInterface(imagePath);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        if (exif == null) {
            return bitmap;
        }
        int rotation = 0;
        switch (exif.getAttributeInt("Orientation", 0)) {
            case 3:
                rotation = ShareConstants.ERROR_CODE.GG_RESULT_UNKNOWN_ERROR;
                break;
            case 6:
                rotation = 90;
                break;
            case 8:
                rotation = TXLiveConstants.RENDER_ROTATION_LANDSCAPE;
                break;
        }
        return rotateBitmap(bitmap, rotation);
    }

    public static Bitmap rotateBitmap(Bitmap bitmap, int rotation) {
        int rotation2 = rotation % 360;
        if (rotation2 == 0) {
            return bitmap;
        }
        boolean rotateDimension = (rotation2 > 45 && rotation2 < 135) || (rotation2 > 225 && rotation2 < 315);
        int width = !rotateDimension ? bitmap.getWidth() : bitmap.getHeight();
        int height = !rotateDimension ? bitmap.getHeight() : bitmap.getWidth();
        Bitmap newBitmap = null;
        try {
            newBitmap = Bitmap.createBitmap(width, height, bitmap.getConfig());
        } catch (Throwable th) {
        }
        if (newBitmap == null || newBitmap == bitmap) {
            return bitmap;
        }
        Canvas canvas = new Canvas(newBitmap);
        int dx = (width - bitmap.getWidth()) / 2;
        int dy = (height - bitmap.getHeight()) / 2;
        if (!(dx == 0 && dy == 0)) {
            canvas.translate((float) dx, (float) dy);
        }
        canvas.rotate((float) rotation2, (float) (bitmap.getWidth() / 2), (float) (bitmap.getHeight() / 2));
        canvas.drawBitmap(bitmap, 0.0f, 0.0f, (Paint) null);
        bitmap.recycle();
        return newBitmap;
    }

    public static Bitmap drawViewToBitmap(Bitmap dest, View view, int width, int height, int downSampling, Drawable drawable) {
        Log.d("drawViewToBitmap", Promotion.ACTION_VIEW + width + " " + height);
        float scale = 1.0f / ((float) downSampling);
        int heightCopy = view.getHeight();
        view.layout(0, 0, width, height);
        int bmpWidth = (int) (((float) width) * scale);
        int bmpHeight = (int) (((float) height) * scale);
        Log.d("drawViewToBitmap", "bmpview" + bmpWidth + " " + bmpHeight);
        Log.d("drawViewToBitmap", "heightCopy" + heightCopy);
        if (!(dest != null && dest.getWidth() == bmpWidth && dest.getHeight() == bmpHeight)) {
            dest = Bitmap.createBitmap(bmpWidth, bmpHeight, Bitmap.Config.ARGB_8888);
        }
        Canvas c = new Canvas(dest);
        drawable.setBounds(new Rect(0, 0, width, height));
        drawable.draw(c);
        if (downSampling > 1) {
            c.scale(scale, scale);
        }
        view.draw(c);
        view.layout(0, 0, width, heightCopy);
        return dest;
    }

    public static Bitmap getBitmapFromView(View v) {
        boolean willNotCache = v.willNotCacheDrawing();
        v.setWillNotCacheDrawing(false);
        int color = v.getDrawingCacheBackgroundColor();
        v.setDrawingCacheBackgroundColor(0);
        if (color != 0) {
            v.destroyDrawingCache();
        }
        v.buildDrawingCache();
        Bitmap cacheBitmap = v.getDrawingCache();
        if (cacheBitmap == null) {
            return null;
        }
        Bitmap createBitmap = Bitmap.createBitmap(cacheBitmap);
        v.destroyDrawingCache();
        v.setWillNotCacheDrawing(willNotCache);
        v.setDrawingCacheBackgroundColor(color);
        return createBitmap;
    }

    public static Bitmap getBitmapFromView(View v, int width, int height) {
        boolean willNotCache = v.willNotCacheDrawing();
        v.setWillNotCacheDrawing(false);
        int color = v.getDrawingCacheBackgroundColor();
        v.setDrawingCacheBackgroundColor(0);
        if (color != 0) {
            v.destroyDrawingCache();
        }
        v.buildDrawingCache();
        Bitmap cacheBitmap = v.getDrawingCache();
        if (cacheBitmap == null) {
            return null;
        }
        Bitmap createBitmap = Bitmap.createBitmap(cacheBitmap, 0, 0, width, height);
        v.destroyDrawingCache();
        v.setWillNotCacheDrawing(willNotCache);
        v.setDrawingCacheBackgroundColor(color);
        return createBitmap;
    }

    /* JADX WARNING: Removed duplicated region for block: B:26:0x006b A[SYNTHETIC, Splitter:B:26:0x006b] */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x0074 A[Catch:{ IOException -> 0x007c }] */
    /* JADX WARNING: Removed duplicated region for block: B:35:0x0084 A[SYNTHETIC, Splitter:B:35:0x0084] */
    /* JADX WARNING: Removed duplicated region for block: B:38:0x008d A[Catch:{ IOException -> 0x0095 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean saveToSdCard(android.graphics.Bitmap r8, java.lang.String r9) {
        /*
            r4 = 0
            r0 = 0
            java.io.ByteArrayOutputStream r1 = new java.io.ByteArrayOutputStream     // Catch:{ IOException -> 0x0064 }
            r1.<init>()     // Catch:{ IOException -> 0x0064 }
            android.graphics.Bitmap$CompressFormat r6 = android.graphics.Bitmap.CompressFormat.JPEG     // Catch:{ IOException -> 0x00a1, all -> 0x009a }
            r7 = 40
            r8.compress(r6, r7, r1)     // Catch:{ IOException -> 0x00a1, all -> 0x009a }
            java.io.File r3 = new java.io.File     // Catch:{ IOException -> 0x00a1, all -> 0x009a }
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x00a1, all -> 0x009a }
            r6.<init>()     // Catch:{ IOException -> 0x00a1, all -> 0x009a }
            java.io.File r7 = android.os.Environment.getExternalStorageDirectory()     // Catch:{ IOException -> 0x00a1, all -> 0x009a }
            java.lang.StringBuilder r6 = r6.append(r7)     // Catch:{ IOException -> 0x00a1, all -> 0x009a }
            java.lang.String r7 = "/tmp"
            java.lang.StringBuilder r6 = r6.append(r7)     // Catch:{ IOException -> 0x00a1, all -> 0x009a }
            java.lang.String r7 = java.io.File.separator     // Catch:{ IOException -> 0x00a1, all -> 0x009a }
            java.lang.StringBuilder r6 = r6.append(r7)     // Catch:{ IOException -> 0x00a1, all -> 0x009a }
            java.lang.StringBuilder r6 = r6.append(r9)     // Catch:{ IOException -> 0x00a1, all -> 0x009a }
            java.lang.String r6 = r6.toString()     // Catch:{ IOException -> 0x00a1, all -> 0x009a }
            r3.<init>(r6)     // Catch:{ IOException -> 0x00a1, all -> 0x009a }
            r3.createNewFile()     // Catch:{ IOException -> 0x00a1, all -> 0x009a }
            java.io.FileOutputStream r5 = new java.io.FileOutputStream     // Catch:{ IOException -> 0x00a1, all -> 0x009a }
            r5.<init>(r3)     // Catch:{ IOException -> 0x00a1, all -> 0x009a }
            byte[] r6 = r1.toByteArray()     // Catch:{ IOException -> 0x00a4, all -> 0x009d }
            r5.write(r6)     // Catch:{ IOException -> 0x00a4, all -> 0x009d }
            r5.flush()     // Catch:{ IOException -> 0x00a4, all -> 0x009d }
            r5.close()     // Catch:{ IOException -> 0x00a4, all -> 0x009d }
            r6 = 1
            if (r1 == 0) goto L_0x00aa
            r1.flush()     // Catch:{ IOException -> 0x005d }
            r1.close()     // Catch:{ IOException -> 0x005d }
            r0 = 0
        L_0x0053:
            if (r5 == 0) goto L_0x0062
            r5.flush()     // Catch:{ IOException -> 0x00a8 }
            r5.close()     // Catch:{ IOException -> 0x00a8 }
            r4 = 0
        L_0x005c:
            return r6
        L_0x005d:
            r2 = move-exception
            r0 = r1
        L_0x005f:
            r2.printStackTrace()
        L_0x0062:
            r4 = r5
            goto L_0x005c
        L_0x0064:
            r2 = move-exception
        L_0x0065:
            r2.printStackTrace()     // Catch:{ all -> 0x0081 }
            r6 = 0
            if (r0 == 0) goto L_0x0072
            r0.flush()     // Catch:{ IOException -> 0x007c }
            r0.close()     // Catch:{ IOException -> 0x007c }
            r0 = 0
        L_0x0072:
            if (r4 == 0) goto L_0x005c
            r4.flush()     // Catch:{ IOException -> 0x007c }
            r4.close()     // Catch:{ IOException -> 0x007c }
            r4 = 0
            goto L_0x005c
        L_0x007c:
            r2 = move-exception
            r2.printStackTrace()
            goto L_0x005c
        L_0x0081:
            r6 = move-exception
        L_0x0082:
            if (r0 == 0) goto L_0x008b
            r0.flush()     // Catch:{ IOException -> 0x0095 }
            r0.close()     // Catch:{ IOException -> 0x0095 }
            r0 = 0
        L_0x008b:
            if (r4 == 0) goto L_0x0094
            r4.flush()     // Catch:{ IOException -> 0x0095 }
            r4.close()     // Catch:{ IOException -> 0x0095 }
            r4 = 0
        L_0x0094:
            throw r6
        L_0x0095:
            r2 = move-exception
            r2.printStackTrace()
            goto L_0x0094
        L_0x009a:
            r6 = move-exception
            r0 = r1
            goto L_0x0082
        L_0x009d:
            r6 = move-exception
            r0 = r1
            r4 = r5
            goto L_0x0082
        L_0x00a1:
            r2 = move-exception
            r0 = r1
            goto L_0x0065
        L_0x00a4:
            r2 = move-exception
            r0 = r1
            r4 = r5
            goto L_0x0065
        L_0x00a8:
            r2 = move-exception
            goto L_0x005f
        L_0x00aa:
            r0 = r1
            goto L_0x0053
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.component.utils.BitmapUtils.saveToSdCard(android.graphics.Bitmap, java.lang.String):boolean");
    }

    /* JADX WARNING: Removed duplicated region for block: B:18:0x0033 A[SYNTHETIC, Splitter:B:18:0x0033] */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x0043 A[SYNTHETIC, Splitter:B:25:0x0043] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String saveBitmap(android.graphics.Bitmap r7, java.lang.String r8) {
        /*
            r4 = 0
            if (r8 == 0) goto L_0x0005
            if (r7 != 0) goto L_0x0007
        L_0x0005:
            r8 = r4
        L_0x0006:
            return r8
        L_0x0007:
            r0 = 0
            java.io.BufferedOutputStream r1 = new java.io.BufferedOutputStream     // Catch:{ Exception -> 0x002d }
            java.io.FileOutputStream r5 = new java.io.FileOutputStream     // Catch:{ Exception -> 0x002d }
            r5.<init>(r8)     // Catch:{ Exception -> 0x002d }
            r1.<init>(r5)     // Catch:{ Exception -> 0x002d }
            android.graphics.Bitmap$CompressFormat r5 = android.graphics.Bitmap.CompressFormat.JPEG     // Catch:{ Exception -> 0x0052, all -> 0x004f }
            r6 = 100
            r7.compress(r5, r6, r1)     // Catch:{ Exception -> 0x0052, all -> 0x004f }
            r1.flush()     // Catch:{ Exception -> 0x0052, all -> 0x004f }
            r1.close()     // Catch:{ Exception -> 0x0052, all -> 0x004f }
            if (r1 == 0) goto L_0x0006
            r1.flush()     // Catch:{ IOException -> 0x0028 }
            r1.close()     // Catch:{ IOException -> 0x0028 }
            goto L_0x0006
        L_0x0028:
            r3 = move-exception
            r3.printStackTrace()
            goto L_0x0006
        L_0x002d:
            r2 = move-exception
        L_0x002e:
            r2.printStackTrace()     // Catch:{ all -> 0x0040 }
            if (r0 == 0) goto L_0x0039
            r0.flush()     // Catch:{ IOException -> 0x003b }
            r0.close()     // Catch:{ IOException -> 0x003b }
        L_0x0039:
            r8 = r4
            goto L_0x0006
        L_0x003b:
            r3 = move-exception
            r3.printStackTrace()
            goto L_0x0039
        L_0x0040:
            r4 = move-exception
        L_0x0041:
            if (r0 == 0) goto L_0x0049
            r0.flush()     // Catch:{ IOException -> 0x004a }
            r0.close()     // Catch:{ IOException -> 0x004a }
        L_0x0049:
            throw r4
        L_0x004a:
            r3 = move-exception
            r3.printStackTrace()
            goto L_0x0049
        L_0x004f:
            r4 = move-exception
            r0 = r1
            goto L_0x0041
        L_0x0052:
            r2 = move-exception
            r0 = r1
            goto L_0x002e
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.component.utils.BitmapUtils.saveBitmap(android.graphics.Bitmap, java.lang.String):java.lang.String");
    }

    public static Bitmap getVideoThumbnail(Context context, String Videopath) {
        ContentResolver testcr = context.getContentResolver();
        Cursor cursor = testcr.query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, new String[]{"_data", "_id"}, "_data = '" + Videopath + "'", (String[]) null, (String) null);
        int _id = 0;
        if (cursor == null) {
            return null;
        }
        if (cursor.getCount() == 0) {
            cursor.close();
            return null;
        }
        if (cursor.moveToFirst()) {
            int _idColumn = cursor.getColumnIndex("_id");
            int _dataColumn = cursor.getColumnIndex("_data");
            do {
                _id = cursor.getInt(_idColumn);
                String videoPath = cursor.getString(_dataColumn);
            } while (cursor.moveToNext());
        }
        cursor.close();
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inDither = false;
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        return MediaStore.Video.Thumbnails.getThumbnail(testcr, (long) _id, 1, options);
    }
}
