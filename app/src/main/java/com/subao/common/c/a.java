package com.subao.common.c;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.WorkerThread;
import com.subao.common.e.ai;
import com.subao.common.intf.RequestBuyCallback;
import com.subao.common.intf.RequestBuyResult;

/* compiled from: BuyRequester */
public class a implements Runnable {
    @NonNull
    public final String a;
    public final int b;
    @NonNull
    public String c;
    @NonNull
    private final String d;
    @Nullable
    private final ai e;
    @NonNull
    private final RequestBuyCallback f;

    public a(@NonNull String str, @Nullable ai aiVar, @NonNull String str2, @NonNull String str3, int i, @NonNull RequestBuyCallback requestBuyCallback) {
        this.d = str;
        this.e = aiVar;
        this.a = str2;
        this.c = str3;
        this.b = i;
        this.f = requestBuyCallback;
    }

    private static int a(int i) {
        return i < 0 ? 1006 : 1008;
    }

    @WorkerThread
    public void run() {
        c cVar = new c(this.d, this.e, this.a, new b(this.c, 1));
        cVar.run();
        String e2 = cVar.e();
        if (e2 == null) {
            this.f.onRequestBuyResult(a(cVar.d()), (RequestBuyResult) null);
            return;
        }
        d dVar = new d(this.d, this.e, this.a, e2, this.b);
        dVar.run();
        this.f.onRequestBuyResult(dVar.d(), dVar.e());
    }
}
