package com.google.android.gms.ads.internal.js;

import com.amazonaws.services.s3.util.Mimetypes;

final class zzh implements Runnable {
    private /* synthetic */ zze zzKW;
    private /* synthetic */ String zzKY;

    zzh(zze zze, String str) {
        this.zzKW = zze;
        this.zzKY = str;
    }

    public final void run() {
        this.zzKW.zzJH.loadData(this.zzKY, Mimetypes.MIMETYPE_HTML, "UTF-8");
    }
}
