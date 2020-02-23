package com.vk.sdk.api.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.model.VKAttachments;
import org.json.JSONException;
import org.json.JSONObject;

public class VKApiVideo extends VKAttachments.VKApiAttachment implements Parcelable, Identifiable {
    public static Parcelable.Creator<VKApiVideo> CREATOR = new Parcelable.Creator<VKApiVideo>() {
        public VKApiVideo createFromParcel(Parcel source) {
            return new VKApiVideo(source);
        }

        public VKApiVideo[] newArray(int size) {
            return new VKApiVideo[size];
        }
    };
    public String access_key;
    public int album_id;
    public boolean can_comment;
    public boolean can_repost;
    public int comments;
    public long date;
    public String description;
    public int duration;
    public String external;
    public int id;
    public int likes;
    public String link;
    public String mp4_1080;
    public String mp4_240;
    public String mp4_360;
    public String mp4_480;
    public String mp4_720;
    public int owner_id;
    public VKPhotoSizes photo = new VKPhotoSizes();
    public String photo_130;
    public String photo_320;
    public String photo_640;
    public String player;
    public int privacy_comment;
    public int privacy_view;
    public boolean repeat;
    public String title;
    public boolean user_likes;
    public int views;

    public VKApiVideo(JSONObject from) throws JSONException {
        parse(from);
    }

    public VKApiVideo parse(JSONObject from) {
        this.id = from.optInt("id");
        this.owner_id = from.optInt(VKApiConst.OWNER_ID);
        this.title = from.optString("title");
        this.description = from.optString("description");
        this.duration = from.optInt("duration");
        this.link = from.optString("link");
        this.date = from.optLong("date");
        this.views = from.optInt("views");
        this.comments = from.optInt("comments");
        this.player = from.optString("player");
        this.access_key = from.optString("access_key");
        this.album_id = from.optInt(VKApiConst.ALBUM_ID);
        JSONObject likes2 = from.optJSONObject("likes");
        if (likes2 != null) {
            this.likes = likes2.optInt(VKApiConst.COUNT);
            this.user_likes = ParseUtils.parseBoolean(likes2, "user_likes");
        }
        this.can_comment = ParseUtils.parseBoolean(from, "can_comment");
        this.can_repost = ParseUtils.parseBoolean(from, "can_repost");
        this.repeat = ParseUtils.parseBoolean(from, "repeat");
        this.privacy_view = VKPrivacy.parsePrivacy(from.optJSONObject("privacy_view"));
        this.privacy_comment = VKPrivacy.parsePrivacy(from.optJSONObject("privacy_comment"));
        JSONObject files = from.optJSONObject("files");
        if (files != null) {
            this.mp4_240 = files.optString("mp4_240");
            this.mp4_360 = files.optString("mp4_360");
            this.mp4_480 = files.optString("mp4_480");
            this.mp4_720 = files.optString("mp4_720");
            this.mp4_1080 = files.optString("mp4_1080");
            this.external = files.optString("external");
        }
        this.photo_130 = from.optString("photo_130");
        if (!TextUtils.isEmpty(this.photo_130)) {
            this.photo.add(VKApiPhotoSize.create(this.photo_130, 130));
        }
        this.photo_320 = from.optString("photo_320");
        if (!TextUtils.isEmpty(this.photo_320)) {
            this.photo.add(VKApiPhotoSize.create(this.photo_320, 320));
        }
        this.photo_640 = from.optString("photo_640");
        if (!TextUtils.isEmpty(this.photo_640)) {
            this.photo.add(VKApiPhotoSize.create(this.photo_640, 640));
        }
        return this;
    }

    public VKApiVideo(Parcel in) {
        boolean z;
        boolean z2;
        boolean z3 = true;
        this.id = in.readInt();
        this.owner_id = in.readInt();
        this.album_id = in.readInt();
        this.title = in.readString();
        this.description = in.readString();
        this.duration = in.readInt();
        this.link = in.readString();
        this.date = in.readLong();
        this.views = in.readInt();
        this.player = in.readString();
        this.photo_130 = in.readString();
        this.photo_320 = in.readString();
        this.photo_640 = in.readString();
        this.photo = (VKPhotoSizes) in.readParcelable(VKPhotoSizes.class.getClassLoader());
        this.access_key = in.readString();
        this.comments = in.readInt();
        this.can_comment = in.readByte() != 0;
        if (in.readByte() != 0) {
            z = true;
        } else {
            z = false;
        }
        this.can_repost = z;
        if (in.readByte() != 0) {
            z2 = true;
        } else {
            z2 = false;
        }
        this.user_likes = z2;
        this.repeat = in.readByte() == 0 ? false : z3;
        this.likes = in.readInt();
        this.privacy_view = in.readInt();
        this.privacy_comment = in.readInt();
        this.mp4_240 = in.readString();
        this.mp4_360 = in.readString();
        this.mp4_480 = in.readString();
        this.mp4_720 = in.readString();
        this.mp4_1080 = in.readString();
        this.external = in.readString();
    }

    public VKApiVideo() {
    }

    public int getId() {
        return this.id;
    }

    public CharSequence toAttachmentString() {
        StringBuilder result = new StringBuilder("video").append(this.owner_id).append('_').append(this.id);
        if (!TextUtils.isEmpty(this.access_key)) {
            result.append('_');
            result.append(this.access_key);
        }
        return result;
    }

    public String getType() {
        return "video";
    }

    public String toString() {
        return this.title;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        byte b;
        byte b2;
        byte b3 = 1;
        dest.writeInt(this.id);
        dest.writeInt(this.owner_id);
        dest.writeInt(this.album_id);
        dest.writeString(this.title);
        dest.writeString(this.description);
        dest.writeInt(this.duration);
        dest.writeString(this.link);
        dest.writeLong(this.date);
        dest.writeInt(this.views);
        dest.writeString(this.player);
        dest.writeString(this.photo_130);
        dest.writeString(this.photo_320);
        dest.writeString(this.photo_640);
        dest.writeParcelable(this.photo, flags);
        dest.writeString(this.access_key);
        dest.writeInt(this.comments);
        dest.writeByte(this.can_comment ? (byte) 1 : 0);
        if (this.can_repost) {
            b = 1;
        } else {
            b = 0;
        }
        dest.writeByte(b);
        if (this.user_likes) {
            b2 = 1;
        } else {
            b2 = 0;
        }
        dest.writeByte(b2);
        if (!this.repeat) {
            b3 = 0;
        }
        dest.writeByte(b3);
        dest.writeInt(this.likes);
        dest.writeInt(this.privacy_view);
        dest.writeInt(this.privacy_comment);
        dest.writeString(this.mp4_240);
        dest.writeString(this.mp4_360);
        dest.writeString(this.mp4_480);
        dest.writeString(this.mp4_720);
        dest.writeString(this.mp4_1080);
        dest.writeString(this.external);
    }
}
