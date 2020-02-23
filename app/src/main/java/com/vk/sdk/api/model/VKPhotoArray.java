package com.vk.sdk.api.model;

import android.os.Parcel;
import android.os.Parcelable;
import org.json.JSONException;
import org.json.JSONObject;

public class VKPhotoArray extends VKList<VKApiPhoto> {
    public static Parcelable.Creator<VKPhotoArray> CREATOR = new Parcelable.Creator<VKPhotoArray>() {
        public VKPhotoArray createFromParcel(Parcel source) {
            return new VKPhotoArray(source);
        }

        public VKPhotoArray[] newArray(int size) {
            return new VKPhotoArray[size];
        }
    };

    public VKApiModel parse(JSONObject response) throws JSONException {
        fill(response, VKApiPhoto.class);
        return this;
    }

    public VKPhotoArray() {
    }

    public VKPhotoArray(Parcel in) {
        super(in);
    }
}
