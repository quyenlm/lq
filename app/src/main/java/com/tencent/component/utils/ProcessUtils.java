package com.tencent.component.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Process;
import java.util.List;

public class ProcessUtils {
    private static volatile String sProcessName;

    public static String myProcessName(Context context) {
        if (sProcessName != null) {
            return sProcessName;
        }
        synchronized (ProcessUtils.class) {
            if (sProcessName != null) {
                String str = sProcessName;
                return str;
            }
            String obtainProcessName = obtainProcessName(context);
            sProcessName = obtainProcessName;
            return obtainProcessName;
        }
    }

    public static boolean isMainProcess(Context context) {
        String processName = myProcessName(context);
        return processName != null && processName.equals(context.getApplicationInfo().processName);
    }

    private static String obtainProcessName(Context context) {
        int pid = Process.myPid();
        List<ActivityManager.RunningAppProcessInfo> listTaskInfo = ((ActivityManager) context.getSystemService("activity")).getRunningAppProcesses();
        if (listTaskInfo != null && listTaskInfo.size() > 0) {
            for (ActivityManager.RunningAppProcessInfo proc : listTaskInfo) {
                if (proc != null && proc.pid == pid) {
                    return proc.processName;
                }
            }
        }
        return null;
    }

    private ProcessUtils() {
    }
}
