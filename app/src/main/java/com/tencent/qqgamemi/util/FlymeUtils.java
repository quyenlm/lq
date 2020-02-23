package com.tencent.qqgamemi.util;

import android.os.Build;

public class FlymeUtils {
    public static boolean isFlyme() {
        try {
            if (Build.class.getMethod("hasSmartBar", new Class[0]) != null) {
                return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }
}
