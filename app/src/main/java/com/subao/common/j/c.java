package com.subao.common.j;

import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.subao.common.j.a;
import com.subao.common.m.d;
import java.io.IOException;
import java.util.List;

/* compiled from: HttpClient */
public class c {
    public static void a(List<m> list, n nVar, String str, byte[] bArr) {
        a.a(list, nVar, str, a.b.POST, bArr);
    }

    /* compiled from: HttpClient */
    static final class a extends AsyncTask<Void, Void, a.c> {
        private final String a;
        private final a.b b;
        private final byte[] c;
        private final List<m> d;
        private n e;

        private a(@NonNull n nVar, @NonNull String str, @NonNull a.b bVar, @Nullable byte[] bArr, @Nullable List<m> list) {
            this.e = nVar;
            this.a = str;
            this.b = bVar;
            this.c = bArr;
            this.d = list;
        }

        public static void a(List<m> list, n nVar, String str, a.b bVar, byte[] bArr) {
            new a(nVar, str, bVar, bArr, list).executeOnExecutor(d.a(), new Void[0]);
        }

        /* access modifiers changed from: protected */
        /* renamed from: a */
        public a.c doInBackground(Void... voidArr) {
            try {
                return a();
            } catch (IOException | RuntimeException e2) {
                e2.printStackTrace();
                return null;
            }
        }

        /* access modifiers changed from: protected */
        /* renamed from: a */
        public void onPostExecute(a.c cVar) {
            if (cVar != null) {
                this.e.a(cVar);
            } else {
                this.e.b();
            }
        }

        /* JADX WARNING: type inference failed for: r1v0 */
        /* JADX WARNING: type inference failed for: r1v1, types: [java.net.HttpURLConnection] */
        /* JADX WARNING: type inference failed for: r1v2 */
        /* JADX WARNING: type inference failed for: r1v3, types: [java.io.Closeable] */
        /* JADX WARNING: type inference failed for: r1v5 */
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        private com.subao.common.j.a.c a() {
            /*
                r5 = this;
                r1 = 0
                com.subao.common.j.a r0 = new com.subao.common.j.a     // Catch:{ all -> 0x0071 }
                r2 = 15000(0x3a98, float:2.102E-41)
                r3 = 15000(0x3a98, float:2.102E-41)
                r0.<init>(r2, r3)     // Catch:{ all -> 0x0071 }
                java.lang.String r2 = r5.a     // Catch:{ all -> 0x0071 }
                java.net.URL r2 = com.subao.common.j.a.a((java.lang.String) r2)     // Catch:{ all -> 0x0071 }
                com.subao.common.j.a$b r3 = r5.b     // Catch:{ all -> 0x0071 }
                com.subao.common.j.a$a r4 = com.subao.common.j.a.C0013a.JSON     // Catch:{ all -> 0x0071 }
                java.lang.String r4 = r4.e     // Catch:{ all -> 0x0071 }
                java.net.HttpURLConnection r2 = r0.a((java.net.URL) r2, (com.subao.common.j.a.b) r3, (java.lang.String) r4)     // Catch:{ all -> 0x0071 }
                java.util.List<com.subao.common.j.m> r0 = r5.d     // Catch:{ all -> 0x0038 }
                if (r0 == 0) goto L_0x0040
                java.util.List<com.subao.common.j.m> r0 = r5.d     // Catch:{ all -> 0x0038 }
                java.util.Iterator r3 = r0.iterator()     // Catch:{ all -> 0x0038 }
            L_0x0024:
                boolean r0 = r3.hasNext()     // Catch:{ all -> 0x0038 }
                if (r0 == 0) goto L_0x0040
                java.lang.Object r0 = r3.next()     // Catch:{ all -> 0x0038 }
                com.subao.common.j.m r0 = (com.subao.common.j.m) r0     // Catch:{ all -> 0x0038 }
                java.lang.String r4 = r0.a     // Catch:{ all -> 0x0038 }
                java.lang.String r0 = r0.b     // Catch:{ all -> 0x0038 }
                r2.addRequestProperty(r4, r0)     // Catch:{ all -> 0x0038 }
                goto L_0x0024
            L_0x0038:
                r0 = move-exception
                r1 = r2
            L_0x003a:
                if (r1 == 0) goto L_0x003f
                r1.disconnect()
            L_0x003f:
                throw r0
            L_0x0040:
                byte[] r0 = r5.c     // Catch:{ all -> 0x0038 }
                if (r0 == 0) goto L_0x0062
                byte[] r0 = r5.c     // Catch:{ all -> 0x0038 }
                int r0 = r0.length     // Catch:{ all -> 0x0038 }
                if (r0 <= 0) goto L_0x0062
                r0 = 1
                r2.setDoOutput(r0)     // Catch:{ all -> 0x0038 }
                byte[] r0 = r5.c     // Catch:{ all -> 0x0038 }
                int r0 = r0.length     // Catch:{ all -> 0x0038 }
                r2.setFixedLengthStreamingMode(r0)     // Catch:{ all -> 0x0038 }
                java.io.OutputStream r1 = r2.getOutputStream()     // Catch:{ all -> 0x006c }
                byte[] r0 = r5.c     // Catch:{ all -> 0x006c }
                r1.write(r0)     // Catch:{ all -> 0x006c }
                r1.flush()     // Catch:{ all -> 0x006c }
                com.subao.common.e.a((java.io.Closeable) r1)     // Catch:{ all -> 0x0038 }
            L_0x0062:
                com.subao.common.j.a$c r0 = com.subao.common.j.a.a((java.net.HttpURLConnection) r2)     // Catch:{ all -> 0x0038 }
                if (r2 == 0) goto L_0x006b
                r2.disconnect()
            L_0x006b:
                return r0
            L_0x006c:
                r0 = move-exception
                com.subao.common.e.a((java.io.Closeable) r1)     // Catch:{ all -> 0x0038 }
                throw r0     // Catch:{ all -> 0x0038 }
            L_0x0071:
                r0 = move-exception
                goto L_0x003a
            */
            throw new UnsupportedOperationException("Method not decompiled: com.subao.common.j.c.a.a():com.subao.common.j.a$c");
        }
    }
}
