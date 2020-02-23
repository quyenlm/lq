package com.tencent.tp.a;

import android.content.DialogInterface;

class ag implements DialogInterface.OnDismissListener {
    final /* synthetic */ af a;

    ag(af afVar) {
        this.a = afVar;
    }

    public void onDismiss(DialogInterface dialogInterface) {
        if (this.a.f != null) {
            this.a.f.a(0);
        }
    }
}
