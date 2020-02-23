package com.tencent.smtt.sdk.a;

import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import com.google.android.gms.drive.DriveFile;
import com.tencent.mtt.spcialcall.sdk.MttLoader;
import com.tencent.smtt.sdk.TbsConfig;
import com.tencent.smtt.sdk.WebView;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class d {

    public static class a {
        public int a = -1;
        public int b = -1;
        public String c = "";
        public String d = "0";
        public String e = null;
    }

    private static class b {
        public String a;
        public String b;

        private b() {
            this.a = "";
            this.b = "";
        }
    }

    public static int a(Context context, String str, HashMap<String, String> hashMap, WebView webView) {
        Intent intent;
        Set<String> keySet;
        if (context == null) {
            return 3;
        }
        if (!a(str)) {
            str = "http://" + str;
        }
        try {
            Uri parse = Uri.parse(str);
            if (parse == null) {
                return 2;
            }
            a a2 = a(context);
            if (a2.a == -1) {
                return 4;
            }
            if (a2.a == 2 && a2.b < 33) {
                return 5;
            }
            Intent intent2 = new Intent("android.intent.action.VIEW");
            if (a2.a == 2) {
                if (a2.b >= 33 && a2.b <= 39) {
                    intent2.setClassName(TbsConfig.APP_QB, "com.tencent.mtt.MainActivity");
                    intent = intent2;
                } else if (a2.b < 40 || a2.b > 45) {
                    if (a2.b >= 46) {
                        Intent intent3 = new Intent("com.tencent.QQBrowser.action.VIEW");
                        b a3 = a(context, parse);
                        if (a3 != null && !TextUtils.isEmpty(a3.a)) {
                            intent3.setClassName(a3.b, a3.a);
                        }
                        intent = intent3;
                    }
                    intent = intent2;
                } else {
                    intent2.setClassName(TbsConfig.APP_QB, "com.tencent.mtt.SplashActivity");
                    intent = intent2;
                }
            } else if (a2.a == 1) {
                if (a2.b == 1) {
                    intent2.setClassName("com.tencent.qbx5", "com.tencent.qbx5.MainActivity");
                    intent = intent2;
                } else {
                    if (a2.b == 2) {
                        intent2.setClassName("com.tencent.qbx5", "com.tencent.qbx5.SplashActivity");
                        intent = intent2;
                    }
                    intent = intent2;
                }
            } else if (a2.a != 0) {
                Intent intent4 = new Intent("com.tencent.QQBrowser.action.VIEW");
                b a4 = a(context, parse);
                if (a4 != null && !TextUtils.isEmpty(a4.a)) {
                    intent4.setClassName(a4.b, a4.a);
                }
                intent = intent4;
            } else if (a2.b < 4 || a2.b > 6) {
                if (a2.b > 6) {
                    Intent intent5 = new Intent("com.tencent.QQBrowser.action.VIEW");
                    b a5 = a(context, parse);
                    if (a5 != null && !TextUtils.isEmpty(a5.a)) {
                        intent5.setClassName(a5.b, a5.a);
                    }
                    intent = intent5;
                }
                intent = intent2;
            } else {
                intent2.setClassName("com.tencent.qbx", "com.tencent.qbx.SplashActivity");
                intent = intent2;
            }
            intent.setData(parse);
            if (!(hashMap == null || (keySet = hashMap.keySet()) == null)) {
                for (String next : keySet) {
                    String str2 = hashMap.get(next);
                    if (!TextUtils.isEmpty(str2)) {
                        intent.putExtra(next, str2);
                    }
                }
            }
            try {
                intent.putExtra("loginType", d(context));
                intent.addFlags(DriveFile.MODE_READ_ONLY);
                if (webView != null) {
                    intent.putExtra("AnchorPoint", new Point(webView.getScrollX(), webView.getScrollY()));
                    intent.putExtra("ContentSize", new Point(webView.getContentWidth(), webView.getContentHeight()));
                }
                context.startActivity(intent);
                return 0;
            } catch (ActivityNotFoundException e) {
                return 4;
            }
        } catch (Exception e2) {
            return 2;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:4:0x000c, code lost:
        r0 = r0.getPackageInfo(com.tencent.smtt.sdk.TbsConfig.APP_QB, 0);
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static int a(android.content.Context r5, java.lang.String r6, java.util.HashMap<java.lang.String, java.lang.String> r7, java.lang.String r8, com.tencent.smtt.sdk.WebView r9) {
        /*
            r1 = 0
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            android.content.pm.PackageManager r0 = r5.getPackageManager()     // Catch:{ Throwable -> 0x0072 }
            if (r0 == 0) goto L_0x007a
            java.lang.String r2 = "com.tencent.mtt"
            r4 = 0
            android.content.pm.PackageInfo r0 = r0.getPackageInfo(r2, r4)     // Catch:{ Throwable -> 0x0072 }
            if (r0 == 0) goto L_0x007a
            int r0 = r0.versionCode     // Catch:{ Throwable -> 0x0072 }
            r2 = 601000(0x92ba8, float:8.4218E-40)
            if (r0 <= r2) goto L_0x007a
            r0 = 1
        L_0x001d:
            r2 = r0
        L_0x001e:
            java.lang.String r0 = "UTF-8"
            java.lang.String r0 = java.net.URLEncoder.encode(r6, r0)     // Catch:{ Exception -> 0x0078 }
            if (r2 == 0) goto L_0x0027
            r6 = r0
        L_0x0027:
            r1 = r2
        L_0x0028:
            if (r1 == 0) goto L_0x0075
            java.lang.String r0 = ",encoded=1"
        L_0x002c:
            java.lang.String r1 = "mttbrowser://url="
            java.lang.StringBuilder r1 = r3.append(r1)
            java.lang.StringBuilder r1 = r1.append(r6)
            java.lang.String r2 = ",product="
            java.lang.StringBuilder r1 = r1.append(r2)
            java.lang.String r2 = "TBS"
            java.lang.StringBuilder r1 = r1.append(r2)
            java.lang.String r2 = ",packagename="
            java.lang.StringBuilder r1 = r1.append(r2)
            java.lang.String r2 = r5.getPackageName()
            java.lang.StringBuilder r1 = r1.append(r2)
            java.lang.String r2 = ",from="
            java.lang.StringBuilder r1 = r1.append(r2)
            java.lang.StringBuilder r1 = r1.append(r8)
            java.lang.String r2 = ",version="
            java.lang.StringBuilder r1 = r1.append(r2)
            java.lang.String r2 = "3.6.0.1211"
            java.lang.StringBuilder r1 = r1.append(r2)
            r1.append(r0)
            java.lang.String r0 = r3.toString()
            int r0 = a(r5, r0, r7, r9)
            return r0
        L_0x0072:
            r0 = move-exception
            r2 = r1
            goto L_0x001e
        L_0x0075:
            java.lang.String r0 = ""
            goto L_0x002c
        L_0x0078:
            r0 = move-exception
            goto L_0x0028
        L_0x007a:
            r0 = r1
            goto L_0x001d
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.smtt.sdk.a.d.a(android.content.Context, java.lang.String, java.util.HashMap, java.lang.String, com.tencent.smtt.sdk.WebView):int");
    }

    public static a a(Context context) {
        boolean z = context.getApplicationContext().getSharedPreferences("x5_proxy_setting", 0).getBoolean("qb_install_status", false);
        a aVar = new a();
        if (z) {
            return aVar;
        }
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = null;
            try {
                packageInfo = packageManager.getPackageInfo(TbsConfig.APP_QB, 0);
                aVar.a = 2;
                aVar.e = TbsConfig.APP_QB;
                aVar.c = "ADRQB_";
                if (packageInfo != null && packageInfo.versionCode > 420000) {
                    aVar.b = packageInfo.versionCode;
                    aVar.c += packageInfo.versionName.replaceAll("\\.", "");
                    aVar.d = packageInfo.versionName.replaceAll("\\.", "");
                    return aVar;
                }
            } catch (PackageManager.NameNotFoundException e) {
            }
            try {
                packageInfo = packageManager.getPackageInfo("com.tencent.qbx", 0);
                aVar.a = 0;
                aVar.e = "com.tencent.qbx";
                aVar.c = "ADRQBX_";
            } catch (PackageManager.NameNotFoundException e2) {
                try {
                    packageInfo = packageManager.getPackageInfo("com.tencent.qbx5", 0);
                    aVar.a = 1;
                    aVar.e = "com.tencent.qbx5";
                    aVar.c = "ADRQBX5_";
                } catch (PackageManager.NameNotFoundException e3) {
                    try {
                        packageInfo = packageManager.getPackageInfo(TbsConfig.APP_QB, 0);
                        aVar.e = TbsConfig.APP_QB;
                        aVar.a = 2;
                        aVar.c = "ADRQB_";
                    } catch (PackageManager.NameNotFoundException e4) {
                        try {
                            packageInfo = packageManager.getPackageInfo("com.tencent.mtt.x86", 0);
                            aVar.e = "com.tencent.mtt.x86";
                            aVar.a = 2;
                            aVar.c = "ADRQB_";
                        } catch (Exception e5) {
                            try {
                                b a2 = a(context, Uri.parse(MttLoader.QQBROWSER_DOWNLOAD_URL));
                                if (a2 != null && !TextUtils.isEmpty(a2.b)) {
                                    packageInfo = packageManager.getPackageInfo(a2.b, 0);
                                    aVar.e = a2.b;
                                    aVar.a = 2;
                                    aVar.c = "ADRQB_";
                                }
                            } catch (Exception e6) {
                            }
                        }
                    }
                }
            }
            if (packageInfo != null) {
                aVar.b = packageInfo.versionCode;
                aVar.c += packageInfo.versionName.replaceAll("\\.", "");
                aVar.d = packageInfo.versionName.replaceAll("\\.", "");
            }
        } catch (Exception e7) {
        }
        return aVar;
    }

    private static b a(Context context, Uri uri) {
        Intent intent = new Intent("com.tencent.QQBrowser.action.VIEW");
        intent.setData(uri);
        List<ResolveInfo> queryIntentActivities = context.getPackageManager().queryIntentActivities(intent, 0);
        if (queryIntentActivities.size() <= 0) {
            return null;
        }
        b bVar = new b();
        for (ResolveInfo next : queryIntentActivities) {
            String str = next.activityInfo.packageName;
            if (str.contains(TbsConfig.APP_QB)) {
                bVar.a = next.activityInfo.name;
                bVar.b = next.activityInfo.packageName;
                return bVar;
            } else if (str.contains("com.tencent.qbx")) {
                bVar.a = next.activityInfo.name;
                bVar.b = next.activityInfo.packageName;
            }
        }
        return bVar;
    }

    public static boolean a(Context context, long j, long j2) {
        a a2 = a(context);
        boolean z = false;
        try {
            if (Long.valueOf(a2.d).longValue() >= j) {
                z = true;
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        if (((long) a2.b) >= j2) {
            return true;
        }
        return z;
    }

    public static boolean a(Context context, String str, int i, String str2, HashMap<String, String> hashMap, Bundle bundle) {
        Set<String> keySet;
        try {
            Intent intent = new Intent("com.tencent.QQBrowser.action.sdk.document");
            if (!(hashMap == null || (keySet = hashMap.keySet()) == null)) {
                for (String next : keySet) {
                    String str3 = hashMap.get(next);
                    if (!TextUtils.isEmpty(str3)) {
                        intent.putExtra(next, str3);
                    }
                }
            }
            File file = new File(str);
            intent.putExtra("key_reader_sdk_id", 3);
            intent.putExtra("key_reader_sdk_type", i);
            if (i == 0) {
                intent.putExtra("key_reader_sdk_path", str);
            } else if (i == 1) {
                intent.putExtra("key_reader_sdk_url", str);
            }
            intent.putExtra("key_reader_sdk_format", str2);
            intent.setDataAndType(Uri.fromFile(file), "mtt/" + str2);
            intent.putExtra("loginType", d(context.getApplicationContext()));
            if (bundle != null) {
                intent.putExtra("key_reader_sdk_extrals", bundle);
            }
            context.startActivity(intent);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean a(Context context, String str, HashMap<String, String> hashMap) {
        boolean z;
        Set<String> keySet;
        Uri parse = Uri.parse(str);
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.setFlags(DriveFile.MODE_READ_ONLY);
        intent.setDataAndType(parse, "video/*");
        if (!(hashMap == null || (keySet = hashMap.keySet()) == null)) {
            for (String next : keySet) {
                String str2 = hashMap.get(next);
                if (!TextUtils.isEmpty(str2)) {
                    intent.putExtra(next, str2);
                }
            }
        }
        try {
            intent.putExtra("loginType", d(context));
            intent.setComponent(new ComponentName(TbsConfig.APP_QB, "com.tencent.mtt.browser.video.H5VideoThrdcallActivity"));
            context.startActivity(intent);
            z = true;
        } catch (Throwable th) {
            z = false;
        }
        if (!z) {
            try {
                intent.setComponent((ComponentName) null);
                context.startActivity(intent);
            } catch (Throwable th2) {
                th2.printStackTrace();
                return false;
            }
        }
        return true;
    }

    private static boolean a(String str) {
        if (str == null || str.length() == 0) {
            return false;
        }
        String trim = str.trim();
        int indexOf = trim.toLowerCase().indexOf("://");
        int indexOf2 = trim.toLowerCase().indexOf(46);
        if (indexOf <= 0 || indexOf2 <= 0 || indexOf <= indexOf2) {
            return trim.toLowerCase().contains("://");
        }
        return false;
    }

    public static boolean b(Context context) {
        return a(context).a != -1;
    }

    public static boolean c(Context context) {
        a a2 = a(context);
        boolean z = false;
        try {
            if (Long.valueOf(a2.d).longValue() >= 6001500) {
                z = true;
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        if (a2.b >= 601500) {
            return true;
        }
        return z;
    }

    private static int d(Context context) {
        String str = context.getApplicationInfo().processName;
        if (str.equals(TbsConfig.APP_QQ)) {
            return 13;
        }
        if (str.equals(TbsConfig.APP_QZONE)) {
            return 14;
        }
        if (str.equals("com.tencent.WBlog")) {
            return 15;
        }
        return str.equals(TbsConfig.APP_WX) ? 24 : 26;
    }
}
