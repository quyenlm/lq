package com.tencent.tpshell;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TPConfig {
    private static String sReflectedAppInfo = "sReflectedAppInfo: true";
    private static String sReflectedShellInfo = "sReflectedShellInfo: true";
    private static String sReflectedTP2Info = "sReflectedTP2Info: true";

    public static boolean useReflectionMode() {
        return getString(sReflectedAppInfo, "false").compareTo("true") == 0;
    }

    public static boolean useShellMode() {
        return getString(sReflectedShellInfo, "false").compareTo("true") == 0;
    }

    public static boolean useTp2Info() {
        return getString(sReflectedTP2Info, "false").compareTo("true") == 0;
    }

    public static String retTp2Info() {
        if (useTp2Info()) {
            return "tersafe2";
        }
        return "tersafe";
    }

    private static String getString(String str, String str2) {
        Matcher matcher = Pattern.compile("^.+:\\s*(.+)").matcher(str);
        return matcher.find() ? matcher.group(1) : str2;
    }
}
