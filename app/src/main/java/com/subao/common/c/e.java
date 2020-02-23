package com.subao.common.c;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.WorkerThread;
import android.util.JsonReader;
import com.subao.common.e.ai;
import com.subao.common.intf.ProductList;
import com.subao.common.j.a;
import java.io.ByteArrayInputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStreamReader;

/* compiled from: ProductListRequester */
public class e extends g {
    @NonNull
    private final a a;

    /* compiled from: ProductListRequester */
    public interface a {
        @WorkerThread
        void a(int i, @Nullable ProductList productList);
    }

    public e(@NonNull String str, @Nullable ai aiVar, @NonNull a aVar) {
        super(str, aiVar, (String) null);
        this.a = aVar;
    }

    /* access modifiers changed from: protected */
    public boolean a_() {
        return true;
    }

    /* access modifiers changed from: protected */
    @NonNull
    public String c() {
        return "/api/v1/" + f() + "/products";
    }

    /* access modifiers changed from: protected */
    @NonNull
    public a.b a() {
        return a.b.GET;
    }

    /* access modifiers changed from: protected */
    public void a(@Nullable a.c cVar) {
        if (cVar == null) {
            this.a.a(-1, (ProductList) null);
        } else if (cVar.a != 200) {
            this.a.a(cVar.a, (ProductList) null);
        } else if (cVar.b == null || cVar.b.length <= 2) {
            this.a.a(500, (ProductList) null);
        } else {
            JsonReader jsonReader = new JsonReader(new InputStreamReader(new ByteArrayInputStream(cVar.b)));
            try {
                ProductList createFromJson = ProductList.createFromJson(jsonReader);
                if (createFromJson != null) {
                    this.a.a(cVar.a, createFromJson);
                    return;
                }
                com.subao.common.e.a((Closeable) jsonReader);
                this.a.a(500, (ProductList) null);
            } catch (IOException | RuntimeException e) {
                e.printStackTrace();
            } finally {
                com.subao.common.e.a((Closeable) jsonReader);
            }
        }
    }
}
