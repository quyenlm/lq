package com.tencent.imsdk.unity.garena;

import android.app.Activity;
import android.content.Context;
import com.tencent.imsdk.extend.garena.api.IMSDKExtendGarena;
import com.tencent.imsdk.tool.etc.IMLogger;
import com.unity3d.player.UnityPlayer;

public class GarenaHelper extends IMSDKExtendGarena {
    protected static Context currentContext;
    private static volatile String unityGameObject = "Tencent.iMSDK.IMGarena";

    public static boolean initialize() {
        currentContext = UnityPlayer.currentActivity;
        try {
            initialize((Activity) currentContext);
        } catch (Exception e) {
            IMLogger.e("initialize caught exception : " + e.getMessage());
        }
        return currentContext != null;
    }
}
