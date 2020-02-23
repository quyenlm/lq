package com.facebook.share.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.facebook.share.model.ShareModel;
import com.facebook.share.model.ShareModelBuilder;

@Deprecated
public class LikeContent implements ShareModel {
    @Deprecated
    public static final Parcelable.Creator<LikeContent> CREATOR = new Parcelable.Creator<LikeContent>() {
        public LikeContent createFromParcel(Parcel in) {
            return new LikeContent(in);
        }

        public LikeContent[] newArray(int size) {
            return new LikeContent[size];
        }
    };
    private final String objectId;
    private final String objectType;

    private LikeContent(Builder builder) {
        this.objectId = builder.objectId;
        this.objectType = builder.objectType;
    }

    @Deprecated
    LikeContent(Parcel in) {
        this.objectId = in.readString();
        this.objectType = in.readString();
    }

    @Deprecated
    public String getObjectId() {
        return this.objectId;
    }

    @Deprecated
    public String getObjectType() {
        return this.objectType;
    }

    @Deprecated
    public int describeContents() {
        return 0;
    }

    @Deprecated
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(this.objectId);
        out.writeString(this.objectType);
    }

    @Deprecated
    public static class Builder implements ShareModelBuilder<LikeContent, Builder> {
        /* access modifiers changed from: private */
        public String objectId;
        /* access modifiers changed from: private */
        public String objectType;

        @Deprecated
        public Builder setObjectId(String objectId2) {
            this.objectId = objectId2;
            return this;
        }

        @Deprecated
        public Builder setObjectType(String objectType2) {
            this.objectType = objectType2;
            return this;
        }

        @Deprecated
        public LikeContent build() {
            return new LikeContent(this);
        }

        /* Debug info: failed to restart local var, previous not found, register: 2 */
        @Deprecated
        public Builder readFrom(LikeContent content) {
            if (content == null) {
                return this;
            }
            return setObjectId(content.getObjectId()).setObjectType(content.getObjectType());
        }
    }
}
