package com.xiaomi.boostersdk;

public interface GameBoosterEngineCallback {
    public static final int THERMAL_CONTROL_LEVEL_HIGH = 2;
    public static final int THERMAL_CONTROL_LEVEL_MIDDLE = 1;
    public static final int THERMAL_CONTROL_LEVEL_NORMAL = 0;

    void onThermalControlChanged(int i);
}
