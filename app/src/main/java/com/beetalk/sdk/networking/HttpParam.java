package com.beetalk.sdk.networking;

import android.text.TextUtils;

public class HttpParam {
    public final String key;
    public final String value;

    public HttpParam(String key2, String value2) {
        this.key = TextUtils.isEmpty(key2) ? "" : key2;
        this.value = TextUtils.isEmpty(value2) ? "" : value2;
    }

    public String toString() {
        return "[" + this.key + " -> " + this.value + "]";
    }
}
