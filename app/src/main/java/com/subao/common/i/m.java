package com.subao.common.i;

import android.support.annotation.NonNull;
import android.util.JsonWriter;
import com.subao.common.c;
import com.subao.common.e;
import com.subao.common.e.g;
import com.subao.common.e.n;
import com.subao.common.n.f;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/* compiled from: Message_EventMsg */
public class m implements c, Iterable<a> {
    public final j a;
    public final g b;
    public final p c;
    private final List<a> d;

    public m(j jVar, g gVar, p pVar, List<a> list) {
        this.a = jVar;
        this.b = gVar;
        this.c = pVar;
        this.d = list;
    }

    public boolean a() {
        return this.d != null && !this.d.isEmpty();
    }

    @NonNull
    public Iterator<a> iterator() {
        if (this.d != null) {
            return this.d.iterator();
        }
        return new Iterator<a>() {
            public boolean hasNext() {
                return false;
            }

            /* renamed from: a */
            public a next() {
                return null;
            }

            public void remove() {
            }
        };
    }

    public void serialize(JsonWriter jsonWriter) {
        jsonWriter.beginObject();
        f.a(jsonWriter, "id", (c) this.a);
        e.a(jsonWriter, "type", this.b);
        f.a(jsonWriter, "version", (c) this.c);
        if (a()) {
            jsonWriter.name("events");
            jsonWriter.beginArray();
            Iterator<a> it = iterator();
            while (it.hasNext()) {
                it.next().serialize(jsonWriter);
            }
            jsonWriter.endArray();
        }
        jsonWriter.endObject();
    }

    /* compiled from: Message_EventMsg */
    public static class a implements c, Iterable<Map.Entry<String, String>> {
        public final String a;
        public final long b;
        private final Map<String, String> c;

        public a(String str, Map<String, String> map) {
            this(str, System.currentTimeMillis() / 1000, map);
        }

        public a(String str, long j, Map<String, String> map) {
            this.a = str;
            this.b = j;
            this.c = map;
        }

        private static boolean a(Map<String, String> map, Map<String, String> map2) {
            boolean z;
            if (map == null) {
                if (map2 == null) {
                    z = true;
                } else {
                    z = false;
                }
                return z;
            } else if (map2 == null || map.size() != map2.size()) {
                return false;
            } else {
                for (Map.Entry next : map.entrySet()) {
                    if (!e.a(next.getValue(), map2.get((String) next.getKey()))) {
                        return false;
                    }
                }
                return true;
            }
        }

        public int a() {
            if (this.c == null) {
                return 0;
            }
            return this.c.size();
        }

        public boolean equals(Object obj) {
            boolean z = true;
            if (obj == null) {
                return false;
            }
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof a)) {
                return false;
            }
            a aVar = (a) obj;
            if (this.b != aVar.b || !e.a(this.a, aVar.a) || !a(this.c, aVar.c)) {
                z = false;
            }
            return z;
        }

        @NonNull
        public Iterator<Map.Entry<String, String>> iterator() {
            if (this.c != null) {
                return this.c.entrySet().iterator();
            }
            return new Iterator<Map.Entry<String, String>>() {
                public boolean hasNext() {
                    return false;
                }

                /* renamed from: a */
                public Map.Entry<String, String> next() {
                    return null;
                }

                public void remove() {
                }
            };
        }

        public void serialize(JsonWriter jsonWriter) {
            jsonWriter.beginObject();
            jsonWriter.name("id").value(this.a);
            jsonWriter.name("time").value(this.b);
            int a2 = a();
            if (a2 > 0) {
                jsonWriter.name("paras");
                jsonWriter.beginArray();
                Iterator<Map.Entry<String, String>> it = iterator();
                while (it.hasNext()) {
                    Map.Entry next = it.next();
                    jsonWriter.beginObject();
                    if (a2 > 1) {
                        jsonWriter.name("key").value((String) next.getKey());
                    }
                    jsonWriter.name("value").value((String) next.getValue());
                    jsonWriter.endObject();
                }
                jsonWriter.endArray();
            }
            jsonWriter.endObject();
        }
    }

    public String toString() {
        Locale locale = n.b;
        Object[] objArr = new Object[1];
        objArr[0] = Integer.valueOf(this.d == null ? 0 : this.d.size());
        return String.format(locale, "[Message_Event: count=%d]", objArr);
    }
}
