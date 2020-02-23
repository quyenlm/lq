package com.facebook.share.model;

import android.os.Parcel;
import android.os.Parcelable;

public class ShareHashtag implements ShareModel {
    public static final Parcelable.Creator<ShareHashtag> CREATOR = new Parcelable.Creator<ShareHashtag>() {
        public ShareHashtag createFromParcel(Parcel in) {
            return new ShareHashtag(in);
        }

        public ShareHashtag[] newArray(int size) {
            return new ShareHashtag[size];
        }
    };
    private final String hashtag;

    private ShareHashtag(Builder builder) {
        this.hashtag = builder.hashtag;
    }

    ShareHashtag(Parcel in) {
        this.hashtag = in.readString();
    }

    public String getHashtag() {
        return this.hashtag;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.hashtag);
    }

    public static class Builder implements ShareModelBuilder<ShareHashtag, Builder> {
        /* access modifiers changed from: private */
        public String hashtag;

        public Builder setHashtag(String hashtag2) {
            this.hashtag = hashtag2;
            return this;
        }

        public String getHashtag() {
            return this.hashtag;
        }

        /* Debug info: failed to restart local var, previous not found, register: 1 */
        public Builder readFrom(ShareHashtag model) {
            return model == null ? this : setHashtag(model.getHashtag());
        }

        /* access modifiers changed from: package-private */
        public Builder readFrom(Parcel parcel) {
            return readFrom((ShareHashtag) parcel.readParcelable(ShareHashtag.class.getClassLoader()));
        }

        public ShareHashtag build() {
            return new ShareHashtag(this);
        }
    }
}
