package com.tencent.imsdk.sns.base;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import com.tencent.imsdk.tool.etc.IMLogger;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import org.apache.http.HttpHost;

public class IMBitmapTool {
    public static boolean isHttpUrl(String path) {
        return path.toLowerCase().startsWith(HttpHost.DEFAULT_SCHEME_NAME);
    }

    public static Bitmap loadBitmap(String url) {
        try {
            return BitmapFactory.decodeStream(new URL(url).openConnection().getInputStream());
        } catch (FileNotFoundException e) {
            IMLogger.e("get image file from web error : " + e.getMessage());
            return null;
        } catch (IOException e2) {
            IMLogger.e("get image file from web error : " + e2.getMessage());
            return null;
        } catch (Exception e3) {
            IMLogger.e("get image file from web error : " + e3.getMessage());
            return null;
        }
    }

    public static Bitmap createFromPath(Context context, String path) {
        File file = new File(path);
        if (file.exists()) {
            IMLogger.d("get normal file : " + file);
            return BitmapFactory.decodeFile(path);
        }
        if (path.toLowerCase().startsWith(TransferTable.COLUMN_FILE)) {
            IMLogger.d("get file:// format file : " + file);
            Uri uri = Uri.parse(path);
            if (uri != null) {
                try {
                    return MediaStore.Images.Media.getBitmap(context.getContentResolver(), uri);
                } catch (FileNotFoundException e) {
                    IMLogger.e("get image file error : " + e.getMessage());
                } catch (IOException e2) {
                    IMLogger.e("get image file error : " + e2.getMessage());
                }
            }
        } else if (path.toLowerCase().startsWith(HttpHost.DEFAULT_SCHEME_NAME)) {
            IMLogger.d("get http format file : " + file);
            return loadBitmap(path);
        }
        return null;
    }
}
