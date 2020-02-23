package com.subao.common.e;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.subao.common.e.z;
import com.subao.common.g.c;
import org.json.JSONArray;
import org.json.JSONException;

/* compiled from: AccelNodesDownloader */
public class e extends z {
    private final c a;

    e(z.a aVar, c cVar) {
        super(aVar);
        this.a = cVar;
    }

    public static a a(z.a aVar, c cVar) {
        e eVar = new e(aVar, cVar);
        aa l = eVar.l();
        eVar.e(l);
        if (eVar.d(l)) {
            return b(l);
        }
        return new a(0, (String) null);
    }

    static a b(aa aaVar) {
        byte[] f = f(aaVar);
        if (f == null) {
            return null;
        }
        String str = new String(f);
        int i = 0;
        try {
            i = new JSONArray(str).length();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new a(i, str);
    }

    private static byte[] f(aa aaVar) {
        byte[] a2;
        if (aaVar == null || (a2 = aaVar.a()) == null || a2.length < 8) {
            return null;
        }
        return a2;
    }

    /* access modifiers changed from: protected */
    @NonNull
    public String a() {
        return "nodes";
    }

    /* access modifiers changed from: protected */
    @NonNull
    public String b() {
        return "AccelNodes";
    }

    /* access modifiers changed from: protected */
    @NonNull
    public String c() {
        return "v2";
    }

    /* access modifiers changed from: protected */
    public boolean c(aa aaVar) {
        return aaVar != null && aaVar.b() > 16;
    }

    /* access modifiers changed from: protected */
    public void a(@Nullable aa aaVar) {
        super.a(aaVar);
        if (aaVar != null) {
            a b = b(aaVar);
            this.a.b(0, "key_node_list", b == null ? null : b.b);
        }
    }

    /* compiled from: AccelNodesDownloader */
    public static class a {
        public final int a;
        public final String b;

        a(int i, String str) {
            this.a = i;
            this.b = str;
        }

        public String toString() {
            return String.format(n.b, "[Accel Nodes %d]", new Object[]{Integer.valueOf(this.a)});
        }

        public boolean equals(Object obj) {
            boolean z = true;
            if (obj == null) {
                return false;
            }
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof a)) {
                return false;
            }
            a aVar = (a) obj;
            if (this.a != aVar.a || !com.subao.common.e.a(this.b, aVar.b)) {
                z = false;
            }
            return z;
        }
    }
}
