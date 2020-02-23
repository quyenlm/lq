package com.subao.common.c;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.JsonReader;
import com.subao.common.e.ai;
import com.subao.common.e.n;
import com.subao.common.intf.RequestBuyResult;
import com.subao.common.intf.RequestBuyResultForSamSung;
import com.subao.common.intf.RequestBuyResultForViVo;
import com.subao.common.j.a;
import com.tencent.midas.oversea.api.UnityPayHelper;

/* compiled from: PayRequester */
public class d extends g {
    @NonNull
    private final String a;
    private final int b;
    private int c = -1;
    private RequestBuyResult d;

    public d(@NonNull String str, @Nullable ai aiVar, @Nullable String str2, @NonNull String str3, int i) {
        super(str, aiVar, str2);
        this.a = str3;
        this.b = i;
    }

    public int d() {
        return this.c;
    }

    @Nullable
    public RequestBuyResult e() {
        return this.d;
    }

    /* access modifiers changed from: protected */
    public void a(@Nullable a.c cVar) {
        b aVar;
        int i = 1008;
        this.d = null;
        if (cVar == null) {
            this.c = 1006;
        } else if (cVar.a != 201) {
            this.c = 1008;
        } else {
            switch (this.b) {
                case 12:
                    aVar = new c();
                    break;
                case 14:
                    aVar = new a();
                    break;
                default:
                    this.c = 1011;
                    aVar = null;
                    break;
            }
            if (aVar != null) {
                this.d = aVar.a(cVar.b, this.a);
                if (this.d != null) {
                    i = 0;
                }
                this.c = i;
            }
        }
    }

    /* compiled from: PayRequester */
    private static abstract class b {
        /* access modifiers changed from: package-private */
        public abstract RequestBuyResult a(@NonNull JsonReader jsonReader, @NonNull String str);

        private b() {
        }

        /* access modifiers changed from: package-private */
        /* JADX WARNING: Code restructure failed: missing block: B:24:0x0025, code lost:
            r1 = e;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:25:0x0026, code lost:
            r2 = null;
            r3 = null;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:30:0x0030, code lost:
            r1 = th;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:31:0x0031, code lost:
            r2 = null;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:36:0x0039, code lost:
            r1 = e;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:37:0x003a, code lost:
            r2 = null;
            r3 = null;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:38:0x003d, code lost:
            r1 = e;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:39:0x003e, code lost:
            r2 = null;
            r3 = null;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:42:0x0045, code lost:
            r1 = e;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:43:0x0046, code lost:
            r2 = null;
            r3 = null;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:44:0x0049, code lost:
            r1 = e;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:45:0x004a, code lost:
            r2 = null;
            r3 = null;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:46:0x004d, code lost:
            r1 = e;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:47:0x004e, code lost:
            r3 = null;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:52:0x0056, code lost:
            r1 = e;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:53:0x0057, code lost:
            r2 = null;
            r3 = null;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:54:0x005a, code lost:
            r1 = e;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:55:0x005b, code lost:
            r3 = null;
         */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Removed duplicated region for block: B:30:0x0030 A[ExcHandler: all (th java.lang.Throwable), Splitter:B:5:0x0008] */
        /* JADX WARNING: Removed duplicated region for block: B:52:0x0056 A[ExcHandler: IOException (e java.io.IOException), Splitter:B:5:0x0008] */
        /* JADX WARNING: Removed duplicated region for block: B:54:0x005a A[ExcHandler: IOException (e java.io.IOException), Splitter:B:17:0x0017] */
        @android.support.annotation.Nullable
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public com.subao.common.intf.RequestBuyResult a(@android.support.annotation.Nullable byte[] r5, @android.support.annotation.NonNull java.lang.String r6) {
            /*
                r4 = this;
                r0 = 0
                if (r5 == 0) goto L_0x0007
                int r1 = r5.length
                r2 = 2
                if (r1 > r2) goto L_0x0008
            L_0x0007:
                return r0
            L_0x0008:
                android.util.JsonReader r2 = new android.util.JsonReader     // Catch:{ IOException -> 0x0056, RuntimeException -> 0x0025, all -> 0x0030 }
                java.io.InputStreamReader r1 = new java.io.InputStreamReader     // Catch:{ IOException -> 0x0056, RuntimeException -> 0x0039, all -> 0x0030 }
                java.io.ByteArrayInputStream r3 = new java.io.ByteArrayInputStream     // Catch:{ IOException -> 0x0056, RuntimeException -> 0x003d, all -> 0x0030 }
                r3.<init>(r5)     // Catch:{ IOException -> 0x0056, RuntimeException -> 0x0041, all -> 0x0030 }
                r1.<init>(r3)     // Catch:{ IOException -> 0x0056, RuntimeException -> 0x0045, all -> 0x0030 }
                r2.<init>(r1)     // Catch:{ IOException -> 0x0056, RuntimeException -> 0x0049, all -> 0x0030 }
                r2.beginObject()     // Catch:{ IOException -> 0x005a, RuntimeException -> 0x004d }
                com.subao.common.intf.RequestBuyResult r0 = r4.a((android.util.JsonReader) r2, (java.lang.String) r6)     // Catch:{ IOException -> 0x005a, RuntimeException -> 0x0050 }
                r2.endObject()     // Catch:{ IOException -> 0x005d, RuntimeException -> 0x0053 }
                com.subao.common.e.a((java.io.Closeable) r2)
                goto L_0x0007
            L_0x0025:
                r1 = move-exception
                r2 = r0
                r3 = r0
            L_0x0028:
                r0 = r3
                r1.printStackTrace()     // Catch:{ all -> 0x0036 }
                com.subao.common.e.a((java.io.Closeable) r2)
                goto L_0x0007
            L_0x0030:
                r1 = move-exception
                r2 = r0
            L_0x0032:
                com.subao.common.e.a((java.io.Closeable) r2)
                throw r1
            L_0x0036:
                r0 = move-exception
                r1 = r0
                goto L_0x0032
            L_0x0039:
                r1 = move-exception
                r2 = r0
                r3 = r0
                goto L_0x0028
            L_0x003d:
                r1 = move-exception
                r2 = r0
                r3 = r0
                goto L_0x0028
            L_0x0041:
                r1 = move-exception
                r2 = r0
                r3 = r0
                goto L_0x0028
            L_0x0045:
                r1 = move-exception
                r2 = r0
                r3 = r0
                goto L_0x0028
            L_0x0049:
                r1 = move-exception
                r2 = r0
                r3 = r0
                goto L_0x0028
            L_0x004d:
                r1 = move-exception
                r3 = r0
                goto L_0x0028
            L_0x0050:
                r1 = move-exception
                r3 = r0
                goto L_0x0028
            L_0x0053:
                r1 = move-exception
                r3 = r0
                goto L_0x0028
            L_0x0056:
                r1 = move-exception
                r2 = r0
                r3 = r0
                goto L_0x0028
            L_0x005a:
                r1 = move-exception
                r3 = r0
                goto L_0x0028
            L_0x005d:
                r1 = move-exception
                r3 = r0
                goto L_0x0028
            */
            throw new UnsupportedOperationException("Method not decompiled: com.subao.common.c.d.b.a(byte[], java.lang.String):com.subao.common.intf.RequestBuyResult");
        }
    }

    /* compiled from: PayRequester */
    private static class c extends b {
        private c() {
            super();
        }

        /* access modifiers changed from: package-private */
        public RequestBuyResult a(@NonNull JsonReader jsonReader, @NonNull String str) {
            String str2 = null;
            String str3 = null;
            String str4 = null;
            while (jsonReader.hasNext()) {
                String nextName = jsonReader.nextName();
                if (UnityPayHelper.PRODUCTID.equals(nextName)) {
                    str4 = jsonReader.nextString();
                } else if ("accessKey".equals(nextName)) {
                    str3 = jsonReader.nextString();
                } else if ("transNo".equals(nextName)) {
                    str2 = jsonReader.nextString();
                } else {
                    jsonReader.skipValue();
                }
            }
            if (str4 == null || str3 == null || str2 == null) {
                return null;
            }
            return new RequestBuyResultForViVo(str4, str, str3, str2);
        }
    }

    /* compiled from: PayRequester */
    private static class a extends b {
        private a() {
            super();
        }

        /* access modifiers changed from: package-private */
        public RequestBuyResult a(@NonNull JsonReader jsonReader, @NonNull String str) {
            String str2 = null;
            String str3 = null;
            while (jsonReader.hasNext()) {
                String nextName = jsonReader.nextName();
                if (UnityPayHelper.PRODUCTID.equals(nextName)) {
                    str3 = jsonReader.nextString();
                } else if ("transid".equals(nextName)) {
                    str2 = jsonReader.nextString();
                } else {
                    jsonReader.skipValue();
                }
            }
            if (TextUtils.isEmpty(str3) || TextUtils.isEmpty(str2)) {
                return null;
            }
            return new RequestBuyResultForSamSung(str3, str, str2);
        }
    }

    /* access modifiers changed from: protected */
    @NonNull
    public String c() {
        return String.format("/api/v1/%s/orders/%s/payment", new Object[]{f(), this.a});
    }

    /* access modifiers changed from: protected */
    @NonNull
    public a.b a() {
        return a.b.POST;
    }

    /* access modifiers changed from: protected */
    @Nullable
    public byte[] b() {
        return String.format(n.b, "{\"payType\":%d}", new Object[]{Integer.valueOf(this.b)}).getBytes();
    }
}
