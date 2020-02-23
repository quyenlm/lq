package com.vk.sdk.api.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import com.vk.sdk.api.model.VKAttachments;
import org.json.JSONObject;

public class VKApiApplicationContent extends VKAttachments.VKApiAttachment implements Parcelable {
    public static Parcelable.Creator<VKApiApplicationContent> CREATOR = new Parcelable.Creator<VKApiApplicationContent>() {
        public VKApiApplicationContent createFromParcel(Parcel source) {
            return new VKApiApplicationContent(source);
        }

        public VKApiApplicationContent[] newArray(int size) {
            return new VKApiApplicationContent[size];
        }
    };
    public int id;
    public String name;
    public VKPhotoSizes photo = new VKPhotoSizes();
    public String photo_130;
    public String photo_604;

    public VKApiApplicationContent(JSONObject source) {
        parse(source);
    }

    public VKApiApplicationContent parse(JSONObject source) {
        this.id = source.optInt("id");
        this.name = source.optString("name");
        this.photo_130 = source.optString("photo_130");
        if (!TextUtils.isEmpty(this.photo_130)) {
            this.photo.add(VKApiPhotoSize.create(this.photo_130, 130));
        }
        this.photo_604 = source.optString("photo_604");
        if (!TextUtils.isEmpty(this.photo_604)) {
            this.photo.add(VKApiPhotoSize.create(this.photo_604, 604));
        }
        return this;
    }

    public VKApiApplicationContent(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.photo_130 = in.readString();
        this.photo_604 = in.readString();
        this.photo = (VKPhotoSizes) in.readParcelable(VKPhotoSizes.class.getClassLoader());
    }

    public VKApiApplicationContent() {
    }

    public CharSequence toAttachmentString() {
        throw new UnsupportedOperationException("Attaching app info is not supported by VK.com API");
    }

    public String getType() {
        return VKAttachments.TYPE_APP;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeString(this.photo_130);
        dest.writeString(this.photo_604);
        dest.writeParcelable(this.photo, flags);
    }

    public int getId() {
        return this.id;
    }
}
