package com.tencent.midas.oversea.comm;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import com.appsflyer.share.Constants;
import com.tencent.imsdk.framework.request.HttpRequest;
import com.tencent.midas.oversea.api.APMidasPayAPI;
import com.tencent.midas.oversea.api.UnityPayHelper;
import com.tencent.midas.oversea.data.userInfo.APUserInfo;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class APDataReportManager {
    public static final String ACCOUNTINPUT_PRE = "e";
    public static final String ACCOUNTLIST_PRE = "c";
    public static final String GAMEANDMONTHSINPUT_PRE = "b";
    public static final String GAMEANDMONTHSLIST_PRE = "a";
    public static final String GETKEY_FAILURE = "sdk.oversea.cgi.getkey.failure";
    public static final String GETKEY_SUCESS = "sdk.oversea.cgi.getkey.sucess";
    public static final String GOODSANDMONTHSINPUT_PRE = "d";
    public static final String HOME_KEY_PRESSED = "com.pay.homeKeyPressed";
    public static final String MCARDVALUE_PRE = "f";
    public static final String NETWORK_REQUEST = "sdk.oversea.midas.networkrequest";
    public static final String OVERSEAINFO_FAILURE = "sdk.oversea.cgi.overseainfo.failure";
    public static final String OVERSEAINFO_SUCESS = "sdk.oversea.cgi.overseainfo.sucess";
    public static final String OVERSEAORDER_FAILURE = "sdk.oversea.cgi.overseaorder.failure";
    public static final String OVERSEAORDER_SUCESS = "sdk.oversea.cgi.overseaorder.sucess";
    public static final String OVERSEAPROVIDE_FAILURE = "sdk.oversea.cgi.overseaprovide.failure";
    public static final String OVERSEAPROVIDE_SUCESS = "sdk.oversea.cgi.overseaprovide.sucess";
    public static final String OVERSEA_FAILURE = "sdk.oversea.cgi.oversea.failure";
    public static final String OVERSEA_INIT_FAILURE = "sdk.oversea.cgi.init.failure";
    public static final String OVERSEA_INIT_SUCESS = "sdk.oversea.cgi.init.sucess";
    public static final String OVERSEA_RESTORE_FAILURE = "sdk.oversea.cgi.restore.failure";
    public static final String OVERSEA_RESTORE_SUCESS = "sdk.oversea.cgi.restore.sucess";
    public static final String OVERSEA_SUCESS = "sdk.oversea.cgi.oversea.sucess";
    public static final String PHONE_DEVICE = "sdk.oversea.deviceinfo";
    public static final String SDK_OVERSEA_BOKU_BACK = "sdk.oversea.boku.back";
    public static final String SDK_OVERSEA_BOKU_CANCEL = "sdk.oversea.boku.cancel";
    public static final String SDK_OVERSEA_BOKU_SHOW = "sdk.oversea.boku.show";
    public static final String SDK_OVERSEA_CHANNEL_PAY = "sdk.oversea.channel.pay";
    public static final String SDK_OVERSEA_CHANNEL_PAYRESULT = "sdk.oversea.channel.payresult";
    public static final String SDK_OVERSEA_CHANNEL_SHOW = "sdk.oversea.channel.show";
    public static final String SDK_OVERSEA_COUNTRY_SELECT = "sdk.oversea.country.select";
    public static final String SDK_OVERSEA_COUNTRY_SHOW = "sdk.oversea.country.show";
    public static final String SDK_OVERSEA_ENTER = "sdk.oversea.enter";
    public static final String SDK_OVERSEA_EXIT = "sdk.oversea.exit";
    public static final String SDK_OVERSEA_GW_CONSUME_END = "sdk.oversea.gw.consume.end";
    public static final String SDK_OVERSEA_GW_CONSUME_START = "sdk.oversea.gw.consume.start";
    public static final String SDK_OVERSEA_GW_INIT_END = "sdk.oversea.gw.init.end";
    public static final String SDK_OVERSEA_GW_INIT_START = "sdk.oversea.gw.init.start";
    public static final String SDK_OVERSEA_GW_PAY_END = "sdk.oversea.gw.pay.end";
    public static final String SDK_OVERSEA_GW_PAY_START = "sdk.oversea.gw.pay.start";
    public static final String SDK_OVERSEA_GW_QUERY_END = "sdk.oversea.gw.query.end";
    public static final String SDK_OVERSEA_GW_QUERY_START = "sdk.oversea.gw.query.start";
    public static final String SDK_OVERSEA_GW_RESTORE_CONSUME_END = "sdk.oversea.gw.restore.consume.end";
    public static final String SDK_OVERSEA_GW_RESTORE_CONSUME_START = "sdk.oversea.gw.restore.consume.start";
    public static final String SDK_OVERSEA_GW_RESTORE_INIT_END = "sdk.oversea.gw.restore.init.end";
    public static final String SDK_OVERSEA_GW_RESTORE_INIT_START = "sdk.oversea.gw.restore.init.start";
    public static final String SDK_OVERSEA_GW_RESTORE_QUERY_END = "sdk.oversea.gw.restore.query.end";
    public static final String SDK_OVERSEA_GW_RESTORE_QUERY_START = "sdk.oversea.gw.restore.query.start";
    public static final String SDK_OVERSEA_MAIN_BACK = "sdk.oversea.main.back";
    public static final String SDK_OVERSEA_MAIN_CANCEL = "sdk.oversea.main.cancel";
    public static final String SDK_OVERSEA_MAIN_SHOW = "sdk.oversea.main.show";
    public static final String TENPAYSUCC_PRE = "pt";
    private static volatile APDataReportManager a = null;
    private int b;
    public List<HashMap<String, String>> dataReport;

    private APDataReportManager() {
        this.dataReport = null;
        this.b = 0;
        this.dataReport = new ArrayList();
        a();
    }

    private void a() {
        SharedPreferences sharedPreferences;
        APMidasPayAPI.singleton();
        Context context = APMidasPayAPI.applicationContext;
        if (context != null && (sharedPreferences = context.getSharedPreferences(APGlobalInfo.SharedPreferencesTag, 0)) != null) {
            this.b = sharedPreferences.getInt("dataCount", 0);
        }
    }

    public static APDataReportManager getInstance() {
        if (a == null) {
            synchronized (APDataReportManager.class) {
                if (a == null) {
                    a = new APDataReportManager();
                }
            }
        }
        return a;
    }

    public static void release() {
        a = null;
    }

    public void clearData() {
        this.dataReport.clear();
    }

    public int getDataId() {
        this.b++;
        if (this.b == 30000) {
            this.b = 0;
        }
        return this.b;
    }

    public int getLogRecord(ArrayList<String> arrayList) {
        try {
            arrayList.clear();
            int size = this.dataReport.size();
            if (size <= 0) {
                return 0;
            }
            int i = size % 12 == 0 ? size / 12 : (size / 12) + 1;
            String str = APDataInterface.singleton().getUserInfo().openId;
            String str2 = "androidoversea_v" + APCommMethod.getVersion();
            StringBuffer stringBuffer = new StringBuffer();
            int i2 = 0;
            while (i2 < i) {
                int i3 = 0;
                int i4 = 0;
                while (i3 < 12 && (i2 * 12) + i3 < size) {
                    int i5 = i4 + 1;
                    HashMap hashMap = this.dataReport.get((i2 * 12) + i3);
                    stringBuffer.append("record" + i3 + HttpRequest.HTTP_REQ_ENTITY_MERGE);
                    stringBuffer.append("3=" + str);
                    stringBuffer.append("|7=0");
                    stringBuffer.append("|13=" + getDataId());
                    stringBuffer.append("|24=" + APAppDataInterface.singleton().getOfferid());
                    if (!TextUtils.isEmpty((CharSequence) hashMap.get("payid"))) {
                        stringBuffer.append("|4=" + ((String) hashMap.get("payid")));
                    }
                    if (!TextUtils.isEmpty((CharSequence) hashMap.get("isBindQQ"))) {
                        stringBuffer.append("|55=" + ((String) hashMap.get("isBindQQ")));
                    }
                    stringBuffer.append("|21=" + ((String) hashMap.get("format")));
                    stringBuffer.append("|26=" + ((String) hashMap.get(UnityPayHelper.PF)));
                    String str3 = (String) hashMap.get("token");
                    if (str3 != null) {
                        stringBuffer.append("|56=" + str3);
                    }
                    String str4 = (String) hashMap.get("extend");
                    APLog.i("getLogRecord extend pre", str4);
                    if (str4 != null) {
                        stringBuffer.append("|8=" + APTools.urlEncode(str4, 3));
                    }
                    String str5 = (String) hashMap.get("from");
                    if (str5 != null) {
                        stringBuffer.append("|20=" + str5);
                    }
                    String str6 = (String) hashMap.get(Constants.URL_MEDIA_SOURCE);
                    if (str6 != null) {
                        stringBuffer.append("|44=" + str6);
                    }
                    String str7 = (String) hashMap.get("buytype");
                    if (str7 != null) {
                        stringBuffer.append("|47=" + str7);
                    }
                    stringBuffer.append("|29=" + ((String) hashMap.get("sessionToken")));
                    stringBuffer.append("|31=" + str2);
                    stringBuffer.append("|38=" + ((String) hashMap.get("times")));
                    stringBuffer.append("|34=" + ((String) hashMap.get("uinTypeFromSvr")));
                    stringBuffer.append("|35=" + ((String) hashMap.get("uinFromSvr")));
                    stringBuffer.append("|37=" + ((String) hashMap.get(UnityPayHelper.SESSIONID)));
                    stringBuffer.append("|43=" + ((String) hashMap.get(UnityPayHelper.SESSIONTYPE)));
                    stringBuffer.append(HttpRequest.HTTP_REQ_ENTITY_JOIN);
                    i3++;
                    i4 = i5;
                }
                if (stringBuffer.length() > 0) {
                    stringBuffer.deleteCharAt(stringBuffer.length() - 1);
                }
                StringBuffer stringBuffer2 = new StringBuffer();
                stringBuffer2.append("num=");
                stringBuffer2.append(i4);
                stringBuffer2.append(HttpRequest.HTTP_REQ_ENTITY_JOIN);
                stringBuffer2.append(stringBuffer.toString());
                arrayList.add(stringBuffer2.toString());
                stringBuffer.setLength(0);
                i2++;
            }
            return i;
        } catch (Exception e) {
            APLog.e("APDataReportManager", "APDataReportManager error:" + e.toString());
            return 0;
        }
    }

    public void insertData(String str, int i) {
        insertData(str, i, (String) null, (String) null, (String) null, (String) null);
    }

    public void insertData(String str, int i, String str2, String str3, String str4) {
        insertData(str, i, (String) null, str2, str3, str4);
    }

    public void insertData(String str, int i, String str2, String str3, String str4, String str5) {
        APUserInfo userInfo = APDataInterface.singleton().getUserInfo();
        HashMap hashMap = new HashMap();
        hashMap.put("format", str);
        hashMap.put("times", String.valueOf(System.currentTimeMillis()));
        if (!TextUtils.isEmpty(str2)) {
            hashMap.put(Constants.URL_MEDIA_SOURCE, str2);
        }
        if (!TextUtils.isEmpty(str3)) {
            hashMap.put("token", str3);
        }
        if (!TextUtils.isEmpty(str4)) {
            hashMap.put("from", str4);
        }
        if (!TextUtils.isEmpty(str5)) {
            hashMap.put("extend", str5);
        }
        if (userInfo.sessionId.equals("hy_gameid") && userInfo.sessionType.equals("wc_actoken")) {
            hashMap.put("payid", userInfo.payId);
            if (userInfo.isBindQQ) {
                hashMap.put("isBindQQ", "true");
            } else {
                hashMap.put("isBindQQ", "false");
            }
        }
        hashMap.put(UnityPayHelper.PF, userInfo.pf);
        hashMap.put("sessionToken", APDataInterface.singleton().getSessionToken());
        hashMap.put("uinTypeFromSvr", userInfo.uinTypeFromSvr);
        hashMap.put("uinFromSvr", userInfo.uinFromSvr);
        hashMap.put(UnityPayHelper.SESSIONID, userInfo.sessionId);
        hashMap.put(UnityPayHelper.SESSIONTYPE, userInfo.sessionType);
        switch (i) {
            case 0:
                hashMap.put("buytype", "game");
                break;
            case 1:
                hashMap.put("buytype", "goods");
                break;
            case 2:
            case 3:
                hashMap.put("buytype", "acct");
                break;
            case 4:
                hashMap.put("buytype", "month");
                break;
            case 5:
                hashMap.put("buytype", "subscribe");
                break;
            default:
                hashMap.put("buytype", "game");
                break;
        }
        this.dataReport.add(hashMap);
    }

    public void saveDataId() {
        SharedPreferences sharedPreferences;
        APMidasPayAPI.singleton();
        Context context = APMidasPayAPI.applicationContext;
        if (context != null && (sharedPreferences = context.getSharedPreferences(APGlobalInfo.SharedPreferencesTag, 0)) != null) {
            sharedPreferences.edit().putInt("dataCount", this.b).commit();
        }
    }
}
