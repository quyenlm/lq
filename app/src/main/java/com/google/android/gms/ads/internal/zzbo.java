package com.google.android.gms.ads.internal;

import android.os.RemoteException;
import android.view.MotionEvent;
import android.view.View;
import com.google.android.gms.internal.zzafr;

final class zzbo implements View.OnTouchListener {
    private /* synthetic */ zzbm zzvf;

    zzbo(zzbm zzbm) {
        this.zzvf = zzbm;
    }

    public final boolean onTouch(View view, MotionEvent motionEvent) {
        if (this.zzvf.zzvd == null) {
            return false;
        }
        try {
            this.zzvf.zzvd.zza(motionEvent);
            return false;
        } catch (RemoteException e) {
            zzafr.zzc("Unable to process ad data", e);
            return false;
        }
    }
}
