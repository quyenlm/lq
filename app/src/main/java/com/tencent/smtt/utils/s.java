package com.tencent.smtt.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import com.google.android.gms.drive.DriveFile;
import com.tencent.imsdk.framework.request.HttpRequest;
import com.tencent.smtt.sdk.TbsConfig;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.a.d;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class s {
    private static s a = null;
    private Handler b;

    private s() {
        this.b = null;
        this.b = new t(this, Looper.getMainLooper());
    }

    private int a(Context context) {
        if (a(TbsConfig.APP_QB, context, 128) != null) {
            return 2;
        }
        return !TextUtils.isEmpty(e(context)) ? 1 : 0;
    }

    public static s a() {
        if (a == null) {
            a = new s();
        }
        return a;
    }

    private Map<String, String> a(String str) {
        HashMap hashMap;
        String[] split;
        if (str == null || str.length() <= 0) {
            return null;
        }
        try {
            hashMap = new HashMap();
            try {
                for (String str2 : str.split("\n")) {
                    if (str2 != null && str2.length() > 0 && (split = str2.trim().split(HttpRequest.HTTP_REQ_ENTITY_MERGE, 2)) != null && split.length >= 2) {
                        String str3 = split[0];
                        String str4 = split[1];
                        if (str3 != null && str3.length() > 0) {
                            hashMap.put(str3, str4);
                        }
                    }
                }
                return hashMap;
            } catch (Throwable th) {
                th = th;
                th.printStackTrace();
                return hashMap;
            }
        } catch (Throwable th2) {
            th = th2;
            hashMap = null;
            th.printStackTrace();
            return hashMap;
        }
    }

    private void b(Context context) {
        Message message = new Message();
        message.what = 1;
        message.obj = new Object[]{context};
        this.b.sendMessage(message);
    }

    public static void b(Context context, String str) {
        if (context != null && str != null && str.length() > 0) {
            d.a(context, str, (HashMap<String, String>) null, (WebView) null);
        }
    }

    /* access modifiers changed from: private */
    public void c(Context context) {
        try {
            String e = e(context);
            if (!TextUtils.isEmpty(e)) {
                File file = new File(e);
                if (file.exists()) {
                    Intent intent = new Intent("android.intent.action.VIEW");
                    intent.addFlags(DriveFile.MODE_READ_ONLY);
                    intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
                    context.startActivity(intent);
                    u.a(context).a(context.getApplicationInfo().processName);
                }
            }
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    private void c(Context context, String str) {
        Message message = new Message();
        message.what = 0;
        message.obj = new Object[]{context, str};
        this.b.sendMessage(message);
    }

    /* JADX WARNING: Removed duplicated region for block: B:29:0x0053 A[SYNTHETIC, Splitter:B:29:0x0053] */
    /* JADX WARNING: Removed duplicated region for block: B:35:0x005f A[SYNTHETIC, Splitter:B:35:0x005f] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private java.util.Map<java.lang.String, java.lang.String> d(android.content.Context r7) {
        /*
            r6 = this;
            r2 = 0
            java.lang.String r0 = ""
            java.lang.String r3 = "/data/data/com.tencent.mm/app_tbs/share/QQBrowserDownloadInfo.ini"
            java.lang.String r4 = "/data/data/com.tencent.mobileqq/app_tbs/share/QQBrowserDownloadInfo.ini"
            java.lang.String r5 = "/data/data/com.qzone/app_tbs/share/QQBrowserDownloadInfo.ini"
            java.io.File r1 = new java.io.File     // Catch:{ Throwable -> 0x004d }
            r1.<init>(r3)     // Catch:{ Throwable -> 0x004d }
            if (r1 == 0) goto L_0x0016
            boolean r3 = r1.exists()     // Catch:{ Throwable -> 0x004d }
            if (r3 != 0) goto L_0x001b
        L_0x0016:
            java.io.File r1 = new java.io.File     // Catch:{ Throwable -> 0x004d }
            r1.<init>(r4)     // Catch:{ Throwable -> 0x004d }
        L_0x001b:
            if (r1 == 0) goto L_0x0023
            boolean r3 = r1.exists()     // Catch:{ Throwable -> 0x004d }
            if (r3 != 0) goto L_0x0028
        L_0x0023:
            java.io.File r1 = new java.io.File     // Catch:{ Throwable -> 0x004d }
            r1.<init>(r5)     // Catch:{ Throwable -> 0x004d }
        L_0x0028:
            if (r1 == 0) goto L_0x0070
            boolean r3 = r1.exists()     // Catch:{ Throwable -> 0x004d }
            if (r3 == 0) goto L_0x0070
            java.io.FileInputStream r3 = new java.io.FileInputStream     // Catch:{ Throwable -> 0x004d }
            r3.<init>(r1)     // Catch:{ Throwable -> 0x004d }
            byte[] r2 = com.tencent.smtt.utils.k.b((java.io.InputStream) r3)     // Catch:{ Throwable -> 0x006d, all -> 0x006a }
            if (r2 == 0) goto L_0x0043
            java.lang.String r1 = new java.lang.String     // Catch:{ Throwable -> 0x006d, all -> 0x006a }
            java.lang.String r4 = "utf-8"
            r1.<init>(r2, r4)     // Catch:{ Throwable -> 0x006d, all -> 0x006a }
            r0 = r1
        L_0x0043:
            if (r3 == 0) goto L_0x0048
            r3.close()     // Catch:{ IOException -> 0x0068 }
        L_0x0048:
            java.util.Map r0 = r6.a((java.lang.String) r0)
            return r0
        L_0x004d:
            r1 = move-exception
        L_0x004e:
            r1.printStackTrace()     // Catch:{ all -> 0x005c }
            if (r2 == 0) goto L_0x0048
            r2.close()     // Catch:{ IOException -> 0x0057 }
            goto L_0x0048
        L_0x0057:
            r1 = move-exception
        L_0x0058:
            r1.printStackTrace()
            goto L_0x0048
        L_0x005c:
            r0 = move-exception
        L_0x005d:
            if (r2 == 0) goto L_0x0062
            r2.close()     // Catch:{ IOException -> 0x0063 }
        L_0x0062:
            throw r0
        L_0x0063:
            r1 = move-exception
            r1.printStackTrace()
            goto L_0x0062
        L_0x0068:
            r1 = move-exception
            goto L_0x0058
        L_0x006a:
            r0 = move-exception
            r2 = r3
            goto L_0x005d
        L_0x006d:
            r1 = move-exception
            r2 = r3
            goto L_0x004e
        L_0x0070:
            r3 = r2
            goto L_0x0043
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.smtt.utils.s.d(android.content.Context):java.util.Map");
    }

    private void d(Context context, String str) {
        Message message = new Message();
        message.what = 2;
        message.obj = new Object[]{context, str};
        this.b.sendMessage(message);
    }

    private String e(Context context) {
        Map<String, String> d = d(context);
        if (d != null && d.size() > 0) {
            String str = d.get("FileDownloadPath");
            String str2 = d.get("FileDownloadVerifyInfo");
            if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
                return "";
            }
            File file = new File(str);
            if (file.exists() && TextUtils.equals(o.a(file.lastModified() + ""), str2)) {
                return str;
            }
        }
        return "";
    }

    /* access modifiers changed from: private */
    public void e(Context context, String str) {
        if (context != null && str != null && str.length() > 0) {
            Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(str));
            intent.addFlags(DriveFile.MODE_READ_ONLY);
            context.startActivity(intent);
        }
    }

    public PackageInfo a(String str, Context context, int i) {
        if (context == null || TextUtils.isEmpty(str)) {
            return null;
        }
        try {
            return context.getPackageManager().getPackageInfo(str, i);
        } catch (Throwable th) {
            return null;
        }
    }

    public boolean a(Context context, String str) {
        String str2;
        String str3;
        if (str != null) {
            try {
                if (str.startsWith("tbsqbdownload://")) {
                    String[] split = str.substring("tbsqbdownload://".length()).split(",");
                    if (split.length > 1) {
                        String[] split2 = split[0].split(HttpRequest.HTTP_REQ_ENTITY_MERGE);
                        str3 = (split2.length <= 1 || !"url".equalsIgnoreCase(split2[0])) ? null : split[0].substring("url".length() + 1);
                        String[] split3 = split[1].split(HttpRequest.HTTP_REQ_ENTITY_MERGE);
                        str2 = (split3.length <= 1 || !"downloadurl".equalsIgnoreCase(split3[0])) ? null : split[1].substring("downloadurl".length() + 1);
                    } else {
                        str2 = null;
                        str3 = null;
                    }
                    if (str3 == null || str2 == null) {
                        return false;
                    }
                    int a2 = a(context);
                    if (a2 == 2) {
                        c(context, str3);
                        return true;
                    } else if (a2 == 1) {
                        b(context);
                        return true;
                    } else if (a2 != 0) {
                        return true;
                    } else {
                        d(context, str2);
                        return true;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}
