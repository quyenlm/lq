package com.tencent.tp.b;

import android.content.Context;
import android.os.AsyncTask;
import com.tencent.midas.oversea.network.http.APNetworkManager2;
import com.tencent.tp.a.ac;
import com.tencent.tp.c.f;
import com.tencent.tp.c.g;
import com.tencent.tp.c.h;
import com.tencent.tp.c.i;
import com.tencent.tp.q;
import java.io.IOException;
import org.apache.http.client.ClientProtocolException;

public class c extends AsyncTask {
    private static final int g = 0;
    private static final int h = -1;
    private static final int i = -2;
    private static final int j = -99;
    private Context a;
    private a b;
    private String c;
    private int d;
    /* access modifiers changed from: private */
    public int e;
    /* access modifiers changed from: private */
    public int f;
    private f k = new d(this);

    public interface a {
        void a();

        void a(int i);

        void a(int i, int i2);

        void b();

        void c();
    }

    public c(Context context, a aVar, String str) {
        this.a = context;
        this.b = aVar;
        this.c = str;
    }

    private int a() {
        try {
            String c2 = i.c(this.a, "base.ini");
            h.a(ac.a, c2, 0, (f) null);
            g gVar = new g(this.a, c2, true);
            i.e(c2);
            String a2 = gVar.a(APNetworkManager2.HTTP_KEY_OVERSEAINFO, "size");
            if (a2 != null) {
                return Integer.parseInt(a2);
            }
            return 0;
        } catch (Exception e2) {
            return 0;
        }
    }

    private void b() {
        int a2 = a();
        String str = this.c;
        try {
            try {
                h.a(str, i.c(this.a, i.c(str)), a2, this.k);
            } catch (ClientProtocolException e2) {
                q.a(e2.toString());
                this.d = -2;
            } catch (IOException e3) {
                q.a(e3.toString());
                this.d = -2;
            } catch (InterruptedException e4) {
                this.d = -2;
            } catch (Exception e5) {
                this.d = -2;
            }
        } catch (IOException e6) {
            q.a(e6.toString());
            this.d = -1;
        }
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public Void doInBackground(Void... voidArr) {
        try {
            this.d = 0;
            b();
            return null;
        } catch (Exception e2) {
            q.a(e2.toString());
            this.d = j;
            return null;
        }
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public void onPostExecute(Void voidR) {
        if (this.b != null) {
            if (this.d == 0) {
                this.b.a();
            } else if (this.d == -1) {
                this.b.c();
            } else if (this.d == -2) {
                this.b.b();
            } else {
                this.b.a(this.d);
            }
        }
    }

    /* access modifiers changed from: protected */
    /* renamed from: b */
    public void onProgressUpdate(Void... voidArr) {
        if (this.b != null) {
            this.b.a(this.e, this.f);
        }
    }
}
