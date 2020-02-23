package com.tencent.ieg.ntv.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Process;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import com.tencent.ieg.ntv.model.NTVDefine;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.math.BigInteger;
import java.net.InetAddress;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Util {
    /* access modifiers changed from: private */
    public static final String TAG = Util.class.getSimpleName();
    public static final int VIVO_FLAG_NOTCH = 32;
    private static long mCurrentLocalMSeconds;
    private static long mCurrentServerMSeconds;
    private static Context sContext;
    private static Map<String, String> sMConfig;

    public interface DNSListener {
        void onComplete(String str);
    }

    public enum NetworkType {
        UNKNOWN,
        NONE,
        WIFI,
        MOBILE_4G,
        MOBILE_3G,
        MOBILE_2G
    }

    public static void setContext(Context context) {
        sContext = context;
    }

    public static Context getContext() {
        return sContext;
    }

    public static String getString(String name) {
        int resId = getResId(name, "string");
        if (resId == 0) {
            return null;
        }
        return sContext.getString(resId);
    }

    private static int getResId(String name, String type) {
        return getResId(name, type, sContext.getPackageName());
    }

    private static int getResId(String name, String type, String packageName) {
        return sContext.getResources().getIdentifier(name, type, packageName);
    }

    public static String getMConfig(String key) {
        if (sMConfig != null && sMConfig.containsKey(key)) {
            return sMConfig.get(key);
        }
        Logger.d(TAG, "getMConfig not found, key:" + key);
        return "";
    }

    public static void setMConfig(String key, String value) {
        if (sMConfig == null) {
            sMConfig = new HashMap();
        }
        Logger.d(TAG, "setMConfig key:" + key + ", value:" + value);
        sMConfig.put(key, value);
    }

    public static String getMeta(String key) {
        try {
            ApplicationInfo info = sContext.getPackageManager().getApplicationInfo(sContext.getPackageName(), 128);
            if (info != null) {
                Object value = info.metaData.get(key);
                String result = "";
                if (value != null) {
                    result = value.toString();
                }
                Logger.d(TAG, "getMeta key:" + key + ", result:" + result);
                return result;
            }
            Logger.w(TAG, "ApplicationInfo null");
            return "";
        } catch (Exception e) {
            Logger.w(TAG, "getMeta exception");
            Logger.w(TAG, e);
        }
    }

    public static String getPackageName() {
        return sContext.getPackageName();
    }

    public static byte[] getBytes(InputStream ins) {
        try {
            if (ins instanceof ByteArrayInputStream) {
                int size = ins.available();
                byte[] buf = new byte[size];
                ins.read(buf, 0, size);
                return buf;
            }
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] buf2 = new byte[1024];
            while (true) {
                int len = ins.read(buf2, 0, 1024);
                if (len == -1) {
                    return bos.toByteArray();
                }
                bos.write(buf2, 0, len);
            }
        } catch (Exception e) {
            Logger.w(TAG, e);
            return null;
        }
    }

    public static void killProcessByName(String name) {
        for (ActivityManager.RunningAppProcessInfo processInfo : ((ActivityManager) sContext.getSystemService("activity")).getRunningAppProcesses()) {
            if (processInfo.processName.equals(name) && !AndroidRomUtil.autoKillMultiProcess()) {
                Logger.d(TAG, "killProcessByName:" + processInfo.processName);
                Process.killProcess(processInfo.pid);
            }
        }
    }

    public static String getMd5(String str) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(str.getBytes());
        return new BigInteger(1, md.digest()).toString(16);
    }

    public static String getDateString(long secs) {
        return new SimpleDateFormat("yyyy-MM-dd E").format(new Date(1000 * secs));
    }

    public static String getTimeString(long secs) {
        return new SimpleDateFormat("HH:mm").format(new Date(1000 * secs));
    }

    public static String getEndTimeString(long secs) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(1000 * secs));
    }

    public static void setServerTimestamp(int seconds) {
        mCurrentServerMSeconds = ((long) seconds) * 1000;
        mCurrentLocalMSeconds = System.currentTimeMillis();
        Logger.d(TAG, "setServerTimestamp : mCurrentServerMSeconds=" + mCurrentServerMSeconds + ", mCurrentLocalMSeconds=" + mCurrentLocalMSeconds);
    }

    public static long getCurrentMSeconds() {
        return mCurrentServerMSeconds + (System.currentTimeMillis() - mCurrentLocalMSeconds);
    }

    public static void requestPermission(Activity context, String[] permissions, int requestCode) {
        ActivityCompat.requestPermissions(context, permissions, requestCode);
    }

    public static void requestPermission(Activity context, String permission, int requestCode) {
        requestPermission(context, new String[]{permission}, requestCode);
    }

    public static boolean permissionGranted(Context context, String permission) {
        return ContextCompat.checkSelfPermission(context, permission) == 0;
    }

    public static boolean permissionGranted(String permission) {
        return permissionGranted(sContext, permission);
    }

    public static boolean permissionDenied(Activity activity, String permission) {
        return ActivityCompat.shouldShowRequestPermissionRationale(activity, permission);
    }

    public static void getIpByHost(String host, DNSListener listener) {
        new DNSTask(listener).execute(new String[]{host});
    }

    private static class DNSTask extends AsyncTask<String, Integer, String> {
        private DNSListener mDNSListener;

        public DNSTask(DNSListener listener) {
            this.mDNSListener = listener;
        }

        /* access modifiers changed from: protected */
        public String doInBackground(String... values) {
            try {
                return InetAddress.getByName(values[0]).getHostAddress();
            } catch (Exception e) {
                Logger.w(Util.TAG, e);
                return null;
            }
        }

        /* access modifiers changed from: protected */
        public void onPostExecute(String value) {
            if (this.mDNSListener != null) {
                this.mDNSListener.onComplete(value);
            }
        }
    }

    public static NetworkType getNetworkType() {
        if (!permissionGranted("android.permission.ACCESS_NETWORK_STATE")) {
            return NetworkType.UNKNOWN;
        }
        ConnectivityManager cm = (ConnectivityManager) sContext.getSystemService("connectivity");
        if (cm == null) {
            return NetworkType.UNKNOWN;
        }
        NetworkInfo networkInfo1 = cm.getNetworkInfo(1);
        if (networkInfo1 != null && networkInfo1.getState() == NetworkInfo.State.CONNECTED) {
            return NetworkType.WIFI;
        }
        NetworkInfo networkInfo2 = cm.getNetworkInfo(0);
        if (networkInfo2 == null || networkInfo2.getState() != NetworkInfo.State.CONNECTED) {
            return NetworkType.NONE;
        }
        switch (networkInfo2.getSubtype()) {
            case 1:
            case 2:
            case 4:
            case 7:
            case 11:
                return NetworkType.MOBILE_2G;
            case 3:
            case 5:
            case 6:
            case 8:
            case 9:
            case 10:
            case 12:
            case 14:
            case 15:
                return NetworkType.MOBILE_3G;
            case 13:
                return NetworkType.MOBILE_4G;
            default:
                return NetworkType.NONE;
        }
    }

    public static boolean isFullscreenIssueDevice() {
        String devices = getString(NTVDefine.KCONF_STRING_FULLSCREEN_ISSUE_DEVICES);
        if (devices == null) {
            return false;
        }
        for (String device : devices.split("\\|")) {
            if (device.equals(Build.MODEL)) {
                return true;
            }
        }
        return false;
    }

    public static int dp2px(Context context, float dp) {
        return (int) ((dp * context.getResources().getDisplayMetrics().density) + 0.5f);
    }

    public static int getDisplayRotation(Activity activity) {
        if (activity == null) {
            return -1;
        }
        return activity.getWindowManager().getDefaultDisplay().getRotation();
    }

    public static boolean hasNotchScreen(Context context) {
        if (isVivo()) {
            boolean hasNotch = hasNotchInVivo(context);
            Logger.d(TAG, "hasNotchInVivo(), " + hasNotch);
            return hasNotch;
        } else if (isOppo()) {
            boolean hasNotch2 = hasNotchInOppo(context);
            Logger.d(TAG, "hasNotchInOppo(), " + hasNotch2);
            return hasNotch2;
        } else if (isHuawei()) {
            boolean hasNotch3 = hasNotchInHuawei(context);
            Logger.d(TAG, "hasNotchInHuawei(), " + hasNotch3);
            return hasNotch3;
        } else if (!isXiaomi()) {
            return false;
        } else {
            boolean hasNotch4 = hasNotchInXiaomi(context);
            Logger.d(TAG, "hasNotchInXiaomi(), " + hasNotch4);
            return hasNotch4;
        }
    }

    public static int getNotchHeight(Context context) {
        if (isVivo()) {
            return getNotchHeightInVivo(context);
        }
        if (isOppo()) {
            return getNotchHeightInOppo(context);
        }
        if (isHuawei()) {
            return getNotchHeightInHuawei(context);
        }
        if (isXiaomi()) {
            return getNotchHeightInXiaomi(context);
        }
        return 0;
    }

    public static boolean isVivo() {
        return "vivo".equals(Build.MANUFACTURER.toLowerCase());
    }

    public static boolean hasNotchInVivo(Context context) {
        boolean hasNotch = false;
        try {
            Class ftFeature = context.getClassLoader().loadClass("android.util.FtFeature");
            hasNotch = ((Boolean) ftFeature.getMethod("isFeatureSupport", new Class[]{Integer.TYPE}).invoke(ftFeature, new Object[]{32})).booleanValue();
            Logger.d(TAG, "hasNotchInVivo: " + hasNotch);
            boolean z = hasNotch;
            return hasNotch;
        } catch (ClassNotFoundException e) {
            Logger.d(TAG, "hasNotchInVivo() ClassNotFoundException: " + e);
            boolean z2 = hasNotch;
            return hasNotch;
        } catch (NoSuchMethodException e2) {
            Logger.d(TAG, "hasNotchInVivo() NoSuchMethodException: " + e2);
            boolean z3 = hasNotch;
            return hasNotch;
        } catch (Exception e3) {
            Logger.d(TAG, "hasNotchInVivo() Exception: " + e3);
            boolean z4 = hasNotch;
            return hasNotch;
        } catch (Throwable th) {
            boolean z5 = hasNotch;
            return hasNotch;
        }
    }

    public static int getNotchHeightInVivo(Context context) {
        return dp2px(context, 28.0f);
    }

    public static boolean isOppo() {
        return "oppo".equals(Build.MANUFACTURER.toLowerCase());
    }

    public static boolean hasNotchInOppo(Context context) {
        try {
            boolean hasNotch = context.getPackageManager().hasSystemFeature("com.oppo.feature.screen.heteromorphism");
            boolean z = hasNotch;
            return hasNotch;
        } catch (Exception e) {
            Logger.d(TAG, "hasNotchInOppo() Exception: " + e);
            return false;
        } catch (Throwable th) {
            return false;
        }
    }

    public static int getNotchHeightInOppo(Context context) {
        return 80;
    }

    public static boolean isHuawei() {
        return "huawei".equals(Build.MANUFACTURER.toLowerCase());
    }

    public static boolean hasNotchInHuawei(Context context) {
        try {
            Class HwNotchSizeUtil = context.getClassLoader().loadClass("com.huawei.android.util.HwNotchSizeUtil");
            boolean hasNotch = ((Boolean) HwNotchSizeUtil.getMethod("hasNotchInScreen", new Class[0]).invoke(HwNotchSizeUtil, new Object[0])).booleanValue();
            boolean z = hasNotch;
            return hasNotch;
        } catch (ClassNotFoundException e) {
            Logger.d(TAG, "hasNotchInHuawei() ClassNotFoundException: " + e);
            return false;
        } catch (NoSuchMethodException e2) {
            Logger.d(TAG, "hasNotchInHuawei() NoSuchMethodException: " + e2);
            return false;
        } catch (Exception e3) {
            Logger.d(TAG, "hasNotchInHuawei() Exception: " + e3);
            return false;
        } catch (Throwable th) {
            return false;
        }
    }

    public static int getNotchHeightInHuawei(Context context) {
        int[] iArr = {0, 0};
        try {
            Class HwNotchSizeUtil = context.getClassLoader().loadClass("com.huawei.android.util.HwNotchSizeUtil");
            int height = ((int[]) HwNotchSizeUtil.getMethod("getNotchSize", new Class[0]).invoke(HwNotchSizeUtil, new Object[0]))[1];
            int i = height;
            return height;
        } catch (ClassNotFoundException e) {
            Logger.d(TAG, "getNotchHeightInHuawei() ClassNotFoundException");
            return 0;
        } catch (NoSuchMethodException e2) {
            Logger.d(TAG, "getNotchHeightInHuawei() NoSuchMethodException");
            return 0;
        } catch (Exception e3) {
            Logger.d(TAG, "getNotchHeightInHuawei() Exception");
            return 0;
        } catch (Throwable th) {
            return 0;
        }
    }

    public static boolean isXiaomi() {
        return "xiaomi".equals(Build.MANUFACTURER.toLowerCase());
    }

    public static boolean hasNotchInXiaomi(Context context) {
        boolean hasNotch;
        try {
            Class SystemProperties = context.getClassLoader().loadClass("android.os.SystemProperties");
            if (((Integer) SystemProperties.getMethod("getInt", new Class[]{String.class, Integer.TYPE}).invoke(SystemProperties, new Object[]{new String("ro.miui.notch"), new Integer(0)})).intValue() == 1) {
                hasNotch = true;
            } else {
                hasNotch = false;
            }
            boolean z = hasNotch;
            return hasNotch;
        } catch (ClassNotFoundException e) {
            Logger.d(TAG, "hasNotchInXiaomi() ClassNotFoundException: " + e);
            return false;
        } catch (NoSuchMethodException e2) {
            Logger.d(TAG, "hasNotchInXiaomi() NoSuchMethodException: " + e2);
            return false;
        } catch (IllegalAccessException e3) {
            Logger.d(TAG, "hasNotchInXiaomi() IllegalAccessException: " + e3);
            return false;
        } catch (IllegalArgumentException e4) {
            Logger.d(TAG, "hasNotchInXiaomi() IllegalArgumentException: " + e4);
            return false;
        } catch (InvocationTargetException e5) {
            Logger.d(TAG, "hasNotchInXiaomi() InvocationTargetException: " + e5);
            return false;
        } catch (Exception e6) {
            Logger.d(TAG, "hasNotchInXiaomi() Exception: " + e6);
            return false;
        } catch (Throwable th) {
            return false;
        }
    }

    public static int getNotchHeightInXiaomi(Context context) {
        int notchHeight = 0;
        try {
            int resourceId = context.getResources().getIdentifier("notch_height", "dimen", SystemMediaRouteProvider.PACKAGE_NAME);
            if (resourceId > 0) {
                notchHeight = context.getResources().getDimensionPixelSize(resourceId);
            }
            int i = notchHeight;
            return notchHeight;
        } catch (Exception e) {
            Logger.d(TAG, "getNotchHeightInXiaomi() Exception: " + e);
            return 0;
        } catch (Throwable th) {
            return 0;
        }
    }
}
