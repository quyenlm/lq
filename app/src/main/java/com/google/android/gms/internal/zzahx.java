package com.google.android.gms.internal;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

final class zzahx implements Runnable {
    private /* synthetic */ String zzZO;
    private /* synthetic */ boolean zzZP;
    private /* synthetic */ boolean zzZQ;
    final /* synthetic */ Context zztF;

    zzahx(zzahw zzahw, Context context, String str, boolean z, boolean z2) {
        this.zztF = context;
        this.zzZO = str;
        this.zzZP = z;
        this.zzZQ = z2;
    }

    public final void run() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.zztF);
        builder.setMessage(this.zzZO);
        if (this.zzZP) {
            builder.setTitle("Error");
        } else {
            builder.setTitle("Info");
        }
        if (this.zzZQ) {
            builder.setNeutralButton("Dismiss", (DialogInterface.OnClickListener) null);
        } else {
            builder.setPositiveButton("Learn More", new zzahy(this));
            builder.setNegativeButton("Dismiss", (DialogInterface.OnClickListener) null);
        }
        builder.create().show();
    }
}
