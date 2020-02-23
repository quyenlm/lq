package com.facebook.share.model;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

public final class ShareMessengerGenericTemplateElement implements ShareModel {
    public static final Parcelable.Creator<ShareMessengerGenericTemplateElement> CREATOR = new Parcelable.Creator<ShareMessengerGenericTemplateElement>() {
        public ShareMessengerGenericTemplateElement createFromParcel(Parcel in) {
            return new ShareMessengerGenericTemplateElement(in);
        }

        public ShareMessengerGenericTemplateElement[] newArray(int size) {
            return new ShareMessengerGenericTemplateElement[size];
        }
    };
    /* access modifiers changed from: private */
    public final ShareMessengerActionButton button;
    /* access modifiers changed from: private */
    public final ShareMessengerActionButton defaultAction;
    /* access modifiers changed from: private */
    public final Uri imageUrl;
    /* access modifiers changed from: private */
    public final String subtitle;
    /* access modifiers changed from: private */
    public final String title;

    private ShareMessengerGenericTemplateElement(Builder builder) {
        this.title = builder.title;
        this.subtitle = builder.subtitle;
        this.imageUrl = builder.imageUrl;
        this.defaultAction = builder.defaultAction;
        this.button = builder.button;
    }

    ShareMessengerGenericTemplateElement(Parcel in) {
        this.title = in.readString();
        this.subtitle = in.readString();
        this.imageUrl = (Uri) in.readParcelable(Uri.class.getClassLoader());
        this.defaultAction = (ShareMessengerActionButton) in.readParcelable(ShareMessengerActionButton.class.getClassLoader());
        this.button = (ShareMessengerActionButton) in.readParcelable(ShareMessengerActionButton.class.getClassLoader());
    }

    public String getTitle() {
        return this.title;
    }

    public String getSubtitle() {
        return this.subtitle;
    }

    public Uri getImageUrl() {
        return this.imageUrl;
    }

    public ShareMessengerActionButton getDefaultAction() {
        return this.defaultAction;
    }

    public ShareMessengerActionButton getButton() {
        return this.button;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.subtitle);
        dest.writeParcelable(this.imageUrl, flags);
        dest.writeParcelable(this.defaultAction, flags);
        dest.writeParcelable(this.button, flags);
    }

    public static class Builder implements ShareModelBuilder<ShareMessengerGenericTemplateElement, Builder> {
        /* access modifiers changed from: private */
        public ShareMessengerActionButton button;
        /* access modifiers changed from: private */
        public ShareMessengerActionButton defaultAction;
        /* access modifiers changed from: private */
        public Uri imageUrl;
        /* access modifiers changed from: private */
        public String subtitle;
        /* access modifiers changed from: private */
        public String title;

        public Builder setTitle(String title2) {
            this.title = title2;
            return this;
        }

        public Builder setSubtitle(String subtitle2) {
            this.subtitle = subtitle2;
            return this;
        }

        public Builder setImageUrl(Uri imageUrl2) {
            this.imageUrl = imageUrl2;
            return this;
        }

        public Builder setDefaultAction(ShareMessengerActionButton defaultAction2) {
            this.defaultAction = defaultAction2;
            return this;
        }

        public Builder setButton(ShareMessengerActionButton button2) {
            this.button = button2;
            return this;
        }

        public ShareMessengerGenericTemplateElement build() {
            return new ShareMessengerGenericTemplateElement(this);
        }

        /* Debug info: failed to restart local var, previous not found, register: 2 */
        public Builder readFrom(ShareMessengerGenericTemplateElement model) {
            if (model == null) {
                return this;
            }
            return setTitle(model.title).setSubtitle(model.subtitle).setImageUrl(model.imageUrl).setDefaultAction(model.defaultAction).setButton(model.button);
        }

        /* access modifiers changed from: package-private */
        public Builder readFrom(Parcel parcel) {
            return readFrom((ShareMessengerGenericTemplateElement) parcel.readParcelable(ShareMessengerGenericTemplateElement.class.getClassLoader()));
        }
    }
}
