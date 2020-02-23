package com.subao.common.e;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.JsonWriter;
import com.beetalk.sdk.plugin.impl.gglive.GGLiveConstants;
import com.subao.common.e;
import com.subao.common.e.b;
import com.subao.common.j.l;
import com.subao.common.n.f;
import com.tencent.component.net.download.multiplex.download.DownloadDBHelper;
import java.io.StringWriter;

/* compiled from: SupportGame */
public class ak {
    public final int a;
    @NonNull
    public final String b;
    @NonNull
    public final String c;
    public final int d;
    @NonNull
    public final l e;
    @Nullable
    public final String f;
    private final boolean g;
    @Nullable
    private final Iterable<p> h;
    private final Iterable<b.c> i;

    public ak(int i2, boolean z, @NonNull String str, @NonNull String str2, int i3, @NonNull l lVar, @Nullable Iterable<p> iterable, @Nullable String str3, @Nullable Iterable<b.c> iterable2) {
        this.a = i2;
        this.g = z;
        this.b = str;
        this.c = str2;
        this.d = i3;
        this.e = lVar;
        this.h = iterable;
        this.f = str3;
        this.i = iterable2;
    }

    public boolean a() {
        return (this.d & 1) != 0;
    }

    public boolean b() {
        return this.g;
    }

    @Nullable
    public Iterable<b.c> c() {
        return this.i;
    }

    @Nullable
    public Iterable<p> d() {
        return this.h;
    }

    public String toString() {
        return String.format(n.b, "[%s (uid=%d), protocol=%s, flag=%d]", new Object[]{this.b, Integer.valueOf(this.a), this.e.d, Integer.valueOf(this.d)});
    }

    public boolean equals(Object obj) {
        boolean z = true;
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof ak)) {
            return false;
        }
        ak akVar = (ak) obj;
        if (this.a != akVar.a || this.g != akVar.g || this.e != akVar.e || this.d != akVar.d || !e.a(this.b, akVar.b) || !e.a(this.c, akVar.c) || !e.a(this.f, akVar.f) || !e.a(this.h, akVar.h)) {
            z = false;
        }
        return z;
    }

    public int hashCode() {
        int ordinal = this.d | (this.e.ordinal() << 16);
        if (this.g) {
            ordinal |= 1048576;
        }
        int i2 = ordinal | (this.a << 21);
        if (this.f != null) {
            i2 ^= this.f.hashCode();
        }
        if (this.h != null) {
            i2 ^= this.h.hashCode();
        }
        return (i2 ^ this.b.hashCode()) ^ this.c.hashCode();
    }

    @NonNull
    public String e() {
        StringWriter stringWriter = new StringWriter(2048);
        JsonWriter jsonWriter = new JsonWriter(stringWriter);
        jsonWriter.beginObject();
        jsonWriter.name(GGLiveConstants.PARAM.UID).value((long) this.a);
        jsonWriter.name("packageName").value(this.b);
        jsonWriter.name("appLabel").value(this.c);
        jsonWriter.name(DownloadDBHelper.FLAG).value((long) this.d);
        jsonWriter.name("protocol").value(this.e.d);
        f.a(jsonWriter, "nodeTag", this.f);
        f.a(jsonWriter, "blackPorts", this.i);
        jsonWriter.endObject();
        jsonWriter.close();
        return stringWriter.toString();
    }
}
