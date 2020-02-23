package com.google.android.gms.ads.internal.js;

import com.google.android.gms.internal.zzafr;
import com.google.android.gms.internal.zzrd;
import com.google.android.gms.internal.zzzn;
import java.util.AbstractMap;
import java.util.HashSet;
import java.util.Iterator;
import org.json.JSONObject;

@zzzn
public final class zzak implements zzaj {
    private final zzai zzLB;
    private final HashSet<AbstractMap.SimpleEntry<String, zzrd>> zzLC = new HashSet<>();

    public zzak(zzai zzai) {
        this.zzLB = zzai;
    }

    public final void zza(String str, zzrd zzrd) {
        this.zzLB.zza(str, zzrd);
        this.zzLC.add(new AbstractMap.SimpleEntry(str, zzrd));
    }

    public final void zza(String str, JSONObject jSONObject) {
        this.zzLB.zza(str, jSONObject);
    }

    public final void zzb(String str, zzrd zzrd) {
        this.zzLB.zzb(str, zzrd);
        this.zzLC.remove(new AbstractMap.SimpleEntry(str, zzrd));
    }

    public final void zzb(String str, JSONObject jSONObject) {
        this.zzLB.zzb(str, jSONObject);
    }

    public final void zzfe() {
        Iterator<AbstractMap.SimpleEntry<String, zzrd>> it = this.zzLC.iterator();
        while (it.hasNext()) {
            AbstractMap.SimpleEntry next = it.next();
            String valueOf = String.valueOf(((zzrd) next.getValue()).toString());
            zzafr.v(valueOf.length() != 0 ? "Unregistering eventhandler: ".concat(valueOf) : new String("Unregistering eventhandler: "));
            this.zzLB.zzb((String) next.getKey(), (zzrd) next.getValue());
        }
        this.zzLC.clear();
    }

    public final void zzi(String str, String str2) {
        this.zzLB.zzi(str, str2);
    }
}
