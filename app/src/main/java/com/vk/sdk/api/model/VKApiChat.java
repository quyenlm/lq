package com.vk.sdk.api.model;

import android.os.Parcel;
import android.os.Parcelable;
import org.json.JSONArray;
import org.json.JSONObject;

public class VKApiChat extends VKApiModel implements Identifiable, Parcelable {
    public static Parcelable.Creator<VKApiChat> CREATOR = new Parcelable.Creator<VKApiChat>() {
        public VKApiChat createFromParcel(Parcel source) {
            return new VKApiChat(source);
        }

        public VKApiChat[] newArray(int size) {
            return new VKApiChat[size];
        }
    };
    public int admin_id;
    public int id;
    public String title;
    public String type;
    public int[] users;

    public VKApiChat(JSONObject from) {
        parse(from);
    }

    public VKApiChat parse(JSONObject source) {
        this.id = source.optInt("id");
        this.type = source.optString("type");
        this.title = source.optString("title");
        this.admin_id = source.optInt("admin_id");
        JSONArray users2 = source.optJSONArray("users");
        if (users2 != null) {
            this.users = new int[users2.length()];
            for (int i = 0; i < this.users.length; i++) {
                this.users[i] = users2.optInt(i);
            }
        }
        return this;
    }

    public VKApiChat(Parcel in) {
        this.id = in.readInt();
        this.type = in.readString();
        this.title = in.readString();
        this.admin_id = in.readInt();
        this.users = in.createIntArray();
    }

    public VKApiChat() {
    }

    public int getId() {
        return this.id;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.type);
        dest.writeString(this.title);
        dest.writeInt(this.admin_id);
        dest.writeIntArray(this.users);
    }
}
