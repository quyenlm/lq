package com.subao.common.c;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.WorkerThread;
import com.subao.common.c.e;
import com.subao.common.e.ai;
import com.subao.common.intf.Product;
import com.subao.common.intf.ProductList;

/* compiled from: TrialRequester */
public class f implements Runnable {
    @Nullable
    private static String a;
    @NonNull
    private final String b;
    @Nullable
    private final ai c;
    @NonNull
    private final String d;
    @NonNull
    private final a e;

    /* compiled from: TrialRequester */
    public interface a {

        /* renamed from: com.subao.common.c.f$a$a  reason: collision with other inner class name */
        /* compiled from: TrialRequester */
        public enum C0005a {
            PRODUCTS,
            ORDER
        }

        @WorkerThread
        void a(C0005a aVar, int i);
    }

    public f(@NonNull String str, @Nullable ai aiVar, @NonNull String str2, @NonNull a aVar) {
        this.b = str;
        this.c = aiVar;
        this.d = str2;
        this.e = aVar;
    }

    @WorkerThread
    public void run() {
        if (a == null) {
            b bVar = new b();
            new e(this.b, this.c, bVar).run();
            if (bVar.a != 200) {
                this.e.a(a.C0005a.PRODUCTS, bVar.a);
                return;
            }
            Product findByType = bVar.b.findByType(3);
            if (findByType == null) {
                this.e.a(a.C0005a.PRODUCTS, 500);
                return;
            }
            a = findByType.getId();
        }
        c cVar = new c(this.b, this.c, this.d, new b(a, 1));
        cVar.run();
        this.e.a(a.C0005a.ORDER, cVar.d());
    }

    /* compiled from: TrialRequester */
    private static class b implements e.a {
        int a;
        ProductList b;

        private b() {
            this.a = -1;
        }

        @WorkerThread
        public void a(int i, ProductList productList) {
            this.a = i;
            this.b = productList;
        }
    }
}
