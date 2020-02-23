package com.vk.sdk.api.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.facebook.places.model.PlaceFields;
import com.vk.sdk.VKAccessToken;
import org.json.JSONException;
import org.json.JSONObject;

public class VKApiPlace extends VKApiModel implements Parcelable, Identifiable {
    public static Parcelable.Creator<VKApiPlace> CREATOR = new Parcelable.Creator<VKApiPlace>() {
        public VKApiPlace createFromParcel(Parcel source) {
            return new VKApiPlace(source);
        }

        public VKApiPlace[] newArray(int size) {
            return new VKApiPlace[size];
        }
    };
    public String address;
    public int checkins;
    public int city_id;
    public int country_id;
    public long created;
    public int id;
    public double latitude;
    public double longitude;
    public String title;
    public long updated;

    public VKApiPlace(JSONObject from) throws JSONException {
        parse(from);
    }

    public VKApiPlace parse(JSONObject from) {
        this.id = from.optInt("id");
        this.title = from.optString("title");
        this.latitude = from.optDouble("latitude");
        this.longitude = from.optDouble("longitude");
        this.created = from.optLong(VKAccessToken.CREATED);
        this.checkins = from.optInt(PlaceFields.CHECKINS);
        this.updated = from.optLong("updated");
        this.country_id = from.optInt("country");
        this.city_id = from.optInt("city");
        this.address = from.optString("address");
        return this;
    }

    public VKApiPlace(Parcel in) {
        this.id = in.readInt();
        this.title = in.readString();
        this.latitude = in.readDouble();
        this.longitude = in.readDouble();
        this.created = in.readLong();
        this.checkins = in.readInt();
        this.updated = in.readLong();
        this.country_id = in.readInt();
        this.city_id = in.readInt();
        this.address = in.readString();
    }

    public VKApiPlace() {
    }

    public int getId() {
        return this.id;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.title);
        dest.writeDouble(this.latitude);
        dest.writeDouble(this.longitude);
        dest.writeLong(this.created);
        dest.writeInt(this.checkins);
        dest.writeLong(this.updated);
        dest.writeInt(this.country_id);
        dest.writeInt(this.city_id);
        dest.writeString(this.address);
    }

    public String toString() {
        return this.address;
    }
}
