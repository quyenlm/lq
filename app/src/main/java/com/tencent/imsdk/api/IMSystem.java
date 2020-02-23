package com.tencent.imsdk.api;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.HandlerThread;
import android.os.Looper;
import com.tencent.component.net.download.multiplex.download.extension.FileUtils;
import com.tencent.imsdk.IMConfig;
import com.tencent.imsdk.IMVersion;
import com.tencent.imsdk.framework.config.Config;
import com.tencent.imsdk.framework.config.ConfigManager;
import com.tencent.imsdk.framework.request.IMSDKServer;
import com.tencent.imsdk.stat.api.IMStat;
import com.tencent.imsdk.tool.etc.ApkExternalInfoTool;
import com.tencent.imsdk.tool.etc.IMLogger;
import com.tencent.imsdk.tool.etc.T;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class IMSystem {
    public static final String DEFAULT_CHANNEL = "00000000";
    public static final String IMSDK_VERSION = IMVersion.getVersion();
    public static final int LOOPER_TYPE_BG = 1;
    public static final int LOOPER_TYPE_UI = 0;
    private static volatile IMSystem instance = null;
    private Activity mActivity = null;
    private Context mApplicationContext = null;
    private HandlerThread mHandlerThread = null;
    private String mIMSDKVersion = "";
    private long mPauseTime = 0;

    public static IMSystem getInstance() {
        if (instance == null) {
            synchronized (IMSystem.class) {
                if (instance == null) {
                    instance = new IMSystem();
                }
            }
        }
        return instance;
    }

    private IMSystem() {
    }

    public void onCreate(Activity activity) {
        IMLogger.d("onCreate start");
        this.mActivity = activity;
        this.mApplicationContext = activity.getApplicationContext();
        IMConfig.initialize(activity);
        this.mHandlerThread = new HandlerThread("IMSDK");
        this.mHandlerThread.start();
        IMSDKServer.getInstance().onCreate(activity);
        IMLogger.d("onCreate end");
    }

    public void onDestroy(Activity activity) {
        this.mActivity = null;
        IMStat.reportEvent("ExitGame", (HashMap<String, String>) null, false);
    }

    public void onPause(Activity activity) {
    }

    public void onStop(Activity activity) {
    }

    public void onRestart(Activity activity) {
    }

    public void onResume(Activity activity) {
    }

    public void onNewIntent(Intent intent) {
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
    }

    public boolean IsDifferentActivity(Activity activity) {
        return this.mActivity != null && !this.mActivity.equals(activity);
    }

    public String getVersion() {
        this.mIMSDKVersion = IMVersion.getVersion();
        return this.mIMSDKVersion;
    }

    public Looper getLooper(int type) {
        if (type == 0) {
            return Looper.getMainLooper();
        }
        if (type == 1) {
            return this.mHandlerThread.getLooper();
        }
        return null;
    }

    public Activity getActivity() {
        return this.mActivity;
    }

    public Context getApplicationContext() {
        return this.mApplicationContext;
    }

    public String getChannelId(Activity activity) {
        try {
            String comment = ApkExternalInfoTool.readChannelId(new File(activity.getPackageCodePath()));
            IMLogger.d("Comment: " + comment);
            String channel = comment;
            if (!T.ckIsEmpty(channel)) {
                String str = channel;
                return channel;
            }
        } catch (IOException e) {
            e.printStackTrace();
            IMLogger.e("Read apk file for channelId Error");
        }
        String channel2 = ConfigManager.readValueByKey(getApplicationContext(), FileUtils.FILE_CHANNEL, Config.KEY_CHANNEL_ID);
        if (!T.ckIsEmpty(channel2)) {
            String str2 = channel2;
            return channel2;
        }
        IMLogger.e("channel in assert is null");
        String str3 = channel2;
        return DEFAULT_CHANNEL;
    }

    public long getPauseTime() {
        return this.mPauseTime;
    }

    public String getQQAppId() {
        return "";
    }

    public String getQQAppKey() {
        return "";
    }

    public String getStatIMEI() {
        return IMStat.getStatIMEI();
    }
}
