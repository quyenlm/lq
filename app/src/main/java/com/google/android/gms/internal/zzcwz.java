package com.google.android.gms.internal;

import com.google.android.gms.internal.zzcwn;

final class zzcwz implements Runnable {
    private /* synthetic */ boolean zzbJC;
    private /* synthetic */ zzcwn.zzb zzbJD;
    private /* synthetic */ String zzbJw;

    zzcwz(zzcwn.zzb zzb, boolean z, String str) {
        this.zzbJD = zzb;
        this.zzbJC = z;
        this.zzbJw = str;
    }

    public final void run() {
        if (zzcwn.this.zzbJl == 2) {
            if (this.zzbJC) {
                int unused = zzcwn.this.zzbJl = 3;
                String str = this.zzbJw;
                zzcvk.v(new StringBuilder(String.valueOf(str).length() + 18).append("Container ").append(str).append(" loaded.").toString());
            } else {
                int unused2 = zzcwn.this.zzbJl = 4;
                String valueOf = String.valueOf(this.zzbJw);
                zzcvk.e(valueOf.length() != 0 ? "Error loading container:".concat(valueOf) : new String("Error loading container:"));
            }
            while (!zzcwn.this.zzbJm.isEmpty()) {
                zzcwn.this.zzbHL.execute((Runnable) zzcwn.this.zzbJm.remove());
            }
            return;
        }
        zzcvk.zzaT("Container load callback completed after timeout");
    }
}
