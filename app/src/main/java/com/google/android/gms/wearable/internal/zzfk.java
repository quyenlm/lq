package com.google.android.gms.wearable.internal;

import com.google.android.gms.internal.zzbaz;
import com.google.android.gms.wearable.NodeApi;
import java.util.ArrayList;

final class zzfk extends zzfc<NodeApi.GetConnectedNodesResult> {
    public zzfk(zzbaz<NodeApi.GetConnectedNodesResult> zzbaz) {
        super(zzbaz);
    }

    public final void zza(zzcy zzcy) {
        ArrayList arrayList = new ArrayList();
        arrayList.addAll(zzcy.zzbSO);
        zzR(new zzee(zzev.zzaY(zzcy.statusCode), arrayList));
    }
}
