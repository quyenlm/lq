package com.vk.sdk.api.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import org.json.JSONObject;

public class VKApiCommunity extends VKApiOwner implements Parcelable, Identifiable {
    public static Parcelable.Creator<VKApiCommunity> CREATOR = new Parcelable.Creator<VKApiCommunity>() {
        public VKApiCommunity createFromParcel(Parcel source) {
            return new VKApiCommunity(source);
        }

        public VKApiCommunity[] newArray(int size) {
            return new VKApiCommunity[size];
        }
    };
    static final String PHOTO_100 = "http://vk.com/images/community_100.gif";
    static final String PHOTO_50 = "http://vk.com/images/community_50.gif";
    private static final String TYPE_EVENT = "event";
    private static final String TYPE_GROUP = "group";
    private static final String TYPE_PAGE = "page";
    public int admin_level;
    public boolean is_admin;
    public int is_closed;
    public boolean is_member;
    public String name;
    public VKPhotoSizes photo = new VKPhotoSizes();
    public String photo_100;
    public String photo_200;
    public String photo_50;
    public String screen_name;
    public int type;

    public VKApiCommunity(JSONObject from) {
        parse(from);
    }

    public VKApiCommunity parse(JSONObject from) {
        super.parse(from);
        this.name = from.optString("name");
        this.screen_name = from.optString(VKApiUserFull.SCREEN_NAME, String.format("club%d", new Object[]{Integer.valueOf(Math.abs(this.id))}));
        this.is_closed = from.optInt("is_closed");
        this.is_admin = ParseUtils.parseBoolean(from, "is_admin");
        this.admin_level = from.optInt("admin_level");
        this.is_member = ParseUtils.parseBoolean(from, "is_member");
        this.photo_50 = from.optString(VKApiUser.FIELD_PHOTO_50, PHOTO_50);
        if (!TextUtils.isEmpty(this.photo_50)) {
            this.photo.add(VKApiPhotoSize.create(this.photo_50, 50));
        }
        this.photo_100 = from.optString(VKApiUser.FIELD_PHOTO_100, PHOTO_100);
        if (!TextUtils.isEmpty(this.photo_100)) {
            this.photo.add(VKApiPhotoSize.create(this.photo_100, 100));
        }
        this.photo_200 = from.optString(VKApiUser.FIELD_PHOTO_200, (String) null);
        if (!TextUtils.isEmpty(this.photo_200)) {
            this.photo.add(VKApiPhotoSize.create(this.photo_200, 200));
        }
        this.photo.sort();
        String type2 = from.optString("type", TYPE_GROUP);
        if (TYPE_GROUP.equals(type2)) {
            this.type = 0;
        } else if ("page".equals(type2)) {
            this.type = 1;
        } else if ("event".equals(type2)) {
            this.type = 2;
        }
        return this;
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public VKApiCommunity(Parcel in) {
        super(in);
        boolean z = true;
        this.name = in.readString();
        this.screen_name = in.readString();
        this.is_closed = in.readInt();
        this.is_admin = in.readByte() != 0;
        this.admin_level = in.readInt();
        this.is_member = in.readByte() == 0 ? false : z;
        this.type = in.readInt();
        this.photo_50 = in.readString();
        this.photo_100 = in.readString();
        this.photo_200 = in.readString();
        this.photo = (VKPhotoSizes) in.readParcelable(VKPhotoSizes.class.getClassLoader());
    }

    public VKApiCommunity() {
    }

    public String toString() {
        return this.name;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        byte b = 1;
        super.writeToParcel(dest, flags);
        dest.writeString(this.name);
        dest.writeString(this.screen_name);
        dest.writeInt(this.is_closed);
        dest.writeByte(this.is_admin ? (byte) 1 : 0);
        dest.writeInt(this.admin_level);
        if (!this.is_member) {
            b = 0;
        }
        dest.writeByte(b);
        dest.writeInt(this.type);
        dest.writeString(this.photo_50);
        dest.writeString(this.photo_100);
        dest.writeString(this.photo_200);
        dest.writeParcelable(this.photo, flags);
    }

    public static class AdminLevel {
        public static final int ADMIN = 3;
        public static final int EDITOR = 2;
        public static final int MODERATOR = 1;

        private AdminLevel() {
        }
    }

    public static class Status {
        public static final int CLOSED = 1;
        public static final int OPEN = 0;
        public static final int PRIVATE = 2;

        private Status() {
        }
    }

    public static class Type {
        public static final int EVENT = 2;
        public static final int GROUP = 0;
        public static final int PAGE = 1;

        private Type() {
        }
    }
}
