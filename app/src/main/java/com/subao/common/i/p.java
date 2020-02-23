package com.subao.common.i;

import android.os.Build;
import android.util.JsonWriter;
import com.subao.common.c;
import com.subao.common.e;
import com.subao.common.n.f;
import com.tencent.imsdk.framework.request.HttpRequestParams;

/* compiled from: Message_VersionInfo */
public class p implements c {
    public final String a;
    public final String b;
    public final String c;
    public final String d;

    p(String str, String str2, String str3, String str4) {
        this.a = str;
        this.b = str2;
        this.c = str3;
        this.d = str4;
    }

    public static p a(String str, String str2) {
        return new p(str, str2, Build.FINGERPRINT, Build.VERSION.RELEASE);
    }

    public void serialize(JsonWriter jsonWriter) {
        jsonWriter.beginObject();
        f.a(jsonWriter, "number", this.a);
        f.a(jsonWriter, "channel", this.b);
        f.a(jsonWriter, HttpRequestParams.OS_VERSION, this.c);
        f.a(jsonWriter, "androidVersion", this.d);
        jsonWriter.endObject();
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
        if (!e.a(this.a, pVar.a) || !e.a(this.b, pVar.b) || !e.a(this.c, pVar.c) || !e.a(this.d, pVar.d)) {
            return false;
        }
        return true;
    }
}
