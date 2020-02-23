package com.subao.common.e;

import android.support.annotation.NonNull;
import com.subao.common.e.s;
import com.subao.common.j.a;
import com.subao.common.m.d;
import com.vk.sdk.api.VKApiConst;
import org.apache.http.HttpHost;

/* compiled from: UserAccelInfoUploader */
public class an extends s {
    private static String c = HttpHost.DEFAULT_SCHEME_NAME;
    private final String d;

    an(@NonNull s.a aVar, @NonNull s.d dVar, @NonNull byte[] bArr) {
        super(aVar, dVar, a.b.POST, bArr);
        this.d = dVar.a;
    }

    public static void a(@NonNull s.a aVar, @NonNull s.d dVar, @NonNull byte[] bArr) {
        new an(aVar, dVar, bArr).a(d.a());
    }

    public static void a(String str) {
        if (VKApiConst.HTTPS.equals(str)) {
            c = str;
        } else {
            c = HttpHost.DEFAULT_SCHEME_NAME;
        }
    }

    /* access modifiers changed from: protected */
    public int a() {
        return 3;
    }

    /* access modifiers changed from: protected */
    public String b() {
        return "/api/v1/" + this.a.a + "/users/" + this.d + "/gameAccel";
    }

    /* access modifiers changed from: protected */
    public String c() {
        return c;
    }
}
