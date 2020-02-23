package com.vk.sdk.api.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import org.json.JSONException;
import org.json.JSONObject;

public class VKApiUniversity extends VKApiModel implements Parcelable, Identifiable {
    public static Parcelable.Creator<VKApiUniversity> CREATOR = new Parcelable.Creator<VKApiUniversity>() {
        public VKApiUniversity createFromParcel(Parcel source) {
            return new VKApiUniversity(source);
        }

        public VKApiUniversity[] newArray(int size) {
            return new VKApiUniversity[size];
        }
    };
    public int chair;
    public String chair_name;
    public int city_id;
    public int country_id;
    public String education_form;
    public String education_status;
    public String faculty;
    public String faculty_name;
    private String fullName;
    public int graduation;
    public int id;
    public String name;

    public VKApiUniversity(JSONObject from) throws JSONException {
        parse(from);
    }

    public VKApiUniversity parse(JSONObject from) {
        this.id = from.optInt("id");
        this.country_id = from.optInt("country_id");
        this.city_id = from.optInt("city_id");
        this.name = from.optString("name");
        this.faculty = from.optString("faculty");
        this.faculty_name = from.optString("faculty_name");
        this.chair = from.optInt("chair");
        this.chair_name = from.optString("chair_name");
        this.graduation = from.optInt("graduation");
        this.education_form = from.optString("education_form");
        this.education_status = from.optString("education_status");
        return this;
    }

    public VKApiUniversity(Parcel in) {
        this.id = in.readInt();
        this.country_id = in.readInt();
        this.city_id = in.readInt();
        this.name = in.readString();
        this.faculty = in.readString();
        this.faculty_name = in.readString();
        this.chair = in.readInt();
        this.chair_name = in.readString();
        this.graduation = in.readInt();
        this.education_form = in.readString();
        this.education_status = in.readString();
    }

    public VKApiUniversity() {
    }

    public String toString() {
        if (this.fullName == null) {
            StringBuilder result = new StringBuilder(this.name);
            result.append(" '");
            result.append(String.format("%02d", new Object[]{Integer.valueOf(this.graduation % 100)}));
            if (!TextUtils.isEmpty(this.faculty_name)) {
                result.append(", ");
                result.append(this.faculty_name);
            }
            if (!TextUtils.isEmpty(this.chair_name)) {
                result.append(", ");
                result.append(this.chair_name);
            }
            this.fullName = result.toString();
        }
        return this.fullName;
    }

    public int getId() {
        return this.id;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeInt(this.country_id);
        dest.writeInt(this.city_id);
        dest.writeString(this.name);
        dest.writeString(this.faculty);
        dest.writeString(this.faculty_name);
        dest.writeInt(this.chair);
        dest.writeString(this.chair_name);
        dest.writeInt(this.graduation);
        dest.writeString(this.education_form);
        dest.writeString(this.education_status);
    }
}
