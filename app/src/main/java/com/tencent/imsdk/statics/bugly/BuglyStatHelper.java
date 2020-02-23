package com.tencent.imsdk.statics.bugly;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import com.google.android.gms.appinvite.PreviewActivity;
import com.tencent.bugly.crashreport.CrashReport;
import com.tencent.imsdk.config.Channel;
import com.tencent.imsdk.expansion.downloader.Constants;
import com.tencent.imsdk.sns.api.IMLogin;
import com.tencent.imsdk.sns.base.IMLoginResult;
import com.tencent.imsdk.stat.api.IMStatPlatInterface;
import com.tencent.imsdk.stat.base.IMStatBase;
import com.tencent.imsdk.stat.innerapi.IMInnerStat;
import com.tencent.imsdk.tool.etc.IMLogger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class BuglyStatHelper extends IMStatBase {
    private static final String CHANNEL = "Bugly";
    private static final int IMSDK_SUCCESS_CODE = 1;
    static final String TAG = "IMBugly";
    private static final String VERSION = "1.12.0";
    private static BuglyStatHelper buglyStatHelper;
    private static LinkedList<Channel> crashReportChannels = new LinkedList<>();
    private static LinkedList<Channel> eventReportChannels = new LinkedList<>();
    private static LinkedList<Channel> exceptionReportChannels = new LinkedList<>();
    private static Object lock = new Object();
    private static LinkedList<Channel> purchaseReportChannels = new LinkedList<>();
    private static LinkedList<Channel> testSpeedReportChannels = new LinkedList<>();
    private static LinkedList<Channel> trackEventReportChannels = new LinkedList<>();
    private static LinkedList<Channel> trackPagChannels = new LinkedList<>();
    protected Context ctx;
    boolean isConfirm;
    boolean isrestore;
    protected IMInnerStat mInnerStat;

    public static BuglyStatHelper getInstance() {
        if (buglyStatHelper == null) {
            synchronized (lock) {
                if (buglyStatHelper == null) {
                    buglyStatHelper = new BuglyStatHelper();
                }
            }
        }
        return buglyStatHelper;
    }

    public void init(Context context, IMStatPlatInterface initInterface) {
        if (!(initInterface.setEventReportChannels() == null || initInterface.setEventReportChannels().length == 0)) {
            for (int i = 0; i < initInterface.setEventReportChannels().length; i++) {
                for (Channel p : Channel.values()) {
                    if (p.val().equalsIgnoreCase(initInterface.setEventReportChannels()[i]) && !eventReportChannels.contains(p)) {
                        eventReportChannels.add(p);
                    }
                }
            }
        }
        if (!(initInterface.setPurchaseReportChannels() == null || initInterface.setPurchaseReportChannels().length == 0)) {
            for (int i2 = 0; i2 < initInterface.setPurchaseReportChannels().length; i2++) {
                for (Channel p2 : Channel.values()) {
                    if (p2.val().equalsIgnoreCase(initInterface.setPurchaseReportChannels()[i2]) && !purchaseReportChannels.contains(p2)) {
                        purchaseReportChannels.add(p2);
                    }
                }
            }
        }
        if (!(initInterface.setTrackEventReportChannels() == null || initInterface.setTrackEventReportChannels().length == 0)) {
            for (int i3 = 0; i3 < initInterface.setTrackEventReportChannels().length; i3++) {
                for (Channel p3 : Channel.values()) {
                    if (p3.val().equalsIgnoreCase(initInterface.setTrackEventReportChannels()[i3]) && !trackEventReportChannels.contains(p3)) {
                        trackEventReportChannels.add(p3);
                    }
                }
            }
        }
        if (!(initInterface.setTrackPageChannels() == null || initInterface.setTrackPageChannels().length == 0)) {
            for (int i4 = 0; i4 < initInterface.setTrackPageChannels().length; i4++) {
                for (Channel p4 : Channel.values()) {
                    if (p4.val().equalsIgnoreCase(initInterface.setTrackPageChannels()[i4]) && !trackPagChannels.contains(p4)) {
                        trackPagChannels.add(p4);
                    }
                }
            }
        }
        if (!(initInterface.setTestSpeedReportChannels() == null || initInterface.setTestSpeedReportChannels().length == 0)) {
            for (int i5 = 0; i5 < initInterface.setTestSpeedReportChannels().length; i5++) {
                for (Channel p5 : Channel.values()) {
                    if (p5.val().equalsIgnoreCase(initInterface.setTestSpeedReportChannels()[i5]) && !testSpeedReportChannels.contains(p5)) {
                        testSpeedReportChannels.add(p5);
                    }
                }
            }
        }
        if (!(initInterface.setExceptionReportChannels() == null || initInterface.setExceptionReportChannels().length == 0)) {
            for (int i6 = 0; i6 < initInterface.setExceptionReportChannels().length; i6++) {
                for (Channel p6 : Channel.values()) {
                    if (p6.val().equalsIgnoreCase(initInterface.setExceptionReportChannels()[i6]) && !exceptionReportChannels.contains(p6)) {
                        exceptionReportChannels.add(p6);
                    }
                }
            }
        }
        if (!(initInterface.setCrashReportChannels() == null || initInterface.setCrashReportChannels().length == 0)) {
            for (int i7 = 0; i7 < initInterface.setCrashReportChannels().length; i7++) {
                for (Channel p7 : Channel.values()) {
                    if (p7.val().equalsIgnoreCase(initInterface.setCrashReportChannels()[i7]) && !crashReportChannels.contains(p7)) {
                        crashReportChannels.add(p7);
                    }
                }
            }
        }
        this.ctx = context;
        Activity activity = (Activity) this.ctx;
        Context appContext = activity.getApplicationContext();
        ApplicationInfo appInfo = new ApplicationInfo();
        try {
            appInfo = this.ctx.getPackageManager().getApplicationInfo(this.ctx.getPackageName(), 128);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        String appId = appInfo.metaData.getString("APPID_BUGLY");
        boolean isDebug = appInfo.metaData.getBoolean("IS_DEBUG", false);
        if (appId.length() >= 5) {
            try {
                if (appId.startsWith("bugly") || appId.contains(CHANNEL)) {
                    appId = appId.substring(5, appId.length());
                }
            } catch (Exception ex) {
                IMLogger.d((Object) ex);
            }
        } else {
            IMLogger.w("Bugly appid is wrong, please configure the correct bugly appid in the AndroidManifest.xml!");
        }
        IMLogger.d("Bugly appid is: " + appId);
        if (appId == null || appId.length() == 0) {
            IMLogger.w("Bugly appid is wrong, please configure the correct bugly appid  in the AndroidManifest.xml!");
        }
        CrashReport.UserStrategy userStrategy = new CrashReport.UserStrategy(activity.getApplicationContext());
        userStrategy.setAppReportDelay(Constants.ACTIVE_THREAD_WATCHDOG);
        userStrategy.setCrashHandleCallback(new CrashReport.CrashHandleCallback() {
            public synchronized Map<String, String> onCrashHandleStart(int crashType, String errorType, String errorMessage, String errorStack) {
                String crashTypeName;
                Map<String, String> userDatas;
                switch (crashType) {
                    case 0:
                        crashTypeName = "JAVA_CRASH";
                        break;
                    case 1:
                        crashTypeName = "JAVA_CATCH";
                        break;
                    case 2:
                        crashTypeName = "JAVA_NATIVE";
                        break;
                    case 3:
                        crashTypeName = "JAVA_U3D";
                        break;
                    default:
                        crashTypeName = "unknown";
                        break;
                }
                IMLogger.w("Crash Happen Type:" + crashType + " TypeName:" + crashTypeName);
                IMLogger.w("errorType:" + errorType);
                IMLogger.w("errorMessage:" + errorMessage);
                IMLogger.w("errorStack:" + errorStack);
                userDatas = super.onCrashHandleStart(crashType, errorType, errorMessage, errorStack);
                if (userDatas == null) {
                    userDatas = new HashMap<>();
                }
                userDatas.put("extmsg", "The Extre Message");
                return userDatas;
            }
        });
        CrashReport.initCrashReport(appContext, appId, isDebug, userStrategy);
        setOpenId();
        if (this.mInnerStat == null) {
            this.mInnerStat = new IMInnerStat(context, getStatVersion());
            this.mInnerStat.reportEvent(getStatID(), true, "initialize", "create", "success", getStatOpenId(), IMInnerStat.convertProperties(context));
        }
    }

    public void reportEvent(String eventName, boolean isRealTime) {
        if (eventReportChannels != null && eventReportChannels.size() != 0 && eventReportChannels.contains(Channel.eChannel_Bugly)) {
            IMLogger.w("IMBugly reportEvent  name:" + eventName + "; body is null, But Bugly does not support this feature");
        }
    }

    public void reportEvent(String eventName, boolean isRealTime, String eventBody) {
        if (eventReportChannels != null && eventReportChannels.size() != 0 && eventReportChannels.contains(Channel.eChannel_Bugly)) {
            IMLogger.w("IMBugly reportEvent  name:" + eventName + "; body: " + eventBody + ", But Bugly does not support this feature");
        }
    }

    public void reportEvent(String eventName, HashMap<String, String> params, boolean isRealTime) {
        if (eventReportChannels != null && eventReportChannels.size() != 0 && eventReportChannels.contains(Channel.eChannel_Bugly)) {
            IMLogger.w("IMBugly reportEvent  name:" + eventName + "; body: " + params.toString() + ", But Bugly does not support this feature");
        }
    }

    public void reportPurchase(String purchaseName, String currencyCode, String expense, boolean isRealTime) {
        if (purchaseReportChannels != null && purchaseReportChannels.size() != 0 && purchaseReportChannels.contains(Channel.eChannel_Bugly)) {
            IMLogger.w("IMBugly reportPurchase  name:" + purchaseName + ", But Bugly does not support this reportPurchase feature, please try Facebook and Appsflyer.");
        }
    }

    public void trackEventBegin(String eventName, String eventBody) {
        if (trackEventReportChannels != null && trackEventReportChannels.size() != 0 && trackEventReportChannels.contains(Channel.eChannel_Bugly)) {
            IMLogger.w("IMBugly trackEventBegin  name:" + eventName + ", But Bugly does not support this trackEventEnd feature, please try MTA");
        }
    }

    public void trackEventEnd(String eventName, String eventBody) {
        if (trackEventReportChannels != null && trackEventReportChannels.size() != 0 && trackEventReportChannels.contains(Channel.eChannel_Bugly)) {
            IMLogger.w("IMBugly trackEventEnd  name:" + eventName + ", But Bugly does not support this trackEventEnd feature, please try MTA");
        }
    }

    public void trackPageBegin(String pageName) {
        if (trackEventReportChannels != null && trackEventReportChannels.size() != 0 && trackEventReportChannels.contains(Channel.eChannel_Bugly)) {
            IMLogger.w("IMBugly trackPageBegin  name:" + pageName + ", But Bugly does not support this trackPageBegin feature, please try MTA");
        }
    }

    public void trackPageEnd(String pageName) {
        if (trackEventReportChannels != null && trackEventReportChannels.size() != 0 && trackEventReportChannels.contains(Channel.eChannel_Bugly)) {
            IMLogger.w("IMBugly trackPageEnd  name:" + pageName + ", But Bugly does not support this trackPageEnd feature, please try MTA");
        }
    }

    public void speedTest(HashMap<String, Integer> hostMap) {
        if (testSpeedReportChannels != null && testSpeedReportChannels.size() != 0 && testSpeedReportChannels.contains(Channel.eChannel_Bugly)) {
            IMLogger.w("IMBugly speedTest  domainList:" + hostMap.toString() + ", But Bugly does not support this formation , please try MTA and Beacon");
        }
    }

    public void speedTest(ArrayList<String> domainList) {
        if (testSpeedReportChannels != null && testSpeedReportChannels.size() != 0 && testSpeedReportChannels.contains(Channel.eChannel_Bugly)) {
            IMLogger.w("IMBugly speedTest  domainList:" + domainList.toString() + ", But Bugly does not support this formation , please try Beacon ");
        }
    }

    public void reportException(Throwable exception) {
        if (exceptionReportChannels != null && exceptionReportChannels.size() != 0 && exceptionReportChannels.contains(Channel.eChannel_Bugly)) {
            IMLogger.d("IMBugly reportException :" + exception);
            CrashReport.postCatchedException(exception);
        }
    }

    public void reportAutoExceptionReport(boolean flag) {
        if (exceptionReportChannels != null && exceptionReportChannels.size() != 0 && exceptionReportChannels.contains(Channel.eChannel_Bugly)) {
            IMLogger.d("IMBugly reportAutoExceptionReport switch is: " + (flag ? "open" : PreviewActivity.ON_CLICK_LISTENER_CLOSE));
        }
    }

    public void reportCrash(boolean flag) {
        if (crashReportChannels != null && crashReportChannels.size() != 0 && crashReportChannels.contains(Channel.eChannel_Bugly)) {
            IMLogger.d("IMBugly reportCrash switch is: " + (flag ? "open" : PreviewActivity.ON_CLICK_LISTENER_CLOSE));
        }
    }

    public String getStatIMEI() {
        return null;
    }

    public String getStatMID() {
        return null;
    }

    private void setOpenId() {
        String openId = getStatOpenId();
        if (openId != null) {
            CrashReport.setUserId(openId);
        } else {
            CrashReport.setUserId("");
        }
    }

    private String getStatOpenId() {
        IMLoginResult loginResult = IMLogin.getLoginResult();
        if (loginResult == null || (loginResult.retCode != 1 && loginResult.imsdkRetCode != 1)) {
            return null;
        }
        return loginResult.openId;
    }

    private String getChannel() {
        return CHANNEL;
    }

    private String getStatVersion() {
        return "1.12.0";
    }

    private String getStatID() {
        return "stat_" + getChannel().toLowerCase();
    }
}
