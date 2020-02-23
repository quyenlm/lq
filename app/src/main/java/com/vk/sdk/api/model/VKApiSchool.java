package com.vk.sdk.api.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import org.json.JSONException;
import org.json.JSONObject;

public class VKApiSchool extends VKApiModel implements Parcelable, Identifiable {
    public static Parcelable.Creator<VKApiSchool> CREATOR = new Parcelable.Creator<VKApiSchool>() {
        public VKApiSchool createFromParcel(Parcel source) {
            return new VKApiSchool(source);
        }

        public VKApiSchool[] newArray(int size) {
            return new VKApiSchool[size];
        }
    };
    public int city_id;
    public String clazz;
    public int country_id;
    private String fullName;
    public int id;
    public String name;
    public String speciality;
    public int year_from;
    public int year_graduated;
    public int year_to;

    public VKApiSchool(JSONObject from) throws JSONException {
        parse(from);
    }

    public VKApiSchool parse(JSONObject from) {
        this.id = from.optInt("id");
        this.country_id = from.optInt("country_id");
        this.city_id = from.optInt("city_id");
        this.name = from.optString("name");
        this.year_from = from.optInt("year_from");
        this.year_to = from.optInt("year_to");
        this.year_graduated = from.optInt("year_graduated");
        this.clazz = from.optString("class");
        this.speciality = from.optString("speciality");
        return this;
    }

    public VKApiSchool(Parcel in) {
        this.id = in.readInt();
        this.country_id = in.readInt();
        this.city_id = in.readInt();
        this.name = in.readString();
        this.year_from = in.readInt();
        this.year_to = in.readInt();
        this.year_graduated = in.readInt();
        this.clazz = in.readString();
        this.speciality = in.readString();
    }

    public VKApiSchool() {
    }

    public int getId() {
        return this.id;
    }

    public String toString() {
        if (this.fullName == null) {
            StringBuilder builder = new StringBuilder(this.name);
            if (this.year_graduated != 0) {
                builder.append(" '");
                builder.append(String.format("%02d", new Object[]{Integer.valueOf(this.year_graduated % 100)}));
            }
            if (!(this.year_from == 0 || this.year_to == 0)) {
                builder.append(", ");
                builder.append(this.year_from);
                builder.append('-');
                builder.append(this.year_to);
            }
            if (!TextUtils.isEmpty(this.clazz)) {
                builder.append('(');
                builder.append(this.clazz);
                builder.append(')');
            }
            if (!TextUtils.isEmpty(this.speciality)) {
                builder.append(", ");
                builder.append(this.speciality);
            }
            this.fullName = builder.toString();
        }
        return this.fullName;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeInt(this.country_id);
        dest.writeInt(this.city_id);
        dest.writeString(this.name);
        dest.writeInt(this.year_from);
        dest.writeInt(this.year_to);
        dest.writeInt(this.year_graduated);
        dest.writeString(this.clazz);
        dest.writeString(this.speciality);
    }
}
