package com.vk.sdk.api.model;

import android.os.Parcel;
import android.os.Parcelable;
import org.json.JSONException;
import org.json.JSONObject;

public class VkVideoArray extends VKList<VKApiVideo> {
    public static Parcelable.Creator<VkVideoArray> CREATOR = new Parcelable.Creator<VkVideoArray>() {
        public VkVideoArray createFromParcel(Parcel source) {
            return new VkVideoArray(source);
        }

        public VkVideoArray[] newArray(int size) {
            return new VkVideoArray[size];
        }
    };

    public VKApiModel parse(JSONObject response) throws JSONException {
        fill(response, VKApiVideo.class);
        return this;
    }

    public VkVideoArray() {
    }

    public VkVideoArray(Parcel in) {
        super(in);
    }
}
