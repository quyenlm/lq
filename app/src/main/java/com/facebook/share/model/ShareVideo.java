package com.facebook.share.model;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import com.facebook.share.model.ShareMedia;

public final class ShareVideo extends ShareMedia {
    public static final Parcelable.Creator<ShareVideo> CREATOR = new Parcelable.Creator<ShareVideo>() {
        public ShareVideo createFromParcel(Parcel source) {
            return new ShareVideo(source);
        }

        public ShareVideo[] newArray(int size) {
            return new ShareVideo[size];
        }
    };
    private final Uri localUrl;

    private ShareVideo(Builder builder) {
        super((ShareMedia.Builder) builder);
        this.localUrl = builder.localUrl;
    }

    ShareVideo(Parcel in) {
        super(in);
        this.localUrl = (Uri) in.readParcelable(Uri.class.getClassLoader());
    }

    @Nullable
    public Uri getLocalUrl() {
        return this.localUrl;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        super.writeToParcel(out, flags);
        out.writeParcelable(this.localUrl, 0);
    }

    public ShareMedia.Type getMediaType() {
        return ShareMedia.Type.VIDEO;
    }

    public static final class Builder extends ShareMedia.Builder<ShareVideo, Builder> {
        /* access modifiers changed from: private */
        public Uri localUrl;

        public Builder setLocalUrl(@Nullable Uri localUrl2) {
            this.localUrl = localUrl2;
            return this;
        }

        public ShareVideo build() {
            return new ShareVideo(this);
        }

        /* Debug info: failed to restart local var, previous not found, register: 2 */
        public Builder readFrom(ShareVideo model) {
            if (model == null) {
                return this;
            }
            return ((Builder) super.readFrom(model)).setLocalUrl(model.getLocalUrl());
        }

        /* access modifiers changed from: package-private */
        public Builder readFrom(Parcel parcel) {
            return readFrom((ShareVideo) parcel.readParcelable(ShareVideo.class.getClassLoader()));
        }
    }
}
