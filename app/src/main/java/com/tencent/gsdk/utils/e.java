package com.tencent.gsdk.utils;

import java.util.UUID;

/* compiled from: GSDKUtils */
public class e {
    public static String a() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}
