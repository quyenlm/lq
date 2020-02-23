package com.tencent.imsdk.sns.innerapi;

import android.content.Context;
import android.support.v4.media.session.PlaybackStateCompat;
import com.tencent.imsdk.IMConfig;
import com.tencent.imsdk.IMVersion;
import com.tencent.imsdk.sns.api.IMLogin;
import com.tencent.imsdk.sns.base.IMLoginBase;
import com.tencent.imsdk.sns.base.IMLoginResult;
import com.tencent.imsdk.stat.innerapi.IMInnerStat;
import com.tencent.imsdk.tool.etc.DeviceInfoUtils;
import com.tencent.imsdk.tool.etc.IMHardwareInfo;
import com.tencent.imsdk.tool.etc.IMLogger;
import com.tencent.imsdk.tool.json.JsonSerializable;
import com.tencent.midas.oversea.api.UnityPayHelper;
import java.util.Properties;

public class IMLoginStat {
    private static final int MAX_LOGIN_RESULT_REPORT = 5;
    static Context currentContext;
    static boolean firstLogin = false;
    public static boolean isReporting = false;
    static String lastOpenId;
    private static IMLoginResult mLastLoginResult;
    private static int mLoginReportTimes = 0;
    static boolean reported = false;

    public static void initialize(Context context) {
        if (context != null && currentContext != context) {
            currentContext = context;
            IMConfig.initialize(currentContext);
        }
    }

    public static void setFirstLogin(boolean flag) {
        firstLogin = flag;
    }

    public static void reset() {
        reported = false;
    }

    static String getOpenId() {
        IMLoginResult loginResult = IMLogin.getLoginResult();
        if (loginResult == null || loginResult.retCode != 1) {
            IMLogger.e("get login result error, need login first");
            return null;
        } else if (loginResult.openId != null && loginResult.openId.length() > 0) {
            return loginResult.openId;
        } else {
            IMLogger.e("open id error");
            return null;
        }
    }

    static void report() {
        if (getOpenId() != null && !getOpenId().equals(lastOpenId)) {
            lastOpenId = getOpenId();
            reset();
        }
        Properties properties = new Properties();
        properties.setProperty("platform", "Android");
        int gameId = IMConfig.getGameId();
        if (gameId <= 0) {
            IMLogger.e("game id configure error !");
            return;
        }
        properties.setProperty("gameId", String.valueOf(gameId));
        String channel = IMLogin.getChannel();
        if (channel == null || channel.length() <= 0) {
            IMLogger.e("need call setChannel first !");
            return;
        }
        try {
            IMLoginBase instance = IMLogin.getInstance(channel);
            if (instance == null) {
                IMLogger.e("can not channel " + channel + " instance !");
                return;
            }
            int channelId = instance.getChannelId();
            if (channelId <= 0) {
                IMLogger.e("channel " + channel + " id error : " + channelId);
                return;
            }
            properties.setProperty("channelId", String.valueOf(channelId));
            String openId = getOpenId();
            if (openId == null) {
                IMLogger.e("open id error");
                return;
            }
            properties.setProperty(UnityPayHelper.OPENID, openId);
            DeviceInfoUtils.IMDeviceInfo deviceInfo = DeviceInfoUtils.getInfo(currentContext);
            if (deviceInfo == null) {
                IMLogger.e("cannot get info");
                return;
            }
            properties.setProperty("source", String.valueOf(deviceInfo.packageChannelId));
            properties.setProperty("sysVersion", deviceInfo.osVersion);
            properties.setProperty("deviceId", deviceInfo.seriesId);
            properties.setProperty("brand", deviceInfo.brand);
            properties.setProperty("deviceModel", deviceInfo.model);
            properties.setProperty("cpuModel", IMHardwareInfo.getCpuName());
            properties.setProperty("deviceRAM", IMHardwareInfo.getTotalMemory());
            properties.setProperty("deviceROM", String.valueOf(IMHardwareInfo.getTotalInternalMemorySize() / PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID) + " kB");
            String apn = deviceInfo.apn;
            if ((apn == null || apn.length() <= 0) && deviceInfo.network != null && deviceInfo.network.length() > 0) {
                apn = "wifi";
            }
            properties.setProperty("deviceApn", apn);
            properties.setProperty("appVersion", deviceInfo.appVersionName);
            properties.setProperty("imsdkVersion", IMVersion.getVersion());
            if (firstLogin) {
                properties.setProperty("isFirstLogin", "true");
            } else {
                properties.setProperty("isFirstLogin", "false");
            }
            IMInnerStat iMInnerStat = new IMInnerStat(currentContext, IMVersion.getVersion());
            iMInnerStat.setDebuggable(false);
            iMInnerStat.reportLogin(properties);
            firstLogin = false;
            reported = true;
            isReporting = false;
        } catch (Exception e) {
            IMLogger.d("get channel id error : " + e.getMessage());
        }
    }

    public static void reportLogin() {
        if (!reported && !isReporting) {
            isReporting = true;
            report();
        }
    }

    public static void reportLoginResult(IMLoginResult loginResult) {
        try {
            reportLogin();
            if (loginResult != null && loginResult.retCode == 1) {
                if (mLastLoginResult != null) {
                    try {
                        if (mLastLoginResult.toUnityString().equals(loginResult.toUnityString())) {
                            return;
                        }
                    } catch (Exception e) {
                        IMLogger.w("compare with last login result failed : " + e.getMessage());
                        return;
                    }
                }
                if (mLoginReportTimes < 5) {
                    mLastLoginResult = loginResult;
                    new IMInnerStat(currentContext, IMVersion.getVersion()).reportEvent("imsdk_login", false, "getLoginResult", "function end ", "success", loginResult.openId, IMInnerStat.convertProperties((JsonSerializable) loginResult), true);
                    mLoginReportTimes++;
                }
            }
        } catch (Exception e2) {
            IMLogger.w("login report failed: " + e2.getMessage());
        }
    }
}
