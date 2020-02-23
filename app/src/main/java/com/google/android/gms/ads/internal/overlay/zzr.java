package com.google.android.gms.ads.internal.overlay;

import android.content.Context;
import android.view.ViewGroup;
import android.view.ViewParent;
import com.google.android.gms.internal.zzaka;
import com.google.android.gms.internal.zzzn;

@zzzn
public final class zzr {
    public final int index;
    public final ViewGroup parent;
    public final ViewGroup.LayoutParams zzPa;
    public final Context zzqD;

    public zzr(zzaka zzaka) throws zzp {
        this.zzPa = zzaka.getLayoutParams();
        ViewParent parent2 = zzaka.getParent();
        this.zzqD = zzaka.zzit();
        if (parent2 == null || !(parent2 instanceof ViewGroup)) {
            throw new zzp("Could not get the parent of the WebView for an overlay.");
        }
        this.parent = (ViewGroup) parent2;
        this.index = this.parent.indexOfChild(zzaka.getView());
        this.parent.removeView(zzaka.getView());
        zzaka.zzA(true);
    }
}
