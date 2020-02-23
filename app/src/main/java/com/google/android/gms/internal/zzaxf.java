package com.google.android.gms.internal;

import android.app.PendingIntent;
import com.google.android.gms.common.api.Status;
import org.json.JSONObject;

final class zzaxf implements zzayt {
    private /* synthetic */ zzawy zzaxj;
    private /* synthetic */ zzaxe zzaxk;

    zzaxf(zzaxe zzaxe, zzawy zzawy) {
        this.zzaxk = zzaxe;
        this.zzaxj = zzawy;
    }

    public final void zza(long j, int i, Object obj) {
        if (obj == null) {
            try {
                this.zzaxk.setResult(new zzaxk(new Status(i, (String) null, (PendingIntent) null), (String) null, j, (JSONObject) null));
            } catch (ClassCastException e) {
                this.zzaxk.setResult(zzaxe.zzj(new Status(13)));
            }
        } else {
            zzaxm zzaxm = (zzaxm) obj;
            String str = zzaxm.zzaxn;
            if (i == 0 && str != null) {
                String unused = this.zzaxk.zzaxd.zzaxc = str;
            }
            this.zzaxk.setResult(new zzaxk(new Status(i, zzaxm.zzaxv, (PendingIntent) null), str, zzaxm.zzaxo, zzaxm.zzaxp));
        }
    }

    public final void zzx(long j) {
        this.zzaxk.setResult(zzaxe.zzj(new Status(2103)));
    }
}
