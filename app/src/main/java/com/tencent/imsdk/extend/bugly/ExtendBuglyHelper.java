package com.tencent.imsdk.extend.bugly;

import android.content.Context;
import com.tencent.imsdk.extend.bugly.api.IMSDKExtendBugly;
import com.tencent.imsdk.tool.etc.IMLogger;
import com.unity3d.player.UnityPlayer;

public class ExtendBuglyHelper extends IMSDKExtendBugly {
    public static volatile Context currentContext = null;

    public static void initialize() {
        IMLogger.d("bugly extend initialize");
        try {
            currentContext = UnityPlayer.currentActivity;
            initialize(currentContext);
        } catch (Exception e) {
            IMLogger.e("initialize get error : " + e.getMessage());
        }
    }

    public static void unitySetLogCache(int byteSize) {
        IMLogger.d("android helper setlogcache");
        setLogCache(byteSize);
    }

    public static void unityBuglyLog(int level, String tag, String log) {
        IMLogger.d("unity buglylog");
        buglyLog(level, tag, log);
    }

    public static void unityBuglyPutUserData(String key, String value) {
        IMLogger.d("unity Bugly put user data");
        buglyPutUserData(key, value);
    }

    public static void unitySetBuglyAppVersion(String version) {
        IMLogger.d("unity set Bugly App Version");
        setBuglyAppVersion(version);
    }
}
