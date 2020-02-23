package com.xiaomi.boostersdk;

import android.content.Context;

public class GameBoosterManager {
    public static void applyHardwareResource(String str) {
        GameBoosterProxy.applyHardwareResource(str);
    }

    public static boolean checkIfSupportGameBooster() {
        return GameBoosterProxy.checkIfSupportGameBooster();
    }

    public static void handleRawCommand(String str) {
        GameBoosterProxy.handleRawCommand(str);
    }

    public static void registerThermalControlListener(Context context, GameBoosterEngineCallback gameBoosterEngineCallback) {
        GameBoosterProxy.registerThermalControlListener(context, gameBoosterEngineCallback);
    }

    public static void unregisterThermalControlListener(Context context, GameBoosterEngineCallback gameBoosterEngineCallback) {
        GameBoosterProxy.unregisterThermalControlListener(context, gameBoosterEngineCallback);
    }

    public static void updateGameInfo(String str) {
        GameBoosterProxy.updateGameInfo(str);
    }
}
