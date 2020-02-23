package com.subao.common.e;

import android.os.SystemClock;
import android.support.annotation.Nullable;
import com.subao.common.e;
import java.util.ArrayList;
import java.util.List;

/* compiled from: Cache */
public class i<K, V> {
    private final long a;
    private final List<i<K, V>.a> b = new ArrayList(4);

    public i(long j) {
        this.a = j;
    }

    private static long a() {
        return SystemClock.elapsedRealtime();
    }

    @Nullable
    public synchronized V a(@Nullable K k) {
        V v;
        int b2 = b(k);
        if (b2 < 0) {
            v = null;
        } else {
            a aVar = this.b.get(b2);
            if (a() >= aVar.d) {
                this.b.remove(b2);
                v = null;
            } else {
                v = aVar.b;
            }
        }
        return v;
    }

    public synchronized void a(@Nullable K k, @Nullable V v) {
        a aVar = null;
        synchronized (this) {
            if (v != null) {
                aVar = new a(k, v, this.a + a());
            }
            int b2 = b(k);
            if (b2 < 0) {
                if (v != null) {
                    this.b.add(aVar);
                }
            } else if (v == null) {
                this.b.remove(b2);
            } else {
                this.b.set(b2, aVar);
            }
        }
    }

    private int b(@Nullable K k) {
        for (int size = this.b.size() - 1; size >= 0; size--) {
            if (e.a(k, this.b.get(size).a)) {
                return size;
            }
        }
        return -1;
    }

    /* compiled from: Cache */
    private class a {
        final K a;
        final V b;
        /* access modifiers changed from: private */
        public final long d;

        private a(K k, V v, long j) {
            this.a = k;
            this.b = v;
            this.d = j;
        }
    }
}
