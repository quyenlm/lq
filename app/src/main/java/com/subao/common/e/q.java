package com.subao.common.e;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import com.subao.common.e;
import com.subao.common.e.s;
import com.subao.common.j.a;
import com.subao.common.m.d;
import com.vk.sdk.api.VKApiConst;

/* compiled from: HRCouponExchange */
public class q extends s {
    private final String c;
    private final String d;
    private String e = VKApiConst.HTTPS;

    protected q(@NonNull s.a aVar, @NonNull s.d dVar, @NonNull String str) {
        super(aVar, dVar, a.b.POST, (byte[]) null);
        this.c = dVar.a;
        if (!TextUtils.isEmpty(aVar.c.a)) {
            this.e = aVar.c.a;
        }
        this.d = str;
    }

    public static void a(@NonNull s.a aVar, @NonNull s.d dVar, @NonNull String str) {
        new q(aVar, dVar, str).a(d.a());
    }

    /* access modifiers changed from: protected */
    public int a() {
        return 5;
    }

    /* access modifiers changed from: protected */
    public String b() {
        return String.format("/api/v2/%s/users/%s/coupons/%s", new Object[]{e.a(this.a.a), e.a(this.c), e.a(this.d)});
    }

    /* access modifiers changed from: protected */
    public String c() {
        return this.e;
    }

    /* access modifiers changed from: protected */
    public void a(@Nullable s.b bVar) {
        if (!com.subao.common.d.a("SubaoData")) {
            return;
        }
        if (bVar == null || bVar.b == null) {
            Log.d("SubaoData", "HRCouponExchange result or response is null");
        } else {
            Log.d("SubaoData", "HRCouponExchange code: " + bVar.b.a);
        }
    }
}
