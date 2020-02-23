package com.google.android.gms.internal;

import android.support.v4.media.session.PlaybackStateCompat;
import com.google.firebase.database.DatabaseError;
import java.util.ArrayList;
import java.util.List;

final class tf implements om, td {
    private /* synthetic */ so zzceR;
    private final vu zzcfe;
    /* access modifiers changed from: private */
    public final th zzcff;

    public tf(so soVar, vu vuVar) {
        this.zzceR = soVar;
        this.zzcfe = vuVar;
        this.zzcff = soVar.zze(vuVar.zzIv());
    }

    public final String zzFY() {
        return this.zzcfe.zzIw().zzIP();
    }

    public final boolean zzFZ() {
        return yy.zzn(this.zzcfe.zzIw()) > PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID;
    }

    public final oc zzGa() {
        ww zzh = ww.zzh(this.zzcfe.zzIw());
        List<qr> zzFR = zzh.zzFR();
        ArrayList arrayList = new ArrayList(zzFR.size());
        for (qr zzHb : zzFR) {
            arrayList.add(zzHb.zzHb());
        }
        return new oc(arrayList, zzh.zzFS());
    }

    public final List<? extends vk> zzb(DatabaseError databaseError) {
        if (databaseError == null) {
            return this.zzcff != null ? this.zzceR.zza(this.zzcff) : this.zzceR.zzs(this.zzcfe.zzIv().zzFq());
        }
        wl zza = this.zzceR.zzbZE;
        String valueOf = String.valueOf(this.zzcfe.zzIv().zzFq());
        String valueOf2 = String.valueOf(databaseError.toString());
        zza.zze(new StringBuilder(String.valueOf(valueOf).length() + 19 + String.valueOf(valueOf2).length()).append("Listen at ").append(valueOf).append(" failed: ").append(valueOf2).toString(), (Throwable) null);
        return this.zzceR.zza(this.zzcfe.zzIv(), databaseError);
    }
}
