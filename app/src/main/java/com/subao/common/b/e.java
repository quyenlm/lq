package com.subao.common.b;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.JsonReader;
import android.util.JsonWriter;
import com.subao.common.c;
import com.subao.common.n.f;
import java.io.IOException;

/* compiled from: JWTTokenResp */
public class e implements Parcelable, c {
    public static final Parcelable.Creator<e> CREATOR = new Parcelable.Creator<e>() {
        /* renamed from: a */
        public e createFromParcel(Parcel parcel) {
            return new e(parcel);
        }

        /* renamed from: a */
        public e[] newArray(int i) {
            return new e[i];
        }
    };
    @NonNull
    public final String a;
    @NonNull
    public final String b;
    public final long c;
    public final String d;
    public final int e;
    public final String f;
    public final int g;
    public final h h;
    public final int i;
    public final long j;
    public final int k;
    public final int l;
    public final String m;
    public final int n;
    public final boolean o;
    @Nullable
    public final String p;

    public e(@NonNull String str, @NonNull String str2, long j2, String str3, int i2, String str4, int i3, h hVar, int i4, long j3, int i5, int i6, String str5, int i7, boolean z, @Nullable String str6) {
        this.a = str;
        this.b = str2;
        this.c = j2;
        this.d = str3;
        this.e = i2;
        this.f = str4;
        this.g = i3;
        this.h = hVar;
        this.i = i4;
        this.j = j3;
        this.k = i5;
        this.l = i6;
        this.m = str5;
        this.n = i7;
        this.o = z;
        this.p = str6;
    }

    protected e(Parcel parcel) {
        this.a = parcel.readString();
        this.b = parcel.readString();
        this.c = parcel.readLong();
        this.d = parcel.readString();
        this.e = parcel.readInt();
        this.f = parcel.readString();
        this.g = parcel.readInt();
        this.h = (h) parcel.readParcelable(h.class.getClassLoader());
        this.i = parcel.readInt();
        this.j = parcel.readLong();
        this.k = parcel.readInt();
        this.l = parcel.readInt();
        this.m = parcel.readString();
        this.n = parcel.readInt();
        this.o = parcel.readInt() == 1;
        this.p = parcel.readString();
    }

    @NonNull
    public static e a(@NonNull JsonReader jsonReader) {
        a aVar = new a();
        try {
            jsonReader.beginObject();
            while (jsonReader.hasNext()) {
                String nextName = jsonReader.nextName();
                if (!TextUtils.isEmpty(nextName)) {
                    char c2 = 65535;
                    switch (nextName.hashCode()) {
                        case -1370246671:
                            if (nextName.equals("accelToken")) {
                                c2 = 1;
                                break;
                            }
                            break;
                        case -1369377985:
                            if (nextName.equals("creditLength")) {
                                c2 = 10;
                                break;
                            }
                            break;
                        case -1334310203:
                            if (nextName.equals("purchaseTimes")) {
                                c2 = 8;
                                break;
                            }
                            break;
                        case -1302672773:
                            if (nextName.equals("totalAccelDays")) {
                                c2 = 6;
                                break;
                            }
                            break;
                        case -907768673:
                            if (nextName.equals("scopes")) {
                                c2 = 7;
                                break;
                            }
                            break;
                        case -836030906:
                            if (nextName.equals("userId")) {
                                c2 = 0;
                                break;
                            }
                            break;
                        case -759770086:
                            if (nextName.equals("useContractDiscount")) {
                                c2 = 14;
                                break;
                            }
                            break;
                        case -564295213:
                            if (nextName.equals("creditType")) {
                                c2 = 11;
                                break;
                            }
                            break;
                        case -314368791:
                            if (nextName.equals("creditStart")) {
                                c2 = 9;
                                break;
                            }
                            break;
                        case 250196857:
                            if (nextName.equals("expiresIn")) {
                                c2 = 2;
                                break;
                            }
                            break;
                        case 952580004:
                            if (nextName.equals("contractStatus")) {
                                c2 = 13;
                                break;
                            }
                            break;
                        case 1132443704:
                            if (nextName.equals("portraits")) {
                                c2 = 15;
                                break;
                            }
                            break;
                        case 1198354826:
                            if (nextName.equals("accelExpiredTime")) {
                                c2 = 5;
                                break;
                            }
                            break;
                        case 1591632797:
                            if (nextName.equals("userStatus")) {
                                c2 = 4;
                                break;
                            }
                            break;
                        case 1822874068:
                            if (nextName.equals("creditID")) {
                                c2 = 12;
                                break;
                            }
                            break;
                        case 2067160759:
                            if (nextName.equals("shortId")) {
                                c2 = 3;
                                break;
                            }
                            break;
                    }
                    switch (c2) {
                        case 0:
                            aVar.a = f.a(jsonReader);
                            break;
                        case 1:
                            aVar.b = jsonReader.nextString();
                            break;
                        case 2:
                            aVar.c = jsonReader.nextLong();
                            break;
                        case 3:
                            aVar.d = jsonReader.nextString();
                            break;
                        case 4:
                            aVar.e = jsonReader.nextInt();
                            break;
                        case 5:
                            aVar.f = jsonReader.nextString();
                            break;
                        case 6:
                            aVar.g = jsonReader.nextInt();
                            break;
                        case 7:
                            aVar.h = h.a(jsonReader);
                            break;
                        case 8:
                            aVar.i = jsonReader.nextInt();
                            break;
                        case 9:
                            aVar.j = jsonReader.nextLong();
                            break;
                        case 10:
                            aVar.k = jsonReader.nextInt();
                            break;
                        case 11:
                            aVar.l = jsonReader.nextInt();
                            break;
                        case 12:
                            aVar.m = jsonReader.nextString();
                            break;
                        case 13:
                            aVar.n = jsonReader.nextInt();
                            break;
                        case 14:
                            aVar.o = jsonReader.nextBoolean();
                            break;
                        case 15:
                            aVar.p = f.a(jsonReader);
                            break;
                        default:
                            jsonReader.skipValue();
                            break;
                    }
                }
            }
            jsonReader.endObject();
            e a2 = aVar.a();
            if (a2 != null) {
                return a2;
            }
            throw new IOException("Create fail (Input JSON Invalid)");
        } catch (RuntimeException e2) {
            throw new IOException(e2.getMessage());
        }
    }

    public boolean equals(Object obj) {
        boolean z = true;
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof e)) {
            return false;
        }
        e eVar = (e) obj;
        if (!(this.g == eVar.g && this.e == eVar.e && this.i == eVar.i && this.j == eVar.j && this.k == eVar.k && this.l == eVar.l && this.n == eVar.n && this.o == eVar.o && com.subao.common.e.a(this.m, eVar.m) && com.subao.common.e.a(this.a, eVar.a) && com.subao.common.e.a(this.b, eVar.b) && com.subao.common.e.a(this.d, eVar.d) && com.subao.common.e.a(this.f, eVar.f) && com.subao.common.e.a(this.h, eVar.h) && com.subao.common.e.a(this.p, eVar.p))) {
            z = false;
        }
        return z;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i2) {
        parcel.writeString(this.a);
        parcel.writeString(this.b);
        parcel.writeLong(this.c);
        parcel.writeString(this.d);
        parcel.writeInt(this.e);
        parcel.writeString(this.f);
        parcel.writeInt(this.g);
        parcel.writeParcelable(this.h, i2);
        parcel.writeInt(this.i);
        parcel.writeLong(this.j);
        parcel.writeInt(this.k);
        parcel.writeInt(this.l);
        parcel.writeString(this.m);
        parcel.writeInt(this.n);
        parcel.writeInt(this.o ? 1 : 0);
        parcel.writeString(this.p);
    }

    public void serialize(JsonWriter jsonWriter) {
        jsonWriter.beginObject();
        f.a(jsonWriter, "userId", this.a);
        f.a(jsonWriter, "accelToken", this.b);
        jsonWriter.name("expiresIn").value(this.c);
        f.a(jsonWriter, "shortId", this.d);
        jsonWriter.name("userStatus").value((long) this.e);
        f.a(jsonWriter, "accelExpiredTime", this.f);
        jsonWriter.name("totalAccelDays").value((long) this.g);
        f.a(jsonWriter, "scopes", (c) this.h);
        jsonWriter.name("purchaseTimes").value((long) this.i);
        jsonWriter.name("creditStart").value(this.j);
        jsonWriter.name("creditLength").value((long) this.k);
        jsonWriter.name("creditType").value((long) this.l);
        jsonWriter.name("creditID").value(this.m);
        jsonWriter.name("contractStatus").value((long) this.n);
        jsonWriter.name("useContractDiscount").value(this.o);
        jsonWriter.name("portraits").value(this.p);
        jsonWriter.endObject();
    }

    /* compiled from: JWTTokenResp */
    public static class a {
        public String a;
        public String b;
        public long c;
        public String d;
        public int e;
        public String f;
        public int g;
        public h h;
        public int i;
        public long j;
        public int k;
        public int l;
        public String m;
        public int n;
        public boolean o;
        @Nullable
        public String p;

        @Nullable
        public e a() {
            if (TextUtils.isEmpty(this.a) || TextUtils.isEmpty(this.b)) {
                return null;
            }
            return new e(this.a, this.b, this.c, this.d, this.e, this.f, this.g, this.h, this.i, this.j, this.k, this.l, this.m, this.n, this.o, this.p);
        }
    }
}
