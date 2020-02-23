package com.vk.sdk.api.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import org.json.JSONException;
import org.json.JSONObject;

public class VKApiUser extends VKApiOwner implements Parcelable {
    public static Parcelable.Creator<VKApiUser> CREATOR = new Parcelable.Creator<VKApiUser>() {
        public VKApiUser createFromParcel(Parcel source) {
            return new VKApiUser(source);
        }

        public VKApiUser[] newArray(int size) {
            return new VKApiUser[size];
        }
    };
    public static final String FIELDS_DEFAULT = TextUtils.join(",", new String[]{"online", FIELD_ONLINE_MOBILE, FIELD_PHOTO_50, FIELD_PHOTO_100, FIELD_PHOTO_200});
    public static final String FIELD_ONLINE = "online";
    public static final String FIELD_ONLINE_MOBILE = "online_mobile";
    public static final String FIELD_PHOTO_100 = "photo_100";
    public static final String FIELD_PHOTO_200 = "photo_200";
    public static final String FIELD_PHOTO_400_ORIGIN = "photo_400_orig";
    public static final String FIELD_PHOTO_50 = "photo_50";
    public static final String FIELD_PHOTO_BIG = "photo_big";
    public static final String FIELD_PHOTO_MAX = "photo_max";
    public static final String FIELD_PHOTO_MAX_ORIGIN = "photo_max_orig";
    public String first_name = "DELETED";
    private String full_name;
    public String last_name = "DELETED";
    public boolean online;
    public boolean online_mobile;
    public VKPhotoSizes photo = new VKPhotoSizes();
    public String photo_100 = "http://vk.com/images/camera_b.gif";
    public String photo_200 = "http://vk.com/images/camera_a.gif";
    public String photo_200_orig = "http://vk.com/images/camera_a.gif";
    public String photo_400_orig = "";
    public String photo_50 = "http://vk.com/images/camera_c.gif";
    public String photo_big = "";
    public String photo_max = "http://vk.com/images/camera_b.gif";
    public String photo_max_orig = "http://vk.com/images/camera_a.gif";

    public VKApiUser(JSONObject from) throws JSONException {
        parse(from);
    }

    public VKApiUser parse(JSONObject from) {
        super.parse(from);
        this.first_name = from.optString("first_name", this.first_name);
        this.last_name = from.optString("last_name", this.last_name);
        this.online = ParseUtils.parseBoolean(from, "online");
        this.online_mobile = ParseUtils.parseBoolean(from, FIELD_ONLINE_MOBILE);
        this.photo_50 = addSquarePhoto(from.optString(FIELD_PHOTO_50, this.photo_50), 50);
        this.photo_100 = addSquarePhoto(from.optString(FIELD_PHOTO_100, this.photo_100), 100);
        this.photo_200 = addSquarePhoto(from.optString(FIELD_PHOTO_200, this.photo_200), 200);
        this.photo_400_orig = from.optString(FIELD_PHOTO_400_ORIGIN, this.photo_400_orig);
        this.photo_max = from.optString(FIELD_PHOTO_MAX, this.photo_max);
        this.photo_max_orig = from.optString(FIELD_PHOTO_MAX_ORIGIN, this.photo_max_orig);
        this.photo_big = from.optString(FIELD_PHOTO_BIG, this.photo_big);
        this.photo.sort();
        return this;
    }

    /* access modifiers changed from: protected */
    public String addSquarePhoto(String photoUrl, int size) {
        if (!TextUtils.isEmpty(photoUrl)) {
            this.photo.add(VKApiPhotoSize.create(photoUrl, size));
        }
        return photoUrl;
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public VKApiUser(Parcel in) {
        super(in);
        boolean z = true;
        this.first_name = in.readString();
        this.last_name = in.readString();
        this.online = in.readByte() != 0;
        this.online_mobile = in.readByte() == 0 ? false : z;
        this.photo_50 = in.readString();
        this.photo_100 = in.readString();
        this.photo_200 = in.readString();
        this.photo = (VKPhotoSizes) in.readParcelable(VKPhotoSizes.class.getClassLoader());
        this.full_name = in.readString();
        this.photo_400_orig = in.readString();
        this.photo_max = in.readString();
        this.photo_max_orig = in.readString();
        this.photo_big = in.readString();
    }

    public VKApiUser() {
    }

    public String toString() {
        if (this.full_name == null) {
            this.full_name = this.first_name + ' ' + this.last_name;
        }
        return this.full_name;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        byte b = 1;
        super.writeToParcel(dest, flags);
        dest.writeString(this.first_name);
        dest.writeString(this.last_name);
        dest.writeByte(this.online ? (byte) 1 : 0);
        if (!this.online_mobile) {
            b = 0;
        }
        dest.writeByte(b);
        dest.writeString(this.photo_50);
        dest.writeString(this.photo_100);
        dest.writeString(this.photo_200);
        dest.writeParcelable(this.photo, flags);
        dest.writeString(this.full_name);
        dest.writeString(this.photo_400_orig);
        dest.writeString(this.photo_max);
        dest.writeString(this.photo_max_orig);
        dest.writeString(this.photo_big);
    }
}
