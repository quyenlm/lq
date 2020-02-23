package com.tencent.smtt.sdk;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.text.TextUtils;
import android.webkit.ValueCallback;
import android.webkit.WebView;
import com.tencent.smtt.sdk.TbsListener;
import com.tencent.smtt.sdk.TbsLogReport;
import com.tencent.smtt.utils.TbsLog;
import com.tencent.smtt.utils.v;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

public class CookieManager {
    public static String LOGTAG = "CookieManager";
    private static CookieManager d;
    ArrayList<b> a;
    String b;
    a c = a.MODE_NONE;
    private boolean e = false;
    private boolean f = false;

    public enum a {
        MODE_NONE,
        MODE_KEYS,
        MODE_ALL
    }

    class b {
        int a;
        String b;
        String c;
        ValueCallback<Boolean> d;

        b() {
        }
    }

    private CookieManager() {
    }

    public static CookieManager getInstance() {
        if (d == null) {
            synchronized (CookieManager.class) {
                if (d == null) {
                    d = new CookieManager();
                }
            }
        }
        return d;
    }

    public static int getROMCookieDBVersion(Context context) {
        return (Build.VERSION.SDK_INT >= 11 ? context.getSharedPreferences("cookiedb_info", 4) : context.getSharedPreferences("cookiedb_info", 0)).getInt("db_version", -1);
    }

    public static void setROMCookieDBVersion(Context context, int i) {
        SharedPreferences.Editor edit = (Build.VERSION.SDK_INT >= 11 ? context.getSharedPreferences("cookiedb_info", 4) : context.getSharedPreferences("cookiedb_info", 0)).edit();
        edit.putInt("db_version", i);
        edit.commit();
    }

    /* access modifiers changed from: package-private */
    public synchronized void a() {
        this.f = true;
        if (!(this.a == null || this.a.size() == 0)) {
            br a2 = br.a();
            if (a2 != null && a2.b()) {
                Iterator<b> it = this.a.iterator();
                while (it.hasNext()) {
                    b next = it.next();
                    switch (next.a) {
                        case 1:
                            setCookie(next.b, next.c, next.d);
                            break;
                        case 2:
                            setCookie(next.b, next.c);
                            break;
                    }
                }
            } else {
                Iterator<b> it2 = this.a.iterator();
                while (it2.hasNext()) {
                    b next2 = it2.next();
                    switch (next2.a) {
                        case 1:
                            if (Build.VERSION.SDK_INT < 21) {
                                break;
                            } else {
                                v.a((Object) android.webkit.CookieManager.getInstance(), "setCookie", (Class<?>[]) new Class[]{String.class, String.class, ValueCallback.class}, next2.b, next2.c, next2.d);
                                break;
                            }
                        case 2:
                            android.webkit.CookieManager.getInstance().setCookie(next2.b, next2.c);
                            break;
                    }
                }
            }
            this.a.clear();
        }
    }

    /* access modifiers changed from: protected */
    public synchronized void a(Context context, boolean z, boolean z2) {
        int i;
        int i2;
        boolean z3;
        if (this.c != a.MODE_NONE && context != null && TbsExtensionFunctionManager.getInstance().canUseFunction(context, TbsExtensionFunctionManager.COOKIE_SWITCH_FILE_NAME) && !this.e) {
            long currentTimeMillis = System.currentTimeMillis();
            long j = 0;
            TbsLog.i(LOGTAG, "compatiableCookieDatabaseIfNeed,isX5Inited:" + z + ",useX5:" + z2);
            if (z || QbSdk.getIsSysWebViewForcedByOuter() || QbSdk.a) {
                if (QbSdk.getIsSysWebViewForcedByOuter() || QbSdk.a) {
                    z2 = false;
                }
                boolean canUseFunction = TbsExtensionFunctionManager.getInstance().canUseFunction(context, TbsExtensionFunctionManager.USEX5_FILE_NAME);
                TbsLog.i(LOGTAG, "usex5 : mUseX5LastProcess->" + canUseFunction + ",useX5:" + z2);
                TbsExtensionFunctionManager.getInstance().setFunctionEnable(context, TbsExtensionFunctionManager.USEX5_FILE_NAME, z2);
                if (canUseFunction != z2) {
                    TbsLogReport.TbsLogInfo a2 = TbsLogReport.a(context).a();
                    if (TextUtils.isEmpty(this.b)) {
                        a2.setErrorCode(701);
                        i = 0;
                        i2 = 0;
                    } else if (am.a().m(context) <= 0 || am.a().m(context) >= 36001) {
                        if (canUseFunction) {
                            i2 = x.d(context);
                            if (i2 > 0) {
                                i = getROMCookieDBVersion(context);
                                z3 = i <= 0;
                            }
                            z3 = false;
                            i = 0;
                        } else {
                            i2 = x.d(context);
                            if (i2 > 0) {
                                String c2 = am.a().c(context, "cookies_database_version");
                                if (!TextUtils.isEmpty(c2)) {
                                    try {
                                        i = Integer.parseInt(c2);
                                        z3 = false;
                                    } catch (Exception e2) {
                                        z3 = false;
                                        i = 0;
                                    }
                                }
                            }
                            z3 = false;
                            i = 0;
                        }
                        if (!z3 && (i2 <= 0 || i <= 0)) {
                            a2.setErrorCode(702);
                        } else if (i >= i2) {
                            a2.setErrorCode(703);
                        } else {
                            x.a(context, this.c, this.b, z3, z2);
                            a2.setErrorCode(TbsListener.ErrorCode.INFO_COOKIE_SWITCH_TRANSFER);
                            j = System.currentTimeMillis() - currentTimeMillis;
                        }
                    }
                    a2.setFailDetail("x5->sys:" + canUseFunction + " from:" + i2 + " to:" + i + ",timeused:" + j);
                    TbsLogReport.a(context).a(TbsLogReport.EventType.TYPE_COOKIE_DB_SWITCH, a2);
                }
            } else {
                br.a().a(context);
            }
        }
    }

    public boolean acceptCookie() {
        br a2 = br.a();
        return (a2 == null || !a2.b()) ? android.webkit.CookieManager.getInstance().acceptCookie() : a2.c().d();
    }

    public synchronized boolean acceptThirdPartyCookies(WebView webView) {
        boolean booleanValue;
        br a2 = br.a();
        if (a2 != null && a2.b()) {
            Object invokeStaticMethod = a2.c().b().invokeStaticMethod("com.tencent.tbs.tbsshell.WebCoreProxy", "cookieManager_acceptThirdPartyCookies", new Class[]{Object.class}, webView.getView());
            booleanValue = invokeStaticMethod != null ? ((Boolean) invokeStaticMethod).booleanValue() : true;
        } else if (Build.VERSION.SDK_INT < 21) {
            booleanValue = true;
        } else {
            Object a3 = v.a((Object) android.webkit.CookieManager.getInstance(), "acceptThirdPartyCookies", (Class<?>[]) new Class[]{WebView.class}, webView.getView());
            booleanValue = a3 != null ? ((Boolean) a3).booleanValue() : false;
        }
        return booleanValue;
    }

    public void flush() {
        br a2 = br.a();
        if (a2 != null && a2.b()) {
            a2.c().b().invokeStaticMethod("com.tencent.tbs.tbsshell.WebCoreProxy", "cookieManager_flush", new Class[0], new Object[0]);
        } else if (Build.VERSION.SDK_INT >= 21) {
            v.a((Object) android.webkit.CookieManager.getInstance(), "flush", (Class<?>[]) new Class[0], new Object[0]);
        }
    }

    public String getCookie(String str) {
        br a2 = br.a();
        if (a2 != null && a2.b()) {
            return a2.c().a(str);
        }
        try {
            return android.webkit.CookieManager.getInstance().getCookie(str);
        } catch (Throwable th) {
            th.printStackTrace();
            return null;
        }
    }

    public boolean hasCookies() {
        br a2 = br.a();
        return (a2 == null || !a2.b()) ? android.webkit.CookieManager.getInstance().hasCookies() : a2.c().h();
    }

    public void removeAllCookie() {
        if (this.a != null) {
            this.a.clear();
        }
        br a2 = br.a();
        if (a2 == null || !a2.b()) {
            android.webkit.CookieManager.getInstance().removeAllCookie();
        } else {
            a2.c().e();
        }
    }

    public void removeAllCookies(ValueCallback<Boolean> valueCallback) {
        if (this.a != null) {
            this.a.clear();
        }
        br a2 = br.a();
        if (a2 != null && a2.b()) {
            a2.c().b().invokeStaticMethod("com.tencent.tbs.tbsshell.WebCoreProxy", "cookieManager_removeAllCookies", new Class[]{ValueCallback.class}, valueCallback);
        } else if (Build.VERSION.SDK_INT >= 21) {
            v.a((Object) android.webkit.CookieManager.getInstance(), "removeAllCookies", (Class<?>[]) new Class[]{ValueCallback.class}, valueCallback);
        }
    }

    public void removeExpiredCookie() {
        br a2 = br.a();
        if (a2 == null || !a2.b()) {
            android.webkit.CookieManager.getInstance().removeExpiredCookie();
        } else {
            a2.c().b().invokeStaticMethod("com.tencent.tbs.tbsshell.WebCoreProxy", "cookieManager_removeExpiredCookie", new Class[0], new Object[0]);
        }
    }

    public void removeSessionCookie() {
        br a2 = br.a();
        if (a2 == null || !a2.b()) {
            android.webkit.CookieManager.getInstance().removeSessionCookie();
        } else {
            a2.c().b().invokeStaticMethod("com.tencent.tbs.tbsshell.WebCoreProxy", "cookieManager_removeSessionCookie", new Class[0], new Object[0]);
        }
    }

    public void removeSessionCookies(ValueCallback<Boolean> valueCallback) {
        br a2 = br.a();
        if (a2 != null && a2.b()) {
            a2.c().b().invokeStaticMethod("com.tencent.tbs.tbsshell.WebCoreProxy", "cookieManager_removeSessionCookies", new Class[]{ValueCallback.class}, valueCallback);
        } else if (Build.VERSION.SDK_INT >= 21) {
            v.a((Object) android.webkit.CookieManager.getInstance(), "removeSessionCookies", (Class<?>[]) new Class[]{ValueCallback.class}, valueCallback);
        }
    }

    public synchronized void setAcceptCookie(boolean z) {
        br a2 = br.a();
        if (a2 == null || !a2.b()) {
            try {
                android.webkit.CookieManager.getInstance().setAcceptCookie(z);
            } catch (Throwable th) {
                th.printStackTrace();
            }
        } else {
            a2.c().b().invokeStaticMethod("com.tencent.tbs.tbsshell.WebCoreProxy", "cookieManager_setAcceptCookie", new Class[]{Boolean.TYPE}, Boolean.valueOf(z));
        }
        return;
    }

    public synchronized void setAcceptThirdPartyCookies(WebView webView, boolean z) {
        br a2 = br.a();
        if (a2 != null && a2.b()) {
            a2.c().b().invokeStaticMethod("com.tencent.tbs.tbsshell.WebCoreProxy", "cookieManager_setAcceptThirdPartyCookies", new Class[]{Object.class, Boolean.TYPE}, webView.getView(), Boolean.valueOf(z));
        } else if (Build.VERSION.SDK_INT >= 21) {
            v.a((Object) android.webkit.CookieManager.getInstance(), "setAcceptThirdPartyCookies", (Class<?>[]) new Class[]{WebView.class, Boolean.TYPE}, webView.getView(), Boolean.valueOf(z));
        }
    }

    public synchronized void setCookie(String str, String str2) {
        setCookie(str, str2, false);
    }

    public synchronized void setCookie(String str, String str2, ValueCallback<Boolean> valueCallback) {
        br a2 = br.a();
        if (a2 == null || !a2.b()) {
            if (!br.a().d()) {
                b bVar = new b();
                bVar.a = 1;
                bVar.b = str;
                bVar.c = str2;
                bVar.d = valueCallback;
                if (this.a == null) {
                    this.a = new ArrayList<>();
                }
                this.a.add(bVar);
            }
            if (this.f && Build.VERSION.SDK_INT >= 21) {
                v.a((Object) android.webkit.CookieManager.getInstance(), "setCookie", (Class<?>[]) new Class[]{String.class, String.class, ValueCallback.class}, str, str2, valueCallback);
            }
        } else {
            a2.c().b().invokeStaticMethod("com.tencent.tbs.tbsshell.WebCoreProxy", "cookieManager_setCookie", new Class[]{String.class, String.class, ValueCallback.class}, str, str2, valueCallback);
        }
    }

    public synchronized void setCookie(String str, String str2, boolean z) {
        br a2 = br.a();
        if (a2 == null || !a2.b()) {
            if (this.f || z) {
                android.webkit.CookieManager.getInstance().setCookie(str, str2);
            }
            if (!br.a().d()) {
                b bVar = new b();
                bVar.a = 2;
                bVar.b = str;
                bVar.c = str2;
                bVar.d = null;
                if (this.a == null) {
                    this.a = new ArrayList<>();
                }
                this.a.add(bVar);
            }
        } else {
            a2.c().b().invokeStaticMethod("com.tencent.tbs.tbsshell.WebCoreProxy", "cookieManager_setCookie", new Class[]{String.class, String.class}, str, str2);
        }
    }

    public boolean setCookieCompatialbeMode(Context context, a aVar, String str, boolean z) {
        System.currentTimeMillis();
        if (context == null || !TbsExtensionFunctionManager.getInstance().canUseFunction(context, TbsExtensionFunctionManager.COOKIE_SWITCH_FILE_NAME)) {
            return false;
        }
        this.c = aVar;
        if (str != null) {
            this.b = str;
        }
        if (this.c != a.MODE_NONE && z && !br.a().d()) {
            br.a().a(context);
        }
        return true;
    }

    public void setCookies(Map<String, String[]> map) {
        br a2 = br.a();
        if (!((a2 == null || !a2.b()) ? false : a2.c().a(map))) {
            for (String next : map.keySet()) {
                for (String cookie : map.get(next)) {
                    setCookie(next, cookie);
                }
            }
        }
    }
}
