package com.tencent.gsdk.api;

public final class Event {
    public boolean isCore;
    public String methodName;
    public String methodParams;
    public String name;
    public String resultData;
    public boolean succeed;

    public Event(String str, boolean z, String str2, String str3, boolean z2, String str4) {
        this.name = str;
        this.isCore = z;
        this.methodName = str2;
        this.methodParams = str3;
        this.succeed = z2;
        this.resultData = str4;
    }
}
