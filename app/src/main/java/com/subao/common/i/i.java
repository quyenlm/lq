package com.subao.common.i;

import android.content.Context;
import android.support.annotation.NonNull;
import com.subao.common.e.g;
import com.subao.common.e.k;
import com.subao.common.e.n;
import com.subao.common.j.j;
import com.subao.common.m.b;
import com.subao.common.n.c;
import com.subao.common.n.h;

/* compiled from: MessageToolsImpl */
public class i implements h {
    private final Context a;
    private final n.a b;
    @NonNull
    private final String c;
    @NonNull
    private final String d;
    @NonNull
    private final j e;
    private final a f;

    public i(Context context, n.a aVar, String str, String str2, @NonNull String str3, @NonNull String str4, @NonNull j jVar) {
        this.a = context.getApplicationContext();
        this.b = aVar;
        this.c = str3;
        this.d = str4;
        this.e = jVar;
        this.f = new a(context, a(aVar), p.a(str, str2));
    }

    static g a(n.a aVar) {
        switch (aVar) {
            case SDK:
            case ROM:
                return g.ANDROID_SDK;
            default:
                return g.ANDROID_APP;
        }
    }

    public void a(Runnable runnable) {
        if (h.b()) {
            runnable.run();
        } else {
            b.a().a(runnable);
        }
    }

    public void b() {
        a((Runnable) new a());
    }

    public Context a() {
        return this.a;
    }

    @NonNull
    public String c() {
        return this.c;
    }

    @NonNull
    public String d() {
        return this.d;
    }

    public a e() {
        return this.f;
    }

    /* compiled from: MessageToolsImpl */
    private static class a implements Runnable {
        private a() {
        }

        public void run() {
            k.a().a(c.a());
        }
    }
}
