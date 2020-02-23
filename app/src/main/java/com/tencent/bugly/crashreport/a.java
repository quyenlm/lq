package com.tencent.bugly.crashreport;

/* compiled from: BUGLY */
public interface a {
    boolean appendLogToNative(String str, String str2, String str3);

    String getLogFromNative();

    boolean setNativeIsAppForeground(boolean z);
}
