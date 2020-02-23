package com.google.android.gms.internal;

import android.content.DialogInterface;
import android.webkit.JsPromptResult;

final class zzala implements DialogInterface.OnCancelListener {
    private /* synthetic */ JsPromptResult zzacI;

    zzala(JsPromptResult jsPromptResult) {
        this.zzacI = jsPromptResult;
    }

    public final void onCancel(DialogInterface dialogInterface) {
        this.zzacI.cancel();
    }
}
