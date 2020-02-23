package com.tencent.mtt.common.utils;

public class UrlUtility {
    public static boolean urlSupportedByX5CoreSp(String url) {
        return isHttpUrl(url) || isHttpsUrl(url) || url.startsWith("about:blank") || isJavascript(url);
    }

    public static boolean isHttpUrl(String url) {
        return url != null && url.length() > 6 && url.substring(0, 7).equalsIgnoreCase("http://");
    }

    public static boolean isHttpsUrl(String url) {
        return url != null && url.length() > 7 && url.substring(0, 8).equalsIgnoreCase("https://");
    }

    public static boolean isJavascript(String url) {
        return url != null && url.length() > 10 && url.substring(0, 11).equalsIgnoreCase("javascript:");
    }
}
