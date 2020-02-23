package com.tencent.smtt.sdk;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.support.v4.provider.FontsContractCompat;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.ValueCallback;
import android.webkit.WebIconDatabase;
import android.webkit.WebStorage;
import android.webkit.WebView;
import android.webkit.WebViewDatabase;
import com.tencent.smtt.export.external.DexLoader;
import com.tencent.smtt.export.external.interfaces.IX5WebViewBase;
import com.tencent.smtt.sdk.TbsDownloadConfig;
import com.tencent.smtt.sdk.TbsListener;
import com.tencent.smtt.sdk.a.d;
import com.tencent.smtt.utils.TbsLog;
import com.tencent.smtt.utils.TbsLogClient;
import com.tencent.smtt.utils.k;
import com.tencent.smtt.utils.v;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

@SuppressLint({"NewApi"})
public class QbSdk {
    /* access modifiers changed from: private */
    public static TbsListener A = null;
    /* access modifiers changed from: private */
    public static TbsListener B = null;
    private static boolean C = false;
    private static boolean D = false;
    public static final int EXTENSION_INIT_FAILURE = -99999;
    public static String KEY_SET_SENDREQUEST_AND_UPLOAD = "SET_SENDREQUEST_AND_UPLOAD";
    public static final String LOGIN_TYPE_KEY_PARTNER_CALL_POS = "PosID";
    public static final String LOGIN_TYPE_KEY_PARTNER_ID = "ChannelID";
    public static final String PARAM_KEY_FEATUREID = "param_key_featureid";
    public static final String PARAM_KEY_FUNCTIONID = "param_key_functionid";
    public static final String PARAM_KEY_POSITIONID = "param_key_positionid";
    public static final int QBMODE = 2;
    public static final String SVNVERSION = "jnizz";
    public static final int TBSMODE = 1;
    public static final String TID_QQNumber_Prefix = "QQ:";
    public static final int VERSION = 1;
    static boolean a = false;
    static boolean b = false;
    static boolean c = true;
    static String d;
    static boolean e = false;
    static long f = 0;
    static long g = 0;
    static Object h = new Object();
    static boolean i = true;
    static boolean j = false;
    static volatile boolean k = a;
    static TbsListener l = new n();
    static Map<String, Object> m = null;
    private static int n = 0;
    private static String o = "";
    private static Class<?> p;
    private static Object q;
    private static boolean r = false;
    private static String[] s;
    public static boolean sIsVersionPrinted = false;
    private static String t = "NULL";
    private static String u = "UNKNOWN";
    private static int v = 0;
    private static int w = 170;
    private static String x = null;
    private static String y = null;
    private static boolean z = true;

    public interface PreInitCallback {
        void onCoreInitFinished();

        void onViewInitFinished(boolean z);
    }

    static Bundle a(Context context, Bundle bundle) {
        if (!a(context)) {
            TbsLogReport.a(context).a((int) TbsListener.ErrorCode.INCR_UPDATE_ERROR, "initForPatch return false!");
            return null;
        }
        Object a2 = v.a(q, "incrUpdate", (Class<?>[]) new Class[]{Context.class, Bundle.class}, context, bundle);
        if (a2 != null) {
            return (Bundle) a2;
        }
        TbsLogReport.a(context).a((int) TbsListener.ErrorCode.INCR_UPDATE_ERROR, "incrUpdate return null!");
        return null;
    }

    static Object a(Context context, String str, Bundle bundle) {
        if (!a(context)) {
            return Integer.valueOf(EXTENSION_INIT_FAILURE);
        }
        Object a2 = v.a(q, "miscCall", (Class<?>[]) new Class[]{String.class, Bundle.class}, str, bundle);
        if (a2 == null) {
            return null;
        }
        return a2;
    }

    static String a() {
        return o;
    }

    static synchronized void a(Context context, String str) {
        synchronized (QbSdk.class) {
            if (!a) {
                a = true;
                u = "forceSysWebViewInner: " + str;
                TbsLog.e("QbSdk", "QbSdk.SysWebViewForcedInner..." + u);
                TbsCoreLoadStat.getInstance().a(context, 401, new Throwable(u));
            }
        }
    }

    static boolean a(Context context) {
        try {
            if (p != null) {
                return true;
            }
            File q2 = am.a().q(context);
            if (q2 == null) {
                TbsLog.e("QbSdk", "QbSdk initExtension (false) optDir == null");
                return false;
            }
            File file = new File(q2, "tbs_sdk_extension_dex.jar");
            if (!file.exists()) {
                TbsLog.e("QbSdk", "QbSdk initExtension (false) dexFile.exists()=false", true);
                return false;
            }
            TbsLog.i("QbSdk", "new DexLoader #3 dexFile is " + file.getAbsolutePath());
            p = new DexLoader(file.getParent(), context, new String[]{file.getAbsolutePath()}, q2.getAbsolutePath(), (Map<String, Object>) null).loadClass("com.tencent.tbs.sdk.extension.TbsSDKExtension");
            b(context, file.getParent());
            return true;
        } catch (Throwable th) {
            TbsLog.e("QbSdk", "initExtension sys WebView: " + Log.getStackTraceString(th));
            return false;
        }
    }

    static boolean a(Context context, int i2) {
        return a(context, i2, 20000);
    }

    static boolean a(Context context, int i2, int i3) {
        if (m != null && m.containsKey(KEY_SET_SENDREQUEST_AND_UPLOAD) && m.get(KEY_SET_SENDREQUEST_AND_UPLOAD).equals("false")) {
            TbsLog.i("QbSdk", "[QbSdk.isX5Disabled] -- SET_SENDREQUEST_AND_UPLOAD is false");
            return true;
        } else if (!c(context)) {
            return true;
        } else {
            Object a2 = v.a(q, "isX5Disabled", (Class<?>[]) new Class[]{Integer.TYPE, Integer.TYPE, Integer.TYPE}, Integer.valueOf(i2), 43600, Integer.valueOf(i3));
            if (a2 != null) {
                return ((Boolean) a2).booleanValue();
            }
            Object a3 = v.a(q, "isX5Disabled", (Class<?>[]) new Class[]{Integer.TYPE, Integer.TYPE}, Integer.valueOf(i2), 43600);
            if (a3 != null) {
                return ((Boolean) a3).booleanValue();
            }
            return true;
        }
    }

    @SuppressLint({"NewApi"})
    private static boolean a(Context context, boolean z2) {
        int i2;
        File file;
        TbsLog.initIfNeed(context);
        if (!sIsVersionPrinted) {
            TbsLog.i("QbSdk", "svn revision: jnizz; SDK_VERSION_CODE: 43600; SDK_VERSION_NAME: 3.6.0.1211");
            sIsVersionPrinted = true;
        }
        if (a && !z2) {
            TbsLog.e("QbSdk", "QbSdk init: " + u, false);
            TbsCoreLoadStat.getInstance().a(context, 414, new Throwable(u));
            return false;
        } else if (b) {
            TbsLog.e("QbSdk", "QbSdk init mIsSysWebViewForcedByOuter = true", true);
            TbsCoreLoadStat.getInstance().a(context, 402, new Throwable(t));
            return false;
        } else {
            if (!z) {
                d(context);
            }
            try {
                File q2 = am.a().q(context);
                if (q2 == null) {
                    TbsLog.e("QbSdk", "QbSdk init (false) optDir == null");
                    TbsCoreLoadStat.getInstance().a(context, TbsListener.ErrorCode.ERROR_TBSCORE_SHARE_DIR, new Throwable("QbSdk.init (false) TbsCoreShareDir is null"));
                    return false;
                }
                if (!TbsShareManager.isThirdPartyApp(context)) {
                    if (n != 0) {
                        i2 = am.a().a(true, context);
                        if (n != i2) {
                            p = null;
                            q = null;
                            TbsLog.e("QbSdk", "QbSdk init (false) not isThirdPartyApp tbsCoreInstalledVer=" + i2, true);
                            TbsLog.e("QbSdk", "QbSdk init (false) not isThirdPartyApp sTbsVersion=" + n, true);
                            TbsCoreLoadStat.getInstance().a(context, 303, new Throwable("sTbsVersion: " + n + "; tbsCoreInstalledVer: " + i2));
                            return false;
                        }
                    } else {
                        i2 = 0;
                    }
                    n = i2;
                } else if (n == 0 || n == TbsShareManager.d(context)) {
                    n = TbsShareManager.d(context);
                } else {
                    p = null;
                    q = null;
                    TbsLog.e("QbSdk", "QbSdk init (false) ERROR_UNMATCH_TBSCORE_VER_THIRDPARTY!");
                    TbsCoreLoadStat.getInstance().a(context, 302, new Throwable("sTbsVersion: " + n + "; AvailableTbsCoreVersion: " + TbsShareManager.d(context)));
                    return false;
                }
                if (p != null) {
                    return true;
                }
                if (!TbsShareManager.isThirdPartyApp(context)) {
                    file = new File(am.a().q(context), "tbs_sdk_extension_dex.jar");
                } else if (TbsShareManager.j(context)) {
                    file = new File(TbsShareManager.c(context), "tbs_sdk_extension_dex.jar");
                } else {
                    TbsCoreLoadStat.getInstance().a(context, 304, new Throwable("isShareTbsCoreAvailable false!"));
                    return false;
                }
                if (!file.exists()) {
                    TbsLog.e("QbSdk", "QbSdk init (false) tbs_sdk_extension_dex.jar is not exist!");
                    int i3 = am.a().i(context);
                    if (new File(file.getParentFile(), "tbs_jars_fusion_dex.jar").exists()) {
                        if (i3 > 0) {
                            TbsCoreLoadStat.getInstance().a(context, TbsListener.ErrorCode.INFO_MISS_SDKEXTENSION_JAR_WITH_FUSION_DEX_WITH_CORE, new Exception("tbs_sdk_extension_dex not exist(with fusion dex)!" + i3));
                        } else {
                            TbsCoreLoadStat.getInstance().a(context, TbsListener.ErrorCode.INFO_MISS_SDKEXTENSION_JAR_WITH_FUSION_DEX_WITHOUT_CORE, new Exception("tbs_sdk_extension_dex not exist(with fusion dex)!" + i3));
                        }
                        return false;
                    }
                    if (i3 > 0) {
                        TbsCoreLoadStat.getInstance().a(context, TbsListener.ErrorCode.INFO_INFO_MISS_SDKEXTENSION_JAR_WITHOUT_FUSION_DEX_WITH_CORE, new Exception("tbs_sdk_extension_dex not exist(without fusion dex)!" + i3));
                    } else {
                        TbsCoreLoadStat.getInstance().a(context, TbsListener.ErrorCode.INFO_INFO_MISS_SDKEXTENSION_JAR_WITHOUT_FUSION_DEX_WITHOUT_CORE, new Exception("tbs_sdk_extension_dex not exist(without fusion dex)!" + i3));
                    }
                    return false;
                }
                String hostCorePathAppDefined = TbsShareManager.getHostCorePathAppDefined() != null ? TbsShareManager.getHostCorePathAppDefined() : q2.getAbsolutePath();
                TbsLog.i("QbSdk", "QbSdk init optDirExtension #1 is " + hostCorePathAppDefined);
                TbsLog.i("QbSdk", "new DexLoader #1 dexFile is " + file.getAbsolutePath());
                p = new DexLoader(file.getParent(), context, new String[]{file.getAbsolutePath()}, hostCorePathAppDefined, (Map<String, Object>) null).loadClass("com.tencent.tbs.sdk.extension.TbsSDKExtension");
                b(context, file.getParent());
                v.a(q, "setClientVersion", (Class<?>[]) new Class[]{Integer.TYPE}, 1);
                return true;
            } catch (Throwable th) {
                TbsLog.e("QbSdk", "QbSdk init Throwable: " + Log.getStackTraceString(th));
                TbsCoreLoadStat.getInstance().a(context, TbsListener.ErrorCode.THROWABLE_QBSDK_INIT, th);
                return false;
            }
        }
    }

    static boolean a(Context context, boolean z2, boolean z3) {
        int i2;
        boolean z4 = true;
        boolean z5 = false;
        if (TbsShareManager.isThirdPartyApp(context) && !TbsShareManager.i(context)) {
            TbsCoreLoadStat.getInstance().a(context, 302);
        } else if (!a(context, z2)) {
            TbsLog.e("QbSdk", "QbSdk.init failure!");
        } else {
            Object a2 = v.a(q, "canLoadX5Core", (Class<?>[]) new Class[]{Integer.TYPE}, 43600);
            if (a2 == null) {
                Object a3 = v.a(q, "canLoadX5", (Class<?>[]) new Class[]{Integer.TYPE}, Integer.valueOf(a.a()));
                if (a3 == null) {
                    TbsCoreLoadStat.getInstance().a(context, TbsListener.ErrorCode.ERROR_CANLOADX5_RETURN_NULL);
                } else if (!(a3 instanceof String) || !((String) a3).equalsIgnoreCase("AuthenticationFail")) {
                    if (a3 instanceof Boolean) {
                        n = o.d();
                        boolean a4 = a(context, o.d());
                        if (((Boolean) a3).booleanValue() && !a4) {
                            z5 = true;
                        }
                        if (!z5) {
                            TbsLog.e(TbsListener.tag_load_error, "318");
                            TbsLog.w(TbsListener.tag_load_error, "isX5Disable:" + a4);
                            TbsLog.w(TbsListener.tag_load_error, "(Boolean) ret:" + ((Boolean) a3));
                        }
                    }
                }
            } else if (!(a2 instanceof String) || !((String) a2).equalsIgnoreCase("AuthenticationFail")) {
                if (!(a2 instanceof Bundle)) {
                    TbsCoreLoadStat.getInstance().a(context, TbsListener.ErrorCode.ERROR_QBSDK_INIT_ERROR_RET_TYPE_NOT_BUNDLE, new Throwable("" + a2));
                    TbsLog.e(TbsListener.tag_load_error, "ret not instance of bundle");
                } else {
                    Bundle bundle = (Bundle) a2;
                    if (bundle.isEmpty()) {
                        TbsCoreLoadStat.getInstance().a(context, TbsListener.ErrorCode.ERROR_QBSDK_INIT_ERROR_EMPTY_BUNDLE, new Throwable("" + a2));
                        TbsLog.e(TbsListener.tag_load_error, "empty bundle");
                    } else {
                        try {
                            i2 = bundle.getInt(FontsContractCompat.Columns.RESULT_CODE, -1);
                        } catch (Exception e2) {
                            TbsLog.e("QbSdk", "bundle.getInt(KEY_RESULT_CODE) error : " + e2.toString());
                            i2 = -1;
                        }
                        boolean z6 = i2 == 0;
                        if (TbsShareManager.isThirdPartyApp(context)) {
                            o.a(TbsShareManager.d(context));
                            o = String.valueOf(TbsShareManager.d(context));
                            if (o.length() == 5) {
                                o = "0" + o;
                            }
                            if (o.length() != 6) {
                                o = "";
                            }
                        } else {
                            if (Build.VERSION.SDK_INT >= 12) {
                                o = bundle.getString("tbs_core_version", "0");
                            } else {
                                o = bundle.getString("tbs_core_version");
                                if (o == null) {
                                    o = "0";
                                }
                            }
                            try {
                                n = Integer.parseInt(o);
                            } catch (NumberFormatException e3) {
                                n = 0;
                            }
                            o.a(n);
                            if (n == 0) {
                                TbsCoreLoadStat.getInstance().a(context, 307, new Throwable("sTbsVersion is 0"));
                            } else {
                                if ((n <= 0 || n > 25442) && n != 25472) {
                                    z4 = false;
                                }
                                if (z4) {
                                    TbsLog.e(TbsDownloader.LOGTAG, "is_obsolete --> delete old core:" + n);
                                    k.b(am.a().q(context));
                                    TbsCoreLoadStat.getInstance().a(context, 307, new Throwable("is_obsolete --> delete old core:" + n));
                                }
                            }
                        }
                        try {
                            s = bundle.getStringArray("tbs_jarfiles");
                            if (!(s instanceof String[])) {
                                TbsCoreLoadStat.getInstance().a(context, 307, new Throwable("sJarFiles not instanceof String[]: " + s));
                            } else {
                                d = bundle.getString("tbs_librarypath");
                                Object obj = null;
                                if (i2 != 0) {
                                    try {
                                        obj = v.a(q, "getErrorCodeForLogReport", (Class<?>[]) new Class[0], new Object[0]);
                                    } catch (Exception e4) {
                                        e4.printStackTrace();
                                    }
                                }
                                switch (i2) {
                                    case -2:
                                        if (!(obj instanceof Integer)) {
                                            TbsCoreLoadStat.getInstance().a(context, 404, new Throwable("detail: " + obj));
                                            break;
                                        } else {
                                            TbsCoreLoadStat.getInstance().a(context, ((Integer) obj).intValue(), new Throwable("detail: " + obj));
                                            break;
                                        }
                                    case -1:
                                        if (!(obj instanceof Integer)) {
                                            TbsCoreLoadStat.getInstance().a(context, 307, new Throwable("detail: " + obj));
                                            break;
                                        } else {
                                            TbsCoreLoadStat.getInstance().a(context, ((Integer) obj).intValue(), new Throwable("detail: " + obj));
                                            break;
                                        }
                                    case 0:
                                        break;
                                    default:
                                        TbsCoreLoadStat.getInstance().a(context, 415, new Throwable("detail: " + obj + "errcode" + i2));
                                        break;
                                }
                                z5 = z6;
                            }
                        } catch (Throwable th) {
                            TbsCoreLoadStat.getInstance().a(context, TbsListener.ErrorCode.ERROR_GETSTRINGARRAY_JARFILE, th);
                        }
                    }
                }
            }
            if (!z5) {
                TbsLog.e(TbsListener.tag_load_error, "319");
            }
        }
        return z5;
    }

    protected static String b() {
        Object invokeStaticMethod;
        br a2 = br.a();
        if (a2 == null || !a2.b() || (invokeStaticMethod = a2.c().b().invokeStaticMethod("com.tencent.tbs.tbsshell.WebCoreProxy", "getGUID", new Class[0], new Object[0])) == null || !(invokeStaticMethod instanceof String)) {
            return null;
        }
        return (String) invokeStaticMethod;
    }

    /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static void b(android.content.Context r7, java.lang.String r8) {
        /*
            r2 = 0
            r0 = 1
            r1 = 0
            java.lang.Class<?> r3 = p     // Catch:{ Throwable -> 0x0048 }
            r4 = 5
            java.lang.Class[] r4 = new java.lang.Class[r4]     // Catch:{ Throwable -> 0x0048 }
            r5 = 0
            java.lang.Class<android.content.Context> r6 = android.content.Context.class
            r4[r5] = r6     // Catch:{ Throwable -> 0x0048 }
            r5 = 1
            java.lang.Class<android.content.Context> r6 = android.content.Context.class
            r4[r5] = r6     // Catch:{ Throwable -> 0x0048 }
            r5 = 2
            java.lang.Class<java.lang.String> r6 = java.lang.String.class
            r4[r5] = r6     // Catch:{ Throwable -> 0x0048 }
            r5 = 3
            java.lang.Class<java.lang.String> r6 = java.lang.String.class
            r4[r5] = r6     // Catch:{ Throwable -> 0x0048 }
            r5 = 4
            java.lang.Class<java.lang.String> r6 = java.lang.String.class
            r4[r5] = r6     // Catch:{ Throwable -> 0x0048 }
            java.lang.reflect.Constructor r1 = r3.getConstructor(r4)     // Catch:{ Throwable -> 0x0048 }
            r2 = r1
        L_0x0026:
            boolean r1 = com.tencent.smtt.sdk.TbsShareManager.isThirdPartyApp(r7)     // Catch:{ Throwable -> 0x007e }
            if (r1 == 0) goto L_0x00c1
            android.content.Context r1 = com.tencent.smtt.sdk.TbsShareManager.e(r7)     // Catch:{ Throwable -> 0x007e }
            if (r1 != 0) goto L_0x004b
            java.lang.String r3 = com.tencent.smtt.sdk.TbsShareManager.getHostCorePathAppDefined()     // Catch:{ Throwable -> 0x007e }
            if (r3 != 0) goto L_0x004b
            android.content.Context r0 = r7.getApplicationContext()     // Catch:{ Throwable -> 0x007e }
            com.tencent.smtt.sdk.TbsLogReport r0 = com.tencent.smtt.sdk.TbsLogReport.a((android.content.Context) r0)     // Catch:{ Throwable -> 0x007e }
            r1 = 227(0xe3, float:3.18E-43)
            java.lang.String r2 = "host context is null!"
            r0.b((int) r1, (java.lang.String) r2)     // Catch:{ Throwable -> 0x007e }
        L_0x0047:
            return
        L_0x0048:
            r0 = move-exception
            r0 = r1
            goto L_0x0026
        L_0x004b:
            if (r0 != 0) goto L_0x00a3
            if (r1 != 0) goto L_0x0080
            java.lang.Class<?> r0 = p     // Catch:{ Throwable -> 0x007e }
            r2 = 3
            java.lang.Class[] r2 = new java.lang.Class[r2]     // Catch:{ Throwable -> 0x007e }
            r3 = 0
            java.lang.Class<android.content.Context> r4 = android.content.Context.class
            r2[r3] = r4     // Catch:{ Throwable -> 0x007e }
            r3 = 1
            java.lang.Class<android.content.Context> r4 = android.content.Context.class
            r2[r3] = r4     // Catch:{ Throwable -> 0x007e }
            r3 = 2
            java.lang.Class<java.lang.String> r4 = java.lang.String.class
            r2[r3] = r4     // Catch:{ Throwable -> 0x007e }
            java.lang.reflect.Constructor r0 = r0.getConstructor(r2)     // Catch:{ Throwable -> 0x007e }
            r2 = 3
            java.lang.Object[] r2 = new java.lang.Object[r2]     // Catch:{ Throwable -> 0x007e }
            r3 = 0
            r2[r3] = r7     // Catch:{ Throwable -> 0x007e }
            r3 = 1
            r2[r3] = r1     // Catch:{ Throwable -> 0x007e }
            r1 = 2
            java.lang.String r3 = com.tencent.smtt.sdk.TbsShareManager.getHostCorePathAppDefined()     // Catch:{ Throwable -> 0x007e }
            r2[r1] = r3     // Catch:{ Throwable -> 0x007e }
            java.lang.Object r0 = r0.newInstance(r2)     // Catch:{ Throwable -> 0x007e }
            q = r0     // Catch:{ Throwable -> 0x007e }
            goto L_0x0047
        L_0x007e:
            r0 = move-exception
            goto L_0x0047
        L_0x0080:
            java.lang.Class<?> r0 = p     // Catch:{ Throwable -> 0x007e }
            r2 = 2
            java.lang.Class[] r2 = new java.lang.Class[r2]     // Catch:{ Throwable -> 0x007e }
            r3 = 0
            java.lang.Class<android.content.Context> r4 = android.content.Context.class
            r2[r3] = r4     // Catch:{ Throwable -> 0x007e }
            r3 = 1
            java.lang.Class<android.content.Context> r4 = android.content.Context.class
            r2[r3] = r4     // Catch:{ Throwable -> 0x007e }
            java.lang.reflect.Constructor r0 = r0.getConstructor(r2)     // Catch:{ Throwable -> 0x007e }
            r2 = 2
            java.lang.Object[] r2 = new java.lang.Object[r2]     // Catch:{ Throwable -> 0x007e }
            r3 = 0
            r2[r3] = r7     // Catch:{ Throwable -> 0x007e }
            r3 = 1
            r2[r3] = r1     // Catch:{ Throwable -> 0x007e }
            java.lang.Object r0 = r0.newInstance(r2)     // Catch:{ Throwable -> 0x007e }
            q = r0     // Catch:{ Throwable -> 0x007e }
            goto L_0x0047
        L_0x00a3:
            r0 = 5
            java.lang.Object[] r0 = new java.lang.Object[r0]     // Catch:{ Throwable -> 0x007e }
            r3 = 0
            r0[r3] = r7     // Catch:{ Throwable -> 0x007e }
            r3 = 1
            r0[r3] = r1     // Catch:{ Throwable -> 0x007e }
            r1 = 2
            java.lang.String r3 = com.tencent.smtt.sdk.TbsShareManager.getHostCorePathAppDefined()     // Catch:{ Throwable -> 0x007e }
            r0[r1] = r3     // Catch:{ Throwable -> 0x007e }
            r1 = 3
            r0[r1] = r8     // Catch:{ Throwable -> 0x007e }
            r1 = 4
            r3 = 0
            r0[r1] = r3     // Catch:{ Throwable -> 0x007e }
            java.lang.Object r0 = r2.newInstance(r0)     // Catch:{ Throwable -> 0x007e }
            q = r0     // Catch:{ Throwable -> 0x007e }
            goto L_0x0047
        L_0x00c1:
            if (r0 != 0) goto L_0x00e7
            java.lang.Class<?> r0 = p     // Catch:{ Throwable -> 0x007e }
            r1 = 2
            java.lang.Class[] r1 = new java.lang.Class[r1]     // Catch:{ Throwable -> 0x007e }
            r2 = 0
            java.lang.Class<android.content.Context> r3 = android.content.Context.class
            r1[r2] = r3     // Catch:{ Throwable -> 0x007e }
            r2 = 1
            java.lang.Class<android.content.Context> r3 = android.content.Context.class
            r1[r2] = r3     // Catch:{ Throwable -> 0x007e }
            java.lang.reflect.Constructor r0 = r0.getConstructor(r1)     // Catch:{ Throwable -> 0x007e }
            r1 = 2
            java.lang.Object[] r1 = new java.lang.Object[r1]     // Catch:{ Throwable -> 0x007e }
            r2 = 0
            r1[r2] = r7     // Catch:{ Throwable -> 0x007e }
            r2 = 1
            r1[r2] = r7     // Catch:{ Throwable -> 0x007e }
            java.lang.Object r0 = r0.newInstance(r1)     // Catch:{ Throwable -> 0x007e }
            q = r0     // Catch:{ Throwable -> 0x007e }
            goto L_0x0047
        L_0x00e7:
            r0 = 5
            java.lang.Object[] r0 = new java.lang.Object[r0]     // Catch:{ Throwable -> 0x007e }
            r1 = 0
            r0[r1] = r7     // Catch:{ Throwable -> 0x007e }
            r1 = 1
            r0[r1] = r7     // Catch:{ Throwable -> 0x007e }
            r1 = 2
            r3 = 0
            r0[r1] = r3     // Catch:{ Throwable -> 0x007e }
            r1 = 3
            r0[r1] = r8     // Catch:{ Throwable -> 0x007e }
            r1 = 4
            r3 = 0
            r0[r1] = r3     // Catch:{ Throwable -> 0x007e }
            java.lang.Object r0 = r2.newInstance(r0)     // Catch:{ Throwable -> 0x007e }
            q = r0     // Catch:{ Throwable -> 0x007e }
            goto L_0x0047
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.smtt.sdk.QbSdk.b(android.content.Context, java.lang.String):void");
    }

    static boolean b(Context context) {
        SharedPreferences sharedPreferences;
        if (context == null) {
            return false;
        }
        try {
            if (context.getApplicationInfo().packageName.contains("com.tencent.portfolio")) {
                TbsLog.i("QbSdk", "clearPluginConfigFile #1");
                String string = TbsDownloadConfig.getInstance(context).mPreferences.getString(TbsDownloadConfig.TbsConfigKey.KEY_APP_VERSIONNAME, (String) null);
                String str = context.getPackageManager().getPackageInfo("com.tencent.portfolio", 0).versionName;
                TbsLog.i("QbSdk", "clearPluginConfigFile oldAppVersionName is " + string + " newAppVersionName is " + str);
                if (!(string == null || string.contains(str) || (sharedPreferences = context.getSharedPreferences("plugin_setting", 0)) == null)) {
                    SharedPreferences.Editor edit = sharedPreferences.edit();
                    edit.clear();
                    edit.commit();
                    TbsLog.i("QbSdk", "clearPluginConfigFile done");
                }
            }
            return true;
        } catch (Throwable th) {
            TbsLog.i("QbSdk", "clearPluginConfigFile error is " + th.getMessage());
            return false;
        }
    }

    private static boolean c(Context context) {
        File file;
        try {
            if (p != null) {
                return true;
            }
            File q2 = am.a().q(context);
            if (q2 == null) {
                TbsLog.e("QbSdk", "QbSdk initForX5DisableConfig (false) optDir == null");
                return false;
            }
            if (!TbsShareManager.isThirdPartyApp(context)) {
                file = new File(am.a().q(context), "tbs_sdk_extension_dex.jar");
            } else if (TbsShareManager.j(context)) {
                file = new File(TbsShareManager.c(context), "tbs_sdk_extension_dex.jar");
            } else {
                TbsCoreLoadStat.getInstance().a(context, 304);
                return false;
            }
            if (!file.exists()) {
                TbsCoreLoadStat.getInstance().a(context, 406, new Exception("initForX5DisableConfig failure -- tbs_sdk_extension_dex.jar is not exist!"));
                return false;
            }
            String hostCorePathAppDefined = TbsShareManager.getHostCorePathAppDefined() != null ? TbsShareManager.getHostCorePathAppDefined() : q2.getAbsolutePath();
            TbsLog.i("QbSdk", "QbSdk init optDirExtension #3 is " + hostCorePathAppDefined);
            TbsLog.i("QbSdk", "new DexLoader #4 dexFile is " + file.getAbsolutePath());
            p = new DexLoader(file.getParent(), context, new String[]{file.getAbsolutePath()}, hostCorePathAppDefined, (Map<String, Object>) null).loadClass("com.tencent.tbs.sdk.extension.TbsSDKExtension");
            b(context, file.getParent());
            v.a(q, "setClientVersion", (Class<?>[]) new Class[]{Integer.TYPE}, 1);
            return true;
        } catch (Throwable th) {
            TbsLog.e("QbSdk", "initForX5DisableConfig sys WebView: " + Log.getStackTraceString(th));
            return false;
        }
    }

    public static boolean canLoadVideo(Context context) {
        Object a2 = v.a(q, "canLoadVideo", (Class<?>[]) new Class[]{Integer.TYPE}, 0);
        if (a2 == null) {
            TbsCoreLoadStat.getInstance().a(context, TbsListener.ErrorCode.ERROR_CANLOADVIDEO_RETURN_NULL);
        } else if (!((Boolean) a2).booleanValue()) {
            TbsCoreLoadStat.getInstance().a(context, TbsListener.ErrorCode.ERROR_CANLOADVIDEO_RETURN_FALSE);
        }
        if (a2 == null) {
            return false;
        }
        return ((Boolean) a2).booleanValue();
    }

    public static boolean canLoadX5(Context context) {
        return a(context, false, false);
    }

    public static boolean canLoadX5FirstTimeThirdApp(Context context) {
        try {
            if (p == null) {
                File q2 = am.a().q(context);
                if (q2 == null) {
                    TbsLog.e("QbSdk", "QbSdk canLoadX5FirstTimeThirdApp (false) optDir == null");
                    return false;
                }
                File file = new File(TbsShareManager.c(context), "tbs_sdk_extension_dex.jar");
                if (!file.exists()) {
                    TbsLog.e("QbSdk", "QbSdk canLoadX5FirstTimeThirdApp (false) dexFile.exists()=false", true);
                    return false;
                }
                String hostCorePathAppDefined = TbsShareManager.getHostCorePathAppDefined() != null ? TbsShareManager.getHostCorePathAppDefined() : q2.getAbsolutePath();
                TbsLog.i("QbSdk", "QbSdk init optDirExtension #2 is " + hostCorePathAppDefined);
                TbsLog.i("QbSdk", "new DexLoader #2 dexFile is " + file.getAbsolutePath());
                p = new DexLoader(file.getParent(), context, new String[]{file.getAbsolutePath()}, hostCorePathAppDefined, (Map<String, Object>) null).loadClass("com.tencent.tbs.sdk.extension.TbsSDKExtension");
                if (q == null) {
                    if (TbsShareManager.e(context) == null && TbsShareManager.getHostCorePathAppDefined() == null) {
                        TbsLogReport.a(context.getApplicationContext()).b((int) TbsListener.ErrorCode.HOST_CONTEXT_IS_NULL, "host context is null!");
                        return false;
                    }
                    b(context, file.getParent());
                }
            }
            Object a2 = v.a(q, "canLoadX5CoreForThirdApp", (Class<?>[]) new Class[0], new Object[0]);
            if (a2 == null || !(a2 instanceof Boolean)) {
                return false;
            }
            return ((Boolean) a2).booleanValue();
        } catch (Throwable th) {
            TbsLog.e("QbSdk", "canLoadX5FirstTimeThirdApp sys WebView: " + Log.getStackTraceString(th));
            return false;
        }
    }

    public static void canOpenFile(Context context, String str, ValueCallback<Boolean> valueCallback) {
        new h(context, str, valueCallback).start();
    }

    public static boolean canOpenMimeFileType(Context context, String str) {
        if (!a(context, false)) {
        }
        return false;
    }

    /* JADX WARNING: Removed duplicated region for block: B:48:0x00fb A[SYNTHETIC, Splitter:B:48:0x00fb] */
    /* JADX WARNING: Removed duplicated region for block: B:78:0x0148 A[SYNTHETIC, Splitter:B:78:0x0148] */
    /* JADX WARNING: Removed duplicated region for block: B:81:0x014c  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean canOpenWebPlus(android.content.Context r9) {
        /*
            r4 = 0
            r8 = 88888888(0x54c5638, float:9.60787E-36)
            r1 = 1
            r2 = 0
            int r0 = v
            if (r0 != 0) goto L_0x0010
            int r0 = com.tencent.smtt.sdk.a.a()
            v = r0
        L_0x0010:
            java.lang.String r0 = "QbSdk"
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r5 = "canOpenWebPlus - totalRAM: "
            java.lang.StringBuilder r3 = r3.append(r5)
            int r5 = v
            java.lang.StringBuilder r3 = r3.append(r5)
            java.lang.String r3 = r3.toString()
            com.tencent.smtt.utils.TbsLog.i(r0, r3)
            int r0 = android.os.Build.VERSION.SDK_INT
            r3 = 7
            if (r0 < r3) goto L_0x0035
            int r0 = v
            int r3 = w
            if (r0 >= r3) goto L_0x0036
        L_0x0035:
            return r2
        L_0x0036:
            if (r9 == 0) goto L_0x0035
            java.io.File r0 = new java.io.File     // Catch:{ Throwable -> 0x00e1, all -> 0x00f7 }
            com.tencent.smtt.sdk.am r3 = com.tencent.smtt.sdk.am.a()     // Catch:{ Throwable -> 0x00e1, all -> 0x00f7 }
            java.io.File r3 = r3.q(r9)     // Catch:{ Throwable -> 0x00e1, all -> 0x00f7 }
            java.lang.String r5 = "tbs.conf"
            r0.<init>(r3, r5)     // Catch:{ Throwable -> 0x00e1, all -> 0x00f7 }
            java.io.FileInputStream r5 = new java.io.FileInputStream     // Catch:{ Throwable -> 0x00e1, all -> 0x00f7 }
            r5.<init>(r0)     // Catch:{ Throwable -> 0x00e1, all -> 0x00f7 }
            java.io.BufferedInputStream r3 = new java.io.BufferedInputStream     // Catch:{ Throwable -> 0x00e1, all -> 0x00f7 }
            r3.<init>(r5)     // Catch:{ Throwable -> 0x00e1, all -> 0x00f7 }
            java.util.Properties r0 = new java.util.Properties     // Catch:{ Throwable -> 0x0164, all -> 0x015f }
            r0.<init>()     // Catch:{ Throwable -> 0x0164, all -> 0x015f }
            r0.load(r3)     // Catch:{ Throwable -> 0x0164, all -> 0x015f }
            java.lang.String r5 = "android_sdk_max_supported"
            java.lang.String r5 = r0.getProperty(r5)     // Catch:{ Throwable -> 0x0164, all -> 0x015f }
            java.lang.String r6 = "android_sdk_min_supported"
            java.lang.String r6 = r0.getProperty(r6)     // Catch:{ Throwable -> 0x0164, all -> 0x015f }
            int r5 = java.lang.Integer.parseInt(r5)     // Catch:{ Throwable -> 0x0164, all -> 0x015f }
            int r6 = java.lang.Integer.parseInt(r6)     // Catch:{ Throwable -> 0x0164, all -> 0x015f }
            java.lang.String r7 = android.os.Build.VERSION.SDK     // Catch:{ Throwable -> 0x0164, all -> 0x015f }
            int r7 = java.lang.Integer.parseInt(r7)     // Catch:{ Throwable -> 0x0164, all -> 0x015f }
            if (r7 > r5) goto L_0x0077
            if (r7 >= r6) goto L_0x0097
        L_0x0077:
            java.lang.String r0 = "QbSdk"
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0164, all -> 0x015f }
            r1.<init>()     // Catch:{ Throwable -> 0x0164, all -> 0x015f }
            java.lang.String r4 = "canOpenWebPlus - sdkVersion: "
            java.lang.StringBuilder r1 = r1.append(r4)     // Catch:{ Throwable -> 0x0164, all -> 0x015f }
            java.lang.StringBuilder r1 = r1.append(r7)     // Catch:{ Throwable -> 0x0164, all -> 0x015f }
            java.lang.String r1 = r1.toString()     // Catch:{ Throwable -> 0x0164, all -> 0x015f }
            com.tencent.smtt.utils.TbsLog.i(r0, r1)     // Catch:{ Throwable -> 0x0164, all -> 0x015f }
            if (r3 == 0) goto L_0x0035
            r3.close()     // Catch:{ Exception -> 0x0095 }
            goto L_0x0035
        L_0x0095:
            r0 = move-exception
            goto L_0x0035
        L_0x0097:
            java.lang.String r5 = "tbs_core_version"
            java.lang.String r0 = r0.getProperty(r5)     // Catch:{ Throwable -> 0x0164, all -> 0x015f }
            int r0 = java.lang.Integer.parseInt(r0)     // Catch:{ Throwable -> 0x0164, all -> 0x015f }
            if (r3 == 0) goto L_0x00a6
            r3.close()     // Catch:{ Exception -> 0x014e }
        L_0x00a6:
            java.io.File r5 = new java.io.File     // Catch:{ Throwable -> 0x0132, all -> 0x0144 }
            java.io.File r3 = com.tencent.smtt.sdk.am.s(r9)     // Catch:{ Throwable -> 0x0132, all -> 0x0144 }
            java.lang.String r6 = "tbs_extension.conf"
            r5.<init>(r3, r6)     // Catch:{ Throwable -> 0x0132, all -> 0x0144 }
            java.io.FileInputStream r3 = new java.io.FileInputStream     // Catch:{ Throwable -> 0x0132, all -> 0x0144 }
            r3.<init>(r5)     // Catch:{ Throwable -> 0x0132, all -> 0x0144 }
            java.util.Properties r4 = new java.util.Properties     // Catch:{ Throwable -> 0x015c, all -> 0x0157 }
            r4.<init>()     // Catch:{ Throwable -> 0x015c, all -> 0x0157 }
            r4.load(r3)     // Catch:{ Throwable -> 0x015c, all -> 0x0157 }
            java.lang.String r5 = "tbs_local_version"
            java.lang.String r5 = r4.getProperty(r5)     // Catch:{ Throwable -> 0x015c, all -> 0x0157 }
            int r5 = java.lang.Integer.parseInt(r5)     // Catch:{ Throwable -> 0x015c, all -> 0x0157 }
            java.lang.String r6 = "app_versioncode_for_switch"
            java.lang.String r6 = r4.getProperty(r6)     // Catch:{ Throwable -> 0x015c, all -> 0x0157 }
            int r6 = java.lang.Integer.parseInt(r6)     // Catch:{ Throwable -> 0x015c, all -> 0x0157 }
            if (r0 == r8) goto L_0x00d6
            if (r5 != r8) goto L_0x00ff
        L_0x00d6:
            r0 = r2
        L_0x00d7:
            if (r3 == 0) goto L_0x00dc
            r3.close()     // Catch:{ Exception -> 0x0153 }
        L_0x00dc:
            if (r0 != 0) goto L_0x014c
        L_0x00de:
            r2 = r1
            goto L_0x0035
        L_0x00e1:
            r0 = move-exception
            r1 = r4
        L_0x00e3:
            r0.printStackTrace()     // Catch:{ all -> 0x0161 }
            java.lang.String r0 = "QbSdk"
            java.lang.String r3 = "canOpenWebPlus - canLoadX5 Exception"
            com.tencent.smtt.utils.TbsLog.i(r0, r3)     // Catch:{ all -> 0x0161 }
            if (r1 == 0) goto L_0x0035
            r1.close()     // Catch:{ Exception -> 0x00f4 }
            goto L_0x0035
        L_0x00f4:
            r0 = move-exception
            goto L_0x0035
        L_0x00f7:
            r0 = move-exception
            r3 = r4
        L_0x00f9:
            if (r3 == 0) goto L_0x00fe
            r3.close()     // Catch:{ Exception -> 0x0151 }
        L_0x00fe:
            throw r0
        L_0x00ff:
            if (r0 <= r5) goto L_0x0103
            r0 = r2
            goto L_0x00d7
        L_0x0103:
            if (r0 != r5) goto L_0x0168
            if (r6 <= 0) goto L_0x010f
            int r0 = com.tencent.smtt.utils.b.b(r9)     // Catch:{ Throwable -> 0x015c, all -> 0x0157 }
            if (r6 == r0) goto L_0x010f
            r0 = r2
            goto L_0x00d7
        L_0x010f:
            java.lang.String r0 = "x5_disabled"
            java.lang.String r0 = r4.getProperty(r0)     // Catch:{ Throwable -> 0x015c, all -> 0x0157 }
            boolean r0 = java.lang.Boolean.parseBoolean(r0)     // Catch:{ Throwable -> 0x015c, all -> 0x0157 }
            if (r0 == 0) goto L_0x0130
            android.content.Context r0 = r9.getApplicationContext()     // Catch:{ Throwable -> 0x015c, all -> 0x0157 }
            com.tencent.smtt.sdk.TbsDownloadConfig r0 = com.tencent.smtt.sdk.TbsDownloadConfig.getInstance(r0)     // Catch:{ Throwable -> 0x015c, all -> 0x0157 }
            android.content.SharedPreferences r0 = r0.mPreferences     // Catch:{ Throwable -> 0x015c, all -> 0x0157 }
            java.lang.String r4 = "switch_backupcore_enable"
            r5 = 0
            boolean r0 = r0.getBoolean(r4, r5)     // Catch:{ Throwable -> 0x015c, all -> 0x0157 }
            if (r0 != 0) goto L_0x0130
            r0 = r1
            goto L_0x00d7
        L_0x0130:
            r0 = r2
            goto L_0x00d7
        L_0x0132:
            r0 = move-exception
        L_0x0133:
            java.lang.String r0 = "QbSdk"
            java.lang.String r3 = "canOpenWebPlus - isX5Disabled Exception"
            com.tencent.smtt.utils.TbsLog.i(r0, r3)     // Catch:{ all -> 0x0159 }
            if (r4 == 0) goto L_0x013f
            r4.close()     // Catch:{ Exception -> 0x0141 }
        L_0x013f:
            r0 = r1
            goto L_0x00dc
        L_0x0141:
            r0 = move-exception
            r0 = r1
            goto L_0x00dc
        L_0x0144:
            r0 = move-exception
            r3 = r4
        L_0x0146:
            if (r3 == 0) goto L_0x014b
            r3.close()     // Catch:{ Exception -> 0x0155 }
        L_0x014b:
            throw r0
        L_0x014c:
            r1 = r2
            goto L_0x00de
        L_0x014e:
            r3 = move-exception
            goto L_0x00a6
        L_0x0151:
            r1 = move-exception
            goto L_0x00fe
        L_0x0153:
            r3 = move-exception
            goto L_0x00dc
        L_0x0155:
            r1 = move-exception
            goto L_0x014b
        L_0x0157:
            r0 = move-exception
            goto L_0x0146
        L_0x0159:
            r0 = move-exception
            r3 = r4
            goto L_0x0146
        L_0x015c:
            r0 = move-exception
            r4 = r3
            goto L_0x0133
        L_0x015f:
            r0 = move-exception
            goto L_0x00f9
        L_0x0161:
            r0 = move-exception
            r3 = r1
            goto L_0x00f9
        L_0x0164:
            r0 = move-exception
            r1 = r3
            goto L_0x00e3
        L_0x0168:
            r0 = r2
            goto L_0x00d7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.smtt.sdk.QbSdk.canOpenWebPlus(android.content.Context):boolean");
    }

    public static boolean canUseVideoFeatrue(Context context, int i2) {
        Object a2 = v.a(q, "canUseVideoFeatrue", (Class<?>[]) new Class[]{Integer.TYPE}, Integer.valueOf(i2));
        if (a2 == null || !(a2 instanceof Boolean)) {
            return false;
        }
        return ((Boolean) a2).booleanValue();
    }

    public static void clear(Context context) {
    }

    public static void clearAllWebViewCache(Context context, boolean z2) {
        br a2;
        try {
            WebView webView = new WebView(context);
            if (Build.VERSION.SDK_INT >= 11) {
                webView.removeJavascriptInterface("searchBoxJavaBridge_");
                webView.removeJavascriptInterface("accessibility");
                webView.removeJavascriptInterface("accessibilityTraversal");
            }
            webView.clearCache(true);
            if (z2) {
                CookieSyncManager.createInstance(context);
                CookieManager.getInstance().removeAllCookie();
            }
            WebViewDatabase.getInstance(context).clearUsernamePassword();
            WebViewDatabase.getInstance(context).clearHttpAuthUsernamePassword();
            WebViewDatabase.getInstance(context).clearFormData();
            WebStorage.getInstance().deleteAllData();
            WebIconDatabase.getInstance().removeAllIcons();
        } catch (Throwable th) {
            TbsLog.e("QbSdk", "clearAllWebViewCache exception 1 -- " + Log.getStackTraceString(th));
        }
        try {
            if (new WebView(context).getWebViewClientExtension() != null && (a2 = br.a()) != null && a2.b()) {
                a2.c().a(context, z2);
            }
        } catch (Throwable th2) {
        }
    }

    public static void closeFileReader(Context context) {
        br a2 = br.a();
        a2.a(context);
        if (a2.b()) {
            a2.c().p();
        }
    }

    public static boolean createMiniQBShortCut(Context context, String str, String str2, Drawable drawable) {
        if (context == null) {
            return false;
        }
        if (TbsDownloader.getOverSea(context)) {
            return false;
        }
        if (isMiniQBShortCutExist(context, str, str2)) {
            return false;
        }
        br a2 = br.a();
        if (a2 == null || !a2.b()) {
            return false;
        }
        Bitmap bitmap = null;
        if (drawable instanceof BitmapDrawable) {
            bitmap = ((BitmapDrawable) drawable).getBitmap();
        }
        DexLoader b2 = a2.c().b();
        TbsLog.e("QbSdk", "qbsdk createMiniQBShortCut");
        Object invokeStaticMethod = b2.invokeStaticMethod("com.tencent.tbs.tbsshell.WebCoreProxy", "createMiniQBShortCut", new Class[]{Context.class, String.class, String.class, Bitmap.class}, context, str, str2, bitmap);
        TbsLog.e("QbSdk", "qbsdk after createMiniQBShortCut ret: " + invokeStaticMethod);
        return invokeStaticMethod != null;
    }

    /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x0060  */
    /* JADX WARNING: Removed duplicated region for block: B:39:0x0113 A[ADDED_TO_REGION] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static void d(android.content.Context r10) {
        /*
            r9 = 3
            r7 = 4
            r5 = 0
            r1 = -1
            r0 = 1
            z = r0
            r3 = 0
            int r0 = android.os.Build.VERSION.SDK_INT     // Catch:{ Throwable -> 0x00ce }
            r2 = 11
            if (r0 < r2) goto L_0x0023
            java.lang.String r0 = "tbs_preloadx5_check_cfg_file"
            r2 = 4
            android.content.SharedPreferences r3 = r10.getSharedPreferences(r0, r2)     // Catch:{ Throwable -> 0x00ce }
        L_0x0015:
            java.lang.String r0 = "tbs_preload_x5_recorder"
            r2 = -1
            int r0 = r3.getInt(r0, r2)     // Catch:{ Throwable -> 0x00ce }
            if (r0 < 0) goto L_0x0184
            int r0 = r0 + 1
            if (r0 <= r7) goto L_0x002b
        L_0x0022:
            return
        L_0x0023:
            java.lang.String r0 = "tbs_preloadx5_check_cfg_file"
            r2 = 0
            android.content.SharedPreferences r3 = r10.getSharedPreferences(r0, r2)     // Catch:{ Throwable -> 0x00ce }
            goto L_0x0015
        L_0x002b:
            r2 = r0
            r6 = r0
        L_0x002d:
            com.tencent.smtt.sdk.am r0 = com.tencent.smtt.sdk.am.a()     // Catch:{ Throwable -> 0x017d }
            int r4 = r0.i(r10)     // Catch:{ Throwable -> 0x017d }
            if (r4 <= 0) goto L_0x0022
            if (r6 > r7) goto L_0x0046
            android.content.SharedPreferences$Editor r0 = r3.edit()     // Catch:{ Throwable -> 0x0181 }
            java.lang.String r7 = "tbs_preload_x5_recorder"
            android.content.SharedPreferences$Editor r0 = r0.putInt(r7, r6)     // Catch:{ Throwable -> 0x0181 }
            r0.commit()     // Catch:{ Throwable -> 0x0181 }
        L_0x0046:
            java.lang.String r0 = "tbs_preload_x5_counter"
            r6 = -1
            int r0 = r3.getInt(r0, r6)     // Catch:{ Throwable -> 0x0181 }
            if (r0 < 0) goto L_0x00ed
            android.content.SharedPreferences$Editor r6 = r3.edit()     // Catch:{ Throwable -> 0x0181 }
            java.lang.String r7 = "tbs_preload_x5_counter"
            int r0 = r0 + 1
            android.content.SharedPreferences$Editor r6 = r6.putInt(r7, r0)     // Catch:{ Throwable -> 0x0181 }
            r6.commit()     // Catch:{ Throwable -> 0x0181 }
        L_0x005e:
            if (r0 <= r9) goto L_0x0113
            java.lang.String r0 = "tbs_preload_x5_version"
            r1 = -1
            int r0 = r3.getInt(r0, r1)     // Catch:{ Throwable -> 0x00af }
            android.content.SharedPreferences$Editor r1 = r3.edit()     // Catch:{ Throwable -> 0x00af }
            if (r0 != r4) goto L_0x00f0
            com.tencent.smtt.sdk.am r2 = com.tencent.smtt.sdk.am.a()     // Catch:{ Throwable -> 0x00af }
            java.io.File r2 = r2.q(r10)     // Catch:{ Throwable -> 0x00af }
            r3 = 0
            com.tencent.smtt.utils.k.a((java.io.File) r2, (boolean) r3)     // Catch:{ Throwable -> 0x00af }
            com.tencent.smtt.sdk.ai r2 = com.tencent.smtt.sdk.ai.a((android.content.Context) r10)     // Catch:{ Throwable -> 0x00af }
            java.io.File r2 = r2.a()     // Catch:{ Throwable -> 0x00af }
            if (r2 == 0) goto L_0x0087
            r3 = 0
            com.tencent.smtt.utils.k.a((java.io.File) r2, (boolean) r3)     // Catch:{ Throwable -> 0x00af }
        L_0x0087:
            java.lang.String r2 = "QbSdk"
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x00af }
            r3.<init>()     // Catch:{ Throwable -> 0x00af }
            java.lang.String r5 = "QbSdk - preload_x5_check: tbs core "
            java.lang.StringBuilder r3 = r3.append(r5)     // Catch:{ Throwable -> 0x00af }
            java.lang.StringBuilder r3 = r3.append(r4)     // Catch:{ Throwable -> 0x00af }
            java.lang.String r4 = " is deleted!"
            java.lang.StringBuilder r3 = r3.append(r4)     // Catch:{ Throwable -> 0x00af }
            java.lang.String r3 = r3.toString()     // Catch:{ Throwable -> 0x00af }
            com.tencent.smtt.utils.TbsLog.e(r2, r3)     // Catch:{ Throwable -> 0x00af }
        L_0x00a5:
            java.lang.String r2 = "tbs_precheck_disable_version"
            r1.putInt(r2, r0)     // Catch:{ Throwable -> 0x00af }
            r1.commit()     // Catch:{ Throwable -> 0x00af }
            goto L_0x0022
        L_0x00af:
            r0 = move-exception
            java.lang.String r1 = "QbSdk"
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = "tbs_preload_x5_counter disable version exception:"
            java.lang.StringBuilder r2 = r2.append(r3)
            java.lang.String r0 = android.util.Log.getStackTraceString(r0)
            java.lang.StringBuilder r0 = r2.append(r0)
            java.lang.String r0 = r0.toString()
            com.tencent.smtt.utils.TbsLog.e(r1, r0)
            goto L_0x0022
        L_0x00ce:
            r0 = move-exception
            r2 = r1
            r4 = r1
        L_0x00d1:
            java.lang.String r6 = "QbSdk"
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r7.<init>()
            java.lang.String r8 = "tbs_preload_x5_counter Inc exception:"
            java.lang.StringBuilder r7 = r7.append(r8)
            java.lang.String r0 = android.util.Log.getStackTraceString(r0)
            java.lang.StringBuilder r0 = r7.append(r0)
            java.lang.String r0 = r0.toString()
            com.tencent.smtt.utils.TbsLog.e(r6, r0)
        L_0x00ed:
            r0 = r1
            goto L_0x005e
        L_0x00f0:
            java.lang.String r2 = "QbSdk"
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x00af }
            r3.<init>()     // Catch:{ Throwable -> 0x00af }
            java.lang.String r5 = "QbSdk - preload_x5_check -- reset exception core_ver:"
            java.lang.StringBuilder r3 = r3.append(r5)     // Catch:{ Throwable -> 0x00af }
            java.lang.StringBuilder r3 = r3.append(r4)     // Catch:{ Throwable -> 0x00af }
            java.lang.String r4 = "; value:"
            java.lang.StringBuilder r3 = r3.append(r4)     // Catch:{ Throwable -> 0x00af }
            java.lang.StringBuilder r3 = r3.append(r0)     // Catch:{ Throwable -> 0x00af }
            java.lang.String r3 = r3.toString()     // Catch:{ Throwable -> 0x00af }
            com.tencent.smtt.utils.TbsLog.e(r2, r3)     // Catch:{ Throwable -> 0x00af }
            goto L_0x00a5
        L_0x0113:
            if (r2 <= 0) goto L_0x012d
            if (r2 > r9) goto L_0x012d
            java.lang.String r0 = "QbSdk"
            java.lang.String r1 = "QbSdk - preload_x5_check -- before creation!"
            com.tencent.smtt.utils.TbsLog.i(r0, r1)
            com.tencent.smtt.sdk.br r0 = com.tencent.smtt.sdk.br.a()
            r0.a((android.content.Context) r10)
            java.lang.String r0 = "QbSdk"
            java.lang.String r1 = "QbSdk - preload_x5_check -- after creation!"
            com.tencent.smtt.utils.TbsLog.i(r0, r1)
            r1 = r5
        L_0x012d:
            java.lang.String r0 = "tbs_preload_x5_counter"
            r2 = -1
            int r0 = r3.getInt(r0, r2)     // Catch:{ Throwable -> 0x015f }
            if (r0 <= 0) goto L_0x0145
            android.content.SharedPreferences$Editor r2 = r3.edit()     // Catch:{ Throwable -> 0x015f }
            java.lang.String r3 = "tbs_preload_x5_counter"
            int r0 = r0 + -1
            android.content.SharedPreferences$Editor r0 = r2.putInt(r3, r0)     // Catch:{ Throwable -> 0x015f }
            r0.commit()     // Catch:{ Throwable -> 0x015f }
        L_0x0145:
            java.lang.String r0 = "QbSdk"
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = "QbSdk -- preload_x5_check result:"
            java.lang.StringBuilder r2 = r2.append(r3)
            java.lang.StringBuilder r1 = r2.append(r1)
            java.lang.String r1 = r1.toString()
            com.tencent.smtt.utils.TbsLog.i(r0, r1)
            goto L_0x0022
        L_0x015f:
            r0 = move-exception
            java.lang.String r2 = "QbSdk"
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = "tbs_preload_x5_counter Dec exception:"
            java.lang.StringBuilder r3 = r3.append(r4)
            java.lang.String r0 = android.util.Log.getStackTraceString(r0)
            java.lang.StringBuilder r0 = r3.append(r0)
            java.lang.String r0 = r0.toString()
            com.tencent.smtt.utils.TbsLog.e(r2, r0)
            goto L_0x0145
        L_0x017d:
            r0 = move-exception
            r4 = r1
            goto L_0x00d1
        L_0x0181:
            r0 = move-exception
            goto L_0x00d1
        L_0x0184:
            r2 = r1
            r6 = r0
            goto L_0x002d
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.smtt.sdk.QbSdk.d(android.content.Context):void");
    }

    public static boolean deleteMiniQBShortCut(Context context, String str, String str2) {
        br a2;
        if (context == null || TbsDownloader.getOverSea(context) || (a2 = br.a()) == null || !a2.b()) {
            return false;
        }
        return a2.c().b().invokeStaticMethod("com.tencent.tbs.tbsshell.WebCoreProxy", "deleteMiniQBShortCut", new Class[]{Context.class, String.class, String.class}, context, str, str2) != null;
    }

    public static void disAllowThirdAppDownload() {
        c = false;
    }

    public static void fileInfoDetect(Context context, String str, ValueCallback<String> valueCallback) {
        br a2 = br.a();
        if (a2 != null && a2.b()) {
            try {
                a2.c().b().invokeStaticMethod("com.tencent.tbs.tbsshell.WebCoreProxy", "fileInfoDetect", new Class[]{Context.class, String.class, ValueCallback.class}, context, str, valueCallback);
            } catch (Throwable th) {
                th.printStackTrace();
            }
        }
    }

    public static void forceSysWebView() {
        b = true;
        t = "SysWebViewForcedByOuter: " + Log.getStackTraceString(new Throwable());
        TbsLog.e("QbSdk", "sys WebView: SysWebViewForcedByOuter");
    }

    public static long getApkFileSize(Context context) {
        if (context != null) {
            return TbsDownloadConfig.getInstance(context.getApplicationContext()).mPreferences.getLong(TbsDownloadConfig.TbsConfigKey.KEY_TBSAPKFILESIZE, 0);
        }
        return 0;
    }

    public static String[] getDexLoaderFileList(Context context, Context context2, String str) {
        if (s instanceof String[]) {
            int length = s.length;
            String[] strArr = new String[length];
            for (int i2 = 0; i2 < length; i2++) {
                strArr[i2] = str + s[i2];
            }
            return strArr;
        }
        Object a2 = v.a(q, "getJarFiles", (Class<?>[]) new Class[]{Context.class, Context.class, String.class}, context, context2, str);
        boolean z2 = a2 instanceof String[];
        String[] strArr2 = a2;
        if (!z2) {
            strArr2 = new String[]{""};
        }
        return (String[]) strArr2;
    }

    public static boolean getDownloadWithoutWifi() {
        return C;
    }

    public static boolean getIsSysWebViewForcedByOuter() {
        return b;
    }

    public static String getMiniQBVersion(Context context) {
        br a2 = br.a();
        a2.a(context);
        if (a2 == null || !a2.b()) {
            return null;
        }
        return a2.c().f();
    }

    public static boolean getOnlyDownload() {
        return j;
    }

    public static String getQQBuildNumber() {
        return y;
    }

    public static Map<String, Object> getSettings() {
        return m;
    }

    public static boolean getTBSInstalling() {
        return D;
    }

    public static String getTID() {
        return x;
    }

    public static String getTbsResourcesPath(Context context) {
        return TbsShareManager.g(context);
    }

    public static int getTbsVersion(Context context) {
        if (TbsShareManager.isThirdPartyApp(context)) {
            return TbsShareManager.a(context, false);
        }
        int i2 = am.a().i(context);
        if (i2 != 0 || ai.a(context).c() != 3) {
            return i2;
        }
        reset(context);
        return i2;
    }

    public static int getTbsVersionForCrash(Context context) {
        if (TbsShareManager.isThirdPartyApp(context)) {
            return TbsShareManager.a(context, false);
        }
        int j2 = am.a().j(context);
        if (j2 != 0 || ai.a(context).c() != 3) {
            return j2;
        }
        reset(context);
        return j2;
    }

    public static void initBuglyAsync(boolean z2) {
        i = z2;
    }

    public static void initTbsSettings(Map<String, Object> map) {
        if (m == null) {
            m = map;
            return;
        }
        try {
            m.putAll(map);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public static void initX5Environment(Context context, PreInitCallback preInitCallback) {
        if (context != null) {
            b(context);
            B = new l(context, preInitCallback);
            if (TbsShareManager.isThirdPartyApp(context)) {
                am.a().b(context, true);
            }
            TbsDownloader.needDownload(context, false, false, new m(context, preInitCallback));
        }
    }

    public static boolean installLocalQbApk(Context context, String str, String str2, Bundle bundle) {
        o a2 = o.a(true);
        a2.a(context, false, false);
        if (a2 == null || !a2.b()) {
            return false;
        }
        return a2.a().a(context, str, str2, bundle);
    }

    public static boolean intentDispatch(WebView webView, Intent intent, String str, String str2) {
        if (webView == null) {
            return false;
        }
        if (str.startsWith("mttbrowser://miniqb/ch=icon?")) {
            Context context = webView.getContext();
            int indexOf = str.indexOf("url=");
            String substring = indexOf > 0 ? str.substring(indexOf + 4) : null;
            HashMap hashMap = new HashMap();
            String str3 = "unknown";
            try {
                str3 = context.getApplicationInfo().packageName;
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            hashMap.put(LOGIN_TYPE_KEY_PARTNER_ID, str3);
            hashMap.put(LOGIN_TYPE_KEY_PARTNER_CALL_POS, "14004");
            if (d.a(context, "miniqb://home".equals(substring) ? "qb://navicard/addCard?cardId=168&cardName=168" : substring, hashMap, "QbSdk.startMiniQBToLoadUrl", (WebView) null) != 0) {
                br a2 = br.a();
                if (a2 != null && a2.b() && a2.c().a(context, substring, (Map<String, String>) null, str2, (ValueCallback<String>) null) == 0) {
                    return true;
                }
                webView.loadUrl(substring);
            }
        } else {
            webView.loadUrl(str);
        }
        return false;
    }

    public static boolean isMiniQBShortCutExist(Context context, String str, String str2) {
        if (context == null) {
            return false;
        }
        if (TbsDownloader.getOverSea(context)) {
            return false;
        }
        br a2 = br.a();
        if (a2 == null || !a2.b()) {
            return false;
        }
        Object invokeStaticMethod = a2.c().b().invokeStaticMethod("com.tencent.tbs.tbsshell.WebCoreProxy", "isMiniQBShortCutExist", new Class[]{Context.class, String.class}, context, str);
        if (invokeStaticMethod == null) {
            return false;
        }
        return (invokeStaticMethod instanceof Boolean ? (Boolean) invokeStaticMethod : false).booleanValue();
    }

    public static boolean isTbsCoreInited() {
        o a2 = o.a(false);
        return a2 != null && a2.g();
    }

    public static boolean isX5DisabledSync(Context context) {
        if (ai.a(context).c() == 2) {
            return false;
        }
        if (!c(context)) {
            return true;
        }
        Object a2 = v.a(q, "isX5DisabledSync", (Class<?>[]) new Class[]{Integer.TYPE, Integer.TYPE}, Integer.valueOf(am.a().i(context)), 43600);
        if (a2 != null) {
            return ((Boolean) a2).booleanValue();
        }
        return true;
    }

    public static synchronized void preInit(Context context) {
        synchronized (QbSdk.class) {
            preInit(context, (PreInitCallback) null);
        }
    }

    public static synchronized void preInit(Context context, PreInitCallback preInitCallback) {
        synchronized (QbSdk.class) {
            TbsLog.initIfNeed(context);
            TbsLog.i("QbSdk", "preInit -- stack: " + Log.getStackTraceString(new Throwable("#")));
            k = a;
            if (!r) {
                k kVar = new k(context, new j(Looper.getMainLooper(), preInitCallback, context));
                kVar.setName("tbs_preinit");
                kVar.setPriority(10);
                kVar.start();
                r = true;
            }
        }
    }

    public static void reset(Context context) {
        reset(context, false);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:8:0x0029, code lost:
        if (r2 != r3) goto L_0x002b;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void reset(android.content.Context r5, boolean r6) {
        /*
            r0 = 1
            r1 = 0
            java.lang.String r2 = "QbSdk"
            java.lang.String r3 = "QbSdk reset!"
            com.tencent.smtt.utils.TbsLog.e(r2, r3, r0)
            com.tencent.smtt.sdk.TbsDownloader.stopDownload()     // Catch:{ Throwable -> 0x0080 }
            if (r6 == 0) goto L_0x009e
            boolean r2 = com.tencent.smtt.sdk.TbsShareManager.isThirdPartyApp(r5)     // Catch:{ Throwable -> 0x0080 }
            if (r2 != 0) goto L_0x009e
            com.tencent.smtt.sdk.am r2 = com.tencent.smtt.sdk.am.a()     // Catch:{ Throwable -> 0x0080 }
            int r2 = r2.h(r5)     // Catch:{ Throwable -> 0x0080 }
            com.tencent.smtt.sdk.am r3 = com.tencent.smtt.sdk.am.a()     // Catch:{ Throwable -> 0x0080 }
            int r3 = r3.i(r5)     // Catch:{ Throwable -> 0x0080 }
            r4 = 43300(0xa924, float:6.0676E-41)
            if (r2 <= r4) goto L_0x009e
            if (r2 == r3) goto L_0x009e
        L_0x002b:
            com.tencent.smtt.sdk.TbsDownloader.b((android.content.Context) r5)     // Catch:{ Throwable -> 0x0080 }
            java.lang.String r1 = "tbs"
            r2 = 0
            java.io.File r1 = r5.getDir(r1, r2)     // Catch:{ Throwable -> 0x0080 }
            r2 = 0
            java.lang.String r3 = "core_share_decouple"
            com.tencent.smtt.utils.k.a((java.io.File) r1, (boolean) r2, (java.lang.String) r3)     // Catch:{ Throwable -> 0x0080 }
            java.lang.String r1 = "QbSdk"
            java.lang.String r2 = "delete downloaded apk success"
            r3 = 1
            com.tencent.smtt.utils.TbsLog.i(r1, r2, r3)     // Catch:{ Throwable -> 0x0080 }
            java.lang.ThreadLocal<java.lang.Integer> r1 = com.tencent.smtt.sdk.am.a     // Catch:{ Throwable -> 0x0080 }
            r2 = 0
            java.lang.Integer r2 = java.lang.Integer.valueOf(r2)     // Catch:{ Throwable -> 0x0080 }
            r1.set(r2)     // Catch:{ Throwable -> 0x0080 }
            java.io.File r1 = new java.io.File     // Catch:{ Throwable -> 0x0080 }
            java.io.File r2 = r5.getFilesDir()     // Catch:{ Throwable -> 0x0080 }
            java.lang.String r3 = "bugly_switch.txt"
            r1.<init>(r2, r3)     // Catch:{ Throwable -> 0x0080 }
            if (r1 == 0) goto L_0x0063
            boolean r2 = r1.exists()     // Catch:{ Throwable -> 0x0080 }
            if (r2 == 0) goto L_0x0063
            r1.delete()     // Catch:{ Throwable -> 0x0080 }
        L_0x0063:
            if (r0 == 0) goto L_0x007f
            com.tencent.smtt.sdk.am r0 = com.tencent.smtt.sdk.am.a()     // Catch:{ Throwable -> 0x0080 }
            java.io.File r0 = r0.p(r5)     // Catch:{ Throwable -> 0x0080 }
            com.tencent.smtt.sdk.am r1 = com.tencent.smtt.sdk.am.a()     // Catch:{ Throwable -> 0x0080 }
            java.io.File r1 = r1.t(r5)     // Catch:{ Throwable -> 0x0080 }
            com.tencent.smtt.utils.k.b((java.io.File) r0, (java.io.File) r1)     // Catch:{ Throwable -> 0x0080 }
            com.tencent.smtt.sdk.am r0 = com.tencent.smtt.sdk.am.a()     // Catch:{ Throwable -> 0x0080 }
            r0.b(r5)     // Catch:{ Throwable -> 0x0080 }
        L_0x007f:
            return
        L_0x0080:
            r0 = move-exception
            java.lang.String r1 = "QbSdk"
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = "QbSdk reset exception:"
            java.lang.StringBuilder r2 = r2.append(r3)
            java.lang.String r0 = android.util.Log.getStackTraceString(r0)
            java.lang.StringBuilder r0 = r2.append(r0)
            java.lang.String r0 = r0.toString()
            com.tencent.smtt.utils.TbsLog.e(r1, r0)
            goto L_0x007f
        L_0x009e:
            r0 = r1
            goto L_0x002b
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.smtt.sdk.QbSdk.reset(android.content.Context, boolean):void");
    }

    public static void resetDecoupleCore(Context context) {
        TbsLog.e("QbSdk", "QbSdk resetDecoupleCore!", true);
        try {
            k.b(am.a().p(context));
        } catch (Throwable th) {
            TbsLog.e("QbSdk", "QbSdk resetDecoupleCore exception:" + Log.getStackTraceString(th));
        }
    }

    public static void setCurrentID(String str) {
        if (str != null && str.startsWith(TID_QQNumber_Prefix)) {
            String substring = str.substring(TID_QQNumber_Prefix.length());
            x = "0000000000000000".substring(substring.length()) + substring;
        }
    }

    public static void setDownloadWithoutWifi(boolean z2) {
        C = z2;
    }

    public static void setOnlyDownload(boolean z2) {
        j = z2;
    }

    public static void setQQBuildNumber(String str) {
        y = str;
    }

    public static void setTBSInstallingStatus(boolean z2) {
        D = z2;
    }

    public static void setTbsListener(TbsListener tbsListener) {
        A = tbsListener;
    }

    public static void setTbsLogClient(TbsLogClient tbsLogClient) {
        TbsLog.setTbsLogClient(tbsLogClient);
    }

    public static int startMiniQBToLoadUrl(Context context, String str, HashMap<String, String> hashMap, ValueCallback<String> valueCallback) {
        TbsCoreLoadStat.getInstance().a(context, 501);
        if (context == null) {
            return -100;
        }
        br a2 = br.a();
        a2.a(context);
        if (!a2.b()) {
            TbsCoreLoadStat.getInstance().a(context, 502);
            Log.e("QbSdk", "startMiniQBToLoadUrl  ret = -102");
            return -102;
        } else if (context != null && context.getApplicationInfo().packageName.equals("com.nd.android.pandahome2") && getTbsVersion(context) < 25487) {
            return -101;
        } else {
            int a3 = a2.c().a(context, str, hashMap, (String) null, valueCallback);
            if (a3 == 0) {
                TbsCoreLoadStat.getInstance().a(context, 503);
            } else {
                TbsLogReport.a(context).b(504, "" + a3);
            }
            Log.e("QbSdk", "startMiniQBToLoadUrl  ret = " + a3);
            return a3;
        }
    }

    public static boolean startQBForDoc(Context context, String str, int i2, int i3, String str2, Bundle bundle) {
        HashMap hashMap = new HashMap();
        hashMap.put(LOGIN_TYPE_KEY_PARTNER_ID, context.getApplicationContext().getApplicationInfo().processName);
        hashMap.put(LOGIN_TYPE_KEY_PARTNER_CALL_POS, Integer.toString(i2));
        return d.a(context, str, i3, str2, hashMap, bundle);
    }

    public static boolean startQBForVideo(Context context, String str, int i2) {
        HashMap hashMap = new HashMap();
        hashMap.put(LOGIN_TYPE_KEY_PARTNER_ID, context.getApplicationInfo().processName);
        hashMap.put(LOGIN_TYPE_KEY_PARTNER_CALL_POS, Integer.toString(i2));
        return d.a(context, str, (HashMap<String, String>) hashMap);
    }

    public static boolean startQBToLoadurl(Context context, String str, int i2, WebView webView) {
        br a2;
        Object invokeStaticMethod;
        IX5WebViewBase iX5WebViewBase;
        HashMap hashMap = new HashMap();
        hashMap.put(LOGIN_TYPE_KEY_PARTNER_ID, context.getApplicationInfo().processName);
        hashMap.put(LOGIN_TYPE_KEY_PARTNER_CALL_POS, Integer.toString(i2));
        if (webView == null) {
            try {
                String str2 = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).packageName;
                if (!((str2 != TbsConfig.APP_WX && str2 != TbsConfig.APP_QQ) || (a2 = br.a()) == null || !a2.b() || (invokeStaticMethod = a2.c().b().invokeStaticMethod("com.tencent.smtt.webkit.WebViewList", "getCurrentMainWebviewJustForQQandWechat", new Class[0], new Object[0])) == null || (iX5WebViewBase = (IX5WebViewBase) invokeStaticMethod) == null)) {
                    webView = (WebView) iX5WebViewBase.getView().getParent();
                }
            } catch (Exception e2) {
            }
        }
        return d.a(context, str, hashMap, "QbSdk.startQBToLoadurl", webView) == 0;
    }

    public static boolean startQbOrMiniQBToLoadUrl(Context context, String str, HashMap<String, String> hashMap, ValueCallback<String> valueCallback) {
        if (context == null) {
            return false;
        }
        br a2 = br.a();
        a2.a(context);
        if (hashMap == null || !"5".equals(hashMap.get(LOGIN_TYPE_KEY_PARTNER_CALL_POS)) || !a2.b() || ((Bundle) a2.c().b().invokeStaticMethod("com.tencent.tbs.tbsshell.WebCoreProxy", "getAdWebViewInfoFromX5Core", new Class[0], new Object[0])) != null) {
        }
        if (d.a(context, str, hashMap, "QbSdk.startMiniQBToLoadUrl", (WebView) null) == 0) {
            return true;
        }
        if (a2.b()) {
            if (context != null && context.getApplicationInfo().packageName.equals("com.nd.android.pandahome2") && getTbsVersion(context) < 25487) {
                return false;
            }
            if (a2.c().a(context, str, hashMap, (String) null, valueCallback) == 0) {
                return true;
            }
        }
        return false;
    }

    public static void unForceSysWebView() {
        b = false;
        TbsLog.e("QbSdk", "sys WebView: unForceSysWebView called");
    }

    public static boolean useSoftWare() {
        if (q == null) {
            return false;
        }
        Object a2 = v.a(q, "useSoftWare", (Class<?>[]) new Class[0], new Object[0]);
        if (a2 == null) {
            a2 = v.a(q, "useSoftWare", (Class<?>[]) new Class[]{Integer.TYPE}, Integer.valueOf(a.a()));
        }
        return a2 == null ? false : ((Boolean) a2).booleanValue();
    }
}
