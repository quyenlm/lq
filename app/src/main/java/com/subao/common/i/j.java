package com.subao.common.i;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.util.JsonWriter;
import com.subao.common.c;
import com.subao.common.e;
import com.subao.common.e.n;
import com.subao.common.n.f;
import com.tencent.imsdk.config.ConfigDBHelper;

/* compiled from: MessageUserId */
public class j implements Parcelable, c {
    public static final Parcelable.Creator<j> CREATOR = new Parcelable.Creator<j>() {
        /* renamed from: a */
        public j createFromParcel(Parcel parcel) {
            b bVar;
            String readString = parcel.readString();
            String readString2 = parcel.readString();
            String readString3 = parcel.readString();
            int readInt = parcel.readInt();
            String readString4 = parcel.readString();
            if (parcel.readInt() >= 0) {
                bVar = b.CREATOR.createFromParcel(parcel);
            } else {
                bVar = null;
            }
            return new j(readString, readString2, readString3, readInt, readString4, bVar);
        }

        /* renamed from: a */
        public j[] newArray(int i) {
            return new j[i];
        }
    };
    private static a g;
    private static String h;
    private static String i;
    private static String j;
    private static int k;
    private static String l;
    private static String m;
    private static b n;
    @Nullable
    public final String a;
    @Nullable
    public final String b;
    @Nullable
    public final String c;
    public final int d;
    @Nullable
    public final String e;
    @Nullable
    final b f;

    /* compiled from: MessageUserId */
    public interface a {
        void a(String str);

        void a(String str, String str2, int i);
    }

    public j(@Nullable String str, @Nullable String str2, @Nullable String str3, int i2, @Nullable String str4, @Nullable b bVar) {
        this.a = str;
        this.b = str2;
        this.c = str3;
        this.d = i2;
        this.e = str4;
        this.f = bVar;
    }

    private static synchronized a h() {
        a aVar;
        synchronized (j.class) {
            aVar = g;
        }
        return aVar;
    }

    public static j a() {
        return new j(b(), c(), d(), e(), f(), g());
    }

    public static synchronized String b() {
        String str;
        synchronized (j.class) {
            str = h;
        }
        return str;
    }

    public static void a(String str) {
        synchronized (j.class) {
            h = str;
        }
        a h2 = h();
        if (h2 != null) {
            h2.a(str);
        }
    }

    public static synchronized void b(String str) {
        synchronized (j.class) {
            i = str;
            j = null;
            k = 0;
            l = null;
            m = null;
            n = null;
        }
    }

    public static void a(String str, String str2, int i2, String str3, b bVar) {
        synchronized (j.class) {
            i = str;
            j = str2;
            k = i2;
            l = null;
            m = str3;
            n = bVar;
        }
        a h2 = h();
        if (h2 != null) {
            h2.a(str, str2, i2);
        }
    }

    @Nullable
    public static synchronized String c() {
        String str;
        synchronized (j.class) {
            str = i;
        }
        return str;
    }

    @Nullable
    public static synchronized String d() {
        String str;
        synchronized (j.class) {
            str = j;
        }
        return str;
    }

    public static synchronized int e() {
        int i2;
        synchronized (j.class) {
            i2 = k;
        }
        return i2;
    }

    @Nullable
    public static synchronized String f() {
        String str;
        synchronized (j.class) {
            str = l;
        }
        return str;
    }

    @Nullable
    public static synchronized b g() {
        b bVar;
        synchronized (j.class) {
            bVar = n;
        }
        return bVar;
    }

    public String toString() {
        return String.format(n.b, "[subaoId=%s, userId=%s, serviceId=%s, userStatus=%d, config=%s]", new Object[]{this.a, this.b, this.c, Integer.valueOf(this.d), this.e});
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof j)) {
            return false;
        }
        j jVar = (j) obj;
        if (this.d != jVar.d || !e.a(this.a, jVar.a) || !e.a(this.b, jVar.b) || !e.a(this.c, jVar.c) || !e.a(this.e, jVar.e) || !e.a(this.f, jVar.f)) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        int i2 = this.d;
        for (Object obj : new Object[]{this.a, this.b, this.c, this.e, this.f}) {
            if (obj != null) {
                i2 ^= obj.hashCode();
            }
        }
        return i2;
    }

    public void serialize(JsonWriter jsonWriter) {
        jsonWriter.beginObject();
        f.a(jsonWriter, "id", this.a);
        f.a(jsonWriter, "userId", this.b);
        f.a(jsonWriter, "serviceId", this.c);
        jsonWriter.name("stat").value((long) this.d);
        f.a(jsonWriter, ConfigDBHelper.DATABASE_TABLE_NAME, this.e);
        f.a(jsonWriter, "credit", (c) this.f);
        jsonWriter.endObject();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i2) {
        parcel.writeString(this.a);
        parcel.writeString(this.b);
        parcel.writeString(this.c);
        parcel.writeInt(this.d);
        parcel.writeString(this.e);
        if (this.f == null) {
            parcel.writeInt(-1);
            return;
        }
        parcel.writeInt(0);
        this.f.writeToParcel(parcel, 0);
    }
}
