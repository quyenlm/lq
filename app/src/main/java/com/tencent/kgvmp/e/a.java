package com.tencent.kgvmp.e;

import java.text.DecimalFormat;

public class a {
    private static DecimalFormat a = new DecimalFormat(".00");

    public static String a(float[] fArr, String str) {
        if (fArr == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        boolean z = false;
        for (float f : fArr) {
            if (z) {
                sb.append(str);
            } else {
                z = true;
            }
            sb.append(a.format((double) f));
        }
        return sb.toString();
    }

    public static boolean a(String[] strArr, String str) {
        for (String equals : strArr) {
            if (equals.equals(str)) {
                return true;
            }
        }
        return false;
    }
}
