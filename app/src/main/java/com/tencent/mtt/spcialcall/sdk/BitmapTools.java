package com.tencent.mtt.spcialcall.sdk;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class BitmapTools {
    private static final int DEFAULT_BUFFER_SIZE = 4096;

    public static byte[] Drawable2Bytes(Drawable d) {
        try {
            Bitmap bitmap = (Bitmap) d.getClass().getMethod("getBitmap", (Class[]) null).invoke(d, new Object[0]);
            if (bitmap != null) {
                return Bitmap2Bytes(bitmap);
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    public static byte[] Bitmap2Bytes(Bitmap bm) {
        if (bm == null) {
            return null;
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }

    public static Bitmap Bytes2Bimap(byte[] b) {
        if (b == null || b.length == 0) {
            return null;
        }
        return BitmapFactory.decodeByteArray(b, 0, b.length);
    }

    public static byte[] toByteArray(InputStream input) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        copy(input, output);
        return output.toByteArray();
    }

    private static int copy(InputStream input, OutputStream output) throws IOException {
        long count = copyLarge(input, output);
        if (count > 2147483647L) {
            return -1;
        }
        return (int) count;
    }

    public static long copyLarge(InputStream input, OutputStream output) throws IOException {
        if (input == null) {
            return -1;
        }
        byte[] buffer = new byte[4096];
        long count = 0;
        while (true) {
            int n = input.read(buffer);
            if (-1 == n) {
                return count;
            }
            output.write(buffer, 0, n);
            count += (long) n;
        }
    }
}
