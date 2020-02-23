package com.subao.common.b;

import android.util.JsonReader;
import com.subao.common.c;
import com.subao.common.e;
import com.subao.common.e.ai;
import com.subao.common.e.n;
import com.subao.common.intf.QueryOriginUserStateCallback;
import com.subao.common.intf.UserInfo;
import com.subao.common.j.a;
import com.subao.common.j.j;
import com.subao.common.m.d;
import com.subao.common.n.f;
import java.io.ByteArrayInputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

/* compiled from: OriginUserStateRequester */
public class g {
    public static void a(j jVar, ai aiVar, String str, UserInfo userInfo, long j, QueryOriginUserStateCallback queryOriginUserStateCallback, Object obj) {
        if (queryOriginUserStateCallback == null) {
            throw new NullPointerException();
        }
        d.a(new a(aiVar, str, userInfo, j, queryOriginUserStateCallback, obj));
    }

    /* compiled from: OriginUserStateRequester */
    private static class a implements Runnable {
        private final ai a;
        private final String b;
        private final UserInfo c;
        private final int d;
        private final QueryOriginUserStateCallback e;
        private final Object f;

        a(ai aiVar, String str, UserInfo userInfo, long j, QueryOriginUserStateCallback queryOriginUserStateCallback, Object obj) {
            this.a = aiVar == null ? n.d : aiVar;
            this.b = str;
            this.c = userInfo;
            this.d = (int) j;
            this.e = queryOriginUserStateCallback;
            this.f = obj;
        }

        public void run() {
            int i;
            C0003a aVar;
            String str;
            int i2;
            int i3 = 0;
            try {
                a.c a2 = a();
                if (a2.a != 200) {
                    i2 = 1008;
                    aVar = null;
                } else {
                    aVar = C0003a.a(a2);
                    i2 = 0;
                }
                i = i2;
            } catch (IOException e2) {
                e2.printStackTrace();
                i = 1006;
                aVar = null;
            } catch (RuntimeException e3) {
                e3.printStackTrace();
                i = 1007;
                aVar = null;
            }
            if (aVar == null) {
                str = null;
            } else {
                i3 = aVar.a;
                str = aVar.b;
            }
            this.e.onOriginUserState(this.c, this.f, i, i3, str);
        }

        private a.c a() {
            return com.subao.common.j.a.a(new com.subao.common.j.a(this.d, this.d).a(new URL(this.a.a, this.a.b, this.a.c, String.format("/api/v1/%s/tokeninfo", new Object[]{this.b})), a.b.POST, a.C0013a.JSON.e), f.a((c) this.c));
        }

        /* renamed from: com.subao.common.b.g$a$a  reason: collision with other inner class name */
        /* compiled from: OriginUserStateRequester */
        private static class C0003a {
            final int a;
            final String b;

            private C0003a(int i, String str) {
                this.a = i;
                this.b = str;
            }

            /* JADX INFO: finally extract failed */
            static C0003a a(a.c cVar) {
                String str = null;
                if (cVar == null || cVar.b == null || cVar.b.length < 2) {
                    return null;
                }
                int i = 0;
                JsonReader jsonReader = new JsonReader(new InputStreamReader(new ByteArrayInputStream(cVar.b)));
                try {
                    jsonReader.beginObject();
                    while (jsonReader.hasNext()) {
                        String nextName = jsonReader.nextName();
                        if ("origin_code".equals(nextName)) {
                            i = jsonReader.nextInt();
                        } else if ("origin_body".equals(nextName)) {
                            str = f.a(jsonReader);
                        } else {
                            jsonReader.skipValue();
                        }
                    }
                    e.a((Closeable) jsonReader);
                    return new C0003a(i, str);
                } catch (Throwable th) {
                    e.a((Closeable) jsonReader);
                    throw th;
                }
            }
        }
    }
}
