package com.tencent.mtt.spcialcall.sdk;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.NinePatch;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import com.tencent.tp.a.h;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;

public class NinePatchUtils {
    private static final int NO_COLOR = 1;

    private NinePatchUtils() {
    }

    public static Drawable decodeDrawableFromAsset(Context context, String assetPath) throws Exception {
        Bitmap bitmap = readFromAsset(context, assetPath);
        if (bitmap.getNinePatchChunk() == null) {
            return new BitmapDrawable(bitmap);
        }
        Rect paddingRect = new Rect();
        readPaddingFromChunk(bitmap.getNinePatchChunk(), paddingRect);
        return new NinePatchDrawable(context.getResources(), bitmap, bitmap.getNinePatchChunk(), paddingRect, (String) null);
    }

    public static Bitmap decodeFromStream(InputStream in) throws Exception {
        Bitmap srcBm = BitmapFactory.decodeStream(in);
        srcBm.setDensity(240);
        byte[] chunk = readChunk(srcBm);
        if (!NinePatch.isNinePatchChunk(chunk)) {
            return srcBm;
        }
        Bitmap tgtBm = Bitmap.createBitmap(srcBm, 1, 1, srcBm.getWidth() - 2, srcBm.getHeight() - 2);
        srcBm.recycle();
        Field f = tgtBm.getClass().getDeclaredField("mNinePatchChunk");
        f.setAccessible(true);
        f.set(tgtBm, chunk);
        return tgtBm;
    }

    public static Bitmap decodeFromFile(String path) throws Exception {
        Bitmap srcBm = BitmapFactory.decodeFile(path);
        if (srcBm == null) {
            return null;
        }
        srcBm.setDensity(240);
        byte[] chunk = readChunk(srcBm);
        if (!NinePatch.isNinePatchChunk(chunk)) {
            return srcBm;
        }
        try {
            Bitmap tgtBm = Bitmap.createBitmap(srcBm, 1, 1, srcBm.getWidth() - 2, srcBm.getHeight() - 2);
            srcBm.recycle();
            Field f = tgtBm.getClass().getDeclaredField("mNinePatchChunk");
            f.setAccessible(true);
            f.set(tgtBm, chunk);
            return tgtBm;
        } catch (OutOfMemoryError e) {
            return null;
        }
    }

    public static Drawable createDrawableFromFile(Context context, String path) throws Exception {
        Bitmap bm = decodeFromFile(path);
        if (bm == null) {
            return null;
        }
        bm.setDensity(240);
        Rect paddingRect = new Rect();
        readPaddingFromChunk(bm.getNinePatchChunk(), paddingRect);
        NinePatchDrawable d = new NinePatchDrawable(context.getResources(), bm, bm.getNinePatchChunk(), paddingRect, (String) null);
        d.getPaint().setAntiAlias(true);
        d.setFilterBitmap(true);
        return d;
    }

    public static Bitmap readFromAsset(Context context, String ninePatchPngPath) throws Exception {
        InputStream is = context.getAssets().open(ninePatchPngPath);
        Bitmap bm = decodeFromStream(is);
        is.close();
        return bm;
    }

    public static void readPaddingFromChunk(byte[] chunk, Rect paddingRect) {
        paddingRect.left = getInt(chunk, 12);
        paddingRect.right = getInt(chunk, 16);
        paddingRect.top = getInt(chunk, 20);
        paddingRect.bottom = getInt(chunk, 24);
    }

    public static byte[] readChunk(Bitmap resourceBmp) throws IOException {
        int BM_W = resourceBmp.getWidth();
        int BM_H = resourceBmp.getHeight();
        int xPointCount = 0;
        int yPointCount = 0;
        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        for (int i = 0; i < 32; i++) {
            bao.write(0);
        }
        int[] pixelsTop = new int[(BM_W - 2)];
        resourceBmp.getPixels(pixelsTop, 0, BM_W, 1, 0, BM_W - 2, 1);
        boolean topFirstPixelIsBlack = pixelsTop[0] == -16777216;
        boolean topLastPixelIsBlack = pixelsTop[pixelsTop.length + -1] == -16777216;
        int tmpLastColor = 0;
        int len = pixelsTop.length;
        for (int i2 = 0; i2 < len; i2++) {
            if (tmpLastColor != pixelsTop[i2]) {
                xPointCount++;
                writeInt(bao, i2);
                tmpLastColor = pixelsTop[i2];
            }
        }
        if (topLastPixelIsBlack) {
            xPointCount++;
            writeInt(bao, pixelsTop.length);
        }
        int xBlockCount = xPointCount + 1;
        if (topFirstPixelIsBlack) {
            xBlockCount--;
        }
        if (topLastPixelIsBlack) {
            xBlockCount--;
        }
        int[] pixelsLeft = new int[(BM_H - 2)];
        resourceBmp.getPixels(pixelsLeft, 0, 1, 0, 1, 1, BM_H - 2);
        boolean firstPixelIsBlack = pixelsLeft[0] == -16777216;
        boolean lastPixelIsBlack = pixelsLeft[pixelsLeft.length + -1] == -16777216;
        int tmpLastColor2 = 0;
        int len2 = pixelsLeft.length;
        for (int i3 = 0; i3 < len2; i3++) {
            if (tmpLastColor2 != pixelsLeft[i3]) {
                yPointCount++;
                writeInt(bao, i3);
                tmpLastColor2 = pixelsLeft[i3];
            }
        }
        if (lastPixelIsBlack) {
            yPointCount++;
            writeInt(bao, pixelsLeft.length);
        }
        int yBlockCount = yPointCount + 1;
        if (firstPixelIsBlack) {
            yBlockCount--;
        }
        if (lastPixelIsBlack) {
            yBlockCount--;
        }
        for (int i4 = 0; i4 < xBlockCount * yBlockCount; i4++) {
            writeInt(bao, 1);
        }
        byte[] data = bao.toByteArray();
        data[0] = 1;
        data[1] = (byte) xPointCount;
        data[2] = (byte) yPointCount;
        data[3] = (byte) (xBlockCount * yBlockCount);
        dealPaddingInfo(resourceBmp, data);
        return data;
    }

    private static void dealPaddingInfo(Bitmap bm, byte[] data) {
        int[] bottomPixels = new int[(bm.getWidth() - 2)];
        bm.getPixels(bottomPixels, 0, bottomPixels.length, 1, bm.getHeight() - 1, bottomPixels.length, 1);
        int i = 0;
        while (true) {
            if (i >= bottomPixels.length) {
                break;
            } else if (-16777216 == bottomPixels[i]) {
                writeInt(data, 12, i);
                break;
            } else {
                i++;
            }
        }
        int i2 = bottomPixels.length - 1;
        while (true) {
            if (i2 < 0) {
                break;
            } else if (-16777216 == bottomPixels[i2]) {
                writeInt(data, 16, (bottomPixels.length - i2) - 2);
                break;
            } else {
                i2--;
            }
        }
        int[] rightPixels = new int[(bm.getHeight() - 2)];
        bm.getPixels(rightPixels, 0, 1, bm.getWidth() - 1, 0, 1, rightPixels.length);
        int i3 = 0;
        while (true) {
            if (i3 >= rightPixels.length) {
                break;
            } else if (-16777216 == rightPixels[i3]) {
                writeInt(data, 20, i3);
                break;
            } else {
                i3++;
            }
        }
        for (int i4 = rightPixels.length - 1; i4 >= 0; i4--) {
            if (-16777216 == rightPixels[i4]) {
                writeInt(data, 24, (rightPixels.length - i4) - 2);
                return;
            }
        }
    }

    private static void writeInt(OutputStream out, int v) throws IOException {
        out.write((v >> 0) & 255);
        out.write((v >> 8) & 255);
        out.write((v >> 16) & 255);
        out.write((v >> 24) & 255);
    }

    private static void writeInt(byte[] b, int offset, int v) {
        b[offset + 0] = (byte) (v >> 0);
        b[offset + 1] = (byte) (v >> 8);
        b[offset + 2] = (byte) (v >> 16);
        b[offset + 3] = (byte) (v >> 24);
    }

    private static int getInt(byte[] bs, int from) {
        return (bs[from + 1] << 8) | bs[from + 0] | (bs[from + 2] << 16) | (bs[from + 3] << 24);
    }

    public static void printChunkInfo(Bitmap bm) {
        byte[] chunk = bm.getNinePatchChunk();
        if (chunk == null) {
            System.out.println("can't find chunk info from this bitmap(" + bm + h.b);
            return;
        }
        byte xLen = chunk[1];
        byte yLen = chunk[2];
        byte cLen = chunk[3];
        StringBuilder sb = new StringBuilder();
        int peddingLeft = getInt(chunk, 12);
        int paddingRight = getInt(chunk, 16);
        int paddingTop = getInt(chunk, 20);
        int paddingBottom = getInt(chunk, 24);
        sb.append("peddingLeft=" + peddingLeft);
        sb.append("\r\n");
        sb.append("paddingRight=" + paddingRight);
        sb.append("\r\n");
        sb.append("paddingTop=" + paddingTop);
        sb.append("\r\n");
        sb.append("paddingBottom=" + paddingBottom);
        sb.append("\r\n");
        sb.append("x info=");
        for (int i = 0; i < xLen; i++) {
            sb.append("," + getInt(chunk, (i * 4) + 32));
        }
        sb.append("\r\n");
        sb.append("y info=");
        for (int i2 = 0; i2 < yLen; i2++) {
            sb.append("," + getInt(chunk, (xLen * 4) + 32 + (i2 * 4)));
        }
        sb.append("\r\n");
        sb.append("color info=");
        for (int i3 = 0; i3 < cLen; i3++) {
            sb.append("," + getInt(chunk, (xLen * 4) + (yLen * 4) + 32 + (i3 * 4)));
        }
        System.err.println(new StringBuilder().append(sb).toString());
    }
}
