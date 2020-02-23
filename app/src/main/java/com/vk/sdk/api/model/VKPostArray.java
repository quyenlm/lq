package com.vk.sdk.api.model;

import android.os.Parcel;
import android.os.Parcelable;
import org.json.JSONException;
import org.json.JSONObject;

public class VKPostArray extends VKList<VKApiPost> {
    public static Parcelable.Creator<VKPostArray> CREATOR = new Parcelable.Creator<VKPostArray>() {
        public VKPostArray createFromParcel(Parcel source) {
            return new VKPostArray(source);
        }

        public VKPostArray[] newArray(int size) {
            return new VKPostArray[size];
        }
    };

    public VKApiModel parse(JSONObject response) throws JSONException {
        fill(response, VKApiPost.class);
        return this;
    }

    public VKPostArray() {
    }

    public VKPostArray(Parcel in) {
        super(in);
    }
}
