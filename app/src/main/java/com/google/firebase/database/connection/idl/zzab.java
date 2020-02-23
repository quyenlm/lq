package com.google.firebase.database.connection.idl;

import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.dynamic.zzn;
import com.google.android.gms.internal.oo;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

final class zzab extends zzx {
    private /* synthetic */ oo zzccg;

    zzab(oo ooVar) {
        this.zzccg = ooVar;
    }

    public final void onDisconnect() {
        this.zzccg.onDisconnect();
    }

    public final void zzGb() {
        this.zzccg.zzGb();
    }

    public final void zzN(IObjectWrapper iObjectWrapper) {
        this.zzccg.zzB((Map) zzn.zzE(iObjectWrapper));
    }

    public final void zza(List<String> list, IObjectWrapper iObjectWrapper, boolean z, long j) {
        this.zzccg.zza(list, zzn.zzE(iObjectWrapper), z, IPersistentConnectionImpl.zzaq(j));
    }

    public final void zza(List<String> list, List<zzak> list2, IObjectWrapper iObjectWrapper, long j) {
        List list3 = (List) zzn.zzE(iObjectWrapper);
        ArrayList arrayList = new ArrayList(list2.size());
        int i = 0;
        while (true) {
            int i2 = i;
            if (i2 < list2.size()) {
                arrayList.add(zzak.zza(list2.get(i2), list3.get(i2)));
                i = i2 + 1;
            } else {
                this.zzccg.zza(list, arrayList, IPersistentConnectionImpl.zzaq(j));
                return;
            }
        }
    }

    public final void zzaB(boolean z) {
        this.zzccg.zzaB(z);
    }
}
