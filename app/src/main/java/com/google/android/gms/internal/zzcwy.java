package com.google.android.gms.internal;

import android.net.Uri;

final class zzcwy implements Runnable {
    private /* synthetic */ Uri zzbJB;
    private /* synthetic */ zzcwn zzbJp;

    zzcwy(zzcwn zzcwn, Uri uri) {
        this.zzbJp = zzcwn;
        this.zzbJB = uri;
    }

    public final void run() {
        String valueOf = String.valueOf(this.zzbJB);
        zzcvk.v(new StringBuilder(String.valueOf(valueOf).length() + 25).append("Preview requested to uri ").append(valueOf).toString());
        synchronized (this.zzbJp.zzbJj) {
            if (this.zzbJp.zzbJl == 2) {
                zzcvk.v("Still initializing. Defer preview container loading.");
                this.zzbJp.zzbJm.add(this);
                return;
            }
            String str = (String) this.zzbJp.zzf((String[]) null).first;
            if (str == null) {
                zzcvk.zzaT("Preview failed (no container found)");
            } else if (!this.zzbJp.zzbJh.zzc(str, this.zzbJB)) {
                String valueOf2 = String.valueOf(this.zzbJB);
                zzcvk.zzaT(new StringBuilder(String.valueOf(valueOf2).length() + 73).append("Cannot preview the app with the uri: ").append(valueOf2).append(". Launching current version instead.").toString());
            } else if (!this.zzbJp.zzuH) {
                String valueOf3 = String.valueOf(this.zzbJB);
                zzcvk.v(new StringBuilder(String.valueOf(valueOf3).length() + 84).append("Deferring container loading for preview uri: ").append(valueOf3).append("(Tag Manager has not been initialized).").toString());
            } else {
                String valueOf4 = String.valueOf(this.zzbJB);
                zzcvk.zzaS(new StringBuilder(String.valueOf(valueOf4).length() + 36).append("Starting to load preview container: ").append(valueOf4).toString());
                if (!this.zzbJp.zzbJg.zzCF()) {
                    zzcvk.zzaT("Failed to reset TagManager service for preview");
                    return;
                }
                boolean unused = this.zzbJp.zzuH = false;
                int unused2 = this.zzbJp.zzbJl = 1;
                this.zzbJp.initialize();
            }
        }
    }
}
