package com.tencent.msdk.stat.common;

import java.io.File;

class n {
    public static boolean a() {
        String[] strArr = {"/bin", "/system/bin/", "/system/xbin/", "/system/sbin/", "/sbin/", "/vendor/bin/"};
        int i = 0;
        while (i < strArr.length) {
            try {
                File file = new File(strArr[i] + "su");
                if (file != null && file.exists()) {
                    return true;
                }
                i++;
            } catch (Exception e) {
            }
        }
        return false;
    }
}
