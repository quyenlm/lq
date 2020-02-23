package com.vk.sdk.api.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.tencent.component.net.download.multiplex.http.ContentType;
import com.vk.sdk.api.VKApiConst;
import org.json.JSONObject;

public class VKApiComment extends VKApiModel implements Identifiable, Parcelable {
    public static Parcelable.Creator<VKApiComment> CREATOR = new Parcelable.Creator<VKApiComment>() {
        public VKApiComment createFromParcel(Parcel source) {
            return new VKApiComment(source);
        }

        public VKApiComment[] newArray(int size) {
            return new VKApiComment[size];
        }
    };
    public VKAttachments attachments = new VKAttachments();
    public boolean can_like;
    public long date;
    public int from_id;
    public int id;
    public int likes;
    public int reply_to_comment;
    public int reply_to_user;
    public String text;
    public boolean user_likes;

    public VKApiComment(JSONObject from) {
        parse(from);
    }

    public VKApiComment parse(JSONObject from) {
        this.id = from.optInt("id");
        this.from_id = from.optInt("from_id");
        this.date = from.optLong("date");
        this.text = from.optString(ContentType.TYPE_TEXT);
        this.reply_to_user = from.optInt("reply_to_user");
        this.reply_to_comment = from.optInt("reply_to_comment");
        this.attachments.fill(from.optJSONArray(VKApiConst.ATTACHMENTS));
        JSONObject likes2 = from.optJSONObject("likes");
        this.likes = ParseUtils.parseInt(likes2, VKApiConst.COUNT);
        this.user_likes = ParseUtils.parseBoolean(likes2, "user_likes");
        this.can_like = ParseUtils.parseBoolean(likes2, "can_like");
        return this;
    }

    public VKApiComment(Parcel in) {
        boolean z;
        boolean z2 = true;
        this.id = in.readInt();
        this.from_id = in.readInt();
        this.date = in.readLong();
        this.text = in.readString();
        this.reply_to_user = in.readInt();
        this.reply_to_comment = in.readInt();
        this.likes = in.readInt();
        if (in.readByte() != 0) {
            z = true;
        } else {
            z = false;
        }
        this.user_likes = z;
        this.can_like = in.readByte() == 0 ? false : z2;
        this.attachments = (VKAttachments) in.readParcelable(VKAttachments.class.getClassLoader());
    }

    public VKApiComment() {
    }

    public int getId() {
        return this.id;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        byte b;
        byte b2 = 1;
        dest.writeInt(this.id);
        dest.writeInt(this.from_id);
        dest.writeLong(this.date);
        dest.writeString(this.text);
        dest.writeInt(this.reply_to_user);
        dest.writeInt(this.reply_to_comment);
        dest.writeInt(this.likes);
        if (this.user_likes) {
            b = 1;
        } else {
            b = 0;
        }
        dest.writeByte(b);
        if (!this.can_like) {
            b2 = 0;
        }
        dest.writeByte(b2);
        dest.writeParcelable(this.attachments, flags);
    }
}
