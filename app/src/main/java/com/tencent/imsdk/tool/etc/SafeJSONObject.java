package com.tencent.imsdk.tool.etc;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SafeJSONObject extends JSONObject {
    public SafeJSONObject(String content) throws JSONException {
        super(content);
    }

    public SafeJSONObject() {
    }

    public SafeJSONObject(JSONObject src) throws JSONException {
        super(src.toString());
    }

    public boolean getBoolean(String name) throws JSONException {
        if (!has(name)) {
            return false;
        }
        return super.getBoolean(name);
    }

    public double getDouble(String name) throws JSONException {
        if (!has(name)) {
            return -1.0d;
        }
        return super.getDouble(name);
    }

    public int getInt(String name) throws JSONException {
        if (!has(name)) {
            return -1;
        }
        return super.getInt(name);
    }

    public JSONArray getJSONArray(String name) throws JSONException {
        if (!has(name)) {
            return new JSONArray();
        }
        return super.getJSONArray(name);
    }

    public JSONObject getJSONObject(String name) throws JSONException {
        if (!has(name)) {
            return new SafeJSONObject();
        }
        return super.getJSONObject(name);
    }

    public long getLong(String name) throws JSONException {
        if (!has(name)) {
            return -1;
        }
        return super.getLong(name);
    }

    public String getString(String name) throws JSONException {
        if (!has(name)) {
            return "";
        }
        return super.getString(name);
    }

    public boolean getBoolean(String name, boolean defaultValue) throws JSONException {
        return !has(name) ? defaultValue : super.getBoolean(name);
    }

    public double getDouble(String name, double defaultValue) throws JSONException {
        return !has(name) ? defaultValue : super.getDouble(name);
    }

    public int getInt(String name, int defaultValue) throws JSONException {
        return !has(name) ? defaultValue : super.getInt(name);
    }

    public JSONArray getJSONArray(String name, JSONArray defaultValue) throws JSONException {
        return !has(name) ? defaultValue : super.getJSONArray(name);
    }

    public JSONObject getJSONObject(String name, JSONObject defaultValue) throws JSONException {
        return !has(name) ? defaultValue : super.getJSONObject(name);
    }

    public long getLong(String name, Long defaultValue) throws JSONException {
        if (!has(name)) {
            return defaultValue.longValue();
        }
        return super.getLong(name);
    }

    public String getString(String name, String defaultValue) throws JSONException {
        return !has(name) ? defaultValue : super.getString(name);
    }
}
