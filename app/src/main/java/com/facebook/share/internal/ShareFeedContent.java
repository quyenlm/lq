package com.facebook.share.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.facebook.share.model.ShareContent;

public class ShareFeedContent extends ShareContent<ShareFeedContent, Builder> {
    public static final Parcelable.Creator<ShareFeedContent> CREATOR = new Parcelable.Creator<ShareFeedContent>() {
        public ShareFeedContent createFromParcel(Parcel in) {
            return new ShareFeedContent(in);
        }

        public ShareFeedContent[] newArray(int size) {
            return new ShareFeedContent[size];
        }
    };
    private final String link;
    private final String linkCaption;
    private final String linkDescription;
    private final String linkName;
    private final String mediaSource;
    private final String picture;
    private final String toId;

    private ShareFeedContent(Builder builder) {
        super((ShareContent.Builder) builder);
        this.toId = builder.toId;
        this.link = builder.link;
        this.linkName = builder.linkName;
        this.linkCaption = builder.linkCaption;
        this.linkDescription = builder.linkDescription;
        this.picture = builder.picture;
        this.mediaSource = builder.mediaSource;
    }

    ShareFeedContent(Parcel in) {
        super(in);
        this.toId = in.readString();
        this.link = in.readString();
        this.linkName = in.readString();
        this.linkCaption = in.readString();
        this.linkDescription = in.readString();
        this.picture = in.readString();
        this.mediaSource = in.readString();
    }

    public String getToId() {
        return this.toId;
    }

    public String getLink() {
        return this.link;
    }

    public String getLinkName() {
        return this.linkName;
    }

    public String getLinkCaption() {
        return this.linkCaption;
    }

    public String getLinkDescription() {
        return this.linkDescription;
    }

    public String getPicture() {
        return this.picture;
    }

    public String getMediaSource() {
        return this.mediaSource;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        super.writeToParcel(out, flags);
        out.writeString(this.toId);
        out.writeString(this.link);
        out.writeString(this.linkName);
        out.writeString(this.linkCaption);
        out.writeString(this.linkDescription);
        out.writeString(this.picture);
        out.writeString(this.mediaSource);
    }

    public static final class Builder extends ShareContent.Builder<ShareFeedContent, Builder> {
        /* access modifiers changed from: private */
        public String link;
        /* access modifiers changed from: private */
        public String linkCaption;
        /* access modifiers changed from: private */
        public String linkDescription;
        /* access modifiers changed from: private */
        public String linkName;
        /* access modifiers changed from: private */
        public String mediaSource;
        /* access modifiers changed from: private */
        public String picture;
        /* access modifiers changed from: private */
        public String toId;

        public Builder setToId(String toId2) {
            this.toId = toId2;
            return this;
        }

        public Builder setLink(String link2) {
            this.link = link2;
            return this;
        }

        public Builder setLinkName(String linkName2) {
            this.linkName = linkName2;
            return this;
        }

        public Builder setLinkCaption(String linkCaption2) {
            this.linkCaption = linkCaption2;
            return this;
        }

        public Builder setLinkDescription(String linkDescription2) {
            this.linkDescription = linkDescription2;
            return this;
        }

        public Builder setPicture(String picture2) {
            this.picture = picture2;
            return this;
        }

        public Builder setMediaSource(String mediaSource2) {
            this.mediaSource = mediaSource2;
            return this;
        }

        public ShareFeedContent build() {
            return new ShareFeedContent(this);
        }

        /* Debug info: failed to restart local var, previous not found, register: 2 */
        public Builder readFrom(ShareFeedContent model) {
            if (model == null) {
                return this;
            }
            return ((Builder) super.readFrom(model)).setToId(model.getToId()).setLink(model.getLink()).setLinkName(model.getLinkName()).setLinkCaption(model.getLinkCaption()).setLinkDescription(model.getLinkDescription()).setPicture(model.getPicture()).setMediaSource(model.getMediaSource());
        }
    }
}
