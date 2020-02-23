package com.subao.common.j;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.JsonReader;
import android.util.Log;
import com.subao.common.e;
import com.subao.common.g.c;
import com.subao.common.j.a;
import com.subao.common.m.d;
import java.io.Closeable;
import java.io.IOException;
import java.io.StringReader;
import java.net.HttpURLConnection;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPut;

/* compiled from: HttpBridge */
public class b {
    @NonNull
    private final c a;
    private final int b;

    public b(@NonNull c cVar, int i) {
        this.a = cVar;
        this.b = i;
    }

    public void a(int i, String str, String str2, @Nullable byte[] bArr, @Nullable String str3) {
        d.a(new C0014b(i, str, str2, bArr, str3));
    }

    /* access modifiers changed from: private */
    public void a(int i, @Nullable byte[] bArr) {
        this.a.a(this.b, i, bArr == null ? "" : new String(bArr), (String) null, (String) null);
    }

    /* compiled from: HttpBridge */
    static class a {
        private static final String[] a = {"GET", "POST", HttpPut.METHOD_NAME, HttpDelete.METHOD_NAME};
        private static final a.b[] b = {a.b.GET, a.b.POST, a.b.PUT, a.b.DELETE};

        @Nullable
        static a.b a(String str) {
            int length = a.length;
            for (int i = 0; i < length; i++) {
                if (a[i].compareToIgnoreCase(str) == 0) {
                    return b[i];
                }
            }
            return null;
        }
    }

    /* renamed from: com.subao.common.j.b$b  reason: collision with other inner class name */
    /* compiled from: HttpBridge */
    private class C0014b implements Runnable {
        private final int b;
        private final String c;
        private final String d;
        @Nullable
        private final byte[] e;
        @Nullable
        private final String f;

        C0014b(int i, String str, String str2, byte[] bArr, @Nullable String str3) {
            this.b = i;
            this.c = str;
            this.d = str2;
            this.e = bArr;
            this.f = str3;
        }

        public void run() {
            if (TextUtils.isEmpty(this.c) || TextUtils.isEmpty(this.d)) {
                b.this.a(-2, (byte[]) null);
                return;
            }
            if (this.e != null && this.e.length > 0 && com.subao.common.d.a("SubaoMessage") && this.c.contains("/report/client/")) {
                Log.d("SubaoMessage", new String(this.e));
            }
            a.b a2 = a.a(this.d);
            if (a2 == null) {
                b.this.a(-2, (byte[]) null);
                return;
            }
            try {
                a.c a3 = a(a2);
                b.this.a(a3.a, a3.b);
            } catch (IOException e2) {
                b.this.a(-1, (byte[]) null);
            }
        }

        private a.c a(a.b bVar) {
            HttpURLConnection a2 = new a(this.b, this.b).a(a.a(this.c), bVar, (String) null);
            a(a2, this.f);
            switch (bVar) {
                case GET:
                case DELETE:
                    return a.b(a2);
                default:
                    return a.a(a2, this.e);
            }
        }

        private void a(HttpURLConnection httpURLConnection, String str) {
            if (!TextUtils.isEmpty(str)) {
                JsonReader jsonReader = new JsonReader(new StringReader(str));
                try {
                    jsonReader.beginObject();
                    while (jsonReader.hasNext()) {
                        httpURLConnection.addRequestProperty(jsonReader.nextName(), jsonReader.nextString());
                    }
                    jsonReader.endObject();
                } finally {
                    e.a((Closeable) jsonReader);
                }
            }
        }
    }
}
