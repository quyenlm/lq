package com.vk.sdk.api.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.tencent.component.net.download.multiplex.http.ContentType;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.model.VKAttachments;
import org.json.JSONException;
import org.json.JSONObject;

public class VKApiPost extends VKAttachments.VKApiAttachment implements Identifiable, Parcelable {
    public static Parcelable.Creator<VKApiPost> CREATOR = new Parcelable.Creator<VKApiPost>() {
        public VKApiPost createFromParcel(Parcel source) {
            return new VKApiPost(source);
        }

        public VKApiPost[] newArray(int size) {
            return new VKApiPost[size];
        }
    };
    public VKAttachments attachments = new VKAttachments();
    public boolean can_like;
    public boolean can_post_comment;
    public boolean can_publish;
    public int comments_count;
    public VKList<VKApiPost> copy_history;
    public long date;
    public boolean friends_only;
    public int from_id;
    public VKApiPlace geo;
    public int id;
    public int likes_count;
    public String post_type;
    public int reply_owner_id;
    public int reply_post_id;
    public int reposts_count;
    public int signer_id;
    public String text;
    public int to_id;
    public boolean user_likes;
    public boolean user_reposted;

    public VKApiPost(JSONObject from) throws JSONException {
        parse(from);
    }

    public VKApiPost parse(JSONObject source) throws JSONException {
        this.id = source.optInt("id");
        this.to_id = source.optInt("to_id");
        this.from_id = source.optInt("from_id");
        this.date = source.optLong("date");
        this.text = source.optString(ContentType.TYPE_TEXT);
        this.reply_owner_id = source.optInt("reply_owner_id");
        this.reply_post_id = source.optInt("reply_post_id");
        this.friends_only = ParseUtils.parseBoolean(source, VKApiConst.FRIENDS_ONLY);
        JSONObject comments = source.optJSONObject("comments");
        if (comments != null) {
            this.comments_count = comments.optInt(VKApiConst.COUNT);
            this.can_post_comment = ParseUtils.parseBoolean(comments, "can_post");
        }
        JSONObject likes = source.optJSONObject("likes");
        if (likes != null) {
            this.likes_count = likes.optInt(VKApiConst.COUNT);
            this.user_likes = ParseUtils.parseBoolean(likes, "user_likes");
            this.can_like = ParseUtils.parseBoolean(likes, "can_like");
            this.can_publish = ParseUtils.parseBoolean(likes, "can_publish");
        }
        JSONObject reposts = source.optJSONObject("reposts");
        if (reposts != null) {
            this.reposts_count = reposts.optInt(VKApiConst.COUNT);
            this.user_reposted = ParseUtils.parseBoolean(reposts, "user_reposted");
        }
        this.post_type = source.optString("post_type");
        this.attachments.fill(source.optJSONArray(VKApiConst.ATTACHMENTS));
        JSONObject geo2 = source.optJSONObject("geo");
        if (geo2 != null) {
            this.geo = new VKApiPlace().parse(geo2);
        }
        this.signer_id = source.optInt("signer_id");
        this.copy_history = new VKList<>(source.optJSONArray("copy_history"), VKApiPost.class);
        return this;
    }

    public VKApiPost(Parcel in) {
        boolean z;
        boolean z2;
        boolean z3;
        boolean z4;
        boolean z5 = true;
        this.id = in.readInt();
        this.to_id = in.readInt();
        this.from_id = in.readInt();
        this.date = in.readLong();
        this.text = in.readString();
        this.reply_owner_id = in.readInt();
        this.reply_post_id = in.readInt();
        this.friends_only = in.readByte() != 0;
        this.comments_count = in.readInt();
        if (in.readByte() != 0) {
            z = true;
        } else {
            z = false;
        }
        this.can_post_comment = z;
        this.likes_count = in.readInt();
        if (in.readByte() != 0) {
            z2 = true;
        } else {
            z2 = false;
        }
        this.user_likes = z2;
        if (in.readByte() != 0) {
            z3 = true;
        } else {
            z3 = false;
        }
        this.can_like = z3;
        if (in.readByte() != 0) {
            z4 = true;
        } else {
            z4 = false;
        }
        this.can_publish = z4;
        this.reposts_count = in.readInt();
        this.user_reposted = in.readByte() == 0 ? false : z5;
        this.post_type = in.readString();
        this.attachments = (VKAttachments) in.readParcelable(VKAttachments.class.getClassLoader());
        this.geo = (VKApiPlace) in.readParcelable(VKApiPlace.class.getClassLoader());
        this.signer_id = in.readInt();
    }

    public VKApiPost() {
    }

    public int getId() {
        return this.id;
    }

    public CharSequence toAttachmentString() {
        return new StringBuilder("wall").append(this.to_id).append('_').append(this.id);
    }

    public String getType() {
        return "wall";
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        byte b;
        byte b2;
        byte b3;
        byte b4;
        byte b5 = 1;
        dest.writeInt(this.id);
        dest.writeInt(this.to_id);
        dest.writeInt(this.from_id);
        dest.writeLong(this.date);
        dest.writeString(this.text);
        dest.writeInt(this.reply_owner_id);
        dest.writeInt(this.reply_post_id);
        dest.writeByte(this.friends_only ? (byte) 1 : 0);
        dest.writeInt(this.comments_count);
        if (this.can_post_comment) {
            b = 1;
        } else {
            b = 0;
        }
        dest.writeByte(b);
        dest.writeInt(this.likes_count);
        if (this.user_likes) {
            b2 = 1;
        } else {
            b2 = 0;
        }
        dest.writeByte(b2);
        if (this.can_like) {
            b3 = 1;
        } else {
            b3 = 0;
        }
        dest.writeByte(b3);
        if (this.can_publish) {
            b4 = 1;
        } else {
            b4 = 0;
        }
        dest.writeByte(b4);
        dest.writeInt(this.reposts_count);
        if (!this.user_reposted) {
            b5 = 0;
        }
        dest.writeByte(b5);
        dest.writeString(this.post_type);
        dest.writeParcelable(this.attachments, flags);
        dest.writeParcelable(this.geo, flags);
        dest.writeInt(this.signer_id);
    }
}
