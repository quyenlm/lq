package com.tencent.imsdk.tool.etc;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

public class SharedPreferenceHelper {
    private static String PREF_FILE = "PREF";

    public static void setCustomFileName(String fileName) {
        if (!TextUtils.isEmpty(fileName)) {
            PREF_FILE = fileName;
        }
    }

    @SuppressLint({"CommitPrefEdits"})
    public static void setSharedPreferenceString(Context context, String key, String value) {
        SharedPreferences.Editor editor = context.getSharedPreferences(PREF_FILE, 0).edit();
        editor.putString(key, value);
        editor.commit();
    }

    @SuppressLint({"CommitPrefEdits"})
    public static void setSharedPreferenceInt(Context context, String key, int value) {
        SharedPreferences.Editor editor = context.getSharedPreferences(PREF_FILE, 0).edit();
        editor.putInt(key, value);
        editor.commit();
    }

    @SuppressLint({"CommitPrefEdits"})
    public static void setSharedPreferenceBoolean(Context context, String key, boolean value) {
        SharedPreferences.Editor editor = context.getSharedPreferences(PREF_FILE, 0).edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public static String getSharedPreferenceString(Context context, String key, String defValue) {
        return context.getSharedPreferences(PREF_FILE, 0).getString(key, defValue);
    }

    public static int getSharedPreferenceInt(Context context, String key, int defValue) {
        return context.getSharedPreferences(PREF_FILE, 0).getInt(key, defValue);
    }

    public static boolean getSharedPreferenceBoolean(Context context, String key, boolean defValue) {
        return context.getSharedPreferences(PREF_FILE, 0).getBoolean(key, defValue);
    }
}
