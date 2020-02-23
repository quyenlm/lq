package com.beetalk.sdk.helper;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.text.TextUtils;
import com.beetalk.sdk.GGLoginSession;
import com.beetalk.sdk.GGPlatform;
import com.beetalk.sdk.SDKConstants;
import com.garena.pay.android.GGErrorCode;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;
import org.json.JSONObject;

public class Helper {
    private static final String APPLICATION_ID = "com.garena.sdk.push.applicationId";
    private static Integer source;
    private static Boolean thirdParty;

    public static String getMetaDataAppId(Context context) {
        Integer appId = getIntegerAppId(context);
        if (appId.intValue() != -1) {
            return appId.toString();
        }
        return null;
    }

    public static Integer getIntegerAppId(Context context) {
        try {
            ApplicationInfo info = context.getPackageManager().getApplicationInfo(context.getPackageName(), 128);
            if (info.metaData != null) {
                return Integer.valueOf(info.metaData.getInt(SDKConstants.APPLICATION_ID_PROPERTY));
            }
        } catch (Exception e) {
            BBLogger.e(e);
        }
        return -1;
    }

    public static Integer getMetaDataSourceId(Context context) {
        if (source != null) {
            return source;
        }
        try {
            ApplicationInfo info = context.getPackageManager().getApplicationInfo(context.getPackageName(), 128);
            if (info.metaData != null) {
                source = Integer.valueOf(info.metaData.getInt(SDKConstants.APPLICATION_SOURCE_PROPERTY, SDKConstants.CHANNEL_SOURCE.GOOGLE_PLAY.intValue()));
            }
        } catch (Exception e) {
            source = SDKConstants.CHANNEL_SOURCE.GOOGLE_PLAY;
        }
        return source;
    }

    public static String getSignatureFingerprint(Context context) {
        Signature[] signatures;
        try {
            PackageInfo callingPackageInfo = context.getPackageManager().getPackageInfo(context.getApplicationContext().getPackageName(), 64);
            if (callingPackageInfo == null || (signatures = callingPackageInfo.signatures) == null || signatures.length <= 0) {
                return null;
            }
            try {
                byte[] fingerprint = MessageDigest.getInstance("SHA1").digest(signatures[0].toByteArray());
                StringBuffer hexString = new StringBuffer();
                for (byte b : fingerprint) {
                    String appendString = Integer.toHexString(b & 255);
                    if (hexString.length() > 0) {
                        hexString.append(":");
                    }
                    if (appendString.length() == 1) {
                        hexString.append("0");
                    }
                    hexString.append(appendString);
                }
                return hexString.toString().toUpperCase();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
                return null;
            }
        } catch (PackageManager.NameNotFoundException e2) {
            e2.printStackTrace();
            return null;
        }
    }

    public static boolean isNullOrEmpty(String aString) {
        return aString == null || aString.length() == 0;
    }

    public static String getMetadataFBAppId(Context context) {
        Validate.notNull(context, "context");
        try {
            ApplicationInfo ai = context.getPackageManager().getApplicationInfo(context.getPackageName(), 128);
            if (ai.metaData != null) {
                return ai.metaData.getString("com.facebook.sdk.ApplicationId");
            }
        } catch (PackageManager.NameNotFoundException e) {
        }
        return null;
    }

    public static boolean isPackageInstalled(String packagename, Context context) {
        try {
            ApplicationInfo info = context.getPackageManager().getApplicationInfo(packagename, 0);
            if (info == null || !info.enabled) {
                return false;
            }
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    public static String getPackageVersion(Context context, String packageName) {
        try {
            PackageManager manager = context.getPackageManager();
            if (manager == null) {
                return "";
            }
            return manager.getPackageInfo(packageName, 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            return "";
        }
    }

    public static int getPackageVersionCode(Context context, String packageName) {
        try {
            PackageManager manager = context.getPackageManager();
            if (manager == null) {
                return 0;
            }
            return manager.getPackageInfo(packageName, 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            return 0;
        }
    }

    public static int getTimeNow() {
        return (int) (System.currentTimeMillis() / 1000);
    }

    public static Boolean getMetaDataThirdParty(Context context) {
        if (thirdParty != null) {
            return thirdParty;
        }
        try {
            ApplicationInfo info = context.getPackageManager().getApplicationInfo(context.getPackageName(), 128);
            if (info.metaData != null) {
                thirdParty = Boolean.valueOf(info.metaData.getBoolean(SDKConstants.APPLICATION_PAYMENT_EXTERNAL, true));
            }
        } catch (Exception e) {
            thirdParty = true;
        }
        return thirdParty;
    }

    public static String generateNonce() {
        String randString = UUID.randomUUID().toString().replace("-", "");
        if (randString.length() > 32) {
            return randString.substring(0, 32);
        }
        return randString;
    }

    public static String getPushAppId(Context context) {
        try {
            ApplicationInfo info = context.getPackageManager().getApplicationInfo(context.getPackageName(), 128);
            if (info.metaData != null) {
                return Integer.valueOf(info.metaData.getInt("com.garena.sdk.push.applicationId")).toString();
            }
            return "";
        } catch (Exception e) {
            return "";
        }
    }

    public static boolean isSupportGasShare(Activity activity) {
        return GGPlatform.GGPlatformInstalledVersionCode(activity, 1) >= 60;
    }

    public static boolean isSupportGasBigImageShare(Activity activity) {
        return GGPlatform.GGPlatformInstalledVersionCode(activity, 1) >= 103;
    }

    public static boolean isHasSendSMSPermission(Context context) {
        if (context == null || context.checkCallingOrSelfPermission("android.permission.SEND_SMS") != 0) {
            return false;
        }
        return true;
    }

    public static void openInGooglePlay(Context context, String packageName) {
        try {
            context.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=" + packageName)));
        } catch (ActivityNotFoundException e) {
            context.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://play.google.com/store/apps/details?id=" + packageName)));
        }
    }

    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm;
        NetworkInfo info;
        if (context == null || (cm = (ConnectivityManager) context.getSystemService("connectivity")) == null || (info = cm.getActiveNetworkInfo()) == null || !info.isConnected()) {
            return false;
        }
        return true;
    }

    public static GGErrorCode getErrorCode(String response) {
        if (TextUtils.isEmpty(response)) {
            return GGErrorCode.NETWORK_EXCEPTION;
        }
        if (!response.contains("error")) {
            return null;
        }
        if (response.contains("error_params")) {
            return GGErrorCode.ERROR_IN_PARAMS;
        }
        if (response.contains(SDKConstants.SERVER_ERRORS.ERROR_SCOPE)) {
            return GGErrorCode.GOP_ERROR_SCOPE;
        }
        if (response.contains("server_error")) {
            return GGErrorCode.GOP_ERROR_SERVER;
        }
        if (!response.contains(SDKConstants.SERVER_ERRORS.ERROR_TOKEN)) {
            return GGErrorCode.UNKNOWN_ERROR;
        }
        GGLoginSession.removeInvalidToken();
        return GGErrorCode.GOP_ERROR_TOKEN;
    }

    public static GGErrorCode getErrorCode(JSONObject response) {
        if (response == null) {
            return GGErrorCode.NETWORK_EXCEPTION;
        }
        String error = response.optString("error");
        if (TextUtils.isEmpty(error)) {
            return null;
        }
        if (error.equals("error_params")) {
            return GGErrorCode.ERROR_IN_PARAMS;
        }
        if (error.equals(SDKConstants.SERVER_ERRORS.ERROR_SCOPE)) {
            return GGErrorCode.GOP_ERROR_SCOPE;
        }
        if (error.equals("server_error")) {
            return GGErrorCode.GOP_ERROR_SERVER;
        }
        if (!error.equals(SDKConstants.SERVER_ERRORS.ERROR_TOKEN)) {
            return GGErrorCode.UNKNOWN_ERROR;
        }
        GGLoginSession.removeInvalidToken();
        return GGErrorCode.GOP_ERROR_TOKEN;
    }
}
