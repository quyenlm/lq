package com.vk.sdk.api.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.vk.sdk.api.VKApiConst;
import org.json.JSONObject;

public class VKApiGetMessagesResponse extends VKApiModel implements Parcelable {
    public static Parcelable.Creator<VKApiGetMessagesResponse> CREATOR = new Parcelable.Creator<VKApiGetMessagesResponse>() {
        public VKApiGetMessagesResponse createFromParcel(Parcel source) {
            return new VKApiGetMessagesResponse(source);
        }

        public VKApiGetMessagesResponse[] newArray(int size) {
            return new VKApiGetMessagesResponse[size];
        }
    };
    public int count;
    public VKList<VKApiMessage> items;

    public VKApiGetMessagesResponse(JSONObject from) {
        parse(from);
    }

    public VKApiGetMessagesResponse parse(JSONObject source) {
        JSONObject response = source.optJSONObject("response");
        this.count = response.optInt(VKApiConst.COUNT);
        this.items = new VKList<>(response.optJSONArray("items"), VKApiMessage.class);
        return this;
    }

    public VKApiGetMessagesResponse(Parcel in) {
        this.count = in.readInt();
        this.items = (VKList) in.readParcelable(VKList.class.getClassLoader());
    }

    public VKApiGetMessagesResponse() {
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.count);
        dest.writeParcelable(this.items, flags);
    }
}
