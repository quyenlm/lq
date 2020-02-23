package com.facebook.share.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.facebook.internal.NativeProtocol;
import com.facebook.share.model.ShareOpenGraphValueContainer;

public final class ShareOpenGraphObject extends ShareOpenGraphValueContainer<ShareOpenGraphObject, Builder> {
    public static final Parcelable.Creator<ShareOpenGraphObject> CREATOR = new Parcelable.Creator<ShareOpenGraphObject>() {
        public ShareOpenGraphObject createFromParcel(Parcel in) {
            return new ShareOpenGraphObject(in);
        }

        public ShareOpenGraphObject[] newArray(int size) {
            return new ShareOpenGraphObject[size];
        }
    };

    private ShareOpenGraphObject(Builder builder) {
        super(builder);
    }

    ShareOpenGraphObject(Parcel in) {
        super(in);
    }

    public static final class Builder extends ShareOpenGraphValueContainer.Builder<ShareOpenGraphObject, Builder> {
        public Builder() {
            putBoolean(NativeProtocol.OPEN_GRAPH_CREATE_OBJECT_KEY, true);
        }

        public ShareOpenGraphObject build() {
            return new ShareOpenGraphObject(this);
        }

        /* access modifiers changed from: package-private */
        public Builder readFrom(Parcel parcel) {
            return (Builder) readFrom((ShareOpenGraphObject) parcel.readParcelable(ShareOpenGraphObject.class.getClassLoader()));
        }
    }
}
