package com.facebook.share.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import com.facebook.share.model.ShareContent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class ShareStoryContent extends ShareContent<ShareStoryContent, Builder> {
    public static final Parcelable.Creator<ShareStoryContent> CREATOR = new Parcelable.Creator<ShareStoryContent>() {
        public ShareStoryContent createFromParcel(Parcel in) {
            return new ShareStoryContent(in);
        }

        public ShareStoryContent[] newArray(int size) {
            return new ShareStoryContent[size];
        }
    };
    private final String mAttributionLink;
    private final ShareMedia mBackgroundAsset;
    @Nullable
    private final List<String> mBackgroundColorList;
    private final SharePhoto mStickerAsset;

    private ShareStoryContent(Builder builder) {
        super((ShareContent.Builder) builder);
        this.mBackgroundAsset = builder.mBackgroundAsset;
        this.mStickerAsset = builder.mStickerAsset;
        this.mBackgroundColorList = builder.mBackgroundColorList;
        this.mAttributionLink = builder.mAttributionLink;
    }

    ShareStoryContent(Parcel in) {
        super(in);
        this.mBackgroundAsset = (ShareMedia) in.readParcelable(ShareMedia.class.getClassLoader());
        this.mStickerAsset = (SharePhoto) in.readParcelable(SharePhoto.class.getClassLoader());
        this.mBackgroundColorList = readUnmodifiableStringList(in);
        this.mAttributionLink = in.readString();
    }

    public ShareMedia getBackgroundAsset() {
        return this.mBackgroundAsset;
    }

    public SharePhoto getStickerAsset() {
        return this.mStickerAsset;
    }

    @Nullable
    public List<String> getBackgroundColorList() {
        if (this.mBackgroundColorList == null) {
            return null;
        }
        return Collections.unmodifiableList(this.mBackgroundColorList);
    }

    public String getAttributionLink() {
        return this.mAttributionLink;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        super.writeToParcel(out, flags);
        out.writeParcelable(this.mBackgroundAsset, 0);
        out.writeParcelable(this.mStickerAsset, 0);
        out.writeStringList(this.mBackgroundColorList);
        out.writeString(this.mAttributionLink);
    }

    @Nullable
    private List<String> readUnmodifiableStringList(Parcel in) {
        List<String> list = new ArrayList<>();
        in.readStringList(list);
        if (list.isEmpty()) {
            return null;
        }
        return Collections.unmodifiableList(list);
    }

    public static final class Builder extends ShareContent.Builder<ShareStoryContent, Builder> {
        static final String TAG = Builder.class.getSimpleName();
        /* access modifiers changed from: private */
        public String mAttributionLink;
        /* access modifiers changed from: private */
        public ShareMedia mBackgroundAsset;
        /* access modifiers changed from: private */
        public List<String> mBackgroundColorList;
        /* access modifiers changed from: private */
        public SharePhoto mStickerAsset;

        public Builder setBackgroundAsset(ShareMedia backgroundAsset) {
            this.mBackgroundAsset = backgroundAsset;
            return this;
        }

        public Builder setStickerAsset(SharePhoto stickerAsset) {
            this.mStickerAsset = stickerAsset;
            return this;
        }

        public Builder setBackgroundColorList(List<String> backgroundColorList) {
            this.mBackgroundColorList = backgroundColorList;
            return this;
        }

        public Builder setAttributionLink(String attributionLink) {
            this.mAttributionLink = attributionLink;
            return this;
        }

        public ShareStoryContent build() {
            return new ShareStoryContent(this);
        }

        /* Debug info: failed to restart local var, previous not found, register: 2 */
        public Builder readFrom(ShareStoryContent model) {
            if (model == null) {
                return this;
            }
            return ((Builder) super.readFrom(model)).setBackgroundAsset(model.getBackgroundAsset()).setStickerAsset(model.getStickerAsset()).setBackgroundColorList(model.getBackgroundColorList()).setAttributionLink(model.getAttributionLink());
        }
    }
}
