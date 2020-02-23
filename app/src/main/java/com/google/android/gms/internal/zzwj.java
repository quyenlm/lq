package com.google.android.gms.internal;

import android.content.DialogInterface;

final class zzwj implements DialogInterface.OnClickListener {
    private /* synthetic */ zzwh zzNx;

    zzwj(zzwh zzwh) {
        this.zzNx = zzwh;
    }

    public final void onClick(DialogInterface dialogInterface, int i) {
        this.zzNx.zzan("Operation denied by user.");
    }
}
