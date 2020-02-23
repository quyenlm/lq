package ngame.support;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.util.Log;
import java.io.File;

public class ApkUtil {
    private static final long NO_VALUE = -1;
    private static final String PREFS_FILE = "NGame.MultiDex.Preferences";
    private static final String PREFS_KEY_APK_CRC = "apk_crc";
    private static final String PREFS_KEY_APK_TIMESTAMP = "apk_timestamp";
    private static final String TAG = ApkUtil.class.getSimpleName();

    public static boolean isApkModified(Context context) {
        boolean result = false;
        SharedPreferences prefs = context.getSharedPreferences(PREFS_FILE, 0);
        long saved_apk_timestamp = prefs.getLong(PREFS_KEY_APK_TIMESTAMP, -1);
        long saved_apk_crc = prefs.getLong(PREFS_KEY_APK_CRC, -1);
        long cur_apk_timestamp = getApkTimeStamp(context);
        long cur_apk_crc = getApkCrc(context);
        if (!(saved_apk_timestamp == cur_apk_timestamp && saved_apk_crc == cur_apk_crc)) {
            result = true;
        }
        Log.d(TAG, "isApkModified:" + result);
        return result;
    }

    public static void saveApkSignature(Context context) {
        SharedPreferences.Editor editor = context.getSharedPreferences(PREFS_FILE, 0).edit();
        long apk_timestamp = getApkTimeStamp(context);
        long apk_crc = getApkCrc(context);
        editor.putLong(PREFS_KEY_APK_TIMESTAMP, apk_timestamp);
        editor.putLong(PREFS_KEY_APK_CRC, apk_crc);
        editor.commit();
    }

    private static long getApkCrc(Context context) {
        ApplicationInfo applicationInfo = getApplicationInfo(context);
        if (applicationInfo != null) {
            return getZipCrc(new File(applicationInfo.sourceDir));
        }
        Log.w(TAG, "getApkCrc, applicationInfo null");
        return -1;
    }

    private static long getApkTimeStamp(Context context) {
        ApplicationInfo applicationInfo = getApplicationInfo(context);
        if (applicationInfo != null) {
            return getTimeStamp(new File(applicationInfo.sourceDir));
        }
        Log.w(TAG, "getApkCrc, applicationInfo null");
        return -1;
    }

    private static long getTimeStamp(File archive) {
        long timeStamp = archive.lastModified();
        if (timeStamp == -1) {
            return timeStamp - 1;
        }
        return timeStamp;
    }

    private static long getZipCrc(File archive) {
        long computedValue = -1;
        try {
            computedValue = ZipUtil.getZipCrc(archive);
        } catch (Throwable e) {
            Log.d(TAG, "getZipCrc exception:" + Log.getStackTraceString(e));
        }
        if (computedValue == -1) {
            return computedValue - 1;
        }
        return computedValue;
    }

    private static ApplicationInfo getApplicationInfo(Context context) {
        try {
            PackageManager pm = context.getPackageManager();
            String packageName = context.getPackageName();
            if (pm == null || packageName == null) {
                return null;
            }
            try {
                return pm.getApplicationInfo(packageName, 128);
            } catch (Throwable e) {
                Log.d(TAG, "getApplicationInfo exception:" + Log.getStackTraceString(e));
                return null;
            }
        } catch (RuntimeException e2) {
            Log.w(TAG, "Failure while trying to obtain ApplicationInfo from Context. Must be running in test mode. Skip patching.", e2);
            return null;
        }
    }
}
