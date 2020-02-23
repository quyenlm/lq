package com.tencent.imsdk.stat.base;

import android.content.Context;
import com.tencent.imsdk.stat.api.IMStatPlatInterface;
import java.util.ArrayList;
import java.util.HashMap;

public abstract class IMStatBase {
    public abstract String getStatIMEI();

    public abstract String getStatMID();

    public abstract void init(Context context, IMStatPlatInterface iMStatPlatInterface);

    public abstract void reportAutoExceptionReport(boolean z);

    public abstract void reportCrash(boolean z);

    public abstract void reportEvent(String str, HashMap<String, String> hashMap, boolean z);

    public abstract void reportEvent(String str, boolean z);

    public abstract void reportEvent(String str, boolean z, String str2);

    public abstract void reportException(Throwable th);

    public abstract void reportPurchase(String str, String str2, String str3, boolean z);

    public abstract void speedTest(ArrayList<String> arrayList);

    public abstract void speedTest(HashMap<String, Integer> hashMap);

    public abstract void trackEventBegin(String str, String str2);

    public abstract void trackEventEnd(String str, String str2);

    public abstract void trackPageBegin(String str);

    public abstract void trackPageEnd(String str);
}
