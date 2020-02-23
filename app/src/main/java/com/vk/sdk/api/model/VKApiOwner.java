package com.vk.sdk.api.model;

import android.os.Parcel;
import android.os.Parcelable;
import org.json.JSONException;
import org.json.JSONObject;

public class VKApiOwner extends VKApiModel implements Identifiable, Parcelable {
    public static Parcelable.Creator<VKApiOwner> CREATOR = new Parcelable.Creator<VKApiOwner>() {
        public VKApiOwner createFromParcel(Parcel source) {
            return new VKApiOwner(source);
        }

        public VKApiOwner[] newArray(int size) {
            return new VKApiOwner[size];
        }
    };
    public int id;

    public VKApiOwner() {
    }

    public VKApiOwner(JSONObject from) throws JSONException {
        parse(from);
    }

    public VKApiOwner parse(JSONObject from) {
        this.fields = from;
        this.id = from.optInt("id");
        return this;
    }

    public VKApiOwner(int id2) {
        this.id = id2;
    }

    public VKApiOwner(Parcel in) {
        this.id = in.readInt();
    }

    public int getId() {
        return this.id;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
    }
}
