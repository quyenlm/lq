package com.tencent.imsdk.stat.innerapi;

import android.content.Context;
import android.text.TextUtils;
import com.amazonaws.services.s3.internal.Constants;
import com.tencent.imsdk.IMConfig;
import com.tencent.imsdk.IMException;
import com.tencent.imsdk.sns.api.IMLogin;
import com.tencent.imsdk.tool.etc.CommonUtil;
import com.tencent.imsdk.tool.etc.DeviceInfoUtils;
import com.tencent.imsdk.tool.etc.IMLogger;
import com.tencent.imsdk.tool.etc.InstallationInfoUtil;
import com.tencent.imsdk.tool.etc.SessionIdFactory;
import com.tencent.imsdk.tool.etc.T;
import com.tencent.imsdk.tool.json.JsonSerializable;
import com.tencent.msdk.stat.StatConfig;
import com.tencent.msdk.stat.StatServiceImpl;
import com.tencent.msdk.stat.StatSpecifyReportedInfo;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class IMInnerStat {
    public static final String CANCEL = "cancel";
    public static final String END = "function end ";
    public static final String ERROR = "error";
    private static final String LOGIN_EVENT_REPORT_NAME = "imsdk_login_report";
    public static final String START = "function start ";
    public static final String SUCCESS = "success";
    public static final String UNKNOWN = "unknown result, no callback execute";
    private static volatile IMInnerStat innerStat;
    private static HashMap<String, String> mClzNameVer;
    private static Context mContext = null;
    private static Object mLock = new Object();
    private static String mSequenceID;
    private static ArrayList<StatEvent> mStatEventStack;
    private boolean isGettingOpenId = false;
    private boolean isNewProperties = true;
    private String mDeviceInfo;
    private String mDid;
    private String mGameId;
    private String mOpenId;
    private Properties mProperties = null;
    private String mSessionId;
    private StatSpecifyReportedInfo mSpecifyReportedInfo = null;
    private String mVersion;

    public IMInnerStat(Context context, String version) {
        initialize(context);
        if (this.mSpecifyReportedInfo != null) {
            this.mSpecifyReportedInfo.setVersion(version);
        }
        this.mVersion = version;
        this.mGameId = String.valueOf(IMConfig.getGameId());
        this.mDid = InstallationInfoUtil.getInstallationID(mContext);
        this.mDeviceInfo = DeviceInfoUtils.getModelAndVersion();
        this.mSessionId = SessionIdFactory.getInstance().getSessionId();
    }

    private IMInnerStat() {
    }

    @Deprecated
    public static IMInnerStat getInstance() {
        if (innerStat == null) {
            synchronized (mLock) {
                if (innerStat == null) {
                    innerStat = new IMInnerStat();
                    mClzNameVer = new HashMap<>();
                    mStatEventStack = new ArrayList<>();
                    IMInnerStat iMInnerStat = innerStat;
                    return iMInnerStat;
                }
            }
        }
        return innerStat;
    }

    static class StatEvent {
        public String mEventId;
        public String mEventResult;
        public String mEventStage;
        public long mEventStartTime;
        public String mEventType;
        public Properties properties;

        public StatEvent(String mEventId2, String mEventType2, String mEventStage2, String mEventResult2, long startTime, Properties properties2) {
            this.mEventId = mEventId2;
            this.mEventType = mEventType2;
            this.mEventStage = mEventStage2;
            this.mEventResult = mEventResult2;
            this.mEventStartTime = startTime;
            this.properties = properties2;
            IMLogger.d(" for codecc check  properties = " + properties2);
        }
    }

    public IMInnerStat setProperty(String key, String value) {
        if (this.isNewProperties && this.mProperties == null) {
            this.mProperties = new Properties();
            this.isNewProperties = false;
        }
        if (value == null) {
            value = "";
        }
        this.mProperties.setProperty(key, value);
        return this;
    }

    public Properties build() {
        return this.mProperties;
    }

    private static String getCurClzName(int trackDeep) {
        String result = "";
        StackTraceElement[] traces = Thread.currentThread().getStackTrace();
        if (trackDeep > 0 || trackDeep <= traces.length) {
            result = traces[trackDeep].getClassName();
            int dollar = result.lastIndexOf(36);
            if (dollar != -1) {
                result = result.substring(dollar + 1);
            } else {
                int dot = result.lastIndexOf(46);
                if (dot != -1) {
                    result = result.substring(dot + 1);
                }
            }
        }
        IMLogger.d("inner stat :" + result);
        return result;
    }

    public void initialize(Context context) {
        if (mContext == null || this.mSpecifyReportedInfo == null) {
            mContext = context;
            IMConfig.initialize(context);
            this.mSpecifyReportedInfo = new StatSpecifyReportedInfo();
            this.mSpecifyReportedInfo.setAppKey("AH3HVXV384J1");
            this.mSpecifyReportedInfo.setSendImmediately(true);
            this.mSpecifyReportedInfo.setImportant(true);
            if (T.ckIsEmpty(IMConfig.getInstallSource())) {
                this.mSpecifyReportedInfo.setInstallChannel("IMSDK");
            } else {
                this.mSpecifyReportedInfo.setInstallChannel(IMConfig.getInstallSource());
            }
        }
    }

    public void setDebuggable(boolean isDebuggable) {
        if (!needInitFirst()) {
            StatConfig.setDebugEnable(isDebuggable);
        }
    }

    @Deprecated
    public void setVersion(String imsdkVersion) {
        if (!needInitFirst()) {
            this.mSpecifyReportedInfo.setVersion(imsdkVersion);
            if (mClzNameVer != null) {
                mClzNameVer.put(getCurClzName(4), imsdkVersion);
                IMLogger.d(" " + mClzNameVer.put(getCurClzName(4), imsdkVersion));
            }
        }
    }

    private void resetVersion() {
        if (!needInitFirst() && mClzNameVer != null) {
            String curClzName = getCurClzName(7);
            if (mClzNameVer.containsKey(curClzName)) {
                this.mSpecifyReportedInfo.setVersion(mClzNameVer.get(curClzName));
                IMLogger.d("key = " + curClzName + " ,value = " + mClzNameVer.get(curClzName));
            }
        }
    }

    public void beginEvent(String eventID, String eventType, String eventResult, Properties properties) {
        beginEvent(eventID, eventType, eventResult, properties, false);
    }

    public void beginEvent(String eventID, String eventType, String eventResult, Properties properties, boolean needEncryptProperties) {
        if (mStatEventStack != null) {
            mStatEventStack.add(new StatEvent(eventID, eventType, "", eventResult, System.currentTimeMillis(), properties));
        }
        reportEvent(eventID, true, eventType, "start", eventResult, properties, needEncryptProperties);
    }

    public void trackEvent(String eventStage, String eventResult, Properties properties) {
        trackEvent(eventStage, eventResult, properties, false);
    }

    public void trackEvent(String eventStage, String eventResult, Properties properties, boolean needEncryptProperties) {
        if (mStatEventStack == null || mStatEventStack.size() <= 0) {
            IMLogger.e("Please call beginEvent by getInstance() first");
            return;
        }
        StatEvent statEvent = mStatEventStack.get(mStatEventStack.size() - 1);
        reportEvent(statEvent.mEventId, false, statEvent.mEventType, eventStage, eventResult, properties, needEncryptProperties);
    }

    public void endEvent(String eventResult, Properties properties) {
        endEvent(eventResult, properties, false);
    }

    public void endEvent(String eventResult, Properties properties, boolean needEncryptProperties) {
        int size;
        if (mStatEventStack == null || (size = mStatEventStack.size()) <= 0) {
            IMLogger.e("Please call beginEvent by getInstance() first");
            return;
        }
        if (properties == null) {
            properties = new Properties();
        }
        StatEvent statEvent = mStatEventStack.get(size - 1);
        properties.setProperty("interval", CommonUtil.convertIfNull(String.valueOf(System.currentTimeMillis() - statEvent.mEventStartTime)));
        reportEvent(statEvent.mEventId, false, statEvent.mEventType, "end", eventResult, properties, needEncryptProperties);
        mStatEventStack.remove(size - 1);
    }

    public void reportEvent(String eventID, String eventType, String eventStage, String eventResult) {
        reportEvent(eventID, false, eventType, eventStage, eventResult, this.mProperties);
    }

    public void reportEvent(String eventID, String eventType, String eventStage, String eventResult, boolean needEncryptProperties) {
        reportEvent(eventID, false, eventType, eventStage, eventResult, this.mProperties, needEncryptProperties);
    }

    public void reportEvent(String eventID, String eventType, String eventStage, String eventResult, Properties properties, boolean needEncryptProperties) {
        reportEvent(eventID, false, eventType, eventStage, eventResult, properties, needEncryptProperties);
    }

    public void reportEvent(String eventID, String eventType, String eventStage, String eventResult, Properties properties) {
        reportEvent(eventID, false, eventType, eventStage, eventResult, properties);
    }

    public void reportEvent(String eventID, boolean isNewSequence, String eventType, String eventStage, String eventResult, Properties properties) {
        if (!this.isGettingOpenId) {
            this.isGettingOpenId = true;
            this.mOpenId = IMLogin.getLoginResult().openId;
            this.isGettingOpenId = false;
            reportEvent(eventID, isNewSequence, eventType, eventStage, eventResult, this.mOpenId, properties);
        }
    }

    public void reportEvent(String eventID, boolean isNewSequence, String eventType, String eventStage, String eventResult, String openId, Properties properties, boolean needEncryptProperties) {
        try {
            resetVersion();
            IMLogger.d("eventId = " + eventID + " isNewSequence = " + isNewSequence + " eventType = " + eventType + " eventStage = " + eventStage + " eventResult = " + eventResult);
            if (needEncryptProperties) {
                Properties propertiesTmp = new Properties();
                for (Object key : properties.keySet()) {
                    propertiesTmp.setProperty(key.toString(), CommonUtil.convertIfNull(IMInnerStatCrypto.encrypt(properties.getProperty(key.toString()))));
                }
                propertiesTmp.setProperty("IMSDKEncryptKey", CommonUtil.convertIfNull(IMInnerStatCrypto.getKey()));
                reportEvent(eventID, isNewSequence, eventType, eventStage, eventResult, openId, propertiesTmp);
                return;
            }
            reportEvent(eventID, isNewSequence, eventType, eventStage, eventResult, openId, properties);
        } catch (Exception e) {
            IMLogger.w("inner report event get error : " + e.getMessage());
        }
    }

    public void reportEvent(String eventID, boolean isNewSequence, String eventType, String eventStage, String eventResult, String openId, Properties properties) {
        try {
            if (!needInitFirst()) {
                if (properties == null) {
                    properties = new Properties();
                }
                if (isNewSequence) {
                    mSequenceID = null;
                    mSequenceID = DeviceInfoUtils.getGuestId(mContext);
                    properties.setProperty("sid", CommonUtil.convertIfNull(mSequenceID));
                }
                if (openId != null) {
                    properties.setProperty("openid", openId);
                } else {
                    properties.setProperty("openid", "");
                }
                properties.setProperty("gameid", CommonUtil.convertIfNull(this.mGameId));
                if (!TextUtils.isEmpty(eventType)) {
                    properties.setProperty("eventtype", eventType);
                }
                if (!TextUtils.isEmpty(eventStage)) {
                    properties.setProperty("eventstage", eventStage);
                }
                if (!TextUtils.isEmpty(eventResult)) {
                    properties.setProperty("eventresult", eventResult);
                }
                if (!TextUtils.isEmpty(this.mVersion)) {
                    properties.setProperty("version", this.mVersion);
                }
                if (!TextUtils.isEmpty(this.mDid)) {
                    properties.setProperty("did", this.mDid);
                }
                if (!TextUtils.isEmpty(this.mSessionId)) {
                    properties.setProperty("sessionid", this.mSessionId);
                }
                if (!TextUtils.isEmpty(this.mDeviceInfo)) {
                    properties.setProperty("deviceinfo", this.mDeviceInfo);
                }
                String mTime = DeviceInfoUtils.getNowTime();
                if (!TextUtils.isEmpty(mTime)) {
                    properties.setProperty("mtime", mTime);
                }
                String apn = DeviceInfoUtils.getApn(mContext);
                if (!TextUtils.isEmpty(apn)) {
                    properties.setProperty("apn", apn);
                }
                properties.setProperty("eventcombine", eventType + "-" + eventStage + "-" + eventResult);
                StatServiceImpl.trackCustomKVEvent(mContext, eventID, properties, this.mSpecifyReportedInfo);
                this.isNewProperties = true;
            }
        } catch (Exception e) {
            IMLogger.w("inner report event get error : " + e.getMessage());
        }
    }

    public void reportEvent(String eventID, boolean isNewSequence, String eventType, String eventStage, String eventResult, Properties properties, boolean needEncryptProperties) {
        resetVersion();
        IMLogger.d("eventId = " + eventID + " isNewSequence = " + isNewSequence + " eventType = " + eventType + " eventStage = " + eventStage + " eventResult = " + eventResult);
        if (needEncryptProperties) {
            Properties propertiesTmp = new Properties();
            for (Object key : properties.keySet()) {
                propertiesTmp.setProperty(key.toString(), CommonUtil.convertIfNull(IMInnerStatCrypto.encrypt(properties.getProperty(key.toString()))));
            }
            propertiesTmp.setProperty("IMSDKEncryptKey", CommonUtil.convertIfNull(IMInnerStatCrypto.getKey()));
            reportEvent(eventID, isNewSequence, eventType, eventStage, eventResult, propertiesTmp);
            return;
        }
        reportEvent(eventID, isNewSequence, eventType, eventStage, eventResult, properties);
    }

    public void reportLogin(Properties properties) {
        if (!needInitFirst()) {
            StatServiceImpl.trackCustomKVEvent(mContext, LOGIN_EVENT_REPORT_NAME, properties, this.mSpecifyReportedInfo);
        }
    }

    private boolean needInitFirst() {
        if (mContext != null) {
            return false;
        }
        IMLogger.e("Need initialize first");
        return true;
    }

    public static Properties convertProperties(Context context) {
        Properties properties = new Properties();
        if (context != null) {
            properties.setProperty("context", context.toString());
        } else {
            properties.setProperty("Context", Constants.NULL_VERSION_ID);
        }
        return properties;
    }

    public static Properties convertProperties(boolean value) {
        Properties properties = new Properties();
        properties.setProperty("value", String.valueOf(value));
        return properties;
    }

    public static Properties convertProperties(String value) {
        Properties properties = new Properties();
        if (value != null) {
            properties.setProperty("value", value);
        } else {
            properties.setProperty("value", Constants.NULL_VERSION_ID);
        }
        return properties;
    }

    public static Properties convertProperties(JsonSerializable result) {
        Properties properties = new Properties();
        if (result != null) {
            try {
                properties.setProperty("value", result.toJSONString());
            } catch (Exception e) {
                IMLogger.w("try report result data failed : " + e.getMessage());
            }
        } else {
            properties.setProperty("value", Constants.NULL_VERSION_ID);
        }
        return properties;
    }

    public static Properties convertProperties(String url, Map<String, String> params) {
        Properties properties = new Properties();
        properties.setProperty("url", CommonUtil.convertIfNull(url));
        if (params != null) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                properties.setProperty(entry.getKey(), entry.getValue() == null ? Constants.NULL_VERSION_ID : entry.getValue());
            }
        }
        return properties;
    }

    public static Properties convertProperties(Exception exception) {
        Properties properties = new Properties();
        if (exception != null) {
            properties.setProperty("error", CommonUtil.convertIfNull(exception.getMessage()));
        } else {
            properties.setProperty("Exception", Constants.NULL_VERSION_ID);
        }
        return properties;
    }

    public static Properties convertProperties(IMException exception) {
        Properties properties = new Properties();
        if (exception != null) {
            properties.setProperty("imsdk code", String.valueOf(exception.imsdkRetCode));
            properties.setProperty("imsdk message", exception.imsdkRetMsg != null ? exception.imsdkRetMsg : Constants.NULL_VERSION_ID);
            properties.setProperty("third code", String.valueOf(exception.thirdRetCode));
            properties.setProperty("third message", exception.thirdRetMsg != null ? exception.thirdRetMsg : Constants.NULL_VERSION_ID);
            properties.setProperty("error", exception.getMessage() != null ? exception.getMessage() : Constants.NULL_VERSION_ID);
        } else {
            properties.setProperty("IMException", Constants.NULL_VERSION_ID);
        }
        return properties;
    }
}
