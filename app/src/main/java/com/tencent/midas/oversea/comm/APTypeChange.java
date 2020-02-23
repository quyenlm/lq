package com.tencent.midas.oversea.comm;

public class APTypeChange {
    public static float StringToFloat(String str) {
        try {
            return Float.parseFloat(str);
        } catch (Exception e) {
            e.printStackTrace();
            return 0.0f;
        }
    }

    public static int StringToInt(String str) {
        try {
            return Integer.parseInt(str);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}
