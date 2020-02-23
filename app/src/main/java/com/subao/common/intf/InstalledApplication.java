package com.subao.common.intf;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import com.subao.common.e;

public class InstalledApplication implements Parcelable {
    public static final Parcelable.Creator<InstalledApplication> CREATOR = new Parcelable.Creator<InstalledApplication>() {
        public InstalledApplication createFromParcel(Parcel parcel) {
            return new InstalledApplication(parcel);
        }

        public InstalledApplication[] newArray(int i) {
            return new InstalledApplication[i];
        }
    };
    @NonNull
    private final String label;
    @NonNull
    private final String packageName;
    private final int uid;

    public InstalledApplication(int i, @NonNull String str, @NonNull String str2) {
        this.uid = i;
        this.packageName = str;
        this.label = str2;
    }

    protected InstalledApplication(Parcel parcel) {
        parcel.readInt();
        this.uid = parcel.readInt();
        this.packageName = parcel.readString();
        this.label = parcel.readString();
    }

    public int getUid() {
        return this.uid;
    }

    @NonNull
    public String getPackageName() {
        return this.packageName;
    }

    @NonNull
    public String getLabel() {
        return this.label;
    }

    public boolean equals(Object obj) {
        boolean z = true;
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof InstalledApplication)) {
            return false;
        }
        InstalledApplication installedApplication = (InstalledApplication) obj;
        if (this.uid != installedApplication.uid || !e.a(this.packageName, installedApplication.packageName) || !e.a(this.label, installedApplication.label)) {
            z = false;
        }
        return z;
    }

    public int hashCode() {
        return (this.uid ^ this.packageName.hashCode()) ^ this.label.hashCode();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(0);
        parcel.writeInt(this.uid);
        parcel.writeString(this.packageName);
        parcel.writeString(this.label);
    }
}
