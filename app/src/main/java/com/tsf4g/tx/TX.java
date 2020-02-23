package com.tsf4g.tx;

import android.app.Activity;
import android.content.Context;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import com.appsflyer.share.Constants;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.tencent.abase.URLRequest;
import java.io.File;

public class TX {
    public static TX Instance = new TX();
    private final String CONFIG_FILENAME = "ApolloSetting.ini";
    NetworkStateChecker NetChecker = new NetworkStateChecker();
    private Handler mHandler = null;
    private boolean m_bFirstLaunch = false;
    private Context m_cntxt;
    SolidConfigReader m_scReader = new SolidConfigReader();
    private String m_szBundleId = null;
    private String m_szCurrentAPN = null;
    private String m_szGameVersion = null;
    private String m_szICCIDInfo = null;
    private double m_szLatitude = FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE;
    private String m_szLocalIPAddress = null;
    private double m_szLongitude = FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE;
    private String m_szModel = null;
    private int m_szSignalStrength = 0;
    private boolean m_szSysRooted = false;
    private String m_szSysVersion = null;
    private String m_szUdid = null;
    TXPaths paths = new TXPaths();
    /* access modifiers changed from: private */
    public Thread savedMainThread = null;
    TXSystem xsystem = new TXSystem();

    static {
        System.loadLibrary("apollo");
    }

    private TX() {
    }

    private int CheckPermission(int i) {
        return this.xsystem.CheckPermission(this.m_cntxt, i);
    }

    private void CreateMainHandler() {
        this.mHandler = new Handler() {
            public void handleMessage(Message message) {
                if (Thread.currentThread() == TX.this.savedMainThread) {
                    TXLog.d("TX", "Main Thread:" + TX.this.savedMainThread + "Current Thread:" + Thread.currentThread());
                } else {
                    TXLog.d("TX", "Main Thread:" + TX.this.savedMainThread + "Current Thread:" + Thread.currentThread());
                }
                TX.this.callJNIperform(Long.parseLong(message.obj.toString()));
                TX.this.wakeup();
            }
        };
    }

    private void TXcallJNIperform(long j) {
        callJNIperform(j);
    }

    private void cacheSystemInfo(Context context) {
        this.m_szUdid = this.xsystem.GetUdid(context);
        this.m_szBundleId = this.xsystem.GetBundleId(context);
        this.m_szModel = this.xsystem.GetModel();
        this.m_szSysVersion = this.xsystem.GetSysVersion();
        this.m_szGameVersion = this.xsystem.GetGameVersion(context);
        this.m_szSysRooted = this.xsystem.IsSysRooted();
        this.m_szICCIDInfo = this.xsystem.GetICCIDInfo(context);
        this.m_bFirstLaunch = this.xsystem.IsFirstLaunch(context);
    }

    private native void callJNIonTXCreate(TXPaths tXPaths);

    private native void callJNIonTest();

    /* access modifiers changed from: private */
    public native void callJNIperform(long j);

    private void callbackFromJNI(long j) {
        Message message = new Message();
        message.obj = Long.valueOf(j);
        if (Thread.currentThread() == this.savedMainThread) {
            TXLog.d("TX", "Main Thread:" + this.savedMainThread + "Current Thread:" + Thread.currentThread());
            callJNIperform(j);
            return;
        }
        TXLog.d("TX", "Main Thread:" + this.savedMainThread + "Current Thread:" + Thread.currentThread());
        TXLog.d("TX", "Send msg to MainThread");
        sendMsg(message, this.mHandler);
    }

    private int checkNetworkState() {
        return this.NetChecker.CheckNetworkState(this.m_cntxt);
    }

    private boolean getSolidConfigBool(String str, String str2, boolean z) {
        return this.m_scReader.GetBool(str, str2, z);
    }

    private int getSolidConfigInt(String str, String str2, int i) {
        return this.m_scReader.GetInt(str, str2, i);
    }

    private String getSolidConfigString(String str, String str2, String str3) {
        return this.m_scReader.GetString(str, str2, str3);
    }

    private synchronized void sendMsg(Message message, Handler handler) {
        this.mHandler.sendMessage(message);
        try {
            wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return;
    }

    /* access modifiers changed from: private */
    public synchronized void wakeup() {
        notify();
    }

    public void CalculateLocaiton() {
        this.m_szLatitude = this.xsystem.GetLatitude();
        this.m_szLongitude = this.xsystem.GetLongitude();
    }

    public void CheckCurrentAPN() {
        this.m_szCurrentAPN = this.xsystem.GetCurrentAPN(this.m_cntxt);
    }

    public void CheckLocalIPAddress() {
        this.m_szLocalIPAddress = this.xsystem.GetLocalIPAddress();
    }

    public void CheckSignalStrength() {
        this.m_szSignalStrength = this.xsystem.GetSignalStrength(this.m_cntxt);
    }

    public void Initialize(Activity activity) {
        TXLog.d("TX", "Initialize");
        this.m_cntxt = activity.getApplicationContext();
        getPaths(this.m_cntxt);
        this.savedMainThread = Thread.currentThread();
        CreateMainHandler();
        callJNIonTXCreate(this.paths);
        cacheSystemInfo(this.m_cntxt);
        this.m_scReader.Init(this.m_cntxt, "ApolloSetting.ini");
        URLRequest.nativeInit();
        TXLog.d("TX", "Initialize ends");
    }

    public native void NetworkStateChangeNotify(int i);

    /* access modifiers changed from: package-private */
    public void getPaths(Context context) {
        File filesDir = context.getFilesDir();
        File cacheDir = context.getCacheDir();
        this.paths.DataPath = filesDir.getAbsolutePath() + Constants.URL_PATH_DELIMITER;
        this.paths.CachePath = cacheDir.getAbsolutePath() + Constants.URL_PATH_DELIMITER;
        this.paths.AppPath = this.paths.CachePath.substring(0, this.paths.CachePath.lastIndexOf(Constants.URL_PATH_DELIMITER) + 1);
        if ("mounted".equals(Environment.getExternalStorageState())) {
            File externalCacheDir = context.getExternalCacheDir();
            if (externalCacheDir == null) {
                try {
                    externalCacheDir = new File(Environment.getExternalStorageDirectory().getPath() + ("/Android/data/" + context.getPackageName() + "/cache"));
                } catch (Exception e) {
                    TXLog.e("TX", "Create cache dir Error");
                    externalCacheDir = cacheDir;
                }
            }
            this.paths.CachePath = externalCacheDir.getAbsolutePath() + Constants.URL_PATH_DELIMITER;
        }
        TXLog.d("TX", "AppPath:" + this.paths.AppPath + "\nCachePath:" + this.paths.CachePath + "\nDataPath:" + this.paths.DataPath);
    }
}
