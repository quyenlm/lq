package com.subao.common.j;

import com.subao.common.j.j;

/* compiled from: MobileNetTypeDetector */
public class f {
    public static j.a a(int i) {
        switch (i) {
            case 1:
            case 2:
            case 4:
            case 7:
            case 11:
            case 16:
                return j.a.MOBILE_2G;
            case 3:
            case 5:
            case 6:
            case 8:
            case 9:
            case 10:
            case 12:
            case 14:
            case 15:
            case 17:
                return j.a.MOBILE_3G;
            case 13:
                return j.a.MOBILE_4G;
            default:
                if (i >= 19) {
                    return j.a.MOBILE_4G;
                }
                return j.a.UNKNOWN;
        }
    }
}
