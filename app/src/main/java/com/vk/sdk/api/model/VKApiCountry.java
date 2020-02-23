package com.vk.sdk.api.model;

import android.os.Parcel;
import android.os.Parcelable;
import org.json.JSONException;
import org.json.JSONObject;

public class VKApiCountry extends VKApiModel implements Parcelable, Identifiable {
    public static Parcelable.Creator<VKApiCountry> CREATOR = new Parcelable.Creator<VKApiCountry>() {
        public VKApiCountry createFromParcel(Parcel source) {
            return new VKApiCountry(source);
        }

        public VKApiCountry[] newArray(int size) {
            return new VKApiCountry[size];
        }
    };
    public int id;
    public String title;

    public VKApiCountry(JSONObject from) throws JSONException {
        parse(from);
    }

    public VKApiCountry parse(JSONObject from) {
        this.id = from.optInt("id");
        this.title = from.optString("title");
        return this;
    }

    public VKApiCountry(Parcel in) {
        this.id = in.readInt();
        this.title = in.readString();
    }

    public VKApiCountry() {
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
