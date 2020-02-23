package com.vk.sdk.api.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.api.model.VKApiUserFull;
import com.vk.sdk.api.model.VKAttachments;
import org.json.JSONException;
import org.json.JSONObject;

public class VKApiWikiPage extends VKAttachments.VKApiAttachment implements Parcelable {
    public static Parcelable.Creator<VKApiWikiPage> CREATOR = new Parcelable.Creator<VKApiWikiPage>() {
        public VKApiWikiPage createFromParcel(Parcel source) {
            return new VKApiWikiPage(source);
        }

        public VKApiWikiPage[] newArray(int size) {
            return new VKApiWikiPage[size];
        }
    };
    public long created;
    public int creator_id;
    public boolean current_user_can_edit;
    public boolean current_user_can_edit_access;
    public long edited;
    public int editor_id;
    public int group_id;
    public int id;
    public String parent;
    public String parent2;
    public String source;
    public String title;
    public int who_can_edit;
    public int who_can_view;

    public VKApiWikiPage(JSONObject from) throws JSONException {
        parse(from);
    }

    public VKApiWikiPage parse(JSONObject source2) {
        this.id = source2.optInt("id");
        this.group_id = source2.optInt("group_id");
        this.creator_id = source2.optInt("creator_id");
        this.title = source2.optString("title");
        this.source = source2.optString("source");
        this.current_user_can_edit = ParseUtils.parseBoolean(source2, "current_user_can_edit");
        this.current_user_can_edit_access = ParseUtils.parseBoolean(source2, "current_user_can_edit_access");
        this.who_can_view = source2.optInt("who_can_view");
        this.who_can_edit = source2.optInt("who_can_edit");
        this.editor_id = source2.optInt("editor_id");
        this.edited = source2.optLong("edited");
        this.created = source2.optLong(VKAccessToken.CREATED);
        this.parent = source2.optString(VKApiUserFull.RelativeType.PARENT);
        this.parent2 = source2.optString("parent2");
        return this;
    }

    public VKApiWikiPage(Parcel in) {
        boolean z = true;
        this.id = in.readInt();
        this.group_id = in.readInt();
        this.creator_id = in.readInt();
        this.title = in.readString();
        this.source = in.readString();
        this.current_user_can_edit = in.readByte() != 0;
        this.current_user_can_edit_access = in.readByte() == 0 ? false : z;
        this.who_can_view = in.readInt();
        this.who_can_edit = in.readInt();
        this.editor_id = in.readInt();
        this.edited = in.readLong();
        this.created = in.readLong();
        this.parent = in.readString();
        this.parent2 = in.readString();
    }

    public VKApiWikiPage() {
    }

    public CharSequence toAttachmentString() {
        return new StringBuilder("page").append(this.group_id).append('_').append(this.id);
    }

    public String getType() {
        return "page";
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        byte b = 1;
        dest.writeInt(this.id);
        dest.writeInt(this.group_id);
        dest.writeInt(this.creator_id);
        dest.writeString(this.title);
        dest.writeString(this.source);
        dest.writeByte(this.current_user_can_edit ? (byte) 1 : 0);
        if (!this.current_user_can_edit_access) {
            b = 0;
        }
        dest.writeByte(b);
        dest.writeInt(this.who_can_view);
        dest.writeInt(this.who_can_edit);
        dest.writeInt(this.editor_id);
        dest.writeLong(this.edited);
        dest.writeLong(this.created);
        dest.writeString(this.parent);
        dest.writeString(this.parent2);
    }

    public int getId() {
        return this.id;
    }
}
