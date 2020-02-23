package com.subao.common.i;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.util.JsonWriter;
import com.subao.common.c;
import com.subao.common.e;
import com.subao.common.e.n;
import com.subao.common.n.g;

/* compiled from: MessageCredit */
public class b implements Parcelable, c {
    public static final Parcelable.Creator<b> CREATOR = new Parcelable.Creator<b>() {
        /* renamed from: a */
        public b createFromParcel(Parcel parcel) {
            return new b(parcel);
        }

        /* renamed from: a */
        public b[] newArray(int i) {
            return new b[i];
        }
    };
    public final long a;
    public final int b;
    public final int c;
    @Nullable
    public final String d;

    public b(long j, int i, int i2, @Nullable String str) {
        this.a = j;
        this.b = i;
        this.c = i2;
        this.d = str;
    }

    protected b(Parcel parcel) {
        this.a = parcel.readLong();
        this.b = parcel.readInt();
        this.c = parcel.readInt();
        this.d = parcel.readString();
    }

    public void serialize(JsonWriter jsonWriter) {
        jsonWriter.beginObject();
        jsonWriter.name("start").value(this.a);
        jsonWriter.name("length").value((long) this.b);
        jsonWriter.name("type").value((long) this.c);
        if (this.d != null) {
            jsonWriter.name("id").value(this.d);
        }
        jsonWriter.endObject();
    }

    public boolean equals(Object obj) {
        boolean z = true;
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof b)) {
            return false;
        }
        b bVar = (b) obj;
        if (!(this.b == bVar.b && this.c == bVar.c && this.a == bVar.a && e.a(this.d, bVar.d))) {
            z = false;
        }
        return z;
    }

    public int hashCode() {
        int i = ((this.b | (this.c << 24)) ^ ((int) this.a)) ^ ((int) (this.a >> 32));
        if (this.d != null) {
            return i ^ this.d.hashCode();
        }
        return i;
    }

    public String toString() {
        return String.format(n.b, "[%d-%d, t=%d, id=%s]", new Object[]{Long.valueOf(this.a), Integer.valueOf(this.b), Integer.valueOf(this.c), g.a((Object) this.d)});
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(this.a);
        parcel.writeInt(this.b);
        parcel.writeInt(this.c);
        parcel.writeString(this.d);
    }
}
