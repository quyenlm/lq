package com.subao.common.e;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.JsonReader;
import android.util.JsonToken;
import android.util.JsonWriter;
import com.subao.common.e;
import com.subao.common.j.l;
import com.subao.common.n.f;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/* compiled from: AccelGame */
public class b implements Parcelable {
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
    @NonNull
    public final String a;
    @Nullable
    public final String b;
    @Nullable
    public final String c;
    @Nullable
    public final String d;
    @Nullable
    public final List<String> e;
    @Nullable
    public final String f;
    @Nullable
    public final List<p> g;
    @Nullable
    public final String h;
    public final int i;
    final int j;
    @Nullable
    public final String k;
    public final boolean l;
    public final int m;
    @Nullable
    private final List<c> n;

    public Iterable<c> a() {
        return this.n;
    }

    public b(@NonNull String str, @Nullable String str2, @Nullable String str3, @Nullable String str4, @Nullable List<String> list, @Nullable String str5, @Nullable List<p> list2, int i2, @Nullable String str6, int i3, @Nullable String str7, int i4, @Nullable List<c> list3) {
        this.a = str.trim();
        this.b = str2;
        this.c = str3;
        this.d = str4;
        this.e = list;
        this.f = str5;
        this.g = list2;
        this.i = i2;
        this.h = str6 == null ? null : str6.trim();
        this.j = i3;
        this.k = str7;
        if (this.a.length() == 3) {
            this.l = a(this.a);
        } else {
            this.l = false;
        }
        this.m = i4;
        this.n = list3;
    }

    public b(Parcel parcel) {
        this.a = parcel.readString();
        this.b = parcel.readString();
        this.c = parcel.readString();
        this.d = parcel.readString();
        this.e = parcel.createStringArrayList();
        this.f = parcel.readString();
        this.g = parcel.createTypedArrayList(p.CREATOR);
        this.i = parcel.readInt();
        this.h = parcel.readString();
        this.j = parcel.readInt();
        this.l = parcel.readInt() != 0;
        this.k = parcel.readString();
        this.m = parcel.readInt();
        this.n = parcel.createTypedArrayList(c.CREATOR);
    }

    private static boolean a(String str) {
        for (int length = str.length() - 1; length >= 0; length--) {
            char charAt = str.charAt(length);
            if (charAt < ' ' || charAt > 127) {
                return false;
            }
        }
        return true;
    }

    public boolean b() {
        return (this.j & 2) != 0;
    }

    public boolean c() {
        return (this.j & 1024) != 0;
    }

    public boolean d() {
        return this.i == 2;
    }

    @NonNull
    public l e() {
        if ((this.j & 16) != 0) {
            if ((this.j & 32) != 0) {
                return l.BOTH;
            }
            return l.UDP;
        } else if ((this.j & 32) != 0) {
            return l.TCP;
        } else {
            return l.BOTH;
        }
    }

    @Nullable
    public List<p> f() {
        return this.g;
    }

    @Nullable
    public String g() {
        List<p> list = this.g;
        if (list == null || list.isEmpty()) {
            return null;
        }
        StringBuilder sb = new StringBuilder(list.size() * 16);
        for (p c2 : list) {
            String c3 = c2.c();
            if (!TextUtils.isEmpty(c3)) {
                sb.append(c3).append(',');
            }
        }
        if (sb.length() <= 0) {
            return null;
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof b)) {
            return false;
        }
        b bVar = (b) obj;
        if (this.m != bVar.m || this.j != bVar.j || this.i != bVar.i || this.l != bVar.l || !e.a(this.a, bVar.a) || !e.a(this.b, bVar.b) || !e.a(this.c, bVar.c) || !e.a(this.d, bVar.d) || !e.a((List) this.e, (List) bVar.e) || !e.a(this.f, bVar.f) || !e.a((List) this.g, (List) bVar.g) || !e.a(this.h, bVar.h) || !e.a(this.k, bVar.k)) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        int i2 = (this.i << 16) | this.j | this.m;
        if (this.l) {
            i2 |= 134217728;
        }
        int hashCode = i2 ^ this.a.hashCode();
        if (this.b != null) {
            hashCode ^= this.b.hashCode();
        }
        if (this.c != null) {
            hashCode ^= this.c.hashCode();
        }
        if (this.d != null) {
            hashCode ^= this.d.hashCode();
        }
        if (this.e != null) {
            hashCode ^= this.e.hashCode();
        }
        if (this.f != null) {
            hashCode ^= this.f.hashCode();
        }
        if (this.g != null) {
            hashCode ^= this.g.hashCode();
        }
        if (this.h != null) {
            hashCode ^= this.h.hashCode();
        }
        if (this.k != null) {
            hashCode ^= this.k.hashCode();
        }
        if (this.n != null) {
            return hashCode ^ this.n.hashCode();
        }
        return hashCode;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i2) {
        parcel.writeString(this.a);
        parcel.writeString(this.b);
        parcel.writeString(this.c);
        parcel.writeString(this.d);
        parcel.writeStringList(this.e);
        parcel.writeString(this.f);
        parcel.writeTypedList(this.g);
        parcel.writeInt(this.i);
        parcel.writeString(this.h);
        parcel.writeInt(this.j);
        parcel.writeInt(this.l ? 1 : 0);
        parcel.writeString(this.k);
        parcel.writeInt(this.m);
        parcel.writeTypedList(this.n);
    }

    /* compiled from: AccelGame */
    public static class c implements Parcelable, com.subao.common.c {
        public static final Parcelable.Creator<c> CREATOR = new Parcelable.Creator<c>() {
            /* renamed from: a */
            public c createFromParcel(Parcel parcel) {
                return new c(parcel);
            }

            /* renamed from: a */
            public c[] newArray(int i) {
                return new c[i];
            }
        };
        public final int a;
        public final int b;

        public c(int i, int i2) {
            this.a = i;
            this.b = i2;
        }

        protected c(Parcel parcel) {
            this.a = parcel.readInt();
            this.b = parcel.readInt();
        }

        public String toString() {
            return String.format(n.b, "[startPort=%d, endPort=%d]", new Object[]{Integer.valueOf(this.a), Integer.valueOf(this.b)});
        }

        public boolean equals(Object obj) {
            boolean z = true;
            if (obj == null) {
                return false;
            }
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof c)) {
                return false;
            }
            c cVar = (c) obj;
            if (!(this.a == cVar.a && this.b == cVar.b)) {
                z = false;
            }
            return z;
        }

        public int hashCode() {
            return this.a | (this.b << 16);
        }

        public int describeContents() {
            return 0;
        }

        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(this.a);
            parcel.writeInt(this.b);
        }

        public void serialize(JsonWriter jsonWriter) {
            jsonWriter.beginObject();
            jsonWriter.name("start").value((long) this.a);
            jsonWriter.name("end").value((long) this.b);
            jsonWriter.endObject();
        }
    }

    /* compiled from: AccelGame */
    public static class a {
        @Nullable
        public List<c> a;
        private String b;
        private String c;
        private String d;
        private List<String> e;
        private String f;
        private List<p> g;
        @Nullable
        private String h;
        private int i;
        private int j;
        @Nullable
        private String k;
        private int l;

        public b a(@NonNull String str) {
            return new b(str, this.b, this.c, this.d, this.e, this.f, this.g, this.i, this.h, this.j, this.k, this.l, this.a);
        }

        public void a(int i2) {
            this.i = i2;
        }

        public void b(int i2) {
            this.j = i2;
        }

        public void b(@Nullable String str) {
            this.h = str;
        }

        public void c(@Nullable String str) {
            this.k = str;
        }

        public void c(int i2) {
            this.l = i2;
        }

        public void d(String str) {
            this.b = str;
        }

        public void e(String str) {
            this.c = str;
        }

        public void f(String str) {
            this.d = str;
        }

        public void a(List<String> list) {
            this.e = list;
        }

        public void g(String str) {
            this.f = str;
        }

        public void b(List<p> list) {
            this.g = list;
        }
    }

    /* renamed from: com.subao.common.e.b$b  reason: collision with other inner class name */
    /* compiled from: AccelGame */
    public static class C0007b {
        @Nullable
        public static b a(@NonNull JsonReader jsonReader, int i) {
            a aVar = new a();
            aVar.c(i);
            jsonReader.beginObject();
            String str = null;
            while (jsonReader.hasNext()) {
                String nextName = jsonReader.nextName();
                if ("appLabel".equals(nextName)) {
                    str = f.a(jsonReader);
                } else if ("appLabelCn".equals(nextName)) {
                    aVar.d(f.a(jsonReader));
                } else if ("appLabelEn".equals(nextName)) {
                    aVar.e(f.a(jsonReader));
                } else if ("description".equals(nextName)) {
                    aVar.f(f.a(jsonReader));
                } else if ("packageName".equals(nextName)) {
                    List a = f.a(jsonReader, new f.a<String>() {
                        @Nullable
                        /* renamed from: a */
                        public String b(@NonNull JsonReader jsonReader) {
                            String a = f.a(jsonReader);
                            if (TextUtils.isEmpty(a)) {
                                return null;
                            }
                            return a;
                        }
                    });
                    if (a == null || a.isEmpty()) {
                        a = null;
                    }
                    aVar.a((List<String>) a);
                } else if ("keyword".equals(nextName)) {
                    aVar.g(f.a(jsonReader));
                } else if ("serverLocation".equals(nextName)) {
                    List a2 = f.a(jsonReader, new f.a<p>() {
                        @Nullable
                        /* renamed from: a */
                        public p b(@NonNull JsonReader jsonReader) {
                            return C0007b.d(jsonReader);
                        }
                    });
                    if (a2 == null || a2.isEmpty()) {
                        a2 = null;
                    }
                    aVar.b((List<p>) a2);
                } else if ("accelMode".equals(nextName)) {
                    aVar.a(jsonReader.nextInt());
                } else if ("bitFlag".equals(nextName)) {
                    aVar.b(jsonReader.nextInt());
                } else if ("hdIcon".equals(nextName)) {
                    aVar.b(jsonReader.nextString());
                } else if ("customFields".equals(nextName)) {
                    aVar.c(f.a(jsonReader));
                } else if ("blackPorts".equals(nextName)) {
                    aVar.a = c(jsonReader);
                } else {
                    jsonReader.skipValue();
                }
            }
            jsonReader.endObject();
            if (TextUtils.isEmpty(str)) {
                return null;
            }
            return aVar.a(str);
        }

        @Nullable
        private static c b(@NonNull JsonReader jsonReader) {
            int i;
            int i2 = -1;
            if (JsonToken.NULL == jsonReader.peek()) {
                jsonReader.skipValue();
                return null;
            }
            jsonReader.beginObject();
            int i3 = -1;
            while (true) {
                i = i2;
                if (!jsonReader.hasNext()) {
                    break;
                }
                String nextName = jsonReader.nextName();
                if ("start".equals(nextName)) {
                    i = jsonReader.nextInt();
                } else if ("end".equals(nextName)) {
                    i3 = jsonReader.nextInt();
                } else {
                    jsonReader.skipValue();
                }
                i2 = i;
            }
            jsonReader.endObject();
            if (i < 0 || i3 < 0) {
                return null;
            }
            return new c(i, i3);
        }

        @Nullable
        private static List<c> c(@NonNull JsonReader jsonReader) {
            if (JsonToken.BEGIN_ARRAY != jsonReader.peek()) {
                jsonReader.skipValue();
                return null;
            }
            ArrayList arrayList = new ArrayList(4);
            jsonReader.beginArray();
            while (jsonReader.hasNext()) {
                c b = b(jsonReader);
                if (b != null) {
                    arrayList.add(b);
                }
            }
            jsonReader.endArray();
            if (arrayList.isEmpty()) {
                return null;
            }
            Collections.sort(arrayList, new Comparator<c>() {
                /* renamed from: a */
                public int compare(c cVar, c cVar2) {
                    int i = cVar.a - cVar2.a;
                    return i != 0 ? i : cVar.b - cVar2.b;
                }
            });
            return arrayList;
        }

        /* access modifiers changed from: private */
        @Nullable
        public static p d(@NonNull JsonReader jsonReader) {
            String str;
            int i = 0;
            jsonReader.beginObject();
            String str2 = null;
            String str3 = null;
            String str4 = null;
            String str5 = null;
            while (jsonReader.hasNext()) {
                String nextName = jsonReader.nextName();
                if ("serverName".equals(nextName)) {
                    str5 = f.a(jsonReader);
                } else if ("serverEnName".equals(nextName)) {
                    str4 = f.a(jsonReader);
                } else if ("gameName".equals(nextName)) {
                    str3 = f.a(jsonReader);
                } else if ("nodeTag".equals(nextName)) {
                    str2 = f.a(jsonReader);
                } else if ("bitFlag".equals(nextName)) {
                    i = jsonReader.nextInt();
                } else {
                    jsonReader.skipValue();
                }
            }
            jsonReader.endObject();
            if (str5 == null) {
                str = "";
            } else {
                str = str5;
            }
            return new p(str, str4, str3, str2, i);
        }
    }
}
