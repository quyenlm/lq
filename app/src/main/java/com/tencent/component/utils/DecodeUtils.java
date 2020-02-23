package com.tencent.component.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import java.io.FileDescriptor;

public class DecodeUtils {
    public static Bitmap decode(FileDescriptor fd, BitmapFactory.Options options) {
        if (options == null) {
            options = new BitmapFactory.Options();
        }
        return BitmapFactory.decodeFileDescriptor(fd, (Rect) null, options);
    }

    public static void decodeBounds(FileDescriptor fd, BitmapFactory.Options options) {
        boolean z;
        if (options != null) {
            z = true;
        } else {
            z = false;
        }
        AssertUtil.assertTrue(z);
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFileDescriptor(fd, (Rect) null, options);
        options.inJustDecodeBounds = false;
    }

    public static Bitmap decode(String path, BitmapFactory.Options options) {
        if (options == null) {
            options = new BitmapFactory.Options();
        }
        return BitmapFactory.decodeFile(path, options);
    }

    public static void decodeBounds(String path, BitmapFactory.Options options) {
        boolean z;
        if (options != null) {
            z = true;
        } else {
            z = false;
        }
        AssertUtil.assertTrue(z);
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);
        options.inJustDecodeBounds = false;
    }

    public static Bitmap decode(byte[] bytes, BitmapFactory.Options options) {
        return decode(bytes, 0, bytes.length, options);
    }

    public static Bitmap decode(byte[] bytes, int offset, int length, BitmapFactory.Options options) {
        if (options == null) {
            options = new BitmapFactory.Options();
        }
        return BitmapFactory.decodeByteArray(bytes, offset, length, options);
    }

    public static void decodeBounds(byte[] bytes, int offset, int length, BitmapFactory.Options options) {
        boolean z;
        if (options != null) {
            z = true;
        } else {
            z = false;
        }
        AssertUtil.assertTrue(z);
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeByteArray(bytes, offset, length, options);
        options.inJustDecodeBounds = false;
    }
}
