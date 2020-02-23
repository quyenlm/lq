package com.beetalk.sdk.cache;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class SharedPrefStorage extends StorageCache implements Serializable {
    private static final String DEFAULT_KEY = "com.garena.msdk.token_cache";
    private SharedPreferences cache;
    private String cacheKey;

    public SharedPrefStorage(Context context) {
        this(context, (String) null);
    }

    public SharedPrefStorage(Context context, String cacheKey2) {
        this.cacheKey = cacheKey2;
        if (TextUtils.isEmpty(this.cacheKey)) {
            this.cacheKey = DEFAULT_KEY;
        }
        Context applicationContext = context.getApplicationContext();
        this.cache = (applicationContext != null ? applicationContext : context).getSharedPreferences(this.cacheKey, 0);
    }

    public void save(Map<String, String> map) {
        SharedPreferences.Editor editor = this.cache.edit();
        for (String key : map.keySet()) {
            editor.putString(key, serialize(map.get(key)));
        }
        editor.apply();
    }

    private String serialize(Object o) {
        if (o instanceof String) {
            return (String) o;
        }
        return "";
    }

    public void clear() {
        SharedPreferences.Editor editor = this.cache.edit();
        editor.clear();
        editor.apply();
    }

    public Map load() {
        HashMap<String, String> map = new HashMap<>();
        for (String key : this.cache.getAll().keySet()) {
            map.put(key, this.cache.getString(key, ""));
        }
        return map;
    }

    public void remove(String key) {
        SharedPreferences.Editor editor = this.cache.edit();
        editor.remove(key);
        editor.apply();
    }
}
