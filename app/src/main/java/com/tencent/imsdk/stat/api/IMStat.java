package com.tencent.imsdk.stat.api;

import android.content.Context;
import android.text.TextUtils;
import com.tencent.imsdk.IMModules;
import com.tencent.imsdk.stat.base.IMStatBase;
import com.tencent.imsdk.tool.etc.IMLogger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

public class IMStat {
    private static LinkedList<String> AllChannelList = new LinkedList<>();
    private static int ChannelSize = 0;
    private static Context context = null;
    private static String eChannel;
    private static LinkedList<String> statFilterChannels = new LinkedList<>();
    private static HashMap<IMStatBase, String> statInstanceHashMap = new HashMap<>();
    private static LinkedList<IMStatBase> statInstanceList = null;

    public static boolean isInitialize() {
        if (statInstanceList != null) {
            return true;
        }
        IMLogger.e("can not get instance : " + eChannel + ", did you add the package and call initialize function ?");
        return false;
    }

    public static String getChannel() {
        return eChannel;
    }

    private static void getInstance(String plat, IMStatPlatInterface initInterface) {
        eChannel = plat;
        String Channel = eChannel;
        IMLogger.d("switch Channel to : " + Channel);
        String ChannelClass = "com.tencent.imsdk.statics." + Channel.toLowerCase() + "." + Channel + "StatHelper";
        IMLogger.d("try to get class : " + ChannelClass);
        IMStatBase tempStatHelper = (IMStatBase) IMModules.getInstance().getModule(ChannelClass);
        if (tempStatHelper != null) {
            statInstanceList.add(tempStatHelper);
            statInstanceHashMap.put(tempStatHelper, plat);
            IMLogger.d("the switchChannel " + plat + " sucess");
            try {
                IMLogger.d("statFilterChannels size is :" + statFilterChannels.size());
                if (statFilterChannels.size() == 0) {
                    IMLogger.d("statInstanceList in statFilterChannels init method is: " + tempStatHelper.toString());
                    tempStatHelper.init(context, initInterface);
                } else if (statFilterChannels.contains(statInstanceHashMap.get(tempStatHelper))) {
                    IMLogger.d("statInstanceList in statFilterChannels init method is: " + tempStatHelper.toString());
                    tempStatHelper.init(context, initInterface);
                }
            } catch (Exception e) {
                IMLogger.e(e.getMessage());
                e.printStackTrace();
            }
        } else {
            IMLogger.e("the switchChannel " + plat + " fail");
        }
    }

    public static void initialize(Context ctx, IMStatPlatInterface initInterface) {
        try {
            IMLogger.d("stat is initing");
            if (initInterface == null) {
                IMLogger.w("The initInterface is null, you have to implement this interface");
                return;
            }
            if (!(initInterface.setEventReportChannels() == null || initInterface.setEventReportChannels().length == 0)) {
                for (int i = 0; i < initInterface.setEventReportChannels().length; i++) {
                    if (!AllChannelList.contains(initInterface.setEventReportChannels()[i])) {
                        AllChannelList.add(initInterface.setEventReportChannels()[i]);
                    }
                }
            }
            if (!(initInterface.setPurchaseReportChannels() == null || initInterface.setPurchaseReportChannels().length == 0)) {
                for (int i2 = 0; i2 < initInterface.setPurchaseReportChannels().length; i2++) {
                    if (!AllChannelList.contains(initInterface.setPurchaseReportChannels()[i2])) {
                        AllChannelList.add(initInterface.setPurchaseReportChannels()[i2]);
                    }
                }
            }
            if (!(initInterface.setTrackEventReportChannels() == null || initInterface.setTrackEventReportChannels().length == 0)) {
                for (int i3 = 0; i3 < initInterface.setTrackEventReportChannels().length; i3++) {
                    if (!AllChannelList.contains(initInterface.setTrackEventReportChannels()[i3])) {
                        AllChannelList.add(initInterface.setTrackEventReportChannels()[i3]);
                    }
                }
            }
            if (!(initInterface.setTrackPageChannels() == null || initInterface.setTrackPageChannels().length == 0)) {
                for (int i4 = 0; i4 < initInterface.setTrackPageChannels().length; i4++) {
                    if (!AllChannelList.contains(initInterface.setTrackPageChannels()[i4])) {
                        AllChannelList.add(initInterface.setTrackPageChannels()[i4]);
                    }
                }
            }
            if (!(initInterface.setTestSpeedReportChannels() == null || initInterface.setTestSpeedReportChannels().length == 0)) {
                for (int i5 = 0; i5 < initInterface.setTestSpeedReportChannels().length; i5++) {
                    if (!AllChannelList.contains(initInterface.setTestSpeedReportChannels()[i5])) {
                        AllChannelList.add(initInterface.setTestSpeedReportChannels()[i5]);
                    }
                }
            }
            if (!(initInterface.setExceptionReportChannels() == null || initInterface.setExceptionReportChannels().length == 0)) {
                for (int i6 = 0; i6 < initInterface.setExceptionReportChannels().length; i6++) {
                    if (!AllChannelList.contains(initInterface.setExceptionReportChannels()[i6])) {
                        AllChannelList.add(initInterface.setExceptionReportChannels()[i6]);
                    }
                }
            }
            if (!(initInterface.setCrashReportChannels() == null || initInterface.setCrashReportChannels().length == 0)) {
                for (int i7 = 0; i7 < initInterface.setCrashReportChannels().length; i7++) {
                    if (!AllChannelList.contains(initInterface.setCrashReportChannels()[i7])) {
                        AllChannelList.add(initInterface.setCrashReportChannels()[i7]);
                    }
                }
            }
            IMLogger.d("report channels : " + AllChannelList.size() + ", " + AllChannelList);
            ChannelSize = AllChannelList.size();
            statInstanceList = new LinkedList<>();
            for (int i8 = 0; i8 < ChannelSize; i8++) {
                context = ctx;
                getInstance(AllChannelList.get(i8), initInterface);
            }
            ChannelSize = statInstanceList.size();
        } catch (Exception e) {
            IMLogger.e(e.getMessage());
            e.printStackTrace();
        }
    }

    public static void reportEvent(String eventName, boolean isRealTime) {
        int i = 0;
        while (i < ChannelSize) {
            try {
                if (statInstanceList.get(i) != null) {
                    IMLogger.d("statFilterChannels size is :" + statFilterChannels.size());
                    if (statFilterChannels.size() == 0) {
                        IMLogger.d("statInstanceList get(i) is: " + statInstanceList.get(i).toString());
                        statInstanceList.get(i).reportEvent(eventName, isRealTime);
                    } else if (statFilterChannels.contains(statInstanceHashMap.get(statInstanceList.get(i)))) {
                        IMLogger.d("statInstanceList in statFilterChannels is: " + statInstanceList.get(i).toString());
                        statInstanceList.get(i).reportEvent(eventName, isRealTime);
                    }
                }
                i++;
            } catch (Exception e) {
                IMLogger.e(e.getMessage());
                e.printStackTrace();
                return;
            }
        }
    }

    public static void reportEvent(String eventName, boolean isRealTime, String eventBody) {
        int i = 0;
        while (i < ChannelSize) {
            try {
                if (statInstanceList.get(i) != null) {
                    IMLogger.d("statFilterChannels size is :" + statFilterChannels.size());
                    if (statFilterChannels.size() == 0) {
                        IMLogger.d("statInstanceList get(i) is: " + statInstanceList.get(i).toString());
                        statInstanceList.get(i).reportEvent(eventName, isRealTime, eventBody);
                    } else if (statFilterChannels.contains(statInstanceHashMap.get(statInstanceList.get(i)))) {
                        IMLogger.d("statInstanceList in statFilterChannels is:  " + statInstanceList.get(i).toString());
                        statInstanceList.get(i).reportEvent(eventName, isRealTime, eventBody);
                    }
                }
                i++;
            } catch (Exception e) {
                IMLogger.e(e.getMessage());
                e.printStackTrace();
                return;
            }
        }
    }

    public static void reportEvent(String eventName, HashMap<String, String> params, boolean isRealTime) {
        int i = 0;
        while (i < ChannelSize) {
            try {
                if (statInstanceList.get(i) != null) {
                    IMLogger.d("statFilterChannels size is :" + statFilterChannels.size());
                    if (statFilterChannels.size() == 0) {
                        IMLogger.d("statInstanceList get(i) is: " + statInstanceList.get(i).toString());
                        statInstanceList.get(i).reportEvent(eventName, params, isRealTime);
                    } else if (statFilterChannels.contains(statInstanceHashMap.get(statInstanceList.get(i)))) {
                        IMLogger.d("statInstanceList in statFilterChannels is:  " + statInstanceList.get(i).toString());
                        statInstanceList.get(i).reportEvent(eventName, params, isRealTime);
                    }
                }
                i++;
            } catch (Exception e) {
                IMLogger.e(e.getMessage());
                e.printStackTrace();
                return;
            }
        }
    }

    public static void reportPurchase(String purchaseName, String currencyCode, String expense, boolean isRealTime) {
        int i = 0;
        while (i < ChannelSize) {
            try {
                if (statInstanceList.get(i) != null) {
                    IMLogger.d("statFilterChannels size is :" + statFilterChannels.size());
                    if (statFilterChannels.size() == 0) {
                        IMLogger.d("statInstanceList get(i) is: " + statInstanceList.get(i).toString());
                        statInstanceList.get(i).reportPurchase(purchaseName, currencyCode, expense, isRealTime);
                    } else if (statFilterChannels.contains(statInstanceHashMap.get(statInstanceList.get(i)))) {
                        IMLogger.d("statInstanceList in statFilterChannels is: " + statInstanceList.get(i).toString());
                        statInstanceList.get(i).reportPurchase(purchaseName, currencyCode, expense, isRealTime);
                    }
                }
                i++;
            } catch (Exception e) {
                IMLogger.e(e.getMessage());
                e.printStackTrace();
                return;
            }
        }
    }

    public static void trackEventBegin(String eventName, String eventBody) {
        int i = 0;
        while (i < ChannelSize) {
            try {
                if (statInstanceList.get(i) != null) {
                    IMLogger.d("statFilterChannels size is :" + statFilterChannels.size());
                    if (statFilterChannels.size() == 0) {
                        IMLogger.d("statInstanceList get(i) is: " + statInstanceList.get(i).toString());
                        statInstanceList.get(i).trackEventBegin(eventName, eventBody);
                    } else if (statFilterChannels.contains(statInstanceHashMap.get(statInstanceList.get(i)))) {
                        IMLogger.d("statInstanceList in statFilterChannels is:  " + statInstanceList.get(i).toString());
                        statInstanceList.get(i).trackEventBegin(eventName, eventBody);
                    }
                }
                i++;
            } catch (Exception e) {
                IMLogger.e(e.getMessage());
                e.printStackTrace();
                return;
            }
        }
    }

    public static void trackEventEnd(String eventName, String eventBody) {
        int i = 0;
        while (i < ChannelSize) {
            try {
                if (statInstanceList.get(i) != null) {
                    IMLogger.d("statFilterChannels size is :" + statFilterChannels.size());
                    if (statFilterChannels.size() == 0) {
                        IMLogger.d("statInstanceList get(i) is: " + statInstanceList.get(i).toString());
                        statInstanceList.get(i).trackEventEnd(eventName, eventBody);
                    } else if (statFilterChannels.contains(statInstanceHashMap.get(statInstanceList.get(i)))) {
                        IMLogger.d("statInstanceList in statFilterChannels is:  " + statInstanceList.get(i).toString());
                        statInstanceList.get(i).trackEventEnd(eventName, eventBody);
                    }
                }
                i++;
            } catch (Exception e) {
                IMLogger.e(e.getMessage());
                e.printStackTrace();
                return;
            }
        }
    }

    public static void trackPageBegin(String pageName) {
        int i = 0;
        while (i < ChannelSize) {
            try {
                if (statInstanceList.get(i) != null) {
                    IMLogger.d("statFilterChannels size is :" + statFilterChannels.size());
                    if (statFilterChannels.size() == 0) {
                        IMLogger.d("statInstanceList get(i) is: " + statInstanceList.get(i).toString());
                        statInstanceList.get(i).trackPageBegin(pageName);
                    } else if (statFilterChannels.contains(statInstanceHashMap.get(statInstanceList.get(i)))) {
                        IMLogger.d("statInstanceList in statFilterChannels is:  " + statInstanceList.get(i).toString());
                        statInstanceList.get(i).trackPageBegin(pageName);
                    }
                }
                i++;
            } catch (Exception e) {
                IMLogger.e(e.getMessage());
                e.printStackTrace();
                return;
            }
        }
    }

    public static void trackPageEnd(String pageName) {
        int i = 0;
        while (i < ChannelSize) {
            try {
                if (statInstanceList.get(i) != null) {
                    IMLogger.d("statFilterChannels size is :" + statFilterChannels.size());
                    if (statFilterChannels.size() == 0) {
                        IMLogger.d("statInstanceList get(i) is: " + statInstanceList.get(i).toString());
                        statInstanceList.get(i).trackPageEnd(pageName);
                    } else if (statFilterChannels.contains(statInstanceHashMap.get(statInstanceList.get(i)))) {
                        IMLogger.d("statInstanceList in statFilterChannels is:  " + statInstanceList.get(i).toString());
                        statInstanceList.get(i).trackPageEnd(pageName);
                    }
                }
                i++;
            } catch (Exception e) {
                IMLogger.e(e.getMessage());
                e.printStackTrace();
                return;
            }
        }
    }

    public static void speedTest(HashMap<String, Integer> hostMap) {
        int i = 0;
        while (i < ChannelSize) {
            try {
                if (statInstanceList.get(i) != null) {
                    IMLogger.d("statFilterChannels size is :" + statFilterChannels.size());
                    if (statFilterChannels.size() == 0) {
                        IMLogger.d("statInstanceList get(i) is: " + statInstanceList.get(i).toString());
                        statInstanceList.get(i).speedTest(hostMap);
                    } else if (statFilterChannels.contains(statInstanceHashMap.get(statInstanceList.get(i)))) {
                        IMLogger.d("statInstanceList in statFilterChannels is:  " + statInstanceList.get(i).toString());
                        statInstanceList.get(i).speedTest(hostMap);
                    }
                }
                i++;
            } catch (Exception e) {
                IMLogger.e(e.getMessage());
                e.printStackTrace();
                return;
            }
        }
    }

    public static void speedTest(ArrayList<String> domainList) {
        int i = 0;
        while (i < ChannelSize) {
            try {
                if (statInstanceList.get(i) != null) {
                    IMLogger.d("statFilterChannels size is :" + statFilterChannels.size());
                    if (statFilterChannels.size() == 0) {
                        IMLogger.d("statInstanceList get(i) is: " + statInstanceList.get(i).toString());
                        statInstanceList.get(i).speedTest(domainList);
                    } else if (statFilterChannels.contains(statInstanceHashMap.get(statInstanceList.get(i)))) {
                        IMLogger.d("statInstanceList in statFilterChannels is:  " + statInstanceList.get(i).toString());
                        statInstanceList.get(i).speedTest(domainList);
                    }
                }
                i++;
            } catch (Exception e) {
                IMLogger.e(e.getMessage());
                e.printStackTrace();
                return;
            }
        }
    }

    public static void reportAutoExceptionReport(boolean flag) {
        int i = 0;
        while (i < ChannelSize) {
            try {
                if (statInstanceList.get(i) != null) {
                    IMLogger.d("statFilterChannels size is :" + statFilterChannels.size());
                    if (statFilterChannels.size() == 0) {
                        IMLogger.d("statInstanceList get(i) is: " + statInstanceList.get(i).toString());
                        statInstanceList.get(i).reportAutoExceptionReport(flag);
                    } else if (statFilterChannels.contains(statInstanceHashMap.get(statInstanceList.get(i)))) {
                        IMLogger.d("statInstanceList in statFilterChannels is:  " + statInstanceList.get(i).toString());
                        statInstanceList.get(i).reportAutoExceptionReport(flag);
                    }
                }
                i++;
            } catch (Exception e) {
                IMLogger.e(e.getMessage());
                e.printStackTrace();
                return;
            }
        }
    }

    public static void reportException(Throwable exception) {
        int i = 0;
        while (i < ChannelSize) {
            try {
                if (statInstanceList.get(i) != null) {
                    IMLogger.d("statFilterChannels size is :" + statFilterChannels.size());
                    if (statFilterChannels.size() == 0) {
                        IMLogger.d("statInstanceList get(i) is: " + statInstanceList.get(i).toString());
                        statInstanceList.get(i).reportException(exception);
                    } else if (statFilterChannels.contains(statInstanceHashMap.get(statInstanceList.get(i)))) {
                        IMLogger.d("statInstanceList in statFilterChannels is:  " + statInstanceList.get(i).toString());
                        statInstanceList.get(i).reportException(exception);
                    }
                }
                i++;
            } catch (Exception e) {
                IMLogger.e(e.getMessage());
                e.printStackTrace();
                return;
            }
        }
    }

    public static void reportCrash(boolean flag) {
        int i = 0;
        while (i < ChannelSize) {
            try {
                if (statInstanceList.get(i) != null) {
                    IMLogger.d("statFilterChannels size is :" + statFilterChannels.size());
                    if (statFilterChannels.size() == 0) {
                        IMLogger.d("statInstanceList get(i) is: " + statInstanceList.get(i).toString());
                        statInstanceList.get(i).reportCrash(flag);
                    } else if (statFilterChannels.contains(statInstanceHashMap.get(statInstanceList.get(i)))) {
                        IMLogger.d("statInstanceList in statFilterChannels is: " + statInstanceList.get(i).toString());
                        statInstanceList.get(i).reportCrash(flag);
                    }
                }
                i++;
            } catch (Exception e) {
                IMLogger.e(e.getMessage());
                e.printStackTrace();
                return;
            }
        }
    }

    public static String getStatIMEI() {
        String IMEI = "";
        int i = 0;
        while (i < ChannelSize) {
            try {
                if (statInstanceList.get(i) != null) {
                    IMEI = statInstanceList.get(i).getStatIMEI();
                    if (!TextUtils.isEmpty(IMEI)) {
                        break;
                    }
                }
                i++;
            } catch (Exception e) {
                IMLogger.e(e.getMessage());
                e.printStackTrace();
            }
        }
        return IMEI;
    }

    public static String getStatMID() {
        String MID = "";
        int i = 0;
        while (i < ChannelSize) {
            try {
                if (statInstanceList.get(i) != null) {
                    MID = statInstanceList.get(i).getStatMID();
                    if (!TextUtils.isEmpty(MID)) {
                        break;
                    }
                }
                i++;
            } catch (Exception e) {
                IMLogger.e(e.getMessage());
                e.printStackTrace();
            }
        }
        return MID;
    }

    public static void setChannelFilter(ArrayList<String> filter) {
        if (filter != null && filter.size() != 0) {
            statFilterChannels.clear();
            for (int i = 0; i < filter.size(); i++) {
                Iterator it = AllChannelList.iterator();
                while (it.hasNext()) {
                    String p = (String) it.next();
                    if (p.equalsIgnoreCase(filter.get(i))) {
                        statFilterChannels.add(p);
                    }
                }
            }
        }
    }

    public static ArrayList<String> getChannelFilter() {
        if (statFilterChannels == null || statFilterChannels.size() == 0) {
            return new ArrayList<>();
        }
        ArrayList<String> filter = new ArrayList<>();
        for (int i = 0; i < statFilterChannels.size(); i++) {
            filter.add(statFilterChannels.get(i));
        }
        return filter;
    }

    public static void clearFilter() {
        if (statFilterChannels.size() >= 1) {
            statFilterChannels.clear();
        }
        IMLogger.d("clear FilterChannels !");
    }
}
