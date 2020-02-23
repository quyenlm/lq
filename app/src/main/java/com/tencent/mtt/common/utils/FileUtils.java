package com.tencent.mtt.common.utils;

import android.os.Environment;
import com.tencent.mtt.engine.AppEngine;
import java.io.File;

public class FileUtils {
    private static final String DIR_DATA = "data";
    private static final String DIR_EXT_MAIN = "QQBrowser";

    public static File getShareCacheDir() {
        File childDir;
        if (isExtStorageReady()) {
            childDir = new File(getExternalRootDir(), ".TempShare");
        } else {
            childDir = new File(getDataDir(), "TempShare");
        }
        if (!childDir.exists()) {
            childDir.mkdirs();
        }
        return childDir;
    }

    private static boolean isExtStorageReady() {
        if ("mounted".equals(Environment.getExternalStorageState())) {
            return true;
        }
        return false;
    }

    public static File getExternalRootDir() {
        File childDir = new File(Environment.getExternalStorageDirectory(), DIR_EXT_MAIN);
        if (!childDir.exists()) {
            childDir.mkdirs();
        }
        return childDir;
    }

    public static File getDir(File parent, String dirName) {
        if (parent == null || dirName == null || dirName.length() == 0) {
            return null;
        }
        File childDir = new File(parent, dirName);
        if (childDir.exists()) {
            return childDir;
        }
        childDir.mkdirs();
        return childDir;
    }

    public static File getDataDir() {
        return getDir(getFilesDir(), "data");
    }

    public static File getFilesDir() {
        return AppEngine.getInstance().getContext().getFilesDir();
    }
}
