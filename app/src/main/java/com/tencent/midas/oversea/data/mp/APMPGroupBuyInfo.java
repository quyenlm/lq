package com.tencent.midas.oversea.data.mp;

public class APMPGroupBuyInfo {
    private static APMPGroupBuyInfo f = null;
    private String a = "0";
    private String b = "";
    private String c = "";
    private String d = "";
    private APMPGroupBuyItem e = null;

    private APMPGroupBuyInfo() {
    }

    public static synchronized APMPGroupBuyInfo getInstance() {
        APMPGroupBuyInfo aPMPGroupBuyInfo;
        synchronized (APMPGroupBuyInfo.class) {
            if (f == null) {
                f = new APMPGroupBuyInfo();
            }
            aPMPGroupBuyInfo = f;
        }
        return aPMPGroupBuyInfo;
    }

    public static void release() {
        f = null;
    }

    public void clear() {
        this.a = "0";
        this.b = "";
        this.c = "";
        this.d = "";
        this.e = null;
    }

    public String getActsetId() {
        return this.d;
    }

    public String getMallUrl() {
        return this.b;
    }

    public APMPGroupBuyItem getRecGroupBuyItem() {
        return this.e;
    }

    public String getResultUrl() {
        return this.c;
    }

    public String getShow() {
        return this.a;
    }

    public void setActsetId(String str) {
        this.d = str;
    }

    public void setMallUrl(String str) {
        this.b = str;
    }

    public void setRecGroupBuyItem(APMPGroupBuyItem aPMPGroupBuyItem) {
        this.e = aPMPGroupBuyItem;
    }

    public void setResultUrl(String str) {
        this.c = str;
    }

    public void setShow(String str) {
        this.a = str;
    }
}
