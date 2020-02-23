package com.tencent.imsdk.statics.beacon;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.text.TextUtils;
import com.tencent.beacon.event.UserAction;
import com.tencent.imsdk.config.Channel;
import com.tencent.imsdk.stat.api.IMStatPlatInterface;
import com.tencent.imsdk.stat.base.IMStatBase;
import com.tencent.imsdk.tool.etc.IMLogger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class BeaconStatHelper extends IMStatBase {
    private static BeaconStatHelper beaconStatHelper;
    private static LinkedList<Channel> crashReportChannels = new LinkedList<>();
    private static LinkedList<Channel> eventReportChannels = new LinkedList<>();
    private static LinkedList<Channel> exceptionReportChannels = new LinkedList<>();
    private static Object lock = new Object();
    private static LinkedList<Channel> purchaseReportChannels = new LinkedList<>();
    private static LinkedList<Channel> testSpeedReportChannels = new LinkedList<>();
    private static LinkedList<Channel> trackEventReportChannels = new LinkedList<>();
    private static LinkedList<Channel> trackPagChannels = new LinkedList<>();
    private final String DEBUG_META_NAME = "com.tencent.imsdk.IsDebug";
    protected Context ctx;

    public static BeaconStatHelper getInstance() {
        if (beaconStatHelper == null) {
            synchronized (lock) {
                if (beaconStatHelper == null) {
                    beaconStatHelper = new BeaconStatHelper();
                }
            }
        }
        return beaconStatHelper;
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
        ApplicationInfo appInfo = new ApplicationInfo();
        try {
            appInfo = this.ctx.getPackageManager().getApplicationInfo(this.ctx.getPackageName(), 128);
        } catch (Exception e) {
            IMLogger.e("get application info error : " + e.getMessage());
        }
        UserAction.initUserAction(context);
        boolean isDebug = appInfo.metaData.getBoolean("com.tencent.imsdk.IsDebug", false);
        if (!isDebug) {
            isDebug = appInfo.metaData.getBoolean("IS_DEBUG", false);
        }
        UserAction.setLogAble(isDebug, false);
    }

    public void reportEvent(String eventName, boolean isRealTime) {
        if (eventReportChannels != null && eventReportChannels.size() != 0 && eventReportChannels.contains(Channel.eChannel_Beacon)) {
            IMLogger.d("IMBeacon reportEvent  name:" + eventName + "; body is null");
            HashMap<String, String> extraMap = new HashMap<>();
            extraMap.put("eventBody", "");
            UserAction.onUserAction(eventName, true, 0, -1, extraMap, isRealTime);
        }
    }

    public void reportEvent(String eventName, boolean isRealTime, String eventBody) {
        if (eventReportChannels != null && eventReportChannels.size() != 0 && eventReportChannels.contains(Channel.eChannel_Beacon)) {
            IMLogger.d("IMBeacon reportEvent  name:" + eventName + "; body: " + eventBody);
            HashMap<String, String> extraMap = new HashMap<>();
            extraMap.put("eventBody", eventBody);
            UserAction.onUserAction(eventName, true, 0, -1, extraMap, isRealTime);
        }
    }

    public void reportEvent(String eventName, HashMap<String, String> params, boolean isRealTime) {
        if (eventReportChannels != null && eventReportChannels.size() != 0 && eventReportChannels.contains(Channel.eChannel_Beacon)) {
            IMLogger.d("IMBeacon reportEvent  name:" + eventName + "; body: " + params.toString());
            UserAction.onUserAction(eventName, true, 0, -1, params, isRealTime);
        }
    }

    public void reportPurchase(String purchaseName, String currencyCode, String expense, boolean isRealTime) {
        if (purchaseReportChannels != null && purchaseReportChannels.size() != 0 && purchaseReportChannels.contains(Channel.eChannel_Beacon)) {
            IMLogger.w("IMBeacon reportPurchase  name:" + purchaseName + ", But Beacon does not support this reportPurchase feature, please try Facebook and Appsflyer.");
        }
    }

    public void trackEventBegin(String eventName, String eventBody) {
        if (trackEventReportChannels != null && trackEventReportChannels.size() != 0 && trackEventReportChannels.contains(Channel.eChannel_Beacon)) {
            IMLogger.w("IMBeacon trackEventBegin  name:" + eventName + ", But Beacon does not support this trackEventEnd feature, please try MTA");
        }
    }

    public void trackEventEnd(String eventName, String eventBody) {
        if (trackEventReportChannels != null && trackEventReportChannels.size() != 0 && trackEventReportChannels.contains(Channel.eChannel_Beacon)) {
            IMLogger.w("IMBeacon trackEventEnd  name:" + eventName + ", But Beacon does not support this trackEventEnd feature, please try MTA");
        }
    }

    public void trackPageBegin(String pageName) {
        if (trackEventReportChannels != null && trackEventReportChannels.size() != 0 && trackEventReportChannels.contains(Channel.eChannel_Beacon)) {
            IMLogger.w("IMBeacon trackPageBegin  name:" + pageName + ", But Beacon does not support this trackPageBegin feature, please try MTA");
        }
    }

    public void trackPageEnd(String pageName) {
        if (trackEventReportChannels != null && trackEventReportChannels.size() != 0 && trackEventReportChannels.contains(Channel.eChannel_Beacon)) {
            IMLogger.w("IMBeacon trackPageEnd  name:" + pageName + ", But Beacon does not support this trackPageEnd feature, please try MTA");
        }
    }

    public void speedTest(HashMap<String, Integer> hostMap) {
        if (testSpeedReportChannels != null && testSpeedReportChannels.size() != 0 && testSpeedReportChannels.contains(Channel.eChannel_Beacon)) {
            IMLogger.d("IMBeacon speedTest  host name:" + hostMap.toString());
            ArrayList<String> hostList = new ArrayList<>();
            for (Map.Entry<String, Integer> entry : hostMap.entrySet()) {
                hostList.add(entry.getKey() + ":" + entry.getValue().intValue());
            }
            UserAction.testSpeedIp(hostList);
        }
    }

    public void speedTest(ArrayList<String> domainList) {
        if (testSpeedReportChannels != null && testSpeedReportChannels.size() != 0 && testSpeedReportChannels.contains(Channel.eChannel_Beacon)) {
            IMLogger.d("IMBeacon speedTest domainList:" + domainList.toString());
            UserAction.testSpeedDomain(domainList);
        }
    }

    public void reportException(Throwable exception) {
        if (exceptionReportChannels != null && exceptionReportChannels.size() != 0 && exceptionReportChannels.contains(Channel.eChannel_Beacon)) {
            IMLogger.w("IMBeacon no support for reportException :" + exception);
        }
    }

    public void reportAutoExceptionReport(boolean flag) {
        if (exceptionReportChannels != null && exceptionReportChannels.size() != 0 && exceptionReportChannels.contains(Channel.eChannel_Beacon)) {
            IMLogger.w("IMBeacon no support for reportAutoExceptionReport !");
        }
    }

    public void reportCrash(boolean flag) {
        if (crashReportChannels != null && crashReportChannels.size() != 0 && crashReportChannels.contains(Channel.eChannel_Beacon)) {
            IMLogger.w("IMBeacon no support for reportCrash !");
        }
    }

    public String getStatIMEI() {
        String imei = "";
        if (eventReportChannels == null || eventReportChannels.contains(Channel.eChannel_Beacon) || purchaseReportChannels == null || purchaseReportChannels.contains(Channel.eChannel_Beacon) || trackPagChannels == null || trackPagChannels.contains(Channel.eChannel_Beacon) || testSpeedReportChannels == null || testSpeedReportChannels.contains(Channel.eChannel_Beacon) || trackEventReportChannels == null || trackEventReportChannels.contains(Channel.eChannel_Beacon) || exceptionReportChannels == null || exceptionReportChannels.contains(Channel.eChannel_Beacon) || crashReportChannels == null || crashReportChannels.contains(Channel.eChannel_Beacon)) {
            imei = UserAction.getQIMEI();
            if (TextUtils.isEmpty(imei)) {
                IMLogger.w("IMBeacon", "Beacon getQIMEI failed!");
            }
        }
        return imei;
    }

    public String getStatMID() {
        return null;
    }
}
