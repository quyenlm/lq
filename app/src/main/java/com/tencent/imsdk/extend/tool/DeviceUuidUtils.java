package com.tencent.imsdk.extend.tool;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import com.amazonaws.services.s3.internal.Constants;
import com.tencent.imsdk.tool.etc.IMLogger;
import com.tencent.imsdk.tool.etc.MD5;

public class DeviceUuidUtils {
    private static final String PREFS_COMBINE_UNIQUE_ID = "uuid";
    private static final String PREFS_FILE = "device_id.xml";

    public static String getDeviceUuid(Context context) {
        String uuid = null;
        if (0 == 0) {
            synchronized (DeviceUuidUtils.class) {
                uuid = getPrefsDeviceId(context);
                if (uuid == null) {
                    String androidId = Settings.Secure.getString(context.getContentResolver(), "android_id");
                    String seriesId = Build.SERIAL;
                    String deviceId = null;
                    try {
                        deviceId = ((TelephonyManager) context.getSystemService("phone")).getDeviceId();
                    } catch (SecurityException e) {
                        IMLogger.e("Current process need android.permission.READ_PHONE_STATE");
                    }
                    String macSeries = getMac(context);
                    IMLogger.d("androidId : " + androidId + ", seriesId : " + seriesId + ", deviceId : " + deviceId + ", mac :" + macSeries);
                    uuid = md5Encrypt(androidId + seriesId + deviceId + macSeries);
                    IMLogger.d("Current uuid = " + uuid);
                }
            }
        }
        return uuid;
    }

    public static String getPrefsDeviceId(Context context) {
        return context.getSharedPreferences(PREFS_FILE, 0).getString(PREFS_COMBINE_UNIQUE_ID, (String) null);
    }

    private static String getMac(Context context) {
        try {
            WifiInfo wifiInfo = ((WifiManager) context.getSystemService("wifi")).getConnectionInfo();
            if (wifiInfo.getMacAddress() != null) {
                return wifiInfo.getMacAddress();
            }
            return Constants.NULL_VERSION_ID;
        } catch (SecurityException ex) {
            ex.printStackTrace();
            IMLogger.w("Need ACCESS_WIFI_STATE permission");
            return "";
        }
    }

    private static String md5Encrypt(String unencryptedString) {
        if (unencryptedString == null) {
            return null;
        }
        return MD5.getMD5(unencryptedString);
    }
}
