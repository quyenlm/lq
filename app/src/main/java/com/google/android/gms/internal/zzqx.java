package com.google.android.gms.internal;

import android.net.Uri;
import android.text.TextUtils;
import com.google.android.gms.ads.internal.zzbs;
import java.util.Map;

final class zzqx implements zzrd {
    zzqx() {
    }

    public final void zza(zzaka zzaka, Map<String, String> map) {
        String str = map.get("u");
        if (str == null) {
            zzafr.zzaT("URL missing from click GMSG.");
            return;
        }
        Uri parse = Uri.parse(str);
        try {
            zzcu zziy = zzaka.zziy();
            parse = (zziy == null || !zziy.zzc(parse)) ? parse : zziy.zza(parse, zzaka.getContext(), zzaka.getView());
        } catch (zzcv e) {
            String valueOf = String.valueOf(str);
            zzafr.zzaT(valueOf.length() != 0 ? "Unable to append parameter to URL: ".concat(valueOf) : new String("Unable to append parameter to URL: "));
        }
        if ((((Boolean) zzbs.zzbL().zzd(zzmo.zzDy)).booleanValue() && zzbs.zzbY().zzp(zzaka.getContext())) && TextUtils.isEmpty(parse.getQueryParameter("fbs_aeid"))) {
            String zzw = zzbs.zzbY().zzw(zzaka.getContext());
            zzbs.zzbz();
            parse = zzagz.zzb(parse.toString(), "fbs_aeid", zzw);
            zzbs.zzbY().zzf(zzaka.getContext(), zzw);
        }
        new zzaiq(zzaka.getContext(), zzaka.zziz().zzaP, parse.toString()).zzhL();
    }
}
