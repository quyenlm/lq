package com.tencent.smtt.sdk;

public final class URLUtil {
    public static String composeSearchUrl(String str, String str2, String str3) {
        br a = br.a();
        return (a == null || !a.b()) ? android.webkit.URLUtil.composeSearchUrl(str, str2, str3) : a.c().a(str, str2, str3);
    }

    public static byte[] decode(byte[] bArr) {
        br a = br.a();
        return (a == null || !a.b()) ? android.webkit.URLUtil.decode(bArr) : a.c().a(bArr);
    }

    public static final String guessFileName(String str, String str2, String str3) {
        br a = br.a();
        return (a == null || !a.b()) ? android.webkit.URLUtil.guessFileName(str, str2, str3) : a.c().b(str, str2, str3);
    }

    public static String guessUrl(String str) {
        br a = br.a();
        return (a == null || !a.b()) ? android.webkit.URLUtil.guessUrl(str) : a.c().m(str);
    }

    public static boolean isAboutUrl(String str) {
        br a = br.a();
        return (a == null || !a.b()) ? android.webkit.URLUtil.isAboutUrl(str) : a.c().q(str);
    }

    public static boolean isAssetUrl(String str) {
        br a = br.a();
        return (a == null || !a.b()) ? android.webkit.URLUtil.isAssetUrl(str) : a.c().n(str);
    }

    public static boolean isContentUrl(String str) {
        br a = br.a();
        return (a == null || !a.b()) ? android.webkit.URLUtil.isContentUrl(str) : a.c().w(str);
    }

    @Deprecated
    public static boolean isCookielessProxyUrl(String str) {
        br a = br.a();
        return (a == null || !a.b()) ? android.webkit.URLUtil.isCookielessProxyUrl(str) : a.c().o(str);
    }

    public static boolean isDataUrl(String str) {
        br a = br.a();
        return (a == null || !a.b()) ? android.webkit.URLUtil.isDataUrl(str) : a.c().r(str);
    }

    public static boolean isFileUrl(String str) {
        br a = br.a();
        return (a == null || !a.b()) ? android.webkit.URLUtil.isFileUrl(str) : a.c().p(str);
    }

    public static boolean isHttpUrl(String str) {
        br a = br.a();
        return (a == null || !a.b()) ? android.webkit.URLUtil.isHttpUrl(str) : a.c().t(str);
    }

    public static boolean isHttpsUrl(String str) {
        br a = br.a();
        return (a == null || !a.b()) ? android.webkit.URLUtil.isHttpsUrl(str) : a.c().u(str);
    }

    public static boolean isJavaScriptUrl(String str) {
        br a = br.a();
        return (a == null || !a.b()) ? android.webkit.URLUtil.isJavaScriptUrl(str) : a.c().s(str);
    }

    public static boolean isNetworkUrl(String str) {
        br a = br.a();
        return (a == null || !a.b()) ? android.webkit.URLUtil.isNetworkUrl(str) : a.c().v(str);
    }

    public static boolean isValidUrl(String str) {
        br a = br.a();
        return (a == null || !a.b()) ? android.webkit.URLUtil.isValidUrl(str) : a.c().x(str);
    }

    public static String stripAnchor(String str) {
        br a = br.a();
        return (a == null || !a.b()) ? android.webkit.URLUtil.stripAnchor(str) : a.c().y(str);
    }
}
