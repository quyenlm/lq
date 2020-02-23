package com.subao.common.e;

import android.os.AsyncTask;
import com.subao.common.j.a;
import com.subao.common.m.d;
import java.io.IOException;
import java.net.URL;

/* compiled from: BeaconCounter */
public class h extends AsyncTask<a, Void, Boolean> {
    private final String a;
    private final ai b;
    private final String c;

    /* compiled from: BeaconCounter */
    public interface a {
        void a(boolean z);
    }

    private h(String str, ai aiVar, String str2) {
        this.a = str;
        this.b = aiVar;
        this.c = str2;
    }

    public static void a(String str, ai aiVar, String str2, a aVar) {
        new h(str, new ai((String) null, aiVar.b, aiVar.c), str2).executeOnExecutor(d.a(), new a[]{aVar});
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public Boolean doInBackground(a... aVarArr) {
        boolean z;
        try {
            if (com.subao.common.j.a.a(new com.subao.common.j.a(15000, 15000).a(new URL(this.b.a, this.b.b, this.b.c, "/api/v1/" + this.a + "/counters/" + this.c), a.b.POST, a.C0013a.JSON.e), (byte[]) null).a == 201) {
                z = true;
                aVarArr[0].a(z);
                return Boolean.valueOf(z);
            }
        } catch (IOException e) {
            e.printStackTrace();
            z = false;
        } catch (RuntimeException e2) {
            e2.printStackTrace();
        }
        z = false;
        aVarArr[0].a(z);
        return Boolean.valueOf(z);
    }
}
