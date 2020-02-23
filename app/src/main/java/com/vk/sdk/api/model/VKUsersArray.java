package com.vk.sdk.api.model;

import android.os.Parcel;
import android.os.Parcelable;
import org.json.JSONException;
import org.json.JSONObject;

public class VKUsersArray extends VKList<VKApiUserFull> {
    public static Parcelable.Creator<VKUsersArray> CREATOR = new Parcelable.Creator<VKUsersArray>() {
        public VKUsersArray createFromParcel(Parcel source) {
            return new VKUsersArray(source);
        }

        public VKUsersArray[] newArray(int size) {
            return new VKUsersArray[size];
        }
    };

    public VKApiModel parse(JSONObject response) throws JSONException {
        fill(response, VKApiUserFull.class);
        return this;
    }

    public VKUsersArray() {
    }

    public VKUsersArray(Parcel in) {
        super(in);
    }
}
