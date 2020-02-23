package com.subao.common.j;

import android.content.Context;
import android.net.ConnectivityManager;
import com.subao.common.h;
import java.lang.reflect.Method;

/* compiled from: NetSwitch */
public class i {
    public static h a(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getApplicationContext().getSystemService("connectivity");
        if (connectivityManager == null) {
            return h.UNKNOWN;
        }
        try {
            Method declaredMethod = ConnectivityManager.class.getDeclaredMethod("getMobileDataEnabled", new Class[0]);
            declaredMethod.setAccessible(true);
            return ((Boolean) declaredMethod.invoke(connectivityManager, new Object[0])).booleanValue() ? h.ON : h.OFF;
        } catch (Exception e) {
            return h.UNKNOWN;
        }
    }
}
