package com.facebook.share.model;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.util.Log;
import com.facebook.share.model.ShareContent;

public final class ShareLinkContent extends ShareContent<ShareLinkContent, Builder> {
    public static final Parcelable.Creator<ShareLinkContent> CREATOR = new Parcelable.Creator<ShareLinkContent>() {
        public ShareLinkContent createFromParcel(Parcel in) {
            return new ShareLinkContent(in);
        }

        public ShareLinkContent[] newArray(int size) {
            return new ShareLinkContent[size];
        }
    };
    @Deprecated
    private final String contentDescription;
    @Deprecated
    private final String contentTitle;
    @Deprecated
    private final Uri imageUrl;
    private final String quote;

    private ShareLinkContent(Builder builder) {
        super((ShareContent.Builder) builder);
        this.contentDescription = builder.contentDescription;
        this.contentTitle = builder.contentTitle;
        this.imageUrl = builder.imageUrl;
        this.quote = builder.quote;
    }

    ShareLinkContent(Parcel in) {
        super(in);
        this.contentDescription = in.readString();
        this.contentTitle = in.readString();
        this.imageUrl = (Uri) in.readParcelable(Uri.class.getClassLoader());
        this.quote = in.readString();
    }

    @Deprecated
    public String getContentDescription() {
        return this.contentDescription;
    }

    @Nullable
    @Deprecated
    public String getContentTitle() {
        return this.contentTitle;
    }

    @Nullable
    @Deprecated
    public Uri getImageUrl() {
        return this.imageUrl;
    }

    @Nullable
    public String getQuote() {
        return this.quote;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        super.writeToParcel(out, flags);
        out.writeString(this.contentDescription);
        out.writeString(this.contentTitle);
        out.writeParcelable(this.imageUrl, 0);
        out.writeString(this.quote);
    }

    public static final class Builder extends ShareContent.Builder<ShareLinkContent, Builder> {
        static final String TAG = Builder.class.getSimpleName();
        /* access modifiers changed from: private */
        @Deprecated
        public String contentDescription;
        /* access modifiers changed from: private */
        @Deprecated
        public String contentTitle;
        /* access modifiers changed from: private */
        @Deprecated
        public Uri imageUrl;
        /* access modifiers changed from: private */
        public String quote;

        @Deprecated
        public Builder setContentDescription(@Nullable String contentDescription2) {
            Log.w(TAG, "This method does nothing. ContentDescription is deprecated in Graph API 2.9.");
            return this;
        }

        @Deprecated
        public Builder setContentTitle(@Nullable String contentTitle2) {
            Log.w(TAG, "This method does nothing. ContentTitle is deprecated in Graph API 2.9.");
            return this;
        }

        @Deprecated
        public Builder setImageUrl(@Nullable Uri imageUrl2) {
            Log.w(TAG, "This method does nothing. ImageUrl is deprecated in Graph API 2.9.");
            return this;
        }

        public Builder setQuote(@Nullable String quote2) {
            this.quote = quote2;
            return this;
        }

        public ShareLinkContent build() {
            return new ShareLinkContent(this);
        }

        /* Debug info: failed to restart local var, previous not found, register: 2 */
        public Builder readFrom(ShareLinkContent model) {
            if (model == null) {
                return this;
            }
            return ((Builder) super.readFrom(model)).setContentDescription(model.getContentDescription()).setImageUrl(model.getImageUrl()).setContentTitle(model.getContentTitle()).setQuote(model.getQuote());
        }
    }
}
