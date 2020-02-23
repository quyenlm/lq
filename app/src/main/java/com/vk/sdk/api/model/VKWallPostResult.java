package com.vk.sdk.api.model;

import android.os.Parcel;
import android.os.Parcelable;

public class VKWallPostResult extends VKApiModel {
    public static Parcelable.Creator<VKWallPostResult> CREATOR = new Parcelable.Creator<VKWallPostResult>() {
        public VKWallPostResult createFromParcel(Parcel source) {
            VKWallPostResult vkWallPostResult = new VKWallPostResult();
            vkWallPostResult.post_id = source.readInt();
            return vkWallPostResult;
        }

        public VKWallPostResult[] newArray(int size) {
            return new VKWallPostResult[size];
        }
    };
    public int post_id;

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.post_id);
    }
}
