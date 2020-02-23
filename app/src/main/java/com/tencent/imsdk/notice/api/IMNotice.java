package com.tencent.imsdk.notice.api;

import android.content.Context;
import android.text.TextUtils;
import com.tencent.imsdk.IMConfig;
import com.tencent.imsdk.IMModules;
import com.tencent.imsdk.notice.base.IMNoticeBase;
import com.tencent.imsdk.tool.etc.IMLogger;

public class IMNotice {
    private static String currentChannel = "";
    private static Context currentContext = null;
    private static IMNoticeBase nInstance = null;

    private static boolean initialize() {
        if (currentContext == null) {
            return false;
        }
        IMConfig.initialize(currentContext);
        return true;
    }

    public static void initialize(Context context) {
        try {
            currentContext = context;
            IMConfig.initialize(currentContext);
        } catch (Exception e) {
            IMLogger.e(e.getMessage());
        }
    }

    public static String getChannel() {
        return currentChannel;
    }

    public static boolean setChannel(String channel) {
        if (TextUtils.isEmpty(channel)) {
            IMLogger.e("channel is null,please check channel value");
            return false;
        } else if (currentContext == null) {
            IMLogger.e("initialize should be called before set channel !");
            return false;
        } else {
            currentChannel = channel;
            nInstance = getInstance(currentChannel);
            if (nInstance != null) {
                nInstance.init(currentContext);
                return true;
            }
            IMLogger.e("get channel  " + currentChannel + " instance failed !");
            return false;
        }
    }

    private static IMNoticeBase getInstance(String channel) {
        currentChannel = channel;
        IMLogger.d("switch channel to : " + channel);
        String platformClass = "com.tencent.imsdk.notice." + currentChannel + "." + currentChannel.substring(0, 1).toUpperCase() + currentChannel.substring(1) + "Notice";
        IMLogger.d("try to get class : " + platformClass);
        IMNoticeBase instance = (IMNoticeBase) IMModules.getInstance().getModule(platformClass);
        if (instance != null) {
            instance.initialize(currentContext);
        } else {
            IMLogger.e("get class : " + platformClass + " failed !");
        }
        return instance;
    }

    public static void setListener(IMNoticeListener listener) {
        try {
            if (nInstance != null) {
                nInstance.setListener(listener);
            }
        } catch (Exception e) {
            IMLogger.e(e.getMessage());
        }
    }

    public static void showNotice(String noticeId, int noticeType, String scene, String extraJson) {
        try {
            if (nInstance != null) {
                nInstance.showNotice(noticeId, noticeType, scene, extraJson);
            }
        } catch (Exception e) {
            IMLogger.e(e.getMessage());
        }
    }

    public static void loadNoticeData(String noticeId, int loadDataType, String scene, int noticeType, String extraJson) {
        try {
            if (nInstance != null) {
                nInstance.loadNoticeData(noticeId, loadDataType, scene, noticeType, extraJson);
            }
        } catch (Exception e) {
            IMLogger.e(e.getMessage());
        }
    }

    public static void loadNoticeData(String version, String language, int region, int partition, boolean isUseCache, int noticeType, String extraJson) {
        try {
            if (nInstance != null) {
                nInstance.loadNoticeData(version, language, region, partition, isUseCache, noticeType, extraJson);
            }
        } catch (Exception ex) {
            IMLogger.e(ex.getMessage());
        }
    }

    public void setUserData(String target, String extraJson) {
        try {
            if (nInstance != null) {
                nInstance.setUserData(target, extraJson);
            }
        } catch (Exception e) {
            IMLogger.e(e.getMessage());
        }
    }

    public void syncUserDataToSvr(String extraJson) {
        try {
            if (nInstance != null) {
                nInstance.syncUserDataToSvr(extraJson);
            }
        } catch (Exception e) {
            IMLogger.e(e.getMessage());
        }
    }

    public void closeNotice(String noticeId, int closeType, String extraJson) {
        try {
            if (nInstance != null) {
                nInstance.closeNotice(noticeId, closeType, extraJson);
            }
        } catch (Exception e) {
            IMLogger.e(e.getMessage());
        }
    }
}
