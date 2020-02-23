package com.tencent.kgvmp.e;

import com.amazonaws.services.s3.internal.Constants;

public class g {
    public static boolean a(String str) {
        return str == null || str.compareTo(Constants.NULL_VERSION_ID) == 0;
    }

    public static boolean b(String str) {
        return str == null || str.compareTo("") == 0;
    }
}
