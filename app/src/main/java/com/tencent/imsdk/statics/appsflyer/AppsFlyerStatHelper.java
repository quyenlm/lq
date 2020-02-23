package com.tencent.imsdk.statics.appsflyer;

import android.app.Activity;
import android.content.Context;
import android.provider.Settings;
import com.appsflyer.AFInAppEventParameterName;
import com.appsflyer.AFInAppEventType;
import com.appsflyer.AppsFlyerLib;
import com.google.android.gms.appinvite.PreviewActivity;
import com.tencent.imsdk.IMErrorDef;
import com.tencent.imsdk.sns.api.IMLogin;
import com.tencent.imsdk.sns.base.IMLoginResult;
import com.tencent.imsdk.stat.api.IMStatPlatInterface;
import com.tencent.imsdk.stat.base.IMStatBase;
import com.tencent.imsdk.tool.etc.IMLogger;
import com.tencent.imsdk.tool.etc.MetaDataUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class AppsFlyerStatHelper extends IMStatBase {
    private static final String AppsFlyer_Channel = "AppsFlyer";
    private static final String DEVKEY_APPSFLYER_KEY = "DEVKEY_APPSFLYER";
    private static final String GCM_PROJECT_NUM_KEY = "GCM_PROJECT_NUM";
    private static final int IMSDK_SUCCESS_CODE = 1;
    private static final String IS_DEBUG_KEY = "IS_DEBUG";
    private static AppsFlyerStatHelper appsflyerStatHelper;
    private static LinkedList<String> crashReportChannels = new LinkedList<>();
    private static LinkedList<String> eventReportChannels = new LinkedList<>();
    private static LinkedList<String> exceptionReportChannels = new LinkedList<>();
    private static Object lock = new Object();
    private static LinkedList<String> purchaseReportChannels = new LinkedList<>();
    private static LinkedList<String> testSpeedReportChannels = new LinkedList<>();
    private static LinkedList<String> trackEventReportChannels = new LinkedList<>();
    private static LinkedList<String> trackPagChannels = new LinkedList<>();
    protected Context mContext;
    public boolean mHasInitializebyExtend = false;
    private String mLastOpenID = "";

    public static AppsFlyerStatHelper getInstance() {
        if (appsflyerStatHelper == null) {
            synchronized (lock) {
                if (appsflyerStatHelper == null) {
                    appsflyerStatHelper = new AppsFlyerStatHelper() {
                    };
                }
            }
        }
        return appsflyerStatHelper;
    }

    public void init(Context context, IMStatPlatInterface initInterface) {
        IMLogger.d("AppsFlyerStatHelper starts to init and initInterface.setEventReportChannels().length is: " + initInterface.setEventReportChannels().length);
        if (initInterface.setEventReportChannels() == null || initInterface.setEventReportChannels().length == 0) {
            IMLogger.d("initInterface.setEventReportChannels is empty!");
        } else {
            for (int i = 0; i < initInterface.setEventReportChannels().length; i++) {
                IMLogger.d("setEventReportChannels " + i + " is: " + initInterface.setEventReportChannels()[i]);
                if (!eventReportChannels.contains(initInterface.setEventReportChannels()[i])) {
                    eventReportChannels.add(initInterface.setEventReportChannels()[i]);
                    IMLogger.d("setEventReportChannels " + i + " is: " + initInterface.setEventReportChannels()[i] + " add to eventReportChannels!");
                }
            }
        }
        if (!(initInterface.setPurchaseReportChannels() == null || initInterface.setPurchaseReportChannels().length == 0)) {
            for (int i2 = 0; i2 < initInterface.setPurchaseReportChannels().length; i2++) {
                IMLogger.d("setPurchaseReportChannels " + i2 + " is: " + initInterface.setPurchaseReportChannels()[i2]);
                if (!purchaseReportChannels.contains(initInterface.setPurchaseReportChannels()[i2])) {
                    purchaseReportChannels.add(initInterface.setPurchaseReportChannels()[i2]);
                    IMLogger.d("setPurchaseReportChannels " + i2 + " is: " + initInterface.setPurchaseReportChannels()[i2] + "add to purchaseReportChannels!");
                }
            }
        }
        if (!(initInterface.setTrackEventReportChannels() == null || initInterface.setTrackEventReportChannels().length == 0)) {
            for (int i3 = 0; i3 < initInterface.setTrackEventReportChannels().length; i3++) {
                IMLogger.d("setTrackEventReportChannels " + i3 + " is: " + initInterface.setTrackEventReportChannels()[i3]);
                if (!trackEventReportChannels.contains(initInterface.setTrackEventReportChannels()[i3])) {
                    trackEventReportChannels.add(initInterface.setTrackEventReportChannels()[i3]);
                    IMLogger.d("setTrackEventReportChannels " + i3 + " is: " + initInterface.setTrackEventReportChannels()[i3] + " add to trackEventReportChannels!");
                }
            }
        }
        if (!(initInterface.setTrackPageChannels() == null || initInterface.setTrackPageChannels().length == 0)) {
            for (int i4 = 0; i4 < initInterface.setTrackPageChannels().length; i4++) {
                IMLogger.d("setTrackPageChannels " + i4 + " is: " + initInterface.setTrackPageChannels()[i4]);
                if (!trackPagChannels.contains(initInterface.setTrackPageChannels()[i4])) {
                    trackPagChannels.add(initInterface.setTrackPageChannels()[i4]);
                    IMLogger.d("setTrackPageChannels " + i4 + " is: " + initInterface.setTrackPageChannels()[i4] + "add to trackPagChannels!");
                }
            }
        }
        if (!(initInterface.setTestSpeedReportChannels() == null || initInterface.setTestSpeedReportChannels().length == 0)) {
            for (int i5 = 0; i5 < initInterface.setTestSpeedReportChannels().length; i5++) {
                IMLogger.d("setTestSpeedReportChannels " + i5 + " is: " + initInterface.setTestSpeedReportChannels()[i5]);
                if (!testSpeedReportChannels.contains(initInterface.setTestSpeedReportChannels()[i5])) {
                    testSpeedReportChannels.add(initInterface.setTestSpeedReportChannels()[i5]);
                    IMLogger.d("setTestSpeedReportChannels " + i5 + " is: " + initInterface.setTestSpeedReportChannels()[i5] + " add to testSpeedReportChannels!");
                }
            }
        }
        if (!(initInterface.setExceptionReportChannels() == null || initInterface.setExceptionReportChannels().length == 0)) {
            for (int i6 = 0; i6 < initInterface.setExceptionReportChannels().length; i6++) {
                IMLogger.d("setExceptionReportChannels " + i6 + " is: " + initInterface.setExceptionReportChannels()[i6]);
                if (!exceptionReportChannels.contains(initInterface.setExceptionReportChannels()[i6])) {
                    exceptionReportChannels.add(initInterface.setExceptionReportChannels()[i6]);
                    IMLogger.d("setExceptionReportChannels " + i6 + " is: " + initInterface.setExceptionReportChannels()[i6] + " add to exceptionReportChannels!");
                }
            }
        }
        if (!(initInterface.setCrashReportChannels() == null || initInterface.setCrashReportChannels().length == 0)) {
            for (int i7 = 0; i7 < initInterface.setCrashReportChannels().length; i7++) {
                IMLogger.d("setCrashReportChannels " + i7 + " is: " + initInterface.setCrashReportChannels()[i7]);
                if (!crashReportChannels.contains(initInterface.setCrashReportChannels()[i7])) {
                    crashReportChannels.add(initInterface.setCrashReportChannels()[i7]);
                    IMLogger.d("setCrashReportChannels " + i7 + " is: " + initInterface.setCrashReportChannels()[i7] + " add to crashReportChannels!");
                }
            }
        }
        initialize(context);
    }

    public void reportEvent(String eventName, boolean isRealTime) {
        if (eventReportChannels != null && eventReportChannels.size() != 0 && eventReportChannels.contains(AppsFlyer_Channel)) {
            IMLogger.d("IMAppsflyer reportEvent  name:" + eventName + "; body is null");
            HashMap<String, Object> eventMap = new HashMap<>();
            if (this.mContext != null) {
                setOpenIDWhileNotSame();
                AppsFlyerLib.getInstance().trackEvent(this.mContext.getApplicationContext(), eventName, eventMap);
                return;
            }
            IMLogger.w("AppsFlyerStatHelper context is null, please check init");
        }
    }

    public void reportEvent(String eventName, boolean isRealTime, String eventValue) {
        IMLogger.d("eventReportChannels.size is: " + eventReportChannels.size());
        for (int i = 0; i < eventReportChannels.size(); i++) {
            IMLogger.d("eventReportChannels " + i + "is: " + eventReportChannels.get(i));
        }
        if (eventReportChannels != null && eventReportChannels.size() != 0 && eventReportChannels.contains(AppsFlyer_Channel)) {
            IMLogger.d("AppsFlyerStatHelper reportEvent  name:" + eventName + "; body: " + eventValue);
            HashMap<String, Object> eventMap = new HashMap<>();
            if (this.mContext != null) {
                if (eventValue != null) {
                    eventMap.put("eventValue", eventValue);
                } else {
                    IMLogger.d("AppsFlyerStatHelper eventValue is null");
                }
                setOpenIDWhileNotSame();
                AppsFlyerLib.getInstance().trackEvent(this.mContext.getApplicationContext(), eventName, eventMap);
                return;
            }
            IMLogger.w("AppsFlyerStatHelper context is null, please check init");
        }
    }

    public void reportEvent(String eventName, HashMap<String, String> params, boolean isRealTime) {
        if (eventReportChannels != null && eventReportChannels.size() != 0 && eventReportChannels.contains(AppsFlyer_Channel)) {
            IMLogger.d("AppsFlyerStatHelper reportEvent  name:" + eventName + "; body: " + params.toString());
            HashMap<String, Object> eventParams = new HashMap<>();
            for (Map.Entry<String, String> entry : params.entrySet()) {
                eventParams.put(entry.getKey(), entry.getValue());
            }
            if (this.mContext != null) {
                setOpenIDWhileNotSame();
                AppsFlyerLib.getInstance().trackEvent(this.mContext.getApplicationContext(), eventName, eventParams);
                return;
            }
            IMLogger.w("AppsFlyerStatHelper context is null, please check init");
        }
    }

    public void reportPurchase(String purchaseName, String currencyCode, String expense, boolean isRealTime) {
        if (purchaseReportChannels != null && purchaseReportChannels.size() != 0 && purchaseReportChannels.contains(AppsFlyer_Channel)) {
            IMLogger.d("AppsFlyerStatHelper reportPurchase  name:" + purchaseName + " and the price is: " + expense + " and the currencyCode is: " + currencyCode);
            Map<String, Object> event = new HashMap<>();
            event.put(AFInAppEventParameterName.PRICE, expense);
            event.put(AFInAppEventParameterName.CURRENCY, currencyCode);
            event.put(AFInAppEventParameterName.QUANTITY, 1);
            setOpenIDWhileNotSame();
            AppsFlyerLib.getInstance().trackEvent(this.mContext, AFInAppEventType.PURCHASE, event);
        }
    }

    public void trackEventBegin(String eventName, String eventBody) {
        if (trackEventReportChannels != null && trackEventReportChannels.size() != 0 && trackEventReportChannels.contains(AppsFlyer_Channel)) {
            IMLogger.w("AppsFlyerStatHelper trackEventBegin  name:" + eventName + ", But Appsflyer does not support this trackEventEnd feature, please try MTA");
        }
    }

    public void trackEventEnd(String eventName, String eventBody) {
        if (trackEventReportChannels != null && trackEventReportChannels.size() != 0 && trackEventReportChannels.contains(AppsFlyer_Channel)) {
            IMLogger.w("AppsFlyerStatHelper trackEventEnd  name:" + eventName + ", But Appsflyer does not support this trackEventEnd feature, please try MTA");
        }
    }

    public void trackPageBegin(String pageName) {
        if (trackEventReportChannels != null && trackEventReportChannels.size() != 0 && trackEventReportChannels.contains(AppsFlyer_Channel)) {
            IMLogger.w("AppsFlyerStatHelper trackPageBegin  name:" + pageName + ", But Appsflyer does not support this trackPageBegin feature, please try MTA");
        }
    }

    public void trackPageEnd(String pageName) {
        if (trackPagChannels != null && trackPagChannels.size() != 0 && trackPagChannels.contains(AppsFlyer_Channel)) {
            IMLogger.w("AppsFlyerStatHelper trackPageEnd  name:" + pageName + ", But Appsflyer does not support this trackPageEnd feature, please try MTA");
        }
    }

    public void speedTest(HashMap<String, Integer> hostMap) {
        if (testSpeedReportChannels != null && testSpeedReportChannels.size() != 0 && testSpeedReportChannels.contains(AppsFlyer_Channel)) {
            IMLogger.w("AppsFlyerStatHelper speedTest: " + hostMap.toString() + ", But Appsflyer does not support this speedTest feature, please try MTA or Beacon");
        }
    }

    public void speedTest(ArrayList<String> domainList) {
        if (testSpeedReportChannels != null && testSpeedReportChannels.size() != 0 && testSpeedReportChannels.contains(AppsFlyer_Channel)) {
            IMLogger.w("AppsFlyerStatHelper speedTest: " + domainList.toString() + ", But Appsflyer does not support this speedTest feature, please try MTA or Beacon");
        }
    }

    public void reportException(Throwable exception) {
        if (exceptionReportChannels != null && exceptionReportChannels.size() != 0 && exceptionReportChannels.contains(AppsFlyer_Channel)) {
            IMLogger.w("AppsFlyerStatHelper reportException: " + exception.toString() + ", But Appsflyer does not support this reportException feature, please try other channel");
        }
    }

    public void reportAutoExceptionReport(boolean flag) {
        if (exceptionReportChannels != null && exceptionReportChannels.size() != 0 && exceptionReportChannels.contains(AppsFlyer_Channel)) {
            IMLogger.w("AppsFlyerStatHelper reportAutoExceptionReport switch is: " + (flag ? "open" : PreviewActivity.ON_CLICK_LISTENER_CLOSE) + " But Appsflyer does not support this reportException feature, please try other channel");
        }
    }

    public void reportCrash(boolean flag) {
        if (crashReportChannels != null && crashReportChannels.size() != 0 && crashReportChannels.contains(AppsFlyer_Channel)) {
            IMLogger.w("AppsFlyerStatHelper reportCrash switch is: " + (flag ? "open" : PreviewActivity.ON_CLICK_LISTENER_CLOSE) + "But Appsflyer does not support this reportException feature, please try other channel");
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:12:0x002e, code lost:
        if (r1.trim().equalsIgnoreCase(com.amazonaws.services.s3.internal.Constants.NULL_VERSION_ID) == false) goto L_0x0063;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.String getStatIMEI() {
        /*
            r6 = this;
            java.lang.String r1 = ""
            android.content.Context r4 = r6.mContext
            if (r4 == 0) goto L_0x0063
            android.content.Context r4 = r6.mContext     // Catch:{ Exception -> 0x004c }
            java.lang.String r5 = "phone"
            java.lang.Object r3 = r4.getSystemService(r5)     // Catch:{ Exception -> 0x004c }
            android.telephony.TelephonyManager r3 = (android.telephony.TelephonyManager) r3     // Catch:{ Exception -> 0x004c }
            if (r3 == 0) goto L_0x0078
            java.lang.String r1 = r3.getDeviceId()     // Catch:{ Exception -> 0x0034 }
        L_0x0016:
            if (r1 == 0) goto L_0x0030
            java.lang.String r4 = r1.trim()     // Catch:{ Exception -> 0x004c }
            java.lang.String r5 = ""
            boolean r4 = r4.equalsIgnoreCase(r5)     // Catch:{ Exception -> 0x004c }
            if (r4 != 0) goto L_0x0030
            java.lang.String r4 = r1.trim()     // Catch:{ Exception -> 0x004c }
            java.lang.String r5 = "null"
            boolean r4 = r4.equalsIgnoreCase(r5)     // Catch:{ Exception -> 0x004c }
            if (r4 == 0) goto L_0x0063
        L_0x0030:
            java.lang.String r4 = ""
            r2 = r1
        L_0x0033:
            return r4
        L_0x0034:
            r0 = move-exception
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x004c }
            java.lang.String r5 = "AppsFlyerStatHelper getStatIMEI catch exception : "
            r4.<init>(r5)     // Catch:{ Exception -> 0x004c }
            java.lang.String r5 = r0.getMessage()     // Catch:{ Exception -> 0x004c }
            java.lang.StringBuilder r4 = r4.append(r5)     // Catch:{ Exception -> 0x004c }
            java.lang.String r4 = r4.toString()     // Catch:{ Exception -> 0x004c }
            com.tencent.imsdk.tool.etc.IMLogger.d((java.lang.String) r4)     // Catch:{ Exception -> 0x004c }
            goto L_0x0016
        L_0x004c:
            r0 = move-exception
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            java.lang.String r5 = "AppsFlyerStatHelper getStatIMEI catch exception : "
            r4.<init>(r5)
            java.lang.String r5 = r0.getMessage()
            java.lang.StringBuilder r4 = r4.append(r5)
            java.lang.String r4 = r4.toString()
            com.tencent.imsdk.tool.etc.IMLogger.d((java.lang.String) r4)
        L_0x0063:
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            java.lang.String r5 = "AppsFlyerStatHelper imei is : "
            r4.<init>(r5)
            java.lang.StringBuilder r4 = r4.append(r1)
            java.lang.String r4 = r4.toString()
            com.tencent.imsdk.tool.etc.IMLogger.d((java.lang.String) r4)
            r2 = r1
            r4 = r1
            goto L_0x0033
        L_0x0078:
            java.lang.String r1 = ""
            goto L_0x0016
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.imsdk.statics.appsflyer.AppsFlyerStatHelper.getStatIMEI():java.lang.String");
    }

    public String getStatMID() {
        return null;
    }

    private String getOpenID() {
        IMLoginResult loginResult = IMLogin.getLoginResult();
        if (loginResult == null || ((loginResult.retCode != IMErrorDef.SUCCESS && loginResult.imsdkRetCode != 1) || loginResult.openId == null)) {
            return "";
        }
        return loginResult.openId;
    }

    private void setOpenIDWhileNotSame() {
        String newOpenID = getOpenID();
        if (!newOpenID.equals(this.mLastOpenID)) {
            AppsFlyerLib.getInstance().setCustomerUserId(newOpenID);
            IMLogger.d("CustomerUserId changed from " + this.mLastOpenID + " to  " + newOpenID);
            this.mLastOpenID = newOpenID;
        }
    }

    private String getAndroidId() {
        String androidId = "";
        if (this.mContext != null) {
            try {
                androidId = Settings.Secure.getString(this.mContext.getContentResolver(), "android_id");
            } catch (Exception e) {
                IMLogger.w("get androidId catch exception : " + e.getMessage());
            }
        }
        IMLogger.d("AppsFlyerStatHelper androidId is : " + androidId);
        return androidId;
    }

    private String getStringMetaData(String key) {
        if (this.mContext != null) {
            String value = MetaDataUtil.readMetaDataFromApplication(this.mContext, key);
            IMLogger.d("AppsFlyerStatHelper with key : " + key + " is : " + value);
            return value;
        }
        IMLogger.w("AppsFlyerStatHelper context is null, please check init");
        return "";
    }

    private boolean getBooleanMetaData(String key) {
        if (this.mContext != null) {
            boolean value = MetaDataUtil.readMetaDataFromApplication(this.mContext, key, false);
            IMLogger.d("AppsFlyerStatHelper with key : " + key + " is : " + value);
            return value;
        }
        IMLogger.w("AppsFlyerStatHelper context is null, please check init");
        return false;
    }

    public void initialize(final Context context) {
        this.mContext = context;
        if (context == null || this.mHasInitializebyExtend) {
            IMLogger.d("Please call initialize first");
            return;
        }
        try {
            String imei = getStatIMEI();
            String androidId = getAndroidId();
            AppsFlyerLib.getInstance().setImeiData(imei);
            AppsFlyerLib.getInstance().setAndroidIdData(androidId);
            IMLogger.d("AppsFlyerStatHelper imei is : " + imei + " androidId is : " + androidId);
            boolean isDebug = getBooleanMetaData(IS_DEBUG_KEY);
            String gcmProjectNum = getStringMetaData(GCM_PROJECT_NUM_KEY);
            final String appsflyerDevKey = getStringMetaData(DEVKEY_APPSFLYER_KEY);
            this.mLastOpenID = getOpenID();
            AppsFlyerLib.getInstance().setCustomerUserId(this.mLastOpenID);
            AppsFlyerLib.getInstance().setDebugLog(isDebug);
            AppsFlyerLib.getInstance().setGCMProjectNumber(gcmProjectNum);
            try {
                ((Activity) context).runOnUiThread(new Runnable() {
                    public void run() {
                        AppsFlyerLib.getInstance().startTracking(((Activity) context).getApplication(), appsflyerDevKey);
                    }
                });
            } catch (Exception e) {
                IMLogger.e(e.getMessage());
            }
            AppsFlyerLib.getInstance().startTracking(((Activity) context).getApplication(), appsflyerDevKey);
            this.mHasInitializebyExtend = true;
        } catch (Exception e2) {
            IMLogger.d("ExtendAppsFlyer onCreate catch exception : " + e2.getMessage());
            this.mHasInitializebyExtend = false;
        }
    }
}
