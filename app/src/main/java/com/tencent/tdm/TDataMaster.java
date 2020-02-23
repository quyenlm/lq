package com.tencent.tdm;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import com.tencent.tdm.Utils.TUtils;
import com.tencent.tdm.system.TX;
import com.tencent.tdm.system.TXLog;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

public class TDataMaster {
    private static TDataMaster Instance = new TDataMaster();
    private static boolean isInitialized = false;
    private static char lifecycle = 0;
    private static Application mApplication = null;
    private static final String tag = "TDataMaster";

    private native void TDMEnableReport(boolean z);

    private native boolean TDMEncryptInit(String str);

    private native String TDMGetSDKVerision();

    private native String TDMGetUID();

    private native void TDMInit();

    private native void TDMPause();

    private native void TDMReportBinary(String str, byte[] bArr, int i, int i2);

    private native void TDMReportEvent(String str, Map<String, String> map, int i);

    private native void TDMResume();

    private native void TDMSetAppId(String str);

    private native void TDMSetLogLevel(int i);

    static {
        System.loadLibrary(tag);
    }

    private TDataMaster() {
        TXLog.i(tag, "TDataMaster Construct");
    }

    public static TDataMaster getInstance() {
        return Instance;
    }

    public static Application getApplication() {
        if (mApplication == null) {
            try {
                Class<?> activityThreadClass = Class.forName("android.app.ActivityThread");
                mApplication = (Application) activityThreadClass.getMethod("getApplication", new Class[0]).invoke(activityThreadClass.getMethod("currentActivityThread", new Class[0]).invoke((Object) null, (Object[]) null), (Object[]) null);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e2) {
                e2.printStackTrace();
            } catch (IllegalAccessException e3) {
                e3.printStackTrace();
            } catch (IllegalArgumentException e4) {
                e4.printStackTrace();
            } catch (InvocationTargetException e5) {
                e5.printStackTrace();
            }
        }
        return mApplication;
    }

    public boolean initialize() {
        return initialize(getApplication());
    }

    public boolean initialize(Context context) {
        int logLevel = TXLog.getLogLevel();
        if (logLevel <= 1) {
            TXLog.w(tag, "now Android logLevel is " + logLevel + ", please use warning or error level in production");
        }
        TXLog.i(tag, "TDataMaster initialize(onCreate), isInitialized: " + isInitialized);
        if (!isInitialized) {
            String jsonFilePath = context.getFilesDir() + "/encrypt.json";
            TUtils.copyAssertsToDest(context, "encrypt.json", jsonFilePath);
            TXLog.i(tag, "TDataMaster TDMEncryptInit res: " + TDMEncryptInit(jsonFilePath));
            TX.GetInstance().Initialize(context.getApplicationContext());
            TDMInit();
            isInitialized = true;
        }
        TX.GetInstance().RegisterReceiver();
        return isInitialized;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        TXLog.i(tag, "OnActivityResult requestCode:" + requestCode + " resultCode:" + resultCode);
    }

    public void onStart() {
        TXLog.i(tag, "OnStart");
        lifecycle = 2;
    }

    public void onResume() {
        TXLog.i(tag, "onResume");
        if (lifecycle != 4) {
            TDMResume();
        }
        lifecycle = 4;
    }

    public void onPause() {
        TXLog.i(tag, "onPause");
        if (lifecycle != 8) {
            TDMPause();
        }
        lifecycle = 8;
    }

    public void onStop() {
        TXLog.i(tag, "OnStop");
        lifecycle = 16;
    }

    public void onDestroy() {
        TXLog.i(tag, "onDestroy");
        TX.GetInstance().UnregisterReceiver();
        lifecycle = ' ';
    }

    public void onRestart() {
        TXLog.i(tag, "OnRestart");
        lifecycle = '@';
    }

    public String getTDMUID() {
        TXLog.d(tag, "getTDMUID");
        return TDMGetUID();
    }

    public void enableReport(boolean enable) {
        TXLog.d(tag, "enableReport");
        TDMEnableReport(enable);
    }

    public void setLogLevel(int level) {
        System.out.println("SetLogLevel: " + level);
        TX.GetInstance().SetLogLevel(level);
        TDMSetLogLevel(level);
    }

    public void setAppId(String appid) {
        TXLog.d(tag, "setAppId: " + appid);
        TDMSetAppId(appid);
    }

    public String getTDMSDKVersion() {
        TXLog.d(tag, "getTDMSDKVersion");
        return TDMGetSDKVerision();
    }

    public void reportEvent(int srcID, String eventName, Map<String, String> eventInfo) {
        TXLog.d(tag, "eventName: " + eventName);
        TXLog.d(tag, "srcID: " + srcID);
        if (eventInfo == null || eventInfo.isEmpty()) {
            TXLog.e(tag, "eventInfo is null or empty");
        } else {
            TDMReportEvent(eventName, eventInfo, srcID);
        }
    }

    public void reportBinary(int srcID, String eventName, byte[] data, int datalen) {
        TXLog.d(tag, "eventName: " + eventName);
        TXLog.d(tag, "srcID: " + srcID);
        if (data == null) {
            TXLog.e(tag, "data is null");
        } else {
            TDMReportBinary(eventName, data, datalen, srcID);
        }
    }
}
