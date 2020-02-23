package com.facebook.share.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import com.facebook.share.model.ShareContent;
import com.facebook.share.model.ShareOpenGraphAction;

public final class ShareOpenGraphContent extends ShareContent<ShareOpenGraphContent, Builder> {
    public static final Parcelable.Creator<ShareOpenGraphContent> CREATOR = new Parcelable.Creator<ShareOpenGraphContent>() {
        public ShareOpenGraphContent createFromParcel(Parcel in) {
            return new ShareOpenGraphContent(in);
        }

        public ShareOpenGraphContent[] newArray(int size) {
            return new ShareOpenGraphContent[size];
        }
    };
    private final ShareOpenGraphAction action;
    private final String previewPropertyName;

    private ShareOpenGraphContent(Builder builder) {
        super((ShareContent.Builder) builder);
        this.action = builder.action;
        this.previewPropertyName = builder.previewPropertyName;
    }

    ShareOpenGraphContent(Parcel in) {
        super(in);
        this.action = new ShareOpenGraphAction.Builder().readFrom(in).build();
        this.previewPropertyName = in.readString();
    }

    @Nullable
    public ShareOpenGraphAction getAction() {
        return this.action;
    }

    @Nullable
    public String getPreviewPropertyName() {
        return this.previewPropertyName;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        super.writeToParcel(out, flags);
        out.writeParcelable(this.action, 0);
        out.writeString(this.previewPropertyName);
    }

    public static final class Builder extends ShareContent.Builder<ShareOpenGraphContent, Builder> {
        /* access modifiers changed from: private */
        public ShareOpenGraphAction action;
        /* access modifiers changed from: private */
        public String previewPropertyName;

        public Builder setAction(@Nullable ShareOpenGraphAction action2) {
            ShareOpenGraphAction build;
            if (action2 == null) {
                build = null;
            } else {
                build = new ShareOpenGraphAction.Builder().readFrom(action2).build();
            }
            this.action = build;
            return this;
        }

        public Builder setPreviewPropertyName(@Nullable String previewPropertyName2) {
            this.previewPropertyName = previewPropertyName2;
            return this;
        }

        public ShareOpenGraphContent build() {
            return new ShareOpenGraphContent(this);
        }

        /* Debug info: failed to restart local var, previous not found, register: 2 */
        public Builder readFrom(ShareOpenGraphContent model) {
            if (model == null) {
                return this;
            }
            return ((Builder) super.readFrom(model)).setAction(model.getAction()).setPreviewPropertyName(model.getPreviewPropertyName());
        }
    }
}
