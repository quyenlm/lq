package com.google.android.gms.internal;

import android.content.Context;
import com.amazonaws.services.s3.util.Mimetypes;

@zzzn
public class zzxz extends zzxr implements zzakf {
    zzxz(Context context, zzafg zzafg, zzaka zzaka, zzxy zzxy) {
        super(context, zzafg, zzaka, zzxy);
    }

    /* access modifiers changed from: protected */
    public final void zzgo() {
        if (this.zzQR.errorCode == -2) {
            this.zzJH.zziw().zza((zzakf) this);
            zzgq();
            zzafr.zzaC("Loading HTML in WebView.");
            this.zzJH.loadDataWithBaseURL(this.zzQR.zzPi, this.zzQR.body, Mimetypes.MIMETYPE_HTML, "UTF-8", (String) null);
        }
    }

    /* access modifiers changed from: protected */
    public void zzgq() {
    }
}
