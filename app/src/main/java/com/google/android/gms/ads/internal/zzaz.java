package com.google.android.gms.ads.internal;

import android.content.Context;
import com.google.android.gms.common.internal.zzbo;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.dynamic.zzn;
import com.google.android.gms.internal.zzacs;
import com.google.android.gms.internal.zzadz;
import com.google.android.gms.internal.zzafr;
import com.google.android.gms.internal.zzua;
import com.google.android.gms.internal.zzub;
import com.google.android.gms.internal.zzut;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

final class zzaz implements Runnable {
    private /* synthetic */ zzay zzuN;

    zzaz(zzay zzay) {
        this.zzuN = zzay;
    }

    public final void run() {
        Context zza = this.zzuN.zzuM.mContext;
        Runnable runnable = this.zzuN.zzuL;
        zzbo.zzcz("Adapters must be initialized on the main thread.");
        Map<String, zzub> zzhm = zzbs.zzbD().zzhD().zzhm();
        if (zzhm != null && !zzhm.isEmpty()) {
            if (runnable != null) {
                try {
                    runnable.run();
                } catch (Throwable th) {
                    zzafr.zzc("Could not initialize rewarded ads.", th);
                    return;
                }
            }
            zzacs zzgO = zzacs.zzgO();
            if (zzgO != null) {
                Collection<zzub> values = zzhm.values();
                HashMap hashMap = new HashMap();
                IObjectWrapper zzw = zzn.zzw(zza);
                for (zzub zzub : values) {
                    for (zzua next : zzub.zzLY) {
                        String str = next.zzLP;
                        for (String next2 : next.zzLJ) {
                            if (!hashMap.containsKey(next2)) {
                                hashMap.put(next2, new ArrayList());
                            }
                            if (str != null) {
                                ((Collection) hashMap.get(next2)).add(str);
                            }
                        }
                    }
                }
                for (Map.Entry entry : hashMap.entrySet()) {
                    String str2 = (String) entry.getKey();
                    try {
                        zzadz zzav = zzgO.zzav(str2);
                        if (zzav != null) {
                            zzut zzgW = zzav.zzgW();
                            if (!zzgW.isInitialized() && zzgW.zzfu()) {
                                zzgW.zza(zzw, zzav.zzgX(), (List) entry.getValue());
                                String valueOf = String.valueOf(str2);
                                zzafr.zzaC(valueOf.length() != 0 ? "Initialized rewarded video mediation adapter ".concat(valueOf) : new String("Initialized rewarded video mediation adapter "));
                            }
                        }
                    } catch (Throwable th2) {
                        zzafr.zzc(new StringBuilder(String.valueOf(str2).length() + 56).append("Failed to initialize rewarded video mediation adapter \"").append(str2).append("\"").toString(), th2);
                    }
                }
            }
        }
    }
}
