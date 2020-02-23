package com.garena.android.gpns.storage;

import android.content.Context;
import android.content.SharedPreferences;
import com.garena.android.gpns.GNotificationService;

final class SharedPreferenceStore {
    public static final String KEY = "SERVICE_PREF";
    private static SharedPreferenceStore mInstance;
    private SharedPreferences mPreference;

    private SharedPreferenceStore(Context context) {
        this.mPreference = context.getSharedPreferences(KEY, 0);
    }

    static SharedPreferenceStore getInstance() {
        if (mInstance == null) {
            mInstance = new SharedPreferenceStore(GNotificationService.getContext());
        }
        return mInstance;
    }

    /* access modifiers changed from: package-private */
    public void putString(String key, String value) {
        if (key != null) {
            SharedPreferences.Editor editor = this.mPreference.edit();
            editor.putString(key, value);
            editor.commit();
        }
    }

    /* access modifiers changed from: package-private */
    public String getString(String key, String defValue) {
        if (key != null) {
            return this.mPreference.getString(key, defValue);
        }
        return defValue;
    }

    /* access modifiers changed from: package-private */
    public void putInt(String key, int value) {
        if (key != null) {
            SharedPreferences.Editor editor = this.mPreference.edit();
            editor.putInt(key, value);
            editor.commit();
        }
    }

    /* access modifiers changed from: package-private */
    public int getInt(String key, int defValue) {
        if (key != null) {
            return this.mPreference.getInt(key, defValue);
        }
        return defValue;
    }

    /* access modifiers changed from: package-private */
    public void putLong(String key, long value) {
        if (key != null) {
            SharedPreferences.Editor editor = this.mPreference.edit();
            editor.putLong(key, value);
            editor.commit();
        }
    }

    /* access modifiers changed from: package-private */
    public long getLong(String key, long defValue) {
        if (key != null) {
            return this.mPreference.getLong(key, defValue);
        }
        return defValue;
    }

    /* access modifiers changed from: package-private */
    public void putBoolean(String key, boolean value) {
        if (key != null) {
            SharedPreferences.Editor editor = this.mPreference.edit();
            editor.putBoolean(key, value);
            editor.commit();
        }
    }

    /* access modifiers changed from: package-private */
    public boolean getBoolean(String key, boolean defValue) {
        if (key != null) {
            return this.mPreference.getBoolean(key, defValue);
        }
        return defValue;
    }
}
