package com.vk.sdk.api.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.tencent.component.net.download.multiplex.http.ContentType;
import com.vk.sdk.api.model.VKAttachments;
import org.json.JSONException;
import org.json.JSONObject;

public class VKApiNote extends VKAttachments.VKApiAttachment implements Identifiable, Parcelable {
    public static Parcelable.Creator<VKApiNote> CREATOR = new Parcelable.Creator<VKApiNote>() {
        public VKApiNote createFromParcel(Parcel source) {
            return new VKApiNote(source);
        }

        public VKApiNote[] newArray(int size) {
            return new VKApiNote[size];
        }
    };
    public int comments;
    public long date;
    public int id;
    public int read_comments;
    public String text;
    public String title;
    public int user_id;
    public String view_url;

    public VKApiNote(JSONObject from) throws JSONException {
        parse(from);
    }

    public VKApiNote parse(JSONObject source) {
        this.id = source.optInt("id");
        this.user_id = source.optInt("user_id");
        this.title = source.optString("title");
        this.text = source.optString(ContentType.TYPE_TEXT);
        this.date = source.optLong("date");
        this.comments = source.optInt("comments");
        this.read_comments = source.optInt("read_comments");
        this.view_url = source.optString("view_url");
        return this;
    }

    public VKApiNote(Parcel in) {
        this.id = in.readInt();
        this.user_id = in.readInt();
        this.title = in.readString();
        this.text = in.readString();
        this.date = in.readLong();
        this.comments = in.readInt();
        this.read_comments = in.readInt();
        this.view_url = in.readString();
    }

    public VKApiNote() {
    }

    public int getId() {
        return this.id;
    }

    public CharSequence toAttachmentString() {
        return new StringBuilder(VKAttachments.TYPE_NOTE).append(this.user_id).append('_').append(this.id);
    }

    public String getType() {
        return VKAttachments.TYPE_NOTE;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeInt(this.user_id);
        dest.writeString(this.title);
        dest.writeString(this.text);
        dest.writeLong(this.date);
        dest.writeInt(this.comments);
        dest.writeInt(this.read_comments);
        dest.writeString(this.view_url);
    }
}
