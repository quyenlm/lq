package com.xiaomi.boostersdk;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

public class GameHandleController {
    private static final String KEY_MIUI_INTERNAL_STORAGE = "ro.miui.internal.storage";
    private static final String KEY_MIUI_VERSION_CODE = "ro.miui.ui.version.code";
    private static final String KEY_MIUI_VERSION_NAME = "ro.miui.ui.version.name";
    private static final String SERVICE_NAME = "miui.whetstone";
    /* access modifiers changed from: private */
    public static boolean mHasRegisterThermalListener = false;
    private static boolean mSetting;
    private static BroadcastReceiver mThermalControlReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            if (intent != null && Constants.ACTION_THERMAL_CONTROL_CHANGE.equals(intent.getAction())) {
                int intExtra = intent.getIntExtra(Constants.INTENT_EXTRA_CUR_THERMAL_LEVEL, 0);
                if (GameHandleController.sCallBack != null && GameHandleController.mHasRegisterThermalListener) {
                    GameHandleController.sCallBack.onThermalControlChanged(intExtra);
                }
            }
        }
    };
    /* access modifiers changed from: private */
    public static GameBoosterEngineCallback sCallBack;
    private static boolean sSupport;

    public static boolean checkIfSupportGameBooster() {
        if (mSetting) {
            return mSetting;
        }
        if (SystemProperties.get(KEY_MIUI_VERSION_CODE, (String) null) == null && SystemProperties.get(KEY_MIUI_VERSION_NAME, (String) null) == null && SystemProperties.get(KEY_MIUI_INTERNAL_STORAGE, (String) null) == null) {
            sSupport = false;
        } else {
            sSupport = true;
        }
        mSetting = true;
        return sSupport;
    }

    public static void registerThermalControlListener(Context context, GameBoosterEngineCallback gameBoosterEngineCallback) {
        if (!mHasRegisterThermalListener && checkIfSupportGameBooster()) {
            context.registerReceiver(mThermalControlReceiver, new IntentFilter(Constants.ACTION_THERMAL_CONTROL_CHANGE));
            sCallBack = gameBoosterEngineCallback;
            mHasRegisterThermalListener = true;
        }
    }

    public static void unregisterThermalControlListener(Context context, GameBoosterEngineCallback gameBoosterEngineCallback) {
        if (mHasRegisterThermalListener) {
            context.unregisterReceiver(mThermalControlReceiver);
            mHasRegisterThermalListener = false;
        }
    }
}
