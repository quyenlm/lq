package com.tencent.smtt.sdk;

import android.webkit.ValueCallback;
import java.util.Map;

public class WebStorage {
    private static WebStorage a;

    @Deprecated
    public interface QuotaUpdater {
        void updateQuota(long j);
    }

    private static synchronized WebStorage a() {
        WebStorage webStorage;
        synchronized (WebStorage.class) {
            if (a == null) {
                a = new WebStorage();
            }
            webStorage = a;
        }
        return webStorage;
    }

    public static WebStorage getInstance() {
        return a();
    }

    public void deleteAllData() {
        br a2 = br.a();
        if (a2 == null || !a2.b()) {
            android.webkit.WebStorage.getInstance().deleteAllData();
        } else {
            a2.c().n();
        }
    }

    public void deleteOrigin(String str) {
        br a2 = br.a();
        if (a2 == null || !a2.b()) {
            android.webkit.WebStorage.getInstance().deleteOrigin(str);
        } else {
            a2.c().e(str);
        }
    }

    public void getOrigins(ValueCallback<Map> valueCallback) {
        br a2 = br.a();
        if (a2 == null || !a2.b()) {
            android.webkit.WebStorage.getInstance().getOrigins(valueCallback);
        } else {
            a2.c().a((ValueCallback<Map>) valueCallback);
        }
    }

    public void getQuotaForOrigin(String str, ValueCallback<Long> valueCallback) {
        br a2 = br.a();
        if (a2 == null || !a2.b()) {
            android.webkit.WebStorage.getInstance().getQuotaForOrigin(str, valueCallback);
        } else {
            a2.c().b(str, valueCallback);
        }
    }

    public void getUsageForOrigin(String str, ValueCallback<Long> valueCallback) {
        br a2 = br.a();
        if (a2 == null || !a2.b()) {
            android.webkit.WebStorage.getInstance().getUsageForOrigin(str, valueCallback);
        } else {
            a2.c().a(str, (ValueCallback<Long>) valueCallback);
        }
    }

    @Deprecated
    public void setQuotaForOrigin(String str, long j) {
        br a2 = br.a();
        if (a2 == null || !a2.b()) {
            android.webkit.WebStorage.getInstance().setQuotaForOrigin(str, j);
        } else {
            a2.c().a(str, j);
        }
    }
}
