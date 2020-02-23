package com.google.android.gms.internal;

import com.google.android.gms.internal.zzcwn;

final class zzcwt implements Runnable {
    private /* synthetic */ zzcwn zzbJp;
    private /* synthetic */ String zzbJw;
    private /* synthetic */ String zzbJx;
    private /* synthetic */ String zzbJy = null;

    zzcwt(zzcwn zzcwn, String str, String str2, String str3) {
        this.zzbJp = zzcwn;
        this.zzbJw = str;
        this.zzbJx = str2;
    }

    public final void run() {
        String str = this.zzbJw;
        zzcvk.v(new StringBuilder(String.valueOf(str).length() + 28).append("Starting to load container ").append(str).append(".").toString());
        if (this.zzbJp.zzbJl != 1) {
            zzcup.zzd("Unexpected state - container loading already initiated.", this.zzbJp.mContext);
            return;
        }
        int unused = this.zzbJp.zzbJl = 2;
        this.zzbJp.zzbJg.zzb(this.zzbJw, this.zzbJx, this.zzbJy, new zzcwn.zzb(this.zzbJp, (zzcwo) null));
    }
}
