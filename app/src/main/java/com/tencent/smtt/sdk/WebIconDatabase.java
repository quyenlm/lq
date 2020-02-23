package com.tencent.smtt.sdk;

import android.content.ContentResolver;
import android.graphics.Bitmap;
import com.tencent.smtt.export.external.interfaces.IconListener;

@Deprecated
public class WebIconDatabase {
    private static WebIconDatabase a;

    @Deprecated
    public interface a {
        void a(String str, Bitmap bitmap);
    }

    private WebIconDatabase() {
    }

    private static synchronized WebIconDatabase a() {
        WebIconDatabase webIconDatabase;
        synchronized (WebIconDatabase.class) {
            if (a == null) {
                a = new WebIconDatabase();
            }
            webIconDatabase = a;
        }
        return webIconDatabase;
    }

    public static WebIconDatabase getInstance() {
        return a();
    }

    public void bulkRequestIconForPageUrl(ContentResolver contentResolver, String str, a aVar) {
    }

    public void close() {
        br a2 = br.a();
        if (a2 == null || !a2.b()) {
            android.webkit.WebIconDatabase.getInstance().close();
        } else {
            a2.c().m();
        }
    }

    public void open(String str) {
        br a2 = br.a();
        if (a2 == null || !a2.b()) {
            android.webkit.WebIconDatabase.getInstance().open(str);
        } else {
            a2.c().b(str);
        }
    }

    public void releaseIconForPageUrl(String str) {
        br a2 = br.a();
        if (a2 == null || !a2.b()) {
            android.webkit.WebIconDatabase.getInstance().releaseIconForPageUrl(str);
        } else {
            a2.c().d(str);
        }
    }

    public void removeAllIcons() {
        br a2 = br.a();
        if (a2 == null || !a2.b()) {
            android.webkit.WebIconDatabase.getInstance().removeAllIcons();
        } else {
            a2.c().l();
        }
    }

    public void requestIconForPageUrl(String str, a aVar) {
        br a2 = br.a();
        if (a2 == null || !a2.b()) {
            android.webkit.WebIconDatabase.getInstance().requestIconForPageUrl(str, new bk(this, aVar));
        } else {
            a2.c().a(str, (IconListener) new bj(this, aVar));
        }
    }

    public void retainIconForPageUrl(String str) {
        br a2 = br.a();
        if (a2 == null || !a2.b()) {
            android.webkit.WebIconDatabase.getInstance().retainIconForPageUrl(str);
        } else {
            a2.c().c(str);
        }
    }
}
