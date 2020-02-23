package com.tencent.liteav.basic.util;

public class TXCCommonUtil {
    private static String mStrAppVersion = "";
    public static String pituLicencePath;

    private static native int nativeGetSDKID();

    private static native String nativeGetSDKVersion();

    static {
        a.d();
    }

    public static int[] getSDKVersion() {
        String[] split = nativeGetSDKVersion().split("\\.");
        int[] iArr = new int[split.length];
        for (int i = 0; i < split.length; i++) {
            try {
                iArr[i] = Integer.parseInt(split[i]);
            } catch (NumberFormatException e) {
                e.printStackTrace();
                iArr[i] = 0;
            }
        }
        return iArr;
    }

    public static String getSDKVersionStr() {
        return nativeGetSDKVersion();
    }

    public static int getSDKID() {
        return nativeGetSDKID();
    }

    public static String getFileExtension(String str) {
        int lastIndexOf;
        if (str == null || str.length() <= 0 || (lastIndexOf = str.lastIndexOf(46)) <= -1 || lastIndexOf >= str.length() - 1) {
            return null;
        }
        return str.substring(lastIndexOf + 1);
    }

    public static void sleep(int i) {
        try {
            Thread.sleep((long) i);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void setAppVersion(String str) {
        mStrAppVersion = str;
    }

    public static void setPituLicencePath(String str) {
        pituLicencePath = str;
    }

    public static String getAppVersion() {
        return mStrAppVersion;
    }
}
