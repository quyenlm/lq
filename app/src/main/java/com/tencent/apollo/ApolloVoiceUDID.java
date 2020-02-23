package com.tencent.apollo;

import android.content.Context;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;
import android.util.Log;
import com.facebook.internal.AnalyticsEvents;
import com.tencent.midas.oversea.api.UnityPayHelper;
import com.tencent.tp.r;
import java.net.NetworkInterface;
import java.security.MessageDigest;
import java.util.Locale;

public class ApolloVoiceUDID {
    private static final String DefaultUUID = "UUID";
    private static String LOGTAG = "ApolloVoice";
    private static Context mainContext;

    public static void SetContext(Context ctxt) {
        mainContext = ctxt;
    }

    public static String GetDeviceID(Context context) {
        if (ContextCompat.checkSelfPermission(context, r.a) != 0) {
            Log.e(LOGTAG, "getDeviceID, Permission Denied. ");
            return "";
        }
        try {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
            if (telephonyManager == null) {
                return "";
            }
            return telephonyManager.getDeviceId();
        } catch (Exception e) {
            Log.e(LOGTAG, "get DeviceID failed: " + e.toString());
            return "";
        }
    }

    public static String DeviceID() {
        return GetDeviceID(mainContext);
    }

    public static String UDID() {
        String deviceId = GetDeviceID(mainContext);
        String serial = Build.SERIAL;
        String androidId = Settings.Secure.getString(mainContext.getContentResolver(), "android_id");
        StringBuilder builder = new StringBuilder();
        if (deviceId != null) {
            builder.append("%");
            builder.append(deviceId);
        }
        if (serial != null) {
            builder.append("%");
            builder.append(serial);
        }
        if (androidId != null) {
            builder.append("%");
            builder.append(androidId);
        }
        String uuid = builder.toString();
        if (uuid.length() == 0) {
            return DefaultUUID;
        }
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            if (digest == null) {
                return DefaultUUID;
            }
            digest.update(uuid.getBytes());
            byte[] hash = digest.digest();
            StringBuilder hex = new StringBuilder();
            for (byte b : hash) {
                hex.append(Integer.toHexString(b & 255));
            }
            return hex.toString().toUpperCase(Locale.ENGLISH);
        } catch (Exception e) {
            Log.e(LOGTAG, "GetUUID Exception:" + e);
            return DefaultUUID;
        }
    }

    public static String MacAddress() {
        String macAddr = null;
        StringBuffer buf = new StringBuffer();
        try {
            NetworkInterface networkInterface = NetworkInterface.getByName("eth1");
            if (networkInterface == null) {
                Log.w(LOGTAG, "networkInterface eth1 is null");
                networkInterface = NetworkInterface.getByName("wlan0");
            }
            if (networkInterface != null) {
                byte[] addr = networkInterface.getHardwareAddress();
                int length = addr.length;
                for (int i = 0; i < length; i++) {
                    buf.append(String.format("%02X:", new Object[]{Byte.valueOf(addr[i])}));
                }
                if (buf.length() > 0) {
                    buf.deleteCharAt(buf.length() - 1);
                }
                macAddr = buf.toString();
            }
        } catch (Exception e) {
            Log.e(LOGTAG, "GetMacAdress Exception:" + e);
        }
        if (macAddr == null) {
            return AnalyticsEvents.PARAMETER_DIALOG_OUTCOME_VALUE_UNKNOWN;
        }
        return macAddr;
    }

    public static String Model() {
        String model = Build.MODEL;
        if (model == null) {
            return AnalyticsEvents.PARAMETER_DIALOG_OUTCOME_VALUE_UNKNOWN;
        }
        return model;
    }

    public static String Brand() {
        String brand = Build.BRAND;
        if (brand == null) {
            return AnalyticsEvents.PARAMETER_DIALOG_OUTCOME_VALUE_UNKNOWN;
        }
        return brand;
    }

    public static String OSVersion() {
        String sysVersion = Build.VERSION.RELEASE;
        if (sysVersion == null) {
            return AnalyticsEvents.PARAMETER_DIALOG_OUTCOME_VALUE_UNKNOWN;
        }
        return sysVersion;
    }

    public static String AppVersion() {
        String appVersion = null;
        try {
            appVersion = mainContext.getPackageManager().getPackageInfo(mainContext.getPackageName(), 1).versionName;
        } catch (Exception e) {
            Log.e(LOGTAG, "GetAppVersion Exception:" + e);
        }
        if (appVersion == null) {
            return AnalyticsEvents.PARAMETER_DIALOG_OUTCOME_VALUE_UNKNOWN;
        }
        return appVersion;
    }

    public static String SimOperator() {
        TelephonyManager tm = (TelephonyManager) mainContext.getSystemService("phone");
        if (tm == null || 5 != tm.getSimState()) {
            return UnityPayHelper.AP_MIDAS_RESP_RESULT_ERROR;
        }
        return tm.getSimOperator();
    }
}
