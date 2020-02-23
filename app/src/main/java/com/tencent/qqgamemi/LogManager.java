package com.tencent.qqgamemi;

import android.util.Log;
import com.tencent.component.utils.log.LogUtil;

public class LogManager {
    private static String TAG = "LogManager";

    public static void setLogTraceLevel(int level) {
        Log.i(TAG, "setLogTraceLevel:" + level);
        LogUtil.setLogcatEnable(false);
        if (level == 0) {
            LogUtil.setFileLogEnable(false);
            return;
        }
        LogUtil.setFileLogEnable(true);
        int traceLevel = getTraceLevel(level);
        LogUtil.setTraceLevel(traceLevel);
        LogUtil.i(TAG, "setTraceLevel：" + traceLevel);
    }

    private static int getTraceLevel(int level) {
        switch (level) {
            case 1:
                return 48;
            case 2:
                return 56;
            case 3:
                return 60;
            case 4:
                return 62;
            case 5:
                return 63;
            default:
                return 32;
        }
    }
}
