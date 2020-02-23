package com.vk.sdk.api.model;

import android.os.Parcel;
import android.os.Parcelable;
import org.json.JSONException;
import org.json.JSONObject;

public class VkAudioArray extends VKList<VKApiAudio> {
    public static Parcelable.Creator<VkAudioArray> CREATOR = new Parcelable.Creator<VkAudioArray>() {
        public VkAudioArray createFromParcel(Parcel source) {
            return new VkAudioArray(source);
        }

        public VkAudioArray[] newArray(int size) {
            return new VkAudioArray[size];
        }
    };

    public VKApiModel parse(JSONObject response) throws JSONException {
        fill(response, VKApiAudio.class);
        return this;
    }

    public VkAudioArray() {
    }

    public VkAudioArray(Parcel in) {
        super(in);
    }
}
