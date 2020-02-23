package com.subao.common.i;

import android.content.Context;
import android.os.Build;
import android.support.v4.media.session.PlaybackStateCompat;
import android.util.JsonWriter;
import com.subao.common.c;
import com.subao.common.n.e;
import com.subao.common.n.f;

/* compiled from: Message_DeviceInfo */
public class l implements c {
    private final String a;
    private final int b;
    private final int c;
    private final int d;
    private final String e;

    public String a() {
        return this.a;
    }

    public int b() {
        return this.b;
    }

    public int c() {
        return this.c;
    }

    public int d() {
        return this.d;
    }

    public String e() {
        return this.e;
    }

    public l(String str, int i, int i2, int i3, String str2) {
        this.a = str;
        this.b = i;
        this.c = i2;
        this.d = i3;
        this.e = str2;
    }

    public l(Context context) {
        this(Build.MODEL, (int) e.a.c(), e.a.b(), (int) (e.b(context) / PlaybackStateCompat.ACTION_SET_CAPTIONING_ENABLED), Build.DISPLAY);
    }

    public void serialize(JsonWriter jsonWriter) {
        jsonWriter.beginObject();
        f.a(jsonWriter, "model", this.a);
        jsonWriter.name("cpuSpeed").value((long) this.b);
        jsonWriter.name("cpuCore").value((long) this.c);
        jsonWriter.name("memory").value((long) this.d);
        f.a(jsonWriter, "rom", this.e);
        jsonWriter.endObject();
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof l)) {
            return false;
        }
        l lVar = (l) obj;
        if (this.b == lVar.b && this.c == lVar.c && this.d == lVar.d && com.subao.common.e.a(this.a, lVar.a) && com.subao.common.e.a(this.e, lVar.e)) {
            return true;
        }
        return false;
    }
}
