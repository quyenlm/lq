package com.subao.common.e;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.subao.common.e;

/* compiled from: GameServerLocation */
public class p implements Parcelable {
    public static final Parcelable.Creator<p> CREATOR = new Parcelable.Creator<p>() {
        /* renamed from: a */
        public p createFromParcel(Parcel parcel) {
            return new p(parcel);
        }

        /* renamed from: a */
        public p[] newArray(int i) {
            return new p[i];
        }
    };
    @NonNull
    private final String a;
    @Nullable
    private final String b;
    @Nullable
    private final String c;
    @Nullable
    private final String d;
    private final int e;

    public p(@NonNull String str, @Nullable String str2, @Nullable String str3, @Nullable String str4, int i) {
        this.a = str;
        this.b = str2;
        this.c = str3;
        this.d = str4;
        this.e = i;
    }

    public p(Parcel parcel) {
        this.a = parcel.readString();
        this.b = parcel.readString();
        this.c = parcel.readString();
        this.d = parcel.readString();
        this.e = parcel.readInt();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.a);
        parcel.writeString(this.b);
        parcel.writeString(this.c);
        parcel.writeString(this.d);
        parcel.writeInt(this.e);
    }

    public int hashCode() {
        int hashCode = this.a.hashCode();
        if (this.b != null) {
            hashCode = (hashCode * 31) + this.b.hashCode();
        }
        if (this.c != null) {
            hashCode = (hashCode * 31) + this.c.hashCode();
        }
        if (this.d != null) {
            hashCode = (hashCode * 31) + this.d.hashCode();
        }
        return (hashCode * 31) + this.e;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof p)) {
            return false;
        }
        p pVar = (p) obj;
        if (this.e != pVar.e || !e.a(this.a, pVar.a) || !e.a(this.b, pVar.b) || !e.a(this.c, pVar.c) || !e.a(this.d, pVar.d)) {
            return false;
        }
        return true;
    }

    @NonNull
    public String a() {
        return this.a;
    }

    @Nullable
    public String b() {
        return this.b;
    }

    @Nullable
    public String c() {
        return this.d;
    }

    public boolean d() {
        return (this.e & 1) != 0;
    }
}
