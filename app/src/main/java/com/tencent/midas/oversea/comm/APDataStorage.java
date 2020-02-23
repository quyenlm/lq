package com.tencent.midas.oversea.comm;

import android.content.Context;
import android.content.SharedPreferences;

public class APDataStorage {
    public void clearData(Context context, String str) {
        SharedPreferences.Editor editor = (SharedPreferences.Editor) context.getSharedPreferences(str, 0);
        editor.clear();
        editor.commit();
    }

    public boolean getAutoLogin(Context context) {
        return context.getSharedPreferences("account", 0).getBoolean("AUTOLOGIN", false);
    }

    public String getData(Context context, String str, String str2) {
        return context.getSharedPreferences(str, 0).getString(str2, "");
    }

    public boolean getRememberPwd(Context context) {
        return context.getSharedPreferences("account", 0).getBoolean("REMEMBERPWD", true);
    }

    public void setAutoLogin(Context context, boolean z) {
        SharedPreferences.Editor edit = context.getSharedPreferences("account", 0).edit();
        edit.putBoolean("AUTOLOGIN", z);
        edit.commit();
    }

    public void setRememberPwd(Context context, boolean z) {
        SharedPreferences.Editor edit = context.getSharedPreferences("account", 0).edit();
        edit.putBoolean("REMEMBERPWD", z);
        edit.commit();
    }

    public void storeData(Context context, String str, String str2, String str3) {
        SharedPreferences.Editor edit = context.getSharedPreferences(str, 0).edit();
        edit.putString(str2, str3);
        edit.commit();
    }
}
