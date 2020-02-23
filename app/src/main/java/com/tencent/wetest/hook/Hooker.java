package com.tencent.wetest.hook;

import android.util.Log;
import com.tencent.wetest.Robot;

public class Hooker {
    public static String TAG = "wetest";

    public static void main() {
        Log.i(TAG, "===>>enter injector!<<===");
        Robot.startAutomation();
        Log.i(TAG, "===>>inject finish!<<===");
    }
}
