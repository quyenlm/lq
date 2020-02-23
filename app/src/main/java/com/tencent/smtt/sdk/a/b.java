package com.tencent.smtt.sdk.a;

import MTT.ThirdAppInfoNew;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.text.TextUtils;
import com.tencent.smtt.sdk.QbSdk;
import com.tencent.smtt.sdk.TbsConfig;
import com.tencent.smtt.sdk.TbsCoreLoadStat;
import com.tencent.smtt.sdk.TbsDownloadConfig;
import com.tencent.smtt.sdk.TbsDownloader;
import com.tencent.smtt.sdk.TbsShareManager;
import com.tencent.smtt.utils.TbsLog;
import com.tencent.smtt.utils.q;
import com.tencent.smtt.utils.w;
import com.tencent.smtt.utils.x;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;
import org.json.JSONObject;

public class b {
    public static byte[] a;

    static {
        a = null;
        try {
            a = "65dRa93L".getBytes("utf-8");
        } catch (UnsupportedEncodingException e) {
        }
    }

    private static String a(Context context) {
        try {
            byte[] byteArray = context.getPackageManager().getPackageInfo(context.getPackageName(), 64).signatures[0].toByteArray();
            if (byteArray == null) {
                return null;
            }
            MessageDigest instance = MessageDigest.getInstance("SHA-1");
            instance.update(byteArray);
            byte[] digest = instance.digest();
            if (digest == null) {
                return null;
            }
            StringBuilder sb = new StringBuilder("");
            if (digest == null || digest.length <= 0) {
                return null;
            }
            for (int i = 0; i < digest.length; i++) {
                String upperCase = Integer.toHexString(digest[i] & 255).toUpperCase();
                if (i > 0) {
                    sb.append(":");
                }
                if (upperCase.length() < 2) {
                    sb.append(0);
                }
                sb.append(upperCase);
            }
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void a(ThirdAppInfoNew thirdAppInfoNew, Context context) {
        new c("HttpUtils", context, thirdAppInfoNew).start();
    }

    public static void a(Context context, String str, String str2, String str3, int i, boolean z, long j) {
        String str4;
        if (QbSdk.getSettings() == null || !QbSdk.getSettings().containsKey(QbSdk.KEY_SET_SENDREQUEST_AND_UPLOAD) || !QbSdk.getSettings().get(QbSdk.KEY_SET_SENDREQUEST_AND_UPLOAD).equals("false")) {
            String str5 = "";
            try {
                ApplicationInfo applicationInfo = context.getApplicationInfo();
                if (TbsConfig.APP_QQ.equals(applicationInfo.packageName)) {
                    str5 = context.getPackageManager().getPackageInfo(applicationInfo.packageName, 0).versionName;
                    if (!TextUtils.isEmpty(QbSdk.getQQBuildNumber())) {
                        str5 = str5 + "." + QbSdk.getQQBuildNumber();
                    }
                }
                str4 = str5;
            } catch (Exception e) {
                e.printStackTrace();
                str4 = str5;
            }
            try {
                ThirdAppInfoNew thirdAppInfoNew = new ThirdAppInfoNew();
                thirdAppInfoNew.sAppName = context.getApplicationContext().getApplicationInfo().packageName;
                x.a(context);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT+08"));
                thirdAppInfoNew.sTime = simpleDateFormat.format(Calendar.getInstance().getTime());
                thirdAppInfoNew.sGuid = str;
                if (z) {
                    thirdAppInfoNew.sQua2 = str2;
                } else {
                    thirdAppInfoNew.sQua2 = w.a(context);
                }
                thirdAppInfoNew.sLc = str3;
                String e2 = com.tencent.smtt.utils.b.e(context);
                String c = com.tencent.smtt.utils.b.c(context);
                String d = com.tencent.smtt.utils.b.d(context);
                String f = com.tencent.smtt.utils.b.f(context);
                if (c != null && !"".equals(c)) {
                    thirdAppInfoNew.sImei = c;
                }
                if (d != null && !"".equals(d)) {
                    thirdAppInfoNew.sImsi = d;
                }
                if (!TextUtils.isEmpty(f)) {
                    thirdAppInfoNew.sAndroidID = f;
                }
                if (e2 != null && !"".equals(e2)) {
                    thirdAppInfoNew.sMac = e2;
                }
                thirdAppInfoNew.iPv = (long) i;
                if (!TbsShareManager.isThirdPartyApp(context)) {
                    thirdAppInfoNew.iCoreType = z ? 1 : 0;
                } else if (!z) {
                    thirdAppInfoNew.iCoreType = 0;
                } else if (TbsShareManager.getCoreFormOwn()) {
                    thirdAppInfoNew.iCoreType = 2;
                } else {
                    thirdAppInfoNew.iCoreType = 1;
                }
                thirdAppInfoNew.sAppVersionName = str4;
                thirdAppInfoNew.sAppSignature = a(context);
                if (!z) {
                    thirdAppInfoNew.sWifiConnectedTime = j;
                    thirdAppInfoNew.localCoreVersion = QbSdk.getTbsVersion(context);
                }
                a(thirdAppInfoNew, context.getApplicationContext());
            } catch (Throwable th) {
                th.printStackTrace();
            }
        } else {
            TbsLog.i("sdkreport", "[HttpUtils.doReport] -- SET_SENDREQUEST_AND_UPLOAD is false");
        }
    }

    /* access modifiers changed from: private */
    public static JSONObject c(ThirdAppInfoNew thirdAppInfoNew, Context context) {
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("APPNAME", thirdAppInfoNew.sAppName);
            jSONObject.put("TIME", thirdAppInfoNew.sTime);
            jSONObject.put("QUA2", thirdAppInfoNew.sQua2);
            jSONObject.put("LC", thirdAppInfoNew.sLc);
            jSONObject.put("GUID", thirdAppInfoNew.sGuid);
            jSONObject.put("IMEI", thirdAppInfoNew.sImei);
            jSONObject.put("IMSI", thirdAppInfoNew.sImsi);
            jSONObject.put("MAC", thirdAppInfoNew.sMac);
            jSONObject.put("PV", thirdAppInfoNew.iPv);
            jSONObject.put("CORETYPE", thirdAppInfoNew.iCoreType);
            jSONObject.put("APPVN", thirdAppInfoNew.sAppVersionName);
            if (thirdAppInfoNew.sAppSignature == null) {
                jSONObject.put("SIGNATURE", "0");
            } else {
                jSONObject.put("SIGNATURE", thirdAppInfoNew.sAppSignature);
            }
            jSONObject.put("PROTOCOL_VERSION", 3);
            jSONObject.put("ANDROID_ID", thirdAppInfoNew.sAndroidID);
            if (TbsShareManager.isThirdPartyApp(context)) {
                jSONObject.put("HOST_COREVERSION", TbsShareManager.getHostCoreVersions(context));
            } else {
                jSONObject.put("HOST_COREVERSION", TbsDownloader.getCoreShareDecoupleCoreVersionByContext(context));
                jSONObject.put("DECOUPLE_COREVERSION", TbsDownloader.getCoreShareDecoupleCoreVersionByContext(context));
            }
            if (thirdAppInfoNew.iCoreType == 0) {
                jSONObject.put("WIFICONNECTEDTIME", thirdAppInfoNew.sWifiConnectedTime);
                jSONObject.put("CORE_EXIST", thirdAppInfoNew.localCoreVersion);
                int i = TbsCoreLoadStat.mLoadErrorCode;
                if (thirdAppInfoNew.localCoreVersion <= 0) {
                    jSONObject.put("TBS_ERROR_CODE", TbsDownloadConfig.getInstance(context).getDownloadInterruptCode());
                } else {
                    jSONObject.put("TBS_ERROR_CODE", i);
                }
                if (i == -1) {
                    TbsLog.e("sdkreport", "ATTENTION: Load errorCode missed!");
                }
            }
            try {
                if (QbSdk.getTID() == null) {
                    return jSONObject;
                }
                if (thirdAppInfoNew.sAppName.equals(TbsConfig.APP_QQ)) {
                    jSONObject.put("TID", q.a().a(QbSdk.getTID()));
                    jSONObject.put("TIDTYPE", 1);
                    return jSONObject;
                } else if (!thirdAppInfoNew.sAppName.equals(TbsConfig.APP_WX)) {
                    return jSONObject;
                } else {
                    jSONObject.put("TID", QbSdk.getTID());
                    jSONObject.put("TIDTYPE", 0);
                    return jSONObject;
                }
            } catch (Exception e) {
                return jSONObject;
            }
        } catch (Exception e2) {
            TbsLog.e("sdkreport", "getPostData exception!");
            return null;
        }
    }
}
