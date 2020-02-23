package com.google.android.gms.dynamic;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;

final class zzf implements View.OnClickListener {
    private /* synthetic */ Intent zzaSA;
    private /* synthetic */ Context zztF;

    zzf(Context context, Intent intent) {
        this.zztF = context;
        this.zzaSA = intent;
    }

    public final void onClick(View view) {
        try {
            this.zztF.startActivity(this.zzaSA);
        } catch (ActivityNotFoundException e) {
            Log.e("DeferredLifecycleHelper", "Failed to start resolution intent", e);
        }
    }
}
