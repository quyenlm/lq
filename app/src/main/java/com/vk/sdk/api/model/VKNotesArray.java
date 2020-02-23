package com.vk.sdk.api.model;

import android.os.Parcel;
import android.os.Parcelable;
import org.json.JSONException;
import org.json.JSONObject;

public class VKNotesArray extends VKList<VKApiNote> {
    public static Parcelable.Creator<VKNotesArray> CREATOR = new Parcelable.Creator<VKNotesArray>() {
        public VKNotesArray createFromParcel(Parcel source) {
            return new VKNotesArray(source);
        }

        public VKNotesArray[] newArray(int size) {
            return new VKNotesArray[size];
        }
    };

    public VKApiModel parse(JSONObject response) throws JSONException {
        fill(response, VKApiNote.class);
        return this;
    }

    public VKNotesArray() {
    }

    public VKNotesArray(Parcel in) {
        super(in);
    }
}
