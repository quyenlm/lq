package com.google.android.gms.internal;

final class or implements oi {
    private /* synthetic */ long zzcbg;
    private /* synthetic */ oq zzcbh;

    or(oq oqVar, long j) {
        this.zzcbh = oqVar;
        this.zzcbg = j;
    }

    public final void onError(String str) {
        if (this.zzcbg == this.zzcbh.zzcbf.zzcaZ) {
            oz unused = this.zzcbh.zzcbf.zzcaN = oz.Disconnected;
            wl zza = this.zzcbh.zzcbf.zzbZE;
            String valueOf = String.valueOf(str);
            zza.zzb(valueOf.length() != 0 ? "Error fetching token: ".concat(valueOf) : new String("Error fetching token: "), (Throwable) null, new Object[0]);
            this.zzcbh.zzcbf.zzGf();
            return;
        }
        this.zzcbh.zzcbf.zzbZE.zzb("Ignoring getToken error, because this was not the latest attempt.", (Throwable) null, new Object[0]);
    }

    public final void zzgF(String str) {
        if (this.zzcbg != this.zzcbh.zzcbf.zzcaZ) {
            this.zzcbh.zzcbf.zzbZE.zzb("Ignoring getToken result, because this was not the latest attempt.", (Throwable) null, new Object[0]);
        } else if (this.zzcbh.zzcbf.zzcaN == oz.GettingToken) {
            this.zzcbh.zzcbf.zzbZE.zzb("Successfully fetched token, opening connection", (Throwable) null, new Object[0]);
            this.zzcbh.zzcbf.zzgI(str);
        } else {
            ok.zzc(this.zzcbh.zzcbf.zzcaN == oz.Disconnected, "Expected connection state disconnected, but was %s", this.zzcbh.zzcbf.zzcaN);
            this.zzcbh.zzcbf.zzbZE.zzb("Not opening connection after token refresh, because connection was set to disconnected", (Throwable) null, new Object[0]);
        }
    }
}
