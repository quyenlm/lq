package com.subao.common.e;

/* compiled from: ChinaISP */
public enum j {
    CHINA_TELECOM(10, "中国电信", "CT"),
    CHINA_UNICOM(11, "中国联通", "CU"),
    CHINA_MOBILE(12, "中国移动", "CM");
    
    public final int d;
    public final String e;
    public final String f;

    private j(int i, String str, String str2) {
        this.d = i;
        this.e = str;
        this.f = str2;
    }
}
