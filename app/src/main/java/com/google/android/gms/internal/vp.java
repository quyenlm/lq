package com.google.android.gms.internal;

import java.util.ArrayList;

final class vp implements Runnable {
    private /* synthetic */ ArrayList zzcgX;
    private /* synthetic */ vo zzcgY;

    vp(vo voVar, ArrayList arrayList) {
        this.zzcgY = voVar;
        this.zzcgX = arrayList;
    }

    public final void run() {
        ArrayList arrayList = this.zzcgX;
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            int i2 = i + 1;
            vk vkVar = (vk) arrayList.get(i);
            if (this.zzcgY.zzbZE.zzIH()) {
                wl zza = this.zzcgY.zzbZE;
                String valueOf = String.valueOf(vkVar.toString());
                zza.zzb(valueOf.length() != 0 ? "Raising ".concat(valueOf) : new String("Raising "), (Throwable) null, new Object[0]);
            }
            vkVar.zzHX();
            i = i2;
        }
    }
}
