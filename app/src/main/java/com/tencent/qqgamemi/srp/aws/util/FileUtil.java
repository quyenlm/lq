package com.tencent.qqgamemi.srp.aws.util;

import java.io.File;

public class FileUtil {
    public static boolean isLegalFile(String filePath) {
        if (filePath == null) {
            return false;
        }
        File file = new File(filePath);
        if (!file.exists() || !file.isFile()) {
            return false;
        }
        return true;
    }
}
