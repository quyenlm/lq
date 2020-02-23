package com.garena.game.kgtw;

import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import com.appsflyer.AppsFlyerLib;
import com.facebook.AccessToken;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.internal.AnalyticsEvents;
import com.facebook.internal.NativeProtocol;
import com.google.android.gms.drive.DriveFile;
import com.google.firebase.FirebaseApp;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.tencent.apollo.ApolloVoiceDeviceMgr;
import com.tencent.bugly.crashreport.CrashReport;
import com.tencent.imsdk.extend.garena.api.IMSDKExtendGarena;
import com.tencent.imsdk.statics.appsflyer.AppsFlyerStatHelper;
import com.tencent.imsdk.statics.appsflyer.ExtendAppsFlyer;
import com.tencent.ngame.utility.DebugLog;
import com.tencent.ngame.utility.SGameUtility;
import com.tencent.tp.a.h;
import com.tsf4g.apollo.ApolloPlayerActivity;
import com.unity3d.player.UnityPlayer;
import java.io.File;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Currency;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SGameRealActivity extends ApolloPlayerActivity {
    private static final String TAG = "SGameRealActivity";
    private final String SGAME_PERMISSION_CALLBACK_METHOD_NAME = "OnRequestPermissionsResult";
    private final String SGAME_PERMISSION_CALLBACK_OBJECT_NAME = "BootObj";
    private FirebaseAnalytics mFirebaseAnalytics;
    private final Handler mHideHandler = new Handler() {
        public void handleMessage(Message msg) {
            SGameRealActivity.this.CheckIfHideSystemUI();
        }
    };
    private BroadcastReceiver mLockscreenReceiver = new LockscreenReceiver();
    private IntentFilter mLockscreenfilter = new IntentFilter("android.intent.action.USER_PRESENT");

    static {
        System.loadLibrary("apollo");
    }

    public SGameRealActivity() {
        Log.i(TAG, "  SGameActivity start");
        Log.i(TAG, "  SGameActivity  end");
        SetEnableTouch(false);
    }

    private void initBugly() {
        String buglyId = "1104811017";
        CrashReport.UserStrategy us = new CrashReport.UserStrategy(this);
        Log.d(TAG, "initBugly");
        Log.d(TAG, CrashReport.getBuglyVersion(this));
        try {
            buglyId = getPackageManager().getApplicationInfo(getPackageName(), 128).metaData.getString("APPID_BUGLY").substring(5);
            Log.d(TAG, "buglyId:" + buglyId);
            PackageInfo pinfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            us.setAppVersion(pinfo.versionName + h.a + pinfo.versionCode + h.b);
        } catch (Exception e) {
            Log.e(TAG, "no bugly id in manifest");
            Log.e(TAG, e.toString());
            e.printStackTrace();
        }
        Log.i(TAG, "Apollo bugly onCreate");
        CrashReport.initCrashReport(this, buglyId, false, us);
    }

    private void checkMRSettings() {
        int status;
        boolean mrEnable = false;
        Log.d(TAG, "checkMRSettings");
        SharedPreferences settings = getSharedPreferences(getPackageName(), 0);
        SharedPreferences.Editor editor = settings.edit();
        if (settings.getInt("m_MobileMTRendering", 0) == 1) {
            mrEnable = true;
        }
        if (mrEnable && (status = settings.getInt("AndriodMRStatus", 1)) != 7) {
            if (status == 2 || status == 4) {
                editor.putInt("DisableMTR", 1);
                Log.d("[MRFeature]:", "DisableMTR");
                editor.putInt("OnMRStartUpExit", 1);
            } else {
                if (status == 1 || status == 3) {
                    status++;
                }
                editor.putInt("AndriodMRStatus", status);
            }
            editor.commit();
        }
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        setEnableRequestPermission(false);
        super.onCreate(savedInstanceState);
        initBugly();
        checkMRSettings();
        SGameUtility.asyncGetGAID();
        Log.i(TAG, "imsdk-garena initialize");
        IMSDKExtendGarena.initialize(this);
        ExtendAppsFlyer.onCreate(UnityPlayer.currentActivity);
        Log.i(TAG, "ApolloVoice onCreate");
        ApolloVoiceDeviceMgr.ApolloVoiceDeviceInit(getApplicationContext(), this);
        Log.i(TAG, "FacebookSdk.sdkInitialize()");
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(getApplication());
        Log.i(TAG, "FirebaseAnalytics.getInstance()");
        this.mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        AppsFlyerStatHelper.getInstance().initialize(this);
        AppsFlyerLib.getInstance().sendDeepLinkData(this);
        getWindow().getDecorView().setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
            public void onSystemUiVisibilityChange(int i) {
                Log.i(SGameRealActivity.TAG, "onSystemUiVisibilityChange int: " + i);
                if (SGameRealActivity.getSystemVersion() >= 19) {
                    SGameRealActivity.this.CheckIfHideSystemUI();
                }
            }
        });
    }

    class LockscreenReceiver extends BroadcastReceiver {
        LockscreenReceiver() {
        }

        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("android.intent.action.USER_PRESENT") && SGameRealActivity.getSystemVersion() == 26) {
                SGameRealActivity.this.startActivity(new Intent(SGameRealActivity.this, BlankActivity.class));
            }
        }
    }

    @TargetApi(19)
    public void CheckIfHideSystemUI() {
        View view;
        if (getSystemVersion() >= 19 && (view = getWindow().getDecorView()) != null) {
            int visibility = view.getSystemUiVisibility();
            Log.i(TAG, "cur visibility int: " + visibility);
            Log.i(TAG, "options int: " + 5894);
            if (visibility != 5894) {
                Log.i(TAG, "apply options " + 5894);
                view.setSystemUiVisibility(5894);
            }
        }
    }

    private void delayedHide(int delayMillis) {
        Log.i(TAG, "delay hide system ui");
        this.mHideHandler.removeMessages(0);
        this.mHideHandler.sendEmptyMessageDelayed(0, (long) delayMillis);
    }

    public void RegisterXGPush(String openID) {
    }

    public void RefeshPhoto(String filename) {
        try {
            String url = MediaStore.Images.Media.insertImage(getApplicationContext().getContentResolver(), new File(filename).getAbsolutePath(), "NGame_Share", (String) null);
            Intent intent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
            intent.setData(Uri.parse(url));
            getApplicationContext().sendBroadcast(intent);
            Log.d("RefeshPhoto", " RefeshPhoto java" + filename);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int CheckSelfPermission(String permission) {
        return ContextCompat.checkSelfPermission(this, permission);
    }

    public boolean ShouldShowRequestPermissionRationale(String permission) {
        boolean flag;
        try {
            flag = getSharedPreferences("permissonsInfo", 0).getBoolean(permission, true);
        } catch (Throwable th) {
            flag = false;
        }
        if (flag || ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
            return true;
        }
        return false;
    }

    public void OpenAndroidSetting() {
        try {
            Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS", Uri.fromParts("package", getPackageName(), (String) null));
            intent.addFlags(DriveFile.MODE_READ_ONLY);
            startActivity(intent);
        } catch (Throwable e) {
            Log.d("XXXX", e.toString());
        }
    }

    public void RequestPermissions(String[] permissions, int requestCode) {
        Log.d(TAG, "Request Permissions: " + Arrays.toString(permissions));
        ActivityCompat.requestPermissions(this, permissions, requestCode);
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        try {
            SharedPreferences.Editor editor = getSharedPreferences("permissonsInfo", 0).edit();
            for (String putBoolean : permissions) {
                editor.putBoolean(putBoolean, false);
            }
            editor.apply();
        } catch (Throwable th) {
        }
        JSONObject args = new JSONObject();
        try {
            JSONArray p = new JSONArray();
            for (String iter : permissions) {
                p.put(iter);
            }
            JSONArray r = new JSONArray();
            for (int iter2 : grantResults) {
                r.put(iter2);
            }
            args.put("requestCode", requestCode);
            args.put(NativeProtocol.RESULT_ARGS_PERMISSIONS, p);
            args.put("grantResults", r);
        } catch (JSONException e) {
            Log.e(TAG, e.toString());
        }
        String json = args.toString();
        Log.d(TAG, "Request Permissions Result: " + json);
        UnityPlayer.UnitySendMessage("BootObj", "OnRequestPermissionsResult", json);
        SGameUtility.onRequestPermissionsResult(requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    public void ShowInstalledPackageNotIntect() {
        Drawable icon = null;
        try {
            icon = getPackageManager().getApplicationInfo(getPackageName(), 0).loadIcon(getPackageManager());
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this, getResources().getIdentifier("AovDialogStyle", AnalyticsEvents.PARAMETER_LIKE_VIEW_STYLE, getPackageName()));
        if (icon != null) {
            builder.setIcon(icon);
        }
        builder.setTitle(R.string.package_is_invaild_title);
        builder.setMessage(R.string.package_is_invaild);
        builder.setNegativeButton(R.string.submit, (DialogInterface.OnClickListener) new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                SGameUtility.ExitApp();
            }
        });
        builder.setCancelable(false);
        builder.create().show();
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i("SGameActivity resultCode", Integer.toString(resultCode));
        super.onActivityResult(requestCode, resultCode, data);
        Log.i(TAG, "imsdk-garena initialize");
        IMSDKExtendGarena.onActivityResult(requestCode, resultCode, data);
        SGameUtility.onActivityResult(requestCode, resultCode, data);
    }

    /* access modifiers changed from: protected */
    public void onNewIntent(Intent intent) {
        try {
            Log.i(TAG, "onNewIntent");
            super.onNewIntent(intent);
        } catch (Exception e) {
            Log.i("SGameActivity onNewIntent", e.toString());
        }
    }

    /* access modifiers changed from: protected */
    public void onPause() {
        Log.i(TAG, "onPause");
        super.onPause();
        Log.i(TAG, "IMSDKExtendGarena onPause");
        IMSDKExtendGarena.onPause();
        if (getSystemVersion() == 26) {
            unregisterReceiver(this.mLockscreenReceiver);
        }
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        Log.i(TAG, "onResume");
        super.onResume();
        Log.i(TAG, "IMSDKExtendGarena onResume");
        IMSDKExtendGarena.onResume();
        if (getSystemVersion() == 26) {
            registerReceiver(this.mLockscreenReceiver, this.mLockscreenfilter);
        }
    }

    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            Log.i(TAG, "onWindowFocusChanged CheckIfHideSystemUI");
            delayedHide(1000);
            return;
        }
        this.mHideHandler.removeMessages(0);
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        Log.i(TAG, "onDestroy");
        super.onDestroy();
        Log.i(TAG, "IMSDKExtendGarena onDestroy");
        IMSDKExtendGarena.onDestroy();
    }

    public static int getSystemVersion() {
        return Build.VERSION.SDK_INT;
    }

    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == 66 || event.getKeyCode() == 4) {
            Log.i(TAG, "dispatchKeyEvent CheckIfHideSystemUI");
            CheckIfHideSystemUI();
        }
        return super.dispatchKeyEvent(event);
    }

    public static String getFirebaseInstanceID(Context context) {
        String firebaseInstanceID = "";
        try {
            if (FirebaseApp.getApps(context).isEmpty()) {
                DebugLog.e(TAG, "FirebaseApp is empty!!! Can not register Firebase notification!");
            } else {
                firebaseInstanceID = FirebaseInstanceId.getInstance().getId();
                if (firebaseInstanceID == null) {
                    firebaseInstanceID = "";
                }
            }
        } catch (IllegalStateException e) {
            DebugLog.e(TAG, "FirebaseApp IllegalStateException!");
        }
        DebugLog.d(TAG, "Firebase InstanceID: " + firebaseInstanceID);
        return firebaseInstanceID;
    }

    public static String getFirebaseToken(Context context) {
        String firebaseToken = "";
        try {
            if (FirebaseApp.getApps(context).isEmpty()) {
                DebugLog.e(TAG, "FirebaseApp is empty!!! Can not register Firebase notification!");
            } else {
                String token = FirebaseInstanceId.getInstance().getToken();
                DebugLog.d(TAG, "FirebaseInstanceId.getInstance().getToken(), token: " + token);
                if (!"".equals(token) && token != null) {
                    firebaseToken = token;
                }
            }
        } catch (IllegalStateException e) {
            DebugLog.e(TAG, "FirebaseApp IllegalStateException!");
        }
        DebugLog.d(TAG, "Firebase InstanceID Token: " + firebaseToken);
        return firebaseToken;
    }

    public static void firebaseSubscribeTopic(Context context, String topic) {
        DebugLog.i(TAG, "firebaseSubscribeTopic ==> " + topic);
        FirebaseMessaging.getInstance().subscribeToTopic(topic);
    }

    public static void firebaseUnsubscribeTopic(Context context, String topic) {
        DebugLog.i(TAG, "firebaseUnsubscribeTopic ==> " + topic);
        FirebaseMessaging.getInstance().unsubscribeFromTopic(topic);
    }

    public static void firebaseLogEvent(Context context, String eventName, Map<String, String> eventValues) {
        DebugLog.i(TAG, "firebaseLogEvent ==> " + eventName);
        Bundle parameters = new Bundle();
        for (String key : eventValues.keySet()) {
            String value = eventValues.get(key);
            DebugLog.d(TAG, "    " + key + ": " + value);
            parameters.putString(key, value);
        }
        FirebaseAnalytics.getInstance(context).logEvent(eventName, parameters);
    }

    public static String getFacebookUserID(Context context) {
        String fbUserID = "";
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        if (accessToken != null) {
            fbUserID = accessToken.getUserId();
        }
        DebugLog.d(TAG, "Facebook UserID: " + fbUserID);
        return fbUserID;
    }

    public static void facebookLogEvent(Context context, String eventName, Map<String, String> eventValues) {
        DebugLog.i(TAG, "facebookLogEvent ==> " + eventName);
        AppEventsLogger logger = AppEventsLogger.newLogger(context);
        Bundle parameters = new Bundle();
        for (String key : eventValues.keySet()) {
            String value = eventValues.get(key);
            DebugLog.d(TAG, "    " + key + ": " + value);
            parameters.putString(key, value);
        }
        logger.logEvent(eventName, parameters);
    }

    public static void facebookLogPurchaseEvent(Context context, String productName, String expense, String currency, Map<String, String> eventValues) {
        DebugLog.i(TAG, "facebookLogPurchaseEvent ==> " + productName);
        AppEventsLogger logger = AppEventsLogger.newLogger(context);
        Bundle parameters = new Bundle();
        for (String key : eventValues.keySet()) {
            String value = eventValues.get(key);
            DebugLog.d(TAG, "    " + key + ": " + value);
            parameters.putString(key, value);
        }
        logger.logPurchase(new BigDecimal(expense), Currency.getInstance(currency), parameters);
    }

    public static String getAppsFlyerDeviceID(Context context) {
        String appsFlyerUID = AppsFlyerLib.getInstance().getAppsFlyerUID(context);
        DebugLog.d(TAG, "AppsFlyer DeviceID: " + appsFlyerUID);
        return appsFlyerUID;
    }
}
