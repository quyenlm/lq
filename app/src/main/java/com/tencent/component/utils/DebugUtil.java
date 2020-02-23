package com.tencent.component.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import com.tencent.component.ComponentContext;

public class DebugUtil {
    private static Boolean sDebuggable;

    public static boolean isDebuggable(Context context) {
        if (sDebuggable == null) {
            ApplicationInfo appInfo = context.getApplicationInfo();
            sDebuggable = Boolean.valueOf((appInfo == null || (appInfo.flags & 2) == 0) ? false : true);
        }
        return sDebuggable.booleanValue();
    }

    public static void setDebuggable(boolean debuggable) {
        sDebuggable = Boolean.valueOf(debuggable);
    }

    private DebugUtil() {
    }

    public static boolean isDebuggable() {
        return isDebuggable(ComponentContext.getContext());
    }
}
