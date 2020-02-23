package com.tencent.midas.oversea.comm;

import android.content.SharedPreferences;
import com.tencent.midas.oversea.api.APMidasPayAPI;
import com.tencent.midas.oversea.network.http.APNetCfg;

public class APAppDataInterface {
    private static APAppDataInterface u = null;
    private String A = "0";
    private String B = "";
    private boolean C = true;
    private String a = "cpay_4.1.1";
    private String b = "";
    private String c = "";
    private String d = "";
    private boolean e = false;
    private String f = "";
    private String g = "release";
    private String h = "";
    private String i = "";
    private String j = "";
    private boolean k = false;
    private boolean l = true;
    private String m = "";
    public String mType;
    public String mofferId;
    private int n = 1;
    private boolean o = true;
    private int p = 0;
    private String q = "";
    private boolean r = true;
    private String s = "r";
    private int t = -1;
    private String v = "";
    private String w = "";
    private String x = "";
    private String y = "0";
    private String z = "";

    public static void release() {
        u = null;
    }

    public static APAppDataInterface singleton() {
        if (u == null) {
            u = new APAppDataInterface();
        }
        return u;
    }

    public int getAppOrientation() {
        return u.n;
    }

    public String getBaseKey() {
        return "34asdS5WEls2SD23SFS282ASD5sf23SF";
    }

    public boolean getChangeKey() {
        return u.e;
    }

    public String getCryptKeyTime() {
        return u.d;
    }

    public String getCryptoKey() {
        return u.c;
    }

    public String getCustomCgi() {
        return u.s;
    }

    public String getDeviceInfo() {
        return u.q;
    }

    public String getEnv() {
        return u.g;
    }

    public String getInstallQQ() {
        return u.y;
    }

    public String getInstallWechat() {
        return u.A;
    }

    public boolean getIsOwnResearch() {
        return u.l;
    }

    public boolean getIsShowSaveNum() {
        return u.o;
    }

    public String getJSVeriosn() {
        return u.x;
    }

    public String getMacAdress() {
        return u.h;
    }

    public String getMidasCoreVersion() {
        return u.w;
    }

    public String getMidasPluginVersion() {
        return u.v;
    }

    public int getNetworkState() {
        return u.p;
    }

    public String getOfferid() {
        return u.f;
    }

    public String getQQVersionName() {
        return u.z;
    }

    public boolean getReloginInSDK() {
        return u.k;
    }

    public int getScreenType() {
        return u.t;
    }

    public String getSecretKey() {
        return u.b;
    }

    public String getSysServerIP() {
        singleton().getEnv();
        if (u.i.equals("")) {
            u.i = APNetCfg.getDomin();
        }
        return u.i;
    }

    public String getVid() {
        return u.a;
    }

    public String getWechatAppId() {
        return u.m;
    }

    public String getWechatVersionName() {
        return u.B;
    }

    public String getXGMid() {
        return u.j;
    }

    public boolean isElseNumberVisible() {
        return u.r;
    }

    public boolean isNewCGI() {
        return this.C;
    }

    public void redIsNewCGI() {
        APMidasPayAPI.singleton();
        this.C = APMidasPayAPI.applicationContext.getSharedPreferences(APGlobalInfo.SharedPreferencesTag, 0).getBoolean("isNewCGI", true);
    }

    public void setAppOrientation(int i2) {
        u.n = i2;
    }

    public void setChangeKey(boolean z2) {
        u.e = z2;
    }

    public void setCryptKey(String str) {
        if (str == null) {
            u.c = "";
        } else {
            u.c = str;
        }
    }

    public void setCryptKeyTime(String str) {
        if (str == null) {
            u.d = "";
        } else {
            u.d = str;
        }
    }

    public void setCustomCgi(String str) {
        u.s = str;
    }

    public void setDeviceInfo(String str) {
        u.q = str;
    }

    public void setElseNumberVisible(boolean z2) {
        u.r = z2;
    }

    public void setEnv(String str) {
        u.g = str;
    }

    public void setInstallQQ(String str) {
        u.y = str;
    }

    public void setInstallWechat(String str) {
        u.A = str;
    }

    public void setIsNewCGI(boolean z2) {
        APMidasPayAPI.singleton();
        SharedPreferences.Editor edit = APMidasPayAPI.applicationContext.getSharedPreferences(APGlobalInfo.SharedPreferencesTag, 0).edit();
        edit.putBoolean("isNewCGI", z2);
        edit.commit();
    }

    public void setIsOwnResearch(boolean z2) {
        u.l = z2;
    }

    public void setIsShowSaveNum(boolean z2) {
        u.o = z2;
    }

    public void setJSVeriosn(String str) {
        u.x = str;
    }

    public void setMacAdress(String str) {
        u.h = str;
    }

    public void setMidasCoreVersion(String str) {
        u.w = str;
    }

    public void setMidasPluginVersion(String str) {
        u.v = str;
    }

    public void setNetworkState(int i2) {
        u.p = i2;
    }

    public void setOfferid(String str) {
        u.f = str;
    }

    public void setQQVersionName(String str) {
        u.z = str;
    }

    public void setReloginInSDK(boolean z2) {
        u.k = z2;
    }

    public void setScreenType(int i2) {
        u.t = i2;
    }

    public void setSecretKey(String str) {
        if (str == null) {
            u.b = "";
        } else {
            u.b = str;
        }
    }

    public void setSysServerIP(String str) {
        u.i = str;
    }

    public void setVid(String str) {
        u.a = str;
    }

    public void setWechatAppId(String str) {
        u.m = str;
    }

    public void setWechatVersionName(String str) {
        u.B = str;
    }

    public void setXGMid(String str) {
        u.j = str;
    }
}
