package com.tencent.ngame.utility;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Debug;
import android.os.Environment;
import android.os.Process;
import android.support.v4.media.session.PlaybackStateCompat;
import android.util.Log;
import com.amazonaws.services.s3.internal.Constants;
import com.facebook.share.internal.ShareConstants;
import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.tencent.ieg.ntv.NTVShowHelperAndroid;
import com.tencent.ngame.utility.SimCardUtil;
import com.tencent.smtt.sdk.WebView;
import com.tencent.tp.a.h;
import com.unity3d.player.UnityPlayer;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import org.json.JSONArray;

public class SGameUtility {
    static final int MAX_LEN = 10240;
    private static final String TAG = "SGameUtility";
    static BatteryReceiver batteryReceiver = null;
    public static boolean g_EnableInput = false;
    static final boolean g_EnableMultiSimCard = true;
    static String g_GAID = "";
    public static StringBuffer g_logBuffer = new StringBuffer();
    /* access modifiers changed from: private */
    public static int lastRet = 0;
    private static ActivityResultListener mActivityResultListener = null;
    private static PermissionListener mPermissionListener = null;
    private static String sDeeplink = null;
    private static Intent sSavedIntent = null;

    public interface ActivityResultListener {
        void onActivityResult(int i, int i2, Intent intent);
    }

    public enum OBB_TYPE {
        MAIN,
        PATCH
    }

    public interface PermissionListener {
        void onRequestPermissionsResult(int i, String[] strArr, int[] iArr);
    }

    public static void enableNativeLog(boolean enable) {
        DebugLog.setLogLevel(enable ? 2 : 6);
    }

    public static void EnableInput(boolean bEnable) {
        g_EnableInput = bEnable;
        Log.i("EnableInput", "java EnableInput " + g_EnableInput);
    }

    public static void dtLog(String log) {
        if (log != null && log.length() != 0) {
            Log.i("dtlog", "java dtlog " + log);
            g_logBuffer.append(log);
            g_logBuffer.append("\n");
            if (g_logBuffer.length() > 10240) {
                g_logBuffer.delete(0, g_logBuffer.length() - 10240);
            }
        }
    }

    public static void UploadException(String nameStr, String str) {
        try {
            String str2 = "CatchedException_(no crash)_" + nameStr + str;
            Log.i("UploadException", " C# CrashAttchLog\n-------\n" + g_logBuffer.toString() + "\n-----------\n");
            Log.i("UploadException", " new java RuntimeException(" + str2 + h.b);
            throw new RuntimeException(str2);
        } catch (Throwable th) {
        }
    }

    public static String GetMainObbPath() {
        return "";
    }

    public static String GetAppCodeVersion() {
        Context context = UnityPlayer.currentActivity;
        String ret = "";
        if (getPackageInfo(context) != null) {
            String ret2 = getPackageInfo(context).versionName;
            ret = ret2.substring(0, ret2.lastIndexOf("."));
        }
        Log.i("GetAppCodeVersion", ret);
        return ret;
    }

    private static PackageInfo getPackageInfo(Context context) {
        try {
            PackageInfo pi = context.getPackageManager().getPackageInfo(context.getPackageName(), 16384);
            PackageInfo packageInfo = pi;
            return pi;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String GetApkAbsPath() {
        try {
            return UnityPlayer.currentActivity.getPackageManager().getApplicationInfo(UnityPlayer.currentActivity.getPackageName(), 0).sourceDir;
        } catch (Exception e) {
            return "";
        }
    }

    public static String GetObbPath(OBB_TYPE type) {
        try {
            String root = Environment.getExternalStorageDirectory().toString();
            String packageName = UnityPlayer.currentActivity.getPackageName();
            String obbpath = root + "/Android/obb/" + packageName + (type == OBB_TYPE.MAIN ? "/main." : "/patch.") + UnityPlayer.currentActivity.getPackageManager().getPackageInfo(packageName, 0).versionCode + "." + packageName + ".obb";
            Log.e(TAG, obbpath);
            if (!new File(obbpath).exists()) {
                return null;
            }
            return obbpath;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static boolean IsFileExistInObb(String fileName, OBB_TYPE type) {
        String obbPath = GetObbPath(type);
        boolean res = false;
        if (obbPath != null) {
            try {
                ZipFile obb = new ZipFile(obbPath);
                Enumeration<? extends ZipEntry> zipEntries = obb.entries();
                while (true) {
                    if (zipEntries.hasMoreElements()) {
                        if (((ZipEntry) zipEntries.nextElement()).getName().equals(fileName)) {
                            res = true;
                            break;
                        }
                    } else {
                        break;
                    }
                }
                obb.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return res;
    }

    public static boolean checkObbFileVaild() {
        String obbPath = GetObbPath(OBB_TYPE.MAIN);
        if (obbPath.isEmpty()) {
            return false;
        }
        try {
            new ZipFile(obbPath).close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void ShowInstalledPackageNotIntect() {
        UnityPlayer.currentActivity.runOnUiThread(new Runnable() {
            public void run() {
                try {
                    UnityPlayer.currentActivity.getClass().getMethod("ShowInstalledPackageNotIntect", new Class[0]).invoke(UnityPlayer.currentActivity, new Object[0]);
                } catch (IllegalAccessException | IllegalArgumentException | NoSuchMethodException | InvocationTargetException e) {
                }
            }
        });
    }

    public static boolean IsFileExistInStreamingAssets(String fileName) {
        boolean res;
        InputStream inputStream = null;
        try {
            InputStream inputStream2 = UnityPlayer.currentActivity.getResources().getAssets().open(fileName);
            res = true;
            if (inputStream2 != null) {
                try {
                    inputStream2.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e2) {
            res = false;
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e3) {
                    e3.printStackTrace();
                }
            }
        } catch (Throwable th) {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e4) {
                    e4.printStackTrace();
                }
            }
            throw th;
        }
        Log.d(TAG, fileName + " " + res + " exist in apk");
        if (!res) {
            fileName = "assets/" + fileName;
            res = IsFileExistInObb(fileName, OBB_TYPE.MAIN);
        }
        Log.d(TAG, fileName + " " + res + " exist in main obb");
        if (!res) {
            res = IsFileExistInObb(fileName, OBB_TYPE.PATCH);
        }
        Log.d(TAG, fileName + " " + res + " exist in patch obb");
        return res;
    }

    public static long getAvailMemory() {
        ActivityManager am = (ActivityManager) UnityPlayer.currentActivity.getSystemService("activity");
        if (am == null) {
            return -1;
        }
        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        am.getMemoryInfo(mi);
        return mi.availMem / PlaybackStateCompat.ACTION_SET_CAPTIONING_ENABLED;
    }

    public static int GetNetworkType() {
        try {
            ConnectivityManager connectivity = (ConnectivityManager) UnityPlayer.currentActivity.getSystemService("connectivity");
            if (connectivity == null) {
                return 0;
            }
            NetworkInfo activeNetInfo = connectivity.getActiveNetworkInfo();
            if (activeNetInfo == null) {
                return -1;
            }
            if (!activeNetInfo.isAvailable() || !activeNetInfo.isConnected()) {
                return -1;
            }
            if (activeNetInfo.getType() == 1) {
                return 1;
            }
            if (activeNetInfo.getType() != 0) {
                return 0;
            }
            switch (activeNetInfo.getSubtype()) {
                case 1:
                case 2:
                case 4:
                case 7:
                case 11:
                    return 2;
                case 3:
                case 5:
                case 6:
                case 8:
                case 9:
                case 10:
                case 15:
                    return 3;
                case 13:
                    return 4;
                default:
                    return 0;
            }
        } catch (Exception e) {
            return -1;
        }
    }

    public static String getCarrierName() {
        SimCardUtil.SIMInfo si = new SimCardUtil(UnityPlayer.currentActivity.getApplicationContext()).getSimInfo();
        ArrayList<String> a_res = new ArrayList<>();
        Iterator<SimCardUtil.SingleSIMInfo> it = si.l_ssim_info.iterator();
        while (it.hasNext()) {
            SimCardUtil.SingleSIMInfo info = it.next();
            if (!(info == null || info.carrierName == null || info.carrierName.isEmpty())) {
                a_res.add(info.carrierName);
            }
        }
        return new JSONArray(a_res).toString();
    }

    public static void ExitApp() {
        Log.d("XXXX", "ExitApp");
        if (batteryReceiver != null) {
            UnityPlayer.currentActivity.unregisterReceiver(batteryReceiver);
            batteryReceiver = null;
        }
        UnityPlayer.currentActivity.finish();
        Process.killProcess(Process.myPid());
        System.exit(0);
    }

    public static void MoveAppToBack() {
        UnityPlayer.currentActivity.moveTaskToBack(true);
    }

    public static long getProcessMemery() {
        Log.d(TAG, "getProcessMemery");
        Context context = UnityPlayer.currentActivity;
        long memSize = -1;
        if (context == null) {
            Log.e(TAG, "Context null");
            return -1;
        }
        try {
            ActivityManager am = (ActivityManager) context.getSystemService("activity");
            int[] myMempid = {Process.myPid()};
            if (am == null) {
                return -1;
            }
            Debug.MemoryInfo[] memoryInfo = am.getProcessMemoryInfo(myMempid);
            if (memoryInfo == null || memoryInfo[0] == null) {
                return -1;
            }
            memSize = ((long) memoryInfo[0].getTotalPss()) / PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID;
            long j = memSize;
            return memSize;
        } catch (Exception e) {
            Log.e(TAG, "Get process Memory error:" + e.getMessage());
        }
    }

    public static void OnRestartFromCSHarp() {
        if (UnityPlayer.currentActivity == null) {
            dtLog("currentActivity is null");
            return;
        }
        new Thread() {
            public void run() {
                Log.d("XXXX", "OnRestartFromCSHarp");
                Intent launch = UnityPlayer.currentActivity.getBaseContext().getPackageManager().getLaunchIntentForPackage(UnityPlayer.currentActivity.getBaseContext().getPackageName());
                launch.addFlags(67108864);
                UnityPlayer.currentActivity.startActivity(launch);
                Process.killProcess(Process.myPid());
                Log.d("XXXX", "OnRestartFromCSHarp finish");
            }
        }.start();
        UnityPlayer.currentActivity.finish();
    }

    public static void CopyTextToClipboard(final String text) {
        Log.i("Call CopyTextToClipboard", text);
        UnityPlayer.currentActivity.runOnUiThread(new Runnable() {
            public void run() {
                Log.i("Call CopyTextToClipboard Run", text);
                ClipboardManager clipboardManager = (ClipboardManager) UnityPlayer.currentActivity.getSystemService("clipboard");
                if (clipboardManager != null) {
                    clipboardManager.setPrimaryClip(ClipData.newPlainText(ShareConstants.WEB_DIALOG_PARAM_DATA, text));
                    Log.i("CopyTextToClipboard", text);
                }
            }
        });
    }

    public static String getAndroidMetaData(String key) {
        String result = "";
        try {
            ApplicationInfo appInfo = UnityPlayer.currentActivity.getPackageManager().getApplicationInfo(UnityPlayer.currentActivity.getPackageName(), 128);
            if (appInfo != null) {
                result = appInfo.metaData.getString(key);
            }
        } catch (Exception e) {
            result = "";
        }
        return result == null ? "" : result;
    }

    public static boolean EMail(String address, String subject, String text) {
        Intent intent = new Intent("android.intent.action.SENDTO");
        intent.setData(Uri.parse(WebView.SCHEME_MAILTO + address));
        intent.putExtra("android.intent.extra.SUBJECT", subject);
        intent.putExtra("android.intent.extra.TEXT", text);
        if (UnityPlayer.currentActivity != null) {
            UnityPlayer.currentActivity.startActivity(Intent.createChooser(intent, ""));
            return true;
        }
        Log.e("SGameUtilitysendEmail", "UnityPlayer.currentActivity  cannot be null ！Failed to send email");
        return false;
    }

    public static String getAppVersion() {
        try {
            PackageInfo pinfo = UnityPlayer.currentActivity.getPackageManager().getPackageInfo(UnityPlayer.currentActivity.getPackageName(), 0);
            return pinfo.versionName + h.a + pinfo.versionCode + h.b;
        } catch (Exception e) {
            Log.e("SGameUtilitygetAppVersion", e.getMessage());
            return "UNKNOW";
        }
    }

    public static String get() {
        return Build.MANUFACTURER;
    }

    public static String getTextFromClipboard() {
        ClipData cdText;
        ClipboardManager clipboard = (ClipboardManager) UnityPlayer.currentActivity.getSystemService("clipboard");
        if (clipboard == null || !clipboard.hasPrimaryClip() || !clipboard.getPrimaryClipDescription().hasMimeType("text/plain") || (cdText = clipboard.getPrimaryClip()) == null) {
            return Constants.NULL_VERSION_ID;
        }
        ClipData.Item item = cdText.getItemAt(0);
        Log.i("getTextFromClipboard", item.getText().toString());
        return item.getText().toString();
    }

    private class AnsQueryConstants {
        public static final short E_CMD_BUTT = 10;
        public static final short E_CMD_GET_CONF_REQ = 3;
        public static final short E_CMD_GET_CONF_RES = 4;
        public static final short E_CMD_GET_OC_LIST_EX_REQ = 5;
        public static final short E_CMD_GET_OC_LIST_EX_RES = 6;
        public static final short E_CMD_GET_OC_LIST_REQ = 1;
        public static final short E_CMD_GET_OC_LIST_RES = 2;
        public static final short E_ERR_DOMAIN_NOT_EXIST = 9;
        public static final short E_ERR_ERROR = 1;
        public static final short E_ERR_SUCC = 0;
        public static final short E_IP_TYPE_OC = 0;
        public static final short E_IP_TYPE_RS = 1;
        public static final short E_IP_TYPE_TEST = 2;
        public static final short E_MOBILE_CHINAMOBILE = 4;
        public static final short E_MOBILE_TELCOM = 1;
        public static final short E_MOBILE_UNICOM = 2;
        public static final short E_MOBILE_UNKNOWN = 0;
        public static final short NETWORK_TYPE_2G = 2;
        public static final short NETWORK_TYPE_3G = 3;
        public static final short NETWORK_TYPE_4G = 4;
        public static final short NETWORK_TYPE_UNCONNECTED = -1;
        public static final short NETWORK_TYPE_UNKNOWN = 0;
        public static final short NETWORK_TYPE_WIFI = 1;

        private AnsQueryConstants() {
        }
    }

    public static void registerAudioObservers() {
        Activity activity = UnityPlayer.currentActivity;
        AudioUtility.registerVolumeChange(activity);
        AudioUtility.registerHeadSetPlug(activity);
    }

    public static void setSavedIntent(Intent intent) {
        sSavedIntent = intent;
        if (intent != null && isDeeplink(intent.getData())) {
            sDeeplink = intent.getDataString();
        }
        NTVShowHelperAndroid.OnNewIntent(intent);
    }

    public static Intent getSavedIntent() {
        return sSavedIntent;
    }

    private static boolean isDeeplink(Uri uri) {
        if (uri != null && uri.getScheme() != null && uri.getScheme().length() > 5 && uri.getScheme().substring(0, 5).equals("ngame")) {
            return true;
        }
        return false;
    }

    public static String getDeeplink() {
        return sDeeplink;
    }

    public static void clearDeeplink() {
        sDeeplink = null;
    }

    public static void setPermissionListener(PermissionListener listener) {
        mPermissionListener = listener;
    }

    public static void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (mPermissionListener != null) {
            mPermissionListener.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    public static void setActivityResultListener(ActivityResultListener listener) {
        mActivityResultListener = listener;
    }

    public static void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (mActivityResultListener != null) {
            mActivityResultListener.onActivityResult(requestCode, resultCode, data);
        }
    }

    public static int getBatteryState() {
        if (batteryReceiver != null) {
            return lastRet;
        }
        batteryReceiver = new BatteryReceiver();
        IntentFilter batteryFilter = new IntentFilter();
        batteryFilter.addAction("android.intent.action.BATTERY_CHANGED");
        return getBatteryStateToInt(UnityPlayer.currentActivity.registerReceiver(batteryReceiver, batteryFilter).getIntExtra("status", -1));
    }

    /* access modifiers changed from: private */
    public static int getBatteryStateToInt(int src) {
        switch (src) {
            case 1:
                return 0;
            case 2:
                return 2;
            case 3:
            case 4:
                return 1;
            case 5:
                return 3;
            default:
                return 0;
        }
    }

    private static class BatteryReceiver extends BroadcastReceiver {
        private BatteryReceiver() {
        }

        public void onReceive(Context arg0, Intent arg1) {
            int ret = SGameUtility.getBatteryStateToInt(arg1.getIntExtra("status", 0));
            if (SGameUtility.lastRet != ret) {
                int unused = SGameUtility.lastRet = ret;
                UnityPlayer.UnitySendMessage("NatviceCallbackUtilGameObject", "batteryStateDidChange", String.valueOf(ret));
                Log.i("Battery", "Current battery status " + ret);
            }
        }
    }

    public static synchronized String getAdId(Context context) {
        String adId = null;
        synchronized (SGameUtility.class) {
            if (Build.VERSION.SDK_INT < 19) {
                Log.e(TAG, "Android version is too low, do not support Google AdvertisingId feature.");
            } else {
                AdvertisingIdClient.Info adInfo = null;
                try {
                    adInfo = AdvertisingIdClient.getAdvertisingIdInfo(context);
                    if (adInfo.isLimitAdTrackingEnabled()) {
                        Log.e(TAG, "Ad tracking is limited by user, Google AdvertisingId is not available.");
                    }
                } catch (GooglePlayServicesNotAvailableException e) {
                    Log.e(TAG, "Google Play is not installed on this device, Google AdvertisingId is not available.");
                    e.printStackTrace();
                } catch (GooglePlayServicesRepairableException e2) {
                    Log.e(TAG, "There was a recoverable error connecting to Google Play Services, Google AdvertisingId is not available.");
                    e2.printStackTrace();
                } catch (IOException e3) {
                    Log.e(TAG, "Connection to Google Play Services failed, Google AdvertisingId is not available.");
                    e3.printStackTrace();
                } catch (IllegalStateException e4) {
                    Log.e(TAG, "This method was called on the main thread.");
                    e4.printStackTrace();
                } catch (Exception e5) {
                    e5.printStackTrace();
                }
                adId = null;
                try {
                    adId = adInfo.getId();
                } catch (NullPointerException e6) {
                    e6.printStackTrace();
                }
            }
        }
        return adId;
    }

    public static void asyncGetGAID() {
        new Thread() {
            public void run() {
                DebugLog.d(SGameUtility.TAG, "getAdId() start");
                SGameUtility.g_GAID = SGameUtility.getAdId(UnityPlayer.currentActivity);
                DebugLog.i(SGameUtility.TAG, "GAID: " + SGameUtility.g_GAID);
                DebugLog.d(SGameUtility.TAG, "getAdId() end");
            }
        }.start();
    }

    public static String getCachedGAID() {
        return g_GAID == null ? "" : g_GAID;
    }
}
