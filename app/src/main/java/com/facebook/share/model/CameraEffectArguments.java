package com.facebook.share.model;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import java.util.Set;

public class CameraEffectArguments implements ShareModel {
    public static final Parcelable.Creator<CameraEffectArguments> CREATOR = new Parcelable.Creator<CameraEffectArguments>() {
        public CameraEffectArguments createFromParcel(Parcel in) {
            return new CameraEffectArguments(in);
        }

        public CameraEffectArguments[] newArray(int size) {
            return new CameraEffectArguments[size];
        }
    };
    /* access modifiers changed from: private */
    public final Bundle params;

    private CameraEffectArguments(Builder builder) {
        this.params = builder.params;
    }

    CameraEffectArguments(Parcel in) {
        this.params = in.readBundle(getClass().getClassLoader());
    }

    @Nullable
    public String getString(String key) {
        return this.params.getString(key);
    }

    @Nullable
    public String[] getStringArray(String key) {
        return this.params.getStringArray(key);
    }

    @Nullable
    public Object get(String key) {
        return this.params.get(key);
    }

    public Set<String> keySet() {
        return this.params.keySet();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeBundle(this.params);
    }

    public static class Builder implements ShareModelBuilder<CameraEffectArguments, Builder> {
        /* access modifiers changed from: private */
        public Bundle params = new Bundle();

        public Builder putArgument(String key, String value) {
            this.params.putString(key, value);
            return this;
        }

        public Builder putArgument(String key, String[] arrayValue) {
            this.params.putStringArray(key, arrayValue);
            return this;
        }

        public Builder readFrom(CameraEffectArguments model) {
            if (model != null) {
                this.params.putAll(model.params);
            }
            return this;
        }

        public Builder readFrom(Parcel parcel) {
            return readFrom((CameraEffectArguments) parcel.readParcelable(CameraEffectArguments.class.getClassLoader()));
        }

        public CameraEffectArguments build() {
            return new CameraEffectArguments(this);
        }
    }
}
