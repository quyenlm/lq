package com.facebook.share.model;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import com.facebook.share.model.ShareMedia;
import java.util.ArrayList;
import java.util.List;

public final class SharePhoto extends ShareMedia {
    public static final Parcelable.Creator<SharePhoto> CREATOR = new Parcelable.Creator<SharePhoto>() {
        public SharePhoto createFromParcel(Parcel source) {
            return new SharePhoto(source);
        }

        public SharePhoto[] newArray(int size) {
            return new SharePhoto[size];
        }
    };
    private final Bitmap bitmap;
    private final String caption;
    private final Uri imageUrl;
    private final boolean userGenerated;

    private SharePhoto(Builder builder) {
        super((ShareMedia.Builder) builder);
        this.bitmap = builder.bitmap;
        this.imageUrl = builder.imageUrl;
        this.userGenerated = builder.userGenerated;
        this.caption = builder.caption;
    }

    SharePhoto(Parcel in) {
        super(in);
        this.bitmap = (Bitmap) in.readParcelable(Bitmap.class.getClassLoader());
        this.imageUrl = (Uri) in.readParcelable(Uri.class.getClassLoader());
        this.userGenerated = in.readByte() != 0;
        this.caption = in.readString();
    }

    @Nullable
    public Bitmap getBitmap() {
        return this.bitmap;
    }

    @Nullable
    public Uri getImageUrl() {
        return this.imageUrl;
    }

    public boolean getUserGenerated() {
        return this.userGenerated;
    }

    public String getCaption() {
        return this.caption;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        int i = 0;
        super.writeToParcel(out, flags);
        out.writeParcelable(this.bitmap, 0);
        out.writeParcelable(this.imageUrl, 0);
        if (this.userGenerated) {
            i = 1;
        }
        out.writeByte((byte) i);
        out.writeString(this.caption);
    }

    public ShareMedia.Type getMediaType() {
        return ShareMedia.Type.PHOTO;
    }

    public static final class Builder extends ShareMedia.Builder<SharePhoto, Builder> {
        /* access modifiers changed from: private */
        public Bitmap bitmap;
        /* access modifiers changed from: private */
        public String caption;
        /* access modifiers changed from: private */
        public Uri imageUrl;
        /* access modifiers changed from: private */
        public boolean userGenerated;

        public Builder setBitmap(@Nullable Bitmap bitmap2) {
            this.bitmap = bitmap2;
            return this;
        }

        public Builder setImageUrl(@Nullable Uri imageUrl2) {
            this.imageUrl = imageUrl2;
            return this;
        }

        public Builder setUserGenerated(boolean userGenerated2) {
            this.userGenerated = userGenerated2;
            return this;
        }

        public Builder setCaption(@Nullable String caption2) {
            this.caption = caption2;
            return this;
        }

        /* access modifiers changed from: package-private */
        public Uri getImageUrl() {
            return this.imageUrl;
        }

        /* access modifiers changed from: package-private */
        public Bitmap getBitmap() {
            return this.bitmap;
        }

        public SharePhoto build() {
            return new SharePhoto(this);
        }

        /* Debug info: failed to restart local var, previous not found, register: 2 */
        public Builder readFrom(SharePhoto model) {
            if (model == null) {
                return this;
            }
            return ((Builder) super.readFrom(model)).setBitmap(model.getBitmap()).setImageUrl(model.getImageUrl()).setUserGenerated(model.getUserGenerated()).setCaption(model.getCaption());
        }

        /* access modifiers changed from: package-private */
        public Builder readFrom(Parcel parcel) {
            return readFrom((SharePhoto) parcel.readParcelable(SharePhoto.class.getClassLoader()));
        }

        static void writePhotoListTo(Parcel out, int parcelFlags, List<SharePhoto> photos) {
            ShareMedia[] array = new ShareMedia[photos.size()];
            for (int i = 0; i < photos.size(); i++) {
                array[i] = photos.get(i);
            }
            out.writeParcelableArray(array, parcelFlags);
        }

        static List<SharePhoto> readPhotoListFrom(Parcel in) {
            List<ShareMedia> media = readListFrom(in);
            List<SharePhoto> photos = new ArrayList<>();
            for (ShareMedia medium : media) {
                if (medium instanceof SharePhoto) {
                    photos.add((SharePhoto) medium);
                }
            }
            return photos;
        }
    }
}
