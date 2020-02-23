package com.tencent.imsdk.statics.appsflyer;

import android.content.Context;
import com.tencent.imsdk.tool.etc.IMLogger;

@Deprecated
public class ExtendAppsFlyer {
    public static void onCreate(Context context) {
        IMLogger.w("ExtendAppsFlyer.onCreate(Context context) function has been deprecated, please use AppsFlyerStatHelper.getInstance().initialize(Context context) instead...");
        AppsFlyerStatHelper.getInstance().initialize(context);
    }
}
