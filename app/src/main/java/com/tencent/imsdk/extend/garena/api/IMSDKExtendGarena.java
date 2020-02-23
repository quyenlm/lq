package com.tencent.imsdk.extend.garena.api;

import android.app.Activity;
import android.content.Intent;
import com.tencent.imsdk.extend.garena.base.ExtendGarenaManager;

public class IMSDKExtendGarena {
    public static void initialize(Activity context) {
        ExtendGarenaManager.getInstance().initialize(context);
    }

    public static void onActivityResult(int requestCode, int resultCode, Intent data) {
        ExtendGarenaManager.getInstance().onActivityResult(requestCode, resultCode, data);
    }

    public static void onPause() {
        ExtendGarenaManager.getInstance().onPause();
    }

    public static void onResume() {
        ExtendGarenaManager.getInstance().onResume();
    }

    public static void onDestroy() {
        ExtendGarenaManager.getInstance().onDestroy();
    }
}
