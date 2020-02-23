package com.tencent.midas.oversea.comm;

import android.text.TextUtils;
import com.tencent.midas.oversea.data.userInfo.APUserInfo;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class APDataInterface {
    private static volatile APDataInterface a = null;
    private String b = "";
    private boolean c = true;
    private String d = "";
    private String e = "";
    private String f = "";
    private String g = "";
    private APUserInfo h;
    private String i;
    public String mDrmInfo;

    public static APDataInterface init() {
        a = new APDataInterface();
        a.h = new APUserInfo();
        a.i = APTools.getUUID();
        return a;
    }

    public static APDataInterface singleton() {
        if (a == null) {
            synchronized (APDataInterface.class) {
                if (a == null) {
                    a = new APDataInterface();
                }
                if (a.h == null) {
                    a.h = new APUserInfo();
                }
                a.i = APTools.getUUID();
            }
        }
        return a;
    }

    public String getCgiExtends() {
        return a.b;
    }

    public boolean getIsSendReport() {
        return a.c;
    }

    public String getSessionToken() {
        return a.i;
    }

    public String getUserIMEI() {
        return a.e;
    }

    public APUserInfo getUserInfo() {
        return a.h;
    }

    public String getUserMAC() {
        return a.f;
    }

    public String getUserUniqueUuid() {
        return a.d;
    }

    public String getUuid() {
        return a.g;
    }

    public void setCgiExtends(String str) {
        if (!TextUtils.isEmpty(str)) {
            try {
                a.b = URLEncoder.encode(str, "UTF-8");
            } catch (UnsupportedEncodingException e2) {
                e2.printStackTrace();
            }
        }
    }

    public void setIsSendReport(boolean z) {
        a.c = z;
    }

    public void setSessionToken(String str) {
        a.i = str;
    }

    public void setUserIMEI(String str) {
        a.e = str;
    }

    public void setUserInfo(APUserInfo aPUserInfo) {
        a.h = aPUserInfo;
    }

    public void setUserMAC(String str) {
        a.f = str;
    }

    public void setUserUniqueUuid(String str) {
        a.d = str;
    }

    public void setUuid(String str) {
        a.g = str;
    }
}
