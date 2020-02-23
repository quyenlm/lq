package com.vk.sdk.api.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.vk.sdk.api.model.VKAttachments;
import org.json.JSONException;
import org.json.JSONObject;

public class VKApiLink extends VKAttachments.VKApiAttachment implements Parcelable {
    public static Parcelable.Creator<VKApiLink> CREATOR = new Parcelable.Creator<VKApiLink>() {
        public VKApiLink createFromParcel(Parcel source) {
            return new VKApiLink(source);
        }

        public VKApiLink[] newArray(int size) {
            return new VKApiLink[size];
        }
    };
    public String description;
    public String image_src;
    public String preview_page;
    public String title;
    public String url;

    public VKApiLink(String url2) {
        this.url = url2;
    }

    public VKApiLink(JSONObject from) throws JSONException {
        parse(from);
    }

    public VKApiLink parse(JSONObject source) {
        this.url = source.optString("url");
        this.title = source.optString("title");
        this.description = source.optString("description");
        this.image_src = source.optString("image_src");
        this.preview_page = source.optString("preview_page");
        return this;
    }

    private VKApiLink(Parcel in) {
        this.url = in.readString();
        this.title = in.readString();
        this.description = in.readString();
        this.image_src = in.readString();
        this.preview_page = in.readString();
    }

    public VKApiLink() {
    }

    public CharSequence toAttachmentString() {
        return this.url;
    }

    public String getType() {
        return "link";
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.url);
        dest.writeString(this.title);
        dest.writeString(this.description);
        dest.writeString(this.image_src);
        dest.writeString(this.preview_page);
    }

    public int getId() {
        return 0;
    }
}
