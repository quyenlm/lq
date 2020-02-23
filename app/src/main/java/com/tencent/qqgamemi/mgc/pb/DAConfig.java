package com.tencent.qqgamemi.mgc.pb;

import android.os.Environment;
import java.io.File;

public class DAConfig {
    public static final String LOG_TAG = "DataAccess";

    public static File getPbCacheDir() {
        File dir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "pbcache");
        if (!dir.exists()) {
            dir.mkdir();
        }
        return dir;
    }
}
