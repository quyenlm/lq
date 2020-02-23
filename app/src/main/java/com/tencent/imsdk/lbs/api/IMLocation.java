package com.tencent.imsdk.lbs.api;

import android.content.Context;
import com.tencent.imsdk.IMCallback;
import com.tencent.imsdk.IMConfig;
import com.tencent.imsdk.lbs.base.IMLocationResult;

public class IMLocation {
    private static IMLocationManager locationManager = new IMLocationManager();

    public static boolean initialize(Context mContext) {
        IMConfig.initialize(mContext);
        return locationManager.initialize(mContext);
    }

    public static void setChannel(String className) {
        locationManager.setChannel(className);
    }

    public static boolean requestLocationInfo(boolean immediate) {
        return locationManager.requestLocationInfo(immediate);
    }

    public static void setLocationListener(IMCallback<IMLocationResult> imCallback) {
        locationManager.setLocationListener(imCallback);
    }

    public static void setLanguageTag(int languageTag) {
        locationManager.setLanguageTag(languageTag);
    }

    public static void enableGPS(boolean enableGPS) {
        locationManager.enableGPS(enableGPS);
    }
}
