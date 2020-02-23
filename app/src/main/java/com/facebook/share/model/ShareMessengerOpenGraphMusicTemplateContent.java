package com.facebook.share.model;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import com.facebook.share.model.ShareContent;

public final class ShareMessengerOpenGraphMusicTemplateContent extends ShareContent<ShareMessengerOpenGraphMusicTemplateContent, Builder> {
    public static final Parcelable.Creator<ShareMessengerOpenGraphMusicTemplateContent> CREATOR = new Parcelable.Creator<ShareMessengerOpenGraphMusicTemplateContent>() {
        public ShareMessengerOpenGraphMusicTemplateContent createFromParcel(Parcel in) {
            return new ShareMessengerOpenGraphMusicTemplateContent(in);
        }

        public ShareMessengerOpenGraphMusicTemplateContent[] newArray(int size) {
            return new ShareMessengerOpenGraphMusicTemplateContent[size];
        }
    };
    private final ShareMessengerActionButton button;
    private final Uri url;

    private ShareMessengerOpenGraphMusicTemplateContent(Builder builder) {
        super((ShareContent.Builder) builder);
        this.url = builder.url;
        this.button = builder.button;
    }

    ShareMessengerOpenGraphMusicTemplateContent(Parcel in) {
        super(in);
        this.url = (Uri) in.readParcelable(Uri.class.getClassLoader());
        this.button = (ShareMessengerActionButton) in.readParcelable(ShareMessengerActionButton.class.getClassLoader());
    }

    public Uri getUrl() {
        return this.url;
    }

    public ShareMessengerActionButton getButton() {
        return this.button;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.url, flags);
        dest.writeParcelable(this.button, flags);
    }

    public static class Builder extends ShareContent.Builder<ShareMessengerOpenGraphMusicTemplateContent, Builder> {
        /* access modifiers changed from: private */
        public ShareMessengerActionButton button;
        /* access modifiers changed from: private */
        public Uri url;

        public Builder setUrl(Uri url2) {
            this.url = url2;
            return this;
        }

        public Builder setButton(ShareMessengerActionButton button2) {
            this.button = button2;
            return this;
        }

        /* Debug info: failed to restart local var, previous not found, register: 2 */
        public Builder readFrom(ShareMessengerOpenGraphMusicTemplateContent content) {
            if (content == null) {
                return this;
            }
            return ((Builder) super.readFrom(content)).setUrl(content.getUrl()).setButton(content.getButton());
        }

        public ShareMessengerOpenGraphMusicTemplateContent build() {
            return new ShareMessengerOpenGraphMusicTemplateContent(this);
        }
    }
}
