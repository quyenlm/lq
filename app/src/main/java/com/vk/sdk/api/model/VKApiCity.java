package com.vk.sdk.api.model;

import android.os.Parcel;
import android.os.Parcelable;
import org.json.JSONObject;

public class VKApiCity extends VKApiModel implements Parcelable, Identifiable {
    public static Parcelable.Creator<VKApiCity> CREATOR = new Parcelable.Creator<VKApiCity>() {
        public VKApiCity createFromParcel(Parcel source) {
            return new VKApiCity(source);
        }

        public VKApiCity[] newArray(int size) {
            return new VKApiCity[size];
        }
    };
    public int id;
    public String title;

    public VKApiCity(JSONObject from) {
        parse(from);
    }

    public VKApiCity parse(JSONObject from) {
        this.id = from.optInt("id");
        this.title = from.optString("title");
        return this;
    }

    public VKApiCity(Parcel in) {
        this.id = in.readInt();
        this.title = in.readString();
    }

    public VKApiCity() {
    }

    public int getId() {
        return this.id;
    }

    public String toString() {
        return this.title;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.title);
    }
}
