package com.vk.sdk.api.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import com.tencent.component.net.download.multiplex.http.ContentType;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.model.VKAttachments;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class VKApiPhoto extends VKAttachments.VKApiAttachment implements Parcelable, Identifiable {
    public static Parcelable.Creator<VKApiPhoto> CREATOR = new Parcelable.Creator<VKApiPhoto>() {
        public VKApiPhoto createFromParcel(Parcel source) {
            return new VKApiPhoto(source);
        }

        public VKApiPhoto[] newArray(int size) {
            return new VKApiPhoto[size];
        }
    };
    public String access_key;
    public int album_id;
    public boolean can_comment;
    public int comments;
    public long date;
    public int height;
    public int id;
    public int likes;
    public int owner_id;
    public String photo_1280;
    public String photo_130;
    public String photo_2560;
    public String photo_604;
    public String photo_75;
    public String photo_807;
    public VKPhotoSizes src = new VKPhotoSizes();
    public int tags;
    public String text;
    public boolean user_likes;
    public int width;

    public VKApiPhoto(JSONObject from) throws JSONException {
        parse(from);
    }

    public VKApiPhoto parse(JSONObject from) {
        this.album_id = from.optInt(VKApiConst.ALBUM_ID);
        this.date = from.optLong("date");
        this.height = from.optInt("height");
        this.width = from.optInt("width");
        this.owner_id = from.optInt(VKApiConst.OWNER_ID);
        this.id = from.optInt("id");
        this.text = from.optString(ContentType.TYPE_TEXT);
        this.access_key = from.optString("access_key");
        this.photo_75 = from.optString("photo_75");
        this.photo_130 = from.optString("photo_130");
        this.photo_604 = from.optString("photo_604");
        this.photo_807 = from.optString("photo_807");
        this.photo_1280 = from.optString("photo_1280");
        this.photo_2560 = from.optString("photo_2560");
        JSONObject likes2 = from.optJSONObject("likes");
        this.likes = ParseUtils.parseInt(likes2, VKApiConst.COUNT);
        this.user_likes = ParseUtils.parseBoolean(likes2, "user_likes");
        this.comments = ParseUtils.parseInt(from.optJSONObject("comments"), VKApiConst.COUNT);
        this.tags = ParseUtils.parseInt(from.optJSONObject("tags"), VKApiConst.COUNT);
        this.can_comment = ParseUtils.parseBoolean(from, "can_comment");
        this.src.setOriginalDimension(this.width, this.height);
        JSONArray photo_sizes = from.optJSONArray("sizes");
        if (photo_sizes != null) {
            this.src.fill(photo_sizes);
        } else {
            if (!TextUtils.isEmpty(this.photo_75)) {
                this.src.add(VKApiPhotoSize.create(this.photo_75, VKApiPhotoSize.S, this.width, this.height));
            }
            if (!TextUtils.isEmpty(this.photo_130)) {
                this.src.add(VKApiPhotoSize.create(this.photo_130, VKApiPhotoSize.M, this.width, this.height));
            }
            if (!TextUtils.isEmpty(this.photo_604)) {
                this.src.add(VKApiPhotoSize.create(this.photo_604, VKApiPhotoSize.X, this.width, this.height));
            }
            if (!TextUtils.isEmpty(this.photo_807)) {
                this.src.add(VKApiPhotoSize.create(this.photo_807, VKApiPhotoSize.Y, this.width, this.height));
            }
            if (!TextUtils.isEmpty(this.photo_1280)) {
                this.src.add(VKApiPhotoSize.create(this.photo_1280, VKApiPhotoSize.Z, this.width, this.height));
            }
            if (!TextUtils.isEmpty(this.photo_2560)) {
                this.src.add(VKApiPhotoSize.create(this.photo_2560, VKApiPhotoSize.W, this.width, this.height));
            }
            this.src.sort();
        }
        return this;
    }

    public VKApiPhoto(Parcel in) {
        boolean z = true;
        this.id = in.readInt();
        this.album_id = in.readInt();
        this.owner_id = in.readInt();
        this.width = in.readInt();
        this.height = in.readInt();
        this.text = in.readString();
        this.date = in.readLong();
        this.src = (VKPhotoSizes) in.readParcelable(VKPhotoSizes.class.getClassLoader());
        this.photo_75 = in.readString();
        this.photo_130 = in.readString();
        this.photo_604 = in.readString();
        this.photo_807 = in.readString();
        this.photo_1280 = in.readString();
        this.photo_2560 = in.readString();
        this.user_likes = in.readByte() != 0;
        this.can_comment = in.readByte() == 0 ? false : z;
        this.likes = in.readInt();
        this.comments = in.readInt();
        this.tags = in.readInt();
        this.access_key = in.readString();
    }

    public VKApiPhoto(String photoAttachmentString) {
        if (photoAttachmentString.startsWith("photo")) {
            String[] ids = photoAttachmentString.substring("photo".length()).split("_");
            this.owner_id = Integer.parseInt(ids[0]);
            this.id = Integer.parseInt(ids[1]);
        }
    }

    public VKApiPhoto() {
    }

    public int getId() {
        return this.id;
    }

    public CharSequence toAttachmentString() {
        StringBuilder result = new StringBuilder("photo").append(this.owner_id).append('_').append(this.id);
        if (!TextUtils.isEmpty(this.access_key)) {
            result.append('_');
            result.append(this.access_key);
        }
        return result;
    }

    public String getType() {
        return "photo";
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        byte b = 1;
        dest.writeInt(this.id);
        dest.writeInt(this.album_id);
        dest.writeInt(this.owner_id);
        dest.writeInt(this.width);
        dest.writeInt(this.height);
        dest.writeString(this.text);
        dest.writeLong(this.date);
        dest.writeParcelable(this.src, flags);
        dest.writeString(this.photo_75);
        dest.writeString(this.photo_130);
        dest.writeString(this.photo_604);
        dest.writeString(this.photo_807);
        dest.writeString(this.photo_1280);
        dest.writeString(this.photo_2560);
        dest.writeByte(this.user_likes ? (byte) 1 : 0);
        if (!this.can_comment) {
            b = 0;
        }
        dest.writeByte(b);
        dest.writeInt(this.likes);
        dest.writeInt(this.comments);
        dest.writeInt(this.tags);
        dest.writeString(this.access_key);
    }
}
