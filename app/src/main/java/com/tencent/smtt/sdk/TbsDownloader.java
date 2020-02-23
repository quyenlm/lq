package com.tencent.smtt.sdk;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.text.TextUtils;
import com.tencent.mna.NetworkBindingListener;
import com.tencent.qqgamemi.util.TimeUtils;
import com.tencent.smtt.sdk.TbsDownloadConfig;
import com.tencent.smtt.sdk.TbsListener;
import com.tencent.smtt.sdk.TbsLogReport;
import com.tencent.smtt.utils.TbsLog;
import com.tencent.smtt.utils.a;
import com.tencent.smtt.utils.b;
import com.tencent.smtt.utils.k;
import com.tencent.smtt.utils.n;
import com.tencent.smtt.utils.x;
import java.io.File;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.json.JSONArray;
import org.json.JSONObject;

public class TbsDownloader {
    public static final boolean DEBUG_DISABLE_DOWNLOAD = false;
    public static boolean DOWNLOAD_OVERSEA_TBS = false;
    public static final String LOGTAG = "TbsDownload";
    static boolean a;
    private static String b;
    /* access modifiers changed from: private */
    public static Context c;
    private static Handler d;
    private static String e;
    private static Object f = new byte[0];
    /* access modifiers changed from: private */
    public static ag g;
    private static HandlerThread h;
    private static boolean i = false;
    private static boolean j = false;
    private static boolean k = false;
    private static long l = -1;

    public interface TbsDownloaderCallback {
        void onNeedDownloadFinish(boolean z, int i);
    }

    protected static File a(int i2) {
        String[] coreProviderAppList = TbsShareManager.getCoreProviderAppList();
        File file = null;
        int length = coreProviderAppList.length;
        int i3 = 0;
        while (true) {
            if (i3 >= length) {
                break;
            }
            String str = coreProviderAppList[i3];
            if (!str.equals(c.getApplicationInfo().packageName)) {
                file = new File(k.a(c, str, 4, false), getOverSea(c) ? "x5.oversea.tbs.org" : "x5.tbs.org");
                if (file == null || !file.exists()) {
                    TbsLog.i(LOGTAG, "can not find local backup core file");
                } else if (a.a(c, file) == i2) {
                    TbsLog.i(LOGTAG, "local tbs version fond,path = " + file.getAbsolutePath());
                    break;
                } else {
                    TbsLog.i(LOGTAG, "version is not match");
                }
            }
            i3++;
        }
        return file;
    }

    static String a(Context context) {
        String str;
        String str2;
        if (!TextUtils.isEmpty(b)) {
            return b;
        }
        Locale locale = Locale.getDefault();
        StringBuffer stringBuffer = new StringBuffer();
        String str3 = Build.VERSION.RELEASE;
        try {
            str = new String(str3.getBytes("UTF-8"), "ISO8859-1");
        } catch (Exception e2) {
            str = str3;
        }
        if (str.length() > 0) {
            stringBuffer.append(str);
        } else {
            stringBuffer.append("1.0");
        }
        stringBuffer.append("; ");
        String language = locale.getLanguage();
        if (language != null) {
            stringBuffer.append(language.toLowerCase());
            String country = locale.getCountry();
            if (country != null) {
                stringBuffer.append("-");
                stringBuffer.append(country.toLowerCase());
            }
        } else {
            stringBuffer.append("en");
        }
        if ("REL".equals(Build.VERSION.CODENAME)) {
            String str4 = Build.MODEL;
            try {
                str2 = new String(str4.getBytes("UTF-8"), "ISO8859-1");
            } catch (Exception e3) {
                str2 = str4;
            }
            if (str2.length() > 0) {
                stringBuffer.append("; ");
                stringBuffer.append(str2);
            }
        }
        String replaceAll = (Build.ID == null ? "" : Build.ID).replaceAll("[一-龥]", "");
        if (replaceAll.length() > 0) {
            stringBuffer.append(" Build/");
            stringBuffer.append(replaceAll);
        }
        String format = String.format("Mozilla/5.0 (Linux; U; Android %s) AppleWebKit/533.1 (KHTML, like Gecko)Version/4.0 Mobile Safari/533.1", new Object[]{stringBuffer});
        b = format;
        return format;
    }

    private static String a(String str) {
        return str == null ? "" : str;
    }

    private static void a(boolean z, TbsDownloaderCallback tbsDownloaderCallback) {
        int i2 = 0;
        TbsLog.i(LOGTAG, "[TbsDownloader.queryConfig]");
        d.removeMessages(100);
        Message obtain = Message.obtain(d, 100);
        if (tbsDownloaderCallback != null) {
            obtain.obj = tbsDownloaderCallback;
        }
        obtain.arg1 = 0;
        if (z) {
            i2 = 1;
        }
        obtain.arg1 = i2;
        obtain.sendToTarget();
    }

    private static boolean a(Context context, boolean z, TbsDownloaderCallback tbsDownloaderCallback) {
        Matcher matcher = null;
        TbsDownloadConfig instance = TbsDownloadConfig.getInstance(context);
        if (Build.VERSION.SDK_INT < 8) {
            instance.setDownloadInterruptCode(-102);
            return false;
        } else if (QbSdk.c || !TbsShareManager.isThirdPartyApp(c) || c()) {
            if (!instance.mPreferences.contains(TbsDownloadConfig.TbsConfigKey.KEY_IS_OVERSEA)) {
                if (z && !TbsConfig.APP_WX.equals(context.getApplicationInfo().packageName)) {
                    TbsLog.i(LOGTAG, "needDownload-oversea is true, but not WX");
                    z = false;
                }
                instance.a.put(TbsDownloadConfig.TbsConfigKey.KEY_IS_OVERSEA, Boolean.valueOf(z));
                instance.commit();
                j = z;
                TbsLog.i(LOGTAG, "needDownload-first-called--isoversea = " + z);
            }
            if (!getOverSea(context) || Build.VERSION.SDK_INT == 16 || Build.VERSION.SDK_INT == 17 || Build.VERSION.SDK_INT == 18) {
                e = instance.mPreferences.getString(TbsDownloadConfig.TbsConfigKey.KEY_DEVICE_CPUABI, (String) null);
                if (!TextUtils.isEmpty(e)) {
                    try {
                        matcher = Pattern.compile("i686|mips|x86_64").matcher(e);
                    } catch (Exception e2) {
                    }
                    if (matcher != null && matcher.find()) {
                        if (tbsDownloaderCallback != null) {
                            tbsDownloaderCallback.onNeedDownloadFinish(false, 0);
                        }
                        instance.setDownloadInterruptCode(-104);
                        return false;
                    }
                }
                return true;
            }
            TbsLog.i(LOGTAG, "needDownload- return false,  because of  version is " + Build.VERSION.SDK_INT + ", and overea");
            if (tbsDownloaderCallback != null) {
                tbsDownloaderCallback.onNeedDownloadFinish(false, 0);
            }
            instance.setDownloadInterruptCode(-103);
            return false;
        } else if (tbsDownloaderCallback == null) {
            return false;
        } else {
            tbsDownloaderCallback.onNeedDownloadFinish(false, 0);
            return false;
        }
    }

    private static boolean a(Context context, boolean z, boolean z2) {
        boolean z3;
        String str;
        TbsDownloadConfig instance = TbsDownloadConfig.getInstance(context);
        if (!z) {
            String string = instance.mPreferences.getString(TbsDownloadConfig.TbsConfigKey.KEY_APP_VERSIONNAME, (String) null);
            int i2 = instance.mPreferences.getInt(TbsDownloadConfig.TbsConfigKey.KEY_APP_VERSIONCODE, 0);
            String string2 = instance.mPreferences.getString(TbsDownloadConfig.TbsConfigKey.KEY_APP_METADATA, (String) null);
            String a2 = b.a(c);
            int b2 = b.b(c);
            String a3 = b.a(c, "com.tencent.mm.BuildInfo.CLIENT_VERSION");
            TbsLog.i(LOGTAG, "[TbsDownloader.needSendQueryRequest] appVersionName=" + a2 + " oldAppVersionName=" + string + " appVersionCode=" + b2 + " oldAppVersionCode=" + i2 + " appMetadata=" + a3 + " oldAppVersionMetadata=" + string2);
            long currentTimeMillis = System.currentTimeMillis();
            long j2 = instance.mPreferences.getLong(TbsDownloadConfig.TbsConfigKey.KEY_LAST_CHECK, 0);
            TbsLog.i(LOGTAG, "[TbsDownloader.needSendQueryRequest] timeLastCheck=" + j2 + " timeNow=" + currentTimeMillis);
            if (z2) {
                boolean contains = instance.mPreferences.contains(TbsDownloadConfig.TbsConfigKey.KEY_LAST_CHECK);
                TbsLog.i(LOGTAG, "[TbsDownloader.needSendQueryRequest] hasLaskCheckKey=" + contains);
                if (contains && j2 == 0) {
                    j2 = currentTimeMillis;
                }
            }
            long j3 = instance.mPreferences.getLong(TbsDownloadConfig.TbsConfigKey.KEY_LAST_REQUEST_SUCCESS, 0);
            long j4 = instance.mPreferences.getLong(TbsDownloadConfig.TbsConfigKey.KEY_COUNT_REQUEST_FAIL_IN_24HOURS, 0);
            long retryInterval = instance.getRetryInterval();
            TbsLog.i(LOGTAG, "retryInterval = " + retryInterval + " s");
            if (currentTimeMillis - j2 > 1000 * retryInterval) {
                z3 = true;
                str = null;
            } else if (TbsShareManager.isThirdPartyApp(c) && j3 > 0 && currentTimeMillis - j3 > retryInterval * 1000 && j4 < 11) {
                z3 = true;
                str = null;
            } else if (!TbsShareManager.isThirdPartyApp(c) || TbsShareManager.findCoreForThirdPartyApp(c) != 0 || e()) {
                if (a2 == null || b2 == 0 || a3 == null) {
                    if (TbsShareManager.isThirdPartyApp(c)) {
                        str = "timeNow - timeLastCheck is " + (currentTimeMillis - j2) + " TbsShareManager.findCoreForThirdPartyApp(sAppContext) is " + TbsShareManager.findCoreForThirdPartyApp(c) + " sendRequestWithSameHostCoreVersion() is " + e() + " appVersionName is " + a2 + " appVersionCode is " + b2 + " appMetadata is " + a3 + " oldAppVersionName is " + string + " oldAppVersionCode is " + i2 + " oldAppVersionMetadata is " + string2;
                        z3 = false;
                    }
                } else if (!a2.equals(string) || b2 != i2 || !a3.equals(string2)) {
                    z3 = true;
                    str = null;
                }
                str = null;
                z3 = false;
            } else {
                z3 = true;
                am.a().d(c);
                str = null;
            }
        } else {
            z3 = true;
            str = null;
        }
        if (!z3 && TbsShareManager.isThirdPartyApp(c)) {
            TbsLogReport.TbsLogInfo a4 = TbsLogReport.a(c).a();
            a4.setErrorCode(-119);
            a4.setFailDetail(str);
            TbsLogReport.a(c).a(TbsLogReport.EventType.TYPE_DOWNLOAD, a4);
        }
        return z3;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:108:0x028e, code lost:
        if (r2 > 0) goto L_0x0290;
     */
    @android.annotation.TargetApi(11)
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static boolean a(java.lang.String r30, int r31, boolean r32, boolean r33) {
        /*
            java.lang.String r2 = "TbsDownload"
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = "[TbsDownloader.readResponse] response="
            java.lang.StringBuilder r3 = r3.append(r4)
            r0 = r30
            java.lang.StringBuilder r3 = r3.append(r0)
            java.lang.String r3 = r3.toString()
            com.tencent.smtt.utils.TbsLog.i(r2, r3)
            android.content.Context r2 = c
            com.tencent.smtt.sdk.TbsDownloadConfig r13 = com.tencent.smtt.sdk.TbsDownloadConfig.getInstance(r2)
            boolean r2 = android.text.TextUtils.isEmpty(r30)
            if (r2 == 0) goto L_0x003c
            if (r32 == 0) goto L_0x0036
            r2 = -108(0xffffffffffffff94, float:NaN)
            r13.setDownloadInterruptCode(r2)
        L_0x002d:
            java.lang.String r2 = "TbsDownload"
            java.lang.String r3 = "[TbsDownloader.readResponse] return #1,response is empty..."
            com.tencent.smtt.utils.TbsLog.i(r2, r3)
            r2 = 0
        L_0x0035:
            return r2
        L_0x0036:
            r2 = -208(0xffffffffffffff30, float:NaN)
            r13.setDownloadInterruptCode(r2)
            goto L_0x002d
        L_0x003c:
            org.json.JSONObject r14 = new org.json.JSONObject
            r0 = r30
            r14.<init>(r0)
            java.lang.String r2 = "RET"
            int r2 = r14.getInt(r2)
            if (r2 == 0) goto L_0x0072
            if (r32 == 0) goto L_0x006c
            r3 = -109(0xffffffffffffff93, float:NaN)
            r13.setDownloadInterruptCode(r3)
        L_0x0052:
            java.lang.String r3 = "TbsDownload"
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r5 = "[TbsDownloader.readResponse] return #2,returnCode="
            java.lang.StringBuilder r4 = r4.append(r5)
            java.lang.StringBuilder r2 = r4.append(r2)
            java.lang.String r2 = r2.toString()
            com.tencent.smtt.utils.TbsLog.i(r3, r2)
            r2 = 0
            goto L_0x0035
        L_0x006c:
            r3 = -209(0xffffffffffffff2f, float:NaN)
            r13.setDownloadInterruptCode(r3)
            goto L_0x0052
        L_0x0072:
            java.lang.String r2 = "RESPONSECODE"
            int r15 = r14.getInt(r2)
            java.lang.String r2 = "DOWNLOADURL"
            java.lang.String r16 = r14.getString(r2)
            java.lang.String r2 = "URLLIST"
            java.lang.String r3 = ""
            java.lang.String r17 = r14.optString(r2, r3)
            java.lang.String r2 = "TBSAPKSERVERVERSION"
            int r18 = r14.getInt(r2)
            java.lang.String r2 = "DOWNLOADMAXFLOW"
            int r19 = r14.getInt(r2)
            java.lang.String r2 = "DOWNLOAD_MIN_FREE_SPACE"
            int r20 = r14.getInt(r2)
            java.lang.String r2 = "DOWNLOAD_SUCCESS_MAX_RETRYTIMES"
            int r21 = r14.getInt(r2)
            java.lang.String r2 = "DOWNLOAD_FAILED_MAX_RETRYTIMES"
            int r22 = r14.getInt(r2)
            java.lang.String r2 = "DOWNLOAD_SINGLE_TIMEOUT"
            long r24 = r14.getLong(r2)
            java.lang.String r2 = "TBSAPKFILESIZE"
            long r26 = r14.getLong(r2)
            java.lang.String r2 = "RETRY_INTERVAL"
            r4 = 0
            long r10 = r14.optLong(r2, r4)
            java.lang.String r2 = "FLOWCTR"
            r3 = -1
            int r23 = r14.optInt(r2, r3)
            r2 = 0
            java.lang.String r3 = "USEBBACKUPVER"
            int r2 = r14.getInt(r3)     // Catch:{ Exception -> 0x0793 }
        L_0x00c6:
            java.util.Map<java.lang.String, java.lang.Object> r3 = r13.a
            java.lang.String r4 = "use_backup_version"
            java.lang.Integer r2 = java.lang.Integer.valueOf(r2)
            r3.put(r4, r2)
            if (r32 == 0) goto L_0x00f5
            boolean r2 = com.tencent.smtt.sdk.QbSdk.i
            if (r2 == 0) goto L_0x00f5
            android.content.Context r2 = c
            boolean r2 = com.tencent.smtt.sdk.TbsShareManager.isThirdPartyApp(r2)
            if (r2 == 0) goto L_0x00f5
            java.lang.String r2 = "BUGLY"
            r3 = 0
            int r2 = r14.optInt(r2, r3)     // Catch:{ Throwable -> 0x01f9 }
            com.tencent.smtt.sdk.TbsExtensionFunctionManager r3 = com.tencent.smtt.sdk.TbsExtensionFunctionManager.getInstance()     // Catch:{ Throwable -> 0x01f9 }
            android.content.Context r4 = c     // Catch:{ Throwable -> 0x01f9 }
            java.lang.String r5 = "bugly_switch.txt"
            r6 = 1
            if (r2 != r6) goto L_0x01f6
            r2 = 1
        L_0x00f2:
            r3.setFunctionEnable(r4, r5, r2)     // Catch:{ Throwable -> 0x01f9 }
        L_0x00f5:
            if (r32 == 0) goto L_0x0126
            java.lang.String r2 = "TEMPLATESWITCH"
            r3 = 0
            int r2 = r14.optInt(r2, r3)     // Catch:{ Throwable -> 0x021b }
            r2 = r2 & 1
            if (r2 == 0) goto L_0x0218
            r2 = 1
        L_0x0103:
            com.tencent.smtt.sdk.TbsExtensionFunctionManager r3 = com.tencent.smtt.sdk.TbsExtensionFunctionManager.getInstance()     // Catch:{ Throwable -> 0x021b }
            android.content.Context r4 = c     // Catch:{ Throwable -> 0x021b }
            java.lang.String r5 = "cookie_switch.txt"
            r3.setFunctionEnable(r4, r5, r2)     // Catch:{ Throwable -> 0x021b }
            java.lang.String r3 = "TbsDownload"
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x021b }
            r4.<init>()     // Catch:{ Throwable -> 0x021b }
            java.lang.String r5 = "useCookieCompatiable:"
            java.lang.StringBuilder r4 = r4.append(r5)     // Catch:{ Throwable -> 0x021b }
            java.lang.StringBuilder r2 = r4.append(r2)     // Catch:{ Throwable -> 0x021b }
            java.lang.String r2 = r2.toString()     // Catch:{ Throwable -> 0x021b }
            com.tencent.smtt.utils.TbsLog.w(r3, r2)     // Catch:{ Throwable -> 0x021b }
        L_0x0126:
            r9 = 0
            r8 = 0
            r4 = 0
            r7 = 0
            r6 = 0
            r3 = 1
            java.lang.String r5 = ""
            java.lang.String r2 = "PKGMD5"
            java.lang.String r9 = r14.getString(r2)     // Catch:{ Exception -> 0x0240 }
            java.lang.String r2 = "RESETX5"
            int r8 = r14.getInt(r2)     // Catch:{ Exception -> 0x0240 }
            java.lang.String r2 = "UPLOADLOG"
            int r7 = r14.getInt(r2)     // Catch:{ Exception -> 0x0240 }
            java.lang.String r2 = "RESETTOKEN"
            boolean r2 = r14.has(r2)     // Catch:{ Exception -> 0x0240 }
            if (r2 == 0) goto L_0x0152
            java.lang.String r2 = "RESETTOKEN"
            int r2 = r14.getInt(r2)     // Catch:{ Exception -> 0x0240 }
            if (r2 == 0) goto L_0x023a
            r2 = 1
        L_0x0151:
            r6 = r2
        L_0x0152:
            java.lang.String r2 = "SETTOKEN"
            boolean r2 = r14.has(r2)     // Catch:{ Exception -> 0x0240 }
            if (r2 == 0) goto L_0x0160
            java.lang.String r2 = "SETTOKEN"
            java.lang.String r5 = r14.getString(r2)     // Catch:{ Exception -> 0x0240 }
        L_0x0160:
            java.lang.String r2 = "ENABLE_LOAD_RENAME_FILE_LOCK"
            boolean r2 = r14.has(r2)     // Catch:{ Exception -> 0x0240 }
            if (r2 == 0) goto L_0x079c
            java.lang.String r2 = "ENABLE_LOAD_RENAME_FILE_LOCK"
            int r2 = r14.getInt(r2)     // Catch:{ Exception -> 0x0240 }
            if (r2 == 0) goto L_0x023d
            r2 = 1
        L_0x0171:
            r12 = r2
        L_0x0172:
            java.lang.String r2 = "RESETDECOUPLECORE"
            int r2 = r14.getInt(r2)     // Catch:{ Exception -> 0x0246 }
        L_0x0178:
            r3 = 0
            java.lang.String r4 = "RESETTODECOUPLECORE"
            int r3 = r14.getInt(r4)     // Catch:{ Exception -> 0x0790 }
        L_0x017f:
            java.lang.Object r4 = f
            monitor-enter(r4)
            if (r6 == 0) goto L_0x0191
            java.util.Map<java.lang.String, java.lang.Object> r6 = r13.a     // Catch:{ all -> 0x024a }
            java.lang.String r28 = "tbs_deskey_token"
            java.lang.String r29 = ""
            r0 = r28
            r1 = r29
            r6.put(r0, r1)     // Catch:{ all -> 0x024a }
        L_0x0191:
            boolean r6 = android.text.TextUtils.isEmpty(r5)     // Catch:{ all -> 0x024a }
            if (r6 != 0) goto L_0x01c5
            int r6 = r5.length()     // Catch:{ all -> 0x024a }
            r28 = 96
            r0 = r28
            if (r6 != r0) goto L_0x01c5
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ all -> 0x024a }
            r6.<init>()     // Catch:{ all -> 0x024a }
            java.lang.StringBuilder r5 = r6.append(r5)     // Catch:{ all -> 0x024a }
            java.lang.String r6 = "&"
            java.lang.StringBuilder r5 = r5.append(r6)     // Catch:{ all -> 0x024a }
            java.lang.String r6 = com.tencent.smtt.utils.p.c()     // Catch:{ all -> 0x024a }
            java.lang.StringBuilder r5 = r5.append(r6)     // Catch:{ all -> 0x024a }
            java.lang.String r5 = r5.toString()     // Catch:{ all -> 0x024a }
            java.util.Map<java.lang.String, java.lang.Object> r6 = r13.a     // Catch:{ all -> 0x024a }
            java.lang.String r28 = "tbs_deskey_token"
            r0 = r28
            r6.put(r0, r5)     // Catch:{ all -> 0x024a }
        L_0x01c5:
            monitor-exit(r4)     // Catch:{ all -> 0x024a }
            r4 = 1
            if (r8 != r4) goto L_0x0256
            if (r32 == 0) goto L_0x024d
            r2 = -110(0xffffffffffffff92, float:NaN)
            r13.setDownloadInterruptCode(r2)
        L_0x01d0:
            android.content.Context r4 = c
            r2 = 1
            if (r3 != r2) goto L_0x0254
            r2 = 1
        L_0x01d6:
            com.tencent.smtt.sdk.QbSdk.reset(r4, r2)
            java.lang.String r2 = "TbsDownload"
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = "[TbsDownloader.readResponse] return #3,needResetTbs=1,isQuery="
            java.lang.StringBuilder r3 = r3.append(r4)
            r0 = r32
            java.lang.StringBuilder r3 = r3.append(r0)
            java.lang.String r3 = r3.toString()
            com.tencent.smtt.utils.TbsLog.i(r2, r3)
            r2 = 0
            goto L_0x0035
        L_0x01f6:
            r2 = 0
            goto L_0x00f2
        L_0x01f9:
            r2 = move-exception
            java.lang.String r3 = "qbsdk"
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r5 = "throwable:"
            java.lang.StringBuilder r4 = r4.append(r5)
            java.lang.String r2 = r2.toString()
            java.lang.StringBuilder r2 = r4.append(r2)
            java.lang.String r2 = r2.toString()
            com.tencent.smtt.utils.TbsLog.i(r3, r2)
            goto L_0x00f5
        L_0x0218:
            r2 = 0
            goto L_0x0103
        L_0x021b:
            r2 = move-exception
            java.lang.String r3 = "qbsdk"
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r5 = "throwable:"
            java.lang.StringBuilder r4 = r4.append(r5)
            java.lang.String r2 = r2.toString()
            java.lang.StringBuilder r2 = r4.append(r2)
            java.lang.String r2 = r2.toString()
            com.tencent.smtt.utils.TbsLog.i(r3, r2)
            goto L_0x0126
        L_0x023a:
            r2 = 0
            goto L_0x0151
        L_0x023d:
            r2 = 0
            goto L_0x0171
        L_0x0240:
            r2 = move-exception
            r2 = r5
            r5 = r2
            r12 = r3
            goto L_0x0172
        L_0x0246:
            r2 = move-exception
            r2 = r4
            goto L_0x0178
        L_0x024a:
            r2 = move-exception
            monitor-exit(r4)     // Catch:{ all -> 0x024a }
            throw r2
        L_0x024d:
            r2 = -210(0xffffffffffffff2e, float:NaN)
            r13.setDownloadInterruptCode(r2)
            goto L_0x01d0
        L_0x0254:
            r2 = 0
            goto L_0x01d6
        L_0x0256:
            if (r12 != 0) goto L_0x025b
            r13.setTbsCoreLoadRenameFileLockEnable(r12)
        L_0x025b:
            r3 = 1
            if (r2 != r3) goto L_0x0263
            android.content.Context r2 = c
            com.tencent.smtt.sdk.QbSdk.resetDecoupleCore(r2)
        L_0x0263:
            r2 = 1
            if (r7 != r2) goto L_0x0278
            android.os.Handler r2 = d
            r3 = 104(0x68, float:1.46E-43)
            r2.removeMessages(r3)
            android.os.Handler r2 = d
            r3 = 104(0x68, float:1.46E-43)
            android.os.Message r2 = android.os.Message.obtain(r2, r3)
            r2.sendToTarget()
        L_0x0278:
            r4 = 86400(0x15180, double:4.26873E-319)
            r2 = 1
            r0 = r23
            if (r0 != r2) goto L_0x0796
            r2 = 604800(0x93a80, double:2.98811E-318)
            int r2 = (r10 > r2 ? 1 : (r10 == r2 ? 0 : -1))
            if (r2 <= 0) goto L_0x0799
            r2 = 604800(0x93a80, double:2.98811E-318)
        L_0x028a:
            r6 = 0
            int r6 = (r2 > r6 ? 1 : (r2 == r6 ? 0 : -1))
            if (r6 <= 0) goto L_0x0796
        L_0x0290:
            long r4 = getRetryIntervalInSeconds()
            r6 = 0
            int r4 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
            if (r4 < 0) goto L_0x029e
            long r2 = getRetryIntervalInSeconds()
        L_0x029e:
            java.util.Map<java.lang.String, java.lang.Object> r4 = r13.a
            java.lang.String r5 = "retry_interval"
            java.lang.Long r2 = java.lang.Long.valueOf(r2)
            r4.put(r5, r2)
            r2 = 0
            r3 = 0
            if (r32 == 0) goto L_0x0362
            java.lang.String r4 = "DECOUPLECOREVERSION"
            int r2 = r14.getInt(r4)     // Catch:{ Exception -> 0x078d }
        L_0x02b3:
            android.content.Context r4 = c     // Catch:{ Exception -> 0x078a }
            com.tencent.smtt.sdk.TbsDownloadConfig r4 = com.tencent.smtt.sdk.TbsDownloadConfig.getInstance(r4)     // Catch:{ Exception -> 0x078a }
            android.content.SharedPreferences r4 = r4.mPreferences     // Catch:{ Exception -> 0x078a }
            java.lang.String r5 = "tbs_downloaddecouplecore"
            r6 = 0
            int r3 = r4.getInt(r5, r6)     // Catch:{ Exception -> 0x078a }
        L_0x02c2:
            if (r32 == 0) goto L_0x02d8
            android.content.Context r4 = c
            boolean r4 = com.tencent.smtt.sdk.TbsShareManager.isThirdPartyApp(r4)
            if (r4 != 0) goto L_0x02d8
            if (r2 != 0) goto L_0x02d8
            com.tencent.smtt.sdk.am r2 = com.tencent.smtt.sdk.am.a()
            android.content.Context r4 = c
            int r2 = r2.h(r4)
        L_0x02d8:
            java.lang.String r4 = "TbsDownload"
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.String r6 = "in response decoupleCoreVersion is "
            java.lang.StringBuilder r5 = r5.append(r6)
            java.lang.StringBuilder r5 = r5.append(r2)
            java.lang.String r5 = r5.toString()
            com.tencent.smtt.utils.TbsLog.i(r4, r5)
            java.util.Map<java.lang.String, java.lang.Object> r4 = r13.a
            java.lang.String r5 = "tbs_decouplecoreversion"
            java.lang.Integer r6 = java.lang.Integer.valueOf(r2)
            r4.put(r5, r6)
            java.util.Map<java.lang.String, java.lang.Object> r4 = r13.a
            java.lang.String r5 = "tbs_downloaddecouplecore"
            java.lang.Integer r6 = java.lang.Integer.valueOf(r3)
            r4.put(r5, r6)
            android.content.Context r4 = c
            boolean r4 = com.tencent.smtt.sdk.TbsShareManager.isThirdPartyApp(r4)
            if (r4 != 0) goto L_0x0331
            if (r2 <= 0) goto L_0x0373
            com.tencent.smtt.sdk.am r4 = com.tencent.smtt.sdk.am.a()
            android.content.Context r5 = c
            int r4 = r4.h(r5)
            if (r2 == r4) goto L_0x0373
            com.tencent.smtt.sdk.am r4 = com.tencent.smtt.sdk.am.a()
            android.content.Context r5 = c
            int r4 = r4.i(r5)
            if (r2 != r4) goto L_0x0373
            com.tencent.smtt.sdk.am r2 = com.tencent.smtt.sdk.am.a()
            android.content.Context r4 = c
            r2.n(r4)
        L_0x0331:
            boolean r2 = android.text.TextUtils.isEmpty(r16)
            if (r2 == 0) goto L_0x0385
            android.content.Context r2 = c
            boolean r2 = com.tencent.smtt.sdk.TbsShareManager.isThirdPartyApp(r2)
            if (r2 == 0) goto L_0x0385
            java.util.Map<java.lang.String, java.lang.Object> r2 = r13.a
            java.lang.String r3 = "tbs_needdownload"
            r4 = 0
            java.lang.Boolean r4 = java.lang.Boolean.valueOf(r4)
            r2.put(r3, r4)
            r13.commit()
            if (r32 == 0) goto L_0x0358
            android.content.Context r2 = c
            r3 = 0
            r0 = r18
            com.tencent.smtt.sdk.TbsShareManager.writeCoreInfoForThirdPartyApp(r2, r0, r3)
        L_0x0358:
            java.lang.String r2 = "TbsDownload"
            java.lang.String r3 = "[TbsDownloader.readResponse] return #4,current app is third app..."
            com.tencent.smtt.utils.TbsLog.i(r2, r3)
            r2 = 0
            goto L_0x0035
        L_0x0362:
            android.content.Context r4 = c     // Catch:{ Exception -> 0x078d }
            com.tencent.smtt.sdk.TbsDownloadConfig r4 = com.tencent.smtt.sdk.TbsDownloadConfig.getInstance(r4)     // Catch:{ Exception -> 0x078d }
            android.content.SharedPreferences r4 = r4.mPreferences     // Catch:{ Exception -> 0x078d }
            java.lang.String r5 = "tbs_decouplecoreversion"
            r6 = 0
            int r2 = r4.getInt(r5, r6)     // Catch:{ Exception -> 0x078d }
            goto L_0x02b3
        L_0x0373:
            if (r2 != 0) goto L_0x0331
            com.tencent.smtt.sdk.am r2 = com.tencent.smtt.sdk.am.a()     // Catch:{ Throwable -> 0x0383 }
            android.content.Context r4 = c     // Catch:{ Throwable -> 0x0383 }
            java.io.File r2 = r2.p(r4)     // Catch:{ Throwable -> 0x0383 }
            com.tencent.smtt.utils.k.b((java.io.File) r2)     // Catch:{ Throwable -> 0x0383 }
            goto L_0x0331
        L_0x0383:
            r2 = move-exception
            goto L_0x0331
        L_0x0385:
            java.lang.String r2 = "TbsDownload"
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r5 = "in response responseCode is "
            java.lang.StringBuilder r4 = r4.append(r5)
            java.lang.StringBuilder r4 = r4.append(r15)
            java.lang.String r4 = r4.toString()
            com.tencent.smtt.utils.TbsLog.i(r2, r4)
            if (r15 != 0) goto L_0x03f0
            java.util.Map<java.lang.String, java.lang.Object> r2 = r13.a
            java.lang.String r3 = "tbs_responsecode"
            java.lang.Integer r4 = java.lang.Integer.valueOf(r15)
            r2.put(r3, r4)
            java.util.Map<java.lang.String, java.lang.Object> r2 = r13.a
            java.lang.String r3 = "tbs_needdownload"
            r4 = 0
            java.lang.Boolean r4 = java.lang.Boolean.valueOf(r4)
            r2.put(r3, r4)
            if (r32 == 0) goto L_0x03dd
            java.util.Map<java.lang.String, java.lang.Object> r2 = r13.a
            java.lang.String r3 = "tbs_download_interrupt_code_reason"
            r4 = -111(0xffffffffffffff91, float:NaN)
            java.lang.Integer r4 = java.lang.Integer.valueOf(r4)
            r2.put(r3, r4)
        L_0x03c5:
            r13.commit()
            android.content.Context r2 = c
            boolean r2 = com.tencent.smtt.sdk.TbsShareManager.isThirdPartyApp(r2)
            if (r2 != 0) goto L_0x03d3
            startDecoupleCoreIfNeeded()
        L_0x03d3:
            java.lang.String r2 = "TbsDownload"
            java.lang.String r3 = "[TbsDownloader.readResponse] return #5,responseCode=0"
            com.tencent.smtt.utils.TbsLog.i(r2, r3)
            r2 = 0
            goto L_0x0035
        L_0x03dd:
            java.util.Map<java.lang.String, java.lang.Object> r2 = r13.a
            java.lang.String r3 = "tbs_download_interrupt_code_reason"
            r4 = -211(0xffffffffffffff2d, float:NaN)
            java.lang.Integer r4 = java.lang.Integer.valueOf(r4)
            r2.put(r3, r4)
            r2 = -211(0xffffffffffffff2d, float:NaN)
            r13.setDownloadInterruptCode(r2)
            goto L_0x03c5
        L_0x03f0:
            android.content.Context r2 = c
            com.tencent.smtt.sdk.TbsDownloadConfig r2 = com.tencent.smtt.sdk.TbsDownloadConfig.getInstance(r2)
            android.content.SharedPreferences r2 = r2.mPreferences
            java.lang.String r4 = "tbs_download_version"
            r5 = 0
            int r4 = r2.getInt(r4, r5)
            r0 = r18
            if (r4 <= r0) goto L_0x0411
            com.tencent.smtt.sdk.ag r2 = g
            r2.d()
            com.tencent.smtt.sdk.am r2 = com.tencent.smtt.sdk.am.a()
            android.content.Context r5 = c
            r2.o(r5)
        L_0x0411:
            r2 = 0
            android.content.Context r5 = c
            boolean r5 = com.tencent.smtt.sdk.TbsShareManager.isThirdPartyApp(r5)
            if (r5 != 0) goto L_0x044d
            com.tencent.smtt.sdk.am r5 = com.tencent.smtt.sdk.am.a()
            android.content.Context r6 = c
            int r5 = r5.g(r6)
            r0 = r18
            if (r5 < r0) goto L_0x0429
            r2 = 1
        L_0x0429:
            java.lang.String r6 = "TbsDownload"
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r7.<init>()
            java.lang.String r8 = "tmpCoreVersion is "
            java.lang.StringBuilder r7 = r7.append(r8)
            java.lang.StringBuilder r5 = r7.append(r5)
            java.lang.String r7 = " tbsDownloadVersion is"
            java.lang.StringBuilder r5 = r5.append(r7)
            r0 = r18
            java.lang.StringBuilder r5 = r5.append(r0)
            java.lang.String r5 = r5.toString()
            com.tencent.smtt.utils.TbsLog.i(r6, r5)
        L_0x044d:
            r0 = r31
            r1 = r18
            if (r0 >= r1) goto L_0x045b
            boolean r5 = android.text.TextUtils.isEmpty(r16)
            if (r5 != 0) goto L_0x045b
            if (r2 == 0) goto L_0x051b
        L_0x045b:
            r2 = 1
            if (r3 == r2) goto L_0x051b
            java.util.Map<java.lang.String, java.lang.Object> r2 = r13.a
            java.lang.String r3 = "tbs_needdownload"
            r5 = 0
            java.lang.Boolean r5 = java.lang.Boolean.valueOf(r5)
            r2.put(r3, r5)
            if (r32 == 0) goto L_0x04f3
            boolean r2 = android.text.TextUtils.isEmpty(r16)
            if (r2 == 0) goto L_0x04c1
            java.util.Map<java.lang.String, java.lang.Object> r2 = r13.a
            java.lang.String r3 = "tbs_download_interrupt_code_reason"
            r5 = -124(0xffffffffffffff84, float:NaN)
            java.lang.Integer r5 = java.lang.Integer.valueOf(r5)
            r2.put(r3, r5)
        L_0x047f:
            r13.commit()
            java.lang.String r2 = "TbsDownload"
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r5 = "version error or downloadUrl empty ,return ahead tbsLocalVersion="
            java.lang.StringBuilder r3 = r3.append(r5)
            r0 = r31
            java.lang.StringBuilder r3 = r3.append(r0)
            java.lang.String r5 = " tbsDownloadVersion="
            java.lang.StringBuilder r3 = r3.append(r5)
            r0 = r18
            java.lang.StringBuilder r3 = r3.append(r0)
            java.lang.String r5 = " tbsLastDownloadVersion="
            java.lang.StringBuilder r3 = r3.append(r5)
            java.lang.StringBuilder r3 = r3.append(r4)
            java.lang.String r4 = " downloadUrl="
            java.lang.StringBuilder r3 = r3.append(r4)
            r0 = r16
            java.lang.StringBuilder r3 = r3.append(r0)
            java.lang.String r3 = r3.toString()
            com.tencent.smtt.utils.TbsLog.i(r2, r3)
            r2 = 0
            goto L_0x0035
        L_0x04c1:
            if (r18 > 0) goto L_0x04d1
            java.util.Map<java.lang.String, java.lang.Object> r2 = r13.a
            java.lang.String r3 = "tbs_download_interrupt_code_reason"
            r5 = -125(0xffffffffffffff83, float:NaN)
            java.lang.Integer r5 = java.lang.Integer.valueOf(r5)
            r2.put(r3, r5)
            goto L_0x047f
        L_0x04d1:
            r0 = r31
            r1 = r18
            if (r0 < r1) goto L_0x04e5
            java.util.Map<java.lang.String, java.lang.Object> r2 = r13.a
            java.lang.String r3 = "tbs_download_interrupt_code_reason"
            r5 = -127(0xffffffffffffff81, float:NaN)
            java.lang.Integer r5 = java.lang.Integer.valueOf(r5)
            r2.put(r3, r5)
            goto L_0x047f
        L_0x04e5:
            java.util.Map<java.lang.String, java.lang.Object> r2 = r13.a
            java.lang.String r3 = "tbs_download_interrupt_code_reason"
            r5 = -112(0xffffffffffffff90, float:NaN)
            java.lang.Integer r5 = java.lang.Integer.valueOf(r5)
            r2.put(r3, r5)
            goto L_0x047f
        L_0x04f3:
            r2 = -212(0xffffffffffffff2c, float:NaN)
            boolean r3 = android.text.TextUtils.isEmpty(r16)
            if (r3 == 0) goto L_0x050d
            r2 = -217(0xffffffffffffff27, float:NaN)
        L_0x04fd:
            java.util.Map<java.lang.String, java.lang.Object> r3 = r13.a
            java.lang.String r5 = "tbs_download_interrupt_code_reason"
            java.lang.Integer r6 = java.lang.Integer.valueOf(r2)
            r3.put(r5, r6)
            r13.setDownloadInterruptCode(r2)
            goto L_0x047f
        L_0x050d:
            if (r18 > 0) goto L_0x0512
            r2 = -218(0xffffffffffffff26, float:NaN)
            goto L_0x04fd
        L_0x0512:
            r0 = r31
            r1 = r18
            if (r0 < r1) goto L_0x04fd
            r2 = -219(0xffffffffffffff25, float:NaN)
            goto L_0x04fd
        L_0x051b:
            android.content.SharedPreferences r2 = r13.mPreferences
            java.lang.String r4 = "tbs_downloadurl"
            r5 = 0
            java.lang.String r2 = r2.getString(r4, r5)
            r0 = r16
            boolean r2 = r0.equals(r2)
            if (r2 != 0) goto L_0x0549
            com.tencent.smtt.sdk.ag r2 = g
            r2.d()
            java.util.Map<java.lang.String, java.lang.Object> r2 = r13.a
            java.lang.String r4 = "tbs_download_failed_retrytimes"
            r5 = 0
            java.lang.Integer r5 = java.lang.Integer.valueOf(r5)
            r2.put(r4, r5)
            java.util.Map<java.lang.String, java.lang.Object> r2 = r13.a
            java.lang.String r4 = "tbs_download_success_retrytimes"
            r5 = 0
            java.lang.Integer r5 = java.lang.Integer.valueOf(r5)
            r2.put(r4, r5)
        L_0x0549:
            java.util.Map<java.lang.String, java.lang.Object> r2 = r13.a
            java.lang.String r4 = "tbs_download_version"
            java.lang.Integer r5 = java.lang.Integer.valueOf(r18)
            r2.put(r4, r5)
            java.lang.String r2 = "TbsDownload"
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r5 = "put KEY_TBS_DOWNLOAD_V is "
            java.lang.StringBuilder r4 = r4.append(r5)
            r0 = r18
            java.lang.StringBuilder r4 = r4.append(r0)
            java.lang.String r4 = r4.toString()
            com.tencent.smtt.utils.TbsLog.i(r2, r4)
            if (r18 <= 0) goto L_0x0597
            r2 = 1
            if (r3 != r2) goto L_0x0650
            java.util.Map<java.lang.String, java.lang.Object> r2 = r13.a
            java.lang.String r4 = "tbs_download_version_type"
            r5 = 1
            java.lang.Integer r5 = java.lang.Integer.valueOf(r5)
            r2.put(r4, r5)
        L_0x057f:
            java.lang.String r2 = "TbsDownload"
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r5 = "put KEY_TBS_DOWNLOAD_V_TYPE is "
            java.lang.StringBuilder r4 = r4.append(r5)
            java.lang.StringBuilder r3 = r4.append(r3)
            java.lang.String r3 = r3.toString()
            com.tencent.smtt.utils.TbsLog.i(r2, r3)
        L_0x0597:
            java.util.Map<java.lang.String, java.lang.Object> r2 = r13.a
            java.lang.String r3 = "tbs_downloadurl"
            r0 = r16
            r2.put(r3, r0)
            java.util.Map<java.lang.String, java.lang.Object> r2 = r13.a
            java.lang.String r3 = "tbs_downloadurl_list"
            r0 = r17
            r2.put(r3, r0)
            java.util.Map<java.lang.String, java.lang.Object> r2 = r13.a
            java.lang.String r3 = "tbs_responsecode"
            java.lang.Integer r4 = java.lang.Integer.valueOf(r15)
            r2.put(r3, r4)
            java.util.Map<java.lang.String, java.lang.Object> r2 = r13.a
            java.lang.String r3 = "tbs_download_maxflow"
            java.lang.Integer r4 = java.lang.Integer.valueOf(r19)
            r2.put(r3, r4)
            java.util.Map<java.lang.String, java.lang.Object> r2 = r13.a
            java.lang.String r3 = "tbs_download_min_free_space"
            java.lang.Integer r4 = java.lang.Integer.valueOf(r20)
            r2.put(r3, r4)
            java.util.Map<java.lang.String, java.lang.Object> r2 = r13.a
            java.lang.String r3 = "tbs_download_success_max_retrytimes"
            java.lang.Integer r4 = java.lang.Integer.valueOf(r21)
            r2.put(r3, r4)
            java.util.Map<java.lang.String, java.lang.Object> r2 = r13.a
            java.lang.String r3 = "tbs_download_failed_max_retrytimes"
            java.lang.Integer r4 = java.lang.Integer.valueOf(r22)
            r2.put(r3, r4)
            java.util.Map<java.lang.String, java.lang.Object> r2 = r13.a
            java.lang.String r3 = "tbs_single_timeout"
            java.lang.Long r4 = java.lang.Long.valueOf(r24)
            r2.put(r3, r4)
            java.util.Map<java.lang.String, java.lang.Object> r2 = r13.a
            java.lang.String r3 = "tbs_apkfilesize"
            java.lang.Long r4 = java.lang.Long.valueOf(r26)
            r2.put(r3, r4)
            r13.commit()
            if (r9 == 0) goto L_0x0602
            java.util.Map<java.lang.String, java.lang.Object> r2 = r13.a
            java.lang.String r3 = "tbs_apk_md5"
            r2.put(r3, r9)
        L_0x0602:
            if (r33 != 0) goto L_0x0671
            com.tencent.smtt.sdk.am r2 = com.tencent.smtt.sdk.am.a()
            android.content.Context r3 = c
            r0 = r18
            boolean r2 = r2.a((android.content.Context) r3, (int) r0)
            if (r2 == 0) goto L_0x0671
            if (r32 == 0) goto L_0x065e
            java.util.Map<java.lang.String, java.lang.Object> r2 = r13.a
            java.lang.String r3 = "tbs_download_interrupt_code_reason"
            r4 = -113(0xffffffffffffff8f, float:NaN)
            java.lang.Integer r4 = java.lang.Integer.valueOf(r4)
            r2.put(r3, r4)
        L_0x0621:
            java.util.Map<java.lang.String, java.lang.Object> r2 = r13.a
            java.lang.String r3 = "tbs_needdownload"
            r4 = 0
            java.lang.Boolean r4 = java.lang.Boolean.valueOf(r4)
            r2.put(r3, r4)
            java.lang.String r2 = "TbsDownload"
            java.lang.String r3 = "[TbsDownloader.readResponse] ##6 set needDownload=false"
            com.tencent.smtt.utils.TbsLog.i(r2, r3)
        L_0x0634:
            java.lang.String r2 = "stop_pre_oat"
            r3 = 0
            int r2 = r14.optInt(r2, r3)
            r3 = 1
            if (r2 != r3) goto L_0x064a
            java.util.Map<java.lang.String, java.lang.Object> r2 = r13.a
            java.lang.String r3 = "tbs_stop_preoat"
            r4 = 1
            java.lang.Boolean r4 = java.lang.Boolean.valueOf(r4)
            r2.put(r3, r4)
        L_0x064a:
            r13.commit()
            r2 = 1
            goto L_0x0035
        L_0x0650:
            java.util.Map<java.lang.String, java.lang.Object> r2 = r13.a
            java.lang.String r4 = "tbs_download_version_type"
            r5 = 0
            java.lang.Integer r5 = java.lang.Integer.valueOf(r5)
            r2.put(r4, r5)
            goto L_0x057f
        L_0x065e:
            java.util.Map<java.lang.String, java.lang.Object> r2 = r13.a
            java.lang.String r3 = "tbs_download_interrupt_code_reason"
            r4 = -213(0xffffffffffffff2b, float:NaN)
            java.lang.Integer r4 = java.lang.Integer.valueOf(r4)
            r2.put(r3, r4)
            r2 = -213(0xffffffffffffff2b, float:NaN)
            r13.setDownloadInterruptCode(r2)
            goto L_0x0621
        L_0x0671:
            if (r33 != 0) goto L_0x06ed
            com.tencent.smtt.sdk.ag r3 = g
            r2 = 1
            if (r15 == r2) goto L_0x067b
            r2 = 2
            if (r15 != r2) goto L_0x06df
        L_0x067b:
            r2 = 1
        L_0x067c:
            r0 = r33
            boolean r2 = r3.a((boolean) r0, (boolean) r2)
            if (r2 == 0) goto L_0x06ed
            java.util.Map<java.lang.String, java.lang.Object> r2 = r13.a
            java.lang.String r3 = "tbs_needdownload"
            r4 = 0
            java.lang.Boolean r4 = java.lang.Boolean.valueOf(r4)
            r2.put(r3, r4)
            android.content.Context r2 = c
            com.tencent.smtt.sdk.TbsLogReport r2 = com.tencent.smtt.sdk.TbsLogReport.a((android.content.Context) r2)
            com.tencent.smtt.sdk.TbsLogReport$TbsLogInfo r2 = r2.a()
            r3 = 100
            r2.setErrorCode(r3)
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = "use local backup apk in needDownload"
            java.lang.StringBuilder r3 = r3.append(r4)
            com.tencent.smtt.sdk.ag r4 = g
            java.lang.String r4 = r4.a
            java.lang.StringBuilder r3 = r3.append(r4)
            java.lang.String r3 = r3.toString()
            r2.setFailDetail((java.lang.String) r3)
            android.content.Context r3 = c
            com.tencent.smtt.sdk.TbsDownloadConfig r3 = com.tencent.smtt.sdk.TbsDownloadConfig.getInstance(r3)
            android.content.SharedPreferences r3 = r3.mPreferences
            java.lang.String r4 = "tbs_downloaddecouplecore"
            r5 = 0
            int r3 = r3.getInt(r4, r5)
            r4 = 1
            if (r3 != r4) goto L_0x06e1
            android.content.Context r3 = c
            com.tencent.smtt.sdk.TbsLogReport r3 = com.tencent.smtt.sdk.TbsLogReport.a((android.content.Context) r3)
            com.tencent.smtt.sdk.TbsLogReport$EventType r4 = com.tencent.smtt.sdk.TbsLogReport.EventType.TYPE_DOWNLOAD_DECOUPLE
            r3.a((com.tencent.smtt.sdk.TbsLogReport.EventType) r4, (com.tencent.smtt.sdk.TbsLogReport.TbsLogInfo) r2)
        L_0x06d6:
            java.lang.String r2 = "TbsDownload"
            java.lang.String r3 = "[TbsDownloader.readResponse] ##7 set needDownload=false"
            com.tencent.smtt.utils.TbsLog.i(r2, r3)
            goto L_0x0634
        L_0x06df:
            r2 = 0
            goto L_0x067c
        L_0x06e1:
            android.content.Context r3 = c
            com.tencent.smtt.sdk.TbsLogReport r3 = com.tencent.smtt.sdk.TbsLogReport.a((android.content.Context) r3)
            com.tencent.smtt.sdk.TbsLogReport$EventType r4 = com.tencent.smtt.sdk.TbsLogReport.EventType.TYPE_DOWNLOAD
            r3.a((com.tencent.smtt.sdk.TbsLogReport.EventType) r4, (com.tencent.smtt.sdk.TbsLogReport.TbsLogInfo) r2)
            goto L_0x06d6
        L_0x06ed:
            android.content.Context r2 = c
            com.tencent.smtt.sdk.TbsDownloadConfig r2 = com.tencent.smtt.sdk.TbsDownloadConfig.getInstance(r2)
            android.content.SharedPreferences r2 = r2.mPreferences
            java.lang.String r3 = "tbs_download_version_type"
            r4 = 0
            int r2 = r2.getInt(r3, r4)
            r3 = 1
            if (r2 != r3) goto L_0x076e
            com.tencent.smtt.sdk.ag r2 = g
            boolean r2 = r2.a()
            if (r2 == 0) goto L_0x076e
            java.util.Map<java.lang.String, java.lang.Object> r2 = r13.a
            java.lang.String r3 = "tbs_needdownload"
            r4 = 0
            java.lang.Boolean r4 = java.lang.Boolean.valueOf(r4)
            r2.put(r3, r4)
            android.content.Context r2 = c
            com.tencent.smtt.sdk.TbsLogReport r2 = com.tencent.smtt.sdk.TbsLogReport.a((android.content.Context) r2)
            com.tencent.smtt.sdk.TbsLogReport$TbsLogInfo r2 = r2.a()
            r3 = 100
            r2.setErrorCode(r3)
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = "installDecoupleCoreFromBackup"
            java.lang.StringBuilder r3 = r3.append(r4)
            com.tencent.smtt.sdk.ag r4 = g
            java.lang.String r4 = r4.a
            java.lang.StringBuilder r3 = r3.append(r4)
            java.lang.String r3 = r3.toString()
            r2.setFailDetail((java.lang.String) r3)
            android.content.Context r3 = c
            com.tencent.smtt.sdk.TbsDownloadConfig r3 = com.tencent.smtt.sdk.TbsDownloadConfig.getInstance(r3)
            android.content.SharedPreferences r3 = r3.mPreferences
            java.lang.String r4 = "tbs_downloaddecouplecore"
            r5 = 0
            int r3 = r3.getInt(r4, r5)
            r4 = 1
            if (r3 != r4) goto L_0x0762
            android.content.Context r3 = c
            com.tencent.smtt.sdk.TbsLogReport r3 = com.tencent.smtt.sdk.TbsLogReport.a((android.content.Context) r3)
            com.tencent.smtt.sdk.TbsLogReport$EventType r4 = com.tencent.smtt.sdk.TbsLogReport.EventType.TYPE_DOWNLOAD_DECOUPLE
            r3.a((com.tencent.smtt.sdk.TbsLogReport.EventType) r4, (com.tencent.smtt.sdk.TbsLogReport.TbsLogInfo) r2)
        L_0x0759:
            java.lang.String r2 = "TbsDownload"
            java.lang.String r3 = "[TbsDownloader.readResponse] ##8 set needDownload=false"
            com.tencent.smtt.utils.TbsLog.i(r2, r3)
            goto L_0x0634
        L_0x0762:
            android.content.Context r3 = c
            com.tencent.smtt.sdk.TbsLogReport r3 = com.tencent.smtt.sdk.TbsLogReport.a((android.content.Context) r3)
            com.tencent.smtt.sdk.TbsLogReport$EventType r4 = com.tencent.smtt.sdk.TbsLogReport.EventType.TYPE_DOWNLOAD
            r3.a((com.tencent.smtt.sdk.TbsLogReport.EventType) r4, (com.tencent.smtt.sdk.TbsLogReport.TbsLogInfo) r2)
            goto L_0x0759
        L_0x076e:
            if (r32 != 0) goto L_0x0775
            r2 = -216(0xffffffffffffff28, float:NaN)
            r13.setDownloadInterruptCode(r2)
        L_0x0775:
            java.util.Map<java.lang.String, java.lang.Object> r2 = r13.a
            java.lang.String r3 = "tbs_needdownload"
            r4 = 1
            java.lang.Boolean r4 = java.lang.Boolean.valueOf(r4)
            r2.put(r3, r4)
            java.lang.String r2 = "TbsDownload"
            java.lang.String r3 = "[TbsDownloader.readResponse] ##9 set needDownload=true"
            com.tencent.smtt.utils.TbsLog.i(r2, r3)
            goto L_0x0634
        L_0x078a:
            r4 = move-exception
            goto L_0x02c2
        L_0x078d:
            r4 = move-exception
            goto L_0x02b3
        L_0x0790:
            r4 = move-exception
            goto L_0x017f
        L_0x0793:
            r3 = move-exception
            goto L_0x00c6
        L_0x0796:
            r2 = r4
            goto L_0x0290
        L_0x0799:
            r2 = r10
            goto L_0x028a
        L_0x079c:
            r2 = r3
            goto L_0x0171
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.smtt.sdk.TbsDownloader.a(java.lang.String, int, boolean, boolean):boolean");
    }

    protected static File b(int i2) {
        String[] coreProviderAppList = TbsShareManager.getCoreProviderAppList();
        File file = null;
        int length = coreProviderAppList.length;
        int i3 = 0;
        while (i3 < length) {
            String str = coreProviderAppList[i3];
            File file2 = new File(k.a(c, str, 4, false), getOverSea(c) ? "x5.oversea.tbs.org" : "x5.tbs.org");
            if (file2 == null || !file2.exists() || a.a(c, file2) != i2) {
                file = new File(k.a(c, str, 4, false), "x5.tbs.decouple");
                if (file == null || !file.exists() || a.a(c, file) != i2) {
                    i3++;
                } else {
                    TbsLog.i(LOGTAG, "local tbs version fond,path = " + file.getAbsolutePath());
                    return file;
                }
            } else {
                TbsLog.i(LOGTAG, "local tbs version fond,path = " + file2.getAbsolutePath());
                return file2;
            }
        }
        return file;
    }

    /* JADX WARNING: Removed duplicated region for block: B:12:0x0084 A[Catch:{ Exception -> 0x0254 }] */
    /* JADX WARNING: Removed duplicated region for block: B:16:0x0092 A[Catch:{ Exception -> 0x0254 }] */
    /* JADX WARNING: Removed duplicated region for block: B:19:0x00a0 A[Catch:{ Exception -> 0x0254 }] */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x010f A[Catch:{ Exception -> 0x0254 }] */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x0119 A[Catch:{ Exception -> 0x0254 }] */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x0126 A[Catch:{ Exception -> 0x0254 }] */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x0160 A[Catch:{ Exception -> 0x0254 }] */
    /* JADX WARNING: Removed duplicated region for block: B:43:0x019d A[Catch:{ Exception -> 0x0254 }] */
    /* JADX WARNING: Removed duplicated region for block: B:48:0x01aa A[Catch:{ Exception -> 0x0254 }] */
    /* JADX WARNING: Removed duplicated region for block: B:51:0x01b8 A[Catch:{ Exception -> 0x0254 }] */
    /* JADX WARNING: Removed duplicated region for block: B:53:0x01c0 A[Catch:{ Exception -> 0x0254 }] */
    /* JADX WARNING: Removed duplicated region for block: B:61:0x01fc A[Catch:{ Exception -> 0x0254 }] */
    /* JADX WARNING: Removed duplicated region for block: B:72:0x024a A[Catch:{ Exception -> 0x0254 }] */
    /* JADX WARNING: Removed duplicated region for block: B:79:0x0259 A[Catch:{ Exception -> 0x0254 }] */
    /* JADX WARNING: Removed duplicated region for block: B:86:0x0277 A[Catch:{ Exception -> 0x0254 }] */
    /* JADX WARNING: Removed duplicated region for block: B:87:0x027a A[Catch:{ Exception -> 0x0254 }] */
    /* JADX WARNING: Removed duplicated region for block: B:90:0x0288  */
    /* JADX WARNING: Removed duplicated region for block: B:92:0x028e  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static org.json.JSONObject b(boolean r13, boolean r14, boolean r15) {
        /*
            r4 = 1
            r3 = 0
            java.lang.String r0 = "TbsDownload"
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "[TbsDownloader.postJsonData]isQuery: "
            java.lang.StringBuilder r1 = r1.append(r2)
            java.lang.StringBuilder r1 = r1.append(r13)
            java.lang.String r2 = " forDecoupleCore is "
            java.lang.StringBuilder r1 = r1.append(r2)
            java.lang.StringBuilder r1 = r1.append(r15)
            java.lang.String r1 = r1.toString()
            com.tencent.smtt.utils.TbsLog.i(r0, r1)
            android.content.Context r0 = c
            com.tencent.smtt.sdk.TbsDownloadConfig r6 = com.tencent.smtt.sdk.TbsDownloadConfig.getInstance(r0)
            android.content.Context r0 = c
            java.lang.String r7 = a((android.content.Context) r0)
            android.content.Context r0 = c
            java.lang.String r8 = com.tencent.smtt.utils.b.d(r0)
            android.content.Context r0 = c
            java.lang.String r9 = com.tencent.smtt.utils.b.c(r0)
            android.content.Context r0 = c
            java.lang.String r10 = com.tencent.smtt.utils.b.f(r0)
            java.lang.String r0 = ""
            java.lang.String r2 = ""
            java.lang.String r1 = ""
            java.util.TimeZone r1 = java.util.TimeZone.getDefault()
            java.lang.String r1 = r1.getID()
            if (r1 == 0) goto L_0x0291
            r5 = r1
        L_0x0053:
            android.content.Context r0 = c     // Catch:{ Exception -> 0x01e3 }
            java.lang.String r11 = "phone"
            java.lang.Object r0 = r0.getSystemService(r11)     // Catch:{ Exception -> 0x01e3 }
            android.telephony.TelephonyManager r0 = (android.telephony.TelephonyManager) r0     // Catch:{ Exception -> 0x01e3 }
            android.telephony.TelephonyManager r0 = (android.telephony.TelephonyManager) r0     // Catch:{ Exception -> 0x01e3 }
            if (r0 == 0) goto L_0x01e7
            java.lang.String r0 = r0.getSimCountryIso()     // Catch:{ Exception -> 0x01e3 }
        L_0x0065:
            if (r0 == 0) goto L_0x028e
        L_0x0067:
            org.json.JSONObject r2 = new org.json.JSONObject
            r2.<init>()
            java.lang.String r1 = "TIMEZONEID"
            r2.put(r1, r5)     // Catch:{ Exception -> 0x0254 }
            java.lang.String r1 = "COUNTRYISO"
            r2.put(r1, r0)     // Catch:{ Exception -> 0x0254 }
            java.lang.String r0 = "PROTOCOLVERSION"
            r1 = 1
            r2.put(r0, r1)     // Catch:{ Exception -> 0x0254 }
            android.content.Context r0 = c     // Catch:{ Exception -> 0x0254 }
            boolean r0 = com.tencent.smtt.sdk.TbsShareManager.isThirdPartyApp(r0)     // Catch:{ Exception -> 0x0254 }
            if (r0 == 0) goto L_0x01fc
            boolean r0 = com.tencent.smtt.sdk.QbSdk.c     // Catch:{ Exception -> 0x0254 }
            if (r0 == 0) goto L_0x01ea
            android.content.Context r0 = c     // Catch:{ Exception -> 0x0254 }
            r1 = 0
            int r0 = com.tencent.smtt.sdk.TbsShareManager.a((android.content.Context) r0, (boolean) r1)     // Catch:{ Exception -> 0x0254 }
            r1 = r0
        L_0x0090:
            if (r13 == 0) goto L_0x024a
            java.lang.String r0 = "FUNCTION"
            r5 = 2
            r2.put(r0, r5)     // Catch:{ Exception -> 0x0254 }
        L_0x0098:
            android.content.Context r0 = c     // Catch:{ Exception -> 0x0254 }
            boolean r0 = com.tencent.smtt.sdk.TbsShareManager.isThirdPartyApp(r0)     // Catch:{ Exception -> 0x0254 }
            if (r0 == 0) goto L_0x0259
            org.json.JSONArray r0 = f()     // Catch:{ Exception -> 0x0254 }
            java.lang.String r5 = "TBSVLARR"
            r2.put(r5, r0)     // Catch:{ Exception -> 0x0254 }
            java.util.Map<java.lang.String, java.lang.Object> r5 = r6.a     // Catch:{ Exception -> 0x0254 }
            java.lang.String r11 = "last_thirdapp_sendrequest_coreversion"
            java.lang.String r0 = r0.toString()     // Catch:{ Exception -> 0x0254 }
            r5.put(r11, r0)     // Catch:{ Exception -> 0x0254 }
            r6.commit()     // Catch:{ Exception -> 0x0254 }
            boolean r0 = com.tencent.smtt.sdk.QbSdk.c     // Catch:{ Exception -> 0x0254 }
            if (r0 == 0) goto L_0x00c1
            java.lang.String r0 = "THIRDREQ"
            r5 = 1
            r2.put(r0, r5)     // Catch:{ Exception -> 0x0254 }
        L_0x00c1:
            android.content.Context r0 = c     // Catch:{ Exception -> 0x0254 }
            java.lang.String r0 = r0.getPackageName()     // Catch:{ Exception -> 0x0254 }
            java.lang.String r5 = "APPN"
            r2.put(r5, r0)     // Catch:{ Exception -> 0x0254 }
            java.lang.String r0 = "APPVN"
            android.content.SharedPreferences r5 = r6.mPreferences     // Catch:{ Exception -> 0x0254 }
            java.lang.String r11 = "app_versionname"
            r12 = 0
            java.lang.String r5 = r5.getString(r11, r12)     // Catch:{ Exception -> 0x0254 }
            java.lang.String r5 = a((java.lang.String) r5)     // Catch:{ Exception -> 0x0254 }
            r2.put(r0, r5)     // Catch:{ Exception -> 0x0254 }
            java.lang.String r0 = "APPVC"
            android.content.SharedPreferences r5 = r6.mPreferences     // Catch:{ Exception -> 0x0254 }
            java.lang.String r11 = "app_versioncode"
            r12 = 0
            int r5 = r5.getInt(r11, r12)     // Catch:{ Exception -> 0x0254 }
            r2.put(r0, r5)     // Catch:{ Exception -> 0x0254 }
            java.lang.String r0 = "APPMETA"
            android.content.SharedPreferences r5 = r6.mPreferences     // Catch:{ Exception -> 0x0254 }
            java.lang.String r11 = "app_metadata"
            r12 = 0
            java.lang.String r5 = r5.getString(r11, r12)     // Catch:{ Exception -> 0x0254 }
            java.lang.String r5 = a((java.lang.String) r5)     // Catch:{ Exception -> 0x0254 }
            r2.put(r0, r5)     // Catch:{ Exception -> 0x0254 }
            java.lang.String r0 = "TBSSDKV"
            r5 = 43600(0xaa50, float:6.1097E-41)
            r2.put(r0, r5)     // Catch:{ Exception -> 0x0254 }
            java.lang.String r0 = "TBSV"
            r2.put(r0, r1)     // Catch:{ Exception -> 0x0254 }
            java.lang.String r5 = "DOWNLOADDECOUPLECORE"
            if (r15 == 0) goto L_0x0277
            r0 = r4
        L_0x0110:
            r2.put(r5, r0)     // Catch:{ Exception -> 0x0254 }
            java.util.Map<java.lang.String, java.lang.Object> r5 = r6.a     // Catch:{ Exception -> 0x0254 }
            java.lang.String r11 = "tbs_downloaddecouplecore"
            if (r15 == 0) goto L_0x027a
            r0 = r4
        L_0x011a:
            java.lang.Integer r0 = java.lang.Integer.valueOf(r0)     // Catch:{ Exception -> 0x0254 }
            r5.put(r11, r0)     // Catch:{ Exception -> 0x0254 }
            r6.commit()     // Catch:{ Exception -> 0x0254 }
            if (r1 == 0) goto L_0x0131
            java.lang.String r0 = "TBSBACKUPV"
            com.tencent.smtt.sdk.ag r5 = g     // Catch:{ Exception -> 0x0254 }
            int r5 = r5.b()     // Catch:{ Exception -> 0x0254 }
            r2.put(r0, r5)     // Catch:{ Exception -> 0x0254 }
        L_0x0131:
            java.lang.String r0 = "CPU"
            java.lang.String r5 = e     // Catch:{ Exception -> 0x0254 }
            r2.put(r0, r5)     // Catch:{ Exception -> 0x0254 }
            java.lang.String r0 = "UA"
            r2.put(r0, r7)     // Catch:{ Exception -> 0x0254 }
            java.lang.String r0 = "IMSI"
            java.lang.String r5 = a((java.lang.String) r8)     // Catch:{ Exception -> 0x0254 }
            r2.put(r0, r5)     // Catch:{ Exception -> 0x0254 }
            java.lang.String r0 = "IMEI"
            java.lang.String r5 = a((java.lang.String) r9)     // Catch:{ Exception -> 0x0254 }
            r2.put(r0, r5)     // Catch:{ Exception -> 0x0254 }
            java.lang.String r0 = "ANDROID_ID"
            java.lang.String r5 = a((java.lang.String) r10)     // Catch:{ Exception -> 0x0254 }
            r2.put(r0, r5)     // Catch:{ Exception -> 0x0254 }
            android.content.Context r0 = c     // Catch:{ Exception -> 0x0254 }
            boolean r0 = com.tencent.smtt.sdk.TbsShareManager.isThirdPartyApp(r0)     // Catch:{ Exception -> 0x0254 }
            if (r0 != 0) goto L_0x017f
            if (r1 == 0) goto L_0x0280
            java.lang.String r5 = "STATUS"
            android.content.Context r0 = c     // Catch:{ Exception -> 0x0254 }
            boolean r0 = com.tencent.smtt.sdk.QbSdk.a((android.content.Context) r0, (int) r1)     // Catch:{ Exception -> 0x0254 }
            if (r0 == 0) goto L_0x027d
            r0 = r3
        L_0x016d:
            r2.put(r5, r0)     // Catch:{ Exception -> 0x0254 }
        L_0x0170:
            java.lang.String r0 = "TBSDV"
            com.tencent.smtt.sdk.am r1 = com.tencent.smtt.sdk.am.a()     // Catch:{ Exception -> 0x0254 }
            android.content.Context r5 = c     // Catch:{ Exception -> 0x0254 }
            int r1 = r1.h(r5)     // Catch:{ Exception -> 0x0254 }
            r2.put(r0, r1)     // Catch:{ Exception -> 0x0254 }
        L_0x017f:
            android.content.Context r0 = c     // Catch:{ Exception -> 0x0254 }
            com.tencent.smtt.sdk.TbsDownloadConfig r0 = com.tencent.smtt.sdk.TbsDownloadConfig.getInstance(r0)     // Catch:{ Exception -> 0x0254 }
            android.content.SharedPreferences r0 = r0.mPreferences     // Catch:{ Exception -> 0x0254 }
            java.lang.String r1 = "request_full_package"
            r5 = 0
            boolean r1 = r0.getBoolean(r1, r5)     // Catch:{ Exception -> 0x0254 }
            android.content.Context r0 = c     // Catch:{ Exception -> 0x0254 }
            java.lang.String r5 = "can_unlzma"
            r6 = 0
            java.lang.Object r0 = com.tencent.smtt.sdk.QbSdk.a((android.content.Context) r0, (java.lang.String) r5, (android.os.Bundle) r6)     // Catch:{ Exception -> 0x0254 }
            if (r0 == 0) goto L_0x0288
            boolean r5 = r0 instanceof java.lang.Boolean     // Catch:{ Exception -> 0x0254 }
            if (r5 == 0) goto L_0x0288
            java.lang.Boolean r0 = (java.lang.Boolean) r0     // Catch:{ Exception -> 0x0254 }
            boolean r0 = r0.booleanValue()     // Catch:{ Exception -> 0x0254 }
        L_0x01a3:
            if (r0 == 0) goto L_0x01a8
            if (r1 != 0) goto L_0x01a8
            r3 = r4
        L_0x01a8:
            if (r3 == 0) goto L_0x01b0
            java.lang.String r0 = "REQUEST_LZMA"
            r1 = 1
            r2.put(r0, r1)     // Catch:{ Exception -> 0x0254 }
        L_0x01b0:
            android.content.Context r0 = c     // Catch:{ Exception -> 0x0254 }
            boolean r0 = getOverSea(r0)     // Catch:{ Exception -> 0x0254 }
            if (r0 == 0) goto L_0x01be
            java.lang.String r0 = "OVERSEA"
            r1 = 1
            r2.put(r0, r1)     // Catch:{ Exception -> 0x0254 }
        L_0x01be:
            if (r14 == 0) goto L_0x01c6
            java.lang.String r0 = "DOWNLOAD_FOREGROUND"
            r1 = 1
            r2.put(r0, r1)     // Catch:{ Exception -> 0x0254 }
        L_0x01c6:
            java.lang.String r0 = "TbsDownload"
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r3 = "[TbsDownloader.postJsonData] jsonData="
            java.lang.StringBuilder r1 = r1.append(r3)
            java.lang.String r3 = r2.toString()
            java.lang.StringBuilder r1 = r1.append(r3)
            java.lang.String r1 = r1.toString()
            com.tencent.smtt.utils.TbsLog.i(r0, r1)
            return r2
        L_0x01e3:
            r0 = move-exception
            r0.printStackTrace()
        L_0x01e7:
            r0 = r1
            goto L_0x0065
        L_0x01ea:
            android.content.Context r0 = c     // Catch:{ Exception -> 0x0254 }
            com.tencent.smtt.sdk.TbsDownloadConfig r0 = com.tencent.smtt.sdk.TbsDownloadConfig.getInstance(r0)     // Catch:{ Exception -> 0x0254 }
            android.content.SharedPreferences r0 = r0.mPreferences     // Catch:{ Exception -> 0x0254 }
            java.lang.String r1 = "tbs_download_version"
            r5 = 0
            int r0 = r0.getInt(r1, r5)     // Catch:{ Exception -> 0x0254 }
            r1 = r0
            goto L_0x0090
        L_0x01fc:
            com.tencent.smtt.sdk.am r0 = com.tencent.smtt.sdk.am.a()     // Catch:{ Exception -> 0x0254 }
            android.content.Context r1 = c     // Catch:{ Exception -> 0x0254 }
            int r0 = r0.m(r1)     // Catch:{ Exception -> 0x0254 }
            if (r0 != 0) goto L_0x0215
            com.tencent.smtt.sdk.am r1 = com.tencent.smtt.sdk.am.a()     // Catch:{ Exception -> 0x0254 }
            android.content.Context r5 = c     // Catch:{ Exception -> 0x0254 }
            boolean r1 = r1.l(r5)     // Catch:{ Exception -> 0x0254 }
            if (r1 == 0) goto L_0x0215
            r0 = -1
        L_0x0215:
            java.lang.String r1 = "TbsDownload"
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0254 }
            r5.<init>()     // Catch:{ Exception -> 0x0254 }
            java.lang.String r11 = "[TbsDownloader.postJsonData] tbsLocalVersion="
            java.lang.StringBuilder r5 = r5.append(r11)     // Catch:{ Exception -> 0x0254 }
            java.lang.StringBuilder r5 = r5.append(r0)     // Catch:{ Exception -> 0x0254 }
            java.lang.String r11 = " isDownloadForeground="
            java.lang.StringBuilder r5 = r5.append(r11)     // Catch:{ Exception -> 0x0254 }
            java.lang.StringBuilder r5 = r5.append(r14)     // Catch:{ Exception -> 0x0254 }
            java.lang.String r5 = r5.toString()     // Catch:{ Exception -> 0x0254 }
            com.tencent.smtt.utils.TbsLog.i(r1, r5)     // Catch:{ Exception -> 0x0254 }
            if (r14 == 0) goto L_0x028b
            com.tencent.smtt.sdk.am r1 = com.tencent.smtt.sdk.am.a()     // Catch:{ Exception -> 0x0254 }
            android.content.Context r5 = c     // Catch:{ Exception -> 0x0254 }
            boolean r1 = r1.l(r5)     // Catch:{ Exception -> 0x0254 }
            if (r1 == 0) goto L_0x0248
        L_0x0245:
            r1 = r0
            goto L_0x0090
        L_0x0248:
            r0 = r3
            goto L_0x0245
        L_0x024a:
            java.lang.String r5 = "FUNCTION"
            if (r1 != 0) goto L_0x0257
            r0 = r3
        L_0x024f:
            r2.put(r5, r0)     // Catch:{ Exception -> 0x0254 }
            goto L_0x0098
        L_0x0254:
            r0 = move-exception
            goto L_0x01c6
        L_0x0257:
            r0 = r4
            goto L_0x024f
        L_0x0259:
            org.json.JSONArray r0 = h()     // Catch:{ Exception -> 0x0254 }
            android.content.Context r5 = c     // Catch:{ Exception -> 0x0254 }
            int r5 = com.tencent.smtt.utils.Apn.getApnType(r5)     // Catch:{ Exception -> 0x0254 }
            r11 = 3
            if (r5 == r11) goto L_0x00c1
            int r5 = r0.length()     // Catch:{ Exception -> 0x0254 }
            if (r5 == 0) goto L_0x00c1
            if (r1 != 0) goto L_0x00c1
            if (r13 == 0) goto L_0x00c1
            java.lang.String r5 = "TBSBACKUPARR"
            r2.put(r5, r0)     // Catch:{ Exception -> 0x0254 }
            goto L_0x00c1
        L_0x0277:
            r0 = r3
            goto L_0x0110
        L_0x027a:
            r0 = r3
            goto L_0x011a
        L_0x027d:
            r0 = r4
            goto L_0x016d
        L_0x0280:
            java.lang.String r0 = "STATUS"
            r1 = 0
            r2.put(r0, r1)     // Catch:{ Exception -> 0x0254 }
            goto L_0x0170
        L_0x0288:
            r0 = r3
            goto L_0x01a3
        L_0x028b:
            r1 = r0
            goto L_0x0090
        L_0x028e:
            r0 = r2
            goto L_0x0067
        L_0x0291:
            r5 = r0
            goto L_0x0053
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.smtt.sdk.TbsDownloader.b(boolean, boolean, boolean):org.json.JSONObject");
    }

    @TargetApi(11)
    static void b(Context context) {
        TbsDownloadConfig.getInstance(context).clear();
        TbsLogReport.a(context).d();
        ag.c(context);
        (Build.VERSION.SDK_INT >= 11 ? context.getSharedPreferences("tbs_extension_config", 4) : context.getSharedPreferences("tbs_extension_config", 0)).edit().clear().commit();
        (Build.VERSION.SDK_INT >= 11 ? context.getSharedPreferences("tbs_preloadx5_check_cfg_file", 4) : context.getSharedPreferences("tbs_preloadx5_check_cfg_file", 0)).edit().clear().commit();
    }

    /* access modifiers changed from: private */
    public static boolean b(boolean z, boolean z2) {
        return c(z, z2, false);
    }

    private static boolean c() {
        try {
            for (String sharedTbsCoreVersion : TbsShareManager.getCoreProviderAppList()) {
                if (TbsShareManager.getSharedTbsCoreVersion(c, sharedTbsCoreVersion) > 0) {
                    return true;
                }
            }
            return false;
        } catch (Exception e2) {
            e2.printStackTrace();
            return false;
        }
    }

    /* access modifiers changed from: private */
    public static boolean c(boolean z, boolean z2, boolean z3) {
        int i2;
        if (QbSdk.m == null || !QbSdk.m.containsKey(QbSdk.KEY_SET_SENDREQUEST_AND_UPLOAD) || !QbSdk.m.get(QbSdk.KEY_SET_SENDREQUEST_AND_UPLOAD).equals("false")) {
            TbsLog.i(LOGTAG, "[TbsDownloader.sendRequest]isQuery: " + z + " forDecoupleCore is " + z3);
            if (am.a().c(c)) {
                TbsLog.i(LOGTAG, "[TbsDownloader.sendRequest] -- isTbsLocalInstalled!");
                return false;
            }
            TbsDownloadConfig instance = TbsDownloadConfig.getInstance(c);
            File file = new File(k.a(c, 1), getOverSea(c) ? "x5.oversea.tbs.org" : "x5.tbs.org");
            File file2 = new File(k.a(c, 2), getOverSea(c) ? "x5.oversea.tbs.org" : "x5.tbs.org");
            File file3 = new File(k.a(c, 3), getOverSea(c) ? "x5.oversea.tbs.org" : "x5.tbs.org");
            File file4 = new File(k.a(c, 4), getOverSea(c) ? "x5.oversea.tbs.org" : "x5.tbs.org");
            if (!file4.exists()) {
                if (file3.exists()) {
                    file3.renameTo(file4);
                } else if (file2.exists()) {
                    file2.renameTo(file4);
                } else if (file.exists()) {
                    file.renameTo(file4);
                }
            }
            if (e == null) {
                e = b.a();
                instance.a.put(TbsDownloadConfig.TbsConfigKey.KEY_DEVICE_CPUABI, e);
                instance.commit();
            }
            boolean z4 = false;
            if (!TextUtils.isEmpty(e)) {
                Matcher matcher = null;
                try {
                    matcher = Pattern.compile("i686|mips|x86_64").matcher(e);
                } catch (Exception e2) {
                }
                if (matcher != null && matcher.find()) {
                    if (TbsShareManager.isThirdPartyApp(c)) {
                        TbsLogReport.TbsLogInfo a2 = TbsLogReport.a(c).a();
                        if (z) {
                            instance.setDownloadInterruptCode(-104);
                            a2.setErrorCode(-104);
                        } else {
                            instance.setDownloadInterruptCode(-205);
                            a2.setErrorCode(-205);
                        }
                        a2.setFailDetail("mycpu is " + e);
                        TbsLogReport.a(c).a(TbsLogReport.EventType.TYPE_DOWNLOAD, a2);
                    } else if (z) {
                        instance.setDownloadInterruptCode(-104);
                    } else {
                        instance.setDownloadInterruptCode(-205);
                    }
                    z4 = true;
                }
            }
            JSONObject b2 = b(z, z2, z3);
            try {
                i2 = b2.getInt("TBSV");
            } catch (Exception e3) {
                i2 = -1;
            }
            if (z4 || i2 != -1) {
                long currentTimeMillis = System.currentTimeMillis();
                if (TbsShareManager.isThirdPartyApp(c)) {
                    instance.a.put(TbsDownloadConfig.TbsConfigKey.KEY_COUNT_REQUEST_FAIL_IN_24HOURS, Long.valueOf(currentTimeMillis - instance.mPreferences.getLong(TbsDownloadConfig.TbsConfigKey.KEY_REQUEST_FAIL, 0) < instance.getRetryInterval() * 1000 ? 1 + instance.mPreferences.getLong(TbsDownloadConfig.TbsConfigKey.KEY_COUNT_REQUEST_FAIL_IN_24HOURS, 0) : 1));
                }
                instance.a.put(TbsDownloadConfig.TbsConfigKey.KEY_LAST_CHECK, Long.valueOf(currentTimeMillis));
                instance.a.put(TbsDownloadConfig.TbsConfigKey.KEY_REQUEST_FAIL, Long.valueOf(currentTimeMillis));
                instance.a.put(TbsDownloadConfig.TbsConfigKey.KEY_APP_VERSIONNAME, b.a(c));
                instance.a.put(TbsDownloadConfig.TbsConfigKey.KEY_APP_VERSIONCODE, Integer.valueOf(b.b(c)));
                instance.a.put(TbsDownloadConfig.TbsConfigKey.KEY_APP_METADATA, b.a(c, "com.tencent.mm.BuildInfo.CLIENT_VERSION"));
                instance.commit();
                if (z4) {
                    return false;
                }
            }
            if (i2 != -1) {
                try {
                    String d2 = x.a(c).d();
                    TbsLog.i(LOGTAG, "[TbsDownloader.sendRequest] postUrl=" + d2);
                    return a(n.a(d2, b2.toString().getBytes("utf-8"), new ak(instance, z), false), i2, z, z2);
                } catch (Throwable th) {
                    th.printStackTrace();
                    if (z) {
                        instance.setDownloadInterruptCode(NetworkBindingListener.NB_PREPARE_GEN_FD_FAIL);
                        return false;
                    }
                    instance.setDownloadInterruptCode(-206);
                }
            }
            return false;
        }
        TbsLog.i(LOGTAG, "[TbsDownloader.sendRequest] -- SET_SENDREQUEST_AND_UPLOAD is false");
        return false;
    }

    private static synchronized void d() {
        synchronized (TbsDownloader.class) {
            if (h == null) {
                h = al.a();
                try {
                    g = new ag(c);
                    d = new aj(h.getLooper());
                } catch (Exception e2) {
                    i = true;
                    TbsLog.e(LOGTAG, "TbsApkDownloader init has Exception");
                }
            }
        }
        return;
    }

    private static boolean e() {
        try {
            return TbsDownloadConfig.getInstance(c).mPreferences.getString(TbsDownloadConfig.TbsConfigKey.KEY_LAST_THIRDAPP_SENDREQUEST_COREVERSION, "").equals(f().toString());
        } catch (Exception e2) {
            return false;
        }
    }

    private static JSONArray f() {
        String[] strArr;
        boolean z;
        boolean z2;
        boolean z3 = false;
        if (!TbsShareManager.isThirdPartyApp(c)) {
            return null;
        }
        JSONArray jSONArray = new JSONArray();
        if (QbSdk.getOnlyDownload()) {
            strArr = new String[]{c.getApplicationContext().getPackageName()};
        } else {
            String[] coreProviderAppList = TbsShareManager.getCoreProviderAppList();
            String packageName = c.getApplicationContext().getPackageName();
            if (packageName.equals(TbsShareManager.f(c))) {
                int length = coreProviderAppList.length;
                strArr = new String[(length + 1)];
                System.arraycopy(coreProviderAppList, 0, strArr, 0, length);
                strArr[length] = packageName;
            } else {
                strArr = coreProviderAppList;
            }
        }
        for (String str : strArr) {
            int sharedTbsCoreVersion = TbsShareManager.getSharedTbsCoreVersion(c, str);
            if (sharedTbsCoreVersion > 0) {
                Context a2 = TbsShareManager.a(c, str);
                if (a2 == null || am.a().f(a2)) {
                    int i2 = 0;
                    while (true) {
                        if (i2 >= jSONArray.length()) {
                            z2 = false;
                            break;
                        } else if (jSONArray.optInt(i2) == sharedTbsCoreVersion) {
                            z2 = true;
                            break;
                        } else {
                            i2++;
                        }
                    }
                    if (!z2) {
                        jSONArray.put(sharedTbsCoreVersion);
                    }
                } else {
                    TbsLog.e(LOGTAG, "host check failed,packageName = " + str);
                }
            }
        }
        for (String str2 : strArr) {
            int coreShareDecoupleCoreVersion = TbsShareManager.getCoreShareDecoupleCoreVersion(c, str2);
            if (coreShareDecoupleCoreVersion > 0) {
                Context a3 = TbsShareManager.a(c, str2);
                if (a3 == null || am.a().f(a3)) {
                    int i3 = 0;
                    while (true) {
                        if (i3 >= jSONArray.length()) {
                            z = false;
                            break;
                        } else if (jSONArray.optInt(i3) == coreShareDecoupleCoreVersion) {
                            z = true;
                            break;
                        } else {
                            i3++;
                        }
                    }
                    if (!z) {
                        jSONArray.put(coreShareDecoupleCoreVersion);
                    }
                } else {
                    TbsLog.e(LOGTAG, "host check failed,packageName = " + str2);
                }
            }
        }
        if (TbsShareManager.getHostCorePathAppDefined() == null) {
            return jSONArray;
        }
        int a4 = am.a().a(TbsShareManager.getHostCorePathAppDefined());
        int i4 = 0;
        while (true) {
            if (i4 >= jSONArray.length()) {
                break;
            } else if (jSONArray.optInt(i4) == a4) {
                z3 = true;
                break;
            } else {
                i4++;
            }
        }
        if (!z3) {
            jSONArray.put(a4);
        }
        return jSONArray;
    }

    private static boolean g() {
        TbsDownloadConfig instance = TbsDownloadConfig.getInstance(c);
        if (instance.mPreferences.getInt(TbsDownloadConfig.TbsConfigKey.KEY_DOWNLOAD_SUCCESS_RETRYTIMES, 0) >= instance.getDownloadSuccessMaxRetrytimes()) {
            TbsLog.i(LOGTAG, "[TbsDownloader.needStartDownload] out of success retrytimes", true);
            instance.setDownloadInterruptCode(-115);
            return false;
        } else if (instance.mPreferences.getInt(TbsDownloadConfig.TbsConfigKey.KEY_DOWNLOAD_FAILED_RETRYTIMES, 0) >= instance.getDownloadFailedMaxRetrytimes()) {
            TbsLog.i(LOGTAG, "[TbsDownloader.needStartDownload] out of failed retrytimes", true);
            instance.setDownloadInterruptCode(-116);
            return false;
        } else if (!k.b(c)) {
            TbsLog.i(LOGTAG, "[TbsDownloader.needStartDownload] local rom freespace limit", true);
            instance.setDownloadInterruptCode(-117);
            return false;
        } else {
            if (System.currentTimeMillis() - instance.mPreferences.getLong(TbsDownloadConfig.TbsConfigKey.KEY_TBSDOWNLOAD_STARTTIME, 0) <= TimeUtils.MILLIS_IN_DAY) {
                long j2 = instance.mPreferences.getLong(TbsDownloadConfig.TbsConfigKey.KEY_TBSDOWNLOAD_FLOW, 0);
                TbsLog.i(LOGTAG, "[TbsDownloader.needStartDownload] downloadFlow=" + j2);
                if (j2 >= instance.getDownloadMaxflow()) {
                    TbsLog.i(LOGTAG, "[TbsDownloader.needStartDownload] failed because you exceeded max flow!", true);
                    instance.setDownloadInterruptCode(-120);
                    return false;
                }
            }
            return true;
        }
    }

    public static int getCoreShareDecoupleCoreVersion() {
        return am.a().h(c);
    }

    public static int getCoreShareDecoupleCoreVersionByContext(Context context) {
        return am.a().h(context);
    }

    public static synchronized boolean getOverSea(Context context) {
        boolean z;
        synchronized (TbsDownloader.class) {
            if (!k) {
                k = true;
                TbsDownloadConfig instance = TbsDownloadConfig.getInstance(context);
                if (instance.mPreferences.contains(TbsDownloadConfig.TbsConfigKey.KEY_IS_OVERSEA)) {
                    j = instance.mPreferences.getBoolean(TbsDownloadConfig.TbsConfigKey.KEY_IS_OVERSEA, false);
                    TbsLog.i(LOGTAG, "[TbsDownloader.getOverSea]  first called. sOverSea = " + j);
                }
                TbsLog.i(LOGTAG, "[TbsDownloader.getOverSea]  sOverSea = " + j);
            }
            z = j;
        }
        return z;
    }

    public static long getRetryIntervalInSeconds() {
        return l;
    }

    public static HandlerThread getsTbsHandlerThread() {
        return h;
    }

    private static JSONArray h() {
        boolean z;
        JSONArray jSONArray = new JSONArray();
        for (String a2 : TbsShareManager.getCoreProviderAppList()) {
            File file = new File(k.a(c, a2, 4, false), getOverSea(c) ? "x5.oversea.tbs.org" : "x5.tbs.org");
            if (file != null && file.exists()) {
                long a3 = (long) a.a(c, file);
                if (a3 > 0) {
                    int i2 = 0;
                    while (true) {
                        if (i2 >= jSONArray.length()) {
                            z = false;
                            break;
                        } else if (((long) jSONArray.optInt(i2)) == a3) {
                            z = true;
                            break;
                        } else {
                            i2++;
                        }
                    }
                    if (!z) {
                        jSONArray.put(a3);
                    }
                }
            }
        }
        return jSONArray;
    }

    public static boolean isDownloadForeground() {
        return g != null && g.e();
    }

    public static synchronized boolean isDownloading() {
        boolean z;
        synchronized (TbsDownloader.class) {
            TbsLog.i(LOGTAG, "[TbsDownloader.isDownloading] is " + a);
            z = a;
        }
        return z;
    }

    public static boolean needDownload(Context context, boolean z) {
        return needDownload(context, z, false, (TbsDownloaderCallback) null);
    }

    public static boolean needDownload(Context context, boolean z, boolean z2, TbsDownloaderCallback tbsDownloaderCallback) {
        boolean z3;
        boolean z4;
        boolean z5;
        TbsLog.i(LOGTAG, "[TbsDownloader.needDownload] oversea=" + z + ",isDownloadForeground=" + z2);
        TbsLog.initIfNeed(context);
        if (am.b) {
            if (tbsDownloaderCallback != null) {
                tbsDownloaderCallback.onNeedDownloadFinish(false, 0);
            }
            TbsLog.i(LOGTAG, "[TbsDownloader.needDownload]#1,return " + false);
            return false;
        }
        TbsLog.app_extra(LOGTAG, context);
        c = context.getApplicationContext();
        TbsDownloadConfig instance = TbsDownloadConfig.getInstance(c);
        instance.setDownloadInterruptCode(-100);
        if (!a(c, z, tbsDownloaderCallback)) {
            TbsLog.i(LOGTAG, "[TbsDownloader.needDownload]#2,return " + false);
            return false;
        }
        d();
        if (i) {
            if (tbsDownloaderCallback != null) {
                tbsDownloaderCallback.onNeedDownloadFinish(false, 0);
            }
            instance.setDownloadInterruptCode(-105);
            TbsLog.i(LOGTAG, "[TbsDownloader.needDownload]#3,return " + false);
            return false;
        }
        boolean a2 = a(c, z2, false);
        TbsLog.i(LOGTAG, "[TbsDownloader.needDownload],needSendRequest=" + a2);
        if (a2) {
            a(z2, tbsDownloaderCallback);
            instance.setDownloadInterruptCode(-114);
        }
        d.removeMessages(102);
        Message.obtain(d, 102).sendToTarget();
        if (QbSdk.c || !TbsShareManager.isThirdPartyApp(context)) {
            z4 = instance.mPreferences.contains(TbsDownloadConfig.TbsConfigKey.KEY_NEEDDOWNLOAD);
            TbsLog.i(LOGTAG, "[TbsDownloader.needDownload] hasNeedDownloadKey=" + z4);
            z3 = (z4 || TbsShareManager.isThirdPartyApp(context)) ? instance.mPreferences.getBoolean(TbsDownloadConfig.TbsConfigKey.KEY_NEEDDOWNLOAD, false) : true;
        } else {
            z4 = false;
            z3 = false;
        }
        TbsLog.i(LOGTAG, "[TbsDownloader.needDownload]#4,needDownload=" + z3 + ",hasNeedDownloadKey=" + z4);
        if (!z3) {
            int m = am.a().m(c);
            TbsLog.i(LOGTAG, "[TbsDownloader.needDownload]#7,tbsLocalVersion=" + m + ",needSendRequest=" + a2);
            if (a2 || m <= 0) {
                d.removeMessages(103);
                if (m > 0 || a2) {
                    Message.obtain(d, 103, 1, 0, c).sendToTarget();
                } else {
                    Message.obtain(d, 103, 0, 0, c).sendToTarget();
                }
                instance.setDownloadInterruptCode(-121);
                z5 = z3;
            } else {
                instance.setDownloadInterruptCode(-119);
                z5 = z3;
            }
        } else if (!g()) {
            TbsLog.i(LOGTAG, "[TbsDownloader.needDownload]#5,set needDownload = false");
            z5 = false;
        } else {
            instance.setDownloadInterruptCode(-118);
            TbsLog.i(LOGTAG, "[TbsDownloader.needDownload]#6");
            z5 = z3;
        }
        if (!a2 && tbsDownloaderCallback != null) {
            tbsDownloaderCallback.onNeedDownloadFinish(false, 0);
        }
        TbsLog.i(LOGTAG, "[TbsDownloader.needDownload] needDownload=" + z5);
        return z5;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:6:0x0042, code lost:
        r2 = com.tencent.smtt.sdk.TbsDownloadConfig.getInstance(c).mPreferences.getInt(com.tencent.smtt.sdk.TbsDownloadConfig.TbsConfigKey.KEY_DECOUPLECOREVERSION, 0);
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean needDownloadDecoupleCore() {
        /*
            r1 = 1
            r0 = 0
            android.content.Context r2 = c
            boolean r2 = com.tencent.smtt.sdk.TbsShareManager.isThirdPartyApp(r2)
            if (r2 == 0) goto L_0x000b
        L_0x000a:
            return r0
        L_0x000b:
            android.content.Context r2 = c
            com.tencent.smtt.sdk.TbsDownloadConfig r2 = com.tencent.smtt.sdk.TbsDownloadConfig.getInstance(r2)
            android.content.SharedPreferences r2 = r2.mPreferences
            java.lang.String r3 = "tbs_downloaddecouplecore"
            int r2 = r2.getInt(r3, r0)
            if (r2 == r1) goto L_0x000a
            android.content.Context r2 = c
            com.tencent.smtt.sdk.TbsDownloadConfig r2 = com.tencent.smtt.sdk.TbsDownloadConfig.getInstance(r2)
            android.content.SharedPreferences r2 = r2.mPreferences
            java.lang.String r3 = "last_download_decouple_core"
            r4 = 0
            long r2 = r2.getLong(r3, r4)
            long r4 = java.lang.System.currentTimeMillis()
            android.content.Context r6 = c
            com.tencent.smtt.sdk.TbsDownloadConfig r6 = com.tencent.smtt.sdk.TbsDownloadConfig.getInstance(r6)
            long r6 = r6.getRetryInterval()
            long r2 = r4 - r2
            r4 = 1000(0x3e8, double:4.94E-321)
            long r4 = r4 * r6
            int r2 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
            if (r2 < 0) goto L_0x000a
            android.content.Context r2 = c
            com.tencent.smtt.sdk.TbsDownloadConfig r2 = com.tencent.smtt.sdk.TbsDownloadConfig.getInstance(r2)
            android.content.SharedPreferences r2 = r2.mPreferences
            java.lang.String r3 = "tbs_decouplecoreversion"
            int r2 = r2.getInt(r3, r0)
            if (r2 <= 0) goto L_0x000a
            com.tencent.smtt.sdk.am r3 = com.tencent.smtt.sdk.am.a()
            android.content.Context r4 = c
            int r3 = r3.h(r4)
            if (r2 == r3) goto L_0x000a
            android.content.Context r3 = c
            com.tencent.smtt.sdk.TbsDownloadConfig r3 = com.tencent.smtt.sdk.TbsDownloadConfig.getInstance(r3)
            android.content.SharedPreferences r3 = r3.mPreferences
            java.lang.String r4 = "tbs_download_version"
            int r3 = r3.getInt(r4, r0)
            if (r3 == r2) goto L_0x000a
            r0 = r1
            goto L_0x000a
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.smtt.sdk.TbsDownloader.needDownloadDecoupleCore():boolean");
    }

    public static boolean needSendRequest(Context context, boolean z) {
        boolean z2 = true;
        c = context.getApplicationContext();
        TbsLog.initIfNeed(context);
        if (!a(c, z, (TbsDownloaderCallback) null)) {
            return false;
        }
        int m = am.a().m(context);
        TbsLog.i(LOGTAG, "[TbsDownloader.needSendRequest] localTbsVersion=" + m);
        if (m > 0) {
            return false;
        }
        if (a(c, false, true)) {
            return true;
        }
        TbsDownloadConfig instance = TbsDownloadConfig.getInstance(c);
        boolean contains = instance.mPreferences.contains(TbsDownloadConfig.TbsConfigKey.KEY_NEEDDOWNLOAD);
        TbsLog.i(LOGTAG, "[TbsDownloader.needSendRequest] hasNeedDownloadKey=" + contains);
        boolean z3 = !contains ? true : instance.mPreferences.getBoolean(TbsDownloadConfig.TbsConfigKey.KEY_NEEDDOWNLOAD, false);
        TbsLog.i(LOGTAG, "[TbsDownloader.needSendRequest] needDownload=" + z3);
        if (!z3 || !g()) {
            z2 = false;
        }
        TbsLog.i(LOGTAG, "[TbsDownloader.needSendRequest] ret=" + z2);
        return z2;
    }

    public static void setRetryIntervalInSeconds(Context context, long j2) {
        if (context != null) {
            if (context.getApplicationInfo().packageName.equals("com.tencent.qqlive")) {
                l = j2;
            }
            TbsLog.i(LOGTAG, "mRetryIntervalInSeconds is " + l);
        }
    }

    public static boolean startDecoupleCoreIfNeeded() {
        TbsLog.i(LOGTAG, "startDecoupleCoreIfNeeded ");
        if (TbsShareManager.isThirdPartyApp(c)) {
            return false;
        }
        TbsLog.i(LOGTAG, "startDecoupleCoreIfNeeded #1");
        if (TbsDownloadConfig.getInstance(c).mPreferences.getInt(TbsDownloadConfig.TbsConfigKey.KEY_DOWNLOADDECOUPLECORE, 0) == 1) {
            return false;
        }
        TbsLog.i(LOGTAG, "startDecoupleCoreIfNeeded #2");
        long j2 = TbsDownloadConfig.getInstance(c).mPreferences.getLong(TbsDownloadConfig.TbsConfigKey.KEY_LAST_DOWNLOAD_DECOUPLE_CORE, 0);
        if (System.currentTimeMillis() - j2 < 1000 * TbsDownloadConfig.getInstance(c).getRetryInterval()) {
            return false;
        }
        TbsLog.i(LOGTAG, "startDecoupleCoreIfNeeded #3");
        int i2 = TbsDownloadConfig.getInstance(c).mPreferences.getInt(TbsDownloadConfig.TbsConfigKey.KEY_DECOUPLECOREVERSION, 0);
        if (i2 <= 0 || i2 == am.a().h(c)) {
            TbsLog.i(LOGTAG, "startDecoupleCoreIfNeeded no need, deCoupleCoreVersion is " + i2 + " getTbsCoreShareDecoupleCoreVersion is " + am.a().h(c));
            return false;
        } else if (TbsDownloadConfig.getInstance(c).mPreferences.getInt(TbsDownloadConfig.TbsConfigKey.KEY_TBS_DOWNLOAD_V, 0) != i2 || TbsDownloadConfig.getInstance(c).mPreferences.getInt(TbsDownloadConfig.TbsConfigKey.KEY_TBS_DOWNLOAD_V_TYPE, 0) == 1) {
            TbsLog.i(LOGTAG, "startDecoupleCoreIfNeeded #4");
            a = true;
            d.removeMessages(108);
            Message obtain = Message.obtain(d, 108, QbSdk.l);
            obtain.arg1 = 0;
            obtain.sendToTarget();
            TbsDownloadConfig.getInstance(c).a.put(TbsDownloadConfig.TbsConfigKey.KEY_LAST_DOWNLOAD_DECOUPLE_CORE, Long.valueOf(System.currentTimeMillis()));
            return true;
        } else {
            TbsLog.i(LOGTAG, "startDecoupleCoreIfNeeded no need, KEY_TBS_DOWNLOAD_V is " + TbsDownloadConfig.getInstance(c).mPreferences.getInt(TbsDownloadConfig.TbsConfigKey.KEY_TBS_DOWNLOAD_V, 0) + " deCoupleCoreVersion is " + i2 + " KEY_TBS_DOWNLOAD_V_TYPE is " + TbsDownloadConfig.getInstance(c).mPreferences.getInt(TbsDownloadConfig.TbsConfigKey.KEY_TBS_DOWNLOAD_V_TYPE, 0));
            return false;
        }
    }

    public static void startDownload(Context context) {
        startDownload(context, false);
    }

    public static synchronized void startDownload(Context context, boolean z) {
        int i2 = 1;
        synchronized (TbsDownloader.class) {
            TbsLog.i(LOGTAG, "[TbsDownloader.startDownload] sAppContext=" + c);
            if (!am.b) {
                a = true;
                c = context.getApplicationContext();
                TbsDownloadConfig.getInstance(c).setDownloadInterruptCode(-200);
                if (Build.VERSION.SDK_INT < 8) {
                    QbSdk.l.onDownloadFinish(110);
                    TbsDownloadConfig.getInstance(c).setDownloadInterruptCode(-201);
                } else {
                    d();
                    if (i) {
                        QbSdk.l.onDownloadFinish(TbsListener.ErrorCode.THREAD_INIT_ERROR);
                        TbsDownloadConfig.getInstance(c).setDownloadInterruptCode(-202);
                    } else {
                        if (z) {
                            stopDownload();
                        }
                        d.removeMessages(101);
                        d.removeMessages(100);
                        Message obtain = Message.obtain(d, 101, QbSdk.l);
                        if (!z) {
                            i2 = 0;
                        }
                        obtain.arg1 = i2;
                        obtain.sendToTarget();
                    }
                }
            }
        }
    }

    public static void stopDownload() {
        if (!i) {
            TbsLog.i(LOGTAG, "[TbsDownloader.stopDownload]");
            if (g != null) {
                g.c();
            }
            if (d != null) {
                d.removeMessages(100);
                d.removeMessages(101);
                d.removeMessages(108);
            }
        }
    }
}
