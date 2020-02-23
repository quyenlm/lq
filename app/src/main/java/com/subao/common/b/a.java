package com.subao.common.b;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.subao.common.c.f;
import com.subao.common.e.ai;
import com.subao.common.e.r;
import com.subao.common.e.s;
import com.subao.common.f.c;
import com.subao.common.intf.RequestTrialCallback;
import com.subao.common.j.n;
import com.subao.common.m.d;

/* compiled from: AuthExecutor */
public class a {
    private static final j a = new j();
    @Nullable
    private static volatile byte[] b = null;

    public static boolean a(@NonNull String str, @Nullable ai aiVar, @NonNull e eVar, @Nullable RequestTrialCallback requestTrialCallback) {
        if (1 == eVar.e) {
            return a(str, aiVar, eVar.b, requestTrialCallback);
        }
        if (requestTrialCallback != null) {
            requestTrialCallback.onRequestTrialResult(1010);
        }
        return false;
    }

    public static boolean a(@NonNull String str, @Nullable ai aiVar, @NonNull String str2, @Nullable RequestTrialCallback requestTrialCallback) {
        d.a(new f(str, aiVar, str2, new C0002a(requestTrialCallback)));
        return true;
    }

    public static void a(@NonNull s.a aVar, @NonNull e eVar, @NonNull r.a aVar2, boolean z) {
        new r(aVar, new s.d(eVar.a, eVar.b), aVar2, z).a(d.a());
    }

    public static void a(ai aiVar, String str, c cVar) {
        d.a(aiVar, str);
    }

    public static void a(boolean z) {
        d.a(z);
    }

    @Nullable
    public static synchronized byte[] a() {
        byte[] bArr;
        synchronized (a.class) {
            bArr = b;
        }
        return bArr;
    }

    public static void a(String str, int i, String str2, n nVar) {
        d.a(str, i, str2, nVar);
    }

    /* renamed from: com.subao.common.b.a$a  reason: collision with other inner class name */
    /* compiled from: AuthExecutor */
    static class C0002a implements f.a {
        private final RequestTrialCallback a;

        C0002a(@Nullable RequestTrialCallback requestTrialCallback) {
            this.a = requestTrialCallback;
        }

        public void a(f.a.C0005a aVar, int i) {
            int i2;
            if (this.a != null) {
                if (i < 0) {
                    i2 = 1006;
                } else if (i == 201 && f.a.C0005a.ORDER == aVar) {
                    i2 = 0;
                } else {
                    i2 = 1008;
                }
                this.a.onRequestTrialResult(i2);
            }
        }
    }
}
