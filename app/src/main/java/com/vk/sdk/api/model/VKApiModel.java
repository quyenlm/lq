package com.vk.sdk.api.model;

import android.os.Parcelable;
import android.util.SparseArray;
import org.json.JSONException;
import org.json.JSONObject;

public abstract class VKApiModel implements Parcelable {
    public JSONObject fields;
    private SparseArray<Object> mKeyedTags;
    private Object mTag;

    public VKApiModel() {
    }

    public Object getTag() {
        return this.mTag;
    }

    public void setTag(Object tag) {
        this.mTag = tag;
    }

    public Object getTag(int key) {
        if (this.mKeyedTags != null) {
            return this.mKeyedTags.get(key);
        }
        return null;
    }

    public void setTag(int key, Object tag) {
        if (this.mKeyedTags == null) {
            this.mKeyedTags = new SparseArray<>(2);
        }
        this.mKeyedTags.put(key, tag);
    }

    public VKApiModel(JSONObject from) throws JSONException {
        parse(from);
    }

    public VKApiModel parse(JSONObject response) throws JSONException {
        return (VKApiModel) ParseUtils.parseViaReflection(this, response);
    }
}
