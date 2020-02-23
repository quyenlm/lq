package com.google.android.gms.internal;

import android.content.DialogInterface;
import android.webkit.JsResult;

final class zzakx implements DialogInterface.OnCancelListener {
    private /* synthetic */ JsResult zzacH;

    zzakx(JsResult jsResult) {
        this.zzacH = jsResult;
    }

    public final void onCancel(DialogInterface dialogInterface) {
        this.zzacH.cancel();
    }
}
