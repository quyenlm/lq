package com.tencent.smtt.utils;

import com.tencent.smtt.utils.k;
import java.io.InputStream;
import java.util.zip.ZipEntry;

final class l implements k.b {
    final /* synthetic */ String a;

    l(String str) {
        this.a = str;
    }

    public boolean a(InputStream inputStream, ZipEntry zipEntry, String str) {
        try {
            return k.b(inputStream, zipEntry, this.a, str);
        } catch (Exception e) {
            throw new Exception("copyFileIfChanged Exception", e);
        }
    }
}
