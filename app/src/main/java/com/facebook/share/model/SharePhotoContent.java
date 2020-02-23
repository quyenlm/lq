package com.facebook.share.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import com.facebook.share.model.ShareContent;
import com.facebook.share.model.SharePhoto;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class SharePhotoContent extends ShareContent<SharePhotoContent, Builder> {
    public static final Parcelable.Creator<SharePhotoContent> CREATOR = new Parcelable.Creator<SharePhotoContent>() {
        public SharePhotoContent createFromParcel(Parcel in) {
            return new SharePhotoContent(in);
        }

        public SharePhotoContent[] newArray(int size) {
            return new SharePhotoContent[size];
        }
    };
    private final List<SharePhoto> photos;

    private SharePhotoContent(Builder builder) {
        super((ShareContent.Builder) builder);
        this.photos = Collections.unmodifiableList(builder.photos);
    }

    SharePhotoContent(Parcel in) {
        super(in);
        this.photos = Collections.unmodifiableList(SharePhoto.Builder.readPhotoListFrom(in));
    }

    @Nullable
    public List<SharePhoto> getPhotos() {
        return this.photos;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        super.writeToParcel(out, flags);
        SharePhoto.Builder.writePhotoListTo(out, flags, this.photos);
    }

    public static class Builder extends ShareContent.Builder<SharePhotoContent, Builder> {
        /* access modifiers changed from: private */
        public final List<SharePhoto> photos = new ArrayList();

        public Builder addPhoto(@Nullable SharePhoto photo) {
            if (photo != null) {
                this.photos.add(new SharePhoto.Builder().readFrom(photo).build());
            }
            return this;
        }

        public Builder addPhotos(@Nullable List<SharePhoto> photos2) {
            if (photos2 != null) {
                for (SharePhoto photo : photos2) {
                    addPhoto(photo);
                }
            }
            return this;
        }

        public SharePhotoContent build() {
            return new SharePhotoContent(this);
        }

        /* Debug info: failed to restart local var, previous not found, register: 2 */
        public Builder readFrom(SharePhotoContent model) {
            if (model == null) {
                return this;
            }
            return ((Builder) super.readFrom(model)).addPhotos(model.getPhotos());
        }

        public Builder setPhotos(@Nullable List<SharePhoto> photos2) {
            this.photos.clear();
            addPhotos(photos2);
            return this;
        }
    }
}
