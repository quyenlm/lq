package com.subao.common.i;

import android.util.JsonWriter;

/* compiled from: MessageJsonUtils */
class e {
    /* JADX WARNING: Code restructure failed: missing block: B:32:?, code lost:
        r1.endObject();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x0063, code lost:
        com.subao.common.e.a((java.io.Closeable) r1);
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static java.lang.String a(byte[] r4) {
        /*
            r0 = 0
            if (r4 == 0) goto L_0x0006
            int r1 = r4.length
            if (r1 != 0) goto L_0x0007
        L_0x0006:
            return r0
        L_0x0007:
            android.util.JsonReader r1 = new android.util.JsonReader
            java.io.InputStreamReader r2 = new java.io.InputStreamReader
            java.io.ByteArrayInputStream r3 = new java.io.ByteArrayInputStream
            r3.<init>(r4)
            r2.<init>(r3)
            r1.<init>(r2)
            r1.beginObject()     // Catch:{ IOException -> 0x004c, RuntimeException -> 0x0055, all -> 0x005b }
        L_0x0019:
            boolean r2 = r1.hasNext()     // Catch:{ IOException -> 0x004c, RuntimeException -> 0x0055, all -> 0x005b }
            if (r2 == 0) goto L_0x0060
            java.lang.String r2 = "id"
            java.lang.String r3 = r1.nextName()     // Catch:{ IOException -> 0x004c, RuntimeException -> 0x0055, all -> 0x005b }
            boolean r2 = r2.equals(r3)     // Catch:{ IOException -> 0x004c, RuntimeException -> 0x0055, all -> 0x005b }
            if (r2 == 0) goto L_0x0057
            r1.beginObject()     // Catch:{ IOException -> 0x004c, RuntimeException -> 0x0055, all -> 0x005b }
        L_0x002e:
            boolean r2 = r1.hasNext()     // Catch:{ IOException -> 0x004c, RuntimeException -> 0x0055, all -> 0x005b }
            if (r2 == 0) goto L_0x0051
            java.lang.String r2 = "id"
            java.lang.String r3 = r1.nextName()     // Catch:{ IOException -> 0x004c, RuntimeException -> 0x0055, all -> 0x005b }
            boolean r2 = r2.equals(r3)     // Catch:{ IOException -> 0x004c, RuntimeException -> 0x0055, all -> 0x005b }
            if (r2 == 0) goto L_0x0048
            java.lang.String r0 = r1.nextString()     // Catch:{ IOException -> 0x004c, RuntimeException -> 0x0055, all -> 0x005b }
            com.subao.common.e.a((java.io.Closeable) r1)
            goto L_0x0006
        L_0x0048:
            r1.skipValue()     // Catch:{ IOException -> 0x004c, RuntimeException -> 0x0055, all -> 0x005b }
            goto L_0x002e
        L_0x004c:
            r2 = move-exception
        L_0x004d:
            com.subao.common.e.a((java.io.Closeable) r1)
            goto L_0x0006
        L_0x0051:
            r1.endObject()     // Catch:{ IOException -> 0x004c, RuntimeException -> 0x0055, all -> 0x005b }
            goto L_0x0019
        L_0x0055:
            r2 = move-exception
            goto L_0x004d
        L_0x0057:
            r1.skipValue()     // Catch:{ IOException -> 0x004c, RuntimeException -> 0x0055, all -> 0x005b }
            goto L_0x0019
        L_0x005b:
            r0 = move-exception
            com.subao.common.e.a((java.io.Closeable) r1)
            throw r0
        L_0x0060:
            r1.endObject()     // Catch:{ IOException -> 0x004c, RuntimeException -> 0x0055, all -> 0x005b }
            com.subao.common.e.a((java.io.Closeable) r1)
            goto L_0x0006
        */
        throw new UnsupportedOperationException("Method not decompiled: com.subao.common.i.e.a(byte[]):java.lang.String");
    }

    static void a(JsonWriter jsonWriter, String str, c cVar) {
        if (cVar != null) {
            jsonWriter.name(str);
            jsonWriter.value((long) cVar.a());
        }
    }
}
