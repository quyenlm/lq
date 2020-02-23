package com.xiaomi.boostersdk;

import android.content.Context;
import android.util.Log;
import org.json.JSONObject;

public class GameBoosterProxy {
    private static final String RESULT = "result";
    private static final String TAG = "GameBoosterProxy";
    private static boolean mHasRegisterThermalListener = false;

    public static void applyHardwareResource(String str) {
        if (!GameHandleController.checkIfSupportGameBooster()) {
            Log.e(TAG, "the system dont support the gamebooster");
            return;
        }
        try {
            ReflectUtil.callStaticObjectMethod(Class.forName("com.xiaomi.joyose.JoyoseManager"), "handleGameBoosterForOneway", new Class[]{Integer.TYPE, String.class}, 2, str);
        } catch (Exception e) {
            Log.e(TAG, "the call throw an exception " + e.toString());
        }
    }

    public static boolean checkIfSupportGameBooster() {
        Boolean bool;
        if (!GameHandleController.checkIfSupportGameBooster()) {
            Log.e(TAG, "the system dont support the gamebooster");
            return false;
        }
        try {
            String str = (String) ReflectUtil.callStaticObjectMethod(Class.forName("com.xiaomi.joyose.JoyoseManager"), "handleGameBoosterForSync", new Class[]{Integer.TYPE, String.class}, 1001, null);
            if (str != null) {
                JSONObject jSONObject = new JSONObject(str);
                if (jSONObject == null) {
                    Log.e(TAG, "the cloud config is null");
                    return false;
                }
                bool = Boolean.valueOf(jSONObject.optBoolean("result", false));
                return bool.booleanValue();
            }
        } catch (Exception e) {
            Log.e(TAG, "the call throw an exception " + e.toString());
        }
        bool = false;
        return bool.booleanValue();
    }

    public static void handleRawCommand(String str) {
        if (!GameHandleController.checkIfSupportGameBooster()) {
            Log.e(TAG, "the system dont support the gamebooster");
            return;
        }
        try {
            ReflectUtil.callStaticObjectMethod(Class.forName("com.xiaomi.joyose.JoyoseManager"), "handleGameBoosterForOneway", new Class[]{Integer.TYPE, String.class}, 100, str);
        } catch (Exception e) {
            Log.e(TAG, "the call throw an exception " + e.toString());
        }
    }

    public static void registerThermalControlListener(Context context, GameBoosterEngineCallback gameBoosterEngineCallback) {
        GameHandleController.registerThermalControlListener(context, gameBoosterEngineCallback);
    }

    public static void unregisterThermalControlListener(Context context, GameBoosterEngineCallback gameBoosterEngineCallback) {
        GameHandleController.unregisterThermalControlListener(context, gameBoosterEngineCallback);
    }

    public static void updateGameInfo(String str) {
        if (!GameHandleController.checkIfSupportGameBooster()) {
            Log.e(TAG, "the system dont support the gamebooster");
            return;
        }
        try {
            ReflectUtil.callStaticObjectMethod(Class.forName("com.xiaomi.joyose.JoyoseManager"), "handleGameBoosterForOneway", new Class[]{Integer.TYPE, String.class}, 1, str);
        } catch (Exception e) {
            Log.e(TAG, "the call throw an exception " + e.toString());
        }
    }
}
