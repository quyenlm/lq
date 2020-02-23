package com.tencent.tp;

import android.content.Context;
import android.os.AsyncTask;

class w extends AsyncTask {
    private Context a;
    private boolean b;
    private boolean c;
    private boolean d;

    public w(Context context, boolean z, boolean z2, boolean z3) {
        this.a = context;
        this.b = z;
        this.c = z2;
        this.d = z3;
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public Void doInBackground(Void... voidArr) {
        try {
            new u(this.b, this.c, this.d).a(this.a.getApplicationContext());
            return null;
        } catch (Exception e) {
            return null;
        }
    }
}
