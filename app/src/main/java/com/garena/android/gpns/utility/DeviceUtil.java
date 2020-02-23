package com.garena.android.gpns.utility;

import android.app.ActivityManager;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import com.garena.android.gpns.GNotificationService;
import com.garena.android.gpns.storage.LocalStorage;
import com.tencent.tp.r;
import java.util.UUID;

public final class DeviceUtil {
    public static boolean isConnectedToNetwork(Context context) {
        boolean isConnected = true;
        ConnectivityManager connectManager = (ConnectivityManager) context.getSystemService("connectivity");
        NetworkInfo mobile = connectManager.getNetworkInfo(0);
        NetworkInfo wifi = connectManager.getNetworkInfo(1);
        if ((mobile == null || mobile.getState() != NetworkInfo.State.CONNECTED) && (wifi == null || wifi.getState() != NetworkInfo.State.CONNECTED)) {
            isConnected = false;
        }
        AppLogger.d("Connected To Network: " + isConnected);
        return isConnected;
    }

    public static boolean isServiceRunning(Context context) {
        for (ActivityManager.RunningServiceInfo service : ((ActivityManager) context.getSystemService("activity")).getRunningServices(Integer.MAX_VALUE)) {
            if (GNotificationService.class.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    private DeviceUtil() {
    }

    public static long getDeviceId(Context context) {
        try {
            return LocalStorage.getDeviceId(context);
        } catch (NumberFormatException e) {
            AppLogger.e((Throwable) e);
            return -1;
        }
    }

    public static long generateDeviceId(Context context) {
        String rawDeviceId;
        long androidIdHash;
        long deviceIdHash;
        String deviceId = LocalStorage.getDeviceIdString(context);
        if (!TextUtils.isEmpty(deviceId)) {
            try {
                long generatedDeviceId = Long.parseLong(deviceId);
                long j = generatedDeviceId;
                return generatedDeviceId;
            } catch (NumberFormatException e) {
                AppLogger.e((Throwable) e);
            }
        }
        String androidId = Settings.Secure.getString(context.getContentResolver(), "android_id");
        if (isHasReadPhoneStatePermission(context)) {
            rawDeviceId = ((TelephonyManager) context.getSystemService("phone")).getDeviceId();
        } else {
            rawDeviceId = "";
        }
        if (TextUtils.isEmpty(androidId)) {
            androidIdHash = (long) UUID.randomUUID().hashCode();
        } else {
            androidIdHash = hash(androidId);
        }
        if (TextUtils.isEmpty(rawDeviceId)) {
            deviceIdHash = (long) UUID.randomUUID().hashCode();
        } else {
            deviceIdHash = hash(rawDeviceId);
        }
        long generatedDeviceId2 = Math.abs((31 * androidIdHash) + deviceIdHash);
        LocalStorage.setDeviceId(context, generatedDeviceId2);
        long j2 = generatedDeviceId2;
        return generatedDeviceId2;
    }

    private static long hash(String string) {
        long h = 1125899906842573L;
        int len = string.length();
        for (int i = 0; i < len; i++) {
            h = (31 * h) + ((long) string.charAt(i));
        }
        return h;
    }

    private static boolean isHasReadPhoneStatePermission(Context context) {
        return context.checkCallingOrSelfPermission(r.a) == 0;
    }
}
