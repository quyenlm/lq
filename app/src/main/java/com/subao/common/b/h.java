package com.subao.common.b;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.JsonReader;
import android.util.JsonWriter;
import com.subao.common.c;
import java.io.IOException;

/* compiled from: Scopes */
public class h implements Parcelable, c {
    public static final Parcelable.Creator<h> CREATOR = new Parcelable.Creator<h>() {
        /* renamed from: a */
        public h createFromParcel(Parcel parcel) {
            return new h(parcel);
        }

        /* renamed from: a */
        public h[] newArray(int i) {
            return new h[i];
        }
    };
    public final long a;
    public final long b;
    public final long c;
    public final long d;
    public final long e;

    public h(long j, long j2, long j3, long j4, long j5) {
        this.a = j;
        this.b = j2;
        this.c = j3;
        this.d = j4;
        this.e = j5;
    }

    protected h(Parcel parcel) {
        this.a = parcel.readLong();
        this.b = parcel.readLong();
        this.c = parcel.readLong();
        this.d = parcel.readLong();
        this.e = parcel.readLong();
    }

    public static h a(JsonReader jsonReader) {
        if (jsonReader == null) {
            throw new NullPointerException();
        }
        try {
            jsonReader.setLenient(true);
            jsonReader.beginObject();
            long j = 0;
            long j2 = 0;
            long j3 = 0;
            long j4 = 0;
            long j5 = 0;
            while (jsonReader.hasNext()) {
                String trim = jsonReader.nextName().trim();
                if ("backbone".equals(trim)) {
                    j5 = jsonReader.nextLong();
                } else if ("multi".equals(trim)) {
                    j4 = jsonReader.nextLong();
                } else if ("qos".equals(trim)) {
                    j3 = jsonReader.nextLong();
                } else if ("pcInternal".equals(trim)) {
                    j2 = jsonReader.nextLong();
                } else if ("pcInternational".equals(trim)) {
                    j = jsonReader.nextLong();
                } else {
                    jsonReader.skipValue();
                }
            }
            jsonReader.endObject();
            return new h(j5, j4, j3, j2, j);
        } catch (RuntimeException e2) {
            throw new IOException(e2.getMessage());
        }
    }

    public void serialize(JsonWriter jsonWriter) {
        jsonWriter.beginObject();
        jsonWriter.name("backbone").value(this.a);
        jsonWriter.name("multi").value(this.b);
        jsonWriter.name("qos").value(this.c);
        jsonWriter.name("pcInternal").value(this.d);
        jsonWriter.name("pcInternational").value(this.e);
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
        if (!(obj instanceof h)) {
            return false;
        }
        h hVar = (h) obj;
        if (!(hVar.a == this.a && hVar.b == this.b && hVar.c == this.c && hVar.d == this.d && hVar.e == this.e)) {
            z = false;
        }
        return z;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(this.a);
        parcel.writeLong(this.b);
        parcel.writeLong(this.c);
        parcel.writeLong(this.d);
        parcel.writeLong(this.e);
    }
}
