package com.google.android.gms.internal;

import android.os.Bundle;
import android.os.DeadObjectException;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.zzbo;
import com.google.android.gms.common.internal.zzbx;

public final class zzbca implements zzbcw {
    /* access modifiers changed from: private */
    public final zzbcx zzaCZ;
    private boolean zzaDa = false;

    public zzbca(zzbcx zzbcx) {
        this.zzaCZ = zzbcx;
    }

    public final void begin() {
    }

    public final void connect() {
        if (this.zzaDa) {
            this.zzaDa = false;
            this.zzaCZ.zza((zzbcy) new zzbcc(this, this));
        }
    }

    public final boolean disconnect() {
        if (this.zzaDa) {
            return false;
        }
        if (this.zzaCZ.zzaCl.zzqf()) {
            this.zzaDa = true;
            for (zzbes zzqK : this.zzaCZ.zzaCl.zzaDK) {
                zzqK.zzqK();
            }
            return false;
        }
        this.zzaCZ.zzg((ConnectionResult) null);
        return true;
    }

    public final void onConnected(Bundle bundle) {
    }

    public final void onConnectionSuspended(int i) {
        this.zzaCZ.zzg((ConnectionResult) null);
        this.zzaCZ.zzaDY.zze(i, this.zzaDa);
    }

    public final void zza(ConnectionResult connectionResult, Api<?> api, boolean z) {
    }

    public final <A extends Api.zzb, R extends Result, T extends zzbay<R, A>> T zzd(T t) {
        return zze(t);
    }

    public final <A extends Api.zzb, T extends zzbay<? extends Result, A>> T zze(T t) {
        try {
            this.zzaCZ.zzaCl.zzaDL.zzb(t);
            zzbcp zzbcp = this.zzaCZ.zzaCl;
            Api.zze zze = zzbcp.zzaDF.get(t.zzpd());
            zzbo.zzb(zze, (Object) "Appropriate Api was not requested.");
            if (zze.isConnected() || !this.zzaCZ.zzaDU.containsKey(t.zzpd())) {
                if (zze instanceof zzbx) {
                    zzbx zzbx = (zzbx) zze;
                    zze = null;
                }
                t.zzb(zze);
                return t;
            }
            t.zzr(new Status(17));
            return t;
        } catch (DeadObjectException e) {
            this.zzaCZ.zza((zzbcy) new zzbcb(this, this));
        }
    }

    /* access modifiers changed from: package-private */
    public final void zzpU() {
        if (this.zzaDa) {
            this.zzaDa = false;
            this.zzaCZ.zzaCl.zzaDL.release();
            disconnect();
        }
    }
}
