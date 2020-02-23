package com.subao.common.i;

import android.util.JsonWriter;
import com.beetalk.sdk.plugin.impl.gglive.GGLiveConstants;
import com.subao.common.e.g;
import com.subao.common.n.f;
import com.vk.sdk.api.model.VKAttachments;

/* compiled from: Message_Start */
public class o implements com.subao.common.c {
    public final j a;
    public final long b = (System.currentTimeMillis() / 1000);
    public final c c;
    public final int d;
    public final int e;
    public final b f;
    public final p g;
    public final g h;

    /* compiled from: Message_Start */
    public enum a {
        UNKNOWN_EXCE_RESULT(0),
        NO_SCRIPT(1),
        SCRIPT_DOWNLOAD_FAIL(2),
        SCRIPT_EXEC_SUCCESS(3),
        SCRIPT_EXEC_FAIL(4);
        
        public final int f;

        private a(int i) {
            this.f = i;
        }
    }

    /* compiled from: Message_Start */
    public enum c {
        UNKNOWN_START_TYPE(0),
        START(1),
        DAILY(2);
        
        public final int d;

        private c(int i) {
            this.d = i;
        }
    }

    /* compiled from: Message_Start */
    public static class b implements com.subao.common.c {
        public final a a;
        public final String b;

        public void serialize(JsonWriter jsonWriter) {
            jsonWriter.beginObject();
            if (this.a != null) {
                jsonWriter.name(GGLiveConstants.PARAM.RESULT).value((long) this.a.f);
            }
            f.a(jsonWriter, VKAttachments.TYPE_NOTE, this.b);
            jsonWriter.endObject();
        }
    }

    public o(j jVar, c cVar, int i, int i2, b bVar, p pVar, g gVar) {
        this.a = jVar;
        this.c = cVar;
        this.d = i;
        this.e = i2;
        this.f = bVar;
        this.g = pVar;
        this.h = gVar;
    }

    public void serialize(JsonWriter jsonWriter) {
        jsonWriter.beginObject();
        if (this.a != null) {
            jsonWriter.name("id");
            this.a.serialize(jsonWriter);
        }
        jsonWriter.name("time").value(this.b);
        if (this.c != null) {
            jsonWriter.name("startType");
            jsonWriter.value((long) this.c.d);
        }
        f.a(jsonWriter, "nodeNum", Integer.valueOf(this.d));
        f.a(jsonWriter, "gameNum", Integer.valueOf(this.e));
        f.a(jsonWriter, "scriptResult", (com.subao.common.c) this.f);
        f.a(jsonWriter, "version", (com.subao.common.c) this.g);
        e.a(jsonWriter, "type", this.h);
        jsonWriter.endObject();
    }
}
