package com.tencent.imsdk.stat.innerapi;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.amazonaws.services.s3.internal.Constants;
import com.tencent.imsdk.IMCallback;
import com.tencent.imsdk.IMConfig;
import com.tencent.imsdk.IMErrorMsg;
import com.tencent.imsdk.IMException;
import com.tencent.imsdk.IMResult;
import com.tencent.imsdk.IMVersion;
import com.tencent.imsdk.sns.api.IMLogin;
import com.tencent.imsdk.sns.base.IMLoginResult;
import com.tencent.imsdk.tool.etc.CommonUtil;
import com.tencent.imsdk.tool.etc.DeviceInfoUtils;
import com.tencent.imsdk.tool.etc.DeviceUuidFactory;
import com.tencent.imsdk.tool.etc.IMLogger;
import com.tencent.imsdk.tool.etc.InstallationInfoUtil;
import com.tencent.imsdk.tool.etc.T;
import com.tencent.imsdk.tool.json.JsonSerializable;
import com.tencent.msdk.stat.StatConfig;
import com.tencent.msdk.stat.StatServiceImpl;
import com.tencent.msdk.stat.StatSpecifyReportedInfo;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;
import java.util.UUID;
import org.json.JSONException;
import org.json.JSONObject;

public class InnerStat {
    private static final String APP_SIGNATURE = "sig";
    private static final String APP_VERSION = "appversion";
    private static final String CALL_INTERVAL = "interval";
    public static final String CANCEL = "cancel";
    private static final int CANCEL_CODE = 2;
    private static final String DEFAULT_EMPTY = "";
    public static final int DEPTH_BASE_OF_PLUGIN_CALL_STAT = 5;
    public static final int DEPTH_PLUGIN_CALL_STAT_DIRECTLY = 4;
    private static final String DID = "did";
    public static final String END = "function end ";
    public static final String ERROR = "error";
    private static final String EVENT = "event";
    private static final String EVENT_RESULT = "result";
    private static final String EVENT_STAGE = "stage";
    private static final String EXTRA = "extra";
    private static final String FB_VERSION = "facebook";
    private static final String GAME_ID = "gid";
    private static final String GMS_VERSION = "gms";
    private static final String GPG_VERSION = "playgames";
    private static final String GPS_VERSION = "playstore";
    private static final String HTTP_SCHEME = "http";
    private static final String IID = "iid";
    private static final String IMSDK_CODE = "code";
    private static final String IMSDK_MSG = "msg";
    private static final String MAC_ADDRESS = "mac";
    private static final String M_TIME = "mtime";
    private static final String OPEN_ID = "openId";
    private static final String PLATFORM = "platform";
    private static final String PLUGIN_NAME = "plugin";
    private static final String SDK_VERSION = "sdk";
    private static final int SERVER_CODE = 5;
    private static final String SID = "sid";
    public static final String START = "function start ";
    public static final String SUCCESS = "success";
    private static final int SUCCESS_CODE = 1;
    private static final String THIRD_CODE = "tcode";
    private static final String THIRD_MSG = "tmsg";
    private static final String TYPE = "type";
    private static final String TYPE_INIT = "init";
    private static final String TYPE_LOG = "log";
    private static final String TYPE_MONITOR = "monitor";
    private static final String TYPE_ONCE = "once";
    private static final String TYPE_PLUGIN = "plugin";
    public static final String UNKNOWN = "unknown result, no callback execute";
    private static final String VERSION = "version";
    /* access modifiers changed from: private */
    public static boolean mAppStartReported = false;
    private static final String mSessionId = UUID.randomUUID().toString();
    private Builder mBuilder;
    private int mGameId;
    private StatSpecifyReportedInfo mSpecifyReportedInfo;

    public static class ThirdPN {
        public static final String FACEBOOK = "com.facebook.katana";
        public static final String GOOGLE = "com.google.android.gms";
        public static final String GOOGLE_PLAY_GAMES = "com.google.android.play.games";
        public static final String GOOGLE_PLAY_STORE = "com.android.vending";
    }

    private InnerStat(Builder builder) {
        this();
        this.mBuilder = builder;
    }

    private InnerStat() {
    }

    private void initMTAEnv() {
        if (this.mSpecifyReportedInfo == null) {
            this.mSpecifyReportedInfo = new StatSpecifyReportedInfo();
            this.mSpecifyReportedInfo.setAppKey("AH3HVXV384J1");
            this.mSpecifyReportedInfo.setSendImmediately(true);
            this.mSpecifyReportedInfo.setImportant(true);
            if (T.ckIsEmpty(IMConfig.getInstallSource())) {
                this.mSpecifyReportedInfo.setInstallChannel("IMSDK");
            } else {
                this.mSpecifyReportedInfo.setInstallChannel(IMConfig.getInstallSource());
            }
            StatConfig.setDebugEnable(isDebug(IMConfig.getDebugLevel()));
        }
    }

    private void setPropertyIfAbsent(Properties properties, String key, String value) {
        if (key != null && !properties.containsKey(key) && value != null && value.length() > 0) {
            properties.setProperty(key, value);
        }
    }

    private void beginReport(String event) {
        Map<String, String> params = new HashMap<>();
        params.put("type", TYPE_MONITOR);
        params.put("event", event);
        params.put(EVENT_STAGE, "begin");
        params.put("code", String.valueOf(1));
        params.put("msg", String.valueOf(IMErrorMsg.getMessageByCode(1)));
        reportEvent(this.mBuilder, params);
    }

    /* access modifiers changed from: private */
    public void endReport(String event, String elapse, IMResult result) {
        int i = 0;
        Map<String, String> params = new HashMap<>();
        params.put("type", TYPE_MONITOR);
        params.put("event", event);
        params.put("code", String.valueOf(result != null ? result.imsdkRetCode : 0));
        if (result != null) {
            i = result.thirdRetCode;
        }
        params.put(THIRD_CODE, String.valueOf(i));
        params.put("msg", result != null ? result.imsdkRetMsg : "");
        params.put(THIRD_MSG, result != null ? result.thirdRetMsg : "");
        params.put(CALL_INTERVAL, elapse);
        params.put(EVENT_STAGE, "end");
        reportEvent(this.mBuilder, params);
    }

    private void reportOnce(String event, String result) {
        Map<String, String> params = new HashMap<>();
        params.put("type", TYPE_ONCE);
        params.put("event", event);
        params.put("result", result);
        reportEvent(this.mBuilder, params);
    }

    @Nullable
    public <D extends IMResult> IMCallback<D> proxyListener4EventReport(@Nullable String channel, @Nullable IMCallback<D> callback) {
        String str;
        StringBuilder sb = new StringBuilder();
        if (channel == null || !channel.startsWith("http")) {
            StackTraceElement element = Thread.currentThread().getStackTrace()[3];
            StringBuilder append = sb.append(element.getFileName().replaceFirst("IM", "").replaceFirst(".java", "")).append("-");
            if (channel == null) {
                str = "imsdk";
            } else {
                str = channel;
            }
            append.append(str).append("-").append(element.getMethodName());
        } else {
            sb.append(channel);
        }
        return proxyListener4EventReport(channel, callback, sb.toString().toLowerCase(Locale.getDefault()));
    }

    @Nullable
    public <D extends IMResult> IMCallback<D> proxyListener4EventReport(@Nullable String result, @Nullable IMCallback<D> callback, String methodName) {
        if (callback != null) {
            beginReport(methodName);
            final long startTime = System.currentTimeMillis();
            final String str = methodName;
            final IMCallback<D> iMCallback = callback;
            return new IMCallback<D>() {
                public void onSuccess(D result) {
                    InnerStat.this.endReport(str, String.valueOf(System.currentTimeMillis() - startTime), result);
                    iMCallback.onSuccess(result);
                }

                public void onCancel() {
                    InnerStat.this.endReport(str, String.valueOf(System.currentTimeMillis() - startTime), new IMResult(2, 2));
                    iMCallback.onCancel();
                }

                public void onError(IMException exception) {
                    InnerStat.this.endReport(str, String.valueOf(System.currentTimeMillis() - startTime), new IMResult(exception.errorCode, exception.getMessage(), exception.imsdkRetCode, exception.imsdkRetMsg, exception.thirdRetCode, exception.thirdRetMsg));
                    iMCallback.onError(exception);
                }
            };
        }
        reportOnce(methodName, result);
        return null;
    }

    public IMCallback<String> proxyHttpListener4EventReport(String methodName, IMCallback<String> callback) {
        if (callback == null) {
            return null;
        }
        if (methodName.startsWith("http")) {
            beginReport(methodName);
            final long startTime = System.currentTimeMillis();
            final String str = methodName;
            final IMCallback<String> iMCallback = callback;
            return new IMCallback<String>() {
                public void onSuccess(String result) {
                    IMResult imResult;
                    long elapseTime = System.currentTimeMillis() - startTime;
                    try {
                        imResult = new IMResult(result);
                        if (imResult.retCode == 1) {
                            imResult.imsdkRetCode = 1;
                            imResult.imsdkRetMsg = IMErrorMsg.getMessageByCode(imResult.imsdkRetCode);
                        } else {
                            imResult.imsdkRetCode = 5;
                            imResult.imsdkRetMsg = IMErrorMsg.getMessageByCode(imResult.imsdkRetCode);
                        }
                        imResult.thirdRetCode = imResult.retCode;
                        imResult.thirdRetMsg = imResult.retMsg;
                    } catch (JSONException e) {
                        imResult = new IMResult(5, 5);
                    }
                    InnerStat.this.endReport(str, String.valueOf(elapseTime), imResult);
                    iMCallback.onSuccess(result);
                }

                public void onCancel() {
                    long elapseTime = System.currentTimeMillis() - startTime;
                    InnerStat.this.endReport(str, String.valueOf(elapseTime), new IMResult(2, 2));
                    iMCallback.onCancel();
                }

                public void onError(IMException exception) {
                    long elapseTime = System.currentTimeMillis();
                    InnerStat.this.endReport(str, String.valueOf(elapseTime), new IMResult(exception.errorCode, exception.getMessage(), exception.imsdkRetCode, exception.imsdkRetMsg, exception.thirdRetCode, exception.thirdRetMsg));
                    iMCallback.onError(exception);
                }
            };
        }
        IMLogger.w("please notice : method name is not start with http");
        return callback;
    }

    public Properties reportEvent() {
        return reportEvent(this.mBuilder);
    }

    public Properties reportEvent(Builder builder, Map<String, String> map) {
        if (builder.innerProp == null) {
            Map unused = builder.innerProp = new HashMap();
        }
        builder.innerProp.putAll(map);
        return reportEvent(builder);
    }

    private void mtaReport(Builder builder, Properties prop) {
        initMTAEnv();
        IMLogger.d("inner stat : " + new TreeMap(prop).toString());
        StatServiceImpl.trackCustomKVEvent(builder.ctx, prop.get("type").toString(), prop, this.mSpecifyReportedInfo);
    }

    public Properties reportEvent(Builder builder) {
        try {
            Properties prop = new Properties();
            if (builder.innerProp != null && !builder.innerProp.isEmpty()) {
                for (Map.Entry<String, String> item : builder.innerProp.entrySet()) {
                    setPropertyIfAbsent(prop, item.getKey(), item.getValue());
                }
            }
            if (builder.extraProp != null && !builder.extraProp.isEmpty()) {
                JSONObject extraJson = new JSONObject();
                for (Map.Entry<String, String> item2 : builder.extraProp.entrySet()) {
                    if (builder.isCrypt) {
                        extraJson.put(item2.getKey(), IMInnerStatCrypto.encrypt(item2.getValue()));
                    } else {
                        extraJson.put(item2.getKey(), item2.getValue());
                    }
                }
                if (builder.isCrypt) {
                    extraJson.put("IMSDKEncryptKey", IMInnerStatCrypto.getKey());
                }
                setPropertyIfAbsent(prop, EXTRA, extraJson.toString());
            }
            if (this.mGameId == 0) {
                this.mGameId = IMConfig.getGameId();
            }
            setPropertyIfAbsent(prop, GAME_ID, String.valueOf(this.mGameId));
            setPropertyIfAbsent(prop, PLATFORM, IMConfig.getPlatform());
            setPropertyIfAbsent(prop, "version", builder.version);
            if (!"plugin".equals(prop.get("type"))) {
                setPropertyIfAbsent(prop, "openId", T.ckIsEmpty(builder.openId) ? getOpenId() : builder.openId);
                setPropertyIfAbsent(prop, DID, DeviceUuidFactory.getInstance(builder.ctx).getDeviceUuid());
                setPropertyIfAbsent(prop, IID, InstallationInfoUtil.getInstallationID(builder.ctx));
                setPropertyIfAbsent(prop, SID, mSessionId);
                setPropertyIfAbsent(prop, "event", builder.method);
                setPropertyIfAbsent(prop, EVENT_STAGE, builder.stage);
                setPropertyIfAbsent(prop, "result", builder.result);
                setPropertyIfAbsent(prop, M_TIME, String.valueOf(System.currentTimeMillis()));
                setPropertyIfAbsent(prop, "type", TYPE_LOG);
            }
            mtaReport(builder, prop);
            builder.reset();
            if (!isDebug(IMConfig.getDebugLevel())) {
                return null;
            }
            return prop;
        } catch (Exception e) {
            IMLogger.w("inner report failed : " + e.getMessage());
            return null;
        }
    }

    public static class Builder {
        /* access modifiers changed from: private */
        public Context ctx;
        /* access modifiers changed from: private */
        public Map<String, String> extraProp;
        /* access modifiers changed from: private */
        public Map<String, String> innerProp;
        /* access modifiers changed from: private */
        public boolean isCrypt;
        /* access modifiers changed from: private */
        public String method;
        /* access modifiers changed from: private */
        public String openId;
        /* access modifiers changed from: private */
        public String result;
        private String sdkVersion;
        /* access modifiers changed from: private */
        public String stage;
        /* access modifiers changed from: private */
        public String version;

        private void reportOnceAtAppStart(@Nullable Context context) {
            if (!InnerStat.mAppStartReported) {
                if (this.innerProp == null) {
                    this.innerProp = new HashMap();
                }
                this.innerProp.put("type", InnerStat.TYPE_INIT);
                this.innerProp.put(InnerStat.MAC_ADDRESS, DeviceInfoUtils.getMac(context));
                this.innerProp.put(InnerStat.GMS_VERSION, DeviceInfoUtils.getAppVersion(context, "com.google.android.gms"));
                this.innerProp.put(InnerStat.FB_VERSION, DeviceInfoUtils.getAppVersion(context, "com.facebook.katana"));
                this.innerProp.put(InnerStat.GPG_VERSION, DeviceInfoUtils.getAppVersion(context, ThirdPN.GOOGLE_PLAY_GAMES));
                this.innerProp.put(InnerStat.GPS_VERSION, DeviceInfoUtils.getAppVersion(context, "com.android.vending"));
                this.innerProp.put("sig", IMConfig.getMD5Key());
                new InnerStat(this).reportEvent(this);
                if (this.innerProp == null) {
                    this.innerProp = new HashMap();
                }
                this.innerProp.put("type", "plugin");
                this.innerProp.put("plugin", "imsdkbase");
                this.innerProp.put(InnerStat.APP_VERSION, DeviceInfoUtils.getAppVersion(context));
                this.innerProp.put("version", IMVersion.getVersion());
                this.innerProp.put("sdk", IMVersion.getVersion());
                this.innerProp.put(InnerStat.PLATFORM, IMConfig.getPlatform());
                new InnerStat(this).reportEvent(this);
                boolean unused = InnerStat.mAppStartReported = true;
            }
        }

        private void reportOnceAtPluginInit(@Nullable Context context, String pluginName) {
            try {
                if (this.innerProp == null) {
                    this.innerProp = new HashMap();
                }
                this.innerProp.put("type", "plugin");
                this.innerProp.put("plugin", pluginName);
                this.innerProp.put(InnerStat.APP_VERSION, DeviceInfoUtils.getAppVersion(context));
                this.innerProp.put("sdk", this.sdkVersion);
                this.innerProp.put(InnerStat.PLATFORM, IMConfig.getPlatform());
                new InnerStat(this).reportEvent(this);
            } catch (Exception e) {
                IMLogger.w("inner report failed : " + e.getMessage());
            }
        }

        public Builder(@NonNull Context context, @NonNull String version2) {
            this.version = version2;
            this.ctx = context;
            reportOnceAtAppStart(this.ctx);
        }

        public Builder(@NonNull Context context, @NonNull String version2, @Nullable String sdkVersion2, String pluginName) {
            this.version = version2;
            this.ctx = context;
            this.sdkVersion = sdkVersion2;
            reportOnceAtAppStart(this.ctx);
            reportOnceAtPluginInit(this.ctx, pluginName);
        }

        public Builder reset() {
            this.result = "";
            this.openId = "";
            this.stage = "";
            this.sdkVersion = "";
            this.isCrypt = false;
            this.extraProp = null;
            this.innerProp = null;
            return this;
        }

        public Builder setVersion(String version2) {
            this.version = version2;
            return this;
        }

        public Builder setMethod(String method2) {
            this.method = method2;
            return this;
        }

        public Builder setStage(String stage2) {
            this.stage = stage2;
            return this;
        }

        public Builder setResult(String result2) {
            this.result = result2;
            return this;
        }

        public Builder setCrypt(boolean isCrypt2) {
            this.isCrypt = isCrypt2;
            return this;
        }

        @Deprecated
        public Builder setNewSeq(boolean isNewSeq) {
            return this;
        }

        @Deprecated
        public Builder setDebuggable(boolean isDebug) {
            return this;
        }

        public Builder setOpenId(String openId2) {
            this.openId = openId2;
            return this;
        }

        public Builder setExtraProp(Map<String, String> extraProp2) {
            this.extraProp = extraProp2;
            return this;
        }

        public InnerStat create() {
            return new InnerStat(this);
        }
    }

    public static Map convertMap(boolean value) {
        Map<String, String> map = new HashMap<>();
        map.put("value", String.valueOf(value));
        return map;
    }

    public static Map convertMap(String value) {
        Map<String, String> map = new HashMap<>();
        if (value != null) {
            map.put("value", value);
        } else {
            map.put("value", Constants.NULL_VERSION_ID);
        }
        return map;
    }

    public static Map convertMap(String url, Map<String, String> params) {
        Map<String, String> map = new HashMap<>();
        map.put("url", CommonUtil.convertIfNull(url));
        map.putAll(params);
        return map;
    }

    public static Map convertMap(Exception exception) {
        Map<String, String> map = new HashMap<>();
        if (exception != null) {
            map.put("error", CommonUtil.convertIfNull(exception.getMessage()));
        } else {
            map.put("Exception", Constants.NULL_VERSION_ID);
        }
        return map;
    }

    public static Map convertMap(JsonSerializable result) {
        Map<String, String> map = new HashMap<>();
        if (result != null) {
            try {
                map.put("value", result.toJSONString());
            } catch (Exception e) {
                IMLogger.w("try report result data failed : " + e.getMessage());
            }
        } else {
            map.put("value", Constants.NULL_VERSION_ID);
        }
        return map;
    }

    public static Map convertMap(Properties properties) {
        return new HashMap<>(properties);
    }

    private boolean isDebug(int debugLevel) {
        return debugLevel <= 3;
    }

    private String getOpenId() {
        IMLoginResult loginResult = IMLogin.getLoginResult();
        if (loginResult == null || loginResult.imsdkRetCode != 1) {
            return "";
        }
        return loginResult.openId;
    }
}
