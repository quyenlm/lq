package com.vk.sdk.api.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import com.tencent.component.net.download.multiplex.http.ContentType;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.model.VKAttachments;
import org.json.JSONException;
import org.json.JSONObject;

public class VKApiDocument extends VKAttachments.VKApiAttachment implements Parcelable, Identifiable {
    public static Parcelable.Creator<VKApiDocument> CREATOR = new Parcelable.Creator<VKApiDocument>() {
        public VKApiDocument createFromParcel(Parcel source) {
            return new VKApiDocument(source);
        }

        public VKApiDocument[] newArray(int size) {
            return new VKApiDocument[size];
        }
    };
    public String access_key;
    public long date = 0;
    public String ext;
    public int id;
    private boolean mIsGif;
    private boolean mIsImage;
    public int owner_id;
    public VKPhotoSizes photo = new VKPhotoSizes();
    public String photo_100;
    public String photo_130;
    public long size;
    public String title;
    public String url;

    public VKApiDocument(JSONObject from) throws JSONException {
        parse(from);
    }

    public VKApiDocument parse(JSONObject jo) {
        this.id = jo.optInt("id");
        this.owner_id = jo.optInt(VKApiConst.OWNER_ID);
        this.title = jo.optString("title");
        this.size = jo.optLong("size");
        this.ext = jo.optString("ext");
        this.url = jo.optString("url");
        this.access_key = jo.optString("access_key");
        this.date = jo.optLong("date", 0) * 1000;
        this.photo_100 = jo.optString(VKApiUser.FIELD_PHOTO_100);
        if (!TextUtils.isEmpty(this.photo_100)) {
            this.photo.add(VKApiPhotoSize.create(this.photo_100, 100, 75));
        }
        this.photo_130 = jo.optString("photo_130");
        if (!TextUtils.isEmpty(this.photo_130)) {
            this.photo.add(VKApiPhotoSize.create(this.photo_130, 130, 100));
        }
        this.photo.sort();
        return this;
    }

    public VKApiDocument(Parcel in) {
        boolean z;
        boolean z2 = true;
        this.id = in.readInt();
        this.owner_id = in.readInt();
        this.title = in.readString();
        this.size = in.readLong();
        this.ext = in.readString();
        this.url = in.readString();
        this.date = in.readLong();
        this.photo_100 = in.readString();
        this.photo_130 = in.readString();
        this.photo = (VKPhotoSizes) in.readParcelable(VKPhotoSizes.class.getClassLoader());
        this.access_key = in.readString();
        if (in.readByte() != 0) {
            z = true;
        } else {
            z = false;
        }
        this.mIsImage = z;
        this.mIsGif = in.readByte() == 0 ? false : z2;
    }

    public VKApiDocument() {
    }

    public boolean isImage() {
        this.mIsImage = this.mIsImage || "jpg".equals(this.ext) || ContentType.SUBTYPE_JPEG.equals(this.ext) || ContentType.SUBTYPE_PNG.equals(this.ext) || "bmp".equals(this.ext);
        return this.mIsImage;
    }

    public boolean isGif() {
        this.mIsGif = this.mIsGif || ContentType.SUBTYPE_GIF.equals(this.ext);
        return this.mIsGif;
    }

    public int getId() {
        return this.id;
    }

    public String toString() {
        return this.title;
    }

    public CharSequence toAttachmentString() {
        StringBuilder result = new StringBuilder(VKAttachments.TYPE_DOC).append(this.owner_id).append('_').append(this.id);
        if (!TextUtils.isEmpty(this.access_key)) {
            result.append('_');
            result.append(this.access_key);
        }
        return result;
    }

    public String getType() {
        return VKAttachments.TYPE_DOC;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        byte b;
        byte b2 = 1;
        dest.writeInt(this.id);
        dest.writeInt(this.owner_id);
        dest.writeString(this.title);
        dest.writeLong(this.size);
        dest.writeString(this.ext);
        dest.writeString(this.url);
        dest.writeLong(this.date);
        dest.writeString(this.photo_100);
        dest.writeString(this.photo_130);
        dest.writeParcelable(this.photo, flags);
        dest.writeString(this.access_key);
        if (this.mIsImage) {
            b = 1;
        } else {
            b = 0;
        }
        dest.writeByte(b);
        if (!this.mIsGif) {
            b2 = 0;
        }
        dest.writeByte(b2);
    }
}
