package com.google.android.gms.internal;

import java.util.HashMap;

public final class zzcao {
    private int zzaxu = 0;
    private HashMap<String, Integer> zzbcK = new HashMap<>();

    public final zzcao zzbd(int i) {
        this.zzaxu = i;
        return this;
    }

    public final zzcao zzs(String str, int i) {
        boolean z;
        switch (i) {
            case 0:
            case 1:
            case 2:
            case 3:
                z = true;
                break;
            default:
                z = false;
                break;
        }
        if (z) {
            this.zzbcK.put(str, Integer.valueOf(i));
        }
        return this;
    }

    public final zzcam zzvm() {
        return new zzcam(this.zzaxu, this.zzbcK);
    }
}
