package com.tencent.mtt.spcialcall;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.widget.Toast;
import com.tencent.imsdk.android.friend.IMSDKFileProvider;
import com.tencent.mtt.common.utils.MttResources;
import com.tencent.mtt.engine.AppEngine;
import java.io.File;
import java.security.MessageDigest;

public class SpecialCallUtility {
    private static final int SAVE_IMAGE_FAIL = 1;
    private static final int SAVE_IMAGE_SUCCESS = 0;
    /* access modifiers changed from: private */
    public static Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    SpecialCallUtility.scanImage(Uri.parse((String) msg.obj));
                    Toast.makeText(AppEngine.getInstance().getContext(), MttResources.getStringId("thrdcall_save_success"), 0).show();
                    return;
                case 1:
                    Toast.makeText(AppEngine.getInstance().getContext(), MttResources.getStringId("thrdcall_image_viewer_save_failed"), 0).show();
                    return;
                default:
                    return;
            }
        }
    };

    public static void saveImage(String urlPath, final Bitmap bitmap) {
        new Thread("saveImage") {
            public void run() {
                String url;
                super.run();
                Message msg = SpecialCallUtility.mHandler.obtainMessage();
                msg.what = 1;
                if (!(bitmap == null || (url = MediaStore.Images.Media.insertImage(AppEngine.getInstance().getContext().getContentResolver(), bitmap, String.valueOf(System.currentTimeMillis()), "")) == null)) {
                    msg.what = 0;
                    msg.obj = url;
                }
                msg.sendToTarget();
            }
        }.start();
    }

    /* access modifiers changed from: private */
    public static void scanImage(Uri uri) {
        Cursor actualimagecursor = AppEngine.getInstance().getContext().getContentResolver().query(uri, new String[]{"_data"}, (String) null, (String[]) null, (String) null);
        if (actualimagecursor != null) {
            int actual_image_column_index = actualimagecursor.getColumnIndexOrThrow("_data");
            actualimagecursor.moveToFirst();
            AppEngine.getInstance().getContext().sendBroadcast(new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE", Uri.parse(IMSDKFileProvider.FILE_SCHEME + new File(actualimagecursor.getString(actual_image_column_index)).getAbsolutePath())));
            actualimagecursor.close();
        }
    }

    public static byte[] getMD5(byte[] src) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(src);
            return md.digest();
        } catch (Exception e) {
            return null;
        }
    }

    public static String byteToHexString(byte[] bytes) {
        if (bytes == null || bytes.length <= 0) {
            return null;
        }
        StringBuffer buf = new StringBuffer(bytes.length * 2);
        for (int i = 0; i < bytes.length; i++) {
            if ((bytes[i] & 255) < 16) {
                buf.append("0");
            }
            buf.append(Long.toString((long) (bytes[i] & 255), 16));
        }
        return buf.toString();
    }
}
