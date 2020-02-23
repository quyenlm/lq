package com.google.android.gms.internal;

import android.content.Context;
import android.support.annotation.Nullable;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.zzbo;
import com.google.android.gms.common.util.zze;
import com.google.android.gms.common.util.zzi;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class cd {
    private final Context mContext;
    private String zzbEa;
    /* access modifiers changed from: private */
    public final co zzbKv;
    private Map<String, cg<db>> zzbKw;
    private final Map<String, cz> zzbKx;
    private final zze zzvw;

    public cd(Context context) {
        this(context, new HashMap(), new co(context), zzi.zzrY());
    }

    private cd(Context context, Map<String, cz> map, co coVar, zze zze) {
        this.zzbEa = null;
        this.zzbKw = new HashMap();
        this.mContext = context.getApplicationContext();
        this.zzvw = zze;
        this.zzbKv = coVar;
        this.zzbKx = map;
    }

    /* access modifiers changed from: package-private */
    public final void zza(Status status, cn cnVar) {
        String containerId = cnVar.zzCU().getContainerId();
        db zzCV = cnVar.zzCV();
        if (this.zzbKw.containsKey(containerId)) {
            cg cgVar = this.zzbKw.get(containerId);
            cgVar.zzak(this.zzvw.currentTimeMillis());
            if (status == Status.zzaBm) {
                cgVar.zzJ(status);
                cgVar.zzP(zzCV);
                return;
            }
            return;
        }
        this.zzbKw.put(containerId, new cg(status, zzCV, this.zzvw.currentTimeMillis()));
    }

    /* access modifiers changed from: package-private */
    public final void zza(cl clVar, List<Integer> list, int i, ce ceVar, @Nullable zzcuo zzcuo) {
        boolean z;
        cz czVar;
        zzcuo zzcuo2 = zzcuo;
        ce ceVar2 = ceVar;
        int i2 = i;
        List<Integer> list2 = list;
        cl clVar2 = clVar;
        while (true) {
            if (i2 == 0) {
                zzcvk.v("Starting to fetch a new resource");
            }
            if (i2 >= list2.size()) {
                String valueOf = String.valueOf(clVar2.zzCP().getContainerId());
                String concat = valueOf.length() != 0 ? "There is no valid resource for the container: ".concat(valueOf) : new String("There is no valid resource for the container: ");
                zzcvk.v(concat);
                ceVar2.zza(new cm(new Status(16, concat), list2.get(i2 - 1).intValue()));
                return;
            }
            switch (list2.get(i2).intValue()) {
                case 0:
                    bz zzCP = clVar2.zzCP();
                    cg cgVar = this.zzbKw.get(zzCP.getContainerId());
                    if (clVar2.zzCP().zzCL()) {
                        z = true;
                    } else {
                        z = (cgVar != null ? cgVar.zzCO() : this.zzbKv.zzfR(zzCP.getContainerId())) + 900000 < this.zzvw.currentTimeMillis();
                    }
                    if (z) {
                        cz czVar2 = this.zzbKx.get(clVar2.getId());
                        if (czVar2 == null) {
                            cz czVar3 = new cz();
                            this.zzbKx.put(clVar2.getId(), czVar3);
                            czVar = czVar3;
                        } else {
                            czVar = czVar2;
                        }
                        String valueOf2 = String.valueOf(zzCP.getContainerId());
                        zzcvk.v(new StringBuilder(String.valueOf(valueOf2).length() + 43).append("Attempting to fetch container ").append(valueOf2).append(" from network").toString());
                        czVar.zza(this.mContext, clVar2, 0, new cf(this, 0, clVar2, ci.zzbKD, list2, i2, ceVar2, zzcuo2));
                        return;
                    }
                    i2++;
                case 1:
                    bz zzCP2 = clVar2.zzCP();
                    String valueOf3 = String.valueOf(zzCP2.getContainerId());
                    zzcvk.v(new StringBuilder(String.valueOf(valueOf3).length() + 52).append("Attempting to fetch container ").append(valueOf3).append(" from a saved resource").toString());
                    this.zzbKv.zza(zzCP2.zzCK(), new cf(this, 1, clVar2, ci.zzbKD, list2, i2, ceVar2, (zzcuo) null));
                    return;
                case 2:
                    bz zzCP3 = clVar2.zzCP();
                    String valueOf4 = String.valueOf(zzCP3.getContainerId());
                    zzcvk.v(new StringBuilder(String.valueOf(valueOf4).length() + 56).append("Attempting to fetch container ").append(valueOf4).append(" from the default resource").toString());
                    this.zzbKv.zza(zzCP3.zzCK(), zzCP3.zzCI(), new cf(this, 2, clVar2, ci.zzbKD, list2, i2, ceVar2, (zzcuo) null));
                    return;
                default:
                    throw new UnsupportedOperationException(new StringBuilder(36).append("Unknown fetching source: ").append(i2).toString());
            }
        }
    }

    public final void zza(String str, @Nullable String str2, @Nullable String str3, List<Integer> list, ce ceVar, zzcuo zzcuo) {
        zzbo.zzaf(!list.isEmpty());
        cl clVar = new cl();
        zzcvs zzCw = zzcvs.zzCw();
        zza(clVar.zza(new bz(str, str2, str3, zzCw.isPreview() && str.equals(zzCw.getContainerId()), zzcvs.zzCw().zzCx())), Collections.unmodifiableList(list), 0, ceVar, zzcuo);
    }
}
