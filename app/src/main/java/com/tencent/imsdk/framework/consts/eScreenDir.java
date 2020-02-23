package com.tencent.imsdk.framework.consts;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import com.tencent.imsdk.tool.etc.IMLogger;

public enum eScreenDir {
    SENSOR(0),
    PORTRAIT(1),
    LANDSCAPE(2);
    
    int value;

    private eScreenDir(int val) {
        this.value = 0;
        this.value = val;
    }

    public static eScreenDir getEnum(int i) {
        switch (i) {
            case 0:
                return SENSOR;
            case 1:
                return PORTRAIT;
            case 2:
                return LANDSCAPE;
            default:
                IMLogger.w("bad screen dir :" + i);
                return SENSOR;
        }
    }

    public int val() {
        return this.value;
    }

    public static eScreenDir getEnum(ActivityInfo tempActivityInfo) {
        if (tempActivityInfo.screenOrientation == 0) {
            return LANDSCAPE;
        }
        if (tempActivityInfo.screenOrientation == 1) {
            return PORTRAIT;
        }
        return SENSOR;
    }

    public static eScreenDir getEnum(Configuration tempConfiguration) {
        if (tempConfiguration.orientation == 2) {
            return LANDSCAPE;
        }
        if (tempConfiguration.orientation == 1) {
            return PORTRAIT;
        }
        return SENSOR;
    }
}
