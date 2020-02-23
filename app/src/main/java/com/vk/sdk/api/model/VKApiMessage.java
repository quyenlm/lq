package com.vk.sdk.api.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.vk.sdk.api.VKApiConst;
import org.json.JSONException;
import org.json.JSONObject;

public class VKApiMessage extends VKApiModel implements Identifiable, Parcelable {
    public static Parcelable.Creator<VKApiMessage> CREATOR = new Parcelable.Creator<VKApiMessage>() {
        public VKApiMessage createFromParcel(Parcel source) {
            return new VKApiMessage(source);
        }

        public VKApiMessage[] newArray(int size) {
            return new VKApiMessage[size];
        }
    };
    public VKAttachments attachments = new VKAttachments();
    public String body;
    public long date;
    public boolean deleted;
    public boolean emoji;
    public VKList<VKApiMessage> fwd_messages;
    public int id;
    public boolean out;
    public boolean read_state;
    public String title;
    public int user_id;

    public VKApiMessage(JSONObject from) throws JSONException {
        parse(from);
    }

    public VKApiMessage parse(JSONObject source) throws JSONException {
        this.id = source.optInt("id");
        this.user_id = source.optInt("user_id");
        this.date = source.optLong("date");
        this.read_state = ParseUtils.parseBoolean(source, "read_state");
        this.out = ParseUtils.parseBoolean(source, VKApiConst.OUT);
        this.title = source.optString("title");
        this.body = source.optString("body");
        this.attachments.fill(source.optJSONArray(VKApiConst.ATTACHMENTS));
        this.fwd_messages = new VKList<>(source.optJSONArray("fwd_messages"), VKApiMessage.class);
        this.emoji = ParseUtils.parseBoolean(source, "emoji");
        this.deleted = ParseUtils.parseBoolean(source, "deleted");
        return this;
    }

    public VKApiMessage(Parcel in) {
        boolean z;
        boolean z2;
        boolean z3 = true;
        this.id = in.readInt();
        this.user_id = in.readInt();
        this.date = in.readLong();
        this.read_state = in.readByte() != 0;
        if (in.readByte() != 0) {
            z = true;
        } else {
            z = false;
        }
        this.out = z;
        this.title = in.readString();
        this.body = in.readString();
        this.attachments = (VKAttachments) in.readParcelable(VKAttachments.class.getClassLoader());
        this.fwd_messages = (VKList) in.readParcelable(VKList.class.getClassLoader());
        if (in.readByte() != 0) {
            z2 = true;
        } else {
            z2 = false;
        }
        this.emoji = z2;
        this.deleted = in.readByte() == 0 ? false : z3;
    }

    public VKApiMessage() {
    }

    public int getId() {
        return this.id;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        byte b;
        byte b2;
        byte b3 = 1;
        dest.writeInt(this.id);
        dest.writeInt(this.user_id);
        dest.writeLong(this.date);
        dest.writeByte(this.read_state ? (byte) 1 : 0);
        if (this.out) {
            b = 1;
        } else {
            b = 0;
        }
        dest.writeByte(b);
        dest.writeString(this.title);
        dest.writeString(this.body);
        dest.writeParcelable(this.attachments, flags);
        dest.writeParcelable(this.fwd_messages, flags);
        if (this.emoji) {
            b2 = 1;
        } else {
            b2 = 0;
        }
        dest.writeByte(b2);
        if (!this.deleted) {
            b3 = 0;
        }
        dest.writeByte(b3);
    }
}
