package com.banalytics;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import com.banalytics.BATrackerConst;
import com.btalk.helper.BBAppLogger;
import com.tencent.tp.r;
import java.util.List;
import org.json.JSONArray;

public class BATracker {
    private static volatile BATracker __instance;
    private static String _deviceId;
    private static int _numOfFails;
    protected DatabaseHelper _databaseHelper;

    public static BATracker getInstance() {
        if (__instance == null) {
            synchronized (BATracker.class) {
                if (__instance == null) {
                    BBAppLogger.i("no tracker available, starting a new one", new Object[0]);
                    __instance = new BATracker();
                }
            }
        }
        return __instance;
    }

    private BATracker() {
    }

    public void init(Context context) {
        _numOfFails = 0;
        _deviceId = loadDeviceIdFromLocal(context);
        if (_deviceId == null) {
            _deviceId = generateDeviceId(context);
            saveDeviceIdToLocal(context, _deviceId);
        }
        this._databaseHelper = new DatabaseHelper(context);
    }

    private static boolean isHasReadPhoneStatePermission(Context context) {
        return context.checkCallingOrSelfPermission(r.a) == 0;
    }

    private String generateDeviceId(Context context) {
        StringBuilder stringBuffer = new StringBuilder();
        stringBuffer.append("DV_");
        stringBuffer.append(Settings.Secure.getString(context.getContentResolver(), "android_id").replace(" ", "$"));
        return stringBuffer.substring(0, Math.min(128, stringBuffer.length()));
    }

    /* JADX WARNING: type inference failed for: r7v4, types: [java.net.URLConnection] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void sendEvents(java.lang.String r12, java.util.List<com.banalytics.BAEvent> r13) {
        /*
            r11 = this;
            r1 = 0
            java.net.URL r4 = new java.net.URL     // Catch:{ IOException -> 0x0076 }
            r4.<init>(r12)     // Catch:{ IOException -> 0x0076 }
            java.net.URLConnection r7 = r4.openConnection()     // Catch:{ IOException -> 0x0076 }
            r0 = r7
            java.net.HttpURLConnection r0 = (java.net.HttpURLConnection) r0     // Catch:{ IOException -> 0x0076 }
            r1 = r0
            java.lang.String r7 = "POST"
            r1.setRequestMethod(r7)     // Catch:{ IOException -> 0x0076 }
            java.lang.String r7 = "content-type"
            java.lang.String r8 = "application/json"
            r1.setRequestProperty(r7, r8)     // Catch:{ IOException -> 0x0076 }
            r7 = 1
            r1.setDoInput(r7)     // Catch:{ IOException -> 0x0076 }
            r7 = 1
            r1.setDoOutput(r7)     // Catch:{ IOException -> 0x0076 }
            java.io.OutputStream r3 = r1.getOutputStream()     // Catch:{ IOException -> 0x0076 }
            java.io.BufferedWriter r6 = new java.io.BufferedWriter     // Catch:{ IOException -> 0x0076 }
            java.io.OutputStreamWriter r7 = new java.io.OutputStreamWriter     // Catch:{ IOException -> 0x0076 }
            java.lang.String r8 = "UTF-8"
            r7.<init>(r3, r8)     // Catch:{ IOException -> 0x0076 }
            r6.<init>(r7)     // Catch:{ IOException -> 0x0076 }
            java.lang.String r7 = generateJSONString(r13)     // Catch:{ IOException -> 0x0076 }
            r6.write(r7)     // Catch:{ IOException -> 0x0076 }
            r6.flush()     // Catch:{ IOException -> 0x0076 }
            r6.close()     // Catch:{ IOException -> 0x0076 }
            r3.close()     // Catch:{ IOException -> 0x0076 }
            r1.connect()     // Catch:{ IOException -> 0x0076 }
            int r5 = r1.getResponseCode()     // Catch:{ IOException -> 0x0076 }
            r7 = 404(0x194, float:5.66E-43)
            if (r5 == r7) goto L_0x0067
            com.banalytics.DatabaseHelper r7 = r11._databaseHelper     // Catch:{ IOException -> 0x0076 }
            r7.removeSentEvents(r13)     // Catch:{ IOException -> 0x0076 }
            java.lang.String r7 = "events log posted to server %d"
            r8 = 1
            java.lang.Object[] r8 = new java.lang.Object[r8]     // Catch:{ IOException -> 0x0076 }
            r9 = 0
            java.lang.Integer r10 = java.lang.Integer.valueOf(r5)     // Catch:{ IOException -> 0x0076 }
            r8[r9] = r10     // Catch:{ IOException -> 0x0076 }
            com.btalk.helper.BBAppLogger.i(r7, r8)     // Catch:{ IOException -> 0x0076 }
        L_0x0061:
            if (r1 == 0) goto L_0x0066
            r1.disconnect()
        L_0x0066:
            return
        L_0x0067:
            int r7 = _numOfFails     // Catch:{ IOException -> 0x0076 }
            int r7 = r7 + 1
            _numOfFails = r7     // Catch:{ IOException -> 0x0076 }
            java.lang.String r7 = "server is likely down :X"
            r8 = 0
            java.lang.Object[] r8 = new java.lang.Object[r8]     // Catch:{ IOException -> 0x0076 }
            com.btalk.helper.BBAppLogger.i(r7, r8)     // Catch:{ IOException -> 0x0076 }
            goto L_0x0061
        L_0x0076:
            r2 = move-exception
            int r7 = _numOfFails     // Catch:{ all -> 0x008b }
            int r7 = r7 + 1
            _numOfFails = r7     // Catch:{ all -> 0x008b }
            java.lang.String r7 = "http request unsuccessful"
            r8 = 0
            java.lang.Object[] r8 = new java.lang.Object[r8]     // Catch:{ all -> 0x008b }
            com.btalk.helper.BBAppLogger.i(r7, r8)     // Catch:{ all -> 0x008b }
            if (r1 == 0) goto L_0x0066
            r1.disconnect()
            goto L_0x0066
        L_0x008b:
            r7 = move-exception
            if (r1 == 0) goto L_0x0091
            r1.disconnect()
        L_0x0091:
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.banalytics.BATracker.sendEvents(java.lang.String, java.util.List):void");
    }

    private static String generateJSONString(List<BAEvent> events) {
        JSONArray eventsJson = new JSONArray();
        for (BAEvent event : events) {
            eventsJson.put(event.toJSON());
        }
        return eventsJson.toString();
    }

    public boolean sendEventsIfNeeded(Context context) {
        if (_numOfFails >= 2 || this._databaseHelper.getPendingEventCount() <= 0) {
            return false;
        }
        List<BAEvent> pendingEvents = this._databaseHelper.getPendingEvents();
        try {
            Bundle bundle = context.getPackageManager().getApplicationInfo(context.getPackageName(), 128).metaData;
            if (bundle != null && bundle.containsKey(BATrackerConst.MANIFEST_REPORT_URL)) {
                BATrackerConst.URL = bundle.getString(BATrackerConst.MANIFEST_REPORT_URL);
            }
        } catch (PackageManager.NameNotFoundException e) {
        }
        BBAppLogger.i("report URL %s", BATrackerConst.URL);
        if (TextUtils.isEmpty(BATrackerConst.URL)) {
            return true;
        }
        sendEvents(BATrackerConst.URL, pendingEvents);
        return true;
    }

    public void recordNewEvent(String cmdType, String remarks, String uid, String country, int appVersion) {
        this._databaseHelper.createNewEvent(_deviceId, cmdType, remarks, uid, country, appVersion);
    }

    public void recordNewInstallEvent(Context context, String remarks, String channel, int apiLevel, String deviceInfo, int appVersion, String referrer, String manufacturer, String appKey) {
        if (!isInstallationEventTracked(context) && this._databaseHelper.createNewInstallEvent(_deviceId, remarks, channel, apiLevel, deviceInfo, appVersion, referrer, manufacturer, appKey)) {
            markInstallationEventAsTracked(context);
        }
    }

    private String loadDeviceIdFromLocal(Context context) {
        return context.getSharedPreferences(BATrackerConst.LOCAL_STORAGE.BA_LOCAL_STORE_V1, 0).getString("device_id", (String) null);
    }

    private void saveDeviceIdToLocal(Context context, String deviceId) {
        SharedPreferences.Editor editor = context.getSharedPreferences(BATrackerConst.LOCAL_STORAGE.BA_LOCAL_STORE_V1, 0).edit();
        editor.putString("device_id", deviceId);
        editor.apply();
    }

    /* access modifiers changed from: protected */
    public void markInstallationEventAsTracked(Context context) {
        SharedPreferences.Editor editor = context.getSharedPreferences(BATrackerConst.LOCAL_STORAGE.BA_LOCAL_STORE_V1, 0).edit();
        editor.putBoolean(BATrackerConst.LOCAL_STORAGE.IS_INSTALLATION_SENT_KEY, true);
        editor.apply();
    }

    /* access modifiers changed from: protected */
    public boolean isInstallationEventTracked(Context context) {
        return context.getSharedPreferences(BATrackerConst.LOCAL_STORAGE.BA_LOCAL_STORE_V1, 0).getBoolean(BATrackerConst.LOCAL_STORAGE.IS_INSTALLATION_SENT_KEY, false);
    }

    protected static void startRecordInstallService(Context context, String remarks, Intent intent) {
        Intent trackerIntent = new Intent(context, BARecordService.class);
        trackerIntent.putExtra("command", 4);
        trackerIntent.putExtra(BATrackerConst.JSON_KEYS.USER_ID, 0);
        trackerIntent.putExtra("description", remarks);
        trackerIntent.putExtra(BATrackerConst.JSON_KEYS.CMD_TYPE, BATrackerConst.EVENT_TYPES.INSTALLATION);
        fillInInstallationDetails(context, intent, trackerIntent);
        context.startService(trackerIntent);
        setRecordInstallationTrue(context);
    }

    public static void startRecordInstallServiceNoIntent(Context context, String remarks, boolean forced) {
        boolean z = false;
        try {
            Bundle bundle = context.getPackageManager().getApplicationInfo(context.getPackageName(), 128).metaData;
            if (bundle != null) {
                if (bundle.containsKey(BATrackerConst.MANIFEST_PREINSTALL_TAG) && bundle.getBoolean(BATrackerConst.MANIFEST_PREINSTALL_TAG)) {
                    z = true;
                }
                if ((Boolean.valueOf(z).booleanValue() || forced) && !hasRecordedInstallation(context)) {
                    Intent trackerIntent = new Intent(context, BARecordService.class);
                    trackerIntent.putExtra("command", 4);
                    trackerIntent.putExtra(BATrackerConst.JSON_KEYS.USER_ID, 0);
                    trackerIntent.putExtra("description", remarks);
                    trackerIntent.putExtra(BATrackerConst.JSON_KEYS.CMD_TYPE, BATrackerConst.EVENT_TYPES.INSTALLATION);
                    fillInInstallationDetails(context, (Intent) null, trackerIntent);
                    context.startService(trackerIntent);
                    setRecordInstallationTrue(context);
                }
            }
        } catch (PackageManager.NameNotFoundException e) {
            BBAppLogger.e(e);
        }
    }

    private static void setRecordInstallationTrue(Context context) {
        SharedPreferences.Editor editor = context.getSharedPreferences(BATrackerConst.LOCAL_STORAGE.BA_LOCAL_STORE_V1, 0).edit();
        editor.putBoolean(BATrackerConst.LOCAL_STORAGE.IS_INSTALLATION_RECORDED, true);
        editor.apply();
    }

    private static boolean hasRecordedInstallation(Context context) {
        return context.getSharedPreferences(BATrackerConst.LOCAL_STORAGE.BA_LOCAL_STORE_V1, 0).getBoolean(BATrackerConst.LOCAL_STORAGE.IS_INSTALLATION_RECORDED, false);
    }

    private static Intent fillInInstallationDetails(Context context, Intent intent, Intent trackerIntent) {
        if (context != null) {
            if (intent != null) {
                trackerIntent.putExtra("referrer", intent.hasExtra("referrer") ? intent.getStringExtra("referrer") : BATrackerConst.DEFAULT_REFERRER);
            }
            try {
                PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
                Bundle bundle = context.getPackageManager().getApplicationInfo(context.getPackageName(), 128).metaData;
                String channel = "";
                String appKey = "";
                if (bundle != null) {
                    channel = bundle.containsKey(BATrackerConst.MANIFEST_CHANNEL_TAG) ? bundle.getString(BATrackerConst.MANIFEST_CHANNEL_TAG) : BATrackerConst.BA_DEFAULT.STR;
                    appKey = bundle.containsKey(BATrackerConst.MANIFEST_META_KEY_TAG) ? bundle.getString(BATrackerConst.MANIFEST_META_KEY_TAG) : BATrackerConst.BA_DEFAULT.STR;
                }
                trackerIntent.putExtra("channel", channel);
                trackerIntent.putExtra("app_key", appKey);
                trackerIntent.putExtra("app_version", packageInfo.versionCode);
                trackerIntent.putExtra("api_level", Build.VERSION.SDK_INT);
                trackerIntent.putExtra("manufacturer", Build.MANUFACTURER);
                trackerIntent.putExtra("device_info", Build.MODEL);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }
        return intent;
    }
}
