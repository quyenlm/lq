package com.vk.sdk.api.model;

import android.os.Parcel;
import android.os.Parcelable;
import org.json.JSONObject;

public class VKApiPostedPhoto extends VKApiPhoto {
    public static Parcelable.Creator<VKApiPostedPhoto> CREATOR = new Parcelable.Creator<VKApiPostedPhoto>() {
        public VKApiPostedPhoto createFromParcel(Parcel source) {
            return new VKApiPostedPhoto(source);
        }

        public VKApiPostedPhoto[] newArray(int size) {
            return new VKApiPostedPhoto[size];
        }
    };

    public VKApiPostedPhoto parse(JSONObject from) {
        super.parse(from);
        return this;
    }

    public VKApiPostedPhoto(Parcel in) {
        super(in);
    }

    public VKApiPostedPhoto() {
    }

    public String getType() {
        return VKAttachments.TYPE_POSTED_PHOTO;
    }
}
