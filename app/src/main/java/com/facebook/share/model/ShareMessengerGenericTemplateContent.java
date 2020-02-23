package com.facebook.share.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.facebook.share.model.ShareContent;

public final class ShareMessengerGenericTemplateContent extends ShareContent<ShareMessengerGenericTemplateContent, Builder> {
    public static final Parcelable.Creator<ShareMessengerGenericTemplateContent> CREATOR = new Parcelable.Creator<ShareMessengerGenericTemplateContent>() {
        public ShareMessengerGenericTemplateContent createFromParcel(Parcel source) {
            return new ShareMessengerGenericTemplateContent(source);
        }

        public ShareMessengerGenericTemplateContent[] newArray(int size) {
            return new ShareMessengerGenericTemplateContent[size];
        }
    };
    private final ShareMessengerGenericTemplateElement genericTemplateElement;
    private final ImageAspectRatio imageAspectRatio;
    private final boolean isSharable;

    public enum ImageAspectRatio {
        HORIZONTAL,
        SQUARE
    }

    protected ShareMessengerGenericTemplateContent(Builder builder) {
        super((ShareContent.Builder) builder);
        this.isSharable = builder.isSharable;
        this.imageAspectRatio = builder.imageAspectRatio;
        this.genericTemplateElement = builder.genericTemplateElement;
    }

    ShareMessengerGenericTemplateContent(Parcel in) {
        super(in);
        this.isSharable = in.readByte() != 0;
        this.imageAspectRatio = (ImageAspectRatio) in.readSerializable();
        this.genericTemplateElement = (ShareMessengerGenericTemplateElement) in.readParcelable(ShareMessengerGenericTemplateElement.class.getClassLoader());
    }

    public boolean getIsSharable() {
        return this.isSharable;
    }

    public ImageAspectRatio getImageAspectRatio() {
        return this.imageAspectRatio;
    }

    public ShareMessengerGenericTemplateElement getGenericTemplateElement() {
        return this.genericTemplateElement;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        super.writeToParcel(out, flags);
        out.writeByte((byte) (this.isSharable ? 1 : 0));
        out.writeSerializable(this.imageAspectRatio);
        out.writeParcelable(this.genericTemplateElement, flags);
    }

    public static class Builder extends ShareContent.Builder<ShareMessengerGenericTemplateContent, Builder> {
        /* access modifiers changed from: private */
        public ShareMessengerGenericTemplateElement genericTemplateElement;
        /* access modifiers changed from: private */
        public ImageAspectRatio imageAspectRatio;
        /* access modifiers changed from: private */
        public boolean isSharable;

        public Builder setIsSharable(boolean isSharable2) {
            this.isSharable = isSharable2;
            return this;
        }

        public Builder setImageAspectRatio(ImageAspectRatio imageAspectRatio2) {
            this.imageAspectRatio = imageAspectRatio2;
            return this;
        }

        public Builder setGenericTemplateElement(ShareMessengerGenericTemplateElement genericTemplateElement2) {
            this.genericTemplateElement = genericTemplateElement2;
            return this;
        }

        public ShareMessengerGenericTemplateContent build() {
            return new ShareMessengerGenericTemplateContent(this);
        }

        /* Debug info: failed to restart local var, previous not found, register: 2 */
        public Builder readFrom(ShareMessengerGenericTemplateContent model) {
            if (model == null) {
                return this;
            }
            return ((Builder) super.readFrom(model)).setIsSharable(model.getIsSharable()).setImageAspectRatio(model.getImageAspectRatio()).setGenericTemplateElement(model.getGenericTemplateElement());
        }
    }
}
