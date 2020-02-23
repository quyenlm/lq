package com.tencent.imsdk.sns.api;

import com.tencent.imsdk.sns.base.IUserState;
import com.tencent.imsdk.tool.etc.IMLogger;

public class IMUserState {
    private static final String DEFAULT_CHANNEL = "Viber";
    private static String mChannel = null;
    private static IUserState userState = null;

    public static void initialize() {
        setChannel(DEFAULT_CHANNEL);
    }

    public static void setChannel(String channel) {
        mChannel = channel;
    }

    public static boolean activatePlayingReport(String extraJson) {
        if (mChannel == null) {
            IMLogger.e("Need setChannel first , Current channel = " + mChannel);
            return false;
        }
        try {
            userState = (IUserState) IMLogin.getInstance(mChannel);
            if (userState == null) {
                return false;
            }
            userState.activatePlayingReport(extraJson);
            return true;
        } catch (ClassCastException e) {
            IMLogger.e(mChannel + " have not implements IUserState");
            return false;
        }
    }

    public static void deactivatePlayingReport() {
        if (userState != null) {
            userState.deactivatePlayingReport();
        }
    }
}
