package com.google.android.gms.tagmanager;

import android.content.Context;
import android.net.Uri;
import com.google.android.gms.internal.zzbf;
import com.google.android.gms.internal.zzbg;
import com.google.android.gms.internal.zzbr;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

class zzm extends zzgi {
    private static final String ID = zzbf.ARBITRARY_PIXEL.toString();
    private static final String URL = zzbg.URL.toString();
    private static final String zzbDq = zzbg.ADDITIONAL_PARAMS.toString();
    private static final String zzbDr = zzbg.UNREPEATABLE.toString();
    private static String zzbDs;
    private static final Set<String> zzbDt = new HashSet();
    private final Context mContext;
    private final zza zzbDu;

    public interface zza {
        zzby zzAF();
    }

    static {
        String str = ID;
        zzbDs = new StringBuilder(String.valueOf(str).length() + 17).append("gtm_").append(str).append("_unrepeatable").toString();
    }

    public zzm(Context context) {
        this(context, new zzn(context));
    }

    private zzm(Context context, zza zza2) {
        super(ID, URL);
        this.zzbDu = zza2;
        this.mContext = context;
    }

    private final synchronized boolean zzeW(String str) {
        boolean z = true;
        synchronized (this) {
            if (!zzbDt.contains(str)) {
                if (this.mContext.getSharedPreferences(zzbDs, 0).contains(str)) {
                    zzbDt.add(str);
                } else {
                    z = false;
                }
            }
        }
        return z;
    }

    public final void zzq(Map<String, zzbr> map) {
        String zzb = map.get(zzbDr) != null ? zzgk.zzb(map.get(zzbDr)) : null;
        if (zzb == null || !zzeW(zzb)) {
            Uri.Builder buildUpon = Uri.parse(zzgk.zzb(map.get(URL))).buildUpon();
            zzbr zzbr = map.get(zzbDq);
            if (zzbr != null) {
                Object zzg = zzgk.zzg(zzbr);
                if (!(zzg instanceof List)) {
                    String valueOf = String.valueOf(buildUpon.build().toString());
                    zzdj.e(valueOf.length() != 0 ? "ArbitraryPixel: additional params not a list: not sending partial hit: ".concat(valueOf) : new String("ArbitraryPixel: additional params not a list: not sending partial hit: "));
                    return;
                }
                for (Object next : (List) zzg) {
                    if (!(next instanceof Map)) {
                        String valueOf2 = String.valueOf(buildUpon.build().toString());
                        zzdj.e(valueOf2.length() != 0 ? "ArbitraryPixel: additional params contains non-map: not sending partial hit: ".concat(valueOf2) : new String("ArbitraryPixel: additional params contains non-map: not sending partial hit: "));
                        return;
                    }
                    for (Map.Entry entry : ((Map) next).entrySet()) {
                        buildUpon.appendQueryParameter(entry.getKey().toString(), entry.getValue().toString());
                    }
                }
            }
            String uri = buildUpon.build().toString();
            this.zzbDu.zzAF().zzfh(uri);
            String valueOf3 = String.valueOf(uri);
            zzdj.v(valueOf3.length() != 0 ? "ArbitraryPixel: url = ".concat(valueOf3) : new String("ArbitraryPixel: url = "));
            if (zzb != null) {
                synchronized (zzm.class) {
                    zzbDt.add(zzb);
                    zzfu.zzd(this.mContext, zzbDs, zzb, "true");
                }
            }
        }
    }
}
