package com.tencent.smtt.sdk;

public class MimeTypeMap {
    private static MimeTypeMap a;

    private MimeTypeMap() {
    }

    public static String getFileExtensionFromUrl(String str) {
        br a2 = br.a();
        return (a2 == null || !a2.b()) ? android.webkit.MimeTypeMap.getFileExtensionFromUrl(str) : a2.c().h(str);
    }

    public static synchronized MimeTypeMap getSingleton() {
        MimeTypeMap mimeTypeMap;
        synchronized (MimeTypeMap.class) {
            if (a == null) {
                a = new MimeTypeMap();
            }
            mimeTypeMap = a;
        }
        return mimeTypeMap;
    }

    public String getExtensionFromMimeType(String str) {
        br a2 = br.a();
        return (a2 == null || !a2.b()) ? android.webkit.MimeTypeMap.getSingleton().getExtensionFromMimeType(str) : a2.c().l(str);
    }

    public String getMimeTypeFromExtension(String str) {
        br a2 = br.a();
        return (a2 == null || !a2.b()) ? android.webkit.MimeTypeMap.getSingleton().getMimeTypeFromExtension(str) : a2.c().j(str);
    }

    public boolean hasExtension(String str) {
        br a2 = br.a();
        return (a2 == null || !a2.b()) ? android.webkit.MimeTypeMap.getSingleton().hasExtension(str) : a2.c().k(str);
    }

    public boolean hasMimeType(String str) {
        br a2 = br.a();
        return (a2 == null || !a2.b()) ? android.webkit.MimeTypeMap.getSingleton().hasMimeType(str) : a2.c().i(str);
    }
}
