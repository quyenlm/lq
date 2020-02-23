package com.google.android.gms.internal;

import android.content.Context;
import com.google.android.gms.ads.internal.zzbs;
import java.util.Map;
import org.json.JSONException;

final class zzach implements zzrd {
    private /* synthetic */ zzabu zzUA;
    private /* synthetic */ zzacg zzWj;
    private /* synthetic */ Context zztF;

    zzach(zzacg zzacg, Context context, zzabu zzabu) {
        this.zzWj = zzacg;
        this.zztF = context;
        this.zzUA = zzabu;
    }

    public final void zza(zzaka zzaka, Map<String, String> map) {
        String str;
        try {
            zzmo.zza(this.zztF, 1, zzbs.zzbz().zzj(map));
            this.zzWj.zzgN();
        } catch (JSONException e) {
            zzafr.zzb("Unable to save SDK Core Constants.", e);
        } finally {
            str = "/loadSdkConstants";
            zzaka.zziw().zzb(str, (zzrd) this);
            zzacg.zze(this.zzUA);
        }
    }
}
