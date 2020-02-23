package com.vk.sdk.api.model;

import android.os.Parcel;
import android.os.Parcelable;
import org.json.JSONException;
import org.json.JSONObject;

public class VKCommentArray extends VKList<VKApiComment> {
    public static Parcelable.Creator<VKCommentArray> CREATOR = new Parcelable.Creator<VKCommentArray>() {
        public VKCommentArray createFromParcel(Parcel source) {
            return new VKCommentArray(source);
        }

        public VKCommentArray[] newArray(int size) {
            return new VKCommentArray[size];
        }
    };

    public VKApiModel parse(JSONObject response) throws JSONException {
        fill(response, VKApiComment.class);
        return this;
    }

    public VKCommentArray() {
    }

    public VKCommentArray(Parcel in) {
        super(in);
    }
}
