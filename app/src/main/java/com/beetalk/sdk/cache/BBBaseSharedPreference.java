package com.beetalk.sdk.cache;

import android.content.Context;
import android.content.SharedPreferences;

public abstract class BBBaseSharedPreference {
    protected SharedPreferences m_settings;

    /* access modifiers changed from: protected */
    public abstract Context __getApplicationContext();

    public BBBaseSharedPreference() {
        check();
    }

    public void check() {
        Context context;
        if (this.m_settings == null && (context = __getApplicationContext()) != null) {
            this.m_settings = context.getSharedPreferences(_getUserProfileName(), 0);
        }
    }

    /* access modifiers changed from: protected */
    public String _getUserProfileName() {
        return "";
    }

    public void _unset(String tag) {
        if (this.m_settings != null) {
            SharedPreferences.Editor editor = this.m_settings.edit();
            editor.remove(tag);
            editor.apply();
        }
    }

    public boolean _getBoolean(String tag, boolean b) {
        return this.m_settings == null ? b : this.m_settings.getBoolean(tag, b);
    }

    public void _setBoolean(String tag, boolean b) {
        if (this.m_settings != null) {
            SharedPreferences.Editor editor = this.m_settings.edit();
            editor.putBoolean(tag, b);
            editor.apply();
        }
    }

    public float _getFloat(String tag, float def) {
        return this.m_settings == null ? def : this.m_settings.getFloat(tag, def);
    }

    public void _setFloat(String tag, float f) {
        if (this.m_settings != null) {
            SharedPreferences.Editor editor = this.m_settings.edit();
            editor.putFloat(tag, f);
            editor.apply();
        }
    }

    public String _getString(String tag, String defaultStr) {
        return this.m_settings == null ? defaultStr : this.m_settings.getString(tag, defaultStr);
    }

    public void _setString(String tag, String valueStr) {
        if (this.m_settings != null) {
            SharedPreferences.Editor editor = this.m_settings.edit();
            editor.putString(tag, valueStr);
            editor.apply();
        }
    }

    public int _getInt(String tag, int defaultVal) {
        return this.m_settings == null ? defaultVal : this.m_settings.getInt(tag, defaultVal);
    }

    public void _setInt(String tag, int val) {
        if (this.m_settings != null) {
            SharedPreferences.Editor editor = this.m_settings.edit();
            editor.putInt(tag, val);
            editor.apply();
        }
    }

    public long _getLong(String tag, long defaultVal) {
        return this.m_settings == null ? defaultVal : this.m_settings.getLong(tag, defaultVal);
    }

    public void _setLong(String tag, long val) {
        if (this.m_settings != null) {
            SharedPreferences.Editor editor = this.m_settings.edit();
            editor.putLong(tag, val);
            editor.apply();
        }
    }

    public void _clear() {
        if (this.m_settings != null) {
            SharedPreferences.Editor editor = this.m_settings.edit();
            editor.clear();
            editor.apply();
        }
    }
}
