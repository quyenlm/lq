package com.tencent.imsdk.push.xg;

import android.app.Activity;
import android.content.Context;
import com.tencent.android.tpush.XGIOperateCallback;
import com.tencent.android.tpush.XGLocalMessage;
import com.tencent.android.tpush.XGPushConfig;
import com.tencent.android.tpush.XGPushManager;
import com.tencent.imsdk.IMConfig;
import com.tencent.imsdk.push.api.IMOperateCallback;
import com.tencent.imsdk.push.api.IMPushListener;
import com.tencent.imsdk.push.base.IMPushBase;
import com.tencent.imsdk.push.entity.IMLocalMessage;
import com.tencent.imsdk.push.xg.XGAccountManager;
import com.tencent.imsdk.sns.api.IMLogin;
import com.tencent.imsdk.sns.base.IMLoginResult;
import com.tencent.imsdk.stat.innerapi.IMInnerStat;
import com.tencent.imsdk.stat.innerapi.InnerStat;
import com.tencent.imsdk.tool.etc.IMLogger;
import com.tencent.imsdk.tool.etc.MetaDataUtil;
import com.tencent.imsdk.tool.etc.T;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Properties;

public class XGPushHelper extends IMPushBase {
    private static final String FCM_ENABLE = "com.tencent.imsdk.FCMEnable";
    private static final String FOREIGN_IP_ENABLE = "com.tencent.imsdk.ForeignIPEnable";
    private static final String INNER_STAT_EVENT_ID = "push_xg_fcm";
    private static final String XG_RESET_ACCOUNT = "com.tencent.imsdk.XGResetAccount";
    private static Object mLockObject = new Object();
    public static volatile XGPushHelper mPushHelper = null;
    protected static IMPushListener mPushListener = null;
    private IMOperateCallback mInnerCallback = new IMOperateCallback() {
        public void onFail(Object data, int errCode, String msg) {
            IMLogger.w("XG register push failed with token = " + data + ", errCode =  " + errCode + ", msg = " + msg);
        }

        public void onSuccess(Object token, int flag) {
            IMLogger.d("XG register push success with token = " + token);
        }
    };
    private IMInnerStat mInnerStat;
    private InnerStat.Builder statBuilder = null;

    private String getOpenId() {
        IMLoginResult result = IMLogin.getLoginResult();
        return result != null ? result.openId : "";
    }

    public static XGPushHelper getInstance() {
        if (mPushHelper == null) {
            synchronized (mLockObject) {
                if (mPushHelper == null) {
                    mPushHelper = new XGPushHelper();
                }
            }
        }
        return mPushHelper;
    }

    public void initialize(Context context) {
        boolean isEnableXGDebug;
        super.initialize(context);
        if (this.statBuilder == null) {
            this.statBuilder = new InnerStat.Builder(context, BuildConfig.VERSION_NAME, String.valueOf(3.27f), "imsdkpushxgfcm");
            if (this.mInnerStat == null) {
                this.mInnerStat = new IMInnerStat(context, BuildConfig.VERSION_NAME);
                this.mInnerStat.reportEvent(INNER_STAT_EVENT_ID, true, "push xg initialize", "function start ", "success", getOpenId(), (Properties) null);
            }
            int debugLevel = IMConfig.getDebugLevel();
            if (!MetaDataUtil.readMetaDataFromApplication(context, "IS_DEBUG", false) || debugLevel <= 0 || debugLevel > 2) {
                isEnableXGDebug = false;
            } else {
                isEnableXGDebug = true;
            }
            XGPushConfig.enableDebug(context, isEnableXGDebug);
            IMLogger.d("enableDebug : " + isEnableXGDebug);
            boolean isForeignEnable = MetaDataUtil.readMetaDataFromApplication(context, FOREIGN_IP_ENABLE, false);
            XGPushConfig.setForeiginPushEnable(context, isForeignEnable);
            IMLogger.d("setForeiginPushEnable : " + isForeignEnable);
            boolean isFCMEnable = MetaDataUtil.readMetaDataFromApplication(context, FCM_ENABLE, true);
            XGPushConfig.enableFcmPush(context, isFCMEnable);
            IMLogger.d("enableFcmPush : " + isFCMEnable);
        }
    }

    public void setPushListener(IMPushListener pushListener) {
        try {
            mPushListener = pushListener;
        } catch (Exception e) {
            IMLogger.e(e.getMessage());
        }
    }

    public IMPushListener getPushListener() {
        return mPushListener;
    }

    public void registerPush(Context context) {
        registerPush(context, this.mInnerCallback);
    }

    public void registerPush(Context context, IMOperateCallback callback) {
        registerPush(context, (String) null, callback);
    }

    public void registerPush(Context context, String account) {
        registerPush(context, account, this.mInnerCallback);
    }

    public void registerPush(Context context, String account, IMOperateCallback callback) {
        registerXgPush(context, account, callback);
    }

    /* access modifiers changed from: private */
    public XGIOperateCallback newOperateCallback(final IMOperateCallback callback) {
        return new XGIOperateCallback() {
            public void onSuccess(Object data, int flag) {
                if (callback != null) {
                    callback.onSuccess(data, flag);
                } else {
                    IMLogger.w("xg register callback is null ");
                }
            }

            public void onFail(Object data, int errCode, String msg) {
                if (callback != null) {
                    callback.onFail(data, errCode, msg);
                } else {
                    IMLogger.w("xg register callback is null ");
                }
            }
        };
    }

    /* access modifiers changed from: private */
    public void registerXgPush(Context context, String account, XGIOperateCallback callback) {
        if (T.ckIsEmpty(account)) {
            XGPushManager.registerPush(context, callback);
        } else {
            XGPushManager.bindAccount(context, account, callback);
        }
    }

    private void registerXgPush(final Context context, final String account, final IMOperateCallback callback) {
        if (MetaDataUtil.readMetaDataFromApplication(context, XG_RESET_ACCOUNT, false)) {
            XGAccountManager.makeLaunchAccountUnique(context, new XGAccountManager.XGAccountCallback() {
                public void onDeleteAccountResult(boolean deleteFlag) {
                    XGPushHelper.this.registerXgPush(context, account, XGPushHelper.this.newOperateCallback(callback));
                }
            });
        } else {
            registerXgPush(context, account, newOperateCallback(callback));
        }
    }

    public void registerPush(Context context, String account, String ticket, int ticketType, String qua, final IMOperateCallback callback) {
        XGPushManager.registerPush(context, account, ticket, ticketType, qua, new XGIOperateCallback() {
            public void onSuccess(Object data, int flag) {
                callback.onSuccess(data, flag);
            }

            public void onFail(Object data, int errCode, String msg) {
                callback.onFail(data, errCode, msg);
            }
        });
    }

    public void unregisterPush(final Context context) {
        if (this.mInnerStat != null) {
            this.mInnerStat.reportEvent(INNER_STAT_EVENT_ID, false, "unregisterPush", "context is " + (context == null ? " null " : "not null"), "success", getOpenId(), (Properties) null);
        }
        XGAccountManager.deleteAllAccount(context, new XGAccountManager.XGAccountCallback() {
            public void onDeleteAccountResult(boolean deleteFlag) {
                XGPushManager.unregisterPush(context);
            }
        });
    }

    public void setTag(Context context, String tagName) {
        if (this.mInnerStat != null) {
            this.mInnerStat.reportEvent(INNER_STAT_EVENT_ID, false, "setTag", "tag is " + tagName, "success", getOpenId(), (Properties) null);
        }
        XGPushManager.setTag(context, tagName);
    }

    public void deleteTag(Context context, String tagName) {
        if (this.mInnerStat != null) {
            this.mInnerStat.reportEvent(INNER_STAT_EVENT_ID, false, "deleteTag", "tag is " + tagName, "success", getOpenId(), (Properties) null);
        }
        XGPushManager.deleteTag(context, tagName);
    }

    public void onActivityStoped(Activity activity) {
        XGPushManager.onActivityStoped(activity);
    }

    public void addLocalNotification(IMLocalMessage localMessage) {
        int i;
        int i2 = 1;
        try {
            SimpleDateFormat YMD = new SimpleDateFormat("yyyyMMdd", Locale.US);
            SimpleDateFormat HH = new SimpleDateFormat("HH", Locale.US);
            SimpleDateFormat MM = new SimpleDateFormat("mm", Locale.US);
            IMLogger.d("fireTime = " + localMessage.fireTime);
            IMLogger.d("localMessage = " + localMessage.toString());
            Date date = new Date(localMessage.fireTime);
            XGLocalMessage message = new XGLocalMessage();
            message.setType(localMessage.type);
            message.setTitle(localMessage.title);
            message.setContent(localMessage.content);
            message.setDate(YMD.format(date));
            message.setHour(HH.format(date));
            message.setMin(MM.format(date));
            if (localMessage.isRinging) {
                i = 1;
            } else {
                i = 0;
            }
            message.setRing(i);
            message.setRing_raw(localMessage.ringRaw);
            message.setLights(localMessage.light);
            if (!localMessage.isVibrate) {
                i2 = 0;
            }
            message.setVibrate(i2);
            message.setIcon_res(localMessage.iconRes);
            message.setPackageDownloadUrl(localMessage.apkDownloadUrl);
            message.setBuilderId(localMessage.builderId);
            message.setStyle_id(localMessage.styleId);
            message.setAction_type(localMessage.actionType);
            switch (localMessage.actionType) {
                case 1:
                    message.setActivity(localMessage.actionContent);
                    break;
                case 2:
                    message.setUrl(localMessage.actionContent);
                    break;
                case 3:
                    message.setIntent(localMessage.actionContent);
                    break;
                case 4:
                    message.setPackageName(localMessage.actionContent);
                    break;
            }
            IMLogger.d("message = " + message.toString());
            XGPushManager.addLocalNotification(this.context, message);
        } catch (Exception ex) {
            IMLogger.w(ex.getMessage());
        }
    }

    public void clearLocalNotifications() {
        IMLogger.d("");
        XGPushManager.clearLocalNotifications(this.context);
    }
}
