package com.subao.common.c;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.WorkerThread;
import android.text.TextUtils;
import com.subao.common.e.ai;
import com.subao.common.j.a;
import com.vk.sdk.api.VKApiConst;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import org.apache.http.HttpHost;

/* compiled from: VaultRequester */
public abstract class g implements Runnable {
    @NonNull
    private final String a;
    @Nullable
    private final ai b;
    @Nullable
    private final String c;

    /* access modifiers changed from: protected */
    @NonNull
    public abstract a.b a();

    /* access modifiers changed from: protected */
    @WorkerThread
    public abstract void a(@Nullable a.c cVar);

    /* access modifiers changed from: protected */
    @NonNull
    public abstract String c();

    public g(@NonNull String str, @Nullable ai aiVar, @Nullable String str2) {
        this.a = str;
        this.b = aiVar;
        this.c = str2;
    }

    @WorkerThread
    public void run() {
        a.c a2;
        try {
            a.b a3 = a();
            HttpURLConnection a4 = new a(15000, 15000).a(i(), a3, g());
            a(a4);
            switch (a3) {
                case POST:
                case PUT:
                    a2 = a.a(a4, b());
                    break;
                default:
                    a2 = a.b(a4);
                    break;
            }
            a(a2);
        } catch (IOException | RuntimeException e) {
            e.printStackTrace();
            a((a.c) null);
        }
    }

    private void a(HttpURLConnection httpURLConnection) {
        Iterable<Map.Entry<String, String>> h = h();
        if (h != null) {
            for (Map.Entry next : h) {
                httpURLConnection.addRequestProperty((String) next.getKey(), (String) next.getValue());
            }
        }
        if (!TextUtils.isEmpty(this.c)) {
            httpURLConnection.setRequestProperty("Authorization", "Bearer " + this.c);
        }
    }

    /* access modifiers changed from: protected */
    public boolean a_() {
        return false;
    }

    /* access modifiers changed from: protected */
    @NonNull
    public String f() {
        return this.a;
    }

    /* access modifiers changed from: protected */
    @Nullable
    public String g() {
        return a.C0013a.JSON.e;
    }

    /* access modifiers changed from: protected */
    @Nullable
    public Iterable<Map.Entry<String, String>> h() {
        return null;
    }

    /* access modifiers changed from: protected */
    @Nullable
    public byte[] b() {
        return null;
    }

    /* access modifiers changed from: protected */
    @NonNull
    public final URL i() {
        if (this.b != null) {
            return new URL(this.b.a, this.b.b, this.b.c, c());
        }
        return new URL(a_() ? HttpHost.DEFAULT_SCHEME_NAME : VKApiConst.HTTPS, "api.xunyou.mobi", -1, c());
    }
}
