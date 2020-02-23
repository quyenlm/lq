package com.tencent.smtt.sdk;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import com.tencent.tbs.video.interfaces.IUserStateChangedListener;
import com.tencent.tbs.video.interfaces.a;

class bd {
    private static bd e = null;
    bg a = null;
    Context b;
    a c;
    IUserStateChangedListener d;

    private bd(Context context) {
        this.b = context.getApplicationContext();
        this.a = new bg(this.b);
    }

    public static synchronized bd a(Context context) {
        bd bdVar;
        synchronized (bd.class) {
            if (e == null) {
                e = new bd(context);
            }
            bdVar = e;
        }
        return bdVar;
    }

    public void a(int i, int i2, Intent intent) {
        if (this.c != null) {
            this.c.a(i, i2, intent);
        }
    }

    /* access modifiers changed from: package-private */
    public void a(Activity activity, int i) {
        this.a.a(activity, i);
    }

    public boolean a() {
        this.a.a();
        return this.a.b();
    }

    public boolean a(String str, Bundle bundle, a aVar) {
        if (bundle == null) {
            bundle = new Bundle();
        }
        if (!TextUtils.isEmpty(str)) {
            bundle.putString("videoUrl", str);
        }
        if (aVar != null) {
            this.a.a();
            if (!this.a.b()) {
                return false;
            }
            this.c = aVar;
            this.d = new be(this);
            this.c.a(this.d);
            bundle.putInt("callMode", 3);
        } else {
            bundle.putInt("callMode", 1);
        }
        bg bgVar = this.a;
        if (aVar == null) {
            this = null;
        }
        bgVar.a(bundle, (Object) this);
        return true;
    }
}
