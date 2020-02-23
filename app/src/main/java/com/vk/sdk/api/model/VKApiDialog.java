package com.vk.sdk.api.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.vk.sdk.api.VKApiConst;
import org.json.JSONException;
import org.json.JSONObject;

public class VKApiDialog extends VKApiModel implements Identifiable, Parcelable {
    public static Parcelable.Creator<VKApiDialog> CREATOR = new Parcelable.Creator<VKApiDialog>() {
        public VKApiDialog createFromParcel(Parcel source) {
            return new VKApiDialog(source);
        }

        public VKApiDialog[] newArray(int size) {
            return new VKApiDialog[size];
        }
    };
    public VKApiMessage message;
    public int unread;

    public VKApiDialog(JSONObject from) throws JSONException {
        parse(from);
    }

    public VKApiDialog parse(JSONObject from) throws JSONException {
        this.unread = from.optInt(VKApiConst.UNREAD);
        this.message = new VKApiMessage(from.optJSONObject("message"));
        return this;
    }

    public VKApiDialog(Parcel in) {
        this.unread = in.readInt();
        this.message = (VKApiMessage) in.readParcelable(VKApiMessage.class.getClassLoader());
    }

    public VKApiDialog() {
    }

    public int getId() {
        return this.message.getId();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.unread);
        dest.writeParcelable(this.message, flags);
    }
}
