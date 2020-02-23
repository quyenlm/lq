package com.subao.common.i;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.JsonWriter;
import com.subao.common.c;
import com.subao.common.e.g;
import com.subao.common.n.e;
import com.subao.common.n.f;
import com.tencent.imsdk.sns.api.IMFriend;

/* compiled from: Message_Installation */
public class n implements c {
    public final long a;
    public final a b;
    public final l c;
    public final p d;
    public final g e;

    public n(g gVar, long j, a aVar, l lVar, p pVar) {
        this.a = j;
        this.b = aVar;
        this.c = lVar;
        this.d = pVar;
        this.e = gVar;
    }

    public void serialize(JsonWriter jsonWriter) {
        jsonWriter.beginObject();
        jsonWriter.name("time").value(this.a);
        if (this.b != null) {
            jsonWriter.name(IMFriend.FriendPrarams.USER);
            this.b.serialize(jsonWriter);
        }
        if (this.c != null) {
            jsonWriter.name("device");
            this.c.serialize(jsonWriter);
        }
        if (this.d != null) {
            jsonWriter.name("version");
            this.d.serialize(jsonWriter);
        }
        e.a(jsonWriter, "type", this.e);
        jsonWriter.endObject();
    }

    /* compiled from: Message_Installation */
    public static class a implements c {
        public final String a;
        public final String b;
        @Nullable
        public final String c;
        public final String d;
        public final String e;

        public a(String str, String str2, @Nullable String str3, String str4, String str5) {
            this.a = str;
            this.b = str2;
            this.c = str3;
            this.d = str4;
            this.e = str5;
        }

        @SuppressLint({"HardwareIds"})
        @NonNull
        public static a a(Context context, @NonNull String str, @NonNull String str2) {
            return new a(str2, Build.SERIAL, e.a(context), str, a(context));
        }

        private static String a(Context context) {
            try {
                return Settings.Secure.getString(context.getContentResolver(), "android_id");
            } catch (RuntimeException e2) {
                return "";
            }
        }

        public void serialize(JsonWriter jsonWriter) {
            jsonWriter.beginObject();
            f.a(jsonWriter, "imsi", this.a);
            f.a(jsonWriter, "sn", this.b);
            f.a(jsonWriter, "mac", this.c);
            f.a(jsonWriter, "deviceId", this.d);
            f.a(jsonWriter, "androidId", this.e);
            jsonWriter.endObject();
        }

        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (!(obj instanceof a)) {
                return false;
            }
            a aVar = (a) obj;
            if (!com.subao.common.e.a(this.a, aVar.a) || !com.subao.common.e.a(this.b, aVar.b) || !com.subao.common.e.a(this.c, aVar.c) || !com.subao.common.e.a(this.d, aVar.d) || !com.subao.common.e.a(this.e, aVar.e)) {
                return false;
            }
            return true;
        }
    }
}
