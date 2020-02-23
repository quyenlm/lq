package com.vk.sdk.api.model;

import android.os.Parcel;
import android.os.Parcelable;
import org.json.JSONException;
import org.json.JSONObject;

public class VKDocsArray extends VKList<VKApiDocument> {
    public static Parcelable.Creator<VKDocsArray> CREATOR = new Parcelable.Creator<VKDocsArray>() {
        public VKDocsArray createFromParcel(Parcel source) {
            return new VKDocsArray(source);
        }

        public VKDocsArray[] newArray(int size) {
            return new VKDocsArray[size];
        }
    };

    public VKApiModel parse(JSONObject response) throws JSONException {
        fill(response, VKApiDocument.class);
        return this;
    }

    public VKDocsArray() {
    }

    public VKDocsArray(Parcel in) {
        super(in);
    }
}
