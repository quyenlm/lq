package com.vk.sdk.api.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.facebook.share.internal.ShareConstants;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.model.VKAttachments;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class VKApiPhotoAlbum extends VKAttachments.VKApiAttachment implements Parcelable, Identifiable {
    public static final String COVER_M = "http://vk.com/images/m_noalbum.png";
    public static final String COVER_S = "http://vk.com/images/s_noalbum.png";
    public static final String COVER_X = "http://vk.com/images/x_noalbum.png";
    public static Parcelable.Creator<VKApiPhotoAlbum> CREATOR = new Parcelable.Creator<VKApiPhotoAlbum>() {
        public VKApiPhotoAlbum createFromParcel(Parcel source) {
            return new VKApiPhotoAlbum(source);
        }

        public VKApiPhotoAlbum[] newArray(int size) {
            return new VKApiPhotoAlbum[size];
        }
    };
    public boolean can_upload;
    public long created;
    public String description;
    public int id;
    public int owner_id;
    public VKPhotoSizes photo = new VKPhotoSizes();
    public int privacy;
    public int size;
    public int thumb_id;
    public String thumb_src;
    public String title;
    public long updated;

    public VKApiPhotoAlbum(JSONObject from) throws JSONException {
        parse(from);
    }

    public VKApiPhotoAlbum parse(JSONObject from) {
        this.id = from.optInt("id");
        this.thumb_id = from.optInt("thumb_id");
        this.owner_id = from.optInt(VKApiConst.OWNER_ID);
        this.title = from.optString("title");
        this.description = from.optString("description");
        this.created = from.optLong(VKAccessToken.CREATED);
        this.updated = from.optLong("updated");
        this.size = from.optInt("size");
        this.can_upload = ParseUtils.parseBoolean(from, "can_upload");
        this.thumb_src = from.optString("thumb_src");
        if (from.has(ShareConstants.WEB_DIALOG_PARAM_PRIVACY)) {
            this.privacy = from.optInt(ShareConstants.WEB_DIALOG_PARAM_PRIVACY);
        } else {
            this.privacy = VKPrivacy.parsePrivacy(from.optJSONObject("privacy_view"));
        }
        JSONArray sizes = from.optJSONArray("sizes");
        if (sizes != null) {
            this.photo.fill(sizes);
        } else {
            this.photo.add(VKApiPhotoSize.create(COVER_S, 75, 55));
            this.photo.add(VKApiPhotoSize.create(COVER_M, 130, 97));
            this.photo.add(VKApiPhotoSize.create(COVER_X, 432, 249));
            this.photo.sort();
        }
        return this;
    }

    public VKApiPhotoAlbum(Parcel in) {
        this.id = in.readInt();
        this.title = in.readString();
        this.size = in.readInt();
        this.privacy = in.readInt();
        this.description = in.readString();
        this.owner_id = in.readInt();
        this.can_upload = in.readByte() != 0;
        this.updated = in.readLong();
        this.created = in.readLong();
        this.thumb_id = in.readInt();
        this.thumb_src = in.readString();
        this.photo = (VKPhotoSizes) in.readParcelable(VKPhotoSizes.class.getClassLoader());
    }

    public VKApiPhotoAlbum() {
    }

    public boolean isClosed() {
        return this.privacy != 0;
    }

    public int getId() {
        return this.id;
    }

    public String toString() {
        return this.title;
    }

    public CharSequence toAttachmentString() {
        return new StringBuilder(VKAttachments.TYPE_ALBUM).append(this.owner_id).append('_').append(this.id);
    }

    public String getType() {
        return VKAttachments.TYPE_ALBUM;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.title);
        dest.writeInt(this.size);
        dest.writeInt(this.privacy);
        dest.writeString(this.description);
        dest.writeInt(this.owner_id);
        dest.writeByte(this.can_upload ? (byte) 1 : 0);
        dest.writeLong(this.updated);
        dest.writeLong(this.created);
        dest.writeInt(this.thumb_id);
        dest.writeString(this.thumb_src);
        dest.writeParcelable(this.photo, flags);
    }
}
