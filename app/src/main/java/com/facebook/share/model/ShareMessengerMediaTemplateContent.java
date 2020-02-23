package com.facebook.share.model;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import com.facebook.share.model.ShareContent;

public final class ShareMessengerMediaTemplateContent extends ShareContent<ShareMessengerMediaTemplateContent, Builder> {
    public static final Parcelable.Creator<ShareMessengerMediaTemplateContent> CREATOR = new Parcelable.Creator<ShareMessengerMediaTemplateContent>() {
        public ShareMessengerMediaTemplateContent createFromParcel(Parcel in) {
            return new ShareMessengerMediaTemplateContent(in);
        }

        public ShareMessengerMediaTemplateContent[] newArray(int size) {
            return new ShareMessengerMediaTemplateContent[size];
        }
    };
    private final String attachmentId;
    private final ShareMessengerActionButton button;
    private final MediaType mediaType;
    private final Uri mediaUrl;

    public enum MediaType {
        IMAGE,
        VIDEO
    }

    private ShareMessengerMediaTemplateContent(Builder builder) {
        super((ShareContent.Builder) builder);
        this.mediaType = builder.mediaType;
        this.attachmentId = builder.attachmentId;
        this.mediaUrl = builder.mediaUrl;
        this.button = builder.button;
    }

    ShareMessengerMediaTemplateContent(Parcel in) {
        super(in);
        this.mediaType = (MediaType) in.readSerializable();
        this.attachmentId = in.readString();
        this.mediaUrl = (Uri) in.readParcelable(Uri.class.getClassLoader());
        this.button = (ShareMessengerActionButton) in.readParcelable(ShareMessengerActionButton.class.getClassLoader());
    }

    public MediaType getMediaType() {
        return this.mediaType;
    }

    public String getAttachmentId() {
        return this.attachmentId;
    }

    public Uri getMediaUrl() {
        return this.mediaUrl;
    }

    public ShareMessengerActionButton getButton() {
        return this.button;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeSerializable(this.mediaType);
        dest.writeString(this.attachmentId);
        dest.writeParcelable(this.mediaUrl, flags);
        dest.writeParcelable(this.button, flags);
    }

    public static class Builder extends ShareContent.Builder<ShareMessengerMediaTemplateContent, Builder> {
        /* access modifiers changed from: private */
        public String attachmentId;
        /* access modifiers changed from: private */
        public ShareMessengerActionButton button;
        /* access modifiers changed from: private */
        public MediaType mediaType;
        /* access modifiers changed from: private */
        public Uri mediaUrl;

        public Builder setMediaType(MediaType mediaType2) {
            this.mediaType = mediaType2;
            return this;
        }

        public Builder setAttachmentId(String attachmentId2) {
            this.attachmentId = attachmentId2;
            return this;
        }

        public Builder setMediaUrl(Uri mediaUrl2) {
            this.mediaUrl = mediaUrl2;
            return this;
        }

        public Builder setButton(ShareMessengerActionButton button2) {
            this.button = button2;
            return this;
        }

        /* Debug info: failed to restart local var, previous not found, register: 2 */
        public Builder readFrom(ShareMessengerMediaTemplateContent content) {
            if (content == null) {
                return this;
            }
            return ((Builder) super.readFrom(content)).setMediaType(content.getMediaType()).setAttachmentId(content.getAttachmentId()).setMediaUrl(content.getMediaUrl()).setButton(content.getButton());
        }

        public ShareMessengerMediaTemplateContent build() {
            return new ShareMessengerMediaTemplateContent(this);
        }
    }
}
