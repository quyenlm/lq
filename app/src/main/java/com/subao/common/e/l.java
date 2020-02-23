package com.subao.common.e;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.JsonReader;
import android.util.JsonToken;
import android.util.JsonWriter;
import com.subao.common.c;
import com.subao.common.e;
import com.subao.common.n.f;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/* compiled from: Coupon */
public class l implements c {
    @NonNull
    private final String a;
    @NonNull
    private final String b;
    private final int c;
    @Nullable
    private final Map<String, String> d;

    public l(@NonNull String str, @NonNull String str2, int i, @Nullable Map<String, String> map) {
        this.a = str;
        this.b = str2;
        this.c = i;
        this.d = map;
    }

    @NonNull
    public static l a(JsonReader jsonReader) {
        int i = 0;
        jsonReader.beginObject();
        Map<String, String> map = null;
        String str = null;
        String str2 = null;
        while (jsonReader.hasNext()) {
            String nextName = jsonReader.nextName();
            if ("couponId".equals(nextName)) {
                str2 = f.a(jsonReader);
            } else if ("couponName".equals(nextName)) {
                str = f.a(jsonReader);
            } else if ("appClientParas".equals(nextName)) {
                map = b(jsonReader);
            } else if ("couponType".equals(nextName)) {
                i = jsonReader.nextInt();
            } else {
                jsonReader.skipValue();
            }
        }
        jsonReader.endObject();
        if (!TextUtils.isEmpty(str2) && !TextUtils.isEmpty(str)) {
            return new l(str2, str, i, map);
        }
        throw new IOException("Invalid Coupon JSON");
    }

    @Nullable
    private static Map<String, String> b(JsonReader jsonReader) {
        if (JsonToken.NULL == jsonReader.peek()) {
            jsonReader.skipValue();
            return null;
        }
        HashMap hashMap = new HashMap(2);
        jsonReader.beginObject();
        while (jsonReader.hasNext()) {
            hashMap.put(jsonReader.nextName(), jsonReader.nextString());
        }
        jsonReader.endObject();
        if (!hashMap.isEmpty()) {
            return hashMap;
        }
        return null;
    }

    @NonNull
    public String a() {
        return this.a;
    }

    @NonNull
    public String b() {
        return this.b;
    }

    public int c() {
        return this.c;
    }

    @Nullable
    public String a(@NonNull String str) {
        if (this.d == null) {
            return null;
        }
        return this.d.get(str);
    }

    public boolean equals(Object obj) {
        boolean z = true;
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof l)) {
            return false;
        }
        l lVar = (l) obj;
        if (this.c != lVar.c || !e.a(this.a, lVar.a) || !e.a(this.b, lVar.b) || !e.a(this.d, lVar.d)) {
            z = false;
        }
        return z;
    }

    public void serialize(JsonWriter jsonWriter) {
        jsonWriter.beginObject();
        jsonWriter.name("couponId").value(a());
        jsonWriter.name("couponName").value(b());
        jsonWriter.name("couponType").value((long) c());
        if (this.d != null && !this.d.isEmpty()) {
            jsonWriter.name("appClientParas");
            jsonWriter.beginObject();
            for (Map.Entry next : this.d.entrySet()) {
                jsonWriter.name((String) next.getKey());
                jsonWriter.value((String) next.getValue());
            }
            jsonWriter.endObject();
        }
        jsonWriter.endObject();
    }
}
