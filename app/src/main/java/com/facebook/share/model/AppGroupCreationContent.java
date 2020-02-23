package com.facebook.share.model;

import android.os.Parcel;
import android.os.Parcelable;

public final class AppGroupCreationContent implements ShareModel {
    public static final Parcelable.Creator<AppGroupCreationContent> CREATOR = new Parcelable.Creator<AppGroupCreationContent>() {
        public AppGroupCreationContent createFromParcel(Parcel in) {
            return new AppGroupCreationContent(in);
        }

        public AppGroupCreationContent[] newArray(int size) {
            return new AppGroupCreationContent[size];
        }
    };
    private final String description;
    private final String name;
    private AppGroupPrivacy privacy;

    public enum AppGroupPrivacy {
        Open,
        Closed
    }

    private AppGroupCreationContent(Builder builder) {
        this.name = builder.name;
        this.description = builder.description;
        this.privacy = builder.privacy;
    }

    AppGroupCreationContent(Parcel in) {
        this.name = in.readString();
        this.description = in.readString();
        this.privacy = (AppGroupPrivacy) in.readSerializable();
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public AppGroupPrivacy getAppGroupPrivacy() {
        return this.privacy;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeString(this.name);
        out.writeString(this.description);
        out.writeSerializable(this.privacy);
    }

    public static class Builder implements ShareModelBuilder<AppGroupCreationContent, Builder> {
        /* access modifiers changed from: private */
        public String description;
        /* access modifiers changed from: private */
        public String name;
        /* access modifiers changed from: private */
        public AppGroupPrivacy privacy;

        public Builder setName(String name2) {
            this.name = name2;
            return this;
        }

        public Builder setDescription(String description2) {
            this.description = description2;
            return this;
        }

        public Builder setAppGroupPrivacy(AppGroupPrivacy privacy2) {
            this.privacy = privacy2;
            return this;
        }

        public AppGroupCreationContent build() {
            return new AppGroupCreationContent(this);
        }

        /* Debug info: failed to restart local var, previous not found, register: 2 */
        public Builder readFrom(AppGroupCreationContent content) {
            if (content == null) {
                return this;
            }
            return setName(content.getName()).setDescription(content.getDescription()).setAppGroupPrivacy(content.getAppGroupPrivacy());
        }
    }
}
