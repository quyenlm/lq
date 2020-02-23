package com.vk.sdk.api.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.vk.sdk.api.VKApiConst;
import org.json.JSONObject;

public class VKApiGetDialogResponse extends VKApiModel implements Parcelable {
    public static Parcelable.Creator<VKApiGetDialogResponse> CREATOR = new Parcelable.Creator<VKApiGetDialogResponse>() {
        public VKApiGetDialogResponse createFromParcel(Parcel source) {
            return new VKApiGetDialogResponse(source);
        }

        public VKApiGetDialogResponse[] newArray(int size) {
            return new VKApiGetDialogResponse[size];
        }
    };
    public int count;
    public VKList<VKApiDialog> items;
    public int unread_dialogs;

    public VKApiGetDialogResponse(JSONObject from) {
        parse(from);
    }

    public VKApiGetDialogResponse parse(JSONObject source) {
        JSONObject response = source.optJSONObject("response");
        this.count = response.optInt(VKApiConst.COUNT);
        this.unread_dialogs = response.optInt("unread_dialogs");
        this.items = new VKList<>(response.optJSONArray("items"), VKApiDialog.class);
        return this;
    }

    public VKApiGetDialogResponse(Parcel in) {
        this.count = in.readInt();
        this.unread_dialogs = in.readInt();
        this.items = (VKList) in.readParcelable(VKList.class.getClassLoader());
    }

    public VKApiGetDialogResponse() {
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.count);
        dest.writeInt(this.unread_dialogs);
        dest.writeParcelable(this.items, flags);
    }
}
