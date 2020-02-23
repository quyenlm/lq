package com.tencent.imsdk.android.friend;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;
import com.tencent.imsdk.tool.etc.IMLogger;
import java.io.File;

public class IMSDKFileProvider extends FileProvider {
    public static final String FILE_SCHEME = "file://";
    public static final String PROVIDER_AUTHORITY = ".IMSDKFileProvider";

    public static Uri getUriFromPath(String path, Context context) {
        try {
            if (path.startsWith(FILE_SCHEME)) {
                path = path.replace(FILE_SCHEME, "");
            }
            File shareFile = new File(path);
            if (!shareFile.exists()) {
                return null;
            }
            if (Build.VERSION.SDK_INT < 24) {
                return Uri.fromFile(shareFile);
            }
            IMLogger.d(" imsdkfileprovider authority packagename = " + context.getPackageName());
            return getUriForFile(context, context.getPackageName() + PROVIDER_AUTHORITY, shareFile);
        } catch (Exception e) {
            IMLogger.d("getUriFromPath error = " + e.getMessage());
            return null;
        }
    }
}
