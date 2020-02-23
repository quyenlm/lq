package com.subao.common.i;

import android.util.JsonWriter;
import com.subao.common.c;
import com.subao.common.e;
import com.subao.common.n.f;

/* compiled from: Message_App */
public class k implements c {
    public final String a;
    public final String b;

    public k(String str, String str2) {
        this.a = str;
        this.b = str2;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof k)) {
            return false;
        }
        k kVar = (k) obj;
        if (!e.a(this.a, kVar.a) || !e.a(this.b, kVar.b)) {
            return false;
        }
        return true;
    }

    public void serialize(JsonWriter jsonWriter) {
        jsonWriter.beginObject();
        f.a(jsonWriter, "AppLabel", this.a);
        f.a(jsonWriter, "PkgName", this.b);
        jsonWriter.endObject();
    }
}
