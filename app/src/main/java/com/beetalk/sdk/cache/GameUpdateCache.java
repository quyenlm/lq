package com.beetalk.sdk.cache;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import com.beetalk.sdk.helper.BBLogger;
import java.util.Iterator;
import org.json.JSONException;
import org.json.JSONObject;

public class GameUpdateCache {
    private static final String KEY_DOWNLOADS = "key_downloads";
    private static final String UPDATE_PREF_NAME = "com.garena.msdk.update";
    private final SharedPreferences mPref;

    public GameUpdateCache(Context context) {
        this.mPref = context.getSharedPreferences(UPDATE_PREF_NAME, 0);
    }

    public long getDownloadId(String url) {
        return getJSONObject().optLong(url);
    }

    public String getDownloadUrl(long downloadId) {
        JSONObject jsonObject = getJSONObject();
        Iterator<String> iter = jsonObject.keys();
        while (iter.hasNext()) {
            String key = iter.next();
            if (jsonObject.optLong(key) == downloadId) {
                return key;
            }
        }
        return null;
    }

    public void putNewDownload(String url, long downloadId) {
        JSONObject jsonObject = getJSONObject();
        try {
            jsonObject.put(url, downloadId);
            setString(KEY_DOWNLOADS, jsonObject.toString());
        } catch (JSONException e) {
            BBLogger.e(e);
        }
    }

    public void removeDownload(String url) {
        JSONObject jsonObject = getJSONObject();
        jsonObject.remove(url);
        setString(KEY_DOWNLOADS, jsonObject.toString());
    }

    private JSONObject getJSONObject() {
        String content = getString(KEY_DOWNLOADS, "");
        JSONObject jsonObject = null;
        if (!TextUtils.isEmpty(content)) {
            try {
                jsonObject = new JSONObject(content);
            } catch (JSONException e) {
                BBLogger.e(e);
            }
        }
        if (jsonObject == null) {
            return new JSONObject();
        }
        return jsonObject;
    }

    private void setString(String key, String value) {
        this.mPref.edit().putString(key, value).commit();
    }

    private String getString(String key, String defaultValue) {
        return this.mPref.getString(key, defaultValue);
    }
}
