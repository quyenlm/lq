package com.tencent.gsdk.api;

import android.content.Context;
import android.content.SharedPreferences;
import com.tencent.gsdk.utils.Logger;

/* compiled from: SwitchPreference */
public class f {
    public static void a(Context context, String str, String str2, Boolean bool) {
        if (context != null && str != null && str2 != null) {
            try {
                SharedPreferences.Editor edit = context.getSharedPreferences(str, 0).edit();
                edit.putBoolean(str2, bool.booleanValue());
                edit.commit();
            } catch (Exception e) {
                Logger.e("updateBooleanPreferences error:" + e.getMessage());
            }
        }
    }

    public static void a(Context context, String str, String str2, String str3) {
        if (context != null && str != null && str2 != null) {
            try {
                SharedPreferences.Editor edit = context.getSharedPreferences(str, 0).edit();
                edit.putString(str2, str3);
                edit.commit();
            } catch (Exception e) {
                Logger.e("updateStringPreferences error:" + e.getMessage());
            }
        }
    }

    public static void a(Context context, String str, String str2, int i) {
        if (context != null && str != null && str2 != null) {
            try {
                SharedPreferences.Editor edit = context.getSharedPreferences(str, 0).edit();
                edit.putInt(str2, i);
                edit.commit();
            } catch (Exception e) {
                Logger.e("updateIntPreferences error:" + e.getMessage());
            }
        }
    }

    public static boolean a(Context context, String str, String str2) {
        boolean z = false;
        if (context == null || str == null || str2 == null) {
            return false;
        }
        try {
            z = context.getSharedPreferences(str, 0).getBoolean(str2, true);
            Logger.d(str2 + "boolean value is " + z);
            return z;
        } catch (Exception e) {
            Logger.e("getBooleanPreferences error:" + e.getMessage());
            return z;
        }
    }

    public static String b(Context context, String str, String str2) {
        String str3 = "NULL";
        if (context == null || str == null || str2 == null) {
            return str3;
        }
        try {
            str3 = context.getSharedPreferences(str, 0).getString(str2, str3);
            Logger.d(str2 + " value is " + str3);
            return str3;
        } catch (Exception e) {
            Logger.e("getStringPreferences error:" + e.getMessage());
            return str3;
        }
    }

    public static int c(Context context, String str, String str2) {
        int i = 0;
        if (context == null || str == null || str2 == null) {
            return 0;
        }
        try {
            i = context.getSharedPreferences(str, 0).getInt(str2, 0);
            Logger.d(str2 + "int value is " + i);
            return i;
        } catch (Exception e) {
            Logger.e("getIntPreferences error:" + e.getMessage());
            return i;
        }
    }
}
