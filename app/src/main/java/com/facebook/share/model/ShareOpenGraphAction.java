package com.facebook.share.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import com.facebook.share.model.ShareOpenGraphValueContainer;

public final class ShareOpenGraphAction extends ShareOpenGraphValueContainer<ShareOpenGraphAction, Builder> {
    public static final Parcelable.Creator<ShareOpenGraphAction> CREATOR = new Parcelable.Creator<ShareOpenGraphAction>() {
        public ShareOpenGraphAction createFromParcel(Parcel in) {
            return new ShareOpenGraphAction(in);
        }

        public ShareOpenGraphAction[] newArray(int size) {
            return new ShareOpenGraphAction[size];
        }
    };

    private ShareOpenGraphAction(Builder builder) {
        super(builder);
    }

    ShareOpenGraphAction(Parcel in) {
        super(in);
    }

    @Nullable
    public String getActionType() {
        return getString("og:type");
    }

    public static final class Builder extends ShareOpenGraphValueContainer.Builder<ShareOpenGraphAction, Builder> {
        private static final String ACTION_TYPE_KEY = "og:type";

        public Builder setActionType(String actionType) {
            putString(ACTION_TYPE_KEY, actionType);
            return this;
        }

        public ShareOpenGraphAction build() {
            return new ShareOpenGraphAction(this);
        }

        /* Debug info: failed to restart local var, previous not found, register: 2 */
        public Builder readFrom(ShareOpenGraphAction model) {
            if (model == null) {
                return this;
            }
            return ((Builder) super.readFrom(model)).setActionType(model.getActionType());
        }

        /* access modifiers changed from: package-private */
        public Builder readFrom(Parcel parcel) {
            return readFrom((ShareOpenGraphAction) parcel.readParcelable(ShareOpenGraphAction.class.getClassLoader()));
        }
    }
}
